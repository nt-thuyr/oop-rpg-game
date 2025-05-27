//package object;
//
//import entity.Entity;
//import main.GamePanel;
//
//public class OBJ_Chest extends Entity {
//
//    private GamePanel gp; // Encapsulated GamePanel
//    public static final String objName = "Chest";
//
//    public OBJ_Chest(GamePanel gp) {
//        super(gp);
//        this.gp = gp;
//
//        setType(getType_obstacle());
//        setName(objName);
//        setImage1(setup("/objects/chest", gp.getTileSize(), gp.getTileSize()));
//        setImage2(setup("/objects/chest_opened", gp.getTileSize(), gp.getTileSize()));
//        setDown1(getImage1());
//        setCollision(true);
//
//        getSolidArea().x = 4;
//        getSolidArea().y = 16;
//        getSolidArea().width = 40;
//        getSolidArea().height = 32;
//        setSolidAreaDefaultX(getSolidArea().x);
//        setSolidAreaDefaultY(getSolidArea().y);
//    }
//
//    public void setLoot(Entity loot) {
//        setLoot(loot);
//        setDialogue();
//    }
//
//    public void setDialogue() {
//        getDialogues()[0][0] = "Bạn mở rương kho báu và tìm thấy " + getLoot().getName() + "!\n...Nhưng không thể mang thêm nữa!";
//        getDialogues()[1][0] = "Bạn mở rương kho báu và tìm thấy " + getLoot().getName() + "!\nBạn đã sở hữu " + getLoot().getName() + "!";
//        getDialogues()[2][0] = "Trống trơn hà...";
//    }
//
//    public void interact() {
//        if (!isOpened()) {
//            gp.playSE(3);
//
//            if (!gp.getPlayer().canObtainItem(getLoot())) {
//                startDialogue(this, 0);
//            } else {
//                startDialogue(this, 1);
//                setDown1(getImage2());
//                setOpened(true);
//            }
//        } else {
//            startDialogue(this, 2);
//        }
//    }
//}