package DMMC.Physics;

import java.util.ArrayList;

import DMMC.Game;
import DMMC.Screen.LevelScreen;
import acm.graphics.GPoint;

public class Corn extends Entity{
	private boolean facing; //false = left; true = right
	private int counter;
	private final int sizeX = 50;
	private final int sizeY = 50;
	public Corn(int id) {
		super("default", id, false, true);
		scaleScreenObj();
		facing = false;
		counter = 0;
		
		//set aniamtion
		setAnimation("corn-idle-" + getFacingString());
	}

	@Override
	public void spawnAction() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deathAction() {
//		GPoint g = new GPoint(getScreenPosX(), -getScreenPosY());
//		LevelScreen.kernelsToBeSpawned.add(g);
//		LevelScreen.kernelsToBeSpawned.add(g);
//		LevelScreen.kernelsToBeSpawned.add(g);
	}

	@Override
	public void behaviorAction() {
		if (counter % 180 == 0){
			GPoint g = new GPoint(getScreenPosX(), getScreenPosY());
			if (!facing)
				g.setLocation(-g.getX(), g.getY());
			
			LevelScreen.kernelsToBeSpawned.add(g);
			setAnimation("corn-attack-" + getFacingString());
			counter = 0;
		}
		
		if(counter % 180 > 30)
			//reset to idle
			setAnimation("corn-idle-" + getFacingString());
		
		facing = (Game.player.getScreenPosX() - getScreenPosX() > 0);
		
		counter++;
	}
	protected void scaleScreenObj()
	{
		screenObj.setSize(sizeX, sizeY);
	}
	
	private String getFacingString()
	{
		if(facing)
			return "right";
		
		return "left";
	}
}
