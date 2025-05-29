package monster;


import entity.Entity;
import main.GamePanel;
import object.OBJ_Coin_Bronze;
import object.OBJ_Door_Iron;
import object.OBJ_Heart;

import java.util.Random;

public class MON_SkeletonLord extends Entity {
    GamePanel gp; // cuz of different package
    public static final String monName = "Hài cốt vương";
    public MON_SkeletonLord(GamePanel gp) {
        super(gp);

        this.gp = gp;

        type = type_monster;
        boss = true;
        name = monName;
        defaultSpeed = 1;
        speed = defaultSpeed;
        maxLife = 40;
        life = maxLife;
        attack = 16;
        defense = 3;
        exp = 40;
        knockBackPower = 5;

        // Thêm pathfinding và AI
        onPath = false;

        // Điều chỉnh collision area để không bị kẹt tường
        int size = gp.tileSize * 5;  // Kích thước tổng thể của sprite
        int collisionSize = (int)(size * 0.4);  // Collision area chỉ bằng 40% kích thước sprite

        solidArea.x = (size - collisionSize)/2;  // Căn giữa collision area
        solidArea.y = (size - collisionSize)/2;
        solidArea.width = collisionSize;
        solidArea.height = collisionSize;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;

        // Attack area
        attackArea.width = 170;
        attackArea.height = 170;
        motion1_duration = 25;
        motion2_duration = 50;

        getImage();
        getAttackImage();
        setDialogue();
    }

