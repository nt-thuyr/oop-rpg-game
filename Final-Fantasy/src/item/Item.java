package item;

import entity.Character;

import main.GamePanel;
import main.UtilityTool;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public abstract class Item {
    private BufferedImage image;
    private String name;
    private String description;
    private int price;
    private boolean stackable;
    private Item loot;
    private int amount; // For stackable items, like potions
    private int value; // Value for pickup items, like hearts
    private boolean temp = false;

    private int attackValue; // For weapons
    private int knockBackPower; // For weapons
    private int defenseValue; // For shields

    //TYPE
    private int type;
    private static final int type_sword = 0;
    private static final int type_shield = 1;
    private static final int type_pickupOnly = 2;
    private static final int type_obstacle = 3;
    private static final int type_consumable = 4;

    protected GamePanel gp; //
    private int worldX, worldY; // Toạ độ trên thế giới
    private int motion1_duration; // Thời gian thực hiển chuyển động đầu tiên
    private int motion2_duration; // Thời gian thực hiện chuyển động thứ hai
    private boolean collision = false; // kiểm tra va chạm
    private Rectangle solidArea = new Rectangle(0, 0, 48, 48); // Khu vực va chạm mặc định
    private int solidAreaDefaultX, solidAreaDefaultY; // Toạ độ mặc định của khu vực va chạm

    private Rectangle attackArea = new Rectangle(0, 0, 0, 0); // Khu vực tấn công của thực thể


    public Item(GamePanel gp) {
        this.gp = gp;
    }
    public void interact() {
    }

//    public void setDialogue() { // thiết lập hội thoại
//        dialogues[0][0] = "No dialogue set.";
//    }

    public boolean inCamera() { // Kiểm tra xem thực thể có nằm trong phạm vi hiển thị hay không
        boolean inCamera = getWorldX() + gp.getTileSize() * 5 > gp.getPlayer().getWorldX() - gp.getPlayer().getScreenX() && //*5 because skeleton lord disappears when the top left corner isn't on the screen
                getWorldX() - gp.getTileSize() < gp.getPlayer().getWorldX() + gp.getPlayer().getScreenX() &&
                getWorldY() + gp.getTileSize() * 5 > gp.getPlayer().getWorldY() - gp.getPlayer().getScreenY() &&
                getWorldY() - gp.getTileSize() < gp.getPlayer().getWorldY() + gp.getPlayer().getScreenY();
        return inCamera;
    }

    public BufferedImage setup(String imagePath, int width, int height) { // Tải và thiết lập hình ảnh cho thực thể
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

//    public void startDialogue(Item item, int setNum) { // Bắt đầu đoạn hội thoại với thực thể được chỉ định
//        gp.setGameState(gp.getDialogueState());
//        gp.getUi().setDialogueEntity(item); // Always set the dialogue entity
//        setDialogueSet(setNum);
//    }

    // Polymorphic use method for all items
    public boolean use(Character user) {
        // Default use logic, can be overridden by subclasses
        return false;
    }

    public int getDetected(Character user, Item[][] target, String targetName)
    {
        int index = 999;

        //Check the surrounding object
        int nextWorldX = user.getLeftX();
        int nextWorldY = user.getTopY();

        switch (user.getDirection())
        {
            case "up" : nextWorldY = user.getTopY() - gp.getPlayer().getSpeed(); break;
            case "down": nextWorldY = user.getBottomY() + gp.getPlayer().getSpeed(); break;
            case "left": nextWorldX = user.getLeftX() - gp.getPlayer().getSpeed(); break;
            case "right": nextWorldX = user.getRightX() + gp.getPlayer().getSpeed(); break;
        }
        int col = nextWorldX/gp.getTileSize();
        int row = nextWorldY/gp.getTileSize();

        for(int i = 0; i < target[1].length; i++)
        {
            if(target[gp.getCurrentMap()][i] != null)
            {
                if (target[gp.getCurrentMap()][i].getCol() == col  // checking if player 1 tile away from target (key etc.) (must be same direction)
                        && target[gp.getCurrentMap()][i].getRow() == row
                        && target[gp.getCurrentMap()][i].getName().equals(targetName))
                {
                    index = i;
                    break;
                }
            }

        }
        return  index;
    }

//    public void setLoot(Item loot) {
//        this.loot = loot; // Gán giá trị loot cho thuộc tính loot
//        setDialogue(); // Thiết lập hội thoại
//    }

    public BufferedImage getImage() {
        return image;
    }

    public void setImage(BufferedImage image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public boolean isStackable() {
        return stackable;
    }

    public void setStackable(boolean stackable) {
        this.stackable = stackable;
    }

    public Item getLoot() {
        return loot;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public int getAttackValue() {
        return attackValue;
    }

    public void setAttackValue(int attackValue) {
        this.attackValue = attackValue;
    }

    public int getKnockBackPower() {
        return knockBackPower;
    }

    public void setKnockBackPower(int knockBackPower) {
        this.knockBackPower = knockBackPower;
    }

    public int getDefenseValue() {
        return defenseValue;
    }

    public void setDefenseValue(int defenseValue) {
        this.defenseValue = defenseValue;
    }

    public static int getType_shield() {
        return type_shield;
    }

    public static int getType_sword() {
        return type_sword;
    }

    public static int getType_pickupOnly() {
        return type_pickupOnly;
    }

    public static int getType_obstacle() {
        return type_obstacle;
    }

    public static int getType_consumable() {
        return type_consumable;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public void draw(Graphics2D g2) {
        if (inCamera()) {
            int tempScreenX = getWorldX() - gp.getPlayer().getWorldX() + gp.getPlayer().getScreenX();
            int tempScreenY = getWorldY() - gp.getPlayer().getWorldY() + gp.getPlayer().getScreenY();
            g2.drawImage(getImage(), tempScreenX, tempScreenY, null);
        }
    }

    // If you want items to manage their own stacking, you can add:
    public void addToInventory(java.util.List<Item> inventory) {
        if (isStackable()) {
            for (Item invItem : inventory) {
                if (invItem.getName().equals(getName())) {
                    invItem.setAmount(invItem.getAmount() + getAmount());
                    return;
                }
            }
        }
        inventory.add(this);
    }

    public boolean isTemp() {
        return temp;
    }

    public void setTemp(boolean temp) {
        this.temp = temp;
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


//    public String[][] getDialogues() {
//        return dialogues;
//    }
//
//    public void setDialogues(String[][] dialogues) {
//        this.dialogues = dialogues;
//    }



    public GamePanel getGp() {
        return gp;
    }

    public void setGp(GamePanel gp) {
        this.gp = gp;
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

    public int getCol() {
        return worldX / gp.getTileSize();
    }

    public int getRow() {
        return worldY / gp.getTileSize();
    }
}
