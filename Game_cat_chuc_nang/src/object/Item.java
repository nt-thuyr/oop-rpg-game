package object;

import entity.Character;
import entity.Entity;
import main.GamePanel;
import java.awt.image.BufferedImage;

public abstract class Item extends Entity {
    private BufferedImage image;
    private String name;
    private String description;
    private int price;
    private boolean stackable;
    private Item loot;
    //TYPE
    private int type;
    private final int type_sword = 0;
    private final int type_shield = 1;
    private final int type_axe = 2;
    private final int type_pickaxe = 3;
    private final int type_consumable = 4;
    private final int type_pickupOnly = 5;
    private final int type_obstacle = 6;

    public Item(GamePanel gp) {
        super(gp);
    }

    public boolean interact() {
        // Default interaction logic, can be overridden by subclasses
        return false;
    }

    public int getDetected(Character user, Character[][] target, String targetName)
    {
        int index = 999;

        //Check the surrounding object
        int nextWorldX = user.getLeftX();
        int nextWorldY = user.getTopY();

        switch (user.getDirection())
        {
            case "up" : nextWorldY = user.getTopY() - gp.player.speed; break;
            case "down": nextWorldY = user.getBottomY() + gp.player.speed; break;
            case "left": nextWorldX = user.getLeftX() - gp.player.speed; break;
            case "right": nextWorldX = user.getRightX() + gp.player.speed; break;
        }
        int col = nextWorldX/gp.tileSize;
        int row = nextWorldY/gp.tileSize;

        for(int i = 0; i < target[1].length; i++)
        {
            if(target[gp.currentMap][i] != null)
            {
                if (target[gp.currentMap][i].getCol() == col                                //checking if player 1 tile away from target (key etc.) (must be same direction)
                        && target[gp.currentMap][i].getRow() == row
                        && target[gp.currentMap][i].name.equals(targetName))
                {
                    index = i;
                    break;
                }
            }

        }
        return  index;
    }

    public void setLoot(Item loot) {
        this.loot = loot; // Gán giá trị loot cho thuộc tính loot
        setDialogue(); // Thiết lập hội thoại
    }

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
}
