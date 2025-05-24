package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {

    GamePanel gp;
    public boolean upPressed, downPressed, leftPressed,rightPressed,enterPressed,shotKeyPressed, spacePressed;
    //DEBUG
    public boolean showDebugText = false;
    public boolean godModeOn = false;

    @Override
    public void keyTyped(KeyEvent e) {
    }

    public KeyHandler(GamePanel gp)
    {
        this.gp = gp;
    }
    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();

        //TITLE STATE
        if(gp.gameState == gp.titleState) {
            titleState(code);
        }
        // PLAY STATE
        else if(gp.gameState == gp.playState)
        {
            playState(code);
        }
        // PAUSE STATE
        else if(gp.gameState == gp.pauseState)
        {
            pauseState(code);
        }
        //DIALOGUE STATE
        else if(gp.gameState == gp.dialogueState || gp.gameState == gp.cutsceneState)
        {
            dialogueState(code);
        }
        // CHARACTER STATE
        else if(gp.gameState == gp.characterState)
        {
            characterState(code);
        }
        // GAMEOVER STATE
        else if(gp.gameState == gp.gameOverState)
        {
            gameOverState(code);
        }
        // TRADE STATE
        else if(gp.gameState == gp.tradeState)
        {
            tradeState(code);
        }
        // MAP STATE
        else if(gp.gameState == gp.mapState)
        {
            mapState(code);
        }
    }

    public void titleState(int code)
    {
        //MAIN MENU
        if (gp.ui.titleScreenState == 0) {
            if (code == KeyEvent.VK_W) {
                gp.ui.commandNum--;
                if (gp.ui.commandNum < 0) {
                    gp.ui.commandNum = 1; // chỉ còn 2 lựa chọn: NEW GAME và QUIT
                }
            }
            if (code == KeyEvent.VK_S) {
                gp.ui.commandNum++;
                if (gp.ui.commandNum > 1) {
                    gp.ui.commandNum = 0;
                }
            }
            if (code == KeyEvent.VK_ENTER) {
                if (gp.ui.commandNum == 0) {
                    // CHUYỂN NGAY SANG MÀN HÌNH CHỌN LỚP nhưng chỉ hiển thị fighter, ko cần chọn nữa
                    gp.ui.titleScreenState = 1;
                    gp.ui.commandNum = 0; // luôn highlight Fighter
                }
                else if (gp.ui.commandNum == 1) {
                    System.exit(0);
                }
            }
        }
        else if (gp.ui.titleScreenState == 1) {
            // Bỏ qua việc chọn class, nhấn enter là vào game luôn
            if (code == KeyEvent.VK_ENTER) {
                // Vào thẳng game luôn
                gp.gameState = gp.playState;
                gp.playMusic(0);
            }
        }
    }
    public void playState(int code)
    {
        if(code == KeyEvent.VK_W)
        {
            upPressed = true;
        }
        if(code == KeyEvent.VK_S)
        {
            downPressed = true;
        }
        if(code == KeyEvent.VK_A)
        {
            leftPressed = true;
        }
        if(code == KeyEvent.VK_D)
        {
            rightPressed = true;
        }
        if(code == KeyEvent.VK_P)
        {
            gp.gameState = gp.pauseState;
        }
        if(code == KeyEvent.VK_C)
        {
            gp.gameState = gp.characterState;
        }
        if(code == KeyEvent.VK_ENTER)
        {
            enterPressed = true;
        }
        if(code == KeyEvent.VK_M)
        {
            gp.gameState = gp.mapState;
        }
        if(code == KeyEvent.VK_SPACE)
        {
            spacePressed = true;
        }
    }
    public void pauseState(int code)
    {
        if(code == KeyEvent.VK_ESCAPE)
        {
            gp.gameState = gp.playState;
        }
    }
    public void dialogueState(int code)
    {
        if(code == KeyEvent.VK_ENTER)
        {
            enterPressed = true;
        }
    }
    public void characterState(int code)
    {
        if(code == KeyEvent.VK_C)
        {
            gp.gameState = gp.playState;
        }

        if(code == KeyEvent.VK_ENTER)
        {
            gp.player.selectItem();
        }
        playerInventory(code);
    }
    public void gameOverState(int code)
    {
        if(code == KeyEvent.VK_W)
        {
            gp.ui.commandNum--;
            if(gp.ui.commandNum < 0)
            {
                gp.ui.commandNum = 1;
            }
            gp.playSE(9);
        }
        if(code == KeyEvent.VK_S)
        {
            gp.ui.commandNum++;
            if(gp.ui.commandNum > 1)
            {
                gp.ui.commandNum = 0;
            }
            gp.playSE(9);
        }
        if(code == KeyEvent.VK_ENTER)
        {
            if(gp.ui.commandNum == 0) //RETRY, reset position, life, mana, monsters, npcs...
            {
                gp.gameState = gp.playState;
                gp.resetGame(false);
                gp.playMusic(0);
            }
            else if(gp.ui.commandNum == 1) //QUIT, reset everything
            {
                gp.ui.titleScreenState = 0;
                gp.gameState = gp.titleState;
                gp.resetGame(true);
            }
        }
    }

    public void tradeState(int code) {
        if (code == KeyEvent.VK_ENTER) {
            enterPressed = true;
        }
        if (gp.ui.subState == 0) {
            if (code == KeyEvent.VK_W) {
                gp.ui.commandNum--;
                if (gp.ui.commandNum < 0) {
                    gp.ui.commandNum = 1;
                }
                gp.playSE(9);
            }
            if (code == KeyEvent.VK_S) {
                gp.ui.commandNum++;
                if (gp.ui.commandNum > 1) {
                    gp.ui.commandNum = 0;
                }
                gp.playSE(9);
            }
        } else if (gp.ui.subState == 1) {
            tradeScreenState(code);
            if (code == KeyEvent.VK_ESCAPE) {
                gp.ui.subState = 0;
            }
        } else if (gp.ui.subState == 2) {
            if (code == KeyEvent.VK_W) {
                gp.ui.commandNum--;
                if (gp.ui.commandNum < 0) {
                    gp.ui.commandNum = 1;
                }
                gp.playSE(9);
            }
            if (code == KeyEvent.VK_S) {
                gp.ui.commandNum++;
                if (gp.ui.commandNum > 1) {
                    gp.ui.commandNum = 0;
                }
                gp.playSE(9);
            }
            if (code == KeyEvent.VK_ESCAPE) {
                gp.ui.subState = 0;
            }
        }
    }

    public void tradeScreenState(int code) {
        if (code == KeyEvent.VK_W) {
            if (gp.ui.isInNPCInventory) {
                if (gp.ui.npcSlotRow != 0) {
                    gp.ui.npcSlotRow--;
                    gp.playSE(9);
                } else if (gp.ui.npcSlotRow == 0) {
                    gp.ui.isInNPCInventory = false;
                    gp.ui.playerSlotRow = 3;
                    gp.ui.playerSlotCol = gp.ui.npcSlotCol;
                    gp.playSE(9);
                }
            } else {
                if (gp.ui.playerSlotRow != 0) {
                    gp.ui.playerSlotRow--;
                    gp.playSE(9);
                } else if (gp.ui.playerSlotRow == 0) {
                    gp.ui.isInNPCInventory = true;
                    gp.ui.npcSlotRow = 3;
                    gp.ui.npcSlotCol = gp.ui.playerSlotCol;
                    gp.playSE(9);
                }
            }
        }
        if (code == KeyEvent.VK_S) {
            if (gp.ui.isInNPCInventory) {
                if (gp.ui.npcSlotRow != 3) {
                    gp.ui.npcSlotRow++;
                    gp.playSE(9);
                } else if (gp.ui.npcSlotRow == 3) {
                    gp.ui.isInNPCInventory = false;
                    gp.ui.playerSlotRow = 0;
                    gp.ui.playerSlotCol = gp.ui.npcSlotCol;
                    gp.playSE(9);
                }
            } else {
                if (gp.ui.playerSlotRow != 3) {
                    gp.ui.playerSlotRow++;
                    gp.playSE(9);
                } else if (gp.ui.playerSlotRow == 3) {
                    gp.ui.isInNPCInventory = true;
                    gp.ui.npcSlotRow = 0;
                    gp.ui.npcSlotCol = gp.ui.playerSlotCol;
                    gp.playSE(9);
                }
            }
        }
        if (code == KeyEvent.VK_A) {
            if (gp.ui.isInNPCInventory) {
                if (gp.ui.npcSlotCol != 0) {
                    gp.ui.npcSlotCol--;
                    gp.playSE(9);
                }
            } else {
                if (gp.ui.playerSlotCol != 0) {
                    gp.ui.playerSlotCol--;
                    gp.playSE(9);
                }
            }
        }
        if (code == KeyEvent.VK_D) {
            if (gp.ui.isInNPCInventory) {
                if (gp.ui.npcSlotCol != 4) {
                    gp.ui.npcSlotCol++;
                    gp.playSE(9);
                }
            } else {
                if (gp.ui.playerSlotCol != 4) {
                    gp.ui.playerSlotCol++;
                    gp.playSE(9);
                }
            }
        }
        if (code == KeyEvent.VK_ENTER && enterPressed) {
            if (gp.ui.isInNPCInventory) {
                int itemIndex = gp.ui.getItemIndexOnSlot(gp.ui.npcSlotCol, gp.ui.npcSlotRow);
                if (itemIndex < gp.ui.npc.inventory.size()) {
                    if (gp.ui.npc.inventory.get(itemIndex).price > gp.player.coin) {
                        gp.ui.subState = 0;
                        gp.ui.npc.startDialogue(gp.ui.npc, 2); // Not enough coin
                        enterPressed = false;
                    } else {
                        if (gp.player.canObtainItem(gp.ui.npc.inventory.get(itemIndex))) {
                            gp.player.coin -= gp.ui.npc.inventory.get(itemIndex).price;
                            gp.ui.subState = 0;
                            gp.ui.npc.startDialogue(gp.ui.npc, 5); // Success purchased
                            enterPressed = false;
                        } else {
                            gp.ui.subState = 0;
                            gp.ui.npc.startDialogue(gp.ui.npc, 3); // Inventory full
                            enterPressed = false;
                        }
                    }
                }
            } else {
                int itemIndex = gp.ui.getItemIndexOnSlot(gp.ui.playerSlotCol, gp.ui.playerSlotRow);
                if (itemIndex < gp.player.inventory.size()) {
                    if (gp.player.inventory.get(itemIndex) == gp.player.currentWeapon ||
                            gp.player.inventory.get(itemIndex) == gp.player.currentShield) {
                        gp.ui.subState = 0;
                        gp.ui.npc.startDialogue(gp.ui.npc, 4); // Can't sell equipped item
                        enterPressed = false;
                    } else {
                        int price = gp.player.inventory.get(itemIndex).price / 2;
                        if (gp.player.inventory.get(itemIndex).amount > 1) {
                            gp.player.inventory.get(itemIndex).amount--;
                        } else {
                            gp.player.inventory.remove(itemIndex);
                        }
                        gp.player.coin += price;
                        gp.ui.subState = 0;
                        gp.ui.npc.startDialogue(gp.ui.npc, 6); // Success purchased
                        enterPressed = false;
                    }
                }
            }
        }
    }

    public void mapState(int code)
    {
        if(code == KeyEvent.VK_M)
        {
            gp.gameState = gp.playState;
        }
    }
    public void playerInventory(int code)
    {
        if(code == KeyEvent.VK_W)
        {
            if(gp.ui.playerSlotRow != 0)
            {
                gp.ui.playerSlotRow--;
                gp.playSE(9);   //cursor.wav
            }
        }
        if(code == KeyEvent.VK_A)
        {
            if(gp.ui.playerSlotCol !=0)
            {
                gp.ui.playerSlotCol--;
                gp.playSE(9);
            }
        }
        if(code == KeyEvent.VK_S)
        {
            if(gp.ui.playerSlotRow != 3)
            {
                gp.ui.playerSlotRow++;
                gp.playSE(9);
            }
        }
        if(code == KeyEvent.VK_D)
        {
            if(gp.ui.playerSlotCol != 4)
            {
                gp.ui.playerSlotCol++;
                gp.playSE(9);
            }
        }
    }
    public void npcInventory(int code)
    {
        if(code == KeyEvent.VK_W)
        {
            if(gp.ui.npcSlotRow != 0)
            {
                gp.ui.npcSlotRow--;
                gp.playSE(9);   //cursor.wav
            }
        }
        if(code == KeyEvent.VK_A)
        {
            if(gp.ui.npcSlotCol !=0)
            {
                gp.ui.npcSlotCol--;
                gp.playSE(9);
            }
        }
        if(code == KeyEvent.VK_S)
        {
            if(gp.ui.npcSlotRow != 3)
            {
                gp.ui.npcSlotRow++;
                gp.playSE(9);
            }
        }
        if(code == KeyEvent.VK_D)
        {
            if(gp.ui.npcSlotCol != 4)
            {
                gp.ui.npcSlotCol++;
                gp.playSE(9);
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();

        if(code == KeyEvent.VK_W)
        {
            upPressed = false;
        }
        if(code == KeyEvent.VK_S)
        {
            downPressed = false;
        }
        if(code == KeyEvent.VK_A)
        {
            leftPressed = false;
        }
        if(code == KeyEvent.VK_D)
        {
            rightPressed = false;
        }
        if(code == KeyEvent.VK_ENTER)
        {
            enterPressed = false;
        }
        if(code == KeyEvent.VK_SPACE)
        {
            spacePressed = false;
        }
    }
}
