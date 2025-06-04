package item;

import entity.Character;
import main.GamePanel;

import java.awt.image.BufferedImage;

public class Heart extends Item {

    private final GamePanel gp; // Encapsulated GamePanel
    public static final String objName = "Heart";
    private BufferedImage image2;
    private BufferedImage image3;

    public Heart(GamePanel gp) {
        super(gp);
        this.gp = gp;

        setType(getType_pickupOnly());
        setName(objName);
        setValue(2);
        setImage(setup("/objects/heart_full", gp.getTileSize(), gp.getTileSize())); // Entity's draw method will draw it.
        setImage2(setup("/objects/heart_half", gp.getTileSize(), gp.getTileSize()));
        setImage3(setup("/objects/heart_blank", gp.getTileSize(), gp.getTileSize()));
        setPrice(175);
    }

    public boolean use(Character character) {
        gp.playSE(2);
        gp.getUi().addMessage("Life +" + getValue());
        character.setLife(character.getLife() + getValue());
        return true;
    }


    public BufferedImage getImage2() {
        return image2;
    }

    public void setImage2(BufferedImage image2) {
        this.image2 = image2;
    }

    public BufferedImage getImage3() {
        return image3;
    }

    public void setImage3(BufferedImage image3) {
        this.image3 = image3;
    }
}