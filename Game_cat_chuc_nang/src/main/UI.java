package main;

import entity.Character;
import entity.Entity;
import object.OBJ_Coin_Bronze;
import object.OBJ_Heart;

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

        OBJ_Heart heart = new OBJ_Heart(gp);
        heart_full = heart.getImage();
        heart_half = heart.getImage2();
        heart_blank = heart.getImage3();

        OBJ_Coin_Bronze bronzeCoin = new OBJ_Coin_Bronze(gp);
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
        drawInventory((Character) getNpc(), true, isInNPCInventory(), frameX, frameY, frameWidth, frameHeight, getNpcSlotCol(), getNpcSlotRow());

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
                if (monster.getState().isHpBarOn()) {
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
        // Changed background color to a darker blue/gray
        g2.setColor(new Color(20, 30, 40)); // New color
        g2.fillRect(0, 0, gp.getScreenWidth(), gp.getScreenHeight());
        // MAIN MENU
        if (getTitleScreenState() == 0) {
            // TITLE NAME
            g2.setFont(g2.getFont().deriveFont(Font.BOLD, 90F));
            String text = "MonsterMash\n";
            int x = getXforCenteredText(text);
            int y = gp.getTileSize() * 2; // Giữ nguyên vị trí tên game ở trên cùng
            // SHADOW
            g2.setColor(Color.gray);
            g2.drawString(text, x + 5, y + 5);
            // MAIN COLOR
            g2.setColor(Color.white);
            g2.drawString(text, x, y);

            // BLUE BOY IMAGE
            int charY = y + g2.getFontMetrics().getHeight() + gp.getTileSize() / 2; // Sử dụng g2.getFontMetrics()
            x = gp.getScreenWidth() / 2 - (gp.getTileSize() * 2) / 2; // Giữ nguyên căn giữa
            g2.drawImage(gp.getPlayer().getDown1(), x, charY, gp.getTileSize() * 2, gp.getTileSize() * 2, null);

            // MENU
            g2.setFont(g2.getFont().deriveFont(Font.BOLD, 48F));

            // START BUTTON
            text = "START";
            FontMetrics metrics = g2.getFontMetrics(g2.getFont()); // Lấy FontMetrics của font hiện tại của g2
            int buttonWidth = (int) metrics.getStringBounds(text, g2).getWidth() + 40;
            int buttonHeight = (int) metrics.getStringBounds(text, g2).getHeight() + 20;

            // Tính toán vị trí Y mới cho nút START
            int startY = charY + gp.getTileSize() * 2 + gp.getTileSize();

            int startX = getXforCenteredText(text);

            drawMenuOptionBox(startX - 20, startY - buttonHeight / 2 - 10, buttonWidth, buttonHeight);
            g2.setColor(Color.white);
            g2.drawString(text, startX, startY);
            if (getCommandNum() == 0) {
                g2.drawString(">", startX - gp.getTileSize(), startY);
            }

            // QUIT BUTTON
            text = "QUIT";
            buttonWidth = (int) metrics.getStringBounds(text, g2).getWidth() + 40;
            buttonHeight = (int) metrics.getStringBounds(text, g2).getHeight() + 20;
            int quitX = getXforCenteredText(text);
            int quitY = startY + buttonHeight + gp.getTileSize() / 2;

            drawMenuOptionBox(quitX - 20, quitY - buttonHeight / 2 - 10, buttonWidth, buttonHeight);
            g2.setColor(Color.white);
            g2.drawString(text, quitX, quitY);
            if (getCommandNum() == 1) {
                g2.drawString(">", quitX - gp.getTileSize(), quitY);
            }
        }
        // SECOND SCREEN (CLASS SELECTION)
        else if (getTitleScreenState() == 1) {
            g2.setColor(Color.white);
            g2.setFont(g2.getFont().deriveFont(40F));

            String text = "- You are ready-";
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

    // New helper method to draw menu option boxes
    public void drawMenuOptionBox(int x, int y, int width, int height) {
        Color c = new Color(0, 0, 0, 180); // Semi-transparent black for the box background
        g2.setColor(c);
        g2.fillRoundRect(x, y, width, height, 25, 25); // Rounded rectangle

        c = new Color(255, 255, 255); // White border
        g2.setColor(c);
        g2.setStroke(new BasicStroke(3)); // Thicker border
        g2.drawRoundRect(x + 3, y + 3, width - 6, height - 6, 20, 20); // Slightly smaller rounded rectangle for the border
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

    public void drawOptionsScreen() {
        g2.setColor(Color.white);
        g2.setFont(g2.getFont().deriveFont(30F));

        // SUB WINDOW
        int frameX = gp.getTileSize() * 6;
        int frameY = gp.getTileSize();
        int frameWidth = gp.getTileSize() * 8;
        int frameHeight = gp.getTileSize() * 10;

        drawSubWindow(frameX, frameY, frameWidth, frameHeight);

        switch (getSubState()) {
            case 0:
                options_top(frameX, frameY);
                break;
            case 1:
                options_fullScreenNotification(frameX, frameY);
                break;
            case 2:
                options_control(frameX, frameY);
                break;
            case 3:
                options_endGameConfirmation(frameX, frameY);
        }
        gp.getKeyH().setEnterPressed(false);
    }

    public void options_top(int frameX, int frameY) {
        int textX;
        int textY;

        // TITLE
        String text = "Options";
        textX = getXforCenteredText(text);
        textY = frameY + gp.getTileSize();
        g2.drawString(text, textX, textY);

        // FULL SCREEN ON/OFF
        textX = frameX + gp.getTileSize();
        textY += gp.getTileSize() * 2;
        g2.drawString("Full Screen", textX, textY);
        if (getCommandNum() == 0) {
            g2.drawString(">", textX - 25, textY);
            if (gp.getKeyH().isEnterPressed()) {
                if (!gp.isFullScreenOn()) {
                    gp.setFullScreenOn(true);
                } else {
                    gp.setFullScreenOn(false);
                }
                setSubState(1);
            }
        }

        // MUSIC
        textY += gp.getTileSize();
        g2.drawString("Music", textX, textY);
        if (getCommandNum() == 1) {
            g2.drawString(">", textX - 25, textY);
        }

        // SE
        textY += gp.getTileSize();
        g2.drawString("SE", textX, textY);
        if (getCommandNum() == 2) {
            g2.drawString(">", textX - 25, textY);
        }

        // CONTROLS
        textY += gp.getTileSize();
        g2.drawString("Controls", textX, textY);
        if (getCommandNum() == 3) {
            g2.drawString(">", textX - 25, textY);
            if (gp.getKeyH().isEnterPressed()) {
                setSubState(2);
                setCommandNum(0);
            }
        }

        // END GAME
        textY += gp.getTileSize();
        g2.drawString("End Game", textX, textY);
        if (getCommandNum() == 4) {
            g2.drawString(">", textX - 25, textY);
            if (gp.getKeyH().isEnterPressed()) {
                setSubState(3);
                setCommandNum(0);
            }
        }

        // BACK
        textY += gp.getTileSize() * 2;
        g2.drawString("Back", textX, textY);
        if (getCommandNum() == 5) {
            g2.drawString(">", textX - 25, textY);
            if (gp.getKeyH().isEnterPressed()) {
                gp.setGameState(gp.getPlayState());
                setCommandNum(0);
            }
        }

        // FULL SCREEN CHECK BOX
        textX = frameX + (int) (gp.getTileSize() * 4.5);
        textY = frameY + gp.getTileSize() * 2 + 24;
        g2.setStroke(new BasicStroke(3));
        g2.drawRect(textX, textY, 24, 24);
        if (gp.isFullScreenOn()) {
            g2.fillRect(textX, textY, 24, 24);
        }

        // MUSIC VOLUME
        textY += gp.getTileSize();
        g2.drawRect(textX, textY, 120, 24); // 120/5 = 24px = 1 scale
        int volumeWidth = 24 * gp.getMusic().getVolumeScale();
        g2.fillRect(textX, textY, volumeWidth, 24);

        // SE VOLUME
        textY += gp.getTileSize();
        g2.drawRect(textX, textY, 120, 24);
        volumeWidth = 24 * gp.getSe().getVolumeScale();
        g2.fillRect(textX, textY, volumeWidth, 24);
    }

    public void options_fullScreenNotification(int frameX, int frameY) {
        int textX = frameX + gp.getTileSize();
        int textY = frameY + gp.getTileSize() * 3;

        setCurrentDialogue("The change will take \neffect after restarting \nthe game.");
        for (String line : getCurrentDialogue().split("\n")) {
            g2.drawString(line, textX, textY);
            textY += 40;
        }

        // BACK
        textY = frameY + gp.getTileSize() * 9;
        g2.drawString("Back", textX, textY);
        if (getCommandNum() == 0) {
            g2.drawString(">", textX - 25, textY);
            if (gp.getKeyH().isEnterPressed()) {
                setSubState(0);
            }
        }
    }

    public void options_control(int frameX, int frameY) {
        int textX;
        int textY;

        // TITLE
        String text = "Controls";
        textX = getXforCenteredText(text);
        textY = frameY + gp.getTileSize();
        g2.drawString(text, textX, textY);

        textX = frameX + gp.getTileSize();
        textY += gp.getTileSize();
        g2.drawString("Move", textX, textY);
        textY += gp.getTileSize();
        g2.drawString("Confirm/Attack", textX, textY);
        textY += gp.getTileSize();
        g2.drawString("Shoot/Cast", textX, textY);
        textY += gp.getTileSize();
        g2.drawString("Character Screen", textX, textY);
        textY += gp.getTileSize();
        g2.drawString("Pause", textX, textY);

        // Dòng lỗi: textY += gp.TileSize();
        // Sửa thành:
        textY += gp.getTileSize(); // Đây là dòng 747 theo ảnh bạn gửi.

        g2.drawString("Options", textX, textY);
        textY += gp.getTileSize();

        // KEYS
        textX = frameX + gp.getTileSize() * 6;
        textY = frameY + gp.getTileSize() * 2;
        g2.drawString("WASD", textX, textY);
        textY += gp.getTileSize();
        g2.drawString("ENTER", textX, textY);
        textY += gp.getTileSize();
        g2.drawString("F", textX, textY);
        textY += gp.getTileSize();
        g2.drawString("C", textX, textY);
        textY += gp.getTileSize();
        g2.drawString("P", textX, textY);
        textY += gp.getTileSize();
        g2.drawString("ESC", textX, textY);
        textY += gp.getTileSize();

        // BACK
        textX = frameX + gp.getTileSize();
        textY = frameY + gp.getTileSize() * 9;
        g2.drawString("Back", textX, textY);
        if (getCommandNum() == 0) {
            g2.drawString(">", textX - 25, textY);
            if (gp.getKeyH().isEnterPressed()) {
                setSubState(0);
                setCommandNum(3); // back to control row
            }
        }
    }

    public void options_endGameConfirmation(int frameX, int frameY) {
        int textX = frameX + gp.getTileSize();
        int textY = frameY + gp.getTileSize() * 3;

        setCurrentDialogue("Quit the game and \nreturn to the title screen?");
        for (String line : getCurrentDialogue().split("\n")) {
            g2.drawString(line, textX, textY);
            textY += 40;
        }
        // YES
        String text = "Yes";
        textX = getXforCenteredText(text);
        textY += gp.getTileSize() * 3;
        g2.drawString(text, textX, textY);
        if (getCommandNum() == 0) {
            g2.drawString(">", textX - 25, textY);
            if (gp.getKeyH().isEnterPressed()) {
                setSubState(0);
                gp.getUi().setTitleScreenState(0);
                gp.setGameState(gp.getTitleState());
                gp.resetGame(true);
                gp.stopMusic();
            }
        }

        // NO
        text = "No";
        textX = getXforCenteredText(text);
        textY += gp.getTileSize();
        g2.drawString(text, textX, textY);
        if (getCommandNum() == 1) {
            g2.drawString(">", textX - 25, textY);
            if (gp.getKeyH().isEnterPressed()) {
                setSubState(0);
                setCommandNum(4); // back to end row
            }
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
        textLength = (int) g2.getFontMetrics(g2.getFont()).getStringBounds(text, g2).getWidth();

        int x = gp.getScreenWidth() / 2 - textLength / 2;
        return x;
    }

    public int getXforAlignToRight(String text, int tailX) {
        int textLength;
        textLength = (int) g2.getFontMetrics(g2.getFont()).getStringBounds(text, g2).getWidth();

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
            // OPTIONS STATE
            if (gp.getGameState() == gp.getOptionsState()) {
                drawOptionsScreen();
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

    public void setCommandNum(int commandNum) {
        this.commandNum = commandNum;
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

    public void setCurrentDialogue(String currentDialogue) {
        this.currentDialogue = currentDialogue;
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

    public ArrayList<String> getMessage() {
        return message;
    }

    public ArrayList<Integer> getMessageCounter() {
        return messageCounter;
    }

    public Character getNpc() {
        return npc;
    }

    public void setNpc(Character npc) {
        this.npc = npc;
    }

    public int getNpcSlotCol() {
        return npcSlotCol;
    }

    public void setNpcSlotCol(int npcSlotCol) {
        this.npcSlotCol = npcSlotCol;
    }

    public int getNpcSlotRow() {
        return npcSlotRow;
    }

    public void setNpcSlotRow(int npcSlotRow) {
        this.npcSlotRow = npcSlotRow;
    }

    public int getPlayerSlotCol() {
        return playerSlotCol;
    }

    public void setPlayerSlotCol(int playerSlotCol) {
        this.playerSlotCol = playerSlotCol;
    }

    public int getPlayerSlotRow() {
        return playerSlotRow;
    }

    public void setPlayerSlotRow(int playerSlotRow) {
        this.playerSlotRow = playerSlotRow;
    }

    public Font getPurisaB() {
        return purisaB;
    }

    public int getSubState() {
        return subState;
    }

    public void setSubState(int subState) {
        this.subState = subState;
    }

    public int getTitleScreenState() {
        return titleScreenState;
    }

    public void setTitleScreenState(int titleScreenState) {
        this.titleScreenState = titleScreenState;
    }

    public boolean isInNPCInventory() {
        return isInNPCInventory;
    }

    public void setInNPCInventory(boolean inNPCInventory) {
        isInNPCInventory = inNPCInventory;
    }

    public Entity getDialogueEntity() {
        return dialogueEntity;
    }

    public void setDialogueEntity(Entity dialogueEntity) {
        this.dialogueEntity = dialogueEntity;
    }
}