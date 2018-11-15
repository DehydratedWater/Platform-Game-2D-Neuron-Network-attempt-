package windows;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

import collizions.BBox;
import collizions.BoundingBox;
import gameRecorder.GameRecorder;
import gameRecorder.RecordedFrame;
import managers.ControlsManager;
import managers.ControlsManagerForNeuronNetwork;
import managers.textureManager;
import map.Constants;
import map.Map;
import neuronalNetwork.Network;
import objects.CollidingObject;
import objects.StaticObject;
import openSave.OpenMap;

public class Window extends WindowSchem implements MouseListener, MouseMotionListener, MouseWheelListener, KeyListener, Constants{


	private static final long serialVersionUID = 1L;
	private textureManager texManagerMain, texManagerMap, texManagerItems;
	private ControlsManager cm;
	private ControlsManagerForNeuronNetwork cm2;
	private Map map, items;
	private int sizeX, sizeY;
	private CollidingObject character;
	private StaticObject background;
	private float gameScale = 0.5f;
	private float locX, locY;
	private int spawnX, spawnY;
	private BBox[] mapTab, itemsTab;
	private float characterScale = 1.3f;
	private float jumpSpeed;
	public static boolean canJump = true;
	public static boolean isOnLayer = false;
	public static int lifePoints = 100, points = 0;
	public boolean gameEnd = false;
	private long timeToRespawn;
	private int maxPoint = 0;
	private boolean isNeuronNetworkPlaying = false;
	private boolean isRecording = false;
	private boolean playRecord = false;
	private int[][] mapValuesTab, itemValueTab;
	
	private Network network;
	private boolean isQClicked;
	private GameRecorder record;
	private boolean isAltClicked = false;
	private long recordTime;
	private float medianMistake;
	
	
	public Window(int resx, int resy, int locx, int locy, String title) {
		super(resx, resy, locx, locy, title);
		WindowSchem.antialiasing = true;
		WindowSchem.clearLastFrame = true;
		WindowSchem.lossyScale = true;
		WindowSchem.showFPS = true;
		WindowSchem.FPSrate = 60;
		this.addKeyListener(this);
		this.addMouseListener(this);
		this.addMouseWheelListener(this);
		this.addMouseMotionListener(this);
		this.sizeX = super.window.getWidth();
		this.sizeY = super.window.getHeight();
	}
	
	public void initializeData()
	{
		cm = new ControlsManager();
		cm2 = new ControlsManagerForNeuronNetwork();
		texManagerMain = new textureManager();
		texManagerMap = new textureManager();
		texManagerItems = new textureManager();
		inicializeTextures(texManagerMain);
		inicializeMapTextures(texManagerMap);
		inicializeItemsTextures(texManagerItems);
		
		mapValuesTab = new int[11][11];
		itemValueTab = new int[11][11];
		
		map = new Map(0, 0);
		map.scaleMap(gameScale, gameScale);
		items = new Map(0, 0);
		items.scaleMap(gameScale, gameScale);
		int[] sp = OpenMap.openMap("maps/map1.map", map, items);
		spawnX = sp[0];
		spawnY = sp[1];
		
		locX = spawnX*BLOCK_SIZE*ScreenScale*gameScale;
		locY = spawnY*BLOCK_SIZE*ScreenScale*gameScale;
		
		maxPoint = items.getMaxItemValue();
		setUpMapBoundingBoxes();
		setUpIemsBoundingBoxes();
		network = new Network(3, new int[]{246, 15, 5}, 243, 5);
		network.generateStandardNetworkAllToAll(2);
		record = new GameRecorder();
	}
	
	public void restartMap()
	{
		System.out.println("Restartowanie mapy");
		int[] sp = OpenMap.openMap("maps/map1.map", map, items);
		spawnX = sp[0];
		spawnY = sp[1];
		
		locX = spawnX*BLOCK_SIZE*ScreenScale*gameScale;
		locY = spawnY*BLOCK_SIZE*ScreenScale*gameScale;
		points = 0;
		lifePoints = 100;
	}
	
