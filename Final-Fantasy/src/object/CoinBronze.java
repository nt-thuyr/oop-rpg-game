package object;

import entity.Character;
import main.GamePanel;

public class CoinBronze extends Item {

    private GamePanel gp; // Encapsulated GamePanel
    public static final String objName = "Tiền đồng";

    public CoinBronze(GamePanel gp) {
        super(gp);
        this.gp = gp;

        setType(getType_pickupOnly());
        setName(objName);
        setValue(30);
        setImage(setup("/objects/coin_bronze", gp.getTileSize(), gp.getTileSize()));
        setPrice(25);
    }

    public boolean use(Character entity) {
        gp.playSE(1);
        gp.getUi().addMessage("Coin +" + getValue());
        entity.setCoin(entity.getCoin() + getValue());
        return true;
    }
}