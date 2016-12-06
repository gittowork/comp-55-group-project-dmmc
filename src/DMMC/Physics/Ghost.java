package DMMC.Physics;

import java.awt.Image;
import acm.graphics.GImage;
import acm.graphics.GPoint;

import java.lang.Math.*;

import DMMC.Game;
import DMMC.Screen.LevelScreen;

public class Ghost extends Entity {

	GImage Sprite;
	static final String GHOST_FILE_PATH = "";
	GPoint playerLoc;
	private final double SPEED = 1.5;
	private static final int sizeX = 50;
	private static final int sizeY = 50;
	
	public Ghost(int id){
		super ("ghost-idle",id, true, false);	
		scaleScreenObj();
		animationSpeed = 60;
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
		double xDifference,yDifference, hypotnuse, ratio;
		
		playerLoc = Game.player.getScreenPos();
		
		xDifference = playerLoc.getX() - getScreenPosX();
		yDifference = playerLoc.getY() - getScreenPosY();
		
		hypotnuse =Math.pow(Math.pow(xDifference,2) + Math.pow(yDifference,2), (0.5));
		if (hypotnuse != 0)
			ratio = SPEED / hypotnuse;
		else
			ratio = 0;
		
		xDifference *= ratio;
		yDifference *= ratio;
		
		setVelX(xDifference);
		setVelY(yDifference);
	}
	
	@Override
	protected void scaleScreenObj()
	{
		screenObj.setSize(sizeX, sizeY);
	}
}
