package monster;

import character.Character;
import main.GamePanel;
import item.CoinBronze;
import item.Heart;
import item.Saliva;

import java.util.Random;

public class RedSlime extends Character {



    public RedSlime(GamePanel gp) {
        super(gp);

        this.gp = gp;

        setType(getType_monster());
        setName("Slime đỏ");
        setDefaultSpeed(2);
        setSpeed(getDefaultSpeed());
        setMaxLife(8);
        setLife(getMaxLife());
        setAttack(1);
        setDefense(0);
        setExp(4);
        setProjectile(new Saliva(gp));

        getSolidArea().x = 3;
        getSolidArea().y = 18;
        getSolidArea().width = 42;
        getSolidArea().height = 30;
        setSolidAreaDefaultX(getSolidArea().x);
        setSolidAreaDefaultY(getSolidArea().y);

        getImage();
    }

    public void getImage() {
        setUp1(setup("/monster/redslime_down_1", gp.getTileSize(), gp.getTileSize()));
        setUp2(setup("/monster/redslime_down_2", gp.getTileSize(), gp.getTileSize()));
        setDown1(setup("/monster/redslime_down_1", gp.getTileSize(), gp.getTileSize()));
        setDown2(setup("/monster/redslime_down_2", gp.getTileSize(), gp.getTileSize()));
        setLeft1(setup("/monster/redslime_down_1", gp.getTileSize(), gp.getTileSize()));
        setLeft2(setup("/monster/redslime_down_2", gp.getTileSize(), gp.getTileSize()));
        setRight1(setup("/monster/redslime_down_1", gp.getTileSize(), gp.getTileSize()));
        setRight2(setup("/monster/redslime_down_2", gp.getTileSize(), gp.getTileSize()));
    }

    public void setAction() {
        if (getState().isOnPath()) {
            checkStopChasingOrNot(gp.getPlayer(), 15, 100);
            searchPath(getGoalCol(gp.getPlayer()), getGoalRow(gp.getPlayer()));
            checkShootOrNot(200, 30);
        } else {
            checkStartChasingOrNot(gp.getPlayer(), 5, 100);
            getRandomDirection(120);
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