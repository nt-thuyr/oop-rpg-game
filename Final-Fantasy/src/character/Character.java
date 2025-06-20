package character;

import main.GamePanel;
import item.Item;
import main.UtilityTool;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;

public class Character {
    private BufferedImage up1, up2, down1, down2, left1, left2, right1, right2;
    private BufferedImage attackUp1, attackUp2, attackDown1, attackDown2, attackLeft1, attackLeft2, attackRight1, attackRight2;

    private Character attacker;

    // STATE
    private String direction = "down";
    private int spriteNum = 1;
    private CharacterState state = new CharacterState();

    // CHARACTER ATTRIBUTES
    private String name;
    private int defaultSpeed;
    private int speed;
    private int maxLife;
    private int life;
    private int ammo;
    private int level;
    private int strength;
    private int dexterity;
    private int attack;
    private int defense;
    private int exp;
    private int nextLevelExp;
    private int coin;
    private Item currentWeapon;
    private Item currentShield;
    private Projectile projectile;
    private boolean boss;

    // INVENTORY ATTRIBUTES
    private final ArrayList<Item> inventory = new ArrayList<>();
    private final int maxInventorySize = 20;
    private int knockBackPower;

    // TYPE
    private int type;
    private final int type_monster = 2;

    protected GamePanel gp;
    private int worldX, worldY; // Toạ độ trên thế giới
    private int dialogueSet = 0; // Xác định tập hội thoại
    private int dialogueIndex = 0; // Xác định đoạ hội thoại
    protected String[][] dialogues = new String[20][20]; // Lưu trữ các đoạn hội thoại
    private int motion1_duration; // Thời gian thực hiển chuyển động đầu tiên
    private int motion2_duration; // Thời gian thực hiện chuyển động thứ hai
    private Rectangle solidArea = new Rectangle(0, 0, 48, 48); // Khu vực va chạm mặc định
    private int solidAreaDefaultX, solidAreaDefaultY; // Toạ độ mặc định của khu vực va chạm

    private Rectangle attackArea = new Rectangle(0, 0, 0, 0); // Khu vực tấn công của thực thể


    public Character(GamePanel gp) {
        this.gp = gp;
    }

    public int getScreenX() {
        int screenX = getWorldX() - gp.getPlayer().getWorldX() + gp.getPlayer().getScreenX();
        return screenX;
    }

    public int getScreenY() {
        int screenY = getWorldY() - gp.getPlayer().getWorldY() + gp.getPlayer().getScreenY();
        return screenY;
    }

    public int getLeftX() {
        return getWorldX() + getSolidArea().x;
    }

    public int getRightX() {
        return getWorldX() + getSolidArea().width + getSolidArea().width;
    }

    public int getTopY() {
        return getWorldY() + getSolidArea().y;
    }

    public int getBottomY() {
        return getWorldY() + getSolidArea().y + getSolidArea().height;
    }

    public int getCenterX() {
        int centerX = getWorldX() + left1.getWidth() / 2;
        return centerX;
    }

    public int getCenterY() {
        int centerY = getWorldY() + up1.getWidth() / 2;
        return centerY;
    }

    public int getXdistance(Character target) {
        int xDistance = Math.abs(getCenterX() - target.getCenterX());
        return xDistance;
    }

    public int getYdistance(Character target) {
        int yDistance = Math.abs(getCenterY() - target.getCenterY());
        return yDistance;
    }

    public int getTileDistance(Character target) {
        int tileDistance = (getXdistance(target) + getYdistance(target)) / gp.getTileSize();
        return tileDistance;
    }

    public int getGoalCol(Character target) {
        int goalCol = (target.getWorldX() + target.getSolidArea().x) / gp.getTileSize();
        return goalCol;

    }

    public int getGoalRow(Character target) {
        int goalRow = (target.getWorldY() + target.getSolidArea().y) / gp.getTileSize();
        return goalRow;
    }

    public void setAction() {

    }

    public void move(String direction) {

    }

    public void damageReaction() {

    }

    public void speak() {

    }

