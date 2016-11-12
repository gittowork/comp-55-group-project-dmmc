package DMMC.Physics;

import acm.graphics.GPoint;

public class CollisionPoint extends GPoint{
	

	private static final long serialVersionUID = 1L;
	
	private boolean collision;
	private int tilePosX;
	private int tilePosY;

	public CollisionPoint(double x, double y) {
		setLocation(x,y);
		collision = false;
	}
	
	public void setColliding(boolean c){collision = c;}
	
	public boolean getColliding(){return collision;}
	
}
