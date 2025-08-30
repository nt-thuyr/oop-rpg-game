package tile;

import main.GamePanel;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Map extends TileManager {

    private int width;
    private int height;

    private GamePanel gp;
    private BufferedImage[] worldMap;


    public Map(GamePanel gp) {
        super(gp);
        this.gp = gp;


        this.width = gp.getTileSize() * gp.getMaxWorldCol();
        this.height = gp.getTileSize() * gp.getMaxWorldRow();


        createWorldMap();

    }

    public void createWorldMap() {
        worldMap = new BufferedImage[gp.getMaxMap()];
        int worldMapWidth = gp.getTileSize() * gp.getMaxWorldCol();
        int worldMapHeight = gp.getTileSize() * gp.getMaxWorldRow();

        // Kiểm tra giá trị width và height
        if (worldMapWidth <= 0 || worldMapHeight <= 0) {
            throw new IllegalArgumentException("Width and height of the world map must be greater than 0. " +
                    "Check gp.getTileSize(), gp.getMaxWorldCol(), and gp.getMaxWorldRow().");
        }

        for (int i = 0; i < gp.getMaxMap(); i++) {
            worldMap[i] = new BufferedImage(worldMapWidth, worldMapHeight, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g2 = worldMap[i].createGraphics(); // Attach this g2 to worldMap Buffered image

            int col = 0;
            int row = 0;
            while (col < gp.getMaxWorldCol() && row < gp.getMaxWorldRow()) {
                int tileNum = getMapTileNum()[i][col][row]; // Sử dụng getter
                int x = gp.getTileSize() * col;
                int y = gp.getTileSize() * row;
                g2.drawImage(getTile()[tileNum].getImage(), x, y, null); // Sử dụng getter
                col++;
                if (col == gp.getMaxWorldCol()) {
                    col = 0;
                    row++;
                }
            }
            g2.dispose();
        }
    }

    public void drawFullMapScreen(Graphics2D g2) {
        // Background Color
        g2.setColor(Color.black);
        g2.fillRect(0, 0, gp.getScreenWidth(), gp.getScreenHeight());

        // Draw map
        int width = 500;
        int height = 500;
        int x = gp.getScreenWidth() / 2 - width / 2;
        int y = gp.getScreenHeight() / 2 - height / 2;
        g2.drawImage(worldMap[gp.getCurrentMap()], x, y, width, height, null);

        // Draw Player
        double scale = (double) (gp.getTileSize() * gp.getMaxWorldCol()) / width; // Scaling from actual map
        int playerX = (int) (x + gp.getPlayer().getWorldX() / scale);
        int playerY = (int) (y + gp.getPlayer().getWorldY() / scale);
        int playerSize = (int) (gp.getTileSize() / scale);
        g2.drawImage(gp.getPlayer().getDown1(), playerX, playerY, playerSize, playerSize, null);

        // Hint
        g2.setFont(gp.getUi().getDeterminationSans().deriveFont(28f));
        g2.setColor(Color.white);
        g2.drawString("Nhấn M để đóng", 750, 550);
    }

    @Override
    public GamePanel getGp() {
        return gp;
    }

    @Override
    public void setGp(GamePanel gp) {
        this.gp = gp;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }



    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

}
