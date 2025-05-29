package object;

import entity.Character;
import main.GamePanel;

public class OBJ_Chest extends Character {

    GamePanel gp;
    public static final String objName = "Chest";
    public OBJ_Demon_Slayer loot;

    public OBJ_Chest(GamePanel gp)
    {
        super(gp);
        this.gp = gp;
        loot = new OBJ_Demon_Slayer(gp);

        type = type_obstacle;
        name = objName;
        image = setup("/objects/chest",gp.tileSize,gp.tileSize);
        image2 = setup("/objects/chest_opened",gp.tileSize,gp.tileSize);
        down1 = image;
        collision = true;

        solidArea.x = 4;
        solidArea.y = 16;
        solidArea.width = 40;
        solidArea.height = 32;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;

        setDialogue();
    }

    public void setDialogue()
    {
        dialogues[0][0] = "Bạn mở rương kho báu và tìm thấy " + loot.name + "!\n...Nhưng không thể mang thêm nữa!";
        dialogues[1][0] = "Bạn mở rương kho báu và tìm thấy " + loot.name + "!\nBạn đã sở hữu " + loot.name + "!";
        dialogues[2][0] = "Trống trơn hà...";
    }

    @Override
    public void interact() {
        if (!opened) {
            gp.playSE(3); // Phát âm thanh mở rương
            startDialogue(this, 1); // Hiển thị dialog khi mở rương
            gp.player.inventory.add(loot); // Thêm vật phẩm vào túi đồ
            down1 = image2; // Cập nhật hình ảnh rương mở
            opened = true; // Đánh dấu rương đã mở
        } else {
            startDialogue(this, 2); // Hiển thị dialog khi rương đã mở
        }
    }
}
