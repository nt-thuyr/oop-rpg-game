package main;

import entity.Character;
import entity.Entity;
import item.CoinBronze;
import item.Heart;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class UI {

    private GamePanel gp;
    private Graphics2D g2;

    private Font determinationSans, purisaB;
    private BufferedImage heart_full, heart_half, heart_blank, crystal_full, crystal_blank, coin;

    private ArrayList<String> message = new ArrayList<>();
    private ArrayList<Integer> messageCounter = new ArrayList<>();

    private String currentDialogue = "";
    private int commandNum = 0;
    private int titleScreenState = 0;
    private int playerSlotCol = 0;
    private int playerSlotRow = 0;
    private int npcSlotCol = 0;
    private int npcSlotRow = 0;

    private int subState = 0;
    private int counter = 0;
    private Entity dialogueEntity; // Can be Character or Item
    private Character npc;
    private int charIndex = 0;
    private String combinedText = "";
    private boolean isInNPCInventory = true;

    public UI(GamePanel gp) {
        this.gp = gp;
        try
        {
            InputStream is = getClass().getResourceAsStream("/font/SVN-Determination Sans.ttf");
            determinationSans = Font.createFont(Font.TRUETYPE_FONT, is);

            is = getClass().getResourceAsStream("/font/Purisa Bold.ttf");
            purisaB = Font.createFont(Font.TRUETYPE_FONT, is);
        } catch (FontFormatException | IOException e) {
            e.printStackTrace();
        }

        Heart heart = new Heart(gp);
        heart_full = heart.getImage();
        heart_half = heart.getImage2();
        heart_blank = heart.getImage3();

        CoinBronze bronzeCoin = new CoinBronze(gp);
        coin = bronzeCoin.getImage();
    }

    public void drawPauseScreen() {
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 80F));
        String text = "GAME PAUSED";
        int x = getXforCenteredText(text);
        int y = gp.getScreenHeight() / 2;
        g2.drawString(text, x, y);
    }

    public void drawDialogueScreen() {
        // WINDOW
        int x = gp.getTileSize() * 3;
        int y = gp.getTileSize() / 2;
        int width = gp.getScreenWidth() - (gp.getTileSize() * 6);
        int height = gp.getTileSize() * 4;

        drawSubWindow(x, y, width, height);

        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 25F));
        x += gp.getTileSize();
        y += gp.getTileSize();

        if (dialogueEntity != null && dialogueEntity.getDialogues()[dialogueEntity.getDialogueSet()][dialogueEntity.getDialogueIndex()] != null) {
            char[] characters = dialogueEntity.getDialogues()[dialogueEntity.getDialogueSet()][dialogueEntity.getDialogueIndex()].toCharArray();

            if (charIndex < characters.length) {
                gp.playSE(17); // Speak sound
                String s = String.valueOf(characters[charIndex]);
                combinedText = combinedText + s; // every loop adds one character to combinedText
                currentDialogue = combinedText;

                charIndex++;
            }
            if (gp.getKeyH().isEnterPressed()) {
                charIndex = 0;
                combinedText = "";
                if (gp.getGameState() == gp.getDialogueState() || gp.getGameState() == gp.getCutsceneState()) {
                    dialogueEntity.setDialogueIndex(dialogueEntity.getDialogueIndex() + 1);
                    gp.getKeyH().setEnterPressed(false);
                }
            }
        } else { // If no text is in the array
            if (dialogueEntity != null) {
                dialogueEntity.setDialogueIndex(0);
            }
            if (gp.getGameState() == gp.getDialogueState()) {
                gp.setGameState(gp.getPlayState());
            }
        }

        for (String line : currentDialogue.split("\n")) { // splits dialogue until "\n" as a line
            g2.drawString(line, x, y);
            y += 40;
        }
    }

    public void drawCharacterScreen() {
        // CREATE A FRAME
        int frameX = gp.getTileSize() * 2;
        int frameY = gp.getTileSize();
        int frameWidth = gp.getTileSize() * 16; // Chiều rộng lớn để chứa cả hình ảnh và chỉ số
        int frameHeight = gp.getTileSize() * 3; // Chiều cao vừa đủ cho phần trên
        drawSubWindow(frameX, frameY, frameWidth, frameHeight);

        // CHARACTER IMAGE
        int charX = frameX + 36;
        int charY = frameY + 20;
        g2.drawImage(gp.getPlayer().getDown1(), charX, charY, gp.getTileSize() * 2, gp.getTileSize() * 2, null);

        // CHARACTER STATS
        int statsX = charX + gp.getTileSize() * 2 + 30; // Bên phải hình ảnh nhân vật
        int statsY = charY + 24;

        g2.setColor(Color.white);
        g2.setFont(g2.getFont().deriveFont(22F));

        // NAMES AND VALUES
        final int lineHeight = 32;
        final int spacing = 118;

        g2.drawString("Life", statsX, statsY);
        String hpValue = gp.getPlayer().getLife() + "/" + gp.getPlayer().getMaxLife();
        g2.drawString(hpValue, statsX + spacing, statsY);

        g2.drawString("Strength", statsX, statsY + lineHeight);
        g2.drawString(String.valueOf(gp.getPlayer().getStrength()), statsX + spacing, statsY + lineHeight);

        g2.drawString("Dexterity", statsX, statsY + lineHeight * 2);
        g2.drawString(String.valueOf(gp.getPlayer().getDexterity()), statsX + spacing, statsY + lineHeight * 2);

        statsX += gp.getTileSize() * 4 + 20; // Dịch sang phải để hiển thị các chỉ số khác
        g2.drawString("Attack", statsX, statsY);
        g2.drawString(String.valueOf(gp.getPlayer().getAttack()), statsX + spacing - 5, statsY);

        g2.drawString("Defense", statsX, statsY + lineHeight);
        g2.drawString(String.valueOf(gp.getPlayer().getDefense()), statsX + spacing - 5, statsY + lineHeight);

        g2.drawString("Coin", statsX, statsY + lineHeight * 2);
        g2.drawString(String.valueOf(gp.getPlayer().getCoin()), statsX + spacing - 5, statsY + lineHeight * 2);

        statsX += gp.getTileSize() * 4 - 20; // Dịch sang phải để hiển thị các chỉ số khác
        g2.drawString("Experience", statsX, statsY);
        String expValue = gp.getPlayer().getExp() + "/" + gp.getPlayer().getNextLevelExp();
        g2.drawString(expValue, statsX + spacing + 10, statsY);

        g2.drawString("Level", statsX, statsY + lineHeight);
        g2.drawString(String.valueOf(gp.getPlayer().getLevel()), statsX + spacing + 10, statsY + lineHeight);

        frameY = gp.getTileSize() * 6;
        drawInventory(gp.getPlayer(), false, true, frameX, frameY, frameWidth, frameHeight, getPlayerSlotCol(), getPlayerSlotRow());
    }

    public void drawTransition() {
        counter++;
        g2.setColor(new Color(0, 0, 0, counter * 5));
        g2.fillRect(0, 0, gp.getScreenWidth2(), gp.getScreenHeight2()); // screen gets darker

        if (counter == 50) { // the transition is done
            counter = 0;
            gp.setGameState(gp.getPlayState());
            gp.getPlayer().setWorldX(gp.getTileSize() * gp.geteHandler().getTempCol());
            gp.getPlayer().setWorldY(gp.getTileSize() * gp.geteHandler().getTempRow());
            gp.setCurrentMap(gp.geteHandler().getTempMap());
            gp.geteHandler().setPreviousEventX(gp.getPlayer().getWorldX());
            gp.geteHandler().setPreviousEventY(gp.getPlayer().getWorldY());
            gp.changeArea();
        }
    }

    public void drawInventory(Character character, boolean isTrading, boolean cursor, int frameX, int frameY, int frameWidth, int frameHeight, int slotCol, int slotRow) {
        // Inventory Frame
        int slotStartX = frameX + 20;
        int slotStartY = frameY + 20;
        int slotX = slotStartX;
        int slotY = slotStartY;
        int slotSize = gp.getTileSize() + 3;

        int invFrameWidth = slotSize * 5 + 40;
        int invFrameHeight = slotSize * 4 + 40;
        drawSubWindow(frameX, frameY, invFrameWidth, invFrameHeight);

        // Draw Items
        for (int i = 0; i < character.getInventory().size(); i++) {
            if (character.getInventory().get(i) == character.getCurrentWeapon() ||
                    character.getInventory().get(i) == character.getCurrentShield()) {
                g2.setColor(new Color(240, 190, 90));
                g2.fillRoundRect(slotX, slotY, gp.getTileSize(), gp.getTileSize(), 10, 10);
            }

            g2.drawImage(character.getInventory().get(i).getImage(), slotX, slotY, null);

            if (character == gp.getPlayer() && character.getInventory().get(i).getAmount() > 1) {
                g2.setFont(g2.getFont().deriveFont(32f));
                int amountX = getXforAlignToRight("" + character.getInventory().get(i).getAmount(), slotX + 44);
                int amountY = slotY + gp.getTileSize();
                g2.setColor(new Color(60, 60, 60));
                g2.drawString("" + character.getInventory().get(i).getAmount(), amountX, amountY);
                g2.setColor(Color.white);
                g2.drawString("" + character.getInventory().get(i).getAmount(), amountX - 3, amountY - 3);
            }

            slotX += slotSize;
            if ((i + 1) % 5 == 0) {
                slotX = slotStartX;
                slotY += slotSize;
            }
        }

        // Cursor
        if (cursor) {
            int cursorX = slotStartX + (slotSize * slotCol);
            int cursorY = slotStartY + (slotSize * slotRow);
            g2.setColor(Color.white);
            g2.setStroke(new BasicStroke(3));
            g2.drawRoundRect(cursorX, cursorY, gp.getTileSize(), gp.getTileSize(), 10, 10);

            // Description and Price
            int itemIndex = getItemIndexOnSlot(slotCol, slotRow);
            if (itemIndex < character.getInventory().size()) {
                int dFrameWidth = gp.getTileSize() * 6 + 20;
                int dFrameX = frameX + invFrameWidth + gp.getTileSize();
                if (!isTrading) {
                    dFrameX = frameX + gp.getTileSize() * 16 - dFrameWidth;
                }
                drawSubWindow(dFrameX, frameY, dFrameWidth, invFrameHeight);

                int textX = dFrameX + 20;
                int textY = frameY + gp.getTileSize();
                g2.setFont(g2.getFont().deriveFont(22F));
                for (String line : character.getInventory().get(itemIndex).getDescription().split("\n")) {
                    g2.drawString(line, textX, textY);
                    textY += 32;
                }

                if (isTrading) {
                    // Price
                    int price = character == getDialogueEntity() ? character.getInventory().get(itemIndex).getPrice() : character.getInventory().get(itemIndex).getPrice() / 2;
                    textY += 32;
                    g2.drawString("Price: " + price, textX, textY);
                    g2.drawImage(getCoin(), textX + gp.getTileSize() * 2 + 15, textY - 27, 36, 36, null);
                    // Player's coin
                    textY += 32;
                    g2.drawString("Your Coin: " + gp.getPlayer().getCoin(), textX, textY);
                    g2.drawImage(getCoin(), textX + gp.getTileSize() * 3 + 10, textY - 27, 36, 36, null);
                }
            }
        }
    }

    public void drawTradeScreen() {
        switch (getSubState()) {
            case 0:
                trade_select();
                break;
            case 1:
                trade_screen();
                break;
        }
        gp.getKeyH().setEnterPressed(false);
    }

    public void trade_select() {
        getDialogueEntity().setDialogueSet(0);
        drawDialogueScreen();

        int x = gp.getTileSize() * 15;
        int y = gp.getTileSize() * 4;
        int width = gp.getTileSize() * 3;
        int height = gp.getTileSize() * 3;
        drawSubWindow(x, y, width, height);

        x += gp.getTileSize();
        y += gp.getTileSize();
        g2.drawString("Trade", x, y);
        if (getCommandNum() == 0) {
            g2.drawString(">", x - 24, y);
            if (gp.getKeyH().isEnterPressed()) {
                setSubState(1);
            }
        }
        y += gp.getTileSize();
        g2.drawString("Leave", x, y);
        if (getCommandNum() == 1) {
            g2.drawString(">", x - 24, y);
            if (gp.getKeyH().isEnterPressed()) {
                setCommandNum(0);
                getDialogueEntity().startDialogue(getDialogueEntity(), 1);
            }
        }
    }

    public void trade_screen() {
        // NPC Inventory (Top Half)
        int frameX = gp.getTileSize() * 2;
        int frameY = gp.getTileSize();
        int frameWidth = gp.getTileSize() * 6;
        int frameHeight = gp.getTileSize() * 5;
        drawInventory(getNpc(), true, isInNPCInventory(), frameX, frameY, frameWidth, frameHeight, getNpcSlotCol(), getNpcSlotRow());

        // Player Inventory (Bottom Half)
        frameY = gp.getTileSize() * 6 + 10;
        drawInventory(gp.getPlayer(), true, !isInNPCInventory(), frameX, frameY, frameWidth, frameHeight, getPlayerSlotCol(), getPlayerSlotRow());
    }

    public int getItemIndexOnSlot(int slotCol, int slotRow) {
        return slotCol + (slotRow * 5);
    }

    public void drawPlayerLife() {
        int x = gp.getTileSize() / 2;
        int y = gp.getTileSize() / 2;
        int i = 0;
        int iconSize = 32;

        // DRAW MAX LIFE (BLANK)
        while (i < gp.getPlayer().getMaxLife() / 2) {
            g2.drawImage(getHeart_blank(), x, y, iconSize, iconSize, null);
            i++;
            x += iconSize;
        }

        // Reset
        x = gp.getTileSize() / 2;
        y = gp.getTileSize() / 2;
        i = 0;

        // DRAW CURRENT HEART
        while (i < gp.getPlayer().getLife()) {
            g2.drawImage(getHeart_half(), x, y, iconSize, iconSize, null);
            i++;
            if (i < gp.getPlayer().getLife()) {
                g2.drawImage(getHeart_full(), x, y, iconSize, iconSize, null);
            }
            i++;
            x += iconSize;
        }

        // DRAW EXP BAR
        int expBarX = gp.getTileSize() / 2;
        int expBarY = y + iconSize + 12;
        int expBarWidth = gp.getTileSize() * 4;
        int expBarHeight = 12;

        // Background (empty EXP)
        g2.setColor(new Color(50, 50, 50));
        g2.fillRoundRect(expBarX, expBarY, expBarWidth, expBarHeight, 10, 10);

        // Filled EXP
        double expRatio = (double) gp.getPlayer().getExp() / gp.getPlayer().getNextLevelExp();
        int filledExpWidth = (int) (expBarWidth * expRatio);
        g2.setColor(new Color(0, 255, 0));
        g2.fillRoundRect(expBarX, expBarY, filledExpWidth, expBarHeight, 10, 10);

        // Draw level text
        int levelTextX = expBarX + 12;
        int levelTextY = expBarY + 12;
        g2.setColor(Color.white);
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 14F));
        String text = "Level " + gp.getPlayer().getLevel();
        g2.drawString(text, levelTextX, levelTextY);
    }

    public void drawMonsterLife() {
        // Monster HP Bar
        for (int i = 0; i < gp.getMonster()[1].length; i++) {
            Character monster = gp.getMonster()[gp.getCurrentMap()][i];

            if (monster != null && monster.inCamera()) {
                if (monster.getState().isHpBarOn() && !monster.isBoss()) {
                    double oneScale = (double) gp.getTileSize() / monster.getMaxLife();
                    double hpBarValue = oneScale * monster.getLife();

                    if (hpBarValue < 0) {
                        hpBarValue = 0;
                    }

                    g2.setColor(new Color(35, 35, 35));
                    g2.fillRect(monster.getScreenX() - 1, monster.getScreenY() - 16, gp.getTileSize() + 2, 12);

                    g2.setColor(new Color(255, 0, 30));
                    g2.fillRect(monster.getScreenX(), monster.getScreenY() - 15, (int) hpBarValue, 10);

                    monster.getState().setHpBarCounter(monster.getState().getHpBarCounter() + 1);
                    if (monster.getState().getHpBarCounter() > 600) {
                        monster.getState().setHpBarCounter(0);
                        monster.getState().setHpBarOn(false);
                    }
                } else if (monster.isBoss()) {
                    double oneScale = (double) gp.getTileSize() * 8 / monster.getMaxLife();
                    double hpBarValue = oneScale * monster.getLife();
                    int x = gp.getScreenWidth() / 2 - gp.getTileSize() * 4;
                    int y = gp.getTileSize() * 10;

                    if (hpBarValue < 0) {
                        hpBarValue = 0;
                    }

                    g2.setColor(new Color(35, 35, 35));
                    g2.fillRect(x - 1, y - 1, gp.getTileSize() * 8 + 2, 22);

                    g2.setColor(new Color(255, 0, 30));
                    g2.fillRect(x, y, (int) hpBarValue, 20);

                    g2.setFont(g2.getFont().deriveFont(Font.BOLD, 22f));
                    g2.setColor(Color.white);
                    g2.drawString(monster.getName(), x + 4, y - 10);
                }
            }
        }
    }

    public void drawMessage() {
        int messageX = gp.getTileSize();
        int messageY = gp.getTileSize() * 4;
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 24F));

        for (int i = 0; i < getMessage().size(); i++) {
            if (getMessage().get(i) != null) {
                // Shadow
                g2.setColor(Color.black);
                g2.drawString(getMessage().get(i), messageX + 2, messageY + 2);
                // Text
                g2.setColor(Color.white);
                g2.drawString(getMessage().get(i), messageX, messageY);

                int counter = getMessageCounter().get(i) + 1;
                getMessageCounter().set(i, counter);
                messageY += 50;

                if (getMessageCounter().get(i) > 150) {
                    getMessage().remove(i);
                    getMessageCounter().remove(i);
                }
            }
        }
    }

    public void drawTitleScreen() {
        g2.setColor(new Color(0, 0, 0)); // FILL BACKGROUND BLACK
        g2.fillRect(0, 0, gp.getScreenWidth(), gp.getScreenHeight());
        // MAIN MENU
        if (getTitleScreenState() == 0) {
            // TITLE NAME
            g2.setFont(g2.getFont().deriveFont(Font.BOLD, 90F));
            String text = "Final Fantasy\n";
            int x = getXforCenteredText(text);
            int y = gp.getTileSize() * 3;
            // SHADOW
            g2.setColor(Color.gray);
            g2.drawString(text, x + 5, y + 5);
            // MAIN COLOR
            g2.setColor(Color.white);
            g2.drawString(text, x, y);

            // BLUE BOY IMAGE
            x = gp.getScreenWidth() / 2 - (gp.getTileSize() * 2) / 2;
            y += gp.getTileSize() * 2;
            g2.drawImage(gp.getPlayer().getDown1(), x, y, gp.getTileSize() * 2, gp.getTileSize() * 2, null);

            // MENU
            g2.setFont(g2.getFont().deriveFont(Font.BOLD, 48F));

            text = "NEW GAME";
            x = getXforCenteredText(text);
            y += (int) (gp.getTileSize() * 3.5);
            g2.drawString(text, x, y);
            if (getCommandNum() == 0) {
                g2.drawString(">", x - gp.getTileSize(), y);
            }

            text = "QUIT";
            x = getXforCenteredText(text);
            y += gp.getTileSize();
            g2.drawString(text, x, y);
            if (getCommandNum() == 1) {
                g2.drawString(">", x - gp.getTileSize(), y);
            }
        }
        // SECOND SCREEN
        else if (getTitleScreenState() == 1) {
            // CLASS SELECTION SCREEN
            g2.setColor(Color.white);
            g2.setFont(g2.getFont().deriveFont(40F));

            String text = "- You are -";
            int x = getXforCenteredText(text);
            int y = gp.getTileSize() * 3;
            g2.drawString(text, x, y);

            text = "Fighter";
            x = getXforCenteredText(text);
            y += gp.getTileSize() * 3;
            g2.drawString(text, x, y);
            if (getCommandNum() == 0) {
                g2.drawString(">", x - gp.getTileSize(), y);
            }
        }
    }

    public void drawGameOverScreen() {
        g2.setColor(new Color(0, 0, 0, 150)); // Half-black
        g2.fillRect(0, 0, gp.getScreenWidth(), gp.getScreenHeight());

        int x;
        int y;
        String text;
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 110f));
        text = "Game Over";

        // Shadow
        g2.setColor(Color.BLACK);
        x = getXforCenteredText(text);
        y = gp.getTileSize() * 4;
        g2.drawString(text, x, y);
        // Text
        g2.setColor(Color.white);
        g2.drawString(text, x - 4, y - 4);

        // RETRY
        g2.setFont(g2.getFont().deriveFont(50f));
        text = "Retry";
        x = getXforCenteredText(text);
        y += gp.getTileSize() * 4;
        g2.drawString(text, x, y);
        if (getCommandNum() == 0) {
            g2.drawString(">", x - 40, y);
        }

        // BACK TO THE TITLE SCREEN
        text = "Quit";
        x = getXforCenteredText(text);
        y += 55;
        g2.drawString(text, x, y);
        if (getCommandNum() == 1) {
            g2.drawString(">", x - 40, y);
        }
    }

    public void drawSubWindow(int x, int y, int width, int height) {
        Color c = new Color(0, 0, 0, 210); // R,G,B, alpha (opacity)
        g2.setColor(c);
        g2.fillRoundRect(x, y, width, height, 35, 35);

        c = new Color(255, 255, 255);
        g2.setColor(c);
        g2.setStroke(new BasicStroke(5)); // 5 = width of outlines of graphics
        g2.drawRoundRect(x + 5, y + 5, width - 10, height - 10, 25, 25);
    }

    public int getXforCenteredText(String text) {
        int textLength;
        textLength = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth(); // Gets width of text.
        int x = gp.getScreenWidth() / 2 - textLength / 2;
        return x;
    }

    public int getXforAlignToRight(String text, int tailX) {
        int textLength;
        textLength = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth(); // Gets width of text.
        int x = tailX - textLength;
        return x;
    }

    public void addMessage(String text) {
        getMessage().add(text);
        getMessageCounter().add(0);
    }

    public void draw(Graphics2D g2) {
        this.g2 = g2;
        g2.setFont(getDeterminationSans());
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);  // Anti Aliasing // Smoothes the text
        g2.setColor(Color.white);

        // TITLE STATE
        if (gp.getGameState() == gp.getTitleState()) {
            drawTitleScreen();
        }
        // OTHERS
        else {
            // PLAY STATE
            if (gp.getGameState() == gp.getPlayState()) {
                drawPlayerLife();
                drawMonsterLife();
                drawMessage();
            }
            // PAUSE STATE
            if (gp.getGameState() == gp.getPauseState()) {
                drawPlayerLife();
                drawPauseScreen();
            }
            // DIALOGUE STATE
            if (gp.getGameState() == gp.getDialogueState()) {
                drawDialogueScreen();
            }
            // CHARACTER STATE
            if (gp.getGameState() == gp.getCharacterState()) {
                drawCharacterScreen();
            }
            // GAME OVER STATE
            if (gp.getGameState() == gp.getGameOverState()) {
                drawGameOverScreen();
            }
            // TRANSITION STATE
            if (gp.getGameState() == gp.getTransitionState()) {
                drawTransition();
            }
            // TRADE STATE
            if (gp.getGameState() == gp.getTradeState()) {
                drawTradeScreen();
            }
        }
    }
    public int getCharIndex() {
        return charIndex;
    }

    public BufferedImage getCoin() {
        return coin;
    }

    public String getCombinedText() {
        return combinedText;
    }

    public int getCommandNum() {
        return commandNum;
    }

    public int getCounter() {
        return counter;
    }

    public BufferedImage getCrystal_blank() {
        return crystal_blank;
    }

    public BufferedImage getCrystal_full() {
        return crystal_full;
    }

    public String getCurrentDialogue() {
        return currentDialogue;
    }

    public Font getDeterminationSans() {
        return determinationSans;
    }

    public Graphics2D getG2() {
        return g2;
    }

    public GamePanel getGp() {
        return gp;
    }

    public BufferedImage getHeart_blank() {
        return heart_blank;
    }

    public BufferedImage getHeart_full() {
        return heart_full;
    }

    public BufferedImage getHeart_half() {
        return heart_half;
    }

    public boolean isInNPCInventory() {
        return isInNPCInventory;
    }

    public ArrayList<String> getMessage() {
        return message;
    }

    public ArrayList<Integer> getMessageCounter() {
        return messageCounter;
    }

    public Character getNpc() {
        return npc;
    }

    public int getNpcSlotCol() {
        return npcSlotCol;
    }

    public int getNpcSlotRow() {
        return npcSlotRow;
    }

    public int getPlayerSlotCol() {
        return playerSlotCol;
    }

    public int getPlayerSlotRow() {
        return playerSlotRow;
    }

    public Font getPurisaB() {
        return purisaB;
    }

    public int getSubState() {
        return subState;
    }

    public int getTitleScreenState() {
        return titleScreenState;
    }

    public void setNpc(Character npc) {
        this.npc = npc;
    }

    public void setCommandNum(int commandNum) {
        this.commandNum = commandNum;
    }

    public void setCharIndex(int charIndex) {
        this.charIndex = charIndex;
    }

    public void setCoin(BufferedImage coin) {
        this.coin = coin;
    }

    public void setCombinedText(String combinedText) {
        this.combinedText = combinedText;
    }

    public void setCounter(int counter) {
        this.counter = counter;
    }

    public void setCrystal_blank(BufferedImage crystal_blank) {
        this.crystal_blank = crystal_blank;
    }

    public void setCrystal_full(BufferedImage crystal_full) {
        this.crystal_full = crystal_full;
    }

    public void setCurrentDialogue(String currentDialogue) {
        this.currentDialogue = currentDialogue;
    }

    public void setDeterminationSans(Font determinationSans) {
        this.determinationSans = determinationSans;
    }

    public void setG2(Graphics2D g2) {
        this.g2 = g2;
    }

    public void setGp(GamePanel gp) {
        this.gp = gp;
    }

    public void setHeart_blank(BufferedImage heart_blank) {
        this.heart_blank = heart_blank;
    }

    public void setHeart_full(BufferedImage heart_full) {
        this.heart_full = heart_full;
    }

    public void setHeart_half(BufferedImage heart_half) {
        this.heart_half = heart_half;
    }

    public void setInNPCInventory(boolean inNPCInventory) {
        isInNPCInventory = inNPCInventory;
    }

    public void setMessage(ArrayList<String> message) {
        this.message = message;
    }

    public void setMessageCounter(ArrayList<Integer> messageCounter) {
        this.messageCounter = messageCounter;
    }

    public void setNpcSlotCol(int npcSlotCol) {
        this.npcSlotCol = npcSlotCol;
    }

    public void setNpcSlotRow(int npcSlotRow) {
        this.npcSlotRow = npcSlotRow;
    }

    public void setPlayerSlotCol(int playerSlotCol) {
        this.playerSlotCol = playerSlotCol;
    }

    public void setPlayerSlotRow(int playerSlotRow) {
        this.playerSlotRow = playerSlotRow;
    }

    public void setPurisaB(Font purisaB) {
        this.purisaB = purisaB;
    }

    public void setSubState(int subState) {
        this.subState = subState;
    }

    public void setTitleScreenState(int titleScreenState) {
        this.titleScreenState = titleScreenState;
    }

    public void setDialogueEntity(Entity entity) {
        this.dialogueEntity = entity;
    }

    public Entity getDialogueEntity() {
        return dialogueEntity;
    }
}
