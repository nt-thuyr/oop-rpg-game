package monster;

import entity.Character;
import main.GamePanel;
import object.OBJ_Coin_Bronze;
import object.OBJ_Door_Iron;
import object.OBJ_Heart;

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
        getState().setSleep(true);

        int size = gp.getTileSize() * 5;
        getSolidArea().x = 48;
        getSolidArea().y = 48;
        getSolidArea().width = size - 48 * 2;
        getSolidArea().height = size - 48;
        setSolidAreaDefaultX(getSolidArea().x);
        setSolidAreaDefaultY(getSolidArea().y);
        getAttackArea().width = 170;
        getAttackArea().height = 170;
        setMotion1_duration(25);
        setMotion2_duration(50);

        getImage();
        getAttackImage();
        setDialogue();
    }

    public void getImage() {
        int i = 5;
        if (!getState().isInRage()) {
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
        if (!getState().isInRage()) {
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
        getDialogues()[0][0] = "Không ai có thể đánh cắp kho báu của ta!";
        getDialogues()[0][1] = "Mi sẽ bỏ mạng thôi!";
        getDialogues()[0][2] = "HÃY ĐÓN NHẬN CÁI CHẾT CỦA NGƯƠI ĐI!";
    }

    public void setAction() {
        if (!getState().isInRage() && getLife() < getMaxLife() / 2) {
            getState().setInRage(true);
            getImage();
            getAttackImage();
            setDefaultSpeed(getDefaultSpeed() + 1);
            setSpeed(getDefaultSpeed());
            setAttack(getAttack() * 2);
        }
        if (getTileDistance(gp.getPlayer()) < 10) {
            moveTowardPlayer(60);
        } else {
            getRandomDirection(120);
        }

        if (!getState().isAttacking()) {
            checkAttackOrNot(60, gp.getTileSize() * 7, gp.getTileSize() * 5);
        }
    }

    public void damageReaction() {
        getState().setActionLockCounter(0);
    }

    public void checkDrop() {
        gp.setBossBattleOn(false);

        gp.stopMusic();
        gp.playMusic(19);

        for (int i = 0; i < gp.getObj()[1].length; i++) {
            if (gp.getObj()[gp.getCurrentMap()][i] != null && gp.getObj()[gp.getCurrentMap()][i].getName().equals(OBJ_Door_Iron.objName)) {
                gp.playSE(21);
                gp.getObj()[gp.getCurrentMap()][i] = null;
            }
        }

        int i = new Random().nextInt(100) + 1;

        if (i < 50) {
            dropItem(new OBJ_Coin_Bronze(gp));
        }
        if (i >= 50 && i < 100) {
            dropItem(new OBJ_Heart(gp));
        }
    }
}