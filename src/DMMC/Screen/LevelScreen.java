package DMMC.Screen;

import java.util.ArrayList;


import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.sound.midi.VoiceStatus;


import DMMC.Game;
import DMMC.Physics.Brussel;
import DMMC.Physics.Entity;
import DMMC.Physics.GButton;
import DMMC.Physics.Ghost;
import DMMC.Physics.Player;
import DMMC.Physics.Sword;
import acm.graphics.GPoint;

public class LevelScreen extends Screen{


	private ArrayList <Entity> entities; 
	private int curWave;
	private int frameNum = 0;
	private Entity player;
	private boolean updateNeeded; // if eneties need to be added to screen
	// bit field, 8 bites in a byte, only using 4 for each key (up,left,right,x)
	private byte keysPressed; 	// fired every time key down
	private byte keysDown; 		// fired once key down

	private GButton pauseButton;  LevelScreen(int sizeX, int sizeY) { //new pause button on game screen
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
		player = e;

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
		Entity e;
		GPoint pos;
		for(int i = 4; i < maxIndex; i += 3)
		{
			//TODO center entities on spawn
			switch (Integer.parseInt(levelData[i])) {
			case 0:
				// Player
				e =	new Player(entities.size());

				//set initial position
				pos = Game.tilePosToScreen(Integer.parseInt(levelData[i + 1]),
						Integer.parseInt(levelData[i + 2]));

				e.setScreenPos(pos);

				entities.add(e);
				player = (Player)e;
				break;
			case 1:
				// Sprout
				e = new Brussel(entities.size());

				//set initial position
				pos = Game.tilePosToScreen(Integer.parseInt(levelData[i + 1]),
						Integer.parseInt(levelData[i + 2]));

				e.setScreenPos(pos);
				entities.add(e);

				break;
			case 2:
				// CaliFr
				e = new Ghost(entities.size());

				//set initial position
				pos = Game.tilePosToScreen(Integer.parseInt(levelData[i + 1]),
						Integer.parseInt(levelData[i + 2]));

				e.setScreenPos(pos);

				entities.add(e);
				break;
			case 3:
				// CornMg
				break;
			case 4:
				// CornCn
				break;
			default:
				System.err.println("INVALID ENTITY");
				break;
			}
		}
	}

	
	public void setNeedsUpdating(boolean u){updateNeeded = u;}
	
	public boolean needsUpdating(){return updateNeeded;}
	
	public ArrayList<Entity> getEntities(){
		return entities;
	}
	
	public void spawnEntity(int type, double posX, double posY)
	{
		Entity e;
		GPoint pos;
		
		switch (type) {
		case 0:
			// Player
			e =	new Player(entities.size());

			//set initial position
			e.setScreenPos(new GPoint(posX, posY));

			entities.add(e);
			player = e;
			break;
		case 1:
			// Sprout
			e = new Brussel(entities.size());

			//set initial position
			e.setScreenPos(new GPoint(posX, posY));
			
			entities.add(e);

			break;
		case 2:
			// CaliFr
			e = new Ghost(entities.size());

			//set initial position
			e.setScreenPos(new GPoint(posX, posY));

			entities.add(e);
			break;
		case 3:
			// CornMg
			break;
		case 4:
			// CornCn
			break;
		case 5:
			//sword
			String facing = "left";
			
			if(player.getCurAnimationName().contains("right"))
				facing = "right";

			e = new Sword(entities.size(), facing);
			e.setScreenPos(new GPoint(posX, posY));

			entities.add(e);
			break;
		default:
			System.err.println("INVALID ENTITY");
			break;
		}
		
		//add to draw
		updateNeeded = true;
		
	}
	
	public void destroyEntity(int id) 
	{
		//Note: all Entities will stay in the list EVEN if destroyed until end of wave
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
	public void inputX() 
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
		
		if(pauseButton != null){	
			pauseButton.fireActionEvent(pauseButton.getGLabel().getLabel());
		}
		
		
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
	public void inputXReleased()
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
				e.update();
		
		frameNum ++;
		
		if(frameNum == 61)
			frameNum = 0;
	}

	@Override
	public void clear()
	{
		tileMap = null;
		entities = null; 
	}

	private void playerControls()
	{
		
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
				if(!Sword.onePresent)
					spawnEntity(5, 0, 0); // note: position set in spawn
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
	
	public void setPauseButton(GButton b){
		this.pauseButton=b;
	}
}