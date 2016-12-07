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
	private double SPEED = 1;
	private static final int sizeX = 50;
	private static final int sizeY = 50;
	private GPoint chargeTo;
	private boolean charge;
	
	public Ghost(int id){
		super ("ghost-idle",id, true, false);	
		scaleScreenObj();
		animationSpeed = 45;
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
double xDifference,yDifference, distance, ratio;
		
		playerLoc = Game.player.getScreenPos();
		
		xDifference = playerLoc.getX() - getScreenPosX();
		yDifference = playerLoc.getY() - getScreenPosY();
		
		distance = Math.pow(Math.pow(xDifference,2) + Math.pow(yDifference,2), (0.5));
				
		if(!charge)
		{
			charge = (distance < 130);
			if(charge)
				chargeTo = playerLoc;
			else
				chargeTo = new GPoint(-1, -1);
			
			animationSpeed = 45;
			SPEED = 1;

		}
		else
		{
			xDifference = chargeTo.getX() - getScreenPosX();
			yDifference = chargeTo.getY() - getScreenPosY();
			distance = Math.pow(Math.pow(xDifference,2) + Math.pow(yDifference,2), (0.5));
			
			animationSpeed = 10;
			SPEED = 3;
			
			charge = (distance > 2);
		}
		
		if (distance != 0)
			ratio = SPEED / distance;
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
