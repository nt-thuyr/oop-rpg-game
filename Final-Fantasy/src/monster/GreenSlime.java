package monster;

import character.Character;
import main.GamePanel;
import item.CoinBronze;
import item.Heart;

import java.util.Random;

public class GreenSlime extends Character {


    public GreenSlime(GamePanel gp) {
        super(gp);

        this.gp = gp;

        setType(getType_monster());
        setName("Slime xanh");
        setDefaultSpeed(1);
        setSpeed(getDefaultSpeed());
        setMaxLife(4);
        setLife(getMaxLife());
        setAttack(1);
        setDefense(0);
        setExp(2);

        getSolidArea().x = 3;
        getSolidArea().y = 18;
        getSolidArea().width = 42;
        getSolidArea().height = 30;
        setSolidAreaDefaultX(getSolidArea().x);
        setSolidAreaDefaultY(getSolidArea().y);

        getImage();
    }

    public void getImage() { // Đổi tên từ getImages => loadImages
        setUp1(setup("/monster/greenslime_down_1", gp.getTileSize(), gp.getTileSize()));
        setUp2(setup("/monster/greenslime_down_2", gp.getTileSize(), gp.getTileSize()));
        setDown1(setup("/monster/greenslime_down_1", gp.getTileSize(), gp.getTileSize()));
        setDown2(setup("/monster/greenslime_down_2", gp.getTileSize(), gp.getTileSize()));
        setLeft1(setup("/monster/greenslime_down_1", gp.getTileSize(), gp.getTileSize()));
        setLeft2(setup("/monster/greenslime_down_2", gp.getTileSize(), gp.getTileSize()));
        setRight1(setup("/monster/greenslime_down_1", gp.getTileSize(), gp.getTileSize()));
        setRight2(setup("/monster/greenslime_down_2", gp.getTileSize(), gp.getTileSize()));
    }

    public void setAction() {
        if (getState().isOnPath()) {
            checkStopChasingOrNot(gp.getPlayer(), 15, 100);
            searchPath(getGoalCol(gp.getPlayer()), getGoalRow(gp.getPlayer()));
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