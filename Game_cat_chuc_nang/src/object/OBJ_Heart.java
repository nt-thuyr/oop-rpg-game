package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Heart extends Entity {

    private GamePanel gp; // Encapsulated GamePanel
    public static final String objName = "Heart";

    public OBJ_Heart(GamePanel gp) {
        super(gp);
        this.gp = gp;

        setType(getType_pickupOnly());
        setName(objName);
        setValue(2);
        setDown1(setup("/objects/heart_full", gp.getTileSize(), gp.getTileSize())); // Entity's draw method will draw it.
        setImage1(setup("/objects/heart_full", gp.getTileSize(), gp.getTileSize()));
        setImage2(setup("/objects/heart_half", gp.getTileSize(), gp.getTileSize()));
        setImage3(setup("/objects/heart_blank", gp.getTileSize(), gp.getTileSize()));
        setPrice(175);
    }

    public boolean use(Entity entity) {
        gp.playSE(2);
        gp.getUi().addMessage("Life +" + getValue());
        entity.setLife(entity.getLife() + getValue());
        return true;
    }


}