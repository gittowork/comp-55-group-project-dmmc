package DMMC.Physics;

import java.awt.Image;

import acm.graphics.GImage;

public class Tile extends PhysicsObject{
	
	private TileType type;

	public Tile(GImage i, Image[] initAnimation, TileType t) 
	{
		super(i,initAnimation);
		
		type = t;
	}
	
	public Tile(GImage i, TileType t)
	{
		super(i);
		
		type = t;
	}
	
	public TileType getType()
	{
		return type;
	}
	
	public void setType(TileType t)
	{
		type = t;
	}
}
