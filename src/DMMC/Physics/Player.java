package DMMC.Physics;

import java.awt.Image;

import acm.graphics.GImage;

public class Player extends Entity {
	
	private static final int sizeX = 40;
	private static final int sizeY = 40;

	public Player(GImage i, Image[] initAnimation) {
		super(i, initAnimation);
		scaleScreenObj();
	}

	public Player(GImage i) {
		super(i);
		
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
		
	}
	
	@Override
	protected void scaleScreenObj()
	{
		screenObj.setSize(sizeX, sizeY);
	}

}
