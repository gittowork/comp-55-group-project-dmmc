package DMMC.Screen;

import java.util.ArrayList;

import com.sun.corba.se.impl.naming.namingutil.IIOPEndpointInfo;

import DMMC.Game;
import DMMC.Physics.Brussel;
import DMMC.Physics.Corn;
import DMMC.Physics.Entity;
import DMMC.Physics.Ghost;
import DMMC.Physics.Heart;
import DMMC.Physics.Kernel;
import DMMC.Physics.Player;
import DMMC.Physics.Sword;
import acm.graphics.GPoint;

public class LevelScreen extends Screen{


	private ArrayList <Entity> entities; 
	public ArrayList <Entity> lastWaveEs;
	private boolean loadedOnce = false;
	private int curWave;
	private int frameNum = 0;
	private Player player;
	private boolean updateNeeded; // if entities need to be added to screen
	// bit field, 8 bites in a byte, only using 4 for each key (up,left,right,x)
	private byte keysPressed; 	// fired every time key down
	private byte keysDown; 		// fired once key down
	public static ArrayList<GPoint> kernelsToBeSpawned;
	private static int curLevelID = -1;

	//private GButton pauseButton;  
	LevelScreen(int sizeX, int sizeY) { 
		super(sizeX, sizeY);
		keysDown = 0;
		entities = new ArrayList<Entity>();
		Entity e = new Player(1); //what happened here?
		Entity g = new Ghost(2);
		e.setScreenPosX(Game.windowWidth/2);
		e.setScreenPosY(Game.windowHeight/2);
		g.setScreenPosX(Game.windowWidth/2);
		g.setScreenPosY(Game.windowHeight/2);
		entities.add(e);
		entities.add(g);
		player = (Player)e;
		kernelsToBeSpawned = new ArrayList<GPoint>();
	}
	public int getCurWave(){return curWave;}
	public void setCurWave(int i){curWave = i;}



	public LevelScreen(int levelID)
	{		
		//parse string from level data
		super(Integer.parseInt(LevelData.getData(levelID)[0]),
				Integer.parseInt(LevelData.getData(levelID)[1]));

		curLevelID = levelID;
				
		keysDown = 0;
		String[] levelData = LevelData.getData(levelID);


		//create tiles
		initTiles(oneDChartoTwoDChar(levelData[2].toCharArray(), 
				levelSizeX, levelSizeY));

		loadWave(3);
		kernelsToBeSpawned = new ArrayList<GPoint>();
		lastWaveEs = new ArrayList<Entity>();
	}

	
	public void setNeedsUpdating(boolean u){updateNeeded = u;}
	
	public boolean needsUpdating(){return updateNeeded;}
	
	public ArrayList<Entity> getEntities(){
		return entities;
	}
	
	public void spawnEntity(int type, int posX, int posY)
	{
		switch (type) {
		case 0:
			// Player
			entities.add(new Player(entities.size()));
			
			//save pointer to player
			player = (Player)entities.get(entities.size() - 1);
			break;
		case 1:
			//Brussel
			entities.add(new Brussel(entities.size()));
			break;
		case 2:
			// CaliFr
			entities.add(new Ghost(entities.size()));
			break;
		case 3:
			// CornMg
			entities.add(new Corn(entities.size()));
			break;
		case 4:
			// CornCn			
			entities.add(new Kernel(entities.size()));
			break;
		case 5:
			//sword
			String facing = "left";
			if(player.getCurAnimationName().contains("right"))
				facing = "right";
			entities.add(new Sword(entities.size(), facing));
			break;
		case 6:
			entities.add(new Kernel(entities.size(), 7));
			break;
		case 7:
			entities.add(new Kernel(entities.size(), -7));
			System.out.println("Shoot");
			break;
		case 8:
			entities.add(new Heart(entities.size()));
			break;
		default:
			System.err.println("INVALID ENTITY");
			break;
		}
		
		//set initial position
		entities.get(entities.size() - 1).setScreenPos(new GPoint(posX, posY));
		
		//add to draw
		updateNeeded = true;
		
	}
	
