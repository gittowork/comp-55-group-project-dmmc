package DMMC.Physics;

import DMMC.Game;
import DMMC.Screen.LevelScreen;

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
		
		
	}

	@Override
	public void behaviorAction() {
		if (Game.player.getScreenPosX() - getScreenPosX() < 0){
			facing = true;
		}
		else{
			facing = false;
		}
		if (counter % 180 == 0){
			LevelScreen temp = (LevelScreen)Game.getCurScreen();
			temp.spawnEntity(6, (int)getScreenPosX(), (int)getScreenPosY(), 1);
		}
		counter++;
	}
	protected void scaleScreenObj()
	{
		screenObj.setSize(sizeX, sizeY);
	}
}
