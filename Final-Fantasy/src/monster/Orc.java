package monster;

import character.Character;
import main.GamePanel;
import item.CoinBronze;
import item.Heart;

import java.util.Random;

public class Orc extends Character {

    public Orc(GamePanel gp) {
        super(gp);

        this.gp = gp;

        setType(getType_monster());
        setName("Orc");
        setDefaultSpeed(1);
        setSpeed(getDefaultSpeed());
        setMaxLife(8);
        setLife(getMaxLife());
        setAttack(7);
        setDefense(2);
        setExp(8);
        setKnockBackPower(5);

        getSolidArea().x = 4;
        getSolidArea().y = 4;
        getSolidArea().width = 40;
        getSolidArea().height = 44;
        setSolidAreaDefaultX(getSolidArea().x);
        setSolidAreaDefaultY(getSolidArea().y);
        getAttackArea().width = 48;
        getAttackArea().height = 48;
        setMotion1_duration(40);
        setMotion2_duration(85);

        getImage();
        getAttackImage();
    }

    public void getImage() {
        setUp1(setup("/monster/orc_up_1", gp.getTileSize(), gp.getTileSize()));
        setUp2(setup("/monster/orc_up_2", gp.getTileSize(), gp.getTileSize()));
        setDown1(setup("/monster/orc_down_1", gp.getTileSize(), gp.getTileSize()));
        setDown2(setup("/monster/orc_down_2", gp.getTileSize(), gp.getTileSize()));
        setLeft1(setup("/monster/orc_left_1", gp.getTileSize(), gp.getTileSize()));
        setLeft2(setup("/monster/orc_left_2", gp.getTileSize(), gp.getTileSize()));
        setRight1(setup("/monster/orc_right_1", gp.getTileSize(), gp.getTileSize()));
        setRight2(setup("/monster/orc_right_2", gp.getTileSize(), gp.getTileSize()));
    }

    public void getAttackImage() {
        setAttackUp1(setup("/monster/orc_attack_up_1", gp.getTileSize(), gp.getTileSize() * 2));
        setAttackUp2(setup("/monster/orc_attack_up_2", gp.getTileSize(), gp.getTileSize() * 2));
        setAttackDown1(setup("/monster/orc_attack_down_1", gp.getTileSize(), gp.getTileSize() * 2));
        setAttackDown2(setup("/monster/orc_attack_down_2", gp.getTileSize(), gp.getTileSize() * 2));
        setAttackLeft1(setup("/monster/orc_attack_left_1", gp.getTileSize() * 2, gp.getTileSize()));
        setAttackLeft2(setup("/monster/orc_attack_left_2", gp.getTileSize() * 2, gp.getTileSize()));
        setAttackRight1(setup("/monster/orc_attack_right_1", gp.getTileSize() * 2, gp.getTileSize()));
        setAttackRight2(setup("/monster/orc_attack_right_2", gp.getTileSize() * 2, gp.getTileSize()));
    }

    public void setAction() {
        if (getState().isOnPath()) {
            checkStopChasingOrNot(gp.getPlayer(), 15, 100);
            searchPath(getGoalCol(gp.getPlayer()), getGoalRow(gp.getPlayer()));
        } else {
            checkStartChasingOrNot(gp.getPlayer(), 5, 100);
            getRandomDirection(120);
        }

        if (!getState().isAttacking()) {
            checkAttackOrNot(30, gp.getTileSize() * 4, gp.getTileSize());
        }
    }

    public void damageReaction() {
        getState().setActionLockCounter(0);
        getState().setOnPath(true); // gets aggro
    }

    public void checkDrop() {
        int i = new Random().nextInt(100) + 1;

        if (i < 50) {
            dropItem(new CoinBronze(gp));
        }
        if (i >= 50 && i < 100) {
            dropItem(new Heart(gp));
        }
    }
}