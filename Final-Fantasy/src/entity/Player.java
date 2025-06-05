package entity;

import main.GamePanel;
import main.KeyHandler;
import item.*;
import tile_interactive.InteractiveTile;

import java.awt.*;
import java.awt.image.BufferedImage;


public class Player extends Character {

    private KeyHandler keyH;
    private final int screenX;
    private final int screenY;
    private int standCounter = 0;
    private boolean attackCanceled = false;

    public Player(GamePanel gp, KeyHandler keyH) {
        super(gp);
        this.keyH = keyH;

        screenX = gp.getScreenWidth() / 2 - (gp.getTileSize() / 2);
        screenY = gp.getScreenHeight() / 2 - (gp.getTileSize() / 2);

        setSolidArea(new Rectangle(8, 16, 32, 32));
        setSolidAreaDefaultX(8);
        setSolidAreaDefaultY(16);

        setDefaultValues();
    }

    public void setDefaultValues()
    {
        setWorldX(gp.getTileSize() * 23);
        setWorldY(gp.getTileSize() * 21);
        gp.setCurrentMap(0);
        gp.setCurrentArea(gp.getOutside());

        setDefaultSpeed(4);
        setSpeed(getDefaultSpeed());
        setDirection("down");

        setLevel(20);
        setMaxLife(10);
        setLife(getMaxLife());
        setMaxMana(8);

        setAmmo(10);
        setStrength(20);
        setDexterity(20);
        setExp(0);
        setNextLevelExp(4);
        setCoin(40);
        getState().setInvincible(false);
        setCurrentWeapon(new SwordNormal(gp));
        setCurrentShield(new ShieldWood(gp));

        setAttack(getAttack());   // The total attack value is decided by strength and weapon
        setDefense(getDefense()); // The total defense value is decided by dexterity and shield

        getImage();
        getAttackImage();
        setItems();
    }
    public void setDefaultPositions() {
        gp.setCurrentMap(0);
        setWorldX(gp.getTileSize() * 23);
        setWorldY(gp.getTileSize() * 21);
        setDirection("down");
    }
    public void setDialogue() {
        String[][] dialogues = getDialogues();
        dialogues[0][0] = "Bạn đã đạt cấp " + getLevel() + " rồi!\n" + "Thấy mạnh mẽ hơn chưa!";
    }

    public void restoreStatus()
    {
        setLife(getMaxLife());
        setMana(getMaxMana());
        setSpeed(getDefaultSpeed());
        getState().setInvincible(false);
        getState().setTransparent(false);
        getState().setAttacking(false);
        getState().setGuarding(false);
        getState().setKnockBack(false);
    }

    public void setItems()
    {
        getInventory().clear();
        getInventory().add(getCurrentWeapon());
        getInventory().add(getCurrentShield());

    }

    public int getAttack() {
        setAttackArea(getCurrentWeapon().getAttackArea());
        setMotion1_duration(getCurrentWeapon().getMotion1_duration());
        setMotion2_duration(getCurrentWeapon().getMotion2_duration());
        return setAttack(getStrength() * getCurrentWeapon().getAttackValue());
    }

    public int getDefense() {
        return setDefense(getDexterity() * getCurrentShield().getDefenseValue());
    }

    public int getCurrentWeaponSlot() {
        int currentWeaponSlot = 0;
        for (int i = 0; i < getInventory().size(); i++) {
            if (getInventory().get(i) == getCurrentWeapon()) {
                currentWeaponSlot = i;
            }
        }
        return currentWeaponSlot;
    }

    public int getCurrentShieldSlot() {
        int currentShieldSlot = 0;
        for (int i = 0; i < getInventory().size(); i++) {
            if (getInventory().get(i) == getCurrentShield()) {
                currentShieldSlot = i;
            }
        }
        return currentShieldSlot;
    }

    public void getImage() {
        setUp1(setup("/player/boy_up_1", gp.getTileSize(), gp.getTileSize()));
        setUp2(setup("/player/boy_up_2", gp.getTileSize(), gp.getTileSize()));
        setDown1(setup("/player/boy_down_1", gp.getTileSize(), gp.getTileSize()));
        setDown2(setup("/player/boy_down_2", gp.getTileSize(), gp.getTileSize()));
        setLeft1(setup("/player/boy_left_1", gp.getTileSize(), gp.getTileSize()));
        setLeft2(setup("/player/boy_left_2", gp.getTileSize(), gp.getTileSize()));
        setRight1(setup("/player/boy_right_1", gp.getTileSize(), gp.getTileSize()));
        setRight2(setup("/player/boy_right_2", gp.getTileSize(), gp.getTileSize()));
    }
    public void getSleepingImage(BufferedImage image) {
        setUp1(image);
        setUp2(image);
        setDown1(image);
        setDown2(image);
        setLeft1(image);
        setLeft2(image);
        setRight1(image);
        setRight2(image);
    }
    public void getAttackImage() {
        if (getCurrentWeapon().getType() == Item.getType_sword()) {
            setAttackUp1(setup("/player/boy_attack_up_1", gp.getTileSize(), gp.getTileSize() * 2));         // 16x32 px
            setAttackUp2(setup("/player/boy_attack_up_2", gp.getTileSize(), gp.getTileSize() * 2));         // 16x32 px
            setAttackDown1(setup("/player/boy_attack_down_1", gp.getTileSize(), gp.getTileSize() * 2));     // 16x32 px
            setAttackDown2(setup("/player/boy_attack_down_2", gp.getTileSize(), gp.getTileSize() * 2));     // 16x32 px
            setAttackLeft1(setup("/player/boy_attack_left_1", gp.getTileSize() * 2, gp.getTileSize()));      // 32x16 px
            setAttackLeft2(setup("/player/boy_attack_left_2", gp.getTileSize() * 2, gp.getTileSize()));      // 32x16 px
            setAttackRight1(setup("/player/boy_attack_right_1", gp.getTileSize() * 2, gp.getTileSize()));    // 32x16 px
            setAttackRight2(setup("/player/boy_attack_right_2", gp.getTileSize() * 2, gp.getTileSize()));    // 32x16 px
        }
    }

    public void update() // Runs 60 times every second.
    {
        if (getState().isKnockBack()) {

            getState().setCollisionOn(false);
            if (isMoving() || keyH.isEnterPressed()) {
                gp.getcChecker().checkTile(this);
                gp.getcChecker().checkObject(this, true);
                gp.getcChecker().checkEntity(this, gp.getNpc());
                gp.getcChecker().checkEntity(this, gp.getMonster());
                gp.getcChecker().checkEntity(this, gp.getiTile());
            }

            if (getState().isCollisionOn()) {
                getState().resetKnockBackCounter();
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
            getState().incrementKnockBackCounter();
            if (getState().getKnockBackCounter() == 10) {
                getState().resetKnockBackCounter();
                getState().setKnockBack(false);
                setSpeed(getDefaultSpeed());
            }
        } else if (getState().isAttacking()) {
            attacking();
        } else if (keyH.isSpacePressed()) {
            getState().setGuarding(true);
            getState().incrementGuardCounter();
        } else if (keyH.isUpPressed() || keyH.isDownPressed() || keyH.isLeftPressed() || keyH.isRightPressed() || keyH.isEnterPressed()) {
            if (keyH.isUpPressed()) {
                setDirection("up");
            } else if (keyH.isDownPressed()) {
                setDirection("down");
            } else if (keyH.isLeftPressed()) {
                setDirection("left");
            } else if (keyH.isRightPressed()) {
                setDirection("right");
            }

            getState().setCollisionOn(false);
            gp.getcChecker().checkTile(this);

            int objIndex = gp.getcChecker().checkObject(this, true);
            pickUpObject(objIndex);

            int npcIndex = gp.getcChecker().checkEntity(this, gp.getNpc());
            interactNPC(npcIndex);

            int monsterIndex = gp.getcChecker().checkEntity(this, gp.getMonster());
            contactMonster(monsterIndex);

            int iTileIndex = gp.getcChecker().checkEntity(this, gp.getiTile());

            if (isMoving() || keyH.isEnterPressed()) {
                gp.geteHandler().checkEvent();
            }

            if (!getState().isCollisionOn() && !keyH.isEnterPressed()) {
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

            if (keyH.isEnterPressed() && !isAttackCanceled()) {
                gp.playSE(7);
                getState().setAttacking(true);
                getState().resetSpriteCounter();
            }

            setAttackCanceled(false);
            keyH.setEnterPressed(false);
            getState().setGuarding(false);
            getState().resetGuardCounter();

            getState().setSpriteCounter(getState().getSpriteCounter() + 1);
            if (getState().getSpriteCounter() > 12) {
                if (getSpriteNum() == 1) {
                    setSpriteNum(2);
                } else if (getSpriteNum() == 2) {
                    setSpriteNum(1);
                }
                getState().resetSpriteCounter();
            }
        } else {
            setStandCounter(getStandCounter() + 1);
            if (getStandCounter() == 20) {
                setSpriteNum(1);
                setStandCounter(0);
            }
            getState().setGuarding(false);
            getState().setGuardCounter(0);
        }

        if (gp.getKeyH().isShotKeyPressed() && !getProjectile().getState().isAlive() && getState().getShotAvailableCounter() == 30 && getProjectile().haveResource(this)) {
            getProjectile().set(getWorldX(), getWorldY(), getDirection(), true, this);
            getProjectile().subtractResource(this);

            for (int i = 0; i < gp.getProjectile()[1].length; i++) {
                if (gp.getProjectile()[gp.getCurrentMap()][i] == null) {
                    gp.getProjectile()[gp.getCurrentMap()][i] = getProjectile();
                    break;
                }
            }

            getState().resetShotAvailableCounter();
            gp.playSE(10);
        }

        if (getState().isInvincible()) {
            getState().incrementInvincibleCounter();
            if (getState().getInvincibleCounter() > 60) {
                getState().setInvincible(false);
                getState().setTransparent(false);
                getState().resetInvincibleCounter();
            }
        }

        if (getState().getShotAvailableCounter() < 30) {
            getState().incrementShotAvailableCounter();
        }
        if (getLife() > getMaxLife()) {
            setLife(getMaxLife());
        }
        if (getMana() > getMaxMana()) {
            setMana(getMaxMana());
        }
        if (!keyH.isGodModeOn()) {
            if (getLife() <= 0) {
                gp.setGameState(gp.getGameOverState());
                gp.getUi().setCommandNum(-1);
                gp.stopMusic();
                gp.playSE(12);
            }
        }
    }

    public boolean isMoving() {

        return keyH.isUpPressed() || keyH.isDownPressed() || keyH.isLeftPressed() || keyH.isRightPressed();
    }


    public void pickUpObject(int i) {
        if (i != 999) {
            // PICKUP ONLY ITEMS
            if (gp.getObj()[gp.getCurrentMap()][i].getType() == Item.getType_pickupOnly()) {
                gp.getObj()[gp.getCurrentMap()][i].use(this);
                gp.getObj()[gp.getCurrentMap()][i] = null;
            }
            // OBSTACLE
            else if (gp.getObj()[gp.getCurrentMap()][i].getType() == Item.getType_obstacle()) {
                if (keyH.isEnterPressed()) {
                    setAttackCanceled(true);
                    gp.getObj()[gp.getCurrentMap()][i].interact();
                }
            }
            // INVENTORY ITEMS
            else {
                String text;
                if (canObtainItem(gp.getObj()[gp.getCurrentMap()][i])) { // If inventory is not full, pick up object
                    gp.playSE(1);
                    text = "Nhận được " + gp.getObj()[gp.getCurrentMap()][i].getName() + "!";
                } else {
                    text = "Không thể mang thêm nữa";
                }
                gp.getUi().addMessage(text);
                gp.getObj()[gp.getCurrentMap()][i] = null;
            }
        }
    }
    public void interactNPC(int i)
    {
        if(i != 999)
        {
            if(gp.getKeyH().isEnterPressed())
            {
                attackCanceled = true;
                gp.getNpc()[gp.getCurrentMap()][i].speak();
            }

            gp.getNpc()[gp.getCurrentMap()][i].move(getDirection());
        }
    }
    public void contactMonster(int i) // CollisionChecker Method Implement //checkPlayer() : Checks who touches to player //checkEntity() : Checks if player touches to an entity;
    {
        if (i != 999) {
            if (!getState().isInvincible() && !gp.getMonster()[gp.getCurrentMap()][i].getState().isDying()) {
                gp.playSE(6);  //

                int damage = gp.getMonster()[gp.getCurrentMap()][i].getAttack() - getDefense();
                if (damage < 1) {
                    damage = 1;
                }
                setLife(getLife() - damage);
                getState().setInvincible(true);
                getState().setTransparent(true);
            }
        }
    }
    public void damageMonster(int i, Character attacker, int attack, int knockBackPower) {
        if (i != 999) {
            Character monster = gp.getMonster()[gp.getCurrentMap()][i];
            if (!monster.getState().isInvincible()) {
                gp.playSE(5); // hitmonster.wav

                if (knockBackPower > 0) {
                    setKnockBack(monster, attacker, knockBackPower);
                }
                if (monster.getState().isOffBalance()) {
                    attack *= 2;
                }
                int damage = attack - monster.getDefense();
                if (damage <= 0) {
                    damage = 1;
                }
                monster.setLife(monster.getLife() - damage);
                gp.getUi().addMessage(damage + " damage!");
                monster.getState().setInvincible(true);
                monster.damageReaction(); // run away from player

                if (monster.getLife() <= 0) {
                    monster.getState().setDying(true);
                    gp.getUi().addMessage("Tiêu diệt " + monster.getName() + "!");
                    gp.getUi().addMessage("Exp +" + monster.getExp() + "!");
                    setExp(getExp() + monster.getExp());
                    checkLevelUp();
                }
            }
        }
    }

    public void damageInteractiveTile(int i) {
        if (i != 999 && gp.getiTile()[gp.getCurrentMap()][i] != null) {
            InteractiveTile tile = gp.getiTile()[gp.getCurrentMap()][i];

            if (tile.isDestructible() && tile.isCorrectItem(this) && !tile.getState().isInvincible()) {
                tile.playSE();
                tile.setLife(tile.getLife() - 1);
                tile.getState().setInvincible(true);

                // Generate Particle
                generateParticle(tile, tile);

                if (tile.getLife() == 0) {
                    gp.getiTile()[gp.getCurrentMap()][i] = tile.getDestroyedForm();
                }
            }
        }
    }

    public void damageProjectile(int i) {
        if (i != 999) {
            Character projectile = gp.getProjectile()[gp.getCurrentMap()][i];
            projectile.getState().setAlive(false);
            generateParticle(projectile, projectile);
        }
    }
    public void checkLevelUp() {
        while (getExp() >= getNextLevelExp()) {
            setLevel(getLevel() + 1);
            setExp(getExp() - getNextLevelExp()); // Example: Your exp is 4 and nextLevelExp is 5. You killed a monster and receive 2exp. So, your exp is now 6. Your 1 extra xp will be recovered for the next level.

            if (getLevel() <= 4) {
                setNextLevelExp(getNextLevelExp() + 4); // Level 2 to 6: 4xp- 8xp- 12xp- 16xp- 20xp
            } else {
                setNextLevelExp(getNextLevelExp() + 8); // After Level 6: 28xp- 36xp- 44xp- 52xp- 60xp
            }

            setMaxLife(getMaxLife() + 2);
            setStrength(getStrength() + 1);
            setDexterity(getDexterity() + 1);
            setAttack(getAttack());
            setDefense(getDefense());
            gp.playSE(8);

            String[][] dialogues = getDialogues();
            dialogues[0][0] = "Bạn đã đạt cấp " + getLevel() + " rồi!\n" + "Thấy mạnh mẽ hơn chưa!";
            setDialogue();
            startDialogue(this, 0);
        }
    }
    public void selectItem() {
        int itemIndex = gp.getUi().getItemIndexOnSlot(gp.getUi().getPlayerSlotCol(), gp.getUi().getPlayerSlotRow());
        if (itemIndex < getInventory().size()) {
            Item selectedItem = getInventory().get(itemIndex);

            if (selectedItem.getType() == Item.getType_sword()) {
                setCurrentWeapon(selectedItem);
                setAttack(getAttack()); // Update player attack
                getAttackImage(); // Update player attack image (sword/axe)
            }
            if (selectedItem.getType() == Item.getType_shield()) {
                setCurrentShield(selectedItem);
                setDefense(getDefense()); // Update player defense
            }
            if (selectedItem.getType() == Item.getType_consumable()) {
                if (selectedItem.use(this)) {
                    if (selectedItem.getAmount() > 1) {
                        selectedItem.setAmount(selectedItem.getAmount() - 1);
                    } else {
                        getInventory().remove(itemIndex);
                    }
                }
            }
        }
    }
    public int searchItemInInventory(String itemName) {
        int itemIndex = 999;
        for (int i = 0; i < getInventory().size(); i++) {
            if (getInventory().get(i).getName().equals(itemName)) {
                itemIndex = i;
                break;
            }
        }
        return itemIndex;
    }
    public boolean canObtainItem(Item item) {
        boolean canObtain = false;

        Item newItem = gp.geteGenerator().getObject(item.getName());

        // CHECK IF STACKABLE
        if (newItem.isStackable()) {
            int index = searchItemInInventory(newItem.getName());

            if (index != 999) {
                getInventory().get(index).setAmount(getInventory().get(index).getAmount() + 1);
                canObtain = true;
            } else {
                // New item, so need to check vacancy
                if (getInventory().size() != getMaxInventorySize()) {
                    getInventory().add(newItem);
                    canObtain = true;
                }
            }
        }
        // NOT STACKABLE so check vacancy
        else {
            if (getInventory().size() != getMaxInventorySize()) {
                getInventory().add(newItem);
                canObtain = true;
            }
        }
        return canObtain;
    }


    public void draw(Graphics2D g2) {
        BufferedImage image = null;
        int tempScreenX = getScreenX();
        int tempScreenY = getScreenY();

        switch (getDirection()) {
            case "up":
                if (!getState().isAttacking()) { // Normal walking sprites
                    image = (getSpriteNum() == 1) ? getUp1() : getUp2();
                } else { // Attacking sprites
                    tempScreenY -= gp.getTileSize(); // Adjusted position
                    image = (getSpriteNum() == 1) ? getAttackUp1() : getAttackUp2();
                }
                break;

            case "down":
                if (!getState().isAttacking()) { // Normal walking sprites
                    image = (getSpriteNum() == 1) ? getDown1() : getDown2();
                } else { // Attacking sprites
                    image = (getSpriteNum() == 1) ? getAttackDown1() : getAttackDown2();
                }
                break;

            case "left":
                if (!getState().isAttacking()) { // Normal walking sprites
                    image = (getSpriteNum() == 1) ? getLeft1() : getLeft2();
                } else { // Attacking sprites
                    tempScreenX -= gp.getTileSize(); // Adjusted position
                    image = (getSpriteNum() == 1) ? getAttackLeft1() : getAttackLeft2();
                }
                break;

            case "right":
                if (!getState().isAttacking()) { // Normal walking sprites
                    image = (getSpriteNum() == 1) ? getRight1() : getRight2();
                } else { // Attacking sprites
                    image = (getSpriteNum() == 1) ? getAttackRight1() : getAttackRight2();
                }
                break;
        }

        // Make player half-transparent (40%) when invincible
        if (getState().isTransparent()) {
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.4f));
        }
        if (getState().isDrawing()) { // For boss cutscene making player invisible to move camera
            g2.drawImage(image, tempScreenX, tempScreenY, null);
        }

        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
    }

    public boolean isAttackCanceled() {
        return attackCanceled;
    }

    public void setAttackCanceled(boolean attackCanceled) {
        this.attackCanceled = attackCanceled;
    }

    public KeyHandler getKeyH() {
        return keyH;
    }

    public void setKeyH(KeyHandler keyH) {
        this.keyH = keyH;
    }

    @Override
    public int getScreenX() {
        return screenX;
    }

    @Override
    public int getScreenY() {
        return screenY;
    }

    public int getStandCounter() {
        return standCounter;
    }

    public void setStandCounter(int standCounter) {
        this.standCounter = standCounter;
    }
}
