package DMMC.Screen;

import java.util.ArrayList;


import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.sound.midi.VoiceStatus;


import DMMC.Game;
import DMMC.Physics.Brussel;
import DMMC.Physics.Corn;
import DMMC.Physics.Entity;
import DMMC.Physics.GButton;
import DMMC.Physics.Ghost;
import DMMC.Physics.Kernel;
import DMMC.Physics.Player;
import DMMC.Physics.Sword;
import acm.graphics.GPoint;

public class LevelScreen extends Screen{


	private ArrayList <Entity> entities; 
	private int curWave;
	private int frameNum = 0;
	private Player player;
	private boolean updateNeeded; // if entities need to be added to screen
	// bit field, 8 bites in a byte, only using 4 for each key (up,left,right,x)
	private byte keysPressed; 	// fired every time key down
	private byte keysDown; 		// fired once key down
	public static ArrayList<GPoint> kernelsToBeSpawned;

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




	public LevelScreen(int levelID)
	{		
		//parse string from level data
		super(Integer.parseInt(LevelData.getData(levelID)[0]),
				Integer.parseInt(LevelData.getData(levelID)[1]));

		keysDown = 0;
		String[] levelData = LevelData.getData(levelID);


		//create tiles
		initTiles(oneDChartoTwoDChar(levelData[2].toCharArray(), 
				levelSizeX, levelSizeY));

		entities = new ArrayList<Entity>();

		//add entities
		int maxIndex = (Integer.parseInt(levelData[3]) * 3) + 4;
		
		for(int i = 4; i < maxIndex; i += 3)
		{
			spawnEntity(Integer.parseInt(levelData[i]),
							Integer.parseInt(levelData[i + 1]) * Game.tileWidth,
							Integer.parseInt(levelData[i + 2]) * Game.tileHeight,
							Player.maxLives);
		}
		kernelsToBeSpawned = new ArrayList<GPoint>();
	}

	
	public void setNeedsUpdating(boolean u){updateNeeded = u;}
	
	public boolean needsUpdating(){return updateNeeded;}
	
	public ArrayList<Entity> getEntities(){
		return entities;
	}
	
	public void spawnEntity(int type, int posX, int posY, int playerHealth)
	{
		switch (type) {
		case 0:
			// Player
			entities.add(new Player(entities.size(), playerHealth));
			
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
//			if (g.getY() > 0){
//				if(g.getX() > 0)
//					spawnEntity(6, (int)g.getX(), (int)g.getY(), 1);
//				else
//					spawnEntity(7, -(int)g.getX(), (int)g.getY(), 1);
//			}
//			else
//				spawnEntity(4, (int)g.getX(), -(int)g.getY(), 1);
			
			int type = 4;
			int x = (int)g.getX();
			int y = (int)g.getY();
			
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
			spawnEntity(type, x, y, 1);
		}
			
		kernelsToBeSpawned.clear();
		
		frameNum ++;
		
		if(frameNum == 60){
			frameNum = 0;
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
					spawnEntity(5, 0, 0, player.getCurLives()); // note: position set in spawn
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
	 * */
	public int gameState()
	{
		//next wave default
		int state = 1;
		
		for(Entity e: entities)
		{
			if(e.isLiving() 
					&& !(e instanceof Player) 
					&& !(e instanceof Sword))
				state = 0; // enemy is still alive
			if(!player.isLiving())
				state = 2;
			if(player.getCurLives() == 0)
				state = 3;
		}
			
		
		return state;
	}
}