package item;

import main.GamePanel;
import java.awt.*;
import java.awt.image.BufferedImage;

public class Chest extends Item {
    private Item loot;
    private final BufferedImage image2 = setup("/objects/chest_opened", gp.getTileSize(), gp.getTileSize());
    private boolean opened = false;

    public static final String objName = "Chest";

    public Chest(GamePanel gp)
    {
        super(gp);
        setType(getType_obstacle());
        setName(objName);
        setImage(setup("/objects/chest", gp.getTileSize(), gp.getTileSize()));
        setCollision(true);

        setSolidArea(new Rectangle(4, 16, 40, 32));
        setSolidAreaDefaultX(getSolidArea().x);
        setSolidAreaDefaultY(getSolidArea().y);

    }


    @Override
    public void interact() {
        if (!opened) {
            gp.playSE(3);
            gp.getPlayer().addToInventory(loot);
            setImage(image2);
            opened = true;
        }
    }
}
