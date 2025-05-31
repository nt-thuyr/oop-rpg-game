package entity;

import main.GamePanel;
import item.*;

import java.awt.*;

public class Merchant extends Character {
    public Merchant(GamePanel gp) {
        super(gp);
        setDirection("down");
        setSpeed(1);

        getImage();
        setDialogue();
        setItems();

        setSolidArea(new Rectangle(8, 16, 32, 32));
        setSolidAreaDefaultX(8);
        setSolidAreaDefaultY(16);
    }

    public void getImage() {
        setUp1(setup("/npc/merchant_down_1", gp.getTileSize(), gp.getTileSize()));
        setUp2(setup("/npc/merchant_down_2", gp.getTileSize(), gp.getTileSize()));
        setDown1(setup("/npc/merchant_down_1", gp.getTileSize(), gp.getTileSize()));
        setDown2(setup("/npc/merchant_down_2", gp.getTileSize(), gp.getTileSize()));
        setLeft1(setup("/npc/merchant_down_1", gp.getTileSize(), gp.getTileSize()));
        setLeft2(setup("/npc/merchant_down_2", gp.getTileSize(), gp.getTileSize()));
        setRight1(setup("/npc/merchant_down_1", gp.getTileSize(), gp.getTileSize()));
        setRight2(setup("/npc/merchant_down_2", gp.getTileSize(), gp.getTileSize()));
    }

    public void setDialogue() {
        String[][] dialogues = getDialogues();
        dialogues[0][0] = "Hê hê, cuối cùng cũng gặp nhau!\nĐồ xịn xò đây.\nTrao đổi không cưng?";
        dialogues[1][0] = "Lần sau lại ghé nhe, hi hi hi!";
        dialogues[2][0] = "Thêm mấy đồng nữa ta mới bán nhe!";
        dialogues[3][0] = "Đầy quá rồi, không chứa thêm được đâu!";
        dialogues[4][0] = "Đồ đang dùng sao mà bán hả cưng!?";
        dialogues[5][0] = "He he cưng cũng biết chọn phết đấy!";
        dialogues[6][0] = "Hề hề cưng có đồ ngon quá nhể!";
    }

    public void setItems() {
        getInventory().add(new PotionRed(gp));
        getInventory().add(new ShieldBlue(gp));
    }

    public void speak() {
        facePlayer();
        startDialogue(this, 0);
        gp.setGameState(gp.getTradeState());
        gp.getUi().setNpc(this);
    }
}