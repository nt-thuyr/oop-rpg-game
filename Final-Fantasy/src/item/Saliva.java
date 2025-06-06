package item;

import character.Projectile;
import main.GamePanel;

import java.awt.*;

public class Saliva extends Projectile {

    public static final String objName = "Rock";

    public Saliva(GamePanel gp) {
        super(gp);
        this.gp = gp;

        setName(objName);
        setSpeed(5);
        setMaxLife(60); // after 80 frames, projectile disappears
        setLife(getMaxLife());
        setAttack(2);
        getState().setAlive(false);
        getImage();
        setKnockBackPower(1);
    }

    public void getImage() {
        setUp1(setup("/projectile/rock_down_1", gp.getTileSize(), gp.getTileSize()));
        setUp2(setup("/projectile/rock_down_1", gp.getTileSize(), gp.getTileSize()));
        setDown1(setup("/projectile/rock_down_1", gp.getTileSize(), gp.getTileSize()));
        setDown2(setup("/projectile/rock_down_1", gp.getTileSize(), gp.getTileSize()));
        setLeft1(setup("/projectile/rock_down_1", gp.getTileSize(), gp.getTileSize()));
        setLeft2(setup("/projectile/rock_down_1", gp.getTileSize(), gp.getTileSize()));
        setRight1(setup("/projectile/rock_down_1", gp.getTileSize(), gp.getTileSize()));
        setRight2(setup("/projectile/rock_down_1", gp.getTileSize(), gp.getTileSize()));
    }

    public Color getParticleColor() {
        return new Color(40, 50, 0);
    }

    public int getParticleSize() {
        return 10; // pixels
    }

    public int getParticleSpeed() {
        return 1;
    }

    public int getParticleMaxLife() {
        return 20;
    }
}