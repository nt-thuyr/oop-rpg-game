package main;
import ai.PathFinder;

import entity.Character;
import entity.Player;
import item.Item;
import tile.Map;
import tile.TileManager;
import tile_interactive.InteractiveTile;

import javax.swing.JPanel;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;


public class GamePanel extends JPanel implements Runnable {
    //SCREEN SETTINGS
    private final int originalTileSize = 16; // 16*16 tile. default
    private final int scale = 3; // 16*3 scale

    private final int tileSize = originalTileSize * scale; // 48*48 tile
    private final int maxScreenCol = 20; // 4:3 window
    private final int maxScreenRow = 12;
    private final int screenWidth = tileSize * maxScreenCol; // 48*20 = 960 pixels
    private final int screenHeight = tileSize * maxScreenRow; // 48*12 = 576 pixels

    // WORLD SETTINGS
    private int maxWorldCol = 50;
    private int maxWorldRow = 50;
    private final int maxMap = 10;
    private int currentMap = 0;

    // FOR SFULLSCREEN
    private int screenWidth2 = screenWidth;
    private int screenHeight2 = screenHeight;
    private BufferedImage tempScreen;
    private Graphics2D g2;
    private boolean fullScreenOn = false;

    // FPS
    private int FPS = 60;

    // SYSTEM
    private TileManager tileM = new TileManager(this);
    private KeyHandler keyH = new KeyHandler(this);
    private EventHandler eHandler = new EventHandler(this);
    private Sound music = new Sound();
    private Sound se = new Sound();
    private CollisionChecker cChecker = new CollisionChecker(this);
    private AssetSetter aSetter = new AssetSetter(this);
    private UI ui = new UI(this);
    private PathFinder pFinder = new PathFinder(this);
    Map map = new Map(this);

    private EntityGenerator eGenerator = new EntityGenerator(this);
    private Thread gameThread;

    // ENTITY AND OBJECT
    private Player player = new Player(this, keyH);
    private final Item[][] obj = new Item[maxMap][20];
    private final Character[][] npc = new Character[maxMap][10];
    private final Character[][] monster = new Character[maxMap][20];
    private final InteractiveTile[][] iTile = new InteractiveTile[maxMap][50];
    private final Character[][] projectile = new Character[maxMap][20];
    private ArrayList<Character> particleList = new ArrayList<>();
    private  ArrayList<Character> charactersList = new ArrayList<>();
    private  ArrayList<Item> itemsList = new ArrayList<>();



    // GAME STATE
    private int gameState;
    private final int titleState = 0;
    private final int playState = 1;
    private final int pauseState = 2;
    private final int dialogueState = 3;
    private final int characterState = 4;
    private final int optionsState = 5;
    private final int gameOverState = 6;
    private final int transitionState = 7;
    private final int tradeState = 8;
    private final int sleepState = 9;
    private final int mapState = 10;
    private final int cutsceneState = 11;

    // AREA
    private int currentArea;
    private int nextArea;
    private final int outside = 50;
    private final int indoor = 51;
    private final int dungeon = 52;


