package DMMC.Physics;

import java.awt.Image;

import DMMC.Game;
import acm.graphics.GImage;
import acm.graphics.GPoint;

public class Entity extends PhysicsObject{
	
	private static int lastId = 0;
	private static double colPointPadding = 2;

	
	protected GPoint velocity;
	protected GPoint acceleration;
	private int id;
	protected int curHealth;
	protected int maxHealth;
	protected boolean weightless;
	protected boolean collidable;
	protected CollisionPoint[] colPoints;

	public Entity(GImage i, Image[] initAnimation) {
		super(i, initAnimation);
		initPoints();
		
	}
	
	public Entity(GImage i) {
		super(i);
		initPoints();
	}
	
	public double getVelX(){return velocity.getX();}
	public double getVelY(){return velocity.getY();}
	public double getAccX(){return acceleration.getX();}
	public double getAccY(){return acceleration.getY();}
	public int getId(){return id;}
	public boolean isWeightless() {return weightless;}
	public boolean isCollideable() {return collidable;}
	
	public void setVelX(double x){ velocity.setLocation(x,getVelY());}
	public void setVelY(double y){ velocity.setLocation(getVelX(),y);}
	public void setAccX(double x){ acceleration.setLocation(x,getAccY());}
	public void setAccY(double y){ acceleration.setLocation(getAccX(),y);}
	public void setWeightless(boolean b){weightless = b;}
	public void setCollideable(boolean b){collidable = b;}
	
	public void update(){
		setAccY(Game.GRAVITY /Game.FPS);
		setVelX(getVelX() + getAccX() );
		setVelY(getVelY() + getAccY() );
		screenObj.move(getVelX(), getVelY());
		System.out.println(getAccY());
	}
	
	public void initPoints(){
		//Vector Points
		velocity = new GPoint(0, 0);
		acceleration = new GPoint(0, 0);
		
		//Collision Points
		colPoints = new CollisionPoint[8];
		
		//top
		colPoints[0] = new CollisionPoint(getScreenPosX(), getScreenPosY() - colPointPadding);
		colPoints[1] = new CollisionPoint(getScreenPosX() + screenObj.getWidth(), getScreenPosY() - colPointPadding);
		
		//left
		colPoints[2] = new CollisionPoint(getScreenPosX() + screenObj.getWidth() + colPointPadding, getScreenPosY());
		colPoints[3] = new CollisionPoint(getScreenPosX() + screenObj.getWidth() + colPointPadding, getScreenPosY() + screenObj.getHeight());
		
		//bottom
		colPoints[4] = new CollisionPoint(getScreenPosX() + screenObj.getWidth(), getScreenPosY() + screenObj.getHeight() + colPointPadding);
		colPoints[5] = new CollisionPoint(getScreenPosX(), getScreenPosY() + screenObj.getHeight() + colPointPadding);
		
		//right
		colPoints[6] = new CollisionPoint(getScreenPosX() - colPointPadding, getScreenPosY() + screenObj.getHeight());
		colPoints[7] = new CollisionPoint(getScreenPosX() - colPointPadding, getScreenPosY());
		
	}
//	public abstract void spawnAction();
//	public abstract void deathAction();
//	public abstract void behaviorAction();
}
