package tile_interactive;

import entity.Character;
import main.GamePanel;
import object.Item;

import java.awt.*;

public class IT_DryTree extends InteractiveTile {

    private GamePanel gp;

    public IT_DryTree(GamePanel gp, int col, int row) {
        super(gp, col, row);
        this.gp = gp;

        setWorldX(gp.getTileSize() * col);
        setWorldY(gp.getTileSize() * row);

        setDown1(setup("/tiles_interactive/drytree", gp.getTileSize(), gp.getTileSize()));
        setDestructible(true);
        setLife(2);
    }

    @Override
    public boolean isCorrectItem(Character entity) {
        return entity.getCurrentWeapon().getType() == Item.getType_axe();
    }

    @Override
    public void playSE() {
        gp.playSE(11);
    }

    @Override
    public InteractiveTile getDestroyedForm() {
        return new IT_Trunk(gp, getWorldX() / gp.getTileSize(), getWorldY() / gp.getTileSize());
    }

    @Override
    public Color getParticleColor() {
        return new Color(65, 50, 30);
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