    public void getImage()
    {

        int i = 5;
        if(inRage == false)
        {
            up1 = setup("/monster/skeletonlord_up_1",gp.tileSize * i,gp.tileSize * i);
            up2 = setup("/monster/skeletonlord_up_2",gp.tileSize * i,gp.tileSize * i);
            down1 = setup("/monster/skeletonlord_down_1",gp.tileSize * i,gp.tileSize * i);
            down2 = setup("/monster/skeletonlord_down_2",gp.tileSize * i,gp.tileSize * i);
            left1 = setup("/monster/skeletonlord_left_1",gp.tileSize * i,gp.tileSize * i);
            left2 = setup("/monster/skeletonlord_left_2",gp.tileSize * i,gp.tileSize * i);
            right1 = setup("/monster/skeletonlord_right_1",gp.tileSize * i,gp.tileSize * i);
            right2 = setup("/monster/skeletonlord_right_2",gp.tileSize * i,gp.tileSize * i);
        }
        if(inRage == true)
        {
            up1 = setup("/monster/skeletonlord_phase2_up_1",gp.tileSize * i,gp.tileSize * i);
            up2 = setup("/monster/skeletonlord_phase2_up_2",gp.tileSize * i,gp.tileSize * i);
            down1 = setup("/monster/skeletonlord_phase2_down_1",gp.tileSize * i,gp.tileSize * i);
            down2 = setup("/monster/skeletonlord_phase2_down_2",gp.tileSize * i,gp.tileSize * i);
            left1 = setup("/monster/skeletonlord_phase2_left_1",gp.tileSize * i,gp.tileSize * i);
            left2 = setup("/monster/skeletonlord_phase2_left_2",gp.tileSize * i,gp.tileSize * i);
            right1 = setup("/monster/skeletonlord_phase2_right_1",gp.tileSize * i,gp.tileSize * i);
            right2 = setup("/monster/skeletonlord_phase2_right_2",gp.tileSize * i,gp.tileSize * i);
        }
    }
    public void getAttackImage()
    {

        int i = 5;

        if(inRage == false)
        {
            attackUp1 = setup("/monster/skeletonlord_attack_up_1",gp.tileSize * i, gp.tileSize * 2 * i);
            attackUp2 = setup("/monster/skeletonlord_attack_up_2",gp.tileSize * i, gp.tileSize * 2 * i);
            attackDown1 = setup("/monster/skeletonlord_attack_down_1",gp.tileSize * i, gp.tileSize * 2 * i);
            attackDown2 = setup("/monster/skeletonlord_attack_down_2",gp.tileSize * i, gp.tileSize * 2 * i);
            attackLeft1 = setup("/monster/skeletonlord_attack_left_1",gp.tileSize * 2 * i, gp.tileSize * i);
            attackLeft2 = setup("/monster/skeletonlord_attack_left_2",gp.tileSize * 2 * i, gp.tileSize * i);
            attackRight1 = setup("/monster/skeletonlord_attack_right_1",gp.tileSize * 2 * i, gp.tileSize * i);
            attackRight2 = setup("/monster/skeletonlord_attack_right_2",gp.tileSize * 2 * i, gp.tileSize * i);
        }
        if(inRage == true)
        {
            attackUp1 = setup("/monster/skeletonlord_phase2_attack_up_1",gp.tileSize * i, gp.tileSize * 2 * i);
            attackUp2 = setup("/monster/skeletonlord_phase2_attack_up_2",gp.tileSize * i, gp.tileSize * 2 * i);
            attackDown1 = setup("/monster/skeletonlord_phase2_attack_down_1",gp.tileSize * i, gp.tileSize * 2 * i);
            attackDown2 = setup("/monster/skeletonlord_phase2_attack_down_2",gp.tileSize * i, gp.tileSize * 2 * i);
            attackLeft1 = setup("/monster/skeletonlord_phase2_attack_left_1",gp.tileSize * 2 * i, gp.tileSize * i);
            attackLeft2 = setup("/monster/skeletonlord_phase2_attack_left_2",gp.tileSize * 2 * i, gp.tileSize * i);
            attackRight1 = setup("/monster/skeletonlord_phase2_attack_right_1",gp.tileSize * 2 * i, gp.tileSize * i);
            attackRight2 = setup("/monster/skeletonlord_phase2_attack_right_2",gp.tileSize * 2 * i, gp.tileSize * i);
        }
    }
    public void setDialogue()
    {
        dialogues[0][0] = "Không ai có thể đánh cắp kho báu của ta!";
        dialogues[0][1] = "Mi sẽ bỏ mạng thôi!";
        dialogues[0][2] = "HÃY ĐÓN NHẬN CÁI CHẾT CỦA NGƯƠI ĐI!";

    }
    public void setAction() {
        if(inRage==false && life < maxLife/2) {
            inRage = true;
            getImage();
            getAttackImage();
            defaultSpeed++;
            speed = defaultSpeed;
            attack *= 2;
        }

        // Kiểm tra khoảng cách đến player
        int xDistance = Math.abs(worldX - gp.player.worldX);
        int yDistance = Math.abs(worldY - gp.player.worldY);
        int tileDistance = (xDistance + yDistance)/gp.tileSize;

        if(tileDistance < 8) {
            // Khi player trong tầm 8 tiles, bật chế độ đuổi theo
            onPath = true;

            // Tìm đường đi đến player
            int goalCol = getGoalCol(gp.player);
            int goalRow = getGoalRow(gp.player);
            searchPath(goalCol, goalRow);

            // Tấn công khi ở gần
            if(attacking == false) {
                int attackRate = inRage ? 30 : 60;
                checkAttackOrNot(attackRate, gp.tileSize*4, gp.tileSize*4);
            }
        } else {
            // Ngoài tầm 8 tiles thì di chuyển ngẫu nhiên
            onPath = false;
            getRandomDirection(120);
        }
    }

    public void damageReaction() {
        actionLockCounter = 0;
        onPath = true; // Luôn kích hoạt chế độ đuổi theo khi bị đánh
        if(inRage && life < maxLife/4) { // Tăng tốc độ khi HP dưới 25% trong rage mode
            defaultSpeed++;
            speed = defaultSpeed;
        }
    }
    public void checkDrop()
    {
        gp.bossBattleOn = false;

        //Restore the previous music
        gp.stopMusic();
        gp.playMusic(19);

        // Remove the iron doors
        for(int i = 0; i < gp.obj[1].length; i++)
        {
            if(gp.obj[gp.currentMap][i] != null && gp.obj[gp.currentMap][i].name.equals(OBJ_Door_Iron.objName))
            {
                gp.playSE(21);
                gp.obj[gp.currentMap][i] = null;
            }
        }

        //CAST A DIE
        int i = new Random().nextInt(100)+1;

        //SET THE MONSTER DROP
        if(i < 50)
        {
            dropItem(new OBJ_Coin_Bronze(gp));
        }
        if(i >= 50 && i < 100)
        {
            dropItem(new OBJ_Heart(gp));
        }
    }
}
