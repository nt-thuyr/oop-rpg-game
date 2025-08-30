package monster;

import character.Character;
import main.GamePanel;
import item.CoinBronze;
import item.Heart;

import java.util.Random;

public class SkeletonLord extends Character {

    public SkeletonLord(GamePanel gp) {
        super(gp);

        this.gp = gp;

        setType(getType_monster());
        setName("Skeleton Lord");
        setDefaultSpeed(1);
        setSpeed(getDefaultSpeed());
        setMaxLife(2);
        setLife(getMaxLife());
        setAttack(2);
        setDefense(2);
        setExp(1);
        setKnockBackPower(1);

        // Điều chỉnh vùng va chạm gấp 3 lần
        getSolidArea().x = 12; // 4 * 3
        getSolidArea().y = 12; // 4 * 3
        getSolidArea().width = 120; // 40 * 3
        getSolidArea().height = 132; // 44 * 3
        setSolidAreaDefaultX(getSolidArea().x);
        setSolidAreaDefaultY(getSolidArea().y);

        // Điều chỉnh vùng tấn công gấp 3 lần
        getAttackArea().width = 144; // 48 * 3
        getAttackArea().height = 144; // 48 * 3
        setMotion1_duration(40);
        setMotion2_duration(85);

        getImage();
        getAttackImage();
    }

    public void getImage() {
        // Phóng to gấp 3 lần kích thước gốc
        int newSize = gp.getTileSize() * 3;
        setUp1(setup("/monster/skeletonlord_down_1", newSize, newSize));
        setUp2(setup("/monster/skeletonlord_down_2", newSize, newSize));
        setDown1(setup("/monster/skeletonlord_down_1", newSize, newSize));
        setDown2(setup("/monster/skeletonlord_down_2", newSize, newSize));
        setLeft1(setup("/monster/skeletonlord_left_1", newSize, newSize));
        setLeft2(setup("/monster/skeletonlord_left_2", newSize, newSize));
        setRight1(setup("/monster/skeletonlord_right_1", newSize, newSize));
        setRight2(setup("/monster/skeletonlord_right_2", newSize, newSize));
    }

    public void getAttackImage() {
        // Phóng to gấp 3 lần kích thước gốc cho cả chiều rộng và chiều cao
        int newSize = gp.getTileSize() * 3;
        setAttackUp1(setup("/monster/skeletonlord_attack_up_1", newSize, newSize * 2));
        setAttackUp2(setup("/monster/skeletonlord_attack_up_2", newSize, newSize * 2));
        setAttackDown1(setup("/monster/skeletonlord_attack_down_1", newSize, newSize * 2));
        setAttackDown2(setup("/monster/skeletonlord_attack_down_2", newSize, newSize * 2));
        setAttackLeft1(setup("/monster/skeletonlord_attack_left_1", newSize * 2, newSize));
        setAttackLeft2(setup("/monster/skeletonlord_attack_left_2", newSize * 2, newSize));
        setAttackRight1(setup("/monster/skeletonlord_attack_right_1", newSize * 2, newSize));
        setAttackRight2(setup("/monster/skeletonlord_attack_right_2", newSize * 2, newSize));
    }

    public void setAction() {
        if (getState().isOnPath()) {
            // Dừng đuổi theo player nếu quá xa
            checkStopChasingOrNot(gp.getPlayer(), 15, 100);
            // Tìm đường đến player
            searchPath(getGoalCol(gp.getPlayer()), getGoalRow(gp.getPlayer()));
        } else {
            // Bắt đầu đuổi theo nếu player đến gần
            checkStartChasingOrNot(gp.getPlayer(), 5, 100);
            // Di chuyển ngẫu nhiên khi không thấy player
            getRandomDirection(120);
        }

        // Kiểm tra và tấn công
        if (!getState().isAttacking()) {
            checkAttackOrNot(30, gp.getTileSize() * 4, gp.getTileSize());
        }
    }

    public void damageReaction() {
        getState().setActionLockCounter(0);
        getState().setOnPath(true);
    }

    public void checkDrop() {
        gp.stopMusic();
        gp.playSE(4); // Phát âm thanh chiến thắng
        gp.setGameState(gp.getEndGameState()); // Chuyển sang endGameState
        // Drop vật phẩm
        int i = new Random().nextInt(100) + 1;
        if (i < 50) {
            dropItem(new CoinBronze(gp));
        }
        if (i >= 50 && i < 75) {
            dropItem(new Heart(gp));
        }
    }
}
