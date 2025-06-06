package entity;

import main.GamePanel;

public class Projectile extends Character {

    private Character user;

    public Projectile(GamePanel gp) {
        super(gp);
    }

    public void set(int worldX, int worldY, String direction, boolean alive, Character user) {
        setWorldX(worldX);
        setWorldY(worldY);
        setDirection(direction);
        getState().setAlive(alive);
        this.user = user;
        setLife(getMaxLife()); // Reset the life to the max value every time you shoot it.
    }

    public void update() {
        if (user == gp.getPlayer()) {
            int monsterIndex = gp.getcChecker().checkEntity(this, gp.getMonster());
            if (monsterIndex != 999) { // Collision with monster
                gp.getPlayer().damageMonster(monsterIndex, this, getAttack() * (1 + (gp.getPlayer().getLevel() / 2)), getKnockBackPower());
                generateParticle(user.getProjectile(), gp.getMonster()[gp.getCurrentMap()][monsterIndex]);
                getState().setAlive(false);
            }
        }
        if (user != gp.getPlayer()) {
            boolean contactPlayer = gp.getcChecker().checkPlayer(this);
            if (!gp.getPlayer().getState().isInvincible() && contactPlayer) {
                damagePlayer(getAttack());
                if (gp.getPlayer().getState().isGuarding()) {
                    generateParticle(user.getProjectile(), user.getProjectile());
                } else {
                    generateParticle(user.getProjectile(), gp.getPlayer());
                }
                getState().setAlive(false);
            }
        }

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

        setLife(getLife() - 1);
        if (getLife() <= 0) {
            getState().setAlive(false); // Once you shoot projectile, it loses its life
        }

        getState().setSpriteCounter(getState().getSpriteCounter() + 1);
        if (getState().getSpriteCounter() > 12) {
            if (getSpriteNum() == 1) {
                setSpriteNum(2);
            } else if (getSpriteNum() == 2) {
                setSpriteNum(1);
            }
            getState().setSpriteCounter(0);
        }
    }
}