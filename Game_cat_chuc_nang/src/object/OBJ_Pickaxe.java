package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Pickaxe extends Entity {

    public static final String objName = "Cuốc chim";

    public OBJ_Pickaxe(GamePanel gp) {
        super(gp);

        setType(getType_pickaxe());
        setName(objName);
        setDown1(setup("/objects/pickaxe", gp.getTileSize(), gp.getTileSize()));
        setAttackValue(1);
        getAttackArea().width = 26;
        getAttackArea().height = 26;
        setDescription("[" + getName() + "]\nBạn sẽ đào được thứ gì đó!");
        setPrice(75);
        setKnockBackPower(1);
        setMotion1_duration(10);
        setMotion2_duration(20);
    }
}