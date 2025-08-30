package monster;

import character.Character;
import main.GamePanel;
import item.CoinBronze;
import item.Heart;

import java.util.Random;

public class Bat extends Character {


    public Bat(GamePanel gp) {
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
            dropItem(new CoinBronze(gp));
        }
        if (i >= 50 && i < 100) {
            dropItem(new Heart(gp));
        }
    }
}