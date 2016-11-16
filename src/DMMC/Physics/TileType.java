package DMMC.Physics;


public enum TileType {
	Air("Air",false),
	Dirt("Dirt", true);
	//...
	
	private String name;
	private boolean solid;
	
	private TileType(String n, boolean s) {
		name= n;
		solid = s;
	}
	
	@Override
	public String toString()
	{
		return name;
	}
	
	public boolean isSolid()
	{
		return solid;
	}
}
