package tile_interactive;

import main.GamePanel;

public class IT_MetalPlate extends InteractiveTile {

    private GamePanel gp;
    public static final String itName = "Metal Plate";

    public IT_MetalPlate(GamePanel gp, int col, int row) {
        super(gp, col, row);
        this.gp = gp;

        setWorldX(gp.getTileSize() * col);
        setWorldY(gp.getTileSize() * row);

        setName(itName);
        setDown1(setup("/tiles_interactive/metalplate", gp.getTileSize(), gp.getTileSize()));

        // NO COLLISION
        getSolidArea().x = 0;
        getSolidArea().y = 0;
        getSolidArea().width = 0;
        getSolidArea().height = 0;
        setSolidAreaDefaultX(getSolidArea().x);
        setSolidAreaDefaultY(getSolidArea().y);
    }
}