	public void destroyEntity(int id) 
	{
		//Note: all Entities will stay in the list EVEN if destroyed until end of wave
		entities.get(id).deathAction();
		entities.get(id).setLiving(false);
		updateNeeded = true;
	}

	@Override
	public void inputRight() 
	{
		if((keysPressed & 1) != 1)
		{
			keysPressed += 1;
			keysDown += 1;
		}
	}


	@Override
	public void inputLeft() 
	{
		if((keysPressed & 4) != 4)
		{
			keysPressed += 4;
			keysDown += 4;
		}

	}

	@Override
	public void inputZ() 
	{
		if((keysPressed & 8) != 8)
		{
			keysPressed += 8;
			keysDown += 8;
		}	
	}

	@Override
	public void inputEnter() 
	{
		
		//if(pauseButton != null){	
		//	pauseButton.fireActionEvent(pauseButton.getGLabel().getLabel());
		//}
		
		
	}

	@Override
	public void inputUp() 
	{
		if((keysPressed & 2) != 2)
		{
			keysPressed += 2;
			keysDown += 2;
		}

	}

	@Override
	public void inputDown() 
	{
		// TODO Auto-generated method stub

	}


	@Override
	public void inputLeftReleased()
	{
		keysPressed -= 4;

	}

	@Override
	public void inputRightReleased()
	{
		keysPressed -= 1;

	}

	@Override
	public void inputUpReleased()
	{
		keysPressed -= 2;
	}
	@Override
	public void inputZReleased()
	{
		keysPressed -= 8;
	}


	@Override
	public void inputEsc() 
	{
		// TODO Auto-generated method stub
		
		

	}

	public Entity getPlayerEntity(){
		return player;
	}

	private char[][] oneDChartoTwoDChar(char[] levelArray, int sizeX, int sizeY)
	{
		char[][] newArr = new char[sizeY][sizeX];

		for(int y = 0; y < sizeY; y ++)
			for(int x = 0; x < sizeX; x ++)
				newArr[y][x] = levelArray[(y * sizeX) + x];

		return newArr;
	}

	@Override
	public void update() 
	{	
		playerControls();

		for(Entity e: getEntities())
			if(e.isLiving())
			{
				if(frameNum%e.getAnimationSpeed() == 0 && !(e instanceof Player))
					e.iterAnimantion();
				e.update();
			}
		
		for(GPoint g: kernelsToBeSpawned){			
			int type = 4;
			int x = (int)g.getX();
			int y = (int)g.getY();
			
			//Kernel spawns
			if(y > 0)
			{
				//shoot
				if(x > 0)
				{
					//right
					type = 6;
				}
				else
				{
					//left
					type = 7;
					x = -x;
				}
			}
			else
			{
				//stay
				y = -y;
			}
			System.out.println(type);
			spawnEntity(type, x, y);
		}
			
		kernelsToBeSpawned.clear();
		
		frameNum ++;
		
		if(frameNum == 60){
			frameNum = 0;
		}
		switch(gameState()){
		case 1:
			if((++curWave) < Integer.parseInt(LevelData.getData(curLevelID)[LevelData.getData(curLevelID).length - 1]))
				loadWave(curWave);
			break;
		case 2:
			loadWave(curWave);
			break;
		case 3://checked in Game
			break;
		default:
			break;
			
		}
	}

	@Override
	public void clear()
	{
		tileMap = null;
		entities = null; 
	}

