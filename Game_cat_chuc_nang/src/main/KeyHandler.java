package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {

    private GamePanel gp;
    private boolean upPressed, downPressed, leftPressed, rightPressed, enterPressed, shotKeyPressed, spacePressed;
    private boolean showDebugText = false;
    private boolean godModeOn = false;

    public KeyHandler(GamePanel gp) {
        this.gp = gp;
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();

        if (gp.getGameState() == gp.getTitleState()) {
            titleState(code);
        } else if (gp.getGameState() == gp.getPlayState()) {
            playState(code);
        } else if (gp.getGameState() == gp.getPauseState()) {
            pauseState(code);
        } else if (gp.getGameState() == gp.getDialogueState() || gp.getGameState() == gp.getCutsceneState()) {
            dialogueState(code);
        } else if (gp.getGameState() == gp.getCharacterState()) {
            characterState(code);
        } else if (gp.getGameState() == gp.getGameOverState()) {
            gameOverState(code);
        } else if (gp.getGameState() == gp.getTradeState()) {
            tradeState(code);
        } else if (gp.getGameState() == gp.getMapState()) {
            mapState(code);
        }
    }

    private void titleState(int code) {
        if (gp.getUi().getTitleScreenState() == 0) {
            if (code == KeyEvent.VK_W) {
                gp.getUi().setCommandNum(gp.getUi().getCommandNum() - 1);
                if (gp.getUi().getCommandNum() < 0) {
                    gp.getUi().setCommandNum(1);
                }
            }
            if (code == KeyEvent.VK_S) {
                gp.getUi().setCommandNum(gp.getUi().getCommandNum() + 1);
                if (gp.getUi().getCommandNum() > 1) {
                    gp.getUi().setCommandNum(0);
                }
            }
            if (code == KeyEvent.VK_ENTER) {
                if (gp.getUi().getCommandNum() == 0) {
                    gp.getUi().setTitleScreenState(1);
                    gp.getUi().setCommandNum(0);
                } else if (gp.getUi().getCommandNum() == 1) {
                    System.exit(0);
                }
            }
        } else if (gp.getUi().getTitleScreenState() == 1) {
            if (code == KeyEvent.VK_ENTER) {
                gp.setGameState(gp.getPlayState());
                gp.playMusic(0);
            }
        }
    }

    private void playState(int code) {
        if (code == KeyEvent.VK_W) {
            setUpPressed(true);
        }
        if (code == KeyEvent.VK_S) {
            setDownPressed(true);
        }
        if (code == KeyEvent.VK_A) {
            setLeftPressed(true);
        }
        if (code == KeyEvent.VK_D) {
            setRightPressed(true);
        }
        if (code == KeyEvent.VK_P) {
            gp.setGameState(gp.getPauseState());
        }
        if (code == KeyEvent.VK_C) {
            gp.setGameState(gp.getCharacterState());
        }
        if (code == KeyEvent.VK_ENTER) {
            setEnterPressed(true);
        }
        if (code == KeyEvent.VK_M) {
            gp.setGameState(gp.getMapState());
        }
        if (code == KeyEvent.VK_SPACE) {
            setSpacePressed(true);
        }
    }

    private void pauseState(int code) {
        if (code == KeyEvent.VK_ESCAPE || code == KeyEvent.VK_P) {
            gp.setGameState(gp.getPlayState());
        }
    }

    private void dialogueState(int code) {
        if (code == KeyEvent.VK_ENTER) {
            setEnterPressed(true);
        }
    }

    private void characterState(int code) {
        if (code == KeyEvent.VK_C) {
            gp.setGameState(gp.getPlayState());
        }
        if (code == KeyEvent.VK_ENTER) {
            gp.getPlayer().selectItem();
        }
        playerInventory(code);
    }

    private void gameOverState(int code) {
        if (code == KeyEvent.VK_W) {
            gp.getUi().setCommandNum(gp.getUi().getCommandNum() - 1);
            if (gp.getUi().getCommandNum() < 0) {
                gp.getUi().setCommandNum(1);
            }
            gp.playSE(9);
        }
        if (code == KeyEvent.VK_S) {
            gp.getUi().setCommandNum(gp.getUi().getCommandNum() + 1);
            if (gp.getUi().getCommandNum() > 1) {
                gp.getUi().setCommandNum(0);
            }
            gp.playSE(9);
        }
        if (code == KeyEvent.VK_ENTER) {
            if (gp.getUi().getCommandNum() == 0) {
                gp.setGameState(gp.getPlayState());
                gp.resetGame(false);
                gp.playMusic(0);
            } else if (gp.getUi().getCommandNum() == 1) {
                gp.getUi().setTitleScreenState(0);
                gp.setGameState(gp.getTitleState());
                gp.resetGame(true);
            }
        }
    }

    private void tradeState(int code) {
        if (code == KeyEvent.VK_ENTER) {
            setEnterPressed(true);
        }
        if (gp.getUi().getSubState() == 0) {
            if (code == KeyEvent.VK_W) {
                gp.getUi().setCommandNum(gp.getUi().getCommandNum() - 1);
                if (gp.getUi().getCommandNum() < 0) {
                    gp.getUi().setCommandNum(1);
                }
                gp.playSE(9);
            }
            if (code == KeyEvent.VK_S) {
                gp.getUi().setCommandNum(gp.getUi().getCommandNum() + 1);
                if (gp.getUi().getCommandNum() > 1) {
                    gp.getUi().setCommandNum(0);
                }
                gp.playSE(9);
            }
        } else if (gp.getUi().getSubState() == 1) {
            tradeScreenState(code);
            if (code == KeyEvent.VK_ESCAPE) {
                gp.getUi().setSubState(0);
                gp.getUi().getNpc().startDialogue(gp.getUi().getNpc(), 1);
                setEnterPressed(false);
            }
        }
    }

    public void tradeScreenState(int code) {
        if (code == KeyEvent.VK_W) {
            if (gp.getUi().isInNPCInventory()) {
                if (gp.getUi().getNpcSlotRow() != 0) {
                    gp.getUi().setNpcSlotRow(gp.getUi().getNpcSlotRow() - 1);
                    gp.playSE(9);
                } else if (gp.getUi().getNpcSlotRow() == 0) {
                    gp.getUi().setInNPCInventory(false);
                    gp.getUi().setPlayerSlotRow(3);
                    gp.getUi().setPlayerSlotCol(gp.getUi().getNpcSlotCol());
                    gp.playSE(9);
                }
            } else {
                if (gp.getUi().getPlayerSlotRow() != 0) {
                    gp.getUi().setPlayerSlotRow(gp.getUi().getPlayerSlotRow() - 1);
                    gp.playSE(9);
                } else if (gp.getUi().getPlayerSlotRow() == 0) {
                    gp.getUi().setInNPCInventory(true);
                    gp.getUi().setNpcSlotRow(3);
                    gp.getUi().setNpcSlotCol(gp.getUi().getPlayerSlotCol());
                    gp.playSE(9);
                }
            }
        }
        if (code == KeyEvent.VK_S) {
            if (gp.getUi().isInNPCInventory()) {
                if (gp.getUi().getNpcSlotRow() != 3) {
                    gp.getUi().setNpcSlotRow(gp.getUi().getNpcSlotRow() + 1);
                    gp.playSE(9);
                } else if (gp.getUi().getNpcSlotRow() == 3) {
                    gp.getUi().setInNPCInventory(false);
                    gp.getUi().setPlayerSlotRow(0);
                    gp.getUi().setPlayerSlotCol(gp.getUi().getNpcSlotCol());
                    gp.playSE(9);
                }
            } else {
                if (gp.getUi().getPlayerSlotRow() != 3) {
                    gp.getUi().setPlayerSlotRow(gp.getUi().getPlayerSlotRow() + 1);
                    gp.playSE(9);
                } else if (gp.getUi().getPlayerSlotRow() == 3) {
                    gp.getUi().setInNPCInventory(true);
                    gp.getUi().setNpcSlotRow(0);
                    gp.getUi().setNpcSlotCol(gp.getUi().getPlayerSlotCol());
                    gp.playSE(9);
                }
            }
        }
        if (code == KeyEvent.VK_A) {
            if (gp.getUi().isInNPCInventory()) {
                if (gp.getUi().getNpcSlotCol() != 0) {
                    gp.getUi().setNpcSlotCol(gp.getUi().getNpcSlotCol() - 1);
                    gp.playSE(9);
                }
            } else {
                if (gp.getUi().getPlayerSlotCol() != 0) {
                    gp.getUi().setPlayerSlotCol(gp.getUi().getPlayerSlotCol() - 1);
                    gp.playSE(9);
                }
            }
        }
        if (code == KeyEvent.VK_D) {
            if (gp.getUi().isInNPCInventory()) {
                if (gp.getUi().getNpcSlotCol() != 4) {
                    gp.getUi().setNpcSlotCol(gp.getUi().getNpcSlotCol() + 1);
                    gp.playSE(9);
                }
            } else {
                if (gp.getUi().getPlayerSlotCol() != 4) {
                    gp.getUi().setPlayerSlotCol(gp.getUi().getPlayerSlotCol() + 1);
                    gp.playSE(9);
                }
            }
        }
        if (code == KeyEvent.VK_ENTER && isEnterPressed()) {
            if (gp.getUi().isInNPCInventory()) {
                int itemIndex = gp.getUi().getItemIndexOnSlot(gp.getUi().getNpcSlotCol(), gp.getUi().getNpcSlotRow());
                if (itemIndex < gp.getUi().getNpc().getInventory().size()) {
                    if (gp.getUi().getNpc().getInventory().get(itemIndex).getPrice() > gp.getPlayer().getCoin()) {
                        gp.getUi().setSubState(0);
                        gp.getUi().getNpc().startDialogue(gp.getUi().getNpc(), 2); // Not enough coin
                    } else {
                        if (gp.getPlayer().canObtainItem(gp.getUi().getNpc().getInventory().get(itemIndex))) {
                            gp.getPlayer().setCoin(gp.getPlayer().getCoin() - gp.getUi().getNpc().getInventory().get(itemIndex).getPrice());
                            gp.getUi().setSubState(0);
                            gp.getUi().getNpc().startDialogue(gp.getUi().getNpc(), 5); // Success purchased
                        } else {
                            gp.getUi().setSubState(0);
                            gp.getUi().getNpc().startDialogue(gp.getUi().getNpc(), 3); // Inventory full
                        }
                    }
                    setEnterPressed(false);
                }
            } else {
                int itemIndex = gp.getUi().getItemIndexOnSlot(gp.getUi().getPlayerSlotCol(), gp.getUi().getPlayerSlotRow());
                if (itemIndex < gp.getPlayer().getInventory().size()) {
                    if (gp.getPlayer().getInventory().get(itemIndex) == gp.getPlayer().getCurrentWeapon() ||
                            gp.getPlayer().getInventory().get(itemIndex) == gp.getPlayer().getCurrentShield()) {
                        gp.getUi().setSubState(0);
                        gp.getUi().getNpc().startDialogue(gp.getUi().getNpc(), 4); // Can't sell equipped item
                    } else {
                        int price = gp.getPlayer().getInventory().get(itemIndex).getPrice() / 2;
                        if (gp.getPlayer().getInventory().get(itemIndex).getAmount() > 1) {
                            gp.getPlayer().getInventory().get(itemIndex).setAmount(gp.getPlayer().getInventory().get(itemIndex).getAmount() - 1);
                        } else {
                            gp.getPlayer().getInventory().remove(itemIndex);
                        }
                        gp.getPlayer().setCoin(gp.getPlayer().getCoin() + price);
                        gp.getUi().setSubState(0);
                        gp.getUi().getNpc().startDialogue(gp.getUi().getNpc(), 6); // Success purchased
                    }
                    setEnterPressed(false);
                }
            }
        }
    }
    private void mapState(int code) {
        if (code == KeyEvent.VK_M) {
            gp.setGameState(gp.getPlayState());
        }
    }

    private void playerInventory(int code) {
        if (code == KeyEvent.VK_W) {
            if (gp.getUi().getPlayerSlotRow() != 0) {
                gp.getUi().setPlayerSlotRow(gp.getUi().getPlayerSlotRow() - 1);
                gp.playSE(9);
            }
        }
        if (code == KeyEvent.VK_A) {
            if (gp.getUi().getPlayerSlotCol() != 0) {
                gp.getUi().setPlayerSlotCol(gp.getUi().getPlayerSlotCol() - 1);
                gp.playSE(9);
            }
        }
        if (code == KeyEvent.VK_S) {
            if (gp.getUi().getPlayerSlotRow() != 3) {
                gp.getUi().setPlayerSlotRow(gp.getUi().getPlayerSlotRow() + 1);
                gp.playSE(9);
            }
        }
        if (code == KeyEvent.VK_D) {
            if (gp.getUi().getPlayerSlotCol() != 4) {
                gp.getUi().setPlayerSlotCol(gp.getUi().getPlayerSlotCol() + 1);
                gp.playSE(9);
            }
        }
    }

    public void npcInventory(int code) {
        if (code == KeyEvent.VK_W) {
            if (gp.getUi().getNpcSlotRow() != 0) {
                gp.getUi().setNpcSlotRow(gp.getUi().getNpcSlotRow() - 1);
                gp.playSE(9);   //cursor.wav
            }
        }
        if (code == KeyEvent.VK_A) {
            if (gp.getUi().getNpcSlotCol() != 0) {
                gp.getUi().setNpcSlotCol(gp.getUi().getNpcSlotCol() - 1);
                gp.playSE(9);
            }
        }
        if (code == KeyEvent.VK_S) {
            if (gp.getUi().getNpcSlotRow() != 3) {
                gp.getUi().setNpcSlotRow(gp.getUi().getNpcSlotRow() + 1);
                gp.playSE(9);
            }
        }
        if (code == KeyEvent.VK_D) {
            if (gp.getUi().getNpcSlotCol() != 4) {
                gp.getUi().setNpcSlotCol(gp.getUi().getNpcSlotCol() + 1);
                gp.playSE(9);
            }
        }
    }


    @Override
    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();

        if (code == KeyEvent.VK_W) {
            setUpPressed(false);
        }
        if (code == KeyEvent.VK_S) {
            setDownPressed(false);
        }
        if (code == KeyEvent.VK_A) {
            setLeftPressed(false);
        }
        if (code == KeyEvent.VK_D) {
            setRightPressed(false);
        }
        if (code == KeyEvent.VK_ENTER) {
            setEnterPressed(false);
        }
        if (code == KeyEvent.VK_SPACE) {
            setSpacePressed(false);
        }
    }

    public boolean isUpPressed() {
        return upPressed;
    }

    public void setUpPressed(boolean upPressed) {
        this.upPressed = upPressed;
    }

    public boolean isDownPressed() {
        return downPressed;
    }

    public void setDownPressed(boolean downPressed) {
        this.downPressed = downPressed;
    }

    public boolean isLeftPressed() {
        return leftPressed;
    }

    public void setLeftPressed(boolean leftPressed) {
        this.leftPressed = leftPressed;
    }

    public boolean isRightPressed() {
        return rightPressed;
    }

    public void setRightPressed(boolean rightPressed) {
        this.rightPressed = rightPressed;
    }

    public boolean isEnterPressed() {
        return enterPressed;
    }

    public void setEnterPressed(boolean enterPressed) {
        this.enterPressed = enterPressed;
    }

    public boolean isSpacePressed() {
        return spacePressed;
    }

    public void setSpacePressed(boolean spacePressed) {
        this.spacePressed = spacePressed;
    }

    public boolean isShowDebugText() {
        return showDebugText;
    }

    public void setShowDebugText(boolean showDebugText) {
        this.showDebugText = showDebugText;
    }

    public boolean isGodModeOn() {
        return godModeOn;
    }

    public void setGodModeOn(boolean godModeOn) {
        this.godModeOn = godModeOn;
    }

    public GamePanel getGp() {
        return gp;
    }

    public void setGp(GamePanel gp) {
        this.gp = gp;
    }

    public boolean isShotKeyPressed() {
        return shotKeyPressed;
    }

    public void setShotKeyPressed(boolean shotKeyPressed) {
        this.shotKeyPressed = shotKeyPressed;
    }
}