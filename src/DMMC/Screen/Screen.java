package DMMC.Screen;

import DMMC.Game;
import DMMC.Physics.Tile;
import DMMC.Physics.TileType;
import acm.graphics.GImage;

public abstract class Screen {

	protected Tile[][] tileMap;
	public int levelSizeX;
	public int levelSizeY;

	public Screen(int sizeX, int sizeY)
	{

		levelSizeX = sizeX;
		levelSizeY = sizeY;
		tileMap = new Tile[sizeX][sizeY];
	}

	public Tile getTile(int i, int j){

		return tileMap[i][j];
	}
	public void setTile(int i, int j, TileType tt){
		tileMap[i][j].setType(tt);
	}
	// void drawAll() {} //drawing all tiles 

	public Tile[][] getTitleMap() {
		return tileMap;
	}


	public void initTiles(char arr[][]) 
	{

		for(int i = 0; i < levelSizeX; i++)
		{
			for(int j = 0; j < levelSizeY; j++)
			{
				switch (arr[j][i]) 
				{
				case '1':
					tileMap[i][j] = new Tile(new GImage("solid.png"), TileType.Dirt);
					break;
				case '0':
					tileMap[i][j] = new Tile(new GImage("empty.png"), TileType.Air);
					break;
				default:
					tileMap[i][j] = new Tile(new GImage("empty.png"), TileType.Air);
					break;
				}
				tileMap[i][j].setScreenPosX(i * Game.tileWidth);
				tileMap[i][j].setScreenPosY(j * Game.tileHeight);
				tileMap[i][j].getScreenObj().setSize(Game.tileWidth, Game.tileHeight);

			}
		}
	}




	public abstract void inputUp();

	public abstract void inputDown();

	public abstract void inputLeft();

	public abstract void inputRight();

	public abstract void inputZ();

	public abstract void inputEnter();

	public abstract void inputEsc();
	
	public abstract void inputLeftReleased();
	
	public abstract void inputRightReleased();
	
	public void clear()
	{
		tileMap = null;
	}
}
