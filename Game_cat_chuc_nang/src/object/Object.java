package object;

import entity.Entity;
import main.GamePanel;
import java.awt.image.BufferedImage;

public abstract class Object {
    GamePanel gp;
    private BufferedImage image;
    private String name;
    private String description;
    private int price;
    private boolean stackable;

    //TYPE
    private int type;
    private final int type_sword = 0;
    private final int type_shield = 1;
    private final int type_axe = 2;
    private final int type_pickaxe = 3;
    private final int type_consumable = 4;
    private final int type_pickupOnly = 5;
    private final int type_obstacle = 6;

    public Object(GamePanel gp) {
        this.gp = gp;
        this.image = null;
        this.name = "";
        this.description = "";
        this.price = 0;
        this.stackable = false;
        this.type = -1; // Default type
    }

    public abstract void setDialogue();

    public boolean interact() {
        // Default interaction logic, can be overridden by subclasses
        return false;
    }

    public int getDetected(Entity user, Entity[][] target, String targetName)
    {
        int index = 999;

        //Check the surrounding object
        int nextWorldX = user.getLeftX();
        int nextWorldY = user.getTopY();

        switch (user.direction)
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
}