	public void inicializeMapTextures(textureManager tm)
	{
		System.out.println("Dodawanie tekstur mapy");
		tm.addNewTextureAndGetTextureID("res/air.png", "air");
		tm.addNewTextureAndGetTextureID("res/airIsland.png", "airIsland");
		tm.addNewTextureAndGetTextureID("res/airIslandLeft.png", "airIslandLeft");
		tm.addNewTextureAndGetTextureID("res/airIslandRight.png", "airIslandRight");
		tm.addNewTextureAndGetTextureID("res/dirt.png", "dirt");
		tm.addNewTextureAndGetTextureID("res/dirtWithBottom.png", "dirtWithBottom");
		tm.addNewTextureAndGetTextureID("res/grassRightUpCornerWithBottom.png", "grassRightUpCornerWithBottom");
		tm.addNewTextureAndGetTextureID("res/grassLeftUpCornerWithBottom.png", "grassLeftUpCornerWithBottom");
		tm.addNewTextureAndGetTextureID("res/grassLeftUpCorner.png", "grassLeftUpCorner");
		tm.addNewTextureAndGetTextureID("res/grassLeft.png", "grassLeft");
		tm.addNewTextureAndGetTextureID("res/grassLeftWithBottom.png", "grassLeftWithBottom");
		tm.addNewTextureAndGetTextureID("res/grassLeftDownCorner.png", "grassLeftDownCorner");
		tm.addNewTextureAndGetTextureID("res/grassUp.png", "grassUp");
		tm.addNewTextureAndGetTextureID("res/grassRightDownCorner.png", "grassRightDownCorner");
		tm.addNewTextureAndGetTextureID("res/grassRight.png", "grassRight");
		tm.addNewTextureAndGetTextureID("res/grassRightWithBottom.png", "grassRightWithBottom");
		tm.addNewTextureAndGetTextureID("res/grassRightUpCorner.png", "grassRightUpCorner");
		tm.addNewTextureAndGetTextureID("res/layerCenter.png", "layerCenter");
		tm.addNewTextureAndGetTextureID("res/layerUp.png", "layerUp");
		tm.addNewTextureAndGetTextureID("res/layerDown.png", "layerDown");
		tm.addNewTextureAndGetTextureID("res/kolce.png", "kolce");
	}
	
	public void setUpMapBoundingBoxes()
	{
		mapTab = new BBox[21];
		mapTab[0] = new BBox(0, 0, 0, 0);
		mapTab[1] = new BBox(0, 20, 64, 10);
		mapTab[2] = new BBox(0, 20, 32, 10);
		mapTab[3] = new BBox(32, 20, 32, 10);
		mapTab[4] = new BBox(0, 0, 64, 64);
		mapTab[5] = new BBox(0, 0, 64, 64);
		mapTab[6] = new BBox(0, 0, 64, 64);
		mapTab[7] = new BBox(0, 0, 64, 64);
		mapTab[8] = new BBox(0, 0, 64, 64);
		mapTab[9] = new BBox(0, 0, 64, 64);
		mapTab[10] = new BBox(0, 0, 64, 64);
		mapTab[11] = new BBox(0, 0, 64, 64);
		mapTab[12] = new BBox(0, 0, 64, 64);
		mapTab[13] = new BBox(0, 0, 64, 64);
		mapTab[14] = new BBox(0, 0, 64, 64);
		mapTab[15] = new BBox(0, 0, 64, 64);
		mapTab[16] = new BBox(0, 0, 64, 64);
		mapTab[17] = new BBox(10, 0, 44, 64);
		mapTab[18] = new BBox(10, 50, 44, 14);
		mapTab[19] = new BBox(10, 0, 44, 54);
		mapTab[20] = new BBox(0, 30, 64, 34);
		scaleStandardBoundingBoxes(mapTab, gameScale, gameScale);
	}
	
	public void setUpIemsBoundingBoxes()
	{
		itemsTab = new BBox[2];
		itemsTab[0] = new BBox(0, 0, 0, 0);
		itemsTab[1] = new BBox(20, 20, 24, 24);
		
		scaleStandardBoundingBoxes(itemsTab, gameScale, gameScale);
	}
	public void scaleStandardBoundingBoxes(BBox[] mapTab, float scaleX, float scaleY)
	{
		for(int i = 1; i < mapTab.length; i++)
		{
			mapTab[i].setScale(scaleX, scaleY);
		}
	}
	public void inicializeItemsTextures(textureManager tm)
	{
		System.out.println("Dodawanie tekstur przedmiotów");
		tm.addNewTextureAndGetTextureID("res/air.png", "air");
		tm.addNewTextureAndGetTextureID("res/coin.png", "coin");
	}
	
