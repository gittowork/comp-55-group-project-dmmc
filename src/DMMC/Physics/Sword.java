package DMMC.Physics;

import DMMC.Game;
import DMMC.Screen.LevelScreen;
import sun.net.www.http.KeepAliveCache;

public class Sword extends Entity {

	private static final int sizeX = 20;
	private static final int sizeY = 5;	
	public static final int maxLife = 15; // num of frames
	
	public static boolean onePresent = false;
	
	private int numFramesAlive = 0;
	
	//facing = right or left
	public Sword(int ID, String facing) {
		super("sword-" + facing, ID, true, true);
		
		onePresent = true;
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
		
		LevelScreen temp = (LevelScreen)Game.getCurScreen();
		
		numFramesAlive ++;
		
		//keep next to player
		if(Game.player.getCurAnimationName().contains("right"))
		{
			
			setScreenPosX(Game.player.getScreenPosX() + Game.player.getScreenObj().getWidth());
			setScreenPosY(Game.player.getScreenPosY() + (Game.player.getScreenObj().getHeight() / 2) - (getScreenObj().getHeight() / 2));
		}
		else 
		{			
			setScreenPosX(Game.player.getScreenPosX() - getScreenObj().getWidth());
			setScreenPosY(Game.player.getScreenPosY() + (Game.player.getScreenObj().getHeight() / 2) - (getScreenObj().getHeight() / 2));
		}
		
		//destroy of alive for too long
		if(numFramesAlive >= maxLife)
		{
			temp.destroyEntity(getId());
			onePresent = false;
		}
		
		//check for entity colisions

		for(int i = 0; i < colPoints.length; i += 2)
			for(Entity e: temp.getEntities())
				if(e.isLiving() 
						&& !(e instanceof Player) 
						&& !(e instanceof Sword))
					if(e.getScreenObj().contains(colPoints[i]))
						//destroy entity
						temp.destroyEntity(e.getId());
		
	}
	
	@Override
	protected void scaleScreenObj()
	{
		screenObj.setSize(sizeX, sizeY);
	}


}
