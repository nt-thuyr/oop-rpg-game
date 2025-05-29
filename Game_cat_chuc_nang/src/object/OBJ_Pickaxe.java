package object;

import entity.Character;
import main.GamePanel;

import java.awt.*;

public class OBJ_Pickaxe extends Item {

    public static final String objName = "Cuốc chim";

    public OBJ_Pickaxe(GamePanel gp) {
        super(gp);

        setType(getType_pickaxe());
        setName(objName);
        setImage(setup("/objects/pickaxe", gp.getTileSize(), gp.getTileSize()));
        setAttackValue(1);
        setAttackArea(new Rectangle(26, 26));
        setDescription("[" + getName() + "]\nBạn sẽ đào được thứ gì đó!");
        setPrice(75);
        setKnockBackPower(1);
        setMotion1_duration(10);
        setMotion2_duration(20);
    }
}