package DMMC.Physics;

import DMMC.Game;
import acm.graphics.GPoint;

public class Heart extends Entity{
	
	private double sizeX = Game.tileWidth;
	private double sizeY = Game.tileHeight;
//	private int counter = 0;
//	private GPoint initPos,secondPos;

	public Heart(int id) {
		super("heart", id, true, false);
//		initPos = screenObj.getLocation();
//		secondPos = new GPoint(screenObj.getX() + screenObj.getWidth() * (0.25/2), 
//				screenObj.getY() + screenObj.getHeight() * (0.25/2));
		// TODO Auto-generated constructor stub
	}

	@Override
	public void spawnAction() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deathAction() {
		// TODO Auto-generated method stub
	}

	@Override
	public void behaviorAction() {
		// TODO Auto-generated method stub
	}

	@Override
	protected void scaleScreenObj()
	{
		screenObj.setSize(sizeX, sizeY);
	}
	
}
