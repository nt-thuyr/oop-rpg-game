package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Shield_Blue extends Entity {

    public static final String objName = "Khiên lam";

    public OBJ_Shield_Blue(GamePanel gp) {
        super(gp);

        setType(getType_shield());
        setName(objName);
        setDown1(setup("/objects/shield_blue", gp.getTileSize(), gp.getTileSize()));
        setDefenseValue(2);
        setDescription("[" + getName() + "]\nMột chiếc khiên xanh\nánh kim lấp lánh.");
        setPrice(150);
    }
}