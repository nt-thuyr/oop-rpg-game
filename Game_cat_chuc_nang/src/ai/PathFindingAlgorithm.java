package ai;

public abstract class PathFindingAlgorithm {
    protected Node[][] node; // Ma trận các nút
    protected Node startNode; // Nút bắt đầu
    protected Node goalNode; // Nút đích
    protected boolean goalReached = false; // Biến kiểm tra xem đã đạt được mục tiêu chưa


    public abstract void resetNodes(); // Phương thức reset trạng thái của các nút

    public abstract void instantiateNodes();

    public abstract void trackThePath();
}
