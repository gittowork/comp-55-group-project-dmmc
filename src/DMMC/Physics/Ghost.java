package DMMC.Physics;

import java.awt.Image;

import acm.graphics.GImage;

public class Ghost extends Entity {

	GImage Sprite;
	static final String GHOST_FILE_PATH = "NEEDS PATH";
	
	public void init(){
		GImage Sprite = new GImage(GHOST_FILE_PATH);
		setWeightless(true);
		setCollideable(false);
	}
	
	public Ghost(GImage i) {
		super (i);
	}
	
	public Ghost(GImage i, Image[] initAnimation){
		super (i, initAnimation);
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
		
		
	}

}
