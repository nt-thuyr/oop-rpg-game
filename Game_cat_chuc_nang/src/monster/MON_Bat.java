package monster;

import entity.Character;
import main.GamePanel;
import object.OBJ_Coin_Bronze;
import object.OBJ_Heart;

import java.util.Random;

public class MON_Bat extends Character {

    private GamePanel gp; // Encapsulated GamePanel

    public MON_Bat(GamePanel gp) {
        super(gp);

        this.gp = gp;

        setType(getType_monster());
        setName("Dơi");
        setDefaultSpeed(4);
        setSpeed(getDefaultSpeed());
        setMaxLife(7);
        setLife(getMaxLife());
        setAttack(4);
        setDefense(0);
        setExp(2);

        getSolidArea().x = 3;
        getSolidArea().y = 15;
        getSolidArea().width = 42;
        getSolidArea().height = 21;
        setSolidAreaDefaultX(getSolidArea().x);
        setSolidAreaDefaultY(getSolidArea().y);

        getImage();
    }

    public void getImage() {
        setUp1(setup("/monster/bat_down_1", gp.getTileSize(), gp.getTileSize()));
        setUp2(setup("/monster/bat_down_2", gp.getTileSize(), gp.getTileSize()));
        setDown1(setup("/monster/bat_down_1", gp.getTileSize(), gp.getTileSize()));
        setDown2(setup("/monster/bat_down_2", gp.getTileSize(), gp.getTileSize()));
        setLeft1(setup("/monster/bat_down_1", gp.getTileSize(), gp.getTileSize()));
        setLeft2(setup("/monster/bat_down_2", gp.getTileSize(), gp.getTileSize()));
        setRight1(setup("/monster/bat_down_1", gp.getTileSize(), gp.getTileSize()));
        setRight2(setup("/monster/bat_down_2", gp.getTileSize(), gp.getTileSize()));
    }

    public void setAction() {
        if (getState().isOnPath()) {
            // Logic remains commented as per the original code
        } else {
            getRandomDirection(10);
        }
    }

    public void damageReaction() {
        getState().setActionLockCounter(0);
    }

    public void checkDrop() {
        // CAST A DIE
        int i = new Random().nextInt(100) + 1;

        // SET THE MONSTER DROP
        if (i < 50) {
            dropItem(new OBJ_Coin_Bronze(gp));
        }
        if (i >= 50 && i < 100) {
            dropItem(new OBJ_Heart(gp));
        }
    }
}