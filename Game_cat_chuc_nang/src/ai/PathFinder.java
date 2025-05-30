package ai;

import entity.Character;
import main.GamePanel;
import tile_interactive.InteractiveTile;

import java.util.ArrayList;

public class PathFinder {

    GamePanel gp;
    private Node[][] node; // Mảng 2 chiều chứa các nút Node đại diện cho bản đồ
    private ArrayList<Node> openList = new ArrayList<>(); // Danh sách các nút mở
    private ArrayList<Node> pathList = new ArrayList<>(); // Đường đi được tìm thấy
    private Node startNode, goalNode, currentNode;
    private boolean goalReached = false;
    private int step = 0;

    public PathFinder(GamePanel gp) {
        this.gp = gp;
        instantiateNodes();
    }

    public void instantiateNodes() {
        node = new Node[gp.getMaxWorldCol()][gp.getMaxWorldRow()]; // Use getter for maxWorldCol and maxWorldRow

        int col = 0;
        int row = 0;

        while (col < gp.getMaxWorldCol() && row < gp.getMaxWorldRow()) {
            node[col][row] = new Node(col, row); // Create a new Node at (col, row)

            col++;
            if (col == gp.getMaxWorldCol()) {
                col = 0;
                row++;
            }
        }
    }

    public void resetNodes() {
        int col = 0;
        int row = 0;
        while (col < gp.getMaxWorldCol() && row < gp.getMaxWorldRow()) {
            node[col][row].setOpen(false); // Set node as not open
            node[col][row].setChecked(false); // Set node as unchecked
            node[col][row].setSolid(false); // Set node as not solid

            col++;
            if (col == gp.getMaxWorldCol()) {
                col = 0;
                row++;
            }
        }
        // Reset other settings
        openList.clear();
        pathList.clear();
        goalReached = false;
        step = 0;
    }

    public void setNodes(int startCol, int startRow, int goalCol, int goalRow, Character entity) {
        resetNodes();
        // Set Start and Goal node
        startNode = node[startCol][startRow];
        currentNode = startNode;
        goalNode = node[goalCol][goalRow];
        openList.add(currentNode);

        int col = 0;
        int row = 0;
        while (col < gp.getMaxWorldCol() && row < gp.getMaxWorldRow()) {
            // SET SOLID NODE
            // CHECK TILES
            int tileNum = gp.getTileM().getMapTileNum()[gp.getCurrentMap()][col][row];
            if (gp.getTileM().getTile()[tileNum].isCollision()) {
                node[col][row].setSolid(true);
            }
            // CHECK INTERACTIVE TILES
            for (int i = 0; i < gp.getiTile()[1].length; i++) {
                if (gp.getiTile()[gp.getCurrentMap()][i] != null &&
                        gp.getiTile()[gp.getCurrentMap()][i] instanceof InteractiveTile &&
                        ((InteractiveTile) gp.getiTile()[gp.getCurrentMap()][i]).isDestructible()) {

                    int itCol = ((InteractiveTile) gp.getiTile()[gp.getCurrentMap()][i]).getWorldX() / gp.getTileSize();
                    int itRow = ((InteractiveTile) gp.getiTile()[gp.getCurrentMap()][i]).getWorldY() / gp.getTileSize();
                    node[itCol][itRow].setSolid(true);
                }
            }

            getCost(node[col][row]); // Calculate G, H, and F costs for the current node

            col++; // Increment column
            if (col == gp.getMaxWorldCol()) {
                col = 0;
                row++;
            }
        }
    }
    // Tính toán chi phí G, H và F cho một nút
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
            if (row + 1 < gp.getMaxWorldRow()) openNode(node[col][row + 1]);
            if (col + 1 < gp.getMaxWorldCol()) openNode(node[col + 1][row]);

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


    public Node getCurrentNode() {
        return currentNode;
    }

    public void setCurrentNode(Node currentNode) {
        this.currentNode = currentNode;
    }

    public Node getGoalNode() {
        return goalNode;
    }

    public void setGoalNode(Node goalNode) {
        this.goalNode = goalNode;
    }

    public boolean isGoalReached() {
        return goalReached;
    }

    public void setGoalReached(boolean goalReached) {
        this.goalReached = goalReached;
    }

    public GamePanel getGp() {
        return gp;
    }

    public void setGp(GamePanel gp) {
        this.gp = gp;
    }

    public Node[][] getNode() {
        return node;
    }

    public void setNode(Node[][] node) {
        this.node = node;
    }

    public ArrayList<Node> getOpenList() {
        return openList;
    }

    public void setOpenList(ArrayList<Node> openList) {
        this.openList = openList;
    }

    public ArrayList<Node> getPathList() {
        return pathList;
    }

    public void setPathList(ArrayList<Node> pathList) {
        this.pathList = pathList;
    }

    public Node getStartNode() {
        return startNode;
    }

    public void setStartNode(Node startNode) {
        this.startNode = startNode;
    }

    public int getStep() {
        return step;
    }

    public void setStep(int step) {
        this.step = step;
    }
}