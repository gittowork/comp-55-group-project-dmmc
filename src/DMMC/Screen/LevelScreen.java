package DMMC.Screen;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import com.sun.glass.ui.Window.Level;
import com.sun.tracing.dtrace.ProviderAttributes;

import DMMC.Game;
import DMMC.Physics.Brussel;
import DMMC.Physics.Entity;
import DMMC.Physics.Ghost;
import DMMC.Physics.Player;
import acm.graphics.GImage;
import acm.graphics.GPoint;

public class LevelScreen extends Screen implements ActionListener {

	/*
	 * Entity IDs
	 * 
	 * Player: 0
	 * Sprout: 1
	 * CaliFr: 2
	 * CornMg: 3
	 * CornCn: 4
	 * 
	 * */

	static String[][] levelData = {
			{
				"14",				// map sizeX
				"10",				// map sizeY
				  "11111111111111"
				+ "10011111111001"
				+ "10010000001001"
				+ "10010000001001"
				+ "10011111111001"
				+ "10000000000001"
				+ "10000000000001"
				+ "10011111111001"
				+ "10000000000001"
				+ "11111111111111", // map layout
				"4",				// num Entities
				"0","2","2",		// add Entity [ID],[TilePosX],[TilePosY]
				"1","6","6",			// add Entity [ID],[TilePosX],[TilePosY]
				"1","7","6",
				"1","6","3"
			}/*,

			{
				// Another Level

			}

			 */
	};

	private ArrayList <Entity> entities; 
	private short frameNum; 
	private int curWave;
	private Entity player;

	public LevelScreen(int sizeX, int sizeY) {
		super(sizeX, sizeY);
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
		super(Integer.parseInt(levelData[levelID][0]),
				Integer.parseInt(levelData[levelID][1]));

		//create tiles
		initTiles(oneDChartoTwoDChar(levelData[levelID][2].toCharArray(), 
				levelSizeX, levelSizeY));

		entities = new ArrayList<Entity>();

		//add entities
		int maxIndex = (Integer.parseInt(levelData[levelID][3]) * 3) + 4;
		Entity e;
		GPoint pos;
		for(int i = 4; i < maxIndex; i += 3)
		{
			//TODO center entities on spawn
			switch (Integer.parseInt(levelData[levelID][i])) {
			case 0:
				// Player
				e =	new Player();

				//set initial position
				pos = Game.tilePosToScreen(Integer.parseInt(levelData[levelID][i + 1]),
						Integer.parseInt(levelData[levelID][i + 2]));
				
				e.setScreenPos(pos);

				entities.add(e);
				player = e;
				break;
			case 1:
				// Sprout
				e = new Brussel();
				
				//set initial position
				pos = Game.tilePosToScreen(Integer.parseInt(levelData[levelID][i + 1]),
						Integer.parseInt(levelData[levelID][i + 2]));
				
				e.setScreenPos(pos);
				entities.add(e);

				break;
			case 2:
				// CaliFr
				e = new Ghost();

				//set initial position
				pos = Game.tilePosToScreen(Integer.parseInt(levelData[levelID][i + 1]),
						Integer.parseInt(levelData[levelID][i + 2]));
				
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

	public void drawEntities() {
		// TODO Auto-generated method stub

	}


	public void collisionUpdate() {
		// TODO Auto-generated method stub

	}


	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub

	}

	public short getFrame() 
	{
		return frameNum;

	}

	public ArrayList<Entity> getEntities(){
		return entities;
	}

	@Override
	public void inputRight() 
	{
		player.setVelX(Entity.maxVelX);
		player.setForced(true);
	}


	@Override
	public void inputLeft() 
	{
		player.setVelX(-Entity.maxVelX);
		player.setForced(true);
	}

	@Override
	public void inputZ() 
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void inputEnter() 
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void inputUp() 
	{
		if (player.isGrounded()){
			player.setVelY(-6);
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
		player.setForced(false);
	}

	@Override
	public void inputRightReleased()
	{
		player.setForced(false);
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
	public void clear()
	{
		tileMap = null;
		entities = null; 
	}
}