package monster;

import entity.Character;
import main.GamePanel;
import object.OBJ_Coin_Bronze;
import object.OBJ_Heart;
import object.OBJ_Rock;

import java.util.Random;

public class MON_RedSlime extends Character {

    private GamePanel gp; // Encapsulated GamePanel

    public MON_RedSlime(GamePanel gp) {
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
        setProjectile(new OBJ_Rock(gp));

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
            dropItem(new OBJ_Coin_Bronze(gp));
        }
        if (i >= 50 && i < 100) {
            dropItem(new OBJ_Heart(gp));
        }
    }
}