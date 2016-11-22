package DMMC.Physics;

import java.awt.Image;

import acm.graphics.GImage;

public class Player extends Entity {

	public Player(GImage i, Image[] initAnimation) {
		super(i, initAnimation);
	}

	public Player(GImage i) {
		super(i);
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

}
