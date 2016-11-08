package DMMC.Physics;

public enum TileType {
	Empty("Init"),
	Solid("UserSelectScreen");
	//...
	
	private String name;
	
	private TileType(String n) {
		name= n;
	}
	
	@Override
	public String toString()
	{
		return name;
	}
}
