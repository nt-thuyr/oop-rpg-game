package main;

//import entity.NPC_BigRock;
import entity.Merchant;
import entity.OldMan;
import monster.*;
import item.*;

public class AssetSetter {

    GamePanel gp;

    public AssetSetter(GamePanel gp) {
        this.gp = gp;
    }

    public void setObject() {
        int mapNum = 0;
        int i = 0;
        gp.getObj()[mapNum][i] = new Door(gp);
        gp.getObj()[mapNum][i].setWorldX(gp.getTileSize() * 12);
        gp.getObj()[mapNum][i].setWorldY(gp.getTileSize() * 12);
        i++;
        gp.getObj()[mapNum][i] = new Key(gp);
        gp.getObj()[mapNum][i].setWorldX(gp.getTileSize() * 22);
        gp.getObj()[mapNum][i].setWorldY(gp.getTileSize() * 41);
        i++;
        gp.getObj()[mapNum][i] = new Key(gp);
        gp.getObj()[mapNum][i].setWorldX(gp.getTileSize() * 38);
        gp.getObj()[mapNum][i].setWorldY(gp.getTileSize() * 40);
        i++;

        mapNum = 1; // adding object to second map
        i = 0;
        gp.getObj()[mapNum][i] = new CoinBronze(gp);
        gp.getObj()[mapNum][i].setWorldX(gp.getTileSize() * 10);
        gp.getObj()[mapNum][i].setWorldY(gp.getTileSize() * 10);
        i++;

        mapNum = 2;
        i = 0;
        gp.getObj()[mapNum][i] = new Chest(gp);
        gp.getObj()[mapNum][i].setLoot(new DemonSlayer(gp));
        gp.getObj()[mapNum][i].setWorldX(gp.getTileSize() * 40);
        gp.getObj()[mapNum][i].setWorldY(gp.getTileSize() * 18);
        i++;
        gp.getObj()[mapNum][i] = new Chest(gp);
        gp.getObj()[mapNum][i].setLoot(new PotionRed(gp));
        gp.getObj()[mapNum][i].setWorldX(gp.getTileSize() * 27);
        gp.getObj()[mapNum][i].setWorldY(gp.getTileSize() * 32);
        i++;
    }

    public void setNPC() {
        int mapNum = 0;
        int i = 0;

        // MAP = 0
        gp.getNpc()[mapNum][i] = new OldMan(gp);
        gp.getNpc()[mapNum][i].setWorldX(gp.getTileSize() * 21);
        gp.getNpc()[mapNum][i].setWorldY(gp.getTileSize() * 21);
        i++;

        // MAP = 1
        mapNum = 1;
        i = 0;

        gp.getNpc()[mapNum][i] = new Merchant(gp);
        gp.getNpc()[mapNum][i].setWorldX(gp.getTileSize() * 12);
        gp.getNpc()[mapNum][i].setWorldY(gp.getTileSize() * 7);
        i++;
    }

    public void setMonster() {
        int mapNum = 0;
        int i = 0;
        gp.getMonster()[mapNum][i] = new GreenSlime(gp);
        gp.getMonster()[mapNum][i].setWorldX(gp.getTileSize() * 23);
        gp.getMonster()[mapNum][i].setWorldY(gp.getTileSize() * 36);
        i++;

        gp.getMonster()[mapNum][i] = new GreenSlime(gp);
        gp.getMonster()[mapNum][i].setWorldX(gp.getTileSize() * 23);
        gp.getMonster()[mapNum][i].setWorldY(gp.getTileSize() * 37);
        i++;

        gp.getMonster()[mapNum][i] = new GreenSlime(gp);
        gp.getMonster()[mapNum][i].setWorldX(gp.getTileSize() * 24);
        gp.getMonster()[mapNum][i].setWorldY(gp.getTileSize() * 37);
        i++;

        gp.getMonster()[mapNum][i] = new GreenSlime(gp);
        gp.getMonster()[mapNum][i].setWorldX(gp.getTileSize() * 34);
        gp.getMonster()[mapNum][i].setWorldY(gp.getTileSize() * 42);
        i++;

        gp.getMonster()[mapNum][i] = new GreenSlime(gp);
        gp.getMonster()[mapNum][i].setWorldX(gp.getTileSize() * 38);
        gp.getMonster()[mapNum][i].setWorldY(gp.getTileSize() * 42);
        i++;

        gp.getMonster()[mapNum][i] = new Orc(gp);
        gp.getMonster()[mapNum][i].setWorldX(gp.getTileSize() * 12);
        gp.getMonster()[mapNum][i].setWorldY(gp.getTileSize() * 33);
        i++;

        gp.getMonster()[mapNum][i] = new Orc(gp);
        gp.getMonster()[mapNum][i].setWorldX(gp.getTileSize() * 23);
        gp.getMonster()[mapNum][i].setWorldY(gp.getTileSize() * 33);
        i++;

        gp.getMonster()[mapNum][i] = new RedSlime(gp);
        gp.getMonster()[mapNum][i].setWorldX(gp.getTileSize() * 34);
        gp.getMonster()[mapNum][i].setWorldY(gp.getTileSize() * 11);
        i++;

        gp.getMonster()[mapNum][i] = new RedSlime(gp);
        gp.getMonster()[mapNum][i].setWorldX(gp.getTileSize() * 38);
        gp.getMonster()[mapNum][i].setWorldY(gp.getTileSize() * 7);
        i++;

        gp.getMonster()[mapNum][i] = new RedSlime(gp);
        gp.getMonster()[mapNum][i].setWorldX(gp.getTileSize() * 20);
        gp.getMonster()[mapNum][i].setWorldY(gp.getTileSize() * 11);
        i++;

        gp.getMonster()[mapNum][i] = new RedSlime(gp);
        gp.getMonster()[mapNum][i].setWorldX(gp.getTileSize() * 37);
        gp.getMonster()[mapNum][i].setWorldY(gp.getTileSize() * 9);
        i++;

        mapNum = 2;
        i = 0;

        gp.getMonster()[mapNum][i] = new SkeletonLord(gp);
        gp.getMonster()[mapNum][i].setWorldX(gp.getTileSize() * 19);
        gp.getMonster()[mapNum][i].setWorldY(gp.getTileSize() * 12);
        i++;

        gp.getMonster()[mapNum][i] = new Bat(gp);
        gp.getMonster()[mapNum][i].setWorldX(gp.getTileSize() * 39);
        gp.getMonster()[mapNum][i].setWorldY(gp.getTileSize() * 26);
        i++;

        gp.getMonster()[mapNum][i] = new Bat(gp);
        gp.getMonster()[mapNum][i].setWorldX(gp.getTileSize() * 28);
        gp.getMonster()[mapNum][i].setWorldY(gp.getTileSize() * 11);
        i++;

        gp.getMonster()[mapNum][i] = new Bat(gp);
        gp.getMonster()[mapNum][i].setWorldX(gp.getTileSize() * 10);
        gp.getMonster()[mapNum][i].setWorldY(gp.getTileSize() * 19);
        i++;

        gp.getMonster()[mapNum][i] = new Bat(gp);
        gp.getMonster()[mapNum][i].setWorldX(gp.getTileSize() * 22);
        gp.getMonster()[mapNum][i].setWorldY(gp.getTileSize() * 41);
        i++;
    }
}
