package object;

import entity.Entity;
import main.GamePanel;

import java.awt.*;

public class OBJ_Coin_Bronze extends Entity {

    private GamePanel gp; // Encapsulated GamePanel
    public static final String objName = "Tiền đồng";

    public OBJ_Coin_Bronze(GamePanel gp) {
        super(gp);
        this.gp = gp;

        setType(getType_pickupOnly());
        setName(objName);
        setValue(30);
        setDown1(setup("/objects/coin_bronze", gp.getTileSize(), gp.getTileSize()));
        setPrice(25);
    }

    public boolean use(Entity entity) {
        gp.playSE(1);
        gp.getUi().addMessage("Coin +" + getValue());
        entity.setCoin(entity.getCoin() + getValue());
        return true;
    }
}