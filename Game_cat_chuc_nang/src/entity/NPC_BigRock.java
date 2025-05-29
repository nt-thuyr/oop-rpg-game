package entity;

import main.GamePanel;
import object.OBJ_Door_Iron;
import tile_interactive.IT_MetalPlate;
import tile_interactive.InteractiveTile;

import java.awt.*;
import java.util.ArrayList;

public class NPC_BigRock extends Entity {

    public static final String npcName = "Big Rock";

    public NPC_BigRock(GamePanel gp) {
        super(gp);

        setName(npcName);
        setDirection("down");
        setSpeed(4);

        setSolidArea(new Rectangle(2, 6, 44, 40));
        setSolidAreaDefaultX(2);
        setSolidAreaDefaultY(6);

        setDialogueSet(-1); // For first dialogueSet (= 0)

        getImage();
        setDialogue();
    }

    public void getImage() {
        setUp1(setup("/npc/bigrock", gp.getTileSize(), gp.getTileSize()));
        setUp2(setup("/npc/bigrock", gp.getTileSize(), gp.getTileSize()));
        setDown1(setup("/npc/bigrock", gp.getTileSize(), gp.getTileSize()));
        setDown2(setup("/npc/bigrock", gp.getTileSize(), gp.getTileSize()));
        setLeft1(setup("/npc/bigrock", gp.getTileSize(), gp.getTileSize()));
        setLeft2(setup("/npc/bigrock", gp.getTileSize(), gp.getTileSize()));
        setRight1(setup("/npc/bigrock", gp.getTileSize(), gp.getTileSize()));
        setRight2(setup("/npc/bigrock", gp.getTileSize(), gp.getTileSize()));
    }

    public void setDialogue() {
        getDialogues()[0][0] = "Một tảng đá khổng lồ!";
    }

    public void setAction() {
        // Define specific actions for the NPC if needed
    }

    public void update() {
        // Update logic for the NPC
    }

    public void speak() {
        facePlayer();
        startDialogue(this, getDialogueSet());

        setDialogueSet(getDialogueSet() + 1);
        if (getDialogues()[getDialogueSet()][0] == null) {
            setDialogueSet(getDialogueSet() - 1); // Displays last set
        }
    }

    public void move(String d) {
        setDirection(d);

        checkCollision();

        if (!getState().isCollisionOn()) {
            switch (getDirection()) {
                case "up":
                    setWorldY(getWorldY() - getSpeed());
                    break;
                case "down":
                    setWorldY(getWorldY() + getSpeed());
                    break;
                case "left":
                    setWorldX(getWorldX() - getSpeed());
                    break;
                case "right":
                    setWorldX(getWorldX() + getSpeed());
                    break;
            }
        }

        detectPlate();
    }

    public void detectPlate() {
        ArrayList<InteractiveTile> plateList = new ArrayList<>();
        ArrayList<Entity> rockList = new ArrayList<>();

        // Create a plate list
        for (int i = 0; i < gp.getiTile()[1].length; i++) {
            InteractiveTile tile = (InteractiveTile) gp.getiTile()[gp.getCurrentMap()][i];
            if (tile != null && tile.getName() != null && tile.getName().equals(IT_MetalPlate.itName)) {
                plateList.add(tile);
            }
        }

        // Create a rock list
        for (int i = 0; i < gp.getNpc()[1].length; i++) {
            Entity npc = gp.getNpc()[gp.getCurrentMap()][i];
            if (npc != null && npc.getName().equals(NPC_BigRock.npcName)) {
                rockList.add(npc);
            }
        }

        int count = 0;

        // Scan the plate list
        for (InteractiveTile plate : plateList) {
            int xDistance = Math.abs(getWorldX() - plate.getWorldX());
            int yDistance = Math.abs(getWorldY() - plate.getWorldY());
            int distance = Math.max(xDistance, yDistance);

            if (distance < 8) {
                if (getLinkedEntity() == null) {
                    setLinkedEntity(plate);
                    gp.playSE(3);
                }
            } else {
                if (getLinkedEntity() == plate) { // Checking if linked before, then you move the rock again somewhere else
                    setLinkedEntity(null);
                }
            }
        }

        // Scan the rock list
        for (Entity rock : rockList) {
            // Count the rocks on the plates
            if (rock.getLinkedEntity() != null) {
                count++;
            }
        }

        // If all the rocks are on the plates, the iron door opens
        if (count == rockList.size()) {
            for (int i = 0; i < gp.getObj()[1].length; i++) {
                if (gp.getObj()[gp.getCurrentMap()][i] != null &&
                        gp.getObj()[gp.getCurrentMap()][i].getName().equals(OBJ_Door_Iron.objName)) {
                    gp.getObj()[gp.getCurrentMap()][i] = null;
                    gp.playSE(21);
                }
            }
        }
    }
}