    public void facePlayer() {
        switch (gp.getPlayer().getDirection()) {
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

    public void checkDrop() {
    }

    public void dropItem(Item droppedItem) {
        for (int i = 0; i < gp.getObj()[1].length; i++) {
            if (gp.getObj()[gp.getCurrentMap()][i] == null) {
                gp.getObj()[gp.getCurrentMap()][i] = droppedItem;
                droppedItem.setWorldX(this.getWorldX());  // the dead monster's worldX
                droppedItem.setWorldY(this.getWorldY());  // the dead monster's worldY
                break; //end loop after finding empty slot on array
            }
        }
    }

    public Color getParticleColor() {
        Color color = null;
        return color;
    }

    public int getParticleSize() {
        int size = 0; //pixels
        return size;
    }

    public int getParticleSpeed() {
        int speed = 0;
        return speed;
    }

    public int getParticleMaxLife() {
        int maxLife = 0;
        return maxLife;
    }

    public void generateParticle(Character generator, Character target) {
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
        boolean contactPlayer = gp.getcChecker().checkPlayer(this);
        if (getType() == getType_monster() && contactPlayer) {
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

        if (targetInRange) {
            int i = new Random().nextInt(rate);
            if (i == 0) {
                getState().setAttacking(true);
                setSpriteNum(1);
                getState().setSpriteCounter(0);
                getState().setShotAvailableCounter(0);
            }
        }
    }

    public void checkShootOrNot(int rate, int shotInterval) {
        int i = new Random().nextInt(rate);
        if (i == 0 && !projectile.getState().isAlive() && state.getShotAvailableCounter() == shotInterval) {
            projectile.set(getWorldX(), getWorldY(), direction, true, this);
            //gp.projectileList.add(projectile);
            //CHECK VACANCY
            for (int ii = 0; ii < gp.getProjectile()[1].length; ii++) {
                if (gp.getProjectile()[gp.getCurrentMap()][ii] == null) {
                    gp.getProjectile()[gp.getCurrentMap()][ii] = projectile;
                    break;
                }
            }
            state.setShotAvailableCounter(0);
        }
    }

    public void checkStartChasingOrNot(Character target, int distance, int rate) {
        if (getTileDistance(target) < distance) {
            int i = new Random().nextInt(rate);
            if (i == 0) {
                state.setOnPath(true);
            }
        }
    }

    public void checkStopChasingOrNot(Character target, int distance, int rate) {
        if (getTileDistance(target) > distance) {
            int i = new Random().nextInt(rate);
            if (i == 0) {
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
            gp.playSE(6); // receivedamage.wav
            if (damage < 1) {
                damage = 1;
            }
            gp.getPlayer().getState().setTransparent(true);
            setKnockBack(gp.getPlayer(), this, getKnockBackPower());

            // Apply damage
            gp.getPlayer().setLife(gp.getPlayer().getLife() - damage);
            gp.getPlayer().getState().setInvincible(true);
        }
    }

    public void setKnockBack(Character target, Character attacker, int knockBackPower) {
        this.attacker = attacker;
        target.getState().setKnockBackDirection(attacker.getDirection());
        target.setSpeed(target.getSpeed() + knockBackPower);
        target.getState().setKnockBack(true);
    }
    public void draw(Graphics2D g2) {
        BufferedImage image = null;

        if (inCamera()) {
            int tempScreenX = getScreenX();
            int tempScreenY = getScreenY();

            switch (getDirection()) {
                case "up":
                    if (!getState().isAttacking()) {
                        image = getSpriteNum() == 1 ? getUp1() : getUp2();
                    } else {
                        tempScreenY = getScreenY() - getUp1().getHeight();
                        image = getSpriteNum() == 1 ? getAttackUp1() : getAttackUp2();
                    }
                    break;
                case "down":
                    if (!getState().isAttacking()) {
                        image = getSpriteNum() == 1 ? getDown1() : getDown2();
                    } else {
                        image = getSpriteNum() == 1 ? getAttackDown1() : getAttackDown2();
                    }
                    break;
                case "left":
                    if (!getState().isAttacking()) {
                        image = getSpriteNum() == 1 ? getLeft1() : getLeft2();
                    } else {
                        tempScreenX = getScreenX() - getUp1().getWidth();
                        image = getSpriteNum() == 1 ? getAttackLeft1() : getAttackLeft2();
                    }
                    break;
                case "right":
                    if (!getState().isAttacking()) {
                        image = getSpriteNum() == 1 ? getRight1() : getRight2();
                    } else {
                        image = getSpriteNum() == 1 ? getAttackRight1() : getAttackRight2();
                    }
                    break;
            }

            if (getState().isInvincible()) {
                getState().setHpBarOn(true);
                getState().setHpBarCounter(0);
                changeAlpha(g2, 0.4F);
            }

            if (getState().isDying()) {
                dyingAnimation(g2);
            }

            g2.drawImage(image, tempScreenX, tempScreenY, null);
            changeAlpha(g2, 1F);
        }
    }

    // Every 5 frames switch alpha between 0 and 1
    public void dyingAnimation(Graphics2D g2) {
        getState().setDyingCounter(getState().getDyingCounter() + 1);
        int i = 5; // interval
        int totalFrames = i * 8; // Tổng số khung hình cho hoạt hình

        if (getState().getDyingCounter() <= totalFrames) {
            // Chuyển đổi alpha: 0 nếu dyingCounter / i là số lẻ, 1 nếu là số chẵn
            float alpha = ((getState().getDyingCounter() - 1) / i) % 2 == 0 ? 0f : 1f;
            changeAlpha(g2, alpha);
        } else {
            getState().setAlive(false);
        }
    }

    public void changeAlpha(Graphics2D g2, float alphaValue) {
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alphaValue));
    }

    public void searchPath(int goalCol, int goalRow) {
        int startCol = (getWorldX() + getSolidArea().x) / gp.getTileSize();
        int startRow = (getWorldY() + getSolidArea().y) / gp.getTileSize();
        gp.getpFinder().setNodes(startCol, startRow, goalCol, goalRow, this);
        if (gp.getpFinder().search()) {
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
                if (getState().isCollisionOn()) {
                    setDirection("left");
                }
            } else if (enTopY > nextY && enLeftX < nextX) {
                // up or right
                setDirection("up");
                checkCollision();
                if (getState().isCollisionOn()) {
                    setDirection("right");
                }
            } else if (enTopY < nextY && enLeftX > nextX) {
                // down or left
                setDirection("down");
                checkCollision();
                if (getState().isCollisionOn()) {
                    setDirection("left");
                }
            } else if (enTopY < nextY && enLeftX < nextX) {
                // down or right
                setDirection("down");
                checkCollision();
                if (getState().isCollisionOn()) {
                    setDirection("right");
                }
            }
        }
    }

    public boolean inCamera() { // Kiểm tra xem thực thể có nằm trong phạm vi hiển thị hay không
        boolean inCamera = getWorldX() + gp.getTileSize() * 5 > gp.getPlayer().getWorldX() - gp.getPlayer().getScreenX() && //*5 because skeleton lord disappears when the top left corner isn't on the screen
                getWorldX() - gp.getTileSize() < gp.getPlayer().getWorldX() + gp.getPlayer().getScreenX() &&
                getWorldY() + gp.getTileSize() * 5 > gp.getPlayer().getWorldY() - gp.getPlayer().getScreenY() &&
                getWorldY() - gp.getTileSize() < gp.getPlayer().getWorldY() + gp.getPlayer().getScreenY();
        return inCamera;
    }

    public BufferedImage setup(String imagePath, int width, int height) { // Tải và thiết lập hình ảnh cho thực thể
        UtilityTool uTool = new UtilityTool();
        BufferedImage image = null;

        try {
            image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(imagePath + ".png")));
            image = uTool.scaleImage(image, width, height); // it scales to tilesize, will fix for player attack(16px x 32px) by adding width and height
        } catch (IOException e) {
            e.printStackTrace();
        }
        return image;
    }

    public void startDialogue(Character character, int setNum) { // Bắt đầu đoạn hội thoại với thực thể được chỉ định
        gp.setGameState(gp.getDialogueState());
        gp.getUi().setDialogueCharacter(character); // Always set the dialogue entity
        setDialogueSet(setNum);
    }

    public void setDialogue() { // thiết lập hội thoại
        dialogues[0][0] = "No dialogue set.";
    }

    public int getAmmo() {
        return ammo;
    }

    public void setAmmo(int ammo) {
        this.ammo = ammo;
    }

    public int getAttack() {
        return attack;
    }

    public int setAttack(int attack) {
        this.attack = attack;
        return attack;
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

    public Character getAttacker() {
        return attacker;
    }

    public void setAttacker(Character attacker) {
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

    public Item getCurrentShield() {
        return currentShield;
    }

    public void setCurrentShield(Item currentShield) {
        this.currentShield = currentShield;
    }

    public Item getCurrentWeapon() {
        return currentWeapon;
    }

    public void setCurrentWeapon(Item currentWeapon) {
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

    public int getDexterity() {
        return dexterity;
    }

    public void setDexterity(int dexterity) {
        this.dexterity = dexterity;
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

    public ArrayList<Item> getInventory() {
        return inventory;
    }

public void addToInventory(Item item) {
    if (item.isStackable()) {
        for (Item invItem : inventory) {
            if (invItem.getName().equals(item.getName())) {
                invItem.setAmount(invItem.getAmount() + item.getAmount());
                return;
            }
        }
    }
    if (inventory.size() < maxInventorySize) {
        inventory.add(item);
    }
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

    public int getMaxInventorySize() {
        return maxInventorySize;
    }

    public int getMaxLife() {
        return maxLife;
    }

    public void setMaxLife(int maxLife) {
        this.maxLife = maxLife;
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


    public CharacterState getState() {
        return state;
    }

    public void setState(CharacterState state) {
        this.state = state;
    }

    public int getStrength() {
        return strength;
    }

    public void setStrength(int strength) {
        this.strength = strength;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getType_monster() {
        return type_monster;
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

    public Rectangle getAttackArea() {
        return attackArea;
    }

    public void setAttackArea(Rectangle attackArea) {
        this.attackArea = attackArea;
    }

    public String[][] getDialogues() {
        return dialogues;
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

    public int getDialogueIndex() {
        return dialogueIndex;
    }

    public void setDialogueIndex(int dialogueIndex) {
        this.dialogueIndex = dialogueIndex;
    }

    public int getDialogueSet() {
        return dialogueSet;
    }

    public void setDialogueSet(int dialogueSet) {
        this.dialogueSet = dialogueSet;
    }
}