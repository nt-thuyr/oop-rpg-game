package item;

import main.GamePanel;

import java.awt.*;

public class Door extends Item {

    public static final String objName = "Door";

    public Door(GamePanel gp) {
        super(gp);
        this.gp = gp;

        setType(getType_obstacle());
        setName(objName);
        setImage(setup("/objects/door", gp.getTileSize(), gp.getTileSize()));
        setCollision(true);

        getSolidArea().x = 0;
        getSolidArea().y = 16;
        setSolidArea(new Rectangle(48, 32));
        setSolidAreaDefaultX(getSolidArea().x);
        setSolidAreaDefaultY(getSolidArea().y);
        setPrice(35);

        setDialogue();
    }

    public void setDialogue() {
        getDialogues()[0][0] = "Cần chìa khoá để mở cửa.";
    }

    public void interact() {
        startDialogue(this, 0);
    }
}