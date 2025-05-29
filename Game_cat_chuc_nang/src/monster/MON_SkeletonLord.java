package monster;

import entity.Character;
import entity.Entity;
import main.GamePanel;
import object.OBJ_Coin_Bronze;
import object.OBJ_Door_Iron;
import object.OBJ_Heart;

import java.awt.*;
import java.util.Random;

public class MON_SkeletonLord extends Character {
    private GamePanel gp; // Encapsulated GamePanel
    public static final String monName = "Hài cốt vương";
    public MON_SkeletonLord(GamePanel gp) {
        super(gp);

        this.gp = gp;

        setType(getType_monster());
        setBoss(true);
        setName(monName);
        setDefaultSpeed(1);
        setSpeed(getDefaultSpeed());
        setMaxLife(40);
        setLife(getMaxLife());
        setAttack(16);
        setDefense(3);
        setExp(40);
        setKnockBackPower(5);

        getState().setOnPath(false);

        int size = gp.getTileSize() * 5;
        int collisionSize = (int)(size * 0.8);

        setSolidArea(new Rectangle((size - collisionSize)/2, (size - collisionSize)/2, collisionSize, collisionSize));
        setSolidAreaDefaultX(getSolidArea().x);
        setSolidAreaDefaultY(getSolidArea().y);

        setAttackArea(new Rectangle(170, 170));
        setMotion1_duration(25);
        setMotion2_duration(50);

        getImage();
        getAttackImage();
        setDialogue();
    }

    public void getImage() {
        int i = 5;
        if (!getState().isInRange()) {
            setUp1(setup("/monster/skeletonlord_up_1", gp.getTileSize() * i, gp.getTileSize() * i));
            setUp2(setup("/monster/skeletonlord_up_2", gp.getTileSize() * i, gp.getTileSize() * i));
            setDown1(setup("/monster/skeletonlord_down_1", gp.getTileSize() * i, gp.getTileSize() * i));
            setDown2(setup("/monster/skeletonlord_down_2", gp.getTileSize() * i, gp.getTileSize() * i));
            setLeft1(setup("/monster/skeletonlord_left_1", gp.getTileSize() * i, gp.getTileSize() * i));
            setLeft2(setup("/monster/skeletonlord_left_2", gp.getTileSize() * i, gp.getTileSize() * i));
            setRight1(setup("/monster/skeletonlord_right_1", gp.getTileSize() * i, gp.getTileSize() * i));
            setRight2(setup("/monster/skeletonlord_right_2", gp.getTileSize() * i, gp.getTileSize() * i));
        } else {
            setUp1(setup("/monster/skeletonlord_phase2_up_1", gp.getTileSize() * i, gp.getTileSize() * i));
            setUp2(setup("/monster/skeletonlord_phase2_up_2", gp.getTileSize() * i, gp.getTileSize() * i));
            setDown1(setup("/monster/skeletonlord_phase2_down_1", gp.getTileSize() * i, gp.getTileSize() * i));
            setDown2(setup("/monster/skeletonlord_phase2_down_2", gp.getTileSize() * i, gp.getTileSize() * i));
            setLeft1(setup("/monster/skeletonlord_phase2_left_1", gp.getTileSize() * i, gp.getTileSize() * i));
            setLeft2(setup("/monster/skeletonlord_phase2_left_2", gp.getTileSize() * i, gp.getTileSize() * i));
            setRight1(setup("/monster/skeletonlord_phase2_right_1", gp.getTileSize() * i, gp.getTileSize() * i));
            setRight2(setup("/monster/skeletonlord_phase2_right_2", gp.getTileSize() * i, gp.getTileSize() * i));
        }
    }

