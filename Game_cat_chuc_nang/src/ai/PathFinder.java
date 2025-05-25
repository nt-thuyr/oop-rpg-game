package ai;

import entity.Entity;
import main.GamePanel;

import java.util.ArrayList;

public class PathFinder {

    GamePanel gp;
    private Node[][] node; // Mảng 2 chiều chứa các nút Node đại diện cho bản đồ
    private ArrayList<Node> openList = new ArrayList<>(); // Danh sách các nút mở
    private ArrayList<Node> pathList = new ArrayList<>(); // Đường đi được tìm thấy
    private Node startNode, goalNode, currentNode;
    private boolean goalReached = false;
    private int step = 0;

    public PathFinder(GamePanel gp)
    {
        this.gp = gp;
        instantiateNodes();
    }
    public void instantiateNodes() // Tạo các nút Node cho toàn bộ bản đồ
    {
        node = new Node[gp.maxWorldCol][gp.maxWorldRow]; // Khởi tạo mảng Node với kích thước bằng số cột và hàng của bản đồ

        int col = 0;
        int row = 0;

        while(col < gp.maxWorldCol && row < gp.maxWorldRow)
        {
            node[col][row] = new Node(col,row); // Tạo một nút Node mới tại vị trí (col, row)

            col++;
            if(col == gp.maxWorldCol)
            {
                col = 0;
                row++;
            }
        }
    }

    // Đặt lại trạng thái của tất cả các nút trước khi thực hiện tìm đường
    public void resetNodes()
    {
        int col = 0;
        int row = 0;
        while(col < gp.maxWorldCol && row < gp.maxWorldRow)
        {

            node[col][row].setOpen(false); // Đặt trạng thái nút là không mở
            node[col][row].setChecked(false); // Đặt trạng thái nút là chưa kiểm tra
            node[col][row].setSolid(false); // Đặt trạng thái nút là không rắn (có thể đi qua)

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

    // Thiết lập các nút bắt đầu và kết thúc, cũng như trạng thái của các nút khác
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
                node[col][row].setSolid(true);

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

            getCost(node[col][row]); // Tính toán chi phí G, H và F cho nút hiện tại

            col++; // Tăng cột
            if(col == gp.maxWorldCol)
            {
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
