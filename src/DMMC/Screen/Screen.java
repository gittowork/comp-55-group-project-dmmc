package DMMC.Screen;

import DMMC.Physics.Tile;
import DMMC.Physics.TileType;

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
	public void drawAll() {

	}
	public void drawTiles() {
		

	}
	
	public abstract void inputUp();

	public abstract void inputDown();

	public abstract void inputLeft();

	public abstract void inputRight();

	public abstract void inputZ();

	public abstract void inputEnter();
	
	
}
