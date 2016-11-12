package DMMC.Screen;

import DMMC.Game;
import DMMC.Physics.Tile;
import DMMC.Physics.TileType;
import acm.graphics.GImage;

public abstract class Screen {

	private Tile[][] tileMap;
	private int levelSizeX;
	private int levelSizeY;

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


	public void initTiles(String arr[][]) 
	{
		int X = Math.min(arr.length, levelSizeX);
		int Y = Math.min(arr[0].length, levelSizeY);

		for(int i = 0; i < X; i++)
		{
			for(int j = 0; j < Y; j++)
			{
				switch (arr[i][j]) 
				{
				case "1":
					tileMap[i][j] = new Tile(new GImage("solid.png"), TileType.Dirt);
					break;
				case "0":
					tileMap[i][j] = new Tile(new GImage("empty.png"), TileType.Air);
					break;
				default:
					tileMap[i][j] = new Tile(new GImage("empty.png"), TileType.Air);
					break;
				}
				System.out.println(i * Game.tileWidth);
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


}
