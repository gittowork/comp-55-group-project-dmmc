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
	private final double SPEED = .5;
	
	public Ghost(GImage i) {
		super (i);
		setWeightless(true);
		setCollideable(false);
	}
	
	public Ghost(GImage i, Image[] initAnimation){
		super (i, initAnimation);
		setWeightless(true);
		setCollideable(false);
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
		double xDifference, yDifference, angle;
		playerLoc = new GPoint(Game.player.getScreenPos());
		xDifference = getScreenPosX() - playerLoc.getX();
		yDifference = playerLoc.getY() - getScreenPosY();
		angle = Math.toDegrees(Math.atan(yDifference/xDifference));
		getScreenObj().movePolar(SPEED, angle);
		System.out.println(angle);
	}

}
