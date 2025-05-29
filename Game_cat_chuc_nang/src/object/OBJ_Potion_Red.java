package object;

import entity.Character;
import main.GamePanel;

public class OBJ_Potion_Red extends Item {

    private GamePanel gp; // Encapsulated GamePanel
    public static final String objName = "Huyết dược";

    public OBJ_Potion_Red(GamePanel gp) {
        super(gp);

        this.gp = gp;

        setType(getType_consumable());
        setName(objName);
        setValue(5);
        setImage(setup("/objects/potion_red", gp.getTileSize(), gp.getTileSize()));
        setDescription("[" + getName() + "]\nHồi phục " + getValue() + " máu.");
        setPrice(50);
        setStackable(true);

        setDialogue();
    }

    public void setDialogue() {
        dialogues[0][0] = "Bạn đã uống " + getName() + "!\n" + "Bạn sẽ được hồi phục " + getValue() + " máu.";
    }

    public boolean use(Character character) {
        startDialogue(this, 0);
        character.setLife(character.getLife() + getValue());
        gp.playSE(2);
        return true;
    }
}