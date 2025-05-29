package entity;

import main.GamePanel;
import main.UtilityTool;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public abstract class Entity {
    protected GamePanel gp;
    private int worldX, worldY;
    protected String[][] dialogues = new String[20][20];
    private int dialogueSet = 0;
    private int dialogueIndex = 0;
    private int motion1_duration;
    private int motion2_duration;
    private boolean collision = false;
    private Rectangle solidArea = new Rectangle(0, 0, 48, 48);
    private int solidAreaDefaultX, solidAreaDefaultY;

    private Rectangle attackArea = new Rectangle(0, 0, 0, 0);

    public Entity(GamePanel gp) {
        this.gp = gp;
    }

    // All entities must implement their own draw logic
    public abstract void draw(Graphics2D g2);

    public void setDialogue() {
        // Default implementation, can be overridden by subclasses
        dialogues[0][0] = "No dialogue set.";
    };

    public void startDialogue(Entity entity, int setNum)
    {
        gp.setGameState(gp.getDialogueState());
        gp.getUi().setNpc(entity);
        setDialogueSet(setNum);
    }

    public BufferedImage setup(String imagePath, int width, int height) {
        UtilityTool uTool = new UtilityTool();
        BufferedImage image = null;

        try {
            image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(imagePath + ".png")));
            image = uTool.scaleImage(image, width, height); // it scales to tilesize, will fix for player attack(16px x 32px) by adding width and height
        } catch (IOException e) {
            e.printStackTrace();
        }
        return image;
    }

    public int getCol() {
        return (getWorldX() + getSolidArea().x) / gp.getTileSize();
    }

    public int getRow() {
        return (getWorldY() + getSolidArea().y) / gp.getTileSize();
    }

    public int getDialogueIndex() {
        return dialogueIndex;
    }

    public void setDialogueIndex(int dialogueIndex) {
        this.dialogueIndex = dialogueIndex;
    }

    public String[][] getDialogues() {
        return dialogues;
    }

    public void setDialogues(String[][] dialogues) {
        this.dialogues = dialogues;
    }

    public int getDialogueSet() {
        return dialogueSet;
    }

    public void setDialogueSet(int dialogueSet) {
        this.dialogueSet = dialogueSet;
    }

    public int getMotion1_duration() {
        return motion1_duration;
    }

    public void setMotion1_duration(int motion1_duration) {
        this.motion1_duration = motion1_duration;
    }

    public int getMotion2_duration() {
        return motion2_duration;
    }

    public void setMotion2_duration(int motion2_duration) {
        this.motion2_duration = motion2_duration;
    }

    public Rectangle getAttackArea() {
        return attackArea;
    }

    public void setAttackArea(Rectangle attackArea) {
        this.attackArea = attackArea;
    }

    public boolean isCollision() {
        return collision;
    }

    public void setCollision(boolean collision) {
        this.collision = collision;
    }

    public Rectangle getSolidArea() {
        return solidArea;
    }

    public void setSolidArea(Rectangle solidArea) {
        this.solidArea = solidArea;
    }

    public int getSolidAreaDefaultX() {
        return solidAreaDefaultX;
    }

    public void setSolidAreaDefaultX(int solidAreaDefaultX) {
        this.solidAreaDefaultX = solidAreaDefaultX;
    }

    public int getSolidAreaDefaultY() {
        return solidAreaDefaultY;
    }

    public void setSolidAreaDefaultY(int solidAreaDefaultY) {
        this.solidAreaDefaultY = solidAreaDefaultY;
    }

    public int getWorldX() {
        return worldX;
    }

    public void setWorldX(int worldX) {
        this.worldX = worldX;
    }

    public int getWorldY() {
        return worldY;
    }

    public void setWorldY(int worldY) {
        this.worldY = worldY;
    }
}
