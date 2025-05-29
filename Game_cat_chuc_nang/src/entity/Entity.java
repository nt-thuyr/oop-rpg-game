package entity;

import main.GamePanel;
import main.UtilityTool;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class Entity {

    GamePanel gp;
    private BufferedImage up1, up2, down1, down2, left1, left2, right1, right2;
    private BufferedImage attackUp1, attackUp2, attackDown1, attackDown2, attackLeft1, attackLeft2, attackRight1, attackRight2;
    private BufferedImage guardUp, guardDown, guardLeft, guardRight;
    private Rectangle solidArea = new Rectangle(0, 0, 48, 48);
    private Rectangle attackArea = new Rectangle(0, 0, 0, 0);
    private int solidAreaDefaultX, solidAreaDefaultY;
    private boolean collision = false;
    private String[][] dialogues = new String[20][20];
    private Entity attacker;
    private Entity linkedEntity;
    private boolean temp = false;

    // STATE
    private int worldX, worldY;
    private String direction = "down";
    private int spriteNum = 1;
    private int dialogueSet = 0;
    private int dialogueIndex = 0;
//    private boolean collisionOn = false;
//    private boolean invincible = false;
//    private boolean attacking = false;
//    private boolean alive = true;
//    private boolean dying = false;
//    private boolean hpBarOn = false;
//    private boolean onPath = false;
//    private boolean knockBack = false;
//    private String knockBackDirection;
//    private boolean guarding = false;
//    private boolean transparent = false;
//    private boolean offBalance = false;
    private Entity loot;
//    private boolean opened = false;
//    private boolean inRage = false;
//    private boolean sleep = false;
//    private boolean drawing = true;
    private EntityState state = new EntityState();


    // COUNTER
//    private int spriteCounter = 0;
//    private int actionLockCounter = 0;
//    private int invincibleCounter = 0;
//    private int shotAvailableCounter = 0;
//    private int dyingCounter = 0;
//    private int hpBarCounter = 0;
//    private int knockBackCounter = 0;
//    private int guardCounter = 0;
//    private int offBalanceCounter = 0;

    // CHARACTER ATTRIBUTES
    private String name;
    private int defaultSpeed;
    private int speed;
    private int maxLife;
    private int life;
    private int maxMana;
    private int mana;
    private int ammo;
    private int level;
    private int strength;
    private int dexterity;
    private int attack;
    private int defense;
    private int exp;
    private int nextLevelExp;
    private int coin;
    private int motion1_duration;
    private int motion2_duration;
    private Entity currentWeapon;
    private Entity currentShield;
    private Entity currentLight;
    private Projectile projectile;
    private boolean boss;

    // ITEM ATTRIBUTES
    private ArrayList<Entity> inventory = new ArrayList<>();
    private final int maxInventorySize = 20;
    private int attackValue;
    private int defenseValue;
    private String description = "";
    private int useCost;
    private int value;
    private int price;
    private int knockBackPower;
    private boolean stackable = false;
    private int amount = 1;
    private int lightRadius;

    // TYPE
    private int type;
    private final int type_player = 0;
    private final int type_npc = 1;
    private final int type_monster = 2;
    private final int type_sword = 3;
    private final int type_axe = 4;
    private final int type_shield = 5;
    private final int type_consumable = 6;
    private final int type_pickupOnly = 7;
    private final int type_obstacle = 8;
    private final int type_light = 9;
    private final int type_pickaxe = 10;

    private BufferedImage image1;
    private BufferedImage image2;
    private BufferedImage image3;

    public Entity(GamePanel gp)
    {
        this.gp = gp;

    }
    public int getScreenX() {
        int screenX = worldX - gp.getPlayer().getWorldX() + gp.getPlayer().getScreenX();
        return screenX;
    }
    public int getScreenY()
    {
        int screenY = worldY - gp.getPlayer().getWorldY() + gp.getPlayer().getScreenY();
        return screenY;
    }
    public int getLeftX()
    {
        return worldX + solidArea.x;
    }
    public int getRightX()
    {
        return worldX + solidArea.width + solidArea.width;
    }
    public int getTopY()
    {
        return worldY + solidArea.y;
    }
    public int getBottomY()
    {
        return worldY + solidArea.y + solidArea.height;
    }
    public int getCol()
    {
        return (worldX + solidArea.x) / gp.getTileSize();
    }
    public int getRow()
    {
        return (worldY + solidArea.y) / gp.getTileSize();
    }
    public int getCenterX()
    {
        int centerX = worldX + left1.getWidth()/2;
        return centerX;
    }
    public int getCenterY()
    {
        int centerY = worldY + up1.getWidth()/2;
        return centerY;
    }
    public int getXdistance(Entity target)
    {
        int xDistance = Math.abs(getCenterX() - target.getCenterX());
        return xDistance;
    }
    public int getYdistance(Entity target)
    {
        int yDistance = Math.abs(getCenterY() - target.getCenterY());
        return yDistance;
    }
    public int getTileDistance(Entity target)
    {
        int tileDistance = (getXdistance(target) + getYdistance(target))/ gp.getTileSize();
        return tileDistance;
    }
    public int getGoalCol(Entity target)
    {
        int goalCol = (target.worldX + target.solidArea.x) / gp.getTileSize();
        return goalCol;

    }
    public int getGoalRow(Entity target)
    {
        int goalRow = (target.worldY + target.solidArea.y) / gp.getTileSize();
        return goalRow;
    }
//    public void resetCounter()
//    {
//        spriteCounter = 0;
//        actionLockCounter = 0;
//        invincibleCounter = 0;
//        shotAvailableCounter = 0;
//        dyingCounter = 0;
//        hpBarCounter = 0;
//        knockBackCounter = 0;
//        guardCounter = 0;
//        offBalanceCounter = 0;
//    }
    public void setDialogue()
    {
    }
    public void setAction()
    {

    }
    public void move(String direction)
    {

    }
    public void damageReaction()
    {

    }
    public void speak()
    {

    }
    public void facePlayer()
    {
        switch (gp.getPlayer().getDirection())
        {
            case "up":
                direction = "down";
                break;
            case "down":
                direction = "up";
                break;
            case "left":
                direction = "right";
                break;
            case "right":
                direction = "left";
                break;
        }
    }
    public void startDialogue(Entity entity, int setNum)
    {
        gp.setGameState(gp.getDialogueState());
        gp.getUi().setNpc(entity);
        dialogueSet = setNum;
    }
    public void interact()
    {

    }
    public boolean use(Entity entity)
    {
        return false;
        //return "true" if you used the item and "false" if you failed to use it.
    }
    public void checkDrop()
    {

    }
    public void dropItem(Entity droppedItem)
    {
        for(int i = 0; i < gp.getObj()[1].length; i++)
        {
            if(gp.getObj()[gp.getCurrentMap()][i] == null)
            {
                gp.getObj()[gp.getCurrentMap()][i] = droppedItem;
                gp.getObj()[gp.getCurrentMap()][i].worldX = worldX;  //the dead monster's worldX
                gp.getObj()[gp.getCurrentMap()][i].worldY = worldY;  //the dead monster's worldY
                break; //end loop after finding empty slot on array
            }
        }
    }
    public Color getParticleColor()
    {
        Color color = null;
        return color;
    }
    public int getParticleSize()
    {
        int size = 0; //pixels
        return size;
    }
    public int getParticleSpeed()
    {
        int speed = 0;
        return speed;
    }
    public int getParticleMaxLife()
    {
        int maxLife = 0;
        return maxLife;
    }
    public void generateParticle(Entity generator, Entity target)
    {
        Color color = generator.getParticleColor();
        int size = generator.getParticleSize();
        int speed = generator.getParticleSpeed();
        int maxLife = generator.getParticleMaxLife();




    }
    public void checkCollision() {
        getState().setCollisionOn(false);
        gp.getcChecker().checkTile(this);
        gp.getcChecker().checkObject(this, false);
        gp.getcChecker().checkEntity(this, gp.getNpc());
        gp.getcChecker().checkEntity(this, gp.getMonster());
        gp.getcChecker().checkEntity(this, gp.getiTile());
        boolean contactPlayer = gp.getcChecker().checkPlayer(this);
        if (getType() == getType_monster() && contactPlayer == true) {
            damagePlayer(getAttack());
        }
    }

    public void update() {
        if (!getState().isSleep()) {
            if (getState().isKnockBack()) {
                checkCollision();
                if (getState().isCollisionOn()) {
                    getState().setKnockBackCounter(0);
                    getState().setKnockBack(false);
                    setSpeed(getDefaultSpeed());
                } else {
                    switch (getState().getKnockBackDirection()) {
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
                getState().setKnockBackCounter(getState().getKnockBackCounter() + 1);
                if (getState().getKnockBackCounter() == 10) {
                    getState().setKnockBackCounter(0);
                    getState().setKnockBack(false);
                    setSpeed(getDefaultSpeed());
                }
            } else if (getState().isAttacking()) {
                attacking();
            } else {
                setAction();
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
                getState().setSpriteCounter(getState().getSpriteCounter() + 1);
                if (getState().getSpriteCounter() > 24) {
                    if (getSpriteNum() == 1) {
                        setSpriteNum(2);
                    } else if (getSpriteNum() == 2) {
                        setSpriteNum(1);
                    }
                    getState().setSpriteCounter(0);
                }
            }
            if (getState().isInvincible()) {
                getState().setInvincibleCounter(getState().getInvincibleCounter() + 1);
                if (getState().getInvincibleCounter() > 40) {
                    getState().setInvincible(false);
                    getState().setInvincibleCounter(0);
                }
            }
            if (getState().getShotAvailableCounter() < 30) {
                getState().setShotAvailableCounter(getState().getShotAvailableCounter() + 1);
            }
            if (getState().isOffBalance()) {
                getState().setOffBalanceCounter(getState().getOffBalanceCounter() + 1);
                if (getState().getOffBalanceCounter() > 60) {
                    getState().setOffBalance(false);
                    getState().setOffBalanceCounter(0);
                }
            }
        }
    }

    public void checkAttackOrNot(int rate, int straight, int horizontal) {
        boolean targetInRange = false;
        int xDis = getXdistance(gp.getPlayer());
        int yDis = getYdistance(gp.getPlayer());

        switch (getDirection()) {
            case "up":
                if (gp.getPlayer().getCenterY() < getCenterY() && yDis < straight && xDis < horizontal) {
                    targetInRange = true;
                }
                break;
            case "down":
                if (gp.getPlayer().getCenterY() > getCenterY() && yDis < straight && xDis < horizontal) {
                    targetInRange = true;
                }
                break;
            case "left":
                if (gp.getPlayer().getCenterX() < getCenterX() && xDis < straight && yDis < horizontal) {
                    targetInRange = true;
                }
                break;
            case "right":
                if (gp.getPlayer().getCenterX() > getCenterX() && xDis < straight && yDis < horizontal) {
                    targetInRange = true;
                }
                break;
        }

        if (targetInRange == true) {
            int i = new Random().nextInt(rate);
            if (i == 0) {
                getState().setAttacking(true);
                setSpriteNum(1);
                getState().setSpriteCounter(0);
                getState().setShotAvailableCounter(0);
            }
        }
    }
    public void checkShootOrNot(int rate, int shotInterval)
    {
        int i = new Random().nextInt(rate);
        if (i == 0 && !projectile.getState().isAlive() && state.getShotAvailableCounter() == shotInterval)
        {
            projectile.set(worldX,worldY,direction,true,this);
            //gp.projectileList.add(projectile);
            //CHECK VACANCY
            for(int ii = 0; ii < gp.getProjectile()[1].length; ii++)
            {
                if(gp.getProjectile()[gp.getCurrentMap()][ii] == null)
                {
                    gp.getProjectile()[gp.getCurrentMap()][ii] = projectile;
                    break;
                }
            }
            state.setShotAvailableCounter(0);
        }
    }
    public void checkStartChasingOrNot(Entity target, int distance, int rate)
    {
        if(getTileDistance(target) < distance)
        {
            int i = new Random().nextInt(rate);
            if(i == 0)
            {
                state.setOnPath(true);
            }
        }
    }
    public void checkStopChasingOrNot(Entity target, int distance, int rate)
    {
        if(getTileDistance(target) > distance)
        {
            int i = new Random().nextInt(rate);
            if(i == 0)
            {
                state.setOnPath(false);
            }
        }
    }
    public void getRandomDirection(int interval) {
        getState().setActionLockCounter(getState().getActionLockCounter() + 1);

        if (getState().getActionLockCounter() > interval) {
            Random random = new Random();
            int i = random.nextInt(100) + 1;  // pick up a number from 1 to 100
            if (i <= 25) {
                setDirection("up");
            }
            if (i > 25 && i <= 50) {
                setDirection("down");
            }
            if (i > 50 && i <= 75) {
                setDirection("left");
            }
            if (i > 75 && i <= 100) {
                setDirection("right");
            }
            getState().setActionLockCounter(0); // reset
        }
    }

    public void moveTowardPlayer(int interval) {
        getState().setActionLockCounter(getState().getActionLockCounter() + 1);

        if (getState().getActionLockCounter() > interval) {
            if (getXdistance(gp.getPlayer()) > getYdistance(gp.getPlayer())) { // If entity is farther from the player on the X-axis
                if (gp.getPlayer().getCenterX() < getCenterX()) { // Player is on the left side, entity moves left
                    setDirection("left");
                } else {
                    setDirection("right");
                }
            } else if (getXdistance(gp.getPlayer()) < getYdistance(gp.getPlayer())) { // If entity is farther from the player on the Y-axis
                if (gp.getPlayer().getCenterY() < getCenterY()) { // Player is above, entity moves up
                    setDirection("up");
                } else {
                    setDirection("down");
                }
            }
            getState().setActionLockCounter(0);
        }
    }

    public String getOppositeDirection(String direction) {
        String oppositeDirection = "";

        switch (direction) {
            case "up":
                oppositeDirection = "down";
                break;
            case "down":
                oppositeDirection = "up";
                break;
            case "left":
                oppositeDirection = "right";
                break;
            case "right":
                oppositeDirection = "left";
                break;
        }

        return oppositeDirection;
    }

    public void attacking() {
        getState().setSpriteCounter(getState().getSpriteCounter() + 1);

        if (getState().getSpriteCounter() <= getMotion1_duration()) {
            setSpriteNum(1);
        }
        if (getState().getSpriteCounter() > getMotion1_duration() && getState().getSpriteCounter() <= getMotion2_duration()) {
            setSpriteNum(2);

            // Save the current worldX, worldY, solidArea
            int currentWorldX = getWorldX();
            int currentWorldY = getWorldY();
            int solidAreaWidth = getSolidArea().width;
            int solidAreaHeight = getSolidArea().height;

            // Adjust player's worldX/worldY for the attackArea
            switch (getDirection()) {
                case "up":
                    setWorldY(getWorldY() - getAttackArea().height);
                    break; // attackArea's size
                case "down":
                    setWorldY(getWorldY() + gp.getTileSize());
                    break; // gp.tileSize (player's size)
                case "left":
                    setWorldX(getWorldX() - getAttackArea().width);
                    break; // attackArea's size
                case "right":
                    setWorldX(getWorldX() + gp.getTileSize());
                    break; // gp.tileSize (player's size)
            }

            // attackArea becomes solidArea
            getSolidArea().width = getAttackArea().width;
            getSolidArea().height = getAttackArea().height;

            if (getType() == getType_monster()) {
                if (gp.getcChecker().checkPlayer(this)) { // This means attack is hitting player
                    damagePlayer(getAttack());
                }
            } else { // Player
                // Check monster collision with the updated worldX, worldY and solidArea
                int monsterIndex = gp.getcChecker().checkEntity(this, gp.getMonster());
                gp.getPlayer().damageMonster(monsterIndex, this, getAttack(), getCurrentWeapon().getKnockBackPower());

                int iTileIndex = gp.getcChecker().checkEntity(this, gp.getiTile());
                gp.getPlayer().damageInteractiveTile(iTileIndex);

                int projectileIndex = gp.getcChecker().checkEntity(this, gp.getProjectile());
                gp.getPlayer().damageProjectile(projectileIndex);
            }

            // After checking collision, restore the original data
            setWorldX(currentWorldX);
            setWorldY(currentWorldY);
            getSolidArea().width = solidAreaWidth;
            getSolidArea().height = solidAreaHeight;
        }
        if (getState().getSpriteCounter() > getMotion2_duration()) {
            setSpriteNum(1);
            getState().setSpriteCounter(0);
            getState().setAttacking(false);
        }
    }
    public void damagePlayer(int attack) {
        if (!gp.getPlayer().getState().isInvincible()) {
            int damage = attack - gp.getPlayer().getDefense();
            // Get an opposite direction of this attacker
            String canGuardDirection = getOppositeDirection(getDirection());

            if (gp.getPlayer().getState().isGuarding() && gp.getPlayer().getDirection().equals(canGuardDirection)) {
                // Parry: If you press guard key less than 10 frames before the attack, you receive 0 damage and get a critical chance
                if (gp.getPlayer().getState().getGuardCounter() < 10) {
                    damage = 0;
                    gp.playSE(16);
                    setKnockBack(this, gp.getPlayer(), getKnockBackPower()); // Knockback attacker
                    getState().setOffBalance(true);
                    getState().setSpriteCounter(getState().getSpriteCounter() - 60); // Attacker's sprites return to motion1 (stun effect)
                } else {
                    // Normal Guard
                    damage /= 2;
                    gp.playSE(15);
                }
            } else {
                // Not guarding
                gp.playSE(6); // receivedamage.wav
                if (damage < 1) {
                    damage = 1;
                }
            }
            if (damage != 0) {
                gp.getPlayer().getState().setTransparent(true);
                setKnockBack(gp.getPlayer(), this, getKnockBackPower());
            }

            // Apply damage
            gp.getPlayer().setLife(gp.getPlayer().getLife() - damage);
            gp.getPlayer().getState().setInvincible(true);
        }
    }

    public void setKnockBack(Entity target, Entity attacker, int knockBackPower) {
        this.attacker = attacker;
        target.getState().setKnockBackDirection(attacker.getDirection());
        target.setSpeed(target.getSpeed() + knockBackPower);
        target.getState().setKnockBack(true);
    }

    public boolean inCamera() {
        boolean inCamera = false;
        if (getWorldX() + gp.getTileSize() * 5 > gp.getPlayer().getWorldX() - gp.getPlayer().getScreenX() && //*5 because skeleton lord disappears when the top left corner isn't on the screen
                getWorldX() - gp.getTileSize() < gp.getPlayer().getWorldX() + gp.getPlayer().getScreenX() &&
                getWorldY() + gp.getTileSize() * 5 > gp.getPlayer().getWorldY() - gp.getPlayer().getScreenY() &&
                getWorldY() - gp.getTileSize() < gp.getPlayer().getWorldY() + gp.getPlayer().getScreenY()) {
            inCamera = true;
        }
        return inCamera;
    }

    public void draw(Graphics2D g2) {
        BufferedImage image = null;

        if (inCamera() == true) {
            int tempScreenX = getScreenX();
            int tempScreenY = getScreenY();

            switch (getDirection()) {
                case "up":
                    if (!getState().isAttacking()) { // Normal walking sprites
                        if (getSpriteNum() == 1) {
                            image = getUp1();
                        }
                        if (getSpriteNum() == 2) {
                            image = getUp2();
                        }
                    }
                    if (getState().isAttacking()) { // Attacking sprites
                        tempScreenY = getScreenY() - getUp1().getHeight(); // Adjusted the player's position one tile to up
                        if (getSpriteNum() == 1) {
                            image = getAttackUp1();
                        }
                        if (getSpriteNum() == 2) {
                            image = getAttackUp2();
                        }
                    }
                    break;

                case "down":
                    if (!getState().isAttacking()) { // Normal walking sprites
                        if (getSpriteNum() == 1) {
                            image = getDown1();
                        }
                        if (getSpriteNum() == 2) {
                            image = getDown2();
                        }
                    }
                    if (getState().isAttacking()) { // Attacking sprites
                        if (getSpriteNum() == 1) {
                            image = getAttackDown1();
                        }
                        if (getSpriteNum() == 2) {
                            image = getAttackDown2();
                        }
                    }
                    break;

                case "left":
                    if (!getState().isAttacking()) { // Normal walking sprites
                        if (getSpriteNum() == 1) {
                            image = getLeft1();
                        }
                        if (getSpriteNum() == 2) {
                            image = getLeft2();
                        }
                    }
                    if (getState().isAttacking()) { // Attacking sprites
                        tempScreenX = getScreenX() - getUp1().getWidth(); // Adjusted the player's position one tile left
                        if (getSpriteNum() == 1) {
                            image = getAttackLeft1();
                        }
                        if (getSpriteNum() == 2) {
                            image = getAttackLeft2();
                        }
                    }
                    break;

                case "right":
                    if (!getState().isAttacking()) { // Normal walking sprites
                        if (getSpriteNum() == 1) {
                            image = getRight1();
                        }
                        if (getSpriteNum() == 2) {
                            image = getRight2();
                        }
                    }
                    if (getState().isAttacking()) { // Attacking sprites
                        if (getSpriteNum() == 1) {
                            image = getAttackRight1();
                        }
                        if (getSpriteNum() == 2) {
                            image = getAttackRight2();
                        }
                    }
                    break;
            }

            // Make entity half-transparent (%30) when invincible
            if (getState().isInvincible()) {
                getState().setHpBarOn(true); // when player attacks monster play hpBar
                getState().setHpBarCounter(0); // reset monster aggro
                changeAlpha(g2, 0.4F);
            }

            if (getState().isDying()) {
                dyingAnimation(g2);
            }

            g2.drawImage(image, tempScreenX, tempScreenY, null);

            // Reset graphics opacity / alpha
            changeAlpha(g2, 1F);
        }
    }
    // Every 5 frames switch alpha between 0 and 1
    public void dyingAnimation(Graphics2D g2) {
        getState().setDyingCounter(getState().getDyingCounter() + 1);
        int i = 5; // interval

        if (getState().getDyingCounter() <= i) {
            changeAlpha(g2, 0f);
        }
        if (getState().getDyingCounter() > i && getState().getDyingCounter() <= i * 2) {
            changeAlpha(g2, 1f);
        }
        if (getState().getDyingCounter() > i * 2 && getState().getDyingCounter() <= i * 3) {
            changeAlpha(g2, 0f);
        }
        if (getState().getDyingCounter() > i * 3 && getState().getDyingCounter() <= i * 4) {
            changeAlpha(g2, 1f);
        }
        if (getState().getDyingCounter() > i * 4 && getState().getDyingCounter() <= i * 5) {
            changeAlpha(g2, 0f);
        }
        if (getState().getDyingCounter() > i * 5 && getState().getDyingCounter() <= i * 6) {
            changeAlpha(g2, 1f);
        }
        if (getState().getDyingCounter() > i * 6 && getState().getDyingCounter() <= i * 7) {
            changeAlpha(g2, 0f);
        }
        if (getState().getDyingCounter() > i * 7 && getState().getDyingCounter() <= i * 8) {
            changeAlpha(g2, 1f);
        }
        if (getState().getDyingCounter() > i * 8) {
            getState().setAlive(false);
        }
    }

    public void changeAlpha(Graphics2D g2, float alphaValue) {
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alphaValue));
    }

    public BufferedImage setup(String imagePath, int width, int height) {
        UtilityTool uTool = new UtilityTool();
        BufferedImage image = null;

        try {
            image = ImageIO.read(getClass().getResourceAsStream(imagePath + ".png"));
            image = uTool.scaleImage(image, width, height); // it scales to tilesize, will fix for player attack(16px x 32px) by adding width and height
        } catch (IOException e) {
            e.printStackTrace();
        }
        return image;
    }

    public void searchPath(int goalCol, int goalRow) {
        int startCol = (getWorldX() + getSolidArea().x) / gp.getTileSize();
        int startRow = (getWorldY() + getSolidArea().y) / gp.getTileSize();
        gp.getpFinder().setNodes(startCol, startRow, goalCol, goalRow, this);
        if (gp.getpFinder().search() == true) {
            // Next WorldX and WorldY
            int nextX = gp.getpFinder().getPathList().get(0).getCol() * gp.getTileSize();
            int nextY = gp.getpFinder().getPathList().get(0).getRow() * gp.getTileSize();
            // Entity's solidArea position
            int enLeftX = getWorldX() + getSolidArea().x;
            int enRightX = getWorldX() + getSolidArea().x + getSolidArea().width;
            int enTopY = getWorldY() + getSolidArea().y;
            int enBottomY = getWorldY() + getSolidArea().y + getSolidArea().height;

            // TOP PATH
            if (enTopY > nextY && enLeftX >= nextX && enRightX < nextX + gp.getTileSize()) {
                setDirection("up");
            }
            // BOTTOM PATH
            else if (enTopY < nextY && enLeftX >= nextX && enRightX < nextX + gp.getTileSize()) {
                setDirection("down");
            }
            // RIGHT - LEFT PATH
            else if (enTopY >= nextY && enBottomY < nextY + gp.getTileSize()) {
                // either left or right
                // LEFT PATH
                if (enLeftX > nextX) {
                    setDirection("left");
                }
                // RIGHT PATH
                if (enLeftX < nextX) {
                    setDirection("right");
                }
            }
            // OTHER EXCEPTIONS
            else if (enTopY > nextY && enLeftX > nextX) {
                // up or left
                setDirection("up");
                checkCollision();
                if (getState().isCollisionOn() == true) {
                    setDirection("left");
                }
            } else if (enTopY > nextY && enLeftX < nextX) {
                // up or right
                setDirection("up");
                checkCollision();
                if (getState().isCollisionOn() == true) {
                    setDirection("right");
                }
            } else if (enTopY < nextY && enLeftX > nextX) {
                // down or left
                setDirection("down");
                checkCollision();
                if (getState().isCollisionOn() == true) {
                    setDirection("left");
                }
            } else if (enTopY < nextY && enLeftX < nextX) {
                // down or right
                setDirection("down");
                checkCollision();
                if (getState().isCollisionOn() == true) {
                    setDirection("right");
                }
            }
        }
    }

    public int getDetected(Entity user, Entity target[][], String targetName) {
        int index = 999;

        // Check the surrounding object
        int nextWorldX = user.getLeftX();
        int nextWorldY = user.getTopY();

        switch (user.getDirection()) {
            case "up":
                nextWorldY = user.getTopY() - gp.getPlayer().getSpeed();
                break;
            case "down":
                nextWorldY = user.getBottomY() + gp.getPlayer().getSpeed();
                break;
            case "left":
                nextWorldX = user.getLeftX() - gp.getPlayer().getSpeed();
                break;
            case "right":
                nextWorldX = user.getRightX() + gp.getPlayer().getSpeed();
                break;
        }
        int col = nextWorldX / gp.getTileSize();
        int row = nextWorldY / gp.getTileSize();

        for (int i = 0; i < target[1].length; i++) {
            if (target[gp.getCurrentMap()][i] != null) {
                if (target[gp.getCurrentMap()][i].getCol() == col // checking if player 1 tile away from target (key etc.) (must be same direction)
                        && target[gp.getCurrentMap()][i].getRow() == row
                        && target[gp.getCurrentMap()][i].getName().equals(targetName)) {
                    index = i;
                    break;
                }
            }
        }
        return index;
    }


    public int getAmmo() {
        return ammo;
    }

    public void setAmmo(int ammo) {
        this.ammo = ammo;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getAttack() {
        return attack;
    }

    public int setAttack(int attack) {
        this.attack = attack;
        return attack;
    }

    public Rectangle getAttackArea() {
        return attackArea;
    }

    public void setAttackArea(Rectangle attackArea) {
        this.attackArea = attackArea;
    }

    public BufferedImage getAttackDown1() {
        return attackDown1;
    }

    public void setAttackDown1(BufferedImage attackDown1) {
        this.attackDown1 = attackDown1;
    }

    public BufferedImage getAttackDown2() {
        return attackDown2;
    }

    public void setAttackDown2(BufferedImage attackDown2) {
        this.attackDown2 = attackDown2;
    }

    public Entity getAttacker() {
        return attacker;
    }

    public void setAttacker(Entity attacker) {
        this.attacker = attacker;
    }

    public BufferedImage getAttackLeft1() {
        return attackLeft1;
    }

    public void setAttackLeft1(BufferedImage attackLeft1) {
        this.attackLeft1 = attackLeft1;
    }

    public BufferedImage getAttackLeft2() {
        return attackLeft2;
    }

    public void setAttackLeft2(BufferedImage attackLeft2) {
        this.attackLeft2 = attackLeft2;
    }

    public BufferedImage getAttackRight1() {
        return attackRight1;
    }

    public void setAttackRight1(BufferedImage attackRight1) {
        this.attackRight1 = attackRight1;
    }

    public BufferedImage getAttackRight2() {
        return attackRight2;
    }

    public void setAttackRight2(BufferedImage attackRight2) {
        this.attackRight2 = attackRight2;
    }

    public BufferedImage getAttackUp1() {
        return attackUp1;
    }

    public void setAttackUp1(BufferedImage attackUp1) {
        this.attackUp1 = attackUp1;
    }

    public BufferedImage getAttackUp2() {
        return attackUp2;
    }

    public void setAttackUp2(BufferedImage attackUp2) {
        this.attackUp2 = attackUp2;
    }

    public int getAttackValue() {
        return attackValue;
    }

    public void setAttackValue(int attackValue) {
        this.attackValue = attackValue;
    }

    public boolean isBoss() {
        return boss;
    }

    public void setBoss(boolean boss) {
        this.boss = boss;
    }

    public int getCoin() {
        return coin;
    }

    public void setCoin(int coin) {
        this.coin = coin;
    }

    public boolean isCollision() {
        return collision;
    }

    public void setCollision(boolean collision) {
        this.collision = collision;
    }

    public Entity getCurrentLight() {
        return currentLight;
    }

    public void setCurrentLight(Entity currentLight) {
        this.currentLight = currentLight;
    }

    public Entity getCurrentShield() {
        return currentShield;
    }

    public void setCurrentShield(Entity currentShield) {
        this.currentShield = currentShield;
    }

    public Entity getCurrentWeapon() {
        return currentWeapon;
    }

    public void setCurrentWeapon(Entity currentWeapon) {
        this.currentWeapon = currentWeapon;
    }

    public int getDefaultSpeed() {
        return defaultSpeed;
    }

    public void setDefaultSpeed(int defaultSpeed) {
        this.defaultSpeed = defaultSpeed;
    }

    public int getDefense() {
        return defense;
    }

    public int setDefense(int defense) {
        this.defense = defense;
        return defense;
    }

    public int getDefenseValue() {
        return defenseValue;
    }

    public void setDefenseValue(int defenseValue) {
        this.defenseValue = defenseValue;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getDexterity() {
        return dexterity;
    }

    public void setDexterity(int dexterity) {
        this.dexterity = dexterity;
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

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public BufferedImage getDown1() {
        return down1;
    }

    public void setDown1(BufferedImage down1) {
        this.down1 = down1;
    }

    public BufferedImage getDown2() {
        return down2;
    }

    public void setDown2(BufferedImage down2) {
        this.down2 = down2;
    }

    public int getExp() {
        return exp;
    }

    public void setExp(int exp) {
        this.exp = exp;
    }

    public GamePanel getGp() {
        return gp;
    }

    public void setGp(GamePanel gp) {
        this.gp = gp;
    }

    public BufferedImage getGuardDown() {
        return guardDown;
    }

    public void setGuardDown(BufferedImage guardDown) {
        this.guardDown = guardDown;
    }

    public BufferedImage getGuardLeft() {
        return guardLeft;
    }

    public void setGuardLeft(BufferedImage guardLeft) {
        this.guardLeft = guardLeft;
    }

    public BufferedImage getGuardRight() {
        return guardRight;
    }

    public void setGuardRight(BufferedImage guardRight) {
        this.guardRight = guardRight;
    }

    public BufferedImage getGuardUp() {
        return guardUp;
    }

    public void setGuardUp(BufferedImage guardUp) {
        this.guardUp = guardUp;
    }

    public BufferedImage getImage1() {
        return image1;
    }

    public void setImage1(BufferedImage image1) {
        this.image1 = image1;
    }

    public BufferedImage getImage2() {
        return image2;
    }

    public void setImage2(BufferedImage image2) {
        this.image2 = image2;
    }

    public BufferedImage getImage3() {
        return image3;
    }

    public void setImage3(BufferedImage image3) {
        this.image3 = image3;
    }

    public ArrayList<Entity> getInventory() {
        return inventory;
    }

    public void setInventory(ArrayList<Entity> inventory) {
        this.inventory = inventory;
    }

    public int getKnockBackPower() {
        return knockBackPower;
    }

    public void setKnockBackPower(int knockBackPower) {
        this.knockBackPower = knockBackPower;
    }

    public BufferedImage getLeft1() {
        return left1;
    }

    public void setLeft1(BufferedImage left1) {
        this.left1 = left1;
    }

    public BufferedImage getLeft2() {
        return left2;
    }

    public void setLeft2(BufferedImage left2) {
        this.left2 = left2;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getLife() {
        return life;
    }

    public void setLife(int life) {
        this.life = life;
    }

    public int getLightRadius() {
        return lightRadius;
    }

    public void setLightRadius(int lightRadius) {
        this.lightRadius = lightRadius;
    }

    public Entity getLinkedEntity() {
        return linkedEntity;
    }

    public void setLinkedEntity(Entity linkedEntity) {
        this.linkedEntity = linkedEntity;
    }

    public Entity getLoot() {
        return loot;
    }

    public int getMana() {
        return mana;
    }

    public void setMana(int mana) {
        this.mana = mana;
    }

    public int getMaxInventorySize() {
        return maxInventorySize;
    }

    public int getMaxLife() {
        return maxLife;
    }

    public void setMaxLife(int maxLife) {
        this.maxLife = maxLife;
    }

    public int getMaxMana() {
        return maxMana;
    }

    public void setMaxMana(int maxMana) {
        this.maxMana = maxMana;
    }

    public int getMotion1_duration() {
        return motion1_duration;
    }

    public void setMotion1_duration(int motion1_duration) {
        this.motion1_duration = motion1_duration;
    }

    public int getMotion2_duration() {
        return motion2_duration;
    }

    public void setMotion2_duration(int motion2_duration) {
        this.motion2_duration = motion2_duration;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNextLevelExp() {
        return nextLevelExp;
    }

    public void setNextLevelExp(int nextLevelExp) {
        this.nextLevelExp = nextLevelExp;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public Projectile getProjectile() {
        return projectile;
    }

    public void setProjectile(Projectile projectile) {
        this.projectile = projectile;
    }

    public BufferedImage getRight1() {
        return right1;
    }

    public void setRight1(BufferedImage right1) {
        this.right1 = right1;
    }

    public BufferedImage getRight2() {
        return right2;
    }

    public void setRight2(BufferedImage right2) {
        this.right2 = right2;
    }

    public Rectangle getSolidArea() {
        return solidArea;
    }

    public void setSolidArea(Rectangle solidArea) {
        this.solidArea = solidArea;
    }

    public int getSolidAreaDefaultX() {
        return solidAreaDefaultX;
    }

    public void setSolidAreaDefaultX(int solidAreaDefaultX) {
        this.solidAreaDefaultX = solidAreaDefaultX;
    }

    public int getSolidAreaDefaultY() {
        return solidAreaDefaultY;
    }

    public void setSolidAreaDefaultY(int solidAreaDefaultY) {
        this.solidAreaDefaultY = solidAreaDefaultY;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getSpriteNum() {
        return spriteNum;
    }

    public void setSpriteNum(int spriteNum) {
        this.spriteNum = spriteNum;
    }

    public boolean isStackable() {
        return stackable;
    }

    public void setStackable(boolean stackable) {
        this.stackable = stackable;
    }

    public EntityState getState() {
        return state;
    }

    public void setState(EntityState state) {
        this.state = state;
    }

    public int getStrength() {
        return strength;
    }

    public void setStrength(int strength) {
        this.strength = strength;
    }

    public boolean isTemp() {
        return temp;
    }

    public void setTemp(boolean temp) {
        this.temp = temp;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getType_axe() {
        return type_axe;
    }

    public int getType_consumable() {
        return type_consumable;
    }

    public int getType_light() {
        return type_light;
    }

    public int getType_monster() {
        return type_monster;
    }

    public int getType_npc() {
        return type_npc;
    }

    public int getType_obstacle() {
        return type_obstacle;
    }

    public int getType_pickaxe() {
        return type_pickaxe;
    }

    public int getType_pickupOnly() {
        return type_pickupOnly;
    }

    public int getType_player() {
        return type_player;
    }

    public int getType_shield() {
        return type_shield;
    }

    public int getType_sword() {
        return type_sword;
    }

    public BufferedImage getUp1() {
        return up1;
    }

    public void setUp1(BufferedImage up1) {
        this.up1 = up1;
    }

    public BufferedImage getUp2() {
        return up2;
    }

    public void setUp2(BufferedImage up2) {
        this.up2 = up2;
    }

    public int getUseCost() {
        return useCost;
    }

    public void setUseCost(int useCost) {
        this.useCost = useCost;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public int getWorldX() {
        return worldX;
    }

    public void setWorldX(int worldX) {
        this.worldX = worldX;
    }

    public int getWorldY() {
        return worldY;
    }

    public void setWorldY(int worldY) {
        this.worldY = worldY;
    }

    public void setLoot(Entity loot) {
        this.loot = loot; // Gán giá trị loot cho thuộc tính loot
        setDialogue(); // Thiết lập hội thoại
    }

}
