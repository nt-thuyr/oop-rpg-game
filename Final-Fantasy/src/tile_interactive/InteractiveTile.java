package tile_interactive;

import character.Character;
import main.GamePanel;

import java.awt.*;

public class InteractiveTile extends Character {

    private GamePanel gp;
    private boolean destructible = false;

    public InteractiveTile(GamePanel gp, int col, int row) {
        super(gp);
        this.gp = gp;
    }

    public boolean isCorrectItem(Character entity) {
        boolean isCorrectItem = false;
        // Sub-class specifications
        return isCorrectItem;
    }

    public void playSE() {
        // Sub-class specifications
    }

    public InteractiveTile getDestroyedForm() {
        InteractiveTile tile = null;
        // Sub-class specifications
        return tile;
    }

    public void update() {
        if (getState().isInvincible()) {
            getState().setInvincibleCounter(getState().getInvincibleCounter() + 1);
            if (getState().getInvincibleCounter() > 20) {
                getState().setInvincible(false);
                getState().setInvincibleCounter(0);
            }
        }
    }

    public void draw(Graphics2D g2) {
        int screenX = getWorldX() - gp.getPlayer().getWorldX() + gp.getPlayer().getScreenX();
        int screenY = getWorldY() - gp.getPlayer().getWorldY() + gp.getPlayer().getScreenY();

        if (getWorldX() + gp.getTileSize() > gp.getPlayer().getWorldX() - gp.getPlayer().getScreenX() &&
                getWorldX() - gp.getTileSize() < gp.getPlayer().getWorldX() + gp.getPlayer().getScreenX() &&
                getWorldY() + gp.getTileSize() > gp.getPlayer().getWorldY() - gp.getPlayer().getScreenY() &&
                getWorldY() - gp.getTileSize() < gp.getPlayer().getWorldY() + gp.getPlayer().getScreenY()) {
            g2.drawImage(getDown1(), screenX, screenY, null);
        }
    }

    public boolean isDestructible() {
        return destructible;
    }


    @Override
    public GamePanel getGp() {
        return gp;
    }

    @Override
    public void setGp(GamePanel gp) {
        this.gp = gp;
    }
}