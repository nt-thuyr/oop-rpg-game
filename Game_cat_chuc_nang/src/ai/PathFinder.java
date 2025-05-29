package ai;

import entity.Entity;
import main.GamePanel;

import java.util.ArrayList;

public class PathFinder extends PathFindingAlgorithm {

    GamePanel gp;
    Node[][] node;
    ArrayList<Node> openList = new ArrayList<>();
    public ArrayList<Node> pathList = new ArrayList<>();
    Node startNode, goalNode, currentNode;
    boolean goalReached = false;
    int step = 0;

    public PathFinder(GamePanel gp)
    {
        this.gp = gp;
        instantiateNodes();
    }
    public void instantiateNodes()
    {
        node = new Node[gp.maxWorldCol][gp.maxWorldRow];

        int col = 0;
        int row = 0;

        while(col < gp.maxWorldCol && row < gp.maxWorldRow)
        {
            node[col][row] = new Node(col,row);

            col++;
            if(col == gp.maxWorldCol)
            {
                col = 0;
                row++;
            }
        }
    }

    //reset previous pathfinding result
    public void resetNodes()
    {
        int col = 0;
        int row = 0;
        while(col < gp.maxWorldCol && row < gp.maxWorldRow)
        {
            //reset open, checked and solid state
            node[col][row].setOpen(false);
            node[col][row].setChecked(false);
            node[col][row].setSolid(false);

            col++;
            if(col == gp.maxWorldCol)
            {
                col = 0;
                row++;
            }
        }
        //reset other settings
        openList.clear();
        pathList.clear();
        goalReached = false;
        step = 0;
    }
    public void setNodes(int startCol, int startRow, int goalCol, int goalRow, Entity entity)
    {
        resetNodes();
        //set Start and Goal node
        startNode = node[startCol][startRow];
        currentNode = startNode;
        goalNode = node[goalCol][goalRow];
        openList.add(currentNode);

        int col = 0;
        int row = 0;
        while(col < gp.maxWorldCol && row < gp.maxWorldRow)
        {
            //SET SOLID NODE
            //CHECK TILES
            int tileNum = gp.tileM.mapTileNum[gp.currentMap][col][row];
            if(gp.tileM.tile[tileNum].collision == true)
            {
                node[col][row].setSolid(true) ;

            }
            //CHECK INTERACTIVE TILES
            for(int i = 0; i < gp.iTile[1].length; i++)
            {
                if(gp.iTile[gp.currentMap][i] != null &&
                        gp.iTile[gp.currentMap][i].destructible == true)
                {
                    int itCol = gp.iTile[gp.currentMap][i].worldX / gp.tileSize;
                    int itRow = gp.iTile[gp.currentMap][i].worldY / gp.tileSize;
                    node[itCol][itRow].setSolid(true);
                }
            }
            //SET COST
            getCost(node[col][row]);

            col++;
            if(col == gp.maxWorldCol)
            {
                col = 0;
                row++;
            }
        }
    }
    public void getCost(Node node) {
        // G Cost
        int xDistance = Math.abs(node.getCol() - startNode.getCol());
        int yDistance = Math.abs(node.getRow() - startNode.getRow());
        node.setgCost(xDistance + yDistance);

        // H Cost
        xDistance = Math.abs(node.getCol() - goalNode.getCol());
        yDistance = Math.abs(node.getRow() - goalNode.getRow());
        node.sethCost(xDistance + yDistance);

        // F Cost
        node.setfCost(node.getgCost() + node.gethCost());
    }
    public boolean search() {
        while (!goalReached && step < 500) {
            int col = currentNode.getCol();
            int row = currentNode.getRow();

            // Đánh dấu node đã kiểm tra
            currentNode.setChecked(true);
            openList.remove(currentNode);

            // Open các node lân cận
            if (row - 1 >= 0) openNode(node[col][row - 1]);
            if (col - 1 >= 0) openNode(node[col - 1][row]);
            if (row + 1 < gp.maxWorldRow) openNode(node[col][row + 1]);
            if (col + 1 < gp.maxWorldCol) openNode(node[col + 1][row]);

            // Tìm node có fCost tốt nhất
            int bestNodeIndex = 0;
            int bestNodefCost = Integer.MAX_VALUE;

            for (int i = 0; i < openList.size(); i++) {
                Node n = openList.get(i);
                int f = n.getfCost();
                if (f < bestNodefCost) {
                    bestNodeIndex = i;
                    bestNodefCost = f;
                } else if (f == bestNodefCost && n.getgCost() < openList.get(bestNodeIndex).getgCost()) {
                    bestNodeIndex = i;
                }
            }

            if (openList.isEmpty()) break;

            currentNode = openList.get(bestNodeIndex);

            if (currentNode == goalNode) {
                goalReached = true;
                trackThePath();
            }

            step++;
        }

        return goalReached;
    }
    public void openNode(Node node) {
        if (!node.isOpen() && !node.isChecked() && !node.isSolid()) {
            node.setOpen(true);
            node.setParent(currentNode);
            openList.add(node);
        }
    }

    public void trackThePath() {
        Node current = goalNode;

        while (current != startNode) {
            pathList.add(0, current);
            current = current.getParent();
        }
    }

}