    public GamePanel() // constructor
    {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight)); // JPanel size
        this.setBackground(Color.black);
        this.setDoubleBuffered(true); // improve game's rendering performance
        this.addKeyListener(keyH);
        this.setFocusable(true);

        this.maxWorldCol = 50; // Số cột tối đa của bản đồ
        this.maxWorldRow = 50; // Số hàng tối đa của bản đồ
        this.map = new Map(this);
    }

    public void setupGame() {
        aSetter.setObject();
        aSetter.setNPC();
        aSetter.setMonster();

        gameState = titleState;
        tempScreen = new BufferedImage(screenWidth, screenHeight, BufferedImage.TYPE_INT_ARGB);
        g2 = (Graphics2D) tempScreen.getGraphics();

    }
    public void resetGame(boolean restart)
    {
        stopMusic();
        currentArea = outside;
        removeTempEntity();
        player.setDefaultPositions();
        player.restoreStatus();
        aSetter.setMonster();
        aSetter.setNPC();
        player.getState().resetCounter();

        if(restart)
        {
            player.setDefaultValues();
            aSetter.setObject();

        }

    }
    public void setFullScreen()
    {
        //GET LOCAL SCREEN DEVICE
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice gd = ge.getDefaultScreenDevice();
        gd.setFullScreenWindow(Main.window);

        //GET FULL SCREEN WIDTH AND HEIGHT
        screenWidth2 = Main.window.getWidth();
        screenHeight2 = Main.window.getHeight();
    }

    public void startGameThread()
    {
        gameThread = new Thread(this);
        gameThread.start(); // run'ı cagirir
    }

    @Override
    public void run()
    {
        double drawInterval = 1000000000/FPS;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;

        while(gameThread != null)
        {
            currentTime = System.nanoTime();

            delta += (currentTime - lastTime) / drawInterval;
            lastTime = currentTime;
            if(delta >= 1)
            {
                update();
                drawToTempScreen(); //FOR FULL SCREEN - Draw everything to the buffered image
                drawToScreen();     //FOR FULL SCREEN - Draw the buffered image to the screen
                delta--;
            }
        }
    }

    public void update()
    {
        if(gameState == playState)
        {
            //PLAYER
            player.update();

            //NPC
            for(int i = 0; i < npc[1].length; i++) //[1] means second dimension's length!!!
            {
                if(npc[currentMap][i] != null)
                {
                    npc[currentMap][i].update();
                }
            }

            //MONSTER
            for(int i = 0; i < monster[1].length; i++)
            {
                if(monster[currentMap][i] != null)
                {
                    if(monster[currentMap][i].getState().isAlive() && !monster[currentMap][i].getState().isDying())
                    {
                        monster[currentMap][i].update();
                    }
                    if(!monster[currentMap][i].getState().isAlive())
                    {
                        monster[currentMap][i].checkDrop(); // when monster dies, i check its drop
                        monster[currentMap][i] = null;
                    }
                }
            }

            //PROJECTILE
            for(int i = 0; i < projectile[1].length; i++)
            {
                if(projectile[currentMap][i] != null)
                {
                    if(projectile[currentMap][i].getState().isAlive())
                    {
                        projectile[currentMap][i].update();
                    }
                    if(!projectile[currentMap][i].getState().isAlive())
                    {
                        projectile[currentMap][i] = null;
                    }
                }
            }

            //PARTICLE
            for(int i = 0; i < particleList.size(); i++)
            {
                if(particleList.get(i)!= null)
                {
                    if(particleList.get(i).getState().isAlive())
                    {
                        particleList.get(i).update();
                    }
                    if(!particleList.get(i).getState().isAlive())
                    {
                        particleList.remove(i);
                    }
                }
            }

            //INTERACTIVE TILE
            for(int i = 0; i < iTile[1].length; i++)
            {
                if(iTile[currentMap][i] != null)
                {
                    iTile[currentMap][i].update();
                }
            }

        }

        if(gameState == pauseState)
        {
            //nothing, just pause screen
        }
    }

    //FOR FULL SCREEN (FIRST DRAW TO TEMP SCREEN INSTEAD OF JPANEL)
    public void drawToTempScreen()
    {
        //DEBUG
        long drawStart = 0;
        if(keyH.isShowDebugText())
        {
            drawStart = System.nanoTime();
        }

        //TITLE SCREEN
        if(gameState == titleState)
        {
            ui.draw(g2);
        }
        //MAP SCREEN
        else if(gameState == mapState)
        {
            map.drawFullMapScreen(g2);
        }
        //OTHERS
        else
        {
            //TILE
            tileM.draw(g2);

            //INTERACTIVE TILE
            for(int i = 0; i < iTile[1].length; i++)
            {
                if(iTile[currentMap][i] != null)
                {
                    iTile[currentMap][i].draw(g2);
                }
            }

            //ADD ENTITIES TO THE LIST
            //PLAYER
            charactersList.add(player);

            //NPCs
            for(int i = 0; i < npc[1].length; i++)
            {
                if(npc[currentMap][i] != null)
                {
                    charactersList.add(npc[currentMap][i]);
                }
            }

            //OBJECTS
            for(int i = 0; i < obj[1].length; i++)
            {
                if(obj[currentMap][i] != null)
                {
                    itemsList.add(obj[currentMap][i]);
                }
            }

            //MONSTERS
            for(int i = 0; i < monster[1].length; i++)
            {
                if(monster[currentMap][i] != null)
                {
                    charactersList.add(monster[currentMap][i]);
                }
            }

            //PROJECTILES
            for(int i = 0; i < projectile[1].length; i++)
            {
                if(projectile[currentMap][i] != null)
                {
                    charactersList.add(projectile[currentMap][i]);
                }
            }

            //PARTICLES
            for(int i = 0; i < particleList.size(); i++)
            {
                if(particleList.get(i) != null)
                {
                    charactersList.add(particleList.get(i));
                }
            }

            // Tạo danh sách chung
            ArrayList<Object> combinedList = new ArrayList<>();
            (combinedList).addAll(charactersList);
            combinedList.addAll(itemsList);

// Sắp xếp danh sách dựa trên worldY
            Collections.sort(combinedList, new Comparator<Object>() {
                @Override
                public int compare(Object o1, Object o2) {
                    int y1 = 0, y2 = 0;

                    if (o1 instanceof Character) {
                        y1 = ((Character) o1).getWorldY();
                    } else if (o1 instanceof Item) {
                        y1 = ((Item) o1).getWorldY();
                    }

                    if (o2 instanceof Character) {
                        y2 = ((Character) o2).getWorldY();
                    } else if (o2 instanceof Item) {
                        y2 = ((Item) o2).getWorldY();
                    }

                    return Integer.compare(y1, y2);
                }
            });

// Vẽ các thực thể
            for (Object obj : combinedList) {
                if (obj instanceof Character) {
                    ((Character) obj).draw(g2);
                } else if (obj instanceof Item) {
                    ((Item) obj).draw(g2);
                }
            }

            //EMPTY ENTITY LIST
            combinedList.clear();

            //UI
            ui.draw(g2);

            //DEBUG

            if(keyH.isShowDebugText())
            {
                long drawEnd = System.nanoTime();
                long passed = drawEnd - drawStart;

                g2.setFont(new Font("Arial", Font.PLAIN,20));
                g2.setColor(Color.white);
                int x = 10;
                int y = 400;
                int lineHeight = 20;

                g2.drawString("WorldX " + player.getWorldX(),x,y);
                y+= lineHeight;
                g2.drawString("WorldY " + player.getWorldY(),x,y);
                y+= lineHeight;
                g2.drawString("Col " + (player.getWorldX() + player.getSolidArea().x) / tileSize,x,y);
                y+= lineHeight;
                g2.drawString("Row " + (player.getWorldY() + player.getSolidArea().y) / tileSize,x,y);
                y+= lineHeight;
                g2.drawString("Map " + currentMap,x,y);
                y+= lineHeight;
                g2.drawString("Draw time: " + passed,x,y);
                y+= lineHeight;
                g2.drawString("God Mode: " + keyH.isGodModeOn(), x, y);

            }
        }
    }
    public void drawToScreen()
    {
        Graphics g = getGraphics();
        g.drawImage(tempScreen, 0, 0,screenWidth2,screenHeight2,null);
        g.dispose();
    }


    public void playMusic(int i)
    {
        music.setFile(i);
        music.play();
        music.loop();
    }
    public void stopMusic()
    {
        music.stop();
    }
    public void playSE(int i) // Sound effect, dont need loop
    {
        se.setFile(i);
        se.play();
    }
    public void changeArea()
    {
        if(nextArea != currentArea)
        {
            stopMusic();

            if(nextArea == outside)
            {
                playMusic(0);
            }
            if(nextArea == indoor)
            {
                playMusic(18);
            }
            if(nextArea == dungeon)
            {
                playMusic(19);
            }
            aSetter.setNPC(); //reset for at the dungeon puzzle's stuck rocks.
        }

        currentArea = nextArea;
        aSetter.setMonster();
    }
    public void removeTempEntity()
    {
        for(int mapNum = 0; mapNum < maxMap; mapNum++)
        {
            for(int i = 0; i < obj[1].length; i++)
            {
                if(obj[mapNum][i] != null && obj[mapNum][i].isTemp())
                {
                    obj[mapNum][i] = null;
                }
            }
        }
    }

    public AssetSetter getaSetter() {
        return aSetter;
    }

    public CollisionChecker getcChecker() {
        return cChecker;
    }

    public int getCharacterState() {
        return characterState;
    }

    public int getCurrentArea() {
        return currentArea;
    }

    public int getCurrentMap() {
        return currentMap;
    }

    public int getCutsceneState() {
        return cutsceneState;
    }

    public int getDialogueState() {
        return dialogueState;
    }

    public int getDungeon() {
        return dungeon;
    }

    public EntityGenerator geteGenerator() {
        return eGenerator;
    }

    public EventHandler geteHandler() {
        return eHandler;
    }


    public int getFPS() {
        return FPS;
    }

    public boolean isFullScreenOn() {
        return fullScreenOn;
    }

    public Graphics2D getG2() {
        return g2;
    }

    public int getGameOverState() {
        return gameOverState;
    }

    public int getGameState() {
        return gameState;
    }

    public Thread getGameThread() {
        return gameThread;
    }

    public int getIndoor() {
        return indoor;
    }

    public InteractiveTile[][] getiTile() {
        return iTile;
    }

    public KeyHandler getKeyH() {
        return keyH;
    }

    public Map getMap() {
        return map;
    }

    public int getMapState() {
        return mapState;
    }

    public int getMaxMap() {
        return maxMap;
    }

    public int getMaxScreenCol() {
        return maxScreenCol;
    }

    public int getMaxScreenRow() {
        return maxScreenRow;
    }

    public int getMaxWorldCol() {
        return maxWorldCol;
    }

    public int getMaxWorldRow() {
        return maxWorldRow;
    }

    public Character[][] getMonster() {
        return monster;
    }

    public Sound getMusic() {
        return music;
    }

    public int getNextArea() {
        return nextArea;
    }

    public Character[][] getNpc() {
        return npc;
    }

    public Item[][] getObj() {
        return obj;
    }

    public int getOptionsState() {
        return optionsState;
    }

    public int getOriginalTileSize() {
        return originalTileSize;
    }

    public int getOutside() {
        return outside;
    }

    public ArrayList<Character> getParticleList() {
        return particleList;
    }

    public int getPauseState() {
        return pauseState;
    }

    public PathFinder getpFinder() {
        return pFinder;
    }

    public Player getPlayer() {
        return player;
    }

    public int getPlayState() {
        return playState;
    }

    public Character[][] getProjectile() {
        return projectile;
    }

    public int getScale() {
        return scale;
    }

    public int getScreenHeight2() {
        return screenHeight2;
    }

    public int getScreenHeight() {
        return screenHeight;
    }

    public int getScreenWidth2() {
        return screenWidth2;
    }

    public int getScreenWidth() {
        return screenWidth;
    }

    public Sound getSe() {
        return se;
    }

    public int getSleepState() {
        return sleepState;
    }

    public BufferedImage getTempScreen() {
        return tempScreen;
    }

    public TileManager getTileM() {
        return tileM;
    }

    public int getTileSize() {
        return tileSize;
    }

    public int getTitleState() {
        return titleState;
    }

    public int getTradeState() {
        return tradeState;
    }

    public int getTransitionState() {
        return transitionState;
    }

    public UI getUi() {
        return ui;
    }

    public void setGameState(int gameState) {
        this.gameState = gameState;
    }

    public void setaSetter(AssetSetter aSetter) {
        this.aSetter = aSetter;
    }

    public void setcChecker(CollisionChecker cChecker) {
        this.cChecker = cChecker;
    }

    public void setCurrentArea(int currentArea) {
        this.currentArea = currentArea;
    }

    public void setCurrentMap(int currentMap) {
        this.currentMap = currentMap;
    }

    public void seteGenerator(EntityGenerator eGenerator) {
        this.eGenerator = eGenerator;
    }

    public void seteHandler(EventHandler eHandler) {
        this.eHandler = eHandler;
    }

    public void setFPS(int FPS) {
        this.FPS = FPS;
    }

    public void setFullScreenOn(boolean fullScreenOn) {
        this.fullScreenOn = fullScreenOn;
    }

    public void setG2(Graphics2D g2) {
        this.g2 = g2;
    }

    public void setGameThread(Thread gameThread) {
        this.gameThread = gameThread;
    }

