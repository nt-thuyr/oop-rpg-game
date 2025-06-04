package entity;

public class CharacterState {

    // STATE (boolean)
    private boolean collisionOn = false;
    private boolean invincible = false;
    private boolean attacking = false;
    private boolean alive = true;
    private boolean dying = false;
    private boolean hpBarOn = false;
    private boolean onPath = false;
    private boolean knockBack = false;
    private String knockBackDirection;
    private boolean guarding = false;
    private boolean transparent = false;
    private boolean offBalance = false;
    private boolean opened = false;
    private boolean inRage = false;
    private boolean sleep = false;
    private boolean drawing = true;
    private boolean temp = false;

    // COUNTER (int)
    private int spriteCounter = 0; // Đếm số lượng khung hình (frames) đã được hiển thị cho sprite (hình ảnh) hiện tại của thực thể.
    private int actionLockCounter = 0; // Quản lý khoảng thời gian giữa các hành động mà một thực thể (thường là NPC hoặc quái vật) thực hiện.
    private int invincibleCounter = 0; // heo dõi thời gian mà một thực thể đang trong trạng thái bất khả xâm phạm (invincible).
    private int shotAvailableCounter = 0; // Quản lý khoảng thời gian giữa các lần bắn (hoặc sử dụng một kỹ năng đặc biệt có thời gian hồi chiêu) của một thực thể có khả năng tấn công từ xa.
    private int dyingCounter = 0; // Theo dõi thời gian diễn ra animation chết của một thực thể.
    private int hpBarCounter = 0; // Quản lý thời gian hiển thị thanh máu của một thực thể (thường là quái vật) sau khi nó bị tấn công.
    private int knockBackCounter = 0; // Theo dõi thời gian mà một thực thể đang bị đẩy lùi (knockback) sau khi bị tấn công.
    private int guardCounter = 0; // Theo dõi thời gian mà người chơi (hoặc một thực thể có khả năng phòng thủ) đang giữ nút phòng thủ.
    private int offBalanceCounter = 0; // Theo dõi thời gian mà một thực thể đang trong trạng thái mất cân bằng (off-balance), thường sau khi bị một đòn tấn công mạnh hoặc bị parry.

    public CharacterState() {
        // Constructor mặc định
    }

    public void resetCounter() {
        spriteCounter = 0;
        actionLockCounter = 0;
        invincibleCounter = 0;
        shotAvailableCounter = 0;
        dyingCounter = 0;
        hpBarCounter = 0;
        knockBackCounter = 0;
        guardCounter = 0;
        offBalanceCounter = 0;
    }

    public void updateSpriteCounter() {
        spriteCounter++;
    }

    public boolean shouldChangeSprite(int interval) {
        return spriteCounter > interval;
    }

    public void resetSpriteCounter() {
        spriteCounter = 0;
    }

    public void updateActionLockCounter() {
        actionLockCounter++;
    }

    public boolean shouldPerformAction(int interval) {
        return actionLockCounter > interval;
    }

    public void resetActionLockCounter() {
        actionLockCounter = 0;
    }

    public void incrementInvincibleCounter() {
        invincibleCounter++;
    }

    public void resetInvincibleCounter() {
        invincibleCounter = 0;
    }

    public void incrementShotAvailableCounter() {
        shotAvailableCounter++;
    }

    public void resetShotAvailableCounter() {
        shotAvailableCounter = 0;
    }

    public void incrementDyingCounter() {
        dyingCounter++;
    }

    public void resetDyingCounter() {
        dyingCounter = 0;
    }

    public void incrementHpBarCounter() {
        hpBarCounter++;
    }

    public void resetHpBarCounter() {
        hpBarCounter = 0;
    }

    public void incrementKnockBackCounter() {
        knockBackCounter++;
    }

    public void resetKnockBackCounter() {
        knockBackCounter = 0;
    }

    public void incrementGuardCounter() {
        guardCounter++;
    }

    public void resetGuardCounter() {
        guardCounter = 0;
    }

    public void incrementOffBalanceCounter() {
        offBalanceCounter++;
    }

    public void resetOffBalanceCounter() {
        offBalanceCounter = 0;
    }

