package tile_interactive;

import main.GamePanel;

public class IT_Trunk extends InteractiveTile {
    private GamePanel gp;

    public IT_Trunk(GamePanel gp, int col, int row) {
        super(gp, col, row);
        this.gp = gp;

        setWorldX(gp.getTileSize() * col);
        setWorldY(gp.getTileSize() * row);

        setDown1(setup("/tiles_interactive/trunk", gp.getTileSize(), gp.getTileSize()));

        // NO COLLISION
        getSolidArea().x = 0;
        getSolidArea().y = 0;
        getSolidArea().width = 0;
        getSolidArea().height = 0;
        setSolidAreaDefaultX(getSolidArea().x);
        setSolidAreaDefaultY(getSolidArea().y);
    }
}