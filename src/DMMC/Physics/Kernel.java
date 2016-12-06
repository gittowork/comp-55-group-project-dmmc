package DMMC.Physics;

public class Kernel extends Entity{

	private String facing; //0 means facing left   1 means facing right
	private int id;
	public Kernel(int id, String f){
		super("kernel-0", id, true, true);
		facing = f;
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
		if(!isGrounded()){
			setVelY(2);
			if(facing.equals("right")){
				setVelX(2);
			}
			else{
				setVelX(-2);
			}
		}
	}
}
