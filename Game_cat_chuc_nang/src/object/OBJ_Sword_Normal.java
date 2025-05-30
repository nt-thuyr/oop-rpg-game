package object;

import main.GamePanel;

import java.awt.*;

public class OBJ_Sword_Normal extends Item {

    public static final String objName = "Kiếm";

    public OBJ_Sword_Normal(GamePanel gp) {
        super(gp);

        setType(getType_sword());
        setName(objName);
        setImage(setup("/objects/sword_normal", gp.getTileSize(), gp.getTileSize()));
        setAttackValue(3);
        setAttackArea(new Rectangle(36, 36));
        setDescription("[" + getName() + "]\nMột thanh kiếm cũ kĩ.");
        setPrice(30);
        setKnockBackPower(3);
        setMotion1_duration(5);
        setMotion2_duration(25);
    }
}