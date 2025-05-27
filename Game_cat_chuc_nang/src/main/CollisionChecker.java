package main;

import entity.Entity;

public class CollisionChecker {
    GamePanel gp;

    public CollisionChecker(GamePanel gp) {
        this.gp = gp;
    }

    public void checkTile(Entity entity) {
        int entityLeftWorldX = entity.getWorldX() + entity.getSolidArea().x;
        int entityRightWorldX = entity.getWorldX() + entity.getSolidArea().x + entity.getSolidArea().width;
        int entityTopWorldY = entity.getWorldY() + entity.getSolidArea().y;
        int entityBottomWorldY = entity.getWorldY() + entity.getSolidArea().y + entity.getSolidArea().height;

        int entityLeftCol = entityLeftWorldX / gp.getTileSize();
        int entityRightCol = entityRightWorldX / gp.getTileSize();
        int entityTopRow = entityTopWorldY / gp.getTileSize();
        int entityBottomRow = entityBottomWorldY / gp.getTileSize();

        int tileNum1, tileNum2;

        String direction = entity.getDirection();
        if (entity.isKnockBack()) {
            direction = entity.getKnockBackDirection();
        }

        switch (direction) {
            case "up":
                entityTopRow = (entityTopWorldY - entity.getSpeed()) / gp.getTileSize();
                tileNum1 = gp.getTileM().getMapTileNum()[gp.getCurrentMap()][entityLeftCol][entityTopRow];
                tileNum2 = gp.getTileM().getMapTileNum()[gp.getCurrentMap()][entityRightCol][entityTopRow];
                if (gp.getTileM().getTile()[tileNum1].isCollision() || gp.getTileM().getTile()[tileNum2].isCollision()) {
                    entity.setCollisionOn(true);
                }
                break;

            case "down":
                entityBottomRow = (entityBottomWorldY + entity.getSpeed()) / gp.getTileSize();
                tileNum1 = gp.getTileM().getMapTileNum()[gp.getCurrentMap()][entityLeftCol][entityBottomRow];
                tileNum2 = gp.getTileM().getMapTileNum()[gp.getCurrentMap()][entityRightCol][entityBottomRow];
                if (gp.getTileM().getTile()[tileNum1].isCollision() || gp.getTileM().getTile()[tileNum2].isCollision()) {
                    entity.setCollisionOn(true);
                }
                break;

            case "left":
                entityLeftCol = (entityLeftWorldX - entity.getSpeed()) / gp.getTileSize();
                tileNum1 = gp.getTileM().getMapTileNum()[gp.getCurrentMap()][entityLeftCol][entityTopRow];
                tileNum2 = gp.getTileM().getMapTileNum()[gp.getCurrentMap()][entityLeftCol][entityBottomRow];
                if (gp.getTileM().getTile()[tileNum1].isCollision() || gp.getTileM().getTile()[tileNum2].isCollision()) {
                    entity.setCollisionOn(true);
                }
                break;

            case "right":
                entityRightCol = (entityRightWorldX + entity.getSpeed()) / gp.getTileSize();
                tileNum1 = gp.getTileM().getMapTileNum()[gp.getCurrentMap()][entityRightCol][entityTopRow];
                tileNum2 = gp.getTileM().getMapTileNum()[gp.getCurrentMap()][entityRightCol][entityBottomRow];
                if (gp.getTileM().getTile()[tileNum1].isCollision() || gp.getTileM().getTile()[tileNum2].isCollision()) {
                    entity.setCollisionOn(true);
                }
                break;
        }
    }