	public void inicializeTextures(textureManager tm)
	{
		System.out.println("Dodawanie tekstur przedmiotów");
		character = new CollidingObject(sizeX/2-24*characterScale , sizeY/2-44*characterScale, "character", "res/character.png", tm, new BoundingBox(new BBox(8, 0, 48, 90)));
		character.scale(characterScale);
		character.isStatic = true;
		background = new StaticObject(0, 0, "background", "res/background.png", tm);
	}
	
	public void refreshFrame()
	{
		super.refresh(texManagerMain);
		this.sizeX = getWidth();
		this.sizeY = getHeight();
		if(lifePoints == 0&&!gameEnd)
		{
			System.out.println("Zgon");
			gameEnd = true;
			timeToRespawn = System.currentTimeMillis();
		}
		if(gameEnd)
		{
		if(System.currentTimeMillis() - timeToRespawn > 1000)
		{
			System.out.println("Respawn");
			restartMap();
			gameEnd = false;
		}
		}
	}
	
	
	
	@Override
	public void useControls(Graphics2D g, float delta) 
	{
		super.useControls(g, delta);
		
		if(cm.R)
			network.randomizeAllWeights();
		
		if(cm.ALT&&!isAltClicked)
		{
			if(!isRecording)
			{
				record.clearFrames();
				recordTime = System.currentTimeMillis();
			}
			isAltClicked = true;
			isRecording = !isRecording;
		}
		if(!cm.ALT)
		{
			isAltClicked = false;
		}
		
		if(cm.RIGHT)
			playRecord = true;
		if(cm.LEFT)
			playRecord = false;
		
		if(cm.Q&&!isQClicked)
		{
			isQClicked = true;
			isNeuronNetworkPlaying=!isNeuronNetworkPlaying;
		}
		if(cm.Q==false)
		{
			isQClicked = false;
		}
		
		if(!playRecord)
		{
		if(!isNeuronNetworkPlaying)
		{
			playerKeys(g, delta);
		}
		else
		{
			networkKeys(g, delta);
		}
		}
		else
		{
			ubdateFrameKeys();
			
		}
	}

	private void ubdateFrameKeys() {
		RecordedFrame f = record.getActualFrame();
		locX = f.locX;
		locY = f.locY;
		lifePoints = (int) f.lifePoints;
		points = (int) f.points;
		items.setMapTab(f.itemTab);
		//setKeys(f.keyTab);
		network.setInputTab(f.dataTab);
		record.nextFrame();
		for(int i = 0; i < 1000; i++)
		network.calculateMistackes(network.calculateFloatTabFromBooleanTab(f.keyTab, 0.10f));
		//System.out.println("B³êdy");
		//network.printMistakes();
		medianMistake = network.getMedianMistake();
		network.learnNetworkOnMistakes();
	}

	private void playerKeys(Graphics2D g, float delta) {
		if(!gameEnd)
		{
		if(!isOnLayer)
		locY+=GRAVITY*delta;
		locY-=jumpSpeed;

		if(cm.W)
		{
			if(isOnLayer)
			locY-=PLAYER_SPEED_ON_LAYER*delta;
		}
		if(cm.S)
		{
			if(isOnLayer)
			locY+=PLAYER_SPEED_ON_LAYER*delta;
		}
		if(cm.A)
		{
			locX-=PLAYER_SPEED*delta;
		}
		if(cm.D)
		{
			locX+=PLAYER_SPEED*delta;
		}
		
		if(cm.SPACE&&canJump)
		{
			canJump = false;
			isOnLayer = false;
			if(!isOnLayer)
				jumpSpeed = JUMP_SPEED*delta;
			else
				jumpSpeed = PLAYER_SPEED_ON_LAYER*delta;
		}
		else
		{
			if(jumpSpeed>0)
			jumpSpeed -= 0.5f;
		}
			
		
		checkCollision(g);
		}
		cm.reset();
	}

	private void networkKeys(Graphics2D g, float delta) {
		cm2.ubdateButtonsSet(network.getResoultTab());
		if(!gameEnd)
		{
		if(!isOnLayer)
		locY+=GRAVITY*delta;
		locY-=jumpSpeed;

		if(cm2.W)
		{
			if(isOnLayer)
			locY-=PLAYER_SPEED_ON_LAYER*delta;
		}
		if(cm2.S)
		{
			if(isOnLayer)
			locY+=PLAYER_SPEED_ON_LAYER*delta;
		}
		if(cm2.A)
		{
			locX-=PLAYER_SPEED*delta;
		}
		if(cm2.D)
		{
			locX+=PLAYER_SPEED*delta;
		}
		
		if(cm2.SPACE&&canJump)
		{
			canJump = false;
			isOnLayer = false;
			if(!isOnLayer)
				jumpSpeed = JUMP_SPEED*delta;
			else
				jumpSpeed = PLAYER_SPEED_ON_LAYER*delta;
		}
		else
		{
			if(jumpSpeed>0)
			jumpSpeed -= 0.5f;
		}
			
		
		checkCollision(g);
		}
		cm2.reset();
	}
	
