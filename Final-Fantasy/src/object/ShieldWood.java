package object;

import main.GamePanel;

public class ShieldWood extends Item {

    public static final String objName = "Khiên gỗ";

    public ShieldWood(GamePanel gp) {
        super(gp);

        setType(getType_shield());
        setName(objName);
        setImage(setup("/objects/shield_wood", gp.getTileSize(), gp.getTileSize()));
        setDefenseValue(1);
        setDescription("[" + getName() + "]\nLàm bằng... gỗ.");
        setPrice(30);
    }
}