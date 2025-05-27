package main;

import object.OBJ_BlueHeart;

import java.awt.*;

public class CutsceneManager {

    GamePanel gp;
    Graphics2D g2;
    public int sceneNum;
    public int scenePhase;

    int counter = 0;
    float alpha = 0f;
    int y;

    // Scene Number
    public final int NA = 0;
    public final int ending = 1;  // Chỉ giữ cảnh ending đơn giản

    public CutsceneManager(GamePanel gp) {
        this.gp = gp;
    }

    public void draw(Graphics2D g2) {
        this.g2 = g2;

        switch (sceneNum) {
            case ending:
                scene_ending();
                break;
        }
    }

    public void scene_ending() {
        if (scenePhase == 0) {
            gp.stopMusic();
            gp.getUi().setNpc(new OBJ_BlueHeart(gp));
            scenePhase++;
        }
        if (scenePhase == 1) {
            gp.getUi().drawDialogueScreen();
        }
        if (scenePhase == 2) {
            gp.playSE(4);
            scenePhase++;
        }
        if (scenePhase == 3) {
            if (counterReached(300)) {
                scenePhase++;
            }
        }
        if (scenePhase == 4) {
            alpha = graduallyAlpha(alpha, 0.005f);
            drawBlackBackground(alpha);
            if (alpha == 1f) {
                alpha = 0;
                scenePhase++;
            }
        }
        if (scenePhase == 5) {
            drawBlackBackground(1f);
            alpha = graduallyAlpha(alpha, 0.005f);

            String text = "After the fierce battle, \n"
                    + "the adventure has just begun.";

            drawString(alpha, 38f, 200, text, 70);

            if (counterReached(600) && alpha == 1f) {
                gp.playMusic(0);
                alpha = 0;
                scenePhase++;
            }
        }
        if (scenePhase == 6) {
            drawBlackBackground(1f);

            alpha = graduallyAlpha(alpha, 0.01f);

            drawString(alpha, 120f, gp.getScreenHeight() / 2, "Blue Boy Adventure", 40);

            if (counterReached(480) && alpha == 1f) {
                scenePhase++;
                alpha = 0;
            }
        }
        if (scenePhase == 7) {
            drawBlackBackground(1f);

            alpha = graduallyAlpha(alpha, 0.01f);

            y = gp.getScreenHeight() / 2;

            // Có thể thay bằng credit đơn giản hoặc bỏ hẳn
            drawString(alpha, 38f, y, "Cam on vi da den!", 40);

            if (counterReached(240) && alpha == 1f) {
                scenePhase++;
                alpha = 0;
            }
        }
        if (scenePhase == 8) {
            drawBlackBackground(1f);
            y--;
            drawString(1f, 38f, y, "Cam on vi da den!", 40);
            if (counterReached(1320)) {
                sceneNum = NA;
                scenePhase = 0;
                gp.setGameState(gp.getPlayState());
                gp.resetGame(false);
            }
        }
    }

    public boolean counterReached(int target) {
        counter++;
        if (counter > target) {
            counter = 0;
            return true;
        }
        return false;
    }

    public void drawBlackBackground(float alpha) {
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
        g2.setColor(Color.black);
        g2.fillRect(0, 0, gp.getScreenWidth(), gp.getScreenHeight());
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
    }

    public void drawString(float alpha, float fontSize, int y, String text, int lineHeight) {
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
        g2.setColor(Color.white);
        g2.setFont(g2.getFont().deriveFont(fontSize));
        for (String line : text.split("\n")) {
            int x = gp.getUi().getXforCenteredText(line);
            g2.drawString(line, x, y);
            y += lineHeight;
        }
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
    }

    public float graduallyAlpha(float alpha, float grade) {
        alpha += grade;
        if (alpha > 1f) {
            alpha = 1f;
        }
        return alpha;
    }

    public void setScenePhase(int scenePhase) {
        this.scenePhase = scenePhase;
    }

    public int getScenePhase() {
        return scenePhase;
    }

    public float getAlpha() {
        return alpha;
    }

    public void setAlpha(float alpha) {
        this.alpha = alpha;
    }

    public int getCounter() {
        return counter;
    }

    public void setCounter(int counter) {
        this.counter = counter;
    }

    public int getEnding() {
        return ending;
    }

    public Graphics2D getG2() {
        return g2;
    }

    public void setG2(Graphics2D g2) {
        this.g2 = g2;
    }

    public GamePanel getGp() {
        return gp;
    }

    public void setGp(GamePanel gp) {
        this.gp = gp;
    }

    public int getNA() {
        return NA;
    }

    public int getSceneNum() {
        return sceneNum;
    }

    public void setSceneNum(int sceneNum) {
        this.sceneNum = sceneNum;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
}
