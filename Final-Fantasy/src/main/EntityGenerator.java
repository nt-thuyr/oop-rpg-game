package main;

import item.*;

public class EntityGenerator {

    GamePanel gp;

    public EntityGenerator(GamePanel gp)
    {
        this.gp = gp;
    }

    public Item getObject(String itemName)
    {
        Item obj = null;

        switch (itemName)
        {
            case Chest.objName: obj = new Chest(gp);break;
            case CoinBronze.objName: obj = new CoinBronze(gp);break;
            case Door.objName: obj = new Door(gp);break;
            case Heart.objName: obj = new Heart(gp);break;
            case Key.objName: obj = new Key(gp);break;
            case PotionRed.objName: obj = new PotionRed(gp);break;
            case ShieldBlue.objName: obj = new ShieldBlue(gp);break;
            case ShieldWood.objName: obj = new ShieldWood(gp);break;
            case SwordNormal.objName: obj = new SwordNormal(gp);break;
            case DemonSlayer.objName: obj = new DemonSlayer(gp);break;
        }
        return obj;
    }

}
