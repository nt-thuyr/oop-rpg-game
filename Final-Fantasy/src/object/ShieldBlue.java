package object;

import main.GamePanel;

public class ShieldBlue extends Item {

    public static final String objName = "Khiên lam";

    public ShieldBlue(GamePanel gp) {
        super(gp);

        setType(getType_shield());
        setName(objName);
        setImage(setup("/objects/shield_blue", gp.getTileSize(), gp.getTileSize()));
        setDefenseValue(2);
        setDescription("[" + getName() + "]\nMột chiếc khiên xanh\nánh kim lấp lánh.");
        setPrice(150);
    }
}