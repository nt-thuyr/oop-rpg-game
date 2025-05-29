package tile;

import main.GamePanel;
import main.UtilityTool;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class TileManager {
    private GamePanel gp;
    private Tile[] tile;
    private int[][][] mapTileNum; // [StoreMapNumber][col][row] // For Transition Between Maps
    private boolean drawPath = true; // for debug (true = draws the path)
    private ArrayList<String> fileNames = new ArrayList<>();
    private ArrayList<String> collisionStatus = new ArrayList<>();

    public TileManager(GamePanel gp) {
        this.gp = gp;

        // READ TILE DATA FILE
        InputStream is = getClass().getResourceAsStream("/maps/tiledata.txt");
        BufferedReader br = new BufferedReader(new InputStreamReader(is));

        // GETTING TILE NAMES AND COLLISION INFO FROM THE FILE
        String line;
        try {
            while ((line = br.readLine()) != null) {
                fileNames.add(line);
                collisionStatus.add(br.readLine()); // read next line
            }
            br.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // INITIALIZE THE TILE ARRAY BASED ON THE fileNames SIZE
        tile = new Tile[fileNames.size()]; // grass, wall, water00, water01...
        getTileImage();

        // GET THE maxWorldCol & Row
        is = getClass().getResourceAsStream("/maps/dungeon02.txt");
        br = new BufferedReader(new InputStreamReader(is));

        try {
            String line2 = br.readLine();
            String[] maxTile = line2.split(" ");

            int maxWorldCol = maxTile.length;
            int maxWorldRow = maxTile.length;

            mapTileNum = new int[gp.getMaxMap()][maxWorldCol][maxWorldRow];

            br.close();
        } catch (IOException e) {
            System.out.println("Exception!");
        }

        loadMap("/maps/worldmap.txt", 0); // To change maps easily.
        loadMap("/maps/indoor01.txt", 1);
        loadMap("/maps/dungeon01.txt", 2);
        loadMap("/maps/dungeon02.txt", 3);
    }

    public void getTileImage() {
        for (int i = 0; i < fileNames.size(); i++) {
            String fileName;
            boolean collision;

            // Get a file name
            fileName = fileNames.get(i);

            // Get a collision status
            collision = collisionStatus.get(i).equals("true");

            setup(i, fileName, collision);
        }
    }

    public void setup(int index, String imageName, boolean collision) {
        UtilityTool uTool = new UtilityTool();
        try {
            tile[index] = new Tile();
            tile[index].setImage(ImageIO.read(getClass().getResourceAsStream("/tiles/" + imageName)));
            tile[index].setImage(uTool.scaleImage(tile[index].getImage(), gp.getTileSize(), gp.getTileSize()));
            tile[index].setCollision(collision);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadMap(String filePath, int map) {
        try {
            InputStream is = getClass().getResourceAsStream(filePath);
            BufferedReader br = new BufferedReader(new InputStreamReader(is)); // to read from txt

            int col = 0;
            int row = 0;

            while (col < gp.getMaxWorldCol() && row < gp.getMaxWorldRow()) {
                String line = br.readLine();

                while (col < gp.getMaxWorldCol()) {
                    String[] numbers = line.split(" ");
                    int num = Integer.parseInt(numbers[col]);

                    mapTileNum[map][col][row] = num;
                    col++;
                }
                if (col == gp.getMaxWorldCol()) {
                    col = 0;
                    row++;
                }
            }
            br.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void draw(Graphics2D g2) {
        int worldCol = 0;
        int worldRow = 0;

        while (worldCol < gp.getMaxWorldCol() && worldRow < gp.getMaxWorldRow()) {
            int tileNum = mapTileNum[gp.getCurrentMap()][worldCol][worldRow]; // drawing current map

            int worldX = worldCol * gp.getTileSize();
            int worldY = worldRow * gp.getTileSize();
            int screenX = worldX - gp.getPlayer().getWorldX() + gp.getPlayer().getScreenX();
            int screenY = worldY - gp.getPlayer().getWorldY() + gp.getPlayer().getScreenY();

            if (worldX + gp.getTileSize() > gp.getPlayer().getWorldX() - gp.getPlayer().getScreenX() &&
                    worldX - gp.getTileSize() < gp.getPlayer().getWorldX() + gp.getPlayer().getScreenX() &&
                    worldY + gp.getTileSize() > gp.getPlayer().getWorldY() - gp.getPlayer().getScreenY() &&
                    worldY - gp.getTileSize() < gp.getPlayer().getWorldY() + gp.getPlayer().getScreenY()) {
                g2.drawImage(tile[tileNum].getImage(), screenX, screenY, null);
            }

            worldCol++;

            if (worldCol == gp.getMaxWorldCol()) {
                worldCol = 0;
                worldRow++;
            }
        }
    }

    public ArrayList<String> getCollisionStatus() {
        return collisionStatus;
    }

    public void setCollisionStatus(ArrayList<String> collisionStatus) {
        this.collisionStatus = collisionStatus;
    }

    public boolean isDrawPath() {
        return drawPath;
    }

    public void setDrawPath(boolean drawPath) {
        this.drawPath = drawPath;
    }

    public ArrayList<String> getFileNames() {
        return fileNames;
    }

    public void setFileNames(ArrayList<String> fileNames) {
        this.fileNames = fileNames;
    }

    public GamePanel getGp() {
        return gp;
    }

    public void setGp(GamePanel gp) {
        this.gp = gp;
    }

    public int[][][] getMapTileNum() {
        return mapTileNum;
    }

    public void setMapTileNum(int[][][] mapTileNum) {
        this.mapTileNum = mapTileNum;
    }

    public Tile[] getTile() {
        return tile;
    }

    public void setTile(Tile[] tile) {
        this.tile = tile;
    }
}