    public void getAttackImage() {
        int i = 5;
        if (!getState().isInRange()) {
            setAttackUp1(setup("/monster/skeletonlord_attack_up_1", gp.getTileSize() * i, gp.getTileSize() * 2 * i));
            setAttackUp2(setup("/monster/skeletonlord_attack_up_2", gp.getTileSize() * i, gp.getTileSize() * 2 * i));
            setAttackDown1(setup("/monster/skeletonlord_attack_down_1", gp.getTileSize() * i, gp.getTileSize() * 2 * i));
            setAttackDown2(setup("/monster/skeletonlord_attack_down_2", gp.getTileSize() * i, gp.getTileSize() * 2 * i));
            setAttackLeft1(setup("/monster/skeletonlord_attack_left_1", gp.getTileSize() * 2 * i, gp.getTileSize() * i));
            setAttackLeft2(setup("/monster/skeletonlord_attack_left_2", gp.getTileSize() * 2 * i, gp.getTileSize() * i));
            setAttackRight1(setup("/monster/skeletonlord_attack_right_1", gp.getTileSize() * 2 * i, gp.getTileSize() * i));
            setAttackRight2(setup("/monster/skeletonlord_attack_right_2", gp.getTileSize() * 2 * i, gp.getTileSize() * i));
        } else {
            setAttackUp1(setup("/monster/skeletonlord_phase2_attack_up_1", gp.getTileSize() * i, gp.getTileSize() * 2 * i));
            setAttackUp2(setup("/monster/skeletonlord_phase2_attack_up_2", gp.getTileSize() * i, gp.getTileSize() * 2 * i));
            setAttackDown1(setup("/monster/skeletonlord_phase2_attack_down_1", gp.getTileSize() * i, gp.getTileSize() * 2 * i));
            setAttackDown2(setup("/monster/skeletonlord_phase2_attack_down_2", gp.getTileSize() * i, gp.getTileSize() * 2 * i));
            setAttackLeft1(setup("/monster/skeletonlord_phase2_attack_left_1", gp.getTileSize() * 2 * i, gp.getTileSize() * i));
            setAttackLeft2(setup("/monster/skeletonlord_phase2_attack_left_2", gp.getTileSize() * 2 * i, gp.getTileSize() * i));
            setAttackRight1(setup("/monster/skeletonlord_phase2_attack_right_1", gp.getTileSize() * 2 * i, gp.getTileSize() * i));
            setAttackRight2(setup("/monster/skeletonlord_phase2_attack_right_2", gp.getTileSize() * 2 * i, gp.getTileSize() * i));
        }
    }

    public void setDialogue() {
        dialogues[0][0] = "Không ai có thể đánh cắp kho báu của ta!";
        dialogues[0][1] = "Mi sẽ bỏ mạng thôi!";
        dialogues[0][2] = "HÃY ĐÓN NHẬN CÁI CHẾT CỦA NGƯƠI ĐI!";
    }
    public void setAction() {
        if (!getState().isInRange() && getLife() < getMaxLife() / 2) {
            getState().setInRage(true);
            getImage();
            getAttackImage();
            setDefaultSpeed(getDefaultSpeed() + 1);
            setSpeed(getDefaultSpeed());
            setAttack(getAttack() * 2);
        }

        // Kiểm tra khoảng cách đến player
        int xDistance = Math.abs(getWorldX() - gp.getPlayer().getWorldX());
        int yDistance = Math.abs(getWorldY() - gp.getPlayer().getWorldY());
        int tileDistance = (xDistance + yDistance)/ gp.getTileSize();

        if(tileDistance < 8) {
            // Khi player trong tầm 8 tiles, bật chế độ đuổi theo
            getState().setOnPath(true);

            // Tìm đường đi đến player
            int goalCol = getGoalCol(gp.getPlayer());
            int goalRow = getGoalRow(gp.getPlayer());
            searchPath(goalCol, goalRow);

            // Tấn công khi ở gần
            if(!getState().isAttacking()) {
                int attackRate = getState().isInRange() ? 30 : 60;
                checkAttackOrNot(attackRate, gp.getTileSize() *4, gp.getTileSize() *4);
            }
        } else {
            // Ngoài tầm 8 tiles thì di chuyển ngẫu nhiên
            getState().setOnPath(false);
            getRandomDirection(120);
        }
    }

    public void damageReaction() {
        getState().setActionLockCounter(0);
        getState().setOnPath(false); // Luôn kích hoạt chế độ đuổi theo khi bị đánh
        if(getState().isInRange() && getLife() < getMaxLife()/4) { // Tăng tốc độ khi HP dưới 25% trong rage mode
            setDefaultSpeed(getDefaultSpeed() + 1);
            setSpeed(getDefaultSpeed());
        }
    }
    public void checkDrop() {
        // Reset boss battle status
        gp.setBossBattleOn(false);
        gp.stopMusic();

        // Remove the iron doors
        for (int i = 0; i < gp.getObj()[1].length; i++) {
            if (gp.getObj()[gp.getCurrentMap()][i] != null && gp.getObj()[gp.getCurrentMap()][i].getName().equals(OBJ_Door_Iron.objName)) {
                gp.playSE(21);
                gp.getObj()[gp.getCurrentMap()][i] = null;
            }
        }

        // Drop items
        int i = new Random().nextInt(100)+1;
        if(i < 50) {
            dropItem(new OBJ_Coin_Bronze(gp));
        }
        if(i >= 50 && i < 100) {
            dropItem(new OBJ_Heart(gp));
        }

        // End game when boss is defeated
        gp.setGameState(gp.getEndGameState());
    }

    public void setDying() {
        getState().setDying(true);
        gp.playSE(4);
    }
}