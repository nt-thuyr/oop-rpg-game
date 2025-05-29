package object;

import entity.Character;
import main.GamePanel;

public class OBJ_Axe extends Character {

    public static final String objName = "Rìu đốn củi";

    public OBJ_Axe(GamePanel gp) {
        super(gp);

        setType(getType_axe());
        setName(objName);
        setDown1(setup("/objects/axe", gp.getTileSize(), gp.getTileSize()));
        setAttackValue(2);
        getAttackArea().width = 26;
        getAttackArea().height = 26;
        setDescription("[" + getName() + "]\nHơi cũ xí thôi\nvẫn chặt được vài nhát.");
        setPrice(75);
        setKnockBackPower(5);
        setMotion1_duration(20);
        setMotion2_duration(40);
    }
}