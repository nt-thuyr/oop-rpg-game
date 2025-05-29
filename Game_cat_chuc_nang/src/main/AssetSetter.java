package main;

//import entity.NPC_BigRock;
import entity.NPC_Merchant;
import entity.NPC_OldMan;
import monster.*;
import object.*;

public class AssetSetter {

    GamePanel gp;

    public AssetSetter(GamePanel gp) {
        this.gp = gp;
    }

    public void setObject() {
        int mapNum = 0;
        int i = 0;

        gp.getObj()[mapNum][i] = new OBJ_Axe(gp);
        gp.getObj()[mapNum][i].setWorldX(gp.getTileSize() * 33);
        gp.getObj()[mapNum][i].setWorldY(gp.getTileSize() * 7);
        i++;

        gp.getObj()[mapNum][i] = new OBJ_Door(gp);
        gp.getObj()[mapNum][i].setWorldX(gp.getTileSize() * 14);
        gp.getObj()[mapNum][i].setWorldY(gp.getTileSize() * 28);
        i++;
        gp.getObj()[mapNum][i] = new OBJ_Door(gp);
        gp.getObj()[mapNum][i].setWorldX(gp.getTileSize() * 12);
        gp.getObj()[mapNum][i].setWorldY(gp.getTileSize() * 12);
        i++;
        gp.getObj()[mapNum][i] = new OBJ_Key(gp);
        gp.getObj()[mapNum][i].setWorldX(gp.getTileSize() * 22);
        gp.getObj()[mapNum][i].setWorldY(gp.getTileSize() * 41);
        i++;
        gp.getObj()[mapNum][i] = new OBJ_Key(gp);
        gp.getObj()[mapNum][i].setWorldX(gp.getTileSize() * 38);
        gp.getObj()[mapNum][i].setWorldY(gp.getTileSize() * 40);
        i++;

        mapNum = 1; // adding object to second map
        i = 0;
        gp.getObj()[mapNum][i] = new OBJ_Coin_Bronze(gp);
        gp.getObj()[mapNum][i].setWorldX(gp.getTileSize() * 10);
        gp.getObj()[mapNum][i].setWorldY(gp.getTileSize() * 10);
        i++;

        mapNum = 2;
        i = 0;
        gp.getObj()[mapNum][i] = new OBJ_Chest(gp);
        gp.getObj()[mapNum][i].setLoot(new OBJ_Pickaxe(gp));
        gp.getObj()[mapNum][i].setWorldX(gp.getTileSize() * 40);
        gp.getObj()[mapNum][i].setWorldY(gp.getTileSize() * 41);
        i++;
        gp.getObj()[mapNum][i] = new OBJ_Chest(gp);
        gp.getObj()[mapNum][i].setLoot(new OBJ_Potion_Red(gp));
        gp.getObj()[mapNum][i].setWorldX(gp.getTileSize() * 13);
        gp.getObj()[mapNum][i].setWorldY(gp.getTileSize() * 16);
        i++;
        gp.getObj()[mapNum][i] = new OBJ_Chest(gp);
        gp.getObj()[mapNum][i].setLoot(new OBJ_Potion_Red(gp));
        gp.getObj()[mapNum][i].setWorldX(gp.getTileSize() * 26);
        gp.getObj()[mapNum][i].setWorldY(gp.getTileSize() * 34);
        i++;
        gp.getObj()[mapNum][i] = new OBJ_Chest(gp);
        gp.getObj()[mapNum][i].setLoot(new OBJ_Potion_Red(gp));
        gp.getObj()[mapNum][i].setWorldX(gp.getTileSize() * 27);
        gp.getObj()[mapNum][i].setWorldY(gp.getTileSize() * 15);
        i++;
        gp.getObj()[mapNum][i] = new OBJ_Door_Iron(gp);
        gp.getObj()[mapNum][i].setWorldX(gp.getTileSize() * 18);
        gp.getObj()[mapNum][i].setWorldY(gp.getTileSize() * 23);
        i++;

        mapNum = 3;
        i = 0;
        gp.getObj()[mapNum][i] = new OBJ_Door_Iron(gp);
        gp.getObj()[mapNum][i].setWorldX(gp.getTileSize() * 25);
        gp.getObj()[mapNum][i].setWorldY(gp.getTileSize() * 15);
        i++;

//        gp.getObj()[mapNum][i] = new OBJ_BlueHeart(gp);
//        gp.getObj()[mapNum][i].setWorldX(gp.getTileSize() * 25);
//        gp.getObj()[mapNum][i].setWorldY(gp.getTileSize() * 8);
//        i++;
    }

