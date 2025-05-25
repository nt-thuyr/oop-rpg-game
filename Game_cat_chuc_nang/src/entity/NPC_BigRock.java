package entity;

import main.GamePanel;
import object.OBJ_Door_Iron;
import tile_interactive.InteractiveTile;

import java.awt.*;
import java.util.ArrayList;

public class NPC_BigRock extends Entity{


    public static final String npcName = "Big Rock";

    public NPC_BigRock(GamePanel gp)
    {
        super(gp);

        name = npcName;
        direction = "down";
        speed = 4;

        solidArea = new Rectangle();
        solidArea.x = 2;
        solidArea.y = 6;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        solidArea.width = 44;
        solidArea.height = 40;


        dialogueSet = -1; //For first dialogueSet(= 0)

        getImage();
        setDialogue();
    }
    public void getImage()
    {
        up1 = setup("/npc/bigrock",gp.tileSize,gp.tileSize);
        up2 = setup("/npc/bigrock",gp.tileSize,gp.tileSize);
        down1 = setup("/npc/bigrock",gp.tileSize,gp.tileSize);
        down2 = setup("/npc/bigrock",gp.tileSize,gp.tileSize);
        left1 = setup("/npc/bigrock",gp.tileSize,gp.tileSize);
        left2 = setup("/npc/bigrock",gp.tileSize,gp.tileSize);
        right1 = setup("/npc/bigrock",gp.tileSize,gp.tileSize);
        right2 = setup("/npc/bigrock",gp.tileSize,gp.tileSize);
    }
    public void setDialogue()
    {
        dialogues[0][0] = "Một tảng đá khổng lồ!";
    }
    public void setAction()
    {

    }
    public void update()
    {

    }
    public void speak()
    {
        facePlayer();
        startDialogue(this,dialogueSet);

        dialogueSet++;
        if(dialogues[dialogueSet][0] == null)
        {
            //dialogueSet = 0;
            dialogueSet--; //displays last set
        }
    }
    public void move(String d)
    {
        this.direction = d;

        checkCollision();

        if(collisionOn == false)
        {
            switch(direction)
            {
                case "up": worldY -= speed; break;
                case "down": worldY += speed; break;
                case "left": worldX -= speed; break;
                case "right": worldX += speed; break;
            }
        }
    }

}
