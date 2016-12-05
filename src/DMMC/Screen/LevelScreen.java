package DMMC.Screen;

import java.util.ArrayList;

import DMMC.Game;
import DMMC.Physics.Brussel;
import DMMC.Physics.Entity;
import DMMC.Physics.Ghost;
import DMMC.Physics.Player;
import acm.graphics.GPoint;

public class LevelScreen extends Screen{


	private ArrayList <Entity> entities; 
	private int curWave;
	private Entity player;
	
	// bit field, 8 bites in a byte, only using 4 for each key (up,left,right,x)
	private byte keysDown;
	
	public LevelScreen(int sizeX, int sizeY) {
		super(sizeX, sizeY);
		keysDown = 0;
		entities = new ArrayList<Entity>();
		Entity e = new Player();
		Entity g = new Ghost();
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
				e =	new Player();

				//set initial position
				pos = Game.tilePosToScreen(Integer.parseInt(levelData[i + 1]),
						Integer.parseInt(levelData[i + 2]));
				
				e.setScreenPos(pos);

				entities.add(e);
				player = e;
				break;
			case 1:
				// Sprout
				e = new Brussel();
				
				//set initial position
				pos = Game.tilePosToScreen(Integer.parseInt(levelData[i + 1]),
						Integer.parseInt(levelData[i + 2]));
				
				e.setScreenPos(pos);
				entities.add(e);

				break;
			case 2:
				// CaliFr
				e = new Ghost();

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

	public ArrayList<Entity> getEntities(){
		return entities;
	}

	@Override
	public void inputRight() 
	{

		if((keysDown & 1) != 1)
			keysDown += 1;
		
	}


	@Override
	public void inputLeft() 
	{
		if((keysDown & 4) != 4)
			keysDown += 4;
		
	}

	@Override
	public void inputX() 
	{
		if((keysDown & 8) != 8)
			keysDown += 8;
	}

	@Override
	public void inputEnter() 
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void inputUp() 
	{
		if((keysDown & 2) != 2)
			keysDown += 2;
		
	}

	@Override
	public void inputDown() 
	{
		// TODO Auto-generated method stub

	}


	@Override
	public void inputLeftReleased()
	{
		keysDown -= 4;
		
	}

	@Override
	public void inputRightReleased()
	{
		keysDown -= 1;
		
	}
	
	@Override
	public void inputUpReleased()
	{
		keysDown -= 2;
	}
	@Override
	public void inputXReleased()
	{
		keysDown -= 8;
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
		if(keysDown != 0)
		{
			if((keysDown & 1) == 1)
			{
				// Move Right
				player.setVelX(Entity.maxVelX);
				player.setForced(true);
			}
			if((keysDown & 2) == 2)
			{
				//Jump
				if(player.isGrounded())
					player.setVelY(-6);
				player.setForced(true);

			}
			if((keysDown & 4) == 4)
			{
				//Move Left
				player.setVelX(-Entity.maxVelX);
				player.setForced(true);

			}
			if((keysDown & 8) == 8)
			{
				//Attack	
			}
		}
		else 
			player.setForced(false);
		for(Entity e: getEntities())
			e.update();
	}

	@Override
	public void clear()
	{
		tileMap = null;
		entities = null; 
	}
}