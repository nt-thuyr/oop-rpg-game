package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_BlueHeart extends Entity {

    private GamePanel gp; // Encapsulated GamePanel
    public static final String objName = "Lam Tinh Thạch";

    public OBJ_BlueHeart(GamePanel gp) {
        super(gp);

        this.gp = gp;

        setType(getType_pickupOnly());
        setName(objName);
        setDown1(setup("/objects/blueheart", gp.getTileSize(), gp.getTileSize()));
        setDialogues();
    }

    public void setDialogues() {
        getDialogues()[0][0] = "Bạn nhặt được một viên ngọc lam tuyệt đẹp.";
        getDialogues()[0][1] = "Bạn đã tìm thấy Lam Tinh Thạch - bảo vật huyền thoại!";
    }

    public boolean use(Entity entity) { // When picked up, this method will be called
        gp.setGameState(gp.getCutsceneState());
        gp.getCsManager().setSceneNum(gp.getCsManager().getEnding());
        return true;
    }
}