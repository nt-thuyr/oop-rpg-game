package item;

import main.GamePanel;

import java.awt.*;

public class DemonSlayer extends Item {

    public static final String objName = "Demon Slayer";

    public DemonSlayer(GamePanel gp) {
        super(gp);

        setType(getType_sword());
        setName(objName);
        setImage(setup("/objects/demon_slayer", gp.getTileSize(), gp.getTileSize()));
        setAttackValue(6);
        setAttackArea(new Rectangle(50, 50));
        setDescription("[" + getName() + "]\nA demon slayer\nto battle the boss.");
        setPrice(500);
        setKnockBackPower(6);
        setMotion1_duration(5);
        setMotion2_duration(25);
    }
}
