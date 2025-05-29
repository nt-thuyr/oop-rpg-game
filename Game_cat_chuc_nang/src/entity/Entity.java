package entity;

import main.GamePanel;

public abstract class Entity {
    GamePanel gp;
    private String[][] dialogues = new String[20][20];
    private int dialogueSet = 0;
    private int dialogueIndex = 0;

    public Entity(GamePanel gp) {
        this.gp = gp;
    }

    public int getDialogueIndex() {
        return dialogueIndex;
    }

    public void setDialogueIndex(int dialogueIndex) {
        this.dialogueIndex = dialogueIndex;
    }

    public String[][] getDialogues() {
        return dialogues;
    }

    public void setDialogues(String[][] dialogues) {
        this.dialogues = dialogues;
    }

    public int getDialogueSet() {
        return dialogueSet;
    }

    public void setDialogueSet(int dialogueSet) {
        this.dialogueSet = dialogueSet;
    }
}