//    public void setiTile(InteractiveTile iTile) {
//        this.iTile = iTile;
//    }

    public void setKeyH(KeyHandler keyH) {
        this.keyH = keyH;
    }

    public void setMap(Map map) {
        this.map = map;
    }

    public void setMaxWorldCol(int maxWorldCol) {
        this.maxWorldCol = maxWorldCol;
    }

    public void setMaxWorldRow(int maxWorldRow) {
        this.maxWorldRow = maxWorldRow;
    }

//    public void setMonster(Entity monster) {
//        this.monster = monster;
//    }

    public void setMusic(Sound music) {
        this.music = music;
    }

    public void setNextArea(int nextArea) {
        this.nextArea = nextArea;
    }

//    public void setNpc(Entity npc) {
//        this.npc = npc;
//    }

//    public void setObj(Entity obj) {
//        this.obj = obj;
//    }

    public void setParticleList(ArrayList<Character> particleList) {
        this.particleList = particleList;
    }

    public void setpFinder(PathFinder pFinder) {
        this.pFinder = pFinder;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

//    public void setProjectile(Entity projectile) {
//        this.projectile = projectile;
//    }

    public void setScreenHeight2(int screenHeight2) {
        this.screenHeight2 = screenHeight2;
    }

    public void setScreenWidth2(int screenWidth2) {
        this.screenWidth2 = screenWidth2;
    }

    public void setSe(Sound se) {
        this.se = se;
    }

    public void setTempScreen(BufferedImage tempScreen) {
        this.tempScreen = tempScreen;
    }

    public void setTileM(TileManager tileM) {
        this.tileM = tileM;
    }

    public void setUi(UI ui) {
        this.ui = ui;
    }
}

