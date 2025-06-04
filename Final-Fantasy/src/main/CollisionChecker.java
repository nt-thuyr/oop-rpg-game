package main;

import entity.Character;

public class CollisionChecker {
    GamePanel gp;

    public CollisionChecker(GamePanel gp) {
        this.gp = gp;
    }

    public void checkTile(Character character) {
        int entityLeftWorldX = character.getWorldX() + character.getSolidArea().x;
        int entityRightWorldX = character.getWorldX() + character.getSolidArea().x + character.getSolidArea().width;
        int entityTopWorldY = character.getWorldY() + character.getSolidArea().y;
        int entityBottomWorldY = character.getWorldY() + character.getSolidArea().y + character.getSolidArea().height;

        int entityLeftCol = entityLeftWorldX / gp.getTileSize();
        int entityRightCol = entityRightWorldX / gp.getTileSize();
        int entityTopRow = entityTopWorldY / gp.getTileSize();
        int entityBottomRow = entityBottomWorldY / gp.getTileSize();

        int tileNum1, tileNum2;

        String direction = character.getDirection();
        if (character.getState().isKnockBack()) {
            direction = character.getState().getKnockBackDirection();
        }

        switch (direction) {
            case "up":
                entityTopRow = (entityTopWorldY - character.getSpeed()) / gp.getTileSize();
                tileNum1 = gp.getTileM().getMapTileNum()[gp.getCurrentMap()][entityLeftCol][entityTopRow];
                tileNum2 = gp.getTileM().getMapTileNum()[gp.getCurrentMap()][entityRightCol][entityTopRow];
                if (gp.getTileM().getTile()[tileNum1].isCollision() || gp.getTileM().getTile()[tileNum2].isCollision()) {
                    character.getState().setCollisionOn(true);
                }
                break;

            case "down":
                entityBottomRow = (entityBottomWorldY + character.getSpeed()) / gp.getTileSize();
                tileNum1 = gp.getTileM().getMapTileNum()[gp.getCurrentMap()][entityLeftCol][entityBottomRow];
                tileNum2 = gp.getTileM().getMapTileNum()[gp.getCurrentMap()][entityRightCol][entityBottomRow];
                if (gp.getTileM().getTile()[tileNum1].isCollision() || gp.getTileM().getTile()[tileNum2].isCollision()) {
                    character.getState().setCollisionOn(true);
                }
                break;

            case "left":
                entityLeftCol = (entityLeftWorldX - character.getSpeed()) / gp.getTileSize();
                tileNum1 = gp.getTileM().getMapTileNum()[gp.getCurrentMap()][entityLeftCol][entityTopRow];
                tileNum2 = gp.getTileM().getMapTileNum()[gp.getCurrentMap()][entityLeftCol][entityBottomRow];
                if (gp.getTileM().getTile()[tileNum1].isCollision() || gp.getTileM().getTile()[tileNum2].isCollision()) {
                    character.getState().setCollisionOn(true);
                }
                break;

            case "right":
                entityRightCol = (entityRightWorldX + character.getSpeed()) / gp.getTileSize();
                tileNum1 = gp.getTileM().getMapTileNum()[gp.getCurrentMap()][entityRightCol][entityTopRow];
                tileNum2 = gp.getTileM().getMapTileNum()[gp.getCurrentMap()][entityRightCol][entityBottomRow];
                if (gp.getTileM().getTile()[tileNum1].isCollision() || gp.getTileM().getTile()[tileNum2].isCollision()) {
                    character.getState().setCollisionOn(true);
                }
                break;
        }
    }

    public int checkObject(Character character, boolean player) {
        int index = 999;

        String direction = character.getDirection();
        if (character.getState().isKnockBack()) {
            direction = character.getState().getKnockBackDirection();
        }

        for (int i = 0; i < gp.getObj()[1].length; i++) {
            if (gp.getObj()[gp.getCurrentMap()][i] != null) {
                character.getSolidArea().x = character.getWorldX() + character.getSolidArea().x;
                character.getSolidArea().y = character.getWorldY() + character.getSolidArea().y;

                gp.getObj()[gp.getCurrentMap()][i].getSolidArea().x =
                        gp.getObj()[gp.getCurrentMap()][i].getWorldX() + gp.getObj()[gp.getCurrentMap()][i].getSolidArea().x;
                gp.getObj()[gp.getCurrentMap()][i].getSolidArea().y =
                        gp.getObj()[gp.getCurrentMap()][i].getWorldY() + gp.getObj()[gp.getCurrentMap()][i].getSolidArea().y;

                switch (direction) {
                    case "up":
                        character.getSolidArea().y -= character.getSpeed();
                        break;
                    case "down":
                        character.getSolidArea().y += character.getSpeed();
                        break;
                    case "left":
                        character.getSolidArea().x -= character.getSpeed();
                        break;
                    case "right":
                        character.getSolidArea().x += character.getSpeed();
                        break;
                }
                if (character.getSolidArea().intersects(gp.getObj()[gp.getCurrentMap()][i].getSolidArea())) {
                    if (gp.getObj()[gp.getCurrentMap()][i].isCollision()) {
                        character.getState().setCollisionOn(true);
                    }
                    if (player) {
                        index = i;
                    }
                }
                character.getSolidArea().x = character.getSolidAreaDefaultX();
                character.getSolidArea().y = character.getSolidAreaDefaultY();

                gp.getObj()[gp.getCurrentMap()][i].getSolidArea().x = gp.getObj()[gp.getCurrentMap()][i].getSolidAreaDefaultX();
                gp.getObj()[gp.getCurrentMap()][i].getSolidArea().y = gp.getObj()[gp.getCurrentMap()][i].getSolidAreaDefaultY();
            }
        }
        return index;
    }

    public int checkEntity(Character character, Character[][] target) {
        int index = 999;

        String direction = character.getDirection();
        if (character.getState().isKnockBack()) {
            direction = character.getState().getKnockBackDirection();
        }

        for (int i = 0; i < target[1].length; i++) {
            if (target[gp.getCurrentMap()][i] != null) {
                character.getSolidArea().x = character.getWorldX() + character.getSolidArea().x;
                character.getSolidArea().y = character.getWorldY() + character.getSolidArea().y;

                target[gp.getCurrentMap()][i].getSolidArea().x =
                        target[gp.getCurrentMap()][i].getWorldX() + target[gp.getCurrentMap()][i].getSolidArea().x;
                target[gp.getCurrentMap()][i].getSolidArea().y =
                        target[gp.getCurrentMap()][i].getWorldY() + target[gp.getCurrentMap()][i].getSolidArea().y;

                switch (direction) {
                    case "up":
                        character.getSolidArea().y -= character.getSpeed();
                        break;
                    case "down":
                        character.getSolidArea().y += character.getSpeed();
                        break;
                    case "left":
                        character.getSolidArea().x -= character.getSpeed();
                        break;
                    case "right":
                        character.getSolidArea().x += character.getSpeed();
                        break;
                }

                if (character.getSolidArea().intersects(target[gp.getCurrentMap()][i].getSolidArea())) {
                    if (target[gp.getCurrentMap()][i] != character) {
                        character.getState().setCollisionOn(true);
                        index = i;
                    }
                }
                character.getSolidArea().x = character.getSolidAreaDefaultX();
                character.getSolidArea().y = character.getSolidAreaDefaultY();

                target[gp.getCurrentMap()][i].getSolidArea().x = target[gp.getCurrentMap()][i].getSolidAreaDefaultX();
                target[gp.getCurrentMap()][i].getSolidArea().y = target[gp.getCurrentMap()][i].getSolidAreaDefaultY();
            }
        }
        return index;
    }

    public boolean checkPlayer(Character character) {
        boolean contactPlayer = false;

        character.getSolidArea().x = character.getWorldX() + character.getSolidArea().x;
        character.getSolidArea().y = character.getWorldY() + character.getSolidArea().y;

        gp.getPlayer().getSolidArea().x = gp.getPlayer().getWorldX() + gp.getPlayer().getSolidArea().x;
        gp.getPlayer().getSolidArea().y = gp.getPlayer().getWorldY() + gp.getPlayer().getSolidArea().y;

        switch (character.getDirection()) {
            case "up":
                character.getSolidArea().y -= character.getSpeed();
                break;
            case "down":
                character.getSolidArea().y += character.getSpeed();
                break;
            case "left":
                character.getSolidArea().x -= character.getSpeed();
                break;
            case "right":
                character.getSolidArea().x += character.getSpeed();
                break;
        }
        if (character.getSolidArea().intersects(gp.getPlayer().getSolidArea())) {
            character.getState().setCollisionOn(true);
            contactPlayer = true;
        }
        character.getSolidArea().x = character.getSolidAreaDefaultX();
        character.getSolidArea().y = character.getSolidAreaDefaultY();

        gp.getPlayer().getSolidArea().x = gp.getPlayer().getSolidAreaDefaultX();
        gp.getPlayer().getSolidArea().y = gp.getPlayer().getSolidAreaDefaultY();

        return contactPlayer;
    }
}