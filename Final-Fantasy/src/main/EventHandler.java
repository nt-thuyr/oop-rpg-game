package main;


import entity.Character;

public class EventHandler{
    GamePanel gp;
    EventRect[][][] eventRect;
    Character eventMaster;

    int previousEventX, previousEventY;
    boolean canTouchEvent = true;
    int tempMap, tempCol, tempRow;


    public EventHandler(GamePanel gp)
    {
        //Set event's interact 2x2 pixels
        this.gp = gp;

        eventMaster = new Character(gp);

        eventRect = new EventRect[gp.getMaxMap()][gp.getMaxWorldCol()][gp.getMaxWorldRow()];

        int map = 0;
        int col = 0;
        int row = 0;
        while(map < gp.getMaxMap() && col < gp.getMaxWorldCol() && row < gp.getMaxWorldRow())
        {
            eventRect[map][col][row] = new EventRect();
            eventRect[map][col][row].x = 23;
            eventRect[map][col][row].y = 23;
            eventRect[map][col][row].width = 2;
            eventRect[map][col][row].height = 2;
            eventRect[map][col][row].setEventRectDefaultX(eventRect[map][col][row].x);
            eventRect[map][col][row].setEventRectDefaultY(eventRect[map][col][row].y);

            col++;
            if(col == gp.getMaxWorldCol())
            {
                col = 0;
                row++;

                if(row == gp.getMaxWorldRow())
                {
                    row = 0;
                    map++; // create eventRectangles for each map
                }
            }
        }
        setDialogue();
    }
    public void setDialogue()
    {
        eventMaster.getDialogues()[0][0] = "Bạn vừa ngã xuống hố!";

        eventMaster.getDialogues()[1][0] = "Bạn vừa uông nước.\nNăng lượng và sức mạnh của bạn sẽ được phục hồi.\n"+ "(Tiến trình đã được lưu)";
        eventMaster.getDialogues()[1][1] = "Khà, nước được phết đấy.";
    }
    public void checkEvent()
    // Kiểm tra và kích hoạt các sự kiện khi người chơi di chuyển đến các vị trí cụ thể
    {
        //Check if the player character is more than 1 tile away from the last event
        int xDistance = Math.abs(gp.getPlayer().getWorldX() - previousEventX);  //pure distance
        int yDistance = Math.abs(gp.getPlayer().getWorldY() - previousEventY);
        int distance = Math.max(xDistance, yDistance);                //returns greater value
        if(distance > gp.getTileSize())
        {
            canTouchEvent = true;
        }

        if(canTouchEvent)
        {
            if(hit(0, 27, 16, "right")) {damagePit(gp.getDialogueState());}
            else if(hit(0, 10, 39, "any")) {teleport(1,12,13, gp.getIndoor());} //to merchant's house
            else if(hit(1, 12, 13, "any")) {teleport(0,10,39, gp.getOutside());} //to outside
            else if(hit(1, 12, 9, "up")) {speak(gp.getNpc()[1][0]);} //merchant

            else if(hit(0, 12, 9, "any")) {teleport(2,9,41, gp.getDungeon());} //to the dungeon
            else if(hit(2, 9, 41, "any")) {teleport(0,12,9, gp.getOutside());} //to outside
            else if(hit(2, 8, 7, "any")) {teleport(3,26,41, gp.getDungeon());} //to B2
            else if(hit(3, 26, 41, "any")) {teleport(2,8,7, gp.getDungeon());} //to B1


        }

    }
    public boolean hit(int map, int col, int row, String reqDirection)
    // Kiểm tra xem người chơi có va chạm với một EventRect tại vị trí cụ thể và hướng di chuyển có khớp với yêu cầu không.
    {
        boolean hit = false;
        if(map == gp.getCurrentMap())
        {
            //Getting player's current solidArea positions
            gp.getPlayer().getSolidArea().x = gp.getPlayer().getWorldX() + gp.getPlayer().getSolidArea().x;
            gp.getPlayer().getSolidArea().y = gp.getPlayer().getWorldY() + gp.getPlayer().getSolidArea().y;
            //Getting eventRect's current solidArea positions
            eventRect[map][col][row].x = col * gp.getTileSize() + eventRect[map][col][row].x;
            eventRect[map][col][row].y = row * gp.getTileSize() + eventRect[map][col][row].y;
            //Checking if player's solidArea is colliding with eventRect's solidArea
            if(gp.getPlayer().getSolidArea().intersects(eventRect[map][col][row]) && !eventRect[map][col][row].isEventDone())
            {
                if(gp.getPlayer().getDirection().contentEquals(reqDirection) || reqDirection.equals("any"))
                {
                    hit = true;

                    previousEventX = gp.getPlayer().getWorldX();
                    previousEventY = gp.getPlayer().getWorldY();
                }
            }
            //RESET
            gp.getPlayer().getSolidArea().x = gp.getPlayer().getSolidAreaDefaultX();
            gp.getPlayer().getSolidArea().y = gp.getPlayer().getSolidAreaDefaultY();
            eventRect[map][col][row].x = eventRect[map][col][row].getEventRectDefaultX();
            eventRect[map][col][row].y = eventRect[map][col][row].getEventRectDefaultY();

        }
        return hit;
    }
    public void teleport(int map, int col, int row, int area)
    {
        gp.setGameState(gp.getTransitionState());
        gp.setNextArea(area);
        tempMap = map;
        tempCol = col;
        tempRow = row;
        //DRAW TRANSITION IN UI
        canTouchEvent = false;
        gp.playSE(13);
    }
    public void damagePit(int gameState)
    {
        gp.setGameState(gameState);
        gp.playSE(6);
        eventMaster.startDialogue(eventMaster, 0);
        gp.getPlayer().setLife(gp.getPlayer().getLife() - 2);
        canTouchEvent = false;
    }

    public void speak(Character character)
    {
        if(gp.getKeyH().isEnterPressed())
        {
            gp.setGameState(gp.getDialogueState());
            gp.getPlayer().setAttackCanceled(true);
            character.speak();
        }
    }

    public GamePanel getGp() {
        return gp;
    }

    public void setGp(GamePanel gp) {
        this.gp = gp;
    }

    public int getPreviousEventX() {
        return previousEventX;
    }

    public void setPreviousEventX(int previousEventX) {
        this.previousEventX = previousEventX;
    }

    public int getPreviousEventY() {
        return previousEventY;
    }

    public void setPreviousEventY(int previousEventY) {
        this.previousEventY = previousEventY;
    }

    public int getTempCol() {
        return tempCol;
    }

    public void setTempCol(int tempCol) {
        this.tempCol = tempCol;
    }

    public int getTempMap() {
        return tempMap;
    }

    public void setTempMap(int tempMap) {
        this.tempMap = tempMap;
    }

    public int getTempRow() {
        return tempRow;
    }

    public void setTempRow(int tempRow) {
        this.tempRow = tempRow;
    }
}