	public boolean[] getKeys()
	{
		return new boolean[]{cm.W, cm.A, cm.S, cm.D, cm.SPACE };
	}
	public void setKeys(boolean[] keys)
	{
		cm.W = keys[0];
		cm.A = keys[1];
		cm.S = keys[2];
		cm.D = keys[3];
		cm.SPACE = keys[4];
	}
	@Override
	public void drawFrame(Graphics2D g, float delta, textureManager texManager) 
	{
		super.drawFrame(g,delta,texManager);
		scaleStandardBoundingBoxes(mapTab, map.getScaleX(), map.getScaleY());
		scaleStandardBoundingBoxes(itemsTab, map.getScaleX(), map.getScaleY());
		background.drawObject(g, texManagerMain);
		map.setX(-locX);
		map.setY(-locY);
		map.drawMap(g, texManagerMap, sizeX, sizeY);
		items.setX(-locX);
		items.setY(-locY);
		items.drawMap(g, texManagerItems, sizeX, sizeY);
		
		map.checkCollizionDrawBoundingBoxes(g, sizeX, sizeY, mapTab, character.BB.bBox);
		items.checkCollizionDrawBoundingBoxes(g, sizeX, sizeY, itemsTab, character.BB.bBox);
		
		getCharacterArea(g);
		//drawAreaTab();
		drawAreaTab(g);
		setNetworkTab(246);
		network.calculateResoult();
		System.out.println("Koñcowy wynik");
		network.printOutputTab();
		character.drawObject(g, texManagerMain);
		character.BB.bBox.drawBBTab(g);
		g.setFont(new Font("Arial", 1, 20));
		g.drawString("Is neuron network playing: "+isNeuronNetworkPlaying, sizeX-600, 25);
		g.drawString("HP: "+lifePoints+"/100  P: "+points+"/"+maxPoint, sizeX-225, 25);
		if(playRecord)
		g.drawString(" Mistake: "+medianMistake, sizeX-280, sizeY-80);
		
		if(isRecording)
		{
			record.addNewFrame(new RecordedFrame(network.getInputTab().clone(), getKeys(), items.getMapTab(), items.getSizeX(), items.getSizeY(), locX, locY, points, lifePoints, isOnLayer, canJump, gameEnd));
			g.setColor(Color.RED);
			g.drawString("RECORDING "+((float)(System.currentTimeMillis()-recordTime)/1000)+" "+record.getFrameNum(), sizeX-280, sizeY-80);
		}
		
		g.setFont(new Font("Arial", 0, 12));
//		try {
//			Thread.sleep(41);
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
	}

	private void setNetworkTab(int sizeOfInput) 
	{
		float tab[] = new float[sizeOfInput];
		int index = 0;
		for(int i = 0; i < 11; i++)
		{
			for(int j = 0; j < 11; j++)
			{
				tab[index] = network.normaliseValues(mapValuesTab[j][i], 9);
				index++;
			}
		}
		
		for(int i = 0; i < 11; i++)
		{
			for(int j = 0; j < 11; j++)
			{
				tab[index] = network.normaliseValues(itemValueTab[j][i], 1);
				index++;
			}
		}
		tab[index] = network.normaliseValues(points, maxPoint);
		index++;
		if(isOnLayer)
			tab[index] = network.normaliseValues(1, 1);
		else
			tab[index] = network.normaliseValues(0, 1);
		index++;
		if(canJump)
			tab[index] = network.normaliseValues(1, 1);
		else
			tab[index] = network.normaliseValues(0, 1);
		if(gameEnd)
			tab[index] = network.normaliseValues(1, 1);
		else
			tab[index] = network.normaliseValues(0, 1);
		
		network.setInputTab(tab);
	}

