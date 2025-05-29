package object;

import entity.Character;
import main.GamePanel;

public class OBJ_Demon_Slayer extends Character {

    public static final String objName = "Demon Slayer";

    public OBJ_Demon_Slayer(GamePanel gp) {
        super(gp);

        type = type_sword;
        name = objName;
        down1 = setup("/objects/demon_slayer",gp.tileSize, gp.tileSize);
        attackValue = 6;
        attackArea.width = 50;
        attackArea.height= 50;
        description = "[" + name + "]\nA demon slayer to battle the boss.";
        price = 100;
        knockBackPower = 6;
        motion1_duration = 5;
        motion2_duration = 25;
    }
}
