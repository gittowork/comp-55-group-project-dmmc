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
		super("corn-idle", id, false, true);
		scaleScreenObj();
		facing = true;
		counter = 0;
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
		}
		counter++;
	}
	protected void scaleScreenObj()
	{
		screenObj.setSize(sizeX, sizeY);
	}
}