	private void playerControls()
	{
		
		//System.out.println("GameState: " + gameState());
		
		//keys down
		if(keysDown != 0)
		{
			if((keysDown & 1) == 1)
			{
				// Right
				player.setAnimation("player-run-right");

			}
			if((keysDown & 2) == 2)
			{
				//UP

			}
			if((keysDown & 4) == 4)
			{
				// Left
				player.setAnimation("player-run-left");

			}
			if((keysDown & 8) == 8)
			{
				// X
				String dir = "left";
				if(player.getCurAnimationName().contains("right"))
					dir = "right";
				
				if(!Sword.onePresent)
				{
					player.setAnimation("player-attack-" + dir);
					spawnEntity(5, 0, 0); // note: position set in spawn
				}
			}
			
			keysDown = 0;
			System.out.println();
		}

		//keys pressed 
		if(keysPressed != 0)
		{
			if((keysPressed & 1) == 1)
			{
				// Move Right
				player.setVelX(Entity.maxVelX);
				
				if(!player.getCurAnimationName().contains("run") && !Sword.onePresent)
					player.setAnimation("player-run-right");
				
				//iterate animation
				if(frameNum % player.getAnimationSpeed() == 0)
					player.iterAnimantion();
				
				player.setForced(true);
			}
			if((keysPressed & 2) == 2)
			{
				//Jump
				if(player.isGrounded())
					player.setVelY(-6);
				player.setForced(true);

			}
			if((keysPressed & 4) == 4)
			{
				//Move Left
				player.setVelX(-Entity.maxVelX);
				
				if(!player.getCurAnimationName().contains("run")  && !Sword.onePresent)
					player.setAnimation("player-run-left");
				
				//iterate animation
				if(frameNum % player.getAnimationSpeed() == 0)
					player.iterAnimantion();
				
				player.setForced(true);

			}
			if((keysPressed & 8) == 8)
			{
				//Attack	
				
			}
		}
		else 
			player.setForced(false);
	}
	
	//public void setPauseButton(GButton b){
		//this.pauseButton=b;
	//}
	
	/*
	 * 0: nothing
	 * 1: next wave
	 * 2: reset cur wave
	 * 3: game over
	 * 4: all waves completed
	 * */
	public int gameState()
	{
		//next wave default
		int state = 1;
		
		for(Entity e: entities)
		{
			if(e.isLiving() 
					&& !(e instanceof Player) 
					&& !(e instanceof Sword)
					&& !(e instanceof Heart))
				state = 0; // enemy is still alive
			if(!player.isLiving())
				state = 2;
			if(player.getCurLives() == 0)
				state = 3;
			if(getCurWave() + 1 > Integer.parseInt(LevelData.getData(curLevelID)[LevelData.getData(curLevelID).length - 1]))
				state = 4;
		}
		
		return state;
	}
	private void loadWave(int waveNum){
		if(loadedOnce)
		{
			for(Entity e: entities)
				lastWaveEs.add(e);
			for(Entity e: lastWaveEs)
				destroyEntity(e.getId());
		}
		entities = new ArrayList<Entity>();
		String[] levelData = LevelData.getData(curLevelID);
		int startIndex = 3;
		for(int i = 0; i < waveNum; i++){
			startIndex += Integer.parseInt(levelData[startIndex]) * 3 + 1;
		}
		//add entities
		int maxIndex = (Integer.parseInt(levelData[startIndex]) * 3) + startIndex;
		System.out.println("start index: " + startIndex + " max index: " + maxIndex);
		for(int i = startIndex + 1; i < maxIndex; i += 3)
		{
			spawnEntity(Integer.parseInt(levelData[i]),
							Integer.parseInt(levelData[i + 1]) * Game.tileWidth,
							Integer.parseInt(levelData[i + 2]) * Game.tileHeight);
		}
		
		//reset sword
		Sword.onePresent = false;
		
		//add hearts
		GPoint pos;
		for(int i = 0; i < player.curLives; i ++)
		{
			pos = Game.tilePosToScreen(8 + i, 0);
			spawnEntity(8, (int)pos.getX(), (int)pos.getY());
		}
		
		loadedOnce = true;
	}
	
}