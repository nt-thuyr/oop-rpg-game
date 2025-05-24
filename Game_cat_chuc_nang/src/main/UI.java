package main;

import entity.Entity;
import object.OBJ_Coin_Bronze;
import object.OBJ_Heart;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class UI {

    GamePanel gp;
    Graphics2D g2;

    public Font determinationSans, purisaB;
    BufferedImage heart_full, heart_half, heart_blank, crystal_full, crystal_blank, coin;

    ArrayList<String> message = new ArrayList<>();
    ArrayList<Integer> messageCounter = new ArrayList<>();

    public String currentDialogue = "";
    public int commandNum = 0;
    public int titleScreenState = 0;
    public int playerSlotCol = 0;
    public int playerSlotRow = 0;
    public int npcSlotCol = 0;
    public int npcSlotRow = 0;

    int subState = 0;
    int counter = 0;
    public Entity npc;
    int charIndex = 0;
    String combinedText = "";
    public boolean isInNPCInventory = true;

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

        Entity heart = new OBJ_Heart(gp);
        heart_full = heart.image;
        heart_half = heart.image2;
        heart_blank = heart.image3;

        Entity bronzeCoin = new OBJ_Coin_Bronze(gp);
        coin = bronzeCoin.down1;
    }

    public void drawPauseScreen() {
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 80F));
        String text = "GAME PAUSED";
        int x = getXforCenteredText(text);
        int y = gp.screenHeight / 2;
        g2.drawString(text, x, y);
    }

    public void drawDialogueScreen() {
        // WINDOW
        int x = gp.tileSize * 3;
        int y = gp.tileSize / 2;
        int width = gp.screenWidth - (gp.tileSize * 6);
        int height = gp.tileSize * 4;

        drawSubWindow(x, y, width, height);


        g2.setFont(g2.getFont().deriveFont(Font.PLAIN,25F));
        x += gp.tileSize;
        y += gp.tileSize;

        if (npc.dialogues[npc.dialogueSet][npc.dialogueIndex] != null) {
            char[] characters = npc.dialogues[npc.dialogueSet][npc.dialogueIndex].toCharArray();

            if (charIndex < characters.length) {
                gp.playSE(17);//Speak sound
                String s = String.valueOf(characters[charIndex]);
                combinedText = combinedText + s; //every loop add one character to combinedText
                currentDialogue = combinedText;

                charIndex++;
            }
            if (gp.keyH.enterPressed) {
                charIndex = 0;
                combinedText = "";
                if (gp.gameState == gp.dialogueState || gp.gameState == gp.cutsceneState) {
                    npc.dialogueIndex++;
                    gp.keyH.enterPressed = false;
                }
            }
        } else //If no text is in the array
        {
            npc.dialogueIndex = 0;
            if (gp.gameState == gp.dialogueState) {
                gp.gameState = gp.playState;
            }
            if (gp.gameState == gp.cutsceneState) {
                gp.csManager.scenePhase++;
            }
        }

        for (String line : currentDialogue.split("\n"))   // splits dialogue until "\n" as a line
        {
            g2.drawString(line, x, y);
            y += 40;
        }
    }

    public void drawCharacterScreen() {
        // CREATE A FRAME
        final int frameX = gp.tileSize * 2;
        final int frameY = gp.tileSize;
        final int frameWidth = gp.tileSize * 16; // Chiều rộng lớn để chứa cả hình ảnh và chỉ số
        final int frameHeight = gp.tileSize * 3; // Chiều cao vừa đủ cho phần trên
        drawSubWindow(frameX, frameY, frameWidth, frameHeight);

        // CHARACTER IMAGE
        int charX = frameX + 20;
        int charY = frameY + 20;
        g2.drawImage(gp.player.down1, charX, charY, gp.tileSize * 2, gp.tileSize * 2, null);

        // CHARACTER STATS
        int statsX = charX + gp.tileSize * 3; // Bên phải hình ảnh nhân vật
        int statsY = charY + 24;

        g2.setColor(Color.white);
        g2.setFont(g2.getFont().deriveFont(20F));
        final int lineHeight = 35;
        final int spacing = 105;

        // NAMES AND VALUES
        g2.drawString("HP", statsX, statsY);
        String hpValue = gp.player.life + "/" + gp.player.maxLife;
        g2.drawString(hpValue, statsX + spacing, statsY);

        g2.drawString("Strength", statsX, statsY + lineHeight);
        g2.drawString(String.valueOf(gp.player.strength), statsX + spacing, statsY + lineHeight);

        g2.drawString("Dexterity", statsX, statsY + lineHeight * 2);
        g2.drawString(String.valueOf(gp.player.dexterity), statsX + spacing, statsY + lineHeight * 2);

        statsX += gp.tileSize * 4; // Dịch sang phải để hiển thị các chỉ số khác
        g2.drawString("Attack", statsX, statsY);
        g2.drawString(String.valueOf(gp.player.attack), statsX + spacing, statsY);

        g2.drawString("Defense", statsX, statsY + lineHeight);
        g2.drawString(String.valueOf(gp.player.defense), statsX + spacing, statsY + lineHeight);

        g2.drawString("Coin", statsX, statsY + lineHeight * 2);
        g2.drawString(String.valueOf(gp.player.coin), statsX + spacing, statsY + +lineHeight * 2);

        statsX += gp.tileSize * 4; // Dịch sang phải để hiển thị các chỉ số khác
        g2.drawString("Experience", statsX, statsY);
        String expValue = gp.player.exp + "/" + gp.player.nextLevelExp;
        g2.drawString(expValue, statsX + spacing + 20, statsY);

        g2.drawString("Level", statsX, statsY + lineHeight);
        g2.drawString(String.valueOf(gp.player.level), statsX + spacing + 20, statsY + lineHeight);
    }

    public void drawTransition() {
        counter++;
        g2.setColor(new Color(0, 0, 0, counter * 5));
        g2.fillRect(0, 0, gp.screenWidth2, gp.screenHeight2); // screen gets darker

        if (counter == 50) //the transition is done
        {
            counter = 0;
            gp.gameState = gp.playState;
            gp.player.worldX = gp.tileSize * gp.eHandler.tempCol;
            gp.player.worldY = gp.tileSize * gp.eHandler.tempRow;
            gp.currentMap = gp.eHandler.tempMap;
            gp.eHandler.previousEventX = gp.player.worldX;
            gp.eHandler.previousEventY = gp.player.worldY;
            gp.changeArea();
        }
    }

    public void drawPlayerInfo(Entity entity) {
        int frameX = 0;
        int frameY = 0;
        int frameWidth = 0;
        int frameHeight = 0;
        int slotCol = 0;
        int slotRow = 0;

        if (entity == gp.player) {
            // FRAME FOR INVENTORY SCREEN (CHỈ LÀ VỊ TRÍ TỔNG QUAN)
            frameX = gp.tileSize * 2;
            frameY = gp.tileSize * 6; // Dưới characterScreen
            frameWidth = gp.tileSize * 16;  // Chiều rộng bằng characterScreen

            slotCol = playerSlotCol;
            slotRow = playerSlotRow;
        } else {
            // FRAME FOR NPC INVENTORY
            frameX = gp.tileSize * 2;
            frameY = gp.tileSize;
            frameWidth = gp.tileSize * 6;
            frameHeight = gp.tileSize * 5;
            slotCol = npcSlotCol;
            slotRow = npcSlotRow;
        }

        // INVENTORY FRAME
        int invFrameX = frameX;
        int invFrameY = frameY;

        // SLOT (4 hàng x 5 cột)
        final int slotXstart = invFrameX + 20;
        final int slotYstart = invFrameY + 20;
        int slotX = slotXstart;
        int slotY = slotYstart;
        int slotSize = gp.tileSize + 3;
        int maxCols = 5;

        int invFrameWidth = slotSize * maxCols + 40;
        int invFrameHeight = slotSize * 4 + 40;
        drawSubWindow(invFrameX, invFrameY, invFrameWidth, invFrameHeight);

        // DRAW ITEMS
        for (int i = 0; i < entity.inventory.size(); i++) {
            // EQUIP CURSOR
            if (entity.inventory.get(i) == entity.currentWeapon ||
                    entity.inventory.get(i) == entity.currentShield ||
                    entity.inventory.get(i) == entity.currentLight) {
                g2.setColor(new Color(240, 190, 90));
                g2.fillRoundRect(slotX, slotY, gp.tileSize, gp.tileSize, 10, 10);
            }

            g2.drawImage(entity.inventory.get(i).down1, slotX, slotY, null);

            // DISPLAY AMOUNT
            if (entity == gp.player && entity.inventory.get(i).amount > 1) {
                g2.setFont(g2.getFont().deriveFont(27F));
                int amountX;
                int amountY;

                String s = "" + entity.inventory.get(i).amount;
                amountX = getXforAlignToRight(s, slotX + 44);
                amountY = slotY + gp.tileSize;

                g2.setColor(new Color(60, 60, 60));
                g2.drawString(s, amountX, amountY);
                g2.setColor(Color.white);
                g2.drawString(s, amountX - 3, amountY - 3);
            }

            slotX += slotSize;
            if ((i + 1) % maxCols == 0) { // Chuyển dòng sau mỗi 5 cột
                slotX = slotXstart;
                slotY += slotSize;
            }
        }

        // CURSOR
        int cursorX = slotXstart + (slotSize * slotCol);
        int cursorY = slotYstart + (slotSize * slotRow);
        int cursorWidth = gp.tileSize;
        int cursorHeight = gp.tileSize;

        g2.setColor(Color.white);
        g2.setStroke(new BasicStroke(3));
        g2.drawRoundRect(cursorX, cursorY, cursorWidth, cursorHeight, 10, 10);

        // DESCRIPTION FRAME đối xứng với inventory
        int dFrameWidth = frameWidth - invFrameWidth - gp.tileSize * 2;
        int dFrameX = invFrameX + frameWidth - dFrameWidth;
        drawSubWindow(dFrameX, invFrameY, dFrameWidth, invFrameHeight);

        // DRAW DESCRIPTION TEXT
        int textX = dFrameX + 20;
        int textY = invFrameY + gp.tileSize;
        g2.setFont(g2.getFont().deriveFont(28F));

        int itemIndex = getItemIndexOnSlot(slotCol, slotRow);
        if (itemIndex < entity.inventory.size()) {
            for (String line : entity.inventory.get(itemIndex).description.split("\n")) {
                g2.drawString(line, textX, textY);
                textY += 32;
            }
        }
    }

    public void drawPlayerInfo(Entity entity, boolean cursor, int frameX, int frameY, int frameWidth, int frameHeight, int slotCol, int slotRow) {
        // Inventory Frame
        int slotStartX = frameX + 20;
        int slotStartY = frameY + 20;
        int slotX = slotStartX;
        int slotY = slotStartY;
        int slotSize = gp.tileSize + 3;
        int maxCols = 5;

        int invFrameWidth = slotSize * maxCols + 40;
        int invFrameHeight = slotSize * 4 + 40;
        drawSubWindow(frameX, frameY, invFrameWidth, invFrameHeight);
      
        // Draw Items
        for (int i = 0; i < entity.inventory.size(); i++) {
            if (entity.inventory.get(i) == entity.currentWeapon ||
                    entity.inventory.get(i) == entity.currentShield ||
                    entity.inventory.get(i) == entity.currentLight) {
                g2.setColor(new Color(240, 190, 90));
                g2.fillRoundRect(slotX, slotY, gp.tileSize, gp.tileSize, 10, 10);
            }

            g2.drawImage(entity.inventory.get(i).down1, slotX, slotY, null);

            if (entity == gp.player && entity.inventory.get(i).amount > 1) {
                g2.setFont(g2.getFont().deriveFont(32f));
                int amountX = getXforAlignToRight("" + entity.inventory.get(i).amount, slotX + 44);
                int amountY = slotY + gp.tileSize;
                g2.setColor(new Color(60, 60, 60));
                g2.drawString("" + entity.inventory.get(i).amount, amountX, amountY);
                g2.setColor(Color.white);
                g2.drawString("" + entity.inventory.get(i).amount, amountX - 3, amountY - 3);
            }

            slotX += slotSize;
            if ((i + 1) % maxCols == 0) {
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
            g2.drawRoundRect(cursorX, cursorY, gp.tileSize, gp.tileSize, 10, 10);

            // Description and Price
            int itemIndex = getItemIndexOnSlot(slotCol, slotRow);
            if (itemIndex < entity.inventory.size()) {
                int dFrameX = frameX + invFrameWidth + gp.tileSize;
                int dFrameWidth = gp.tileSize * 6;
                drawSubWindow(dFrameX, frameY, dFrameWidth, invFrameHeight);

                int textX = dFrameX + 20;
                int textY = frameY + gp.tileSize;
                g2.setFont(g2.getFont().deriveFont(28F));
                for (String line : entity.inventory.get(itemIndex).description.split("\n")) {
                    g2.drawString(line, textX, textY);
                    textY += 32;
                }

                // Price
                int price = entity == npc ? entity.inventory.get(itemIndex).price : entity.inventory.get(itemIndex).price / 2;
                textY += 32;
                g2.drawString("Price: " + price, textX, textY);
                g2.drawImage(coin, textX + gp.tileSize * 2, textY - 28, 36, 36, null);

                // Player's coin
                textY += 32;
                g2.drawString("Your Coin: " + gp.player.coin, textX, textY);
                g2.drawImage(coin, textX + gp.tileSize * 2 + 42, textY - 28, 36, 36, null);
            }
        }
    }

    public void drawTradeScreen() {
        switch (subState) {
            case 0:
                trade_select();
                break;
            case 1:
                trade_screen();
                break;
        }
        gp.keyH.enterPressed = false;
    }

    public void trade_select() {
        npc.dialogueSet = 0;
        drawDialogueScreen();

        int x = gp.tileSize * 15;
        int y = gp.tileSize * 4;
        int width = gp.tileSize * 3;
        int height = gp.tileSize * 3;
        drawSubWindow(x, y, width, height);

        x += gp.tileSize;
        y += gp.tileSize;
        g2.drawString("Trade", x, y);
        if (commandNum == 0) {
            g2.drawString(">", x - 24, y);
            if (gp.keyH.enterPressed) {
                subState = 1;
            }
        }
        y += gp.tileSize;
        g2.drawString("Leave", x, y);
        if (commandNum == 1) {
            g2.drawString(">", x - 24, y);
            if (gp.keyH.enterPressed) {
                commandNum = 0;
                npc.startDialogue(npc, 1);
            }
        }
    }

    public void trade_screen() {
        // NPC Inventory (Top Half)
        int frameX = gp.tileSize * 2;
        int frameY = gp.tileSize;
        int frameWidth = gp.tileSize * 6;
        int frameHeight = gp.tileSize * 5;
        drawPlayerInfo(npc, isInNPCInventory, frameX, frameY, frameWidth, frameHeight, npcSlotCol, npcSlotRow);

        // Player Inventory (Bottom Half)
        frameY = gp.tileSize * 6 + 10;
        drawPlayerInfo(gp.player, !isInNPCInventory, frameX, frameY, frameWidth, frameHeight, playerSlotCol, playerSlotRow);
    }

    public int getItemIndexOnSlot(int slotCol, int slotRow) {
        return slotCol + (slotRow * 5);
    }

    public void drawPlayerLife() {
        //gp.player.life = 5;
        int x = gp.tileSize / 2;
        int y = gp.tileSize / 2;
        int i = 0;
        int iconSize = 32;

        //DRAW MAX LIFE (BLANK)
        while (i < gp.player.maxLife / 2) {
            g2.drawImage(heart_blank, x, y, iconSize, iconSize, null);
            i++;
            x += iconSize;

        }
        //reset
        x = gp.tileSize / 2;
        y = gp.tileSize / 2;
        i = 0;
        //DRAW CURRENT HEART // ITS LIKE COLORING THE BLANK HEARTS
        while (i < gp.player.life) {
            g2.drawImage(heart_half, x, y, iconSize, iconSize, null);
            i++;
            if (i < gp.player.life) {
                g2.drawImage(heart_full, x, y, iconSize, iconSize, null);
            }
            i++;
            x += iconSize;
        }

        // DRAW EXP BAR
        int expBarX = gp.tileSize / 2;
        int expBarY = y + iconSize + 12;
        int expBarWidth = gp.tileSize * 4;  // Chiều dài thanh EXP
        int expBarHeight = 12;              // Chiều cao thanh EXP
        // Background (empty EXP)
        g2.setColor(new Color(50, 50, 50));
        g2.fillRoundRect(expBarX, expBarY, expBarWidth, expBarHeight, 10, 10);
        // Filled EXP
        double expRatio = (double) gp.player.exp / gp.player.nextLevelExp;
        int filledExpWidth = (int) (expBarWidth * expRatio);
        g2.setColor(new Color(0, 255, 0)); // Màu xanh cho EXP
        g2.fillRoundRect(expBarX, expBarY, filledExpWidth, expBarHeight, 10, 10);

        // Draw level text
        int levelTextX = expBarX + 12;
        int levelTextY = expBarY + 12;
        g2.setColor(Color.white);
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 14F));
        String text = "Level " + gp.player.level;
        g2.drawString(text, levelTextX, levelTextY);
    }

    public void drawMonsterLife() {
        //Monster HP Bar
        for (int i = 0; i < gp.monster[1].length; i++) {
            Entity monster = gp.monster[gp.currentMap][i];

            if (monster != null && monster.inCamera()) {
                if (monster.hpBarOn && !monster.boss) {
                    double oneScale = (double) gp.tileSize / monster.maxLife; // (bar lenght / maxlife) Ex: if monster hp = 2, tilesize = 48px. So, 1 hp = 24px
                    double hpBarValue = oneScale * monster.life;

                    if (hpBarValue < 0) //Ex: You attack 5 hp to monster which has 3 hp. Monster's hp will be -2 and bar will ofset to left. To avoid that check if hpBarValue less than 0.
                    {
                        hpBarValue = 0;
                    }

                    g2.setColor(new Color(35, 35, 35));
                    g2.fillRect(monster.getScreenX() - 1, monster.getScreenY() - 16, gp.tileSize + 2, 12);

                    g2.setColor(new Color(255, 0, 30));
                    g2.fillRect(monster.getScreenX(), monster.getScreenY() - 15, (int) hpBarValue, 10);

                    monster.hpBarCounter++;
                    if (monster.hpBarCounter > 600)  // 10
                    {
                        monster.hpBarCounter = 0;
                        monster.hpBarOn = false;
                    }
                } else if (monster.boss) {
                    double oneScale = (double) gp.tileSize * 8 / monster.maxLife; // (bar lenght / maxlife) Ex: if monster hp = 2, tilesize = 48px. So, 1 hp = 24px
                    double hpBarValue = oneScale * monster.life;
                    int x = gp.screenWidth / 2 - gp.tileSize * 4;
                    int y = gp.tileSize * 10;

                    if (hpBarValue < 0)  //Ex: You attack 5 hp to monster which has 3 hp. Monster's hp will be -2 and bar will offset to left. To avoid that check if hpBarValue less than 0.
                    {
                        hpBarValue = 0;
                    }

                    g2.setColor(new Color(35, 35, 35));
                    g2.fillRect(x - 1, y - 1, gp.tileSize * 8 + 2, 22);

                    g2.setColor(new Color(255, 0, 30));
                    g2.fillRect(x, y, (int) hpBarValue, 20);

                    g2.setFont(g2.getFont().deriveFont(Font.BOLD, 22f));
                    g2.setColor(Color.white);
                    g2.drawString(monster.name, x + 4, y - 10);
                }
            }
        }

    }

    public void drawMessage() {
        int messageX = gp.tileSize;
        int messageY = gp.tileSize * 4;
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 24F));


        for (int i = 0; i < message.size(); i++) {
            if (message.get(i) != null) {
                //Shadow
                g2.setColor(Color.black);
                g2.drawString(message.get(i), messageX + 2, messageY + 2);
                //Text
                g2.setColor(Color.white);
                g2.drawString(message.get(i), messageX, messageY);

                int counter = messageCounter.get(i) + 1; //messageCounter++
                messageCounter.set(i, counter);           //set the counter to the array
                messageY += 50;

                if (messageCounter.get(i) > 150)          //display 2.5 seconds
                {
                    message.remove(i);
                    messageCounter.remove(i);
                }
            }

        }
    }

    public void drawTitleScreen() {
        g2.setColor(new Color(0, 0, 0));             // FILL BACKGROUND BLACK
        g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);
        //MAIN MENU
        if (titleScreenState == 0) {

            //TITLE NAME
            g2.setFont(g2.getFont().deriveFont(Font.BOLD, 90F));
            String text = "Blue Boy Adventure\n";
            int x = getXforCenteredText(text);
            int y = gp.tileSize * 3;
            //SHADOW
            g2.setColor(Color.gray);
            g2.drawString(text, x + 5, y + 5);
            //MAIN COLOR
            g2.setColor(Color.white);
            g2.drawString(text, x, y);

            //BLUE BOY IMAGE
            x = gp.screenWidth / 2 - (gp.tileSize * 2) / 2;
            y += gp.tileSize * 2;
            g2.drawImage(gp.player.down1, x, y, gp.tileSize * 2, gp.tileSize * 2, null);

            //MENU
            g2.setFont(g2.getFont().deriveFont(Font.BOLD, 48F));

            text = "NEW GAME";
            x = getXforCenteredText(text);
            y += gp.tileSize * 3.5;
            g2.drawString(text, x, y);
            if (commandNum == 0) {
                g2.drawString(">", x - gp.tileSize, y);
            }

            text = "QUIT";
            x = getXforCenteredText(text);
            y += gp.tileSize;
            g2.drawString(text, x, y);
            if (commandNum == 1) {
                g2.drawString(">", x - gp.tileSize, y);
            }
        }
        //SECOND SCREEN
        else if (titleScreenState == 1) {
            //CLASS SELECTION SCREEN
            g2.setColor(Color.white);
            g2.setFont(g2.getFont().deriveFont(40F));

            String text = "- You are -";
            int x = getXforCenteredText(text);
            int y = gp.tileSize * 3;
            g2.drawString(text, x, y);

            text = "Fighter";
            x = getXforCenteredText(text);
            y += gp.tileSize * 3;
            g2.drawString(text, x, y);
            if (commandNum == 0) {
                g2.drawString(">", x - gp.tileSize, y);
            }

        }

    }

    public void drawGameOverScreen() {
        g2.setColor(new Color(0, 0, 0, 150)); //Half-black
        g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);

        int x;
        int y;
        String text;
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 110f));
        text = "Game Over";

        //Shadow
        g2.setColor(Color.BLACK);
        x = getXforCenteredText(text);
        y = gp.tileSize * 4;
        g2.drawString(text, x, y);
        //Text
        g2.setColor(Color.white);
        g2.drawString(text, x - 4, y - 4);

        //RETRY
        g2.setFont(g2.getFont().deriveFont(50f));
        text = "Retry";
        x = getXforCenteredText(text);
        y += gp.tileSize * 4;
        g2.drawString(text, x, y);
        if (commandNum == 0) {
            g2.drawString(">", x - 40, y);
        }

        //BACK TO THE TITLE SCREEN
        text = "Quit";
        x = getXforCenteredText(text);
        y += 55;
        g2.drawString(text, x, y);
        if (commandNum == 1) {
            g2.drawString(">", x - 40, y);
        }

    }

    public void drawOptionsScreen() {
        g2.setColor(Color.white);
        g2.setFont(g2.getFont().deriveFont(30F));

        // SUB WINDOW

        int frameX = gp.tileSize * 6;
        int frameY = gp.tileSize;
        int frameWidth = gp.tileSize * 8;
        int frameHeight = gp.tileSize * 10;

        drawSubWindow(frameX, frameY, frameWidth, frameHeight);

        switch (subState) {
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
        gp.keyH.enterPressed = false;
    }

    public void options_top(int frameX, int frameY) {
        int textX;
        int textY;

        //TITLE
        String text = "Options";
        textX = getXforCenteredText(text);
        textY = frameY + gp.tileSize;
        g2.drawString(text, textX, textY);

        //FULL SCREEN ON/OFF
        textX = frameX + gp.tileSize;
        textY += gp.tileSize * 2;
        g2.drawString("Full Screen", textX, textY);
        if (commandNum == 0) {
            g2.drawString(">", textX - 25, textY);
            if (gp.keyH.enterPressed) {
                if (!gp.fullScreenOn) {
                    gp.fullScreenOn = true;
                } else if (gp.fullScreenOn) {
                    gp.fullScreenOn = false;
                }
                subState = 1;
            }
        }

        //MUSIC
        textY += gp.tileSize;
        g2.drawString("Music", textX, textY);
        if (commandNum == 1) {
            g2.drawString(">", textX - 25, textY);
        }

        //SE
        textY += gp.tileSize;
        g2.drawString("SE", textX, textY);
        if (commandNum == 2) {
            g2.drawString(">", textX - 25, textY);
        }

        //CONTROLS
        textY += gp.tileSize;
        g2.drawString("Controls", textX, textY);
        if (commandNum == 3) {
            g2.drawString(">", textX - 25, textY);
            if (gp.keyH.enterPressed) {
                subState = 2;
                commandNum = 0;
            }
        }

        //END GAME
        textY += gp.tileSize;
        g2.drawString("End Game", textX, textY);
        if (commandNum == 4) {
            g2.drawString(">", textX - 25, textY);
            if (gp.keyH.enterPressed) {
                subState = 3;
                commandNum = 0;
            }
        }

        //BACK
        textY += gp.tileSize * 2;
        g2.drawString("Back", textX, textY);
        if (commandNum == 5) {
            g2.drawString(">", textX - 25, textY);
            if (gp.keyH.enterPressed) {
                gp.gameState = gp.playState;
                commandNum = 0;
            }
        }

        //FULL SCREEN CHECK BOX
        textX = frameX + (int) (gp.tileSize * 4.5);
        textY = frameY + gp.tileSize * 2 + 24;
        g2.setStroke(new BasicStroke(3));
        g2.drawRect(textX, textY, 24, 24);
        if (gp.fullScreenOn) {
            g2.fillRect(textX, textY, 24, 24);
        }

        //MUSIC VOLUME
        textY += gp.tileSize;
        g2.drawRect(textX, textY, 120, 24); //120/5 = 24px = 1 scale
        int volumeWidth = 24 * gp.music.volumeScale;
        g2.fillRect(textX, textY, volumeWidth, 24);

        //SE VOLUME
        textY += gp.tileSize;
        g2.drawRect(textX, textY, 120, 24);
        volumeWidth = 24 * gp.se.volumeScale;
        g2.fillRect(textX, textY, volumeWidth, 24);

    }

    public void options_fullScreenNotification(int frameX, int frameY) {
        int textX = frameX + gp.tileSize;
        int textY = frameY + gp.tileSize * 3;

        currentDialogue = "The change will take \neffect after restarting \nthe game.";
        for (String line : currentDialogue.split("\n")) {
            g2.drawString(line, textX, textY);
            textY += 40;
        }

        //BACK
        textY = frameY + gp.tileSize * 9;
        g2.drawString("Back", textX, textY);
        if (commandNum == 0) {
            g2.drawString(">", textX - 25, textY);
            if (gp.keyH.enterPressed) {
                subState = 0;
            }
        }
    }

    public void options_control(int frameX, int frameY) {
        int textX;
        int textY;

        //TITLE
        String text = "Controls";
        textX = getXforCenteredText(text);
        textY = frameY + gp.tileSize;
        g2.drawString(text, textX, textY);

        textX = frameX + gp.tileSize;
        textY += gp.tileSize;
        g2.drawString("Move", textX, textY);
        textY += gp.tileSize;
        g2.drawString("Confirm/Attack", textX, textY);
        textY += gp.tileSize;
        g2.drawString("Shoot/Cast", textX, textY);
        textY += gp.tileSize;
        g2.drawString("Character Screen", textX, textY);
        textY += gp.tileSize;
        g2.drawString("Pause", textX, textY);
        textY += gp.tileSize;
        g2.drawString("Options", textX, textY);
        textY += gp.tileSize;

        //KEYS
        textX = frameX + gp.tileSize * 6;
        textY = frameY + gp.tileSize * 2;
        g2.drawString("WASD", textX, textY);
        textY += gp.tileSize;
        g2.drawString("ENTER", textX, textY);
        textY += gp.tileSize;
        g2.drawString("F", textX, textY);
        textY += gp.tileSize;
        g2.drawString("C", textX, textY);
        textY += gp.tileSize;
        g2.drawString("P", textX, textY);
        textY += gp.tileSize;
        g2.drawString("ESC", textX, textY);
        textY += gp.tileSize;


        //BACK
        textX = frameX + gp.tileSize;
        textY = frameY + gp.tileSize * 9;
        g2.drawString("Back", textX, textY);
        if (commandNum == 0) {
            g2.drawString(">", textX - 25, textY);
            if (gp.keyH.enterPressed) {
                subState = 0;
                commandNum = 3; //back to control row
            }
        }
    }

    public void options_endGameConfirmation(int frameX, int frameY) {
        int textX = frameX + gp.tileSize;
        int textY = frameY + gp.tileSize * 3;

        currentDialogue = "Quit the game and \nreturn to the title screen?";
        for (String line : currentDialogue.split("\n")) {
            g2.drawString(line, textX, textY);
            textY += 40;
        }
        //YES
        String text = "Yes";
        textX = getXforCenteredText(text);
        textY += gp.tileSize * 3;
        g2.drawString(text, textX, textY);
        if (commandNum == 0) {
            g2.drawString(">", textX - 25, textY);
            if (gp.keyH.enterPressed) {
                subState = 0;
                gp.ui.titleScreenState = 0;
                gp.gameState = gp.titleState;
                gp.resetGame(true);
                gp.stopMusic();
            }
        }

        //NO
        text = "No";
        textX = getXforCenteredText(text);
        textY += gp.tileSize;
        g2.drawString(text, textX, textY);
        if (commandNum == 1) {
            g2.drawString(">", textX - 25, textY);
            if (gp.keyH.enterPressed) {
                subState = 0;
                commandNum = 4; //back to end row
            }
        }
    }

    public void drawSubWindow(int x, int y, int width, int height) {
        Color c = new Color(0, 0, 0, 210);  // R,G,B, alfa(opacity)
        g2.setColor(c);
        g2.fillRoundRect(x, y, width, height, 35, 35);

        c = new Color(255, 255, 255);
        g2.setColor(c);
        g2.setStroke(new BasicStroke(5));    // 5 = width of outlines of graphics
        g2.drawRoundRect(x + 5, y + 5, width - 10, height - 10, 25, 25);

    }

    public int getXforCenteredText(String text) {
        int textLenght;
        textLenght = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth(); // Gets width of text.
        int x = gp.screenWidth / 2 - textLenght / 2;
        return x;
    }

    public int getXforAlignToRight(String text, int tailX) {
        int textLenght;
        textLenght = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth(); // Gets width of text.
        int x = tailX - textLenght;
        return x;
    }

    public void addMessage(String text) {
        message.add(text);
        messageCounter.add(0);
    }

    public void draw(Graphics2D g2) {
        this.g2 = g2;
        g2.setFont(determinationSans);
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);  // Anti Aliasing // Smoothes the text
        g2.setColor(Color.white);

        //TITLE STATE
        if (gp.gameState == gp.titleState) {
            drawTitleScreen();
        }
        //OTHERS
        else {
            //PLAY STATE
            if (gp.gameState == gp.playState) {
                drawPlayerLife();
                drawMonsterLife();
                drawMessage();
            }
            //PAUSE STATE
            if (gp.gameState == gp.pauseState) {
                drawPlayerLife();
                drawPauseScreen();
            }
            //DIALOGUE STATE
            if (gp.gameState == gp.dialogueState) {
                drawDialogueScreen();
            }
            //CHARACTER STATE
            if (gp.gameState == gp.characterState) {
                drawCharacterScreen();
                drawPlayerInfo(gp.player);
            }
            //OPTIONS STATE
            if (gp.gameState == gp.optionsState) {
                drawOptionsScreen();
            }
            //GAME OVER STATE
            if (gp.gameState == gp.gameOverState) {
                drawGameOverScreen();
            }
            //TRANSITION STATE
            if (gp.gameState == gp.transitionState) {
                drawTransition();
            }
            //TRADE STATE
            if (gp.gameState == gp.tradeState) {
                drawTradeScreen();
            }
        }
    }
}