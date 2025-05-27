package entity;

import main.GamePanel;

import java.awt.*;
import java.util.Random;

public class NPC_OldMan extends Entity {

    public NPC_OldMan(GamePanel gp) {
        super(gp);
        setDirection("down");
        setSpeed(1);

        setSolidArea(new Rectangle(8, 16, 32, 32));
        setSolidAreaDefaultX(8);
        setSolidAreaDefaultY(16);

        setDialogueSet(-1); // For first dialogueSet (= 0)

        getImage();
        setDialogue();
    }

    public void getImage() {
        setUp1(setup("/npc/oldman_up_1", gp.getTileSize(), gp.getTileSize()));
        setUp2(setup("/npc/oldman_up_2", gp.getTileSize(), gp.getTileSize()));
        setDown1(setup("/npc/oldman_down_1", gp.getTileSize(), gp.getTileSize()));
        setDown2(setup("/npc/oldman_down_2", gp.getTileSize(), gp.getTileSize()));
        setLeft1(setup("/npc/oldman_left_1", gp.getTileSize(), gp.getTileSize()));
        setLeft2(setup("/npc/oldman_left_2", gp.getTileSize(), gp.getTileSize()));
        setRight1(setup("/npc/oldman_right_1", gp.getTileSize(), gp.getTileSize()));
        setRight2(setup("/npc/oldman_right_2", gp.getTileSize(), gp.getTileSize()));
    }

    public void setDialogue() {
        String[][] dialogues = getDialogues();
        dialogues[0][0] = "Này nhóc!";
        dialogues[0][1] = "Nhóc đến đây để tìm kho báu hửm?";
        dialogues[0][2] = "Ta đây từng là một đại pháp sư...\nNhưng giờ già cả rồi...";
        dialogues[0][4] = "Thôi, chúc nhóc may mắn ^^";
        dialogues[0][3] = "Có việc gì thì cứ đến hỏi ta.";

        dialogues[1][0] = "Mệt thì nghỉ bên bờ nước đi nhóc.";
        dialogues[1][1] = "Nhưng lũ quái sẽ hồi sinh nếu nhóc nghỉ ngơi.\nTa cũng chẳng hiểu tại sao, nhưng luật nó thế đấy.";
        dialogues[1][2] = "Dù sao thì... đừng cố quá, nhóc ạ.";

        dialogues[2][0] = "Làm sao để mở cánh cửa đó nhỉ...";
    }

    public void setAction() {
        if (isOnPath()) {
            int goalCol = (gp.getPlayer().getWorldX() + gp.getPlayer().getSolidArea().x) / gp.getTileSize();
            int goalRow = (gp.getPlayer().getWorldY() + gp.getPlayer().getSolidArea().y) / gp.getTileSize();
            searchPath(goalCol, goalRow);
        } else {
            setActionLockCounter(getActionLockCounter() + 1);

            if (getActionLockCounter() == 120) {
                Random random = new Random();
                int i = random.nextInt(100) + 1; // pick up a number from 1 to 100
                if (i <= 25) {
                    setDirection("up");
                } else if (i > 25 && i <= 50) {
                    setDirection("down");
                } else if (i > 50 && i <= 75) {
                    setDirection("left");
                } else if (i > 75 && i <= 100) {
                    setDirection("right");
                }
                setActionLockCounter(0); // reset
            }
        }
    }

    public void speak() {
        facePlayer();
        startDialogue(this, getDialogueSet());

        setDialogueSet(getDialogueSet() + 1);
        if (getDialogues()[getDialogueSet()][0] == null) {
            setDialogueSet(getDialogueSet() - 1); // Displays last set
        }
    }
}