package DMMC.Physics;

import DMMC.Game;
import acm.graphics.GPoint;

public class CollisionPoint extends GPoint{
	

	private static final long serialVersionUID = 1L;
	
	private boolean collision;

	public CollisionPoint(double x, double y) {
		setLocation(x,y);
		collision = false;
		
	}
	
	public void setColliding(boolean c){collision = c;}
	
	public boolean getColliding(){return collision;}
	
	public int getTileX()
	{
		return Game.screenPosToTile(getX(), false);
	}
	public int getTileY()
	{
		return Game.screenPosToTile(getY(), true);
	}
}
