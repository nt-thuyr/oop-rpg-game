package tile_interactive;

import entity.Character;
import main.GamePanel;
import object.Item;

import java.awt.*;

public class IT_DestructibleWall extends InteractiveTile {

    private GamePanel gp;

    public IT_DestructibleWall(GamePanel gp, int col, int row) {
        super(gp, col, row);
        this.gp = gp;

        setWorldX(gp.getTileSize() * col);
        setWorldY(gp.getTileSize() * row);

        setDown1(setup("/tiles_interactive/destructiblewall", gp.getTileSize(), gp.getTileSize()));
        setDestructible(true);
        setLife(3);
    }

    @Override
    public boolean isCorrectItem(Character entity) {
        return entity.getCurrentWeapon().getType() == Item.getType_pickaxe();
    }

    @Override
    public void playSE() {
        gp.playSE(20);
    }

    @Override
    public InteractiveTile getDestroyedForm() {
        return null; // Sub-class specifications
    }

    @Override
    public Color getParticleColor() {
        return new Color(65, 65, 65);
    }

    @Override
    public int getParticleSize() {
        return 6; // pixels
    }

    @Override
    public int getParticleSpeed() {
        return 1;
    }

    @Override
    public int getParticleMaxLife() {
        return 20;
    }
}