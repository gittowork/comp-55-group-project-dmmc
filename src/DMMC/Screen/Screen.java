package DMMC.Screen;

public abstract class Screen {
	
	//private Tile[][] tileMap;
	private int levelSizeX;
	private int levelSizeY;
	
	public Screen(int sizeX, int sizeY)
	{
		
		levelSizeX = sizeX;
		levelSizeY = sizeY;
		//tileMap = new Tile[sizeX][sizeY];
	}

}
