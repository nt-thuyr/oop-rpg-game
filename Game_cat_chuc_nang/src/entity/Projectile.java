package entity;

import main.GamePanel;

public class Projectile extends Entity {

    private Entity user;

    public Projectile(GamePanel gp) {
        super(gp);
    }

    public void set(int worldX, int worldY, String direction, boolean alive, Entity user) {
        setWorldX(worldX);
        setWorldY(worldY);
        setDirection(direction);
        setAlive(alive);
        this.user = user;
        setLife(getMaxLife()); // Reset the life to the max value every time you shoot it.
    }

    public void update() {
        if (user == gp.getPlayer()) {
            int monsterIndex = gp.getcChecker().checkEntity(this, gp.getMonster());
            if (monsterIndex != 999) { // Collision with monster
                gp.getPlayer().damageMonster(monsterIndex, this, getAttack() * (1 + (gp.getPlayer().getLevel() / 2)), getKnockBackPower());
                generateParticle(user.getProjectile(), gp.getMonster()[gp.getCurrentMap()][monsterIndex]);
                setAlive(false);
            }
        }
        if (user != gp.getPlayer()) {
            boolean contactPlayer = gp.getcChecker().checkPlayer(this);
            if (!gp.getPlayer().isInvincible() && contactPlayer) {
                damagePlayer(getAttack());
                if (gp.getPlayer().isGuarding()) {
                    generateParticle(user.getProjectile(), user.getProjectile());
                } else {
                    generateParticle(user.getProjectile(), gp.getPlayer());
                }
                setAlive(false);
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
            setAlive(false); // Once you shoot projectile, it loses its life
        }

        setSpriteCounter(getSpriteCounter() + 1);
        if (getSpriteCounter() > 12) {
            if (getSpriteNum() == 1) {
                setSpriteNum(2);
            } else if (getSpriteNum() == 2) {
                setSpriteNum(1);
            }
            setSpriteCounter(0);
        }
    }

    public boolean haveResource(Entity user) {
        return false;
    }

    public void subtractResource(Entity user) {
        // No implementation provided
    }

    public Entity getUser() {
        return user;
    }

    public void setUser(Entity user) {
        this.user = user;
    }
}