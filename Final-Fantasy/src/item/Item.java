package item;

import entity.Character;
import entity.Entity;
import main.GamePanel;
import java.awt.image.BufferedImage;
import java.awt.Graphics2D;

public abstract class Item extends Entity {
    private BufferedImage image;
    private String name;
    private String description;
    private int price;
    private boolean stackable;
    private Item loot;
    private int amount; // For stackable items, like potions
    private int value; // Value for pickup items, like hearts
    private boolean temp = false;

    private int attackValue; // For weapons
    private int knockBackPower; // For weapons
    private int defenseValue; // For shields

    //TYPE
    private int type;
    private static final int type_sword = 0;
    private static final int type_shield = 1;
    private static final int type_pickupOnly = 2;
    private static final int type_obstacle = 3;
    private static final int type_consumable = 4;

    public Item(GamePanel gp) {
        super(gp);
    }

    public void interact() {
        // Default interaction logic, can be overridden by subclasses
    }

    // Polymorphic use method for all items
    public boolean use(Character user) {
        // Default use logic, can be overridden by subclasses
        return false;
    }

    public int getDetected(Character user, Item[][] target, String targetName)
    {
        int index = 999;

        //Check the surrounding object
        int nextWorldX = user.getLeftX();
        int nextWorldY = user.getTopY();

        switch (user.getDirection())
        {
            case "up" : nextWorldY = user.getTopY() - gp.getPlayer().getSpeed(); break;
            case "down": nextWorldY = user.getBottomY() + gp.getPlayer().getSpeed(); break;
            case "left": nextWorldX = user.getLeftX() - gp.getPlayer().getSpeed(); break;
            case "right": nextWorldX = user.getRightX() + gp.getPlayer().getSpeed(); break;
        }
        int col = nextWorldX/gp.getTileSize();
        int row = nextWorldY/gp.getTileSize();

        for(int i = 0; i < target[1].length; i++)
        {
            if(target[gp.getCurrentMap()][i] != null)
            {
                if (target[gp.getCurrentMap()][i].getCol() == col  // checking if player 1 tile away from target (key etc.) (must be same direction)
                        && target[gp.getCurrentMap()][i].getRow() == row
                        && target[gp.getCurrentMap()][i].getName().equals(targetName))
                {
                    index = i;
                    break;
                }
            }

        }
        return  index;
    }

    public void setLoot(Item loot) {
        this.loot = loot; // Gán giá trị loot cho thuộc tính loot
        setDialogue(); // Thiết lập hội thoại
    }

    public BufferedImage getImage() {
        return image;
    }

    public void setImage(BufferedImage image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public boolean isStackable() {
        return stackable;
    }

    public void setStackable(boolean stackable) {
        this.stackable = stackable;
    }

    public Item getLoot() {
        return loot;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public int getAttackValue() {
        return attackValue;
    }

    public void setAttackValue(int attackValue) {
        this.attackValue = attackValue;
    }

    public int getKnockBackPower() {
        return knockBackPower;
    }

    public void setKnockBackPower(int knockBackPower) {
        this.knockBackPower = knockBackPower;
    }

    public int getDefenseValue() {
        return defenseValue;
    }

    public void setDefenseValue(int defenseValue) {
        this.defenseValue = defenseValue;
    }

    public static int getType_shield() {
        return type_shield;
    }

    public static int getType_sword() {
        return type_sword;
    }

    public static int getType_pickupOnly() {
        return type_pickupOnly;
    }

    public static int getType_obstacle() {
        return type_obstacle;
    }

    public static int getType_consumable() {
        return type_consumable;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    @Override
    public void draw(Graphics2D g2) {
        if (inCamera()) {
            int tempScreenX = getWorldX() - gp.getPlayer().getWorldX() + gp.getPlayer().getScreenX();
            int tempScreenY = getWorldY() - gp.getPlayer().getWorldY() + gp.getPlayer().getScreenY();
            g2.drawImage(getImage(), tempScreenX, tempScreenY, null);
        }
    }

    // If you want items to manage their own stacking, you can add:
    public void addToInventory(java.util.List<Item> inventory) {
        if (isStackable()) {
            for (Item invItem : inventory) {
                if (invItem.getName().equals(getName())) {
                    invItem.setAmount(invItem.getAmount() + getAmount());
                    return;
                }
            }
        }
        inventory.add(this);
    }

    public boolean isTemp() {
        return temp;
    }

    public void setTemp(boolean temp) {
        this.temp = temp;
    }
}