    public int getActionLockCounter() {
        return actionLockCounter;
    }

    public void setActionLockCounter(int actionLockCounter) {
        this.actionLockCounter = actionLockCounter;
    }

    public boolean isAlive() {
        return alive;
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
    }

    public boolean isAttacking() {
        return attacking;
    }

    public void setAttacking(boolean attacking) {
        this.attacking = attacking;
    }

    public boolean isCollisionOn() {
        return collisionOn;
    }

    public void setCollisionOn(boolean collisionOn) {
        this.collisionOn = collisionOn;
    }

    public boolean isDrawing() {
        return drawing;
    }

    public void setDrawing(boolean drawing) {
        this.drawing = drawing;
    }

    public boolean isDying() {
        return dying;
    }

    public void setDying(boolean dying) {
        this.dying = dying;
    }

    public int getDyingCounter() {
        return dyingCounter;
    }

    public void setDyingCounter(int dyingCounter) {
        this.dyingCounter = dyingCounter;
    }

    public int getGuardCounter() {
        return guardCounter;
    }

    public void setGuardCounter(int guardCounter) {
        this.guardCounter = guardCounter;
    }

    public boolean isGuarding() {
        return guarding;
    }

    public void setGuarding(boolean guarding) {
        this.guarding = guarding;
    }

    public int getHpBarCounter() {
        return hpBarCounter;
    }

    public void setHpBarCounter(int hpBarCounter) {
        this.hpBarCounter = hpBarCounter;
    }

    public boolean isHpBarOn() {
        return hpBarOn;
    }

    public void setHpBarOn(boolean hpBarOn) {
        this.hpBarOn = hpBarOn;
    }

    public boolean isInRage() {
        return inRage;
    }

    public void setInRage(boolean inRage) {
        this.inRage = inRage;
    }

    public boolean isInvincible() {
        return invincible;
    }

    public void setInvincible(boolean invincible) {
        this.invincible = invincible;
    }

    public int getInvincibleCounter() {
        return invincibleCounter;
    }

    public void setInvincibleCounter(int invincibleCounter) {
        this.invincibleCounter = invincibleCounter;
    }

    public boolean isKnockBack() {
        return knockBack;
    }

    public void setKnockBack(boolean knockBack) {
        this.knockBack = knockBack;
    }

    public int getKnockBackCounter() {
        return knockBackCounter;
    }

    public void setKnockBackCounter(int knockBackCounter) {
        this.knockBackCounter = knockBackCounter;
    }

    public boolean isOffBalance() {
        return offBalance;
    }

    public void setOffBalance(boolean offBalance) {
        this.offBalance = offBalance;
    }

    public int getOffBalanceCounter() {
        return offBalanceCounter;
    }

    public void setOffBalanceCounter(int offBalanceCounter) {
        this.offBalanceCounter = offBalanceCounter;
    }

    public boolean isOnPath() {
        return onPath;
    }

    public void setOnPath(boolean onPath) {
        this.onPath = onPath;
    }

    public boolean isOpened() {
        return opened;
    }

    public void setOpened(boolean opened) {
        this.opened = opened;
    }

    public int getShotAvailableCounter() {
        return shotAvailableCounter;
    }

    public void setShotAvailableCounter(int shotAvailableCounter) {
        this.shotAvailableCounter = shotAvailableCounter;
    }

    public boolean isSleep() {
        return sleep;
    }

    public void setSleep(boolean sleep) {
        this.sleep = sleep;
    }

    public int getSpriteCounter() {
        return spriteCounter;
    }

    public void setSpriteCounter(int spriteCounter) {
        this.spriteCounter = spriteCounter;
    }

    public boolean isTemp() {
        return temp;
    }

    public void setTemp(boolean temp) {
        this.temp = temp;
    }

    public boolean isTransparent() {
        return transparent;
    }

    public void setTransparent(boolean transparent) {
        this.transparent = transparent;
    }

    public String getKnockBackDirection() {
        return knockBackDirection;
    }

    public void setKnockBackDirection(String knockBackDirection) {
        this.knockBackDirection = knockBackDirection;
    }
}