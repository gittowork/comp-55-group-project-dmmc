package DMMC.Physics;

import DMMC.Game;
import DMMC.Screen.LevelScreen;
import acm.graphics.GImage;

public class Player extends Entity {
	
	private static final int sizeX = 40;
	private static final int sizeY = 40;
	public static final int maxLives = 3;
	
	private boolean entityColision;
	private int attackTime = 0;
	private int curLives;
	
	public Player(int id, int lives) {
		super("player-idle-right",id, false, true);
		scaleScreenObj();		
		curLives = lives;
	}
	
	public Player(int id) {
		super("player-idle-right",id, false, true);
		scaleScreenObj();		
		curLives = maxLives;
	}
	
	public int getCurLives() {return curLives;}


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
				if(e.getScreenObj().contains(colPoints[i])
						&& !(e instanceof Player)
						&& !(e instanceof Sword)
						&& e.isLiving())
					entityColision = true;
		}
		
		if(entityColision)
		{
			setLiving(false);
			curLives --;
			System.out.println("curLives: " + curLives);
		}
		
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
