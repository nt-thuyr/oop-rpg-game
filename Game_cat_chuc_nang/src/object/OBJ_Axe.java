package object;

import main.GamePanel;

import java.awt.*;

public class OBJ_Axe extends Item {

    public static final String objName = "Rìu đốn củi";

    public OBJ_Axe(GamePanel gp) {
        super(gp);

        setType(getType_axe());
        setName(objName);
        setImage(setup("/objects/axe", gp.getTileSize(), gp.getTileSize()));
        setAttackValue(2);
        setAttackArea(new Rectangle(26, 26));
        setDescription("[" + getName() + "]\nHơi cũ xí thôi\nvẫn chặt được vài nhát.");
        setPrice(75);
        setKnockBackPower(5);
        setMotion1_duration(20);
        setMotion2_duration(40);
    }
}