    public int checkObject(Entity entity, boolean player) {
        int index = 999;

        String direction = entity.getDirection();
        if (entity.isKnockBack()) {
            direction = entity.getKnockBackDirection();
        }

        for (int i = 0; i < gp.getObj()[1].length; i++) {
            if (gp.getObj()[gp.getCurrentMap()][i] != null) {
                entity.getSolidArea().x = entity.getWorldX() + entity.getSolidArea().x;
                entity.getSolidArea().y = entity.getWorldY() + entity.getSolidArea().y;

                gp.getObj()[gp.getCurrentMap()][i].getSolidArea().x =
                        gp.getObj()[gp.getCurrentMap()][i].getWorldX() + gp.getObj()[gp.getCurrentMap()][i].getSolidArea().x;
                gp.getObj()[gp.getCurrentMap()][i].getSolidArea().y =
                        gp.getObj()[gp.getCurrentMap()][i].getWorldY() + gp.getObj()[gp.getCurrentMap()][i].getSolidArea().y;

                switch (direction) {
                    case "up":
                        entity.getSolidArea().y -= entity.getSpeed();
                        break;
                    case "down":
                        entity.getSolidArea().y += entity.getSpeed();
                        break;
                    case "left":
                        entity.getSolidArea().x -= entity.getSpeed();
                        break;
                    case "right":
                        entity.getSolidArea().x += entity.getSpeed();
                        break;
                }
                if (entity.getSolidArea().intersects(gp.getObj()[gp.getCurrentMap()][i].getSolidArea())) {
                    if (gp.getObj()[gp.getCurrentMap()][i].isCollision()) {
                        entity.setCollisionOn(true);
                    }
                    if (player) {
                        index = i;
                    }
                }
                entity.getSolidArea().x = entity.getSolidAreaDefaultX();
                entity.getSolidArea().y = entity.getSolidAreaDefaultY();

                gp.getObj()[gp.getCurrentMap()][i].getSolidArea().x = gp.getObj()[gp.getCurrentMap()][i].getSolidAreaDefaultX();
                gp.getObj()[gp.getCurrentMap()][i].getSolidArea().y = gp.getObj()[gp.getCurrentMap()][i].getSolidAreaDefaultY();
            }
        }
        return index;
    }

    public int checkEntity(Entity entity, Entity[][] target) {
        int index = 999;

        String direction = entity.getDirection();
        if (entity.isKnockBack()) {
            direction = entity.getKnockBackDirection();
        }

        for (int i = 0; i < target[1].length; i++) {
            if (target[gp.getCurrentMap()][i] != null) {
                entity.getSolidArea().x = entity.getWorldX() + entity.getSolidArea().x;
                entity.getSolidArea().y = entity.getWorldY() + entity.getSolidArea().y;

                target[gp.getCurrentMap()][i].getSolidArea().x =
                        target[gp.getCurrentMap()][i].getWorldX() + target[gp.getCurrentMap()][i].getSolidArea().x;
                target[gp.getCurrentMap()][i].getSolidArea().y =
                        target[gp.getCurrentMap()][i].getWorldY() + target[gp.getCurrentMap()][i].getSolidArea().y;

                switch (direction) {
                    case "up":
                        entity.getSolidArea().y -= entity.getSpeed();
                        break;
                    case "down":
                        entity.getSolidArea().y += entity.getSpeed();
                        break;
                    case "left":
                        entity.getSolidArea().x -= entity.getSpeed();
                        break;
                    case "right":
                        entity.getSolidArea().x += entity.getSpeed();
                        break;
                }

                if (entity.getSolidArea().intersects(target[gp.getCurrentMap()][i].getSolidArea())) {
                    if (target[gp.getCurrentMap()][i] != entity) {
                        entity.setCollisionOn(true);
                        index = i;
                    }
                }
                entity.getSolidArea().x = entity.getSolidAreaDefaultX();
                entity.getSolidArea().y = entity.getSolidAreaDefaultY();

                target[gp.getCurrentMap()][i].getSolidArea().x = target[gp.getCurrentMap()][i].getSolidAreaDefaultX();
                target[gp.getCurrentMap()][i].getSolidArea().y = target[gp.getCurrentMap()][i].getSolidAreaDefaultY();
            }
        }
        return index;
    }

    public boolean checkPlayer(Entity entity) {
        boolean contactPlayer = false;

        entity.getSolidArea().x = entity.getWorldX() + entity.getSolidArea().x;
        entity.getSolidArea().y = entity.getWorldY() + entity.getSolidArea().y;

        gp.getPlayer().getSolidArea().x = gp.getPlayer().getWorldX() + gp.getPlayer().getSolidArea().x;
        gp.getPlayer().getSolidArea().y = gp.getPlayer().getWorldY() + gp.getPlayer().getSolidArea().y;

        switch (entity.getDirection()) {
            case "up":
                entity.getSolidArea().y -= entity.getSpeed();
                break;
            case "down":
                entity.getSolidArea().y += entity.getSpeed();
                break;
            case "left":
                entity.getSolidArea().x -= entity.getSpeed();
                break;
            case "right":
                entity.getSolidArea().x += entity.getSpeed();
                break;
        }
        if (entity.getSolidArea().intersects(gp.getPlayer().getSolidArea())) {
            entity.setCollisionOn(true);
            contactPlayer = true;
        }
        entity.getSolidArea().x = entity.getSolidAreaDefaultX();
        entity.getSolidArea().y = entity.getSolidAreaDefaultY();

        gp.getPlayer().getSolidArea().x = gp.getPlayer().getSolidAreaDefaultX();
        gp.getPlayer().getSolidArea().y = gp.getPlayer().getSolidAreaDefaultY();

        return contactPlayer;
    }
}