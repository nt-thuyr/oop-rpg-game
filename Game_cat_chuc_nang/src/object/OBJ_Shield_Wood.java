package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Shield_Wood extends Entity {

    public static final String objName = "Khiên gỗ";

    public OBJ_Shield_Wood(GamePanel gp) {
        super(gp);

        setType(getType_shield());
        setName(objName);
        setDown1(setup("/objects/shield_wood", gp.getTileSize(), gp.getTileSize()));
        setDefenseValue(1);
        setDescription("[" + getName() + "]\nLàm bằng... gỗ.");
        setPrice(30);
    }
}