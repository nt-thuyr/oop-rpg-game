package object;

import entity.Character;
import main.GamePanel;
import java.awt.*;
import java.awt.image.BufferedImage;

public class OBJ_Chest extends Item {
    private Item loot;
    private BufferedImage image2 = setup("/objects/chest_opened", gp.getTileSize(), gp.getTileSize());
    private boolean opened = false;

    public static final String objName = "Chest";

    public OBJ_Chest(GamePanel gp)
    {
        super(gp);
        setType(getType_obstacle());
        setName(objName);
        setImage(setup("/objects/chest", gp.getTileSize(), gp.getTileSize()));
        setCollision(true);

        setSolidArea(new Rectangle(4, 16, 40, 32));
        setSolidAreaDefaultX(getSolidArea().x);
        setSolidAreaDefaultY(getSolidArea().y);

        setDialogue();
    }

    public void setDialogue()
    {
        dialogues[0][0] = "Bạn mở rương kho báu và tìm thấy " + (loot != null ? loot.getName() : "???") + "!\n...Nhưng không thể mang thêm nữa!";
        dialogues[1][0] = "Bạn mở rương kho báu và tìm thấy " + (loot != null ? loot.getName() : "???") + "!\nBạn đã sở hữu " + (loot != null ? loot.getName() : "???") + "!";
        dialogues[2][0] = "Trống trơn hà...";
    }


    @Override
    public void interact() {
        if (!opened) {
            gp.playSE(3);
            startDialogue(this, 1);
            gp.getPlayer().addToInventory(loot);
            setImage(image2);
            opened = true;
        } else {
            startDialogue(this, 2);
        }
    }

    public void setLoot(Item loot) {
        this.loot = loot;
        setDialogue();
    }
}
