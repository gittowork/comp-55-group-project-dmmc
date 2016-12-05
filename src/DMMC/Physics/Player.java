package DMMC.Physics;

import DMMC.Game;
import DMMC.Screen.LevelScreen;
import acm.graphics.GImage;

public class Player extends Entity {
	
	private static final int sizeX = 40;
	private static final int sizeY = 40;
	
	
	private boolean entityColision;
	private int attackTime = 0;
	
	public Player(int id) {
		super("player-idle-right",id, false, true);
		scaleScreenObj();		
	}


	@Override
	public void spawnAction() {
		playSpawnAnimation();
	}

	private void playSpawnAnimation() {
		
	}

	@Override
	public void deathAction() {
		playDeathAnimation();
	}

	private void playDeathAnimation() {
		
	}

	@Override
	public void behaviorAction() {
		//check for entity colisions
		entityColision = false;
		
		//loop through every other col point
		for(int i = 0; i < colPoints.length; i += 2)
		{
			LevelScreen s = (LevelScreen)Game.getCurScreen();
			for(Entity e: s.getEntities())
			{
				if(e.getScreenObj().contains(colPoints[i])
						&& Game.player != e)
					entityColision = true;
			}
		}
		
		if(entityColision);
			//System.out.println("Ya dead");
		
		if(getCurAnimationName().contains("attack"))
			attackTime ++;
		if(attackTime >= Sword.maxLife)
		{
			//reset animation
			String dir = "left";
			if(getCurAnimationName().contains("right"))
				dir = "right";
			
			setAnimation("player-idle-" + dir);
			
			attackTime = 0;
		}
	}
	
	@Override
	protected void scaleScreenObj()
	{
		screenObj.setSize(sizeX, sizeY);
	}

}