	private void getCharacterArea(Graphics2D g) 
	{
		//System.out.println("Rozpoczêto");
		float[] player = character.BB.bBox.getBBTab();
		map.setClicked(g, (int)player[4], (int)player[5]);
		int[] centre = map.getClicked((int)player[4], (int)player[5]);
		if(centre==null)
		{
			for(int i = 0; i < 11; i++)
			{
				for(int j = 0; j < 11; j++)
				{
					mapValuesTab[j][i] = 0;
					itemValueTab[j][i] = 0;
				}
				
			}
			return;
		}
		int sX = centre[0] - 5;
		int sY = centre[1] - 5;
		int eX = centre[0] + 5;
		int eY = centre[1] + 5;
		int indX = 0;
		int indY = 0;
		//System.out.println("Wyznaczono obszar "+sX+" "+eX+" "+sY+" "+eY);
		for(int i = sX; i <= eX; i++)
		{
			for(int j = sY; j <= eY; j++)
			{
//				System.out.println(map.getFieldWithCheck(i, j));
				mapValuesTab[indX][indY] = BLOCK_VALUE_TAB[map.getFieldWithCheck(i, j)];
				indY++;
			}
			indX++;
			indY = 0;
		}
		indX = 0;
		indY = 0;
		
		for(int i = sX; i <= eX; i++)
		{
			for(int j = sY; j <= eY; j++)
			{
				itemValueTab[indX][indY] = ITEMS_VALUE_TAB[items.getFieldWithCheck(i, j)];
				indY++;
			}
			indX++;
			indY = 0;
		}
		//System.out.println("Zakoñczono");
	}
	
	@SuppressWarnings("unused")
	private void drawAreaTab()
	{
		System.out.println();
		for(int i = 0; i < 11; i++)
		{
			for(int j = 0; j < 11; j++)
			{
				System.out.print(mapValuesTab[j][i]+" ");
			}
			System.out.println();
		}
		//System.out.println();
	}
	private void drawAreaTab(Graphics2D g)
	{
		int x = 10;
		int y = 550;
		for(int i = 0; i < 11; i++)
		{
			for(int j = 0; j < 11; j++)
			{
				g.setColor(COLOR_VALUES_BLOCKS[mapValuesTab[j][i]]);
				g.fillRect(x+j*10, y+i*10, 10, 10);
			}
		}
		
		for(int i = 0; i < 11; i++)
		{
			for(int j = 0; j < 11; j++)
			{
				if(itemValueTab[j][i]==0)
					continue;
				g.setColor(COLOR_VALUES_ITEMS[itemValueTab[j][i]]);
				g.fillOval(x+j*10, y+i*10, 8, 8);
				g.setColor(Color.black);
				g.drawOval(x+j*10, y+i*10, 8, 8);
			}
		}
		//System.out.println();
	}
	@Override
	public void checkCollisions(Graphics2D g, float delta) 
	{
		if(!gameEnd)
		{
		super.checkCollisions(g, delta);
		checkCollision(g);
		}
	}

	private void checkCollision(Graphics2D g) {
		float[] newLoc = map.checkCollizionWithMove(g, sizeX, sizeY, mapTab, locX, locY, character.BB.bBox);
		locX = newLoc[0];
		locY = newLoc[1];
		items.checkCollizionWithGrab(g, sizeX, sizeY, itemsTab, locX, locY, character.BB.bBox);
		map.setX(-locX);
		map.setY(-locY);
		items.setX(-locX);
		items.setY(-locY);
	}
	public void clearUp() 
	{
		
		
	}


	public void keyPressed(KeyEvent e) {
		cm.keyPressed(e);
	}
	public void keyReleased(KeyEvent e) {
		cm.keyReleased(e);
	}
	public void keyTyped(KeyEvent e) {
		cm.keyTyped(e);
	}
	public void mouseWheelMoved(MouseWheelEvent arg0) {
		cm.mouseWheelMoved(arg0);
	}
	public void mouseDragged(MouseEvent arg0) {
		cm.mouseDragged(arg0);
	}
	public void mouseMoved(MouseEvent arg0) {
		cm.mouseMoved(arg0);
	}
	public void mouseClicked(MouseEvent arg0) {
		cm.mouseClicked(arg0);
	}
	public void mouseEntered(MouseEvent arg0) {
		cm.mouseEntered(arg0);
	}
	public void mouseExited(MouseEvent arg0) {
		cm.mouseExited(arg0);
	}
	public void mousePressed(MouseEvent arg0) {
		cm.mousePressed(arg0);
	}
	public void mouseReleased(MouseEvent arg0) {
		cm.mouseReleased(arg0);
	}
}
