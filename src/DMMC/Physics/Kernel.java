package DMMC.Physics;

import java.util.Random;

public class Kernel extends Entity{
	
	private static boolean lastFacing = false; 
	
	private boolean facing; //false = left; true = right
	private double ispeed;
	private double speed;
	private final int sizeX = 30;
	private final int sizeY = 30;
	private Random rand = new Random();
	public Kernel(int id){
		super("kernel-run", id, false, true);
		lastFacing = !lastFacing; 
		facing = lastFacing;
		speed = rand.nextDouble()* 2 + 1;
		scaleScreenObj();
	}
	
	public Kernel(int id, double s){
		super("kernel-run", id, false, true);
		facing = (s > 0);
		ispeed = s;
		speed = s;
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
		
		setVelX(speed);
		
		if(colIndex[1] != 0)
		{
			//change direction
			speed = -rand.nextDouble()* 2 - 1;
			facing = true;
		}
		if(colIndex[3] != 0)
		{
			
			speed = rand.nextDouble()* 2 + 1;
			facing = false;
		}
	}
	@Override
	protected void scaleScreenObj()
	{
		screenObj.setSize(sizeX, sizeY);
	}
}
