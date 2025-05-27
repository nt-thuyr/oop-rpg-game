package object;

import entity.Entity;
import main.GamePanel;

import java.awt.*;

public class OBJ_Key extends Entity {

    private GamePanel gp; // Encapsulated GamePanel
    public static final String objName = "Chìa khoá";

    public OBJ_Key(GamePanel gp) {
        super(gp);
        this.gp = gp;

        setType(getType_consumable());
        setName(objName);
        setDown1(setup("/objects/key", gp.getTileSize(), gp.getTileSize()));
        setDescription("[" + getName() + "]\nDùng để mở cửa.");
        setPrice(350);
        setStackable(true);

        setDialogue();
    }

    public void setDialogue() {
        getDialogues()[0][0] = "Bạn đã dùng " + getName() + " để mở cánh cửa này.";
        getDialogues()[1][0] = "Làm cái gì zậy?";
    }

    public boolean use(Entity entity) {
        int objIndex = getDetected(entity, gp.getObj(), "Door"); // user, target, name
        if (objIndex != 999) {
            startDialogue(this, 0);
            gp.playSE(3);
            gp.getObj()[gp.getCurrentMap()][objIndex] = null;
            return true;
        } else {
            startDialogue(this, 1);
            return false;
        }
    }
}