package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Door_Iron extends Entity {

    private GamePanel gp; // Encapsulated GamePanel
    public static final String objName = "Iron Door";

    public OBJ_Door_Iron(GamePanel gp) {
        super(gp);
        this.gp = gp;

        setType(getType_obstacle());
        setName(objName);
        setDown1(setup("/objects/door_iron", gp.getTileSize(), gp.getTileSize()));
        setCollision(true);

        getSolidArea().x = 0;
        getSolidArea().y = 16;
        getSolidArea().width = 48;
        getSolidArea().height = 32;
        setSolidAreaDefaultX(getSolidArea().x);
        setSolidAreaDefaultY(getSolidArea().y);
        setPrice(35);
        setDialogue();
    }

    public void setDialogue() {
        getDialogues()[0][0] = "Nó sẽ không nhúc nhích.";
    }

    public void interact() {
        startDialogue(this, 0);
    }
}