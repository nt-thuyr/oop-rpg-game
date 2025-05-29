package object;

import entity.Character;
import main.GamePanel;

import java.awt.*;

public class OBJ_Door extends Item {

    private GamePanel gp; // Encapsulated GamePanel
    public static final String objName = "Door";

    public OBJ_Door(GamePanel gp) {
        super(gp);
        this.gp = gp;

        setType(getType_obstacle());
        setName(objName);
        setImage(setup("/objects/door", gp.getTileSize(), gp.getTileSize()));
        setCollision(true);

        setSolidArea(new Rectangle(0,16,48, 32));
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