    public void setNPC() {
        int mapNum = 0;
        int i = 0;

        // MAP = 0
        gp.getNpc()[mapNum][i] = new NPC_OldMan(gp);
        gp.getNpc()[mapNum][i].setWorldX(gp.getTileSize() * 21);
        gp.getNpc()[mapNum][i].setWorldY(gp.getTileSize() * 21);
        i++;

        // MAP = 1
        mapNum = 1;
        i = 0;

        gp.getNpc()[mapNum][i] = new NPC_Merchant(gp);
        gp.getNpc()[mapNum][i].setWorldX(gp.getTileSize() * 12);
        gp.getNpc()[mapNum][i].setWorldY(gp.getTileSize() * 7);
        i++;
    }

    public void setMonster() {
        int mapNum = 0;
        int i = 0;
        gp.getMonster()[mapNum][i] = new MON_GreenSlime(gp);
        gp.getMonster()[mapNum][i].setWorldX(gp.getTileSize() * 23);
        gp.getMonster()[mapNum][i].setWorldY(gp.getTileSize() * 36);
        i++;

        gp.getMonster()[mapNum][i] = new MON_GreenSlime(gp);
        gp.getMonster()[mapNum][i].setWorldX(gp.getTileSize() * 23);
        gp.getMonster()[mapNum][i].setWorldY(gp.getTileSize() * 37);
        i++;

        gp.getMonster()[mapNum][i] = new MON_GreenSlime(gp);
        gp.getMonster()[mapNum][i].setWorldX(gp.getTileSize() * 24);
        gp.getMonster()[mapNum][i].setWorldY(gp.getTileSize() * 37);
        i++;

        gp.getMonster()[mapNum][i] = new MON_GreenSlime(gp);
        gp.getMonster()[mapNum][i].setWorldX(gp.getTileSize() * 34);
        gp.getMonster()[mapNum][i].setWorldY(gp.getTileSize() * 42);
        i++;

        gp.getMonster()[mapNum][i] = new MON_GreenSlime(gp);
        gp.getMonster()[mapNum][i].setWorldX(gp.getTileSize() * 38);
        gp.getMonster()[mapNum][i].setWorldY(gp.getTileSize() * 42);
        i++;

        gp.getMonster()[mapNum][i] = new MON_Orc(gp);
        gp.getMonster()[mapNum][i].setWorldX(gp.getTileSize() * 12);
        gp.getMonster()[mapNum][i].setWorldY(gp.getTileSize() * 33);
        i++;

        gp.getMonster()[mapNum][i] = new MON_Orc(gp);
        gp.getMonster()[mapNum][i].setWorldX(gp.getTileSize() * 23);
        gp.getMonster()[mapNum][i].setWorldY(gp.getTileSize() * 33);
        i++;

        gp.getMonster()[mapNum][i] = new MON_RedSlime(gp);
        gp.getMonster()[mapNum][i].setWorldX(gp.getTileSize() * 34);
        gp.getMonster()[mapNum][i].setWorldY(gp.getTileSize() * 11);
        i++;

        gp.getMonster()[mapNum][i] = new MON_RedSlime(gp);
        gp.getMonster()[mapNum][i].setWorldX(gp.getTileSize() * 38);
        gp.getMonster()[mapNum][i].setWorldY(gp.getTileSize() * 7);
        i++;

        gp.getMonster()[mapNum][i] = new MON_RedSlime(gp);
        gp.getMonster()[mapNum][i].setWorldX(gp.getTileSize() * 20);
        gp.getMonster()[mapNum][i].setWorldY(gp.getTileSize() * 11);
        i++;

        gp.getMonster()[mapNum][i] = new MON_RedSlime(gp);
        gp.getMonster()[mapNum][i].setWorldX(gp.getTileSize() * 37);
        gp.getMonster()[mapNum][i].setWorldY(gp.getTileSize() * 9);
        i++;

        mapNum = 2;
        i = 0;

        gp.getMonster()[mapNum][i] = new MON_SkeletonLord(gp);
        gp.getMonster()[mapNum][i].setWorldX(gp.getTileSize() * 19);
        gp.getMonster()[mapNum][i].setWorldY(gp.getTileSize() * 12);
        i++;

        gp.getMonster()[mapNum][i] = new MON_Bat(gp);
        gp.getMonster()[mapNum][i].setWorldX(gp.getTileSize() * 39);
        gp.getMonster()[mapNum][i].setWorldY(gp.getTileSize() * 26);
        i++;

        gp.getMonster()[mapNum][i] = new MON_Bat(gp);
        gp.getMonster()[mapNum][i].setWorldX(gp.getTileSize() * 28);
        gp.getMonster()[mapNum][i].setWorldY(gp.getTileSize() * 11);
        i++;

        gp.getMonster()[mapNum][i] = new MON_Bat(gp);
        gp.getMonster()[mapNum][i].setWorldX(gp.getTileSize() * 10);
        gp.getMonster()[mapNum][i].setWorldY(gp.getTileSize() * 19);
        i++;
    }
}
