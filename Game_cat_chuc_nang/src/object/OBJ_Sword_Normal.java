package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Sword_Normal extends Entity {

    public static final String objName = "Kiếm";

    public OBJ_Sword_Normal(GamePanel gp) {
        super(gp);

        setType(getType_sword());
        setName(objName);
        setDown1(setup("/objects/sword_normal", gp.getTileSize(), gp.getTileSize()));
        setAttackValue(3);
        getAttackArea().width = 36;
        getAttackArea().height = 36;
        setDescription("[" + getName() + "]\nMột thanh kiếm cũ kĩ.");
        setPrice(30);
        setKnockBackPower(3);
        setMotion1_duration(5);
        setMotion2_duration(25);
    }
}