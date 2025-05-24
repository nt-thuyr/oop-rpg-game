package entity;

import main.GamePanel;

import java.awt.*;
import java.util.Random;


public class NPC_OldMan extends Entity{

    public NPC_OldMan(GamePanel gp)
    {
        super(gp);
        direction = "down";
        speed = 1;

        solidArea = new Rectangle();
        solidArea.x = 8;
        solidArea.y = 16;
        solidArea.width = 32;
        solidArea.height = 32;

        solidAreaDefaultX = 8;
        solidAreaDefaultY = 16;

        dialogueSet = -1; //For first dialogueSet(= 0)

        getImage();
        setDialogue();
    }
    public void getImage()
    {
        up1 = setup("/npc/oldman_up_1",gp.tileSize,gp.tileSize);
        up2 = setup("/npc/oldman_up_2",gp.tileSize,gp.tileSize);
        down1 = setup("/npc/oldman_down_1",gp.tileSize,gp.tileSize);
        down2 = setup("/npc/oldman_down_2",gp.tileSize,gp.tileSize);
        left1 = setup("/npc/oldman_left_1",gp.tileSize,gp.tileSize);
        left2 = setup("/npc/oldman_left_2",gp.tileSize,gp.tileSize);
        right1 = setup("/npc/oldman_right_1",gp.tileSize,gp.tileSize);
        right2 = setup("/npc/oldman_right_2",gp.tileSize,gp.tileSize);
    }
    public void setDialogue()
    {
        dialogues[0][0] = "Này nhóc!";
        dialogues[0][1] = "Nhóc đến đây để tìm kho báu hửm?";
        dialogues[0][2] = "Ta đây từng là một đại pháp sư...\nNhưng giờ già cả rồi...";
        dialogues[0][4] = "Thôi, chúc nhóc may mắn ^^";
        dialogues[0][3] = "Có việc gì thì cứ đến hỏi ta.";

        dialogues[1][0] = "Mệt thì nghỉ bên bờ nước đi nhóc.";
        dialogues[1][1] = "Nhưng lũ quái sẽ hồi sinh nếu nhóc nghỉ ngơi.\nTa cũng chẳng hiểu tại sao, nhưng luật nó thế đấy.";
        dialogues[1][2] = "Dù sao thì... đừng cố quá, nhóc ạ.";

        dialogues[2][0] = "Làm sao để mở cánh cửa đó nhỉ...";
    }
    public void setAction()
    {
        if(onPath == true)
        {
//            int goalCol = 12;
//            int goalRow = 9;

            int goalCol = (gp.player.worldX + gp.player.solidArea.x) / gp.tileSize;
            int goalRow = (gp.player.worldY + gp.player.solidArea.y) / gp.tileSize;
            searchPath(goalCol, goalRow);

        }
        else
        {
            actionLockCounter++;

            if(actionLockCounter == 120)
            {
                Random random = new Random();
                int i = random.nextInt(100) + 1;  // pick up  a number from 1 to 100
                if(i <= 25)
                {
                    direction = "up";
                }
                if(i>25 && i <= 50)
                {
                    direction = "down";
                }
                if(i>50 && i <= 75)
                {
                    direction = "left";
                }
                if(i>75 && i <= 100)
                {
                    direction = "right";
                }
                actionLockCounter = 0; // reset
            }
        }
    }
    public void speak()
    {
        //ENTITY CLASS SPEAK()
        //Do this character specific stuff

        facePlayer();
        startDialogue(this,dialogueSet);

        dialogueSet++;
        if(dialogues[dialogueSet][0] == null)
        {
            //dialogueSet = 0;
            dialogueSet--; //displays last set
        }

        /*if(gp.player.life < gp.player.maxLife/3)
        {
            dialogueSet = 1;
        }*/
        //follow me, come with me  or something like that
        //onPath = true;
    }
}
