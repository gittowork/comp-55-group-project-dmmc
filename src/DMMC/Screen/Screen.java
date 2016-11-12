package DMMC.Screen;

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
					tileMap[i][j] = new Tile(new GImage("solid.png"), TileType.Solid);
					break;
				case "0":
					tileMap[i][j] = new Tile(new GImage("empty.png"), TileType.Empty);
					break;
				default:
					tileMap[i][j] = new Tile(new GImage("empty.png"), TileType.Empty);
					break;
				}

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
