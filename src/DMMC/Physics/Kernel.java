package DMMC.Physics;

import java.util.Random;

public class Kernel extends Entity{
	
	private static boolean lastFacing = false; 
	
	private boolean facing; //false means facing left true means facing right
	private int id;
	private double speed;
	private final int sizeX = 25;
	private final int sizeY = 25;
	private Random rand = new Random();
	public Kernel(int id, boolean f){
		super("kernel-run", id, false, true);
		facing = f;
		speed = rand.nextDouble()* 2 + 1;
		scaleScreenObj();
	}
	
	public Kernel(int id){
		super("kernel-run", id, false, true);
		lastFacing = !lastFacing;
		facing = lastFacing;
		speed = rand.nextDouble()* 2 + 1;
		scaleScreenObj();
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
		
		if (isGrounded()){
			setVelY(-2);
		}
		if(!facing){
			setVelX(speed);
		}
		else{
			setVelX(-speed);
		}
		if(colIndex[1] != 0)
		{
			//change direction
			facing = true;
		}
		if(colIndex[3] != 0)
		{
			facing = false;
		}
	}
	@Override
	protected void scaleScreenObj()
	{
		screenObj.setSize(sizeX, sizeY);
	}
}
