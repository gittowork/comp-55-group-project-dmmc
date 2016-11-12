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
		

		setColPoint();
		
		//set desired velocity before collision
		setAccY(Game.GRAVITY /Game.FPS);
		setVelX(getVelX() + getAccX());
		setVelY(getVelY() + getAccY());
		
		for(int i = 0; i < colPoints.length; i ++)
			if(colPoints[i].getColliding())
			{
				setVelX(0);
				setVelY(0);
			}	
		screenObj.move(getVelX(), getVelY());

	}
	
	private void initPoints(){
		//Vector Points
		velocity = new GPoint(0, 0);
		acceleration = new GPoint(0, 0);
		
		//Collision Points
		colPoints = new CollisionPoint[8];
		for(int i = 0; i < colPoints.length; i ++)
			colPoints[i] = new CollisionPoint(0,0);
			
	}
	
	private void setColPoint()
	{
		//top
		colPoints[0].setLocation(getScreenPosX(), getScreenPosY() - colPointPadding);
		colPoints[1].setLocation(getScreenPosX() + screenObj.getWidth(), getScreenPosY() - colPointPadding);
				
		//left
		colPoints[2].setLocation(getScreenPosX() + screenObj.getWidth() + colPointPadding, getScreenPosY());
		colPoints[3].setLocation(getScreenPosX() + screenObj.getWidth() + colPointPadding, getScreenPosY() + screenObj.getHeight());
				
		//bottom
		colPoints[4].setLocation(getScreenPosX() + screenObj.getWidth(), getScreenPosY() + screenObj.getHeight() + colPointPadding);
		colPoints[5].setLocation(getScreenPosX(), getScreenPosY() + screenObj.getHeight() + colPointPadding);
				
		//right
		colPoints[6].setLocation(getScreenPosX() - colPointPadding, getScreenPosY() + screenObj.getHeight());
		colPoints[7].setLocation(getScreenPosX() - colPointPadding, getScreenPosY());
		
		//check for collision
		for(int i = 0; i < colPoints.length; i ++)
			colPoints[i].setColliding(Game.isPointOnSolid(colPoints[i]));
	}
//	public abstract void spawnAction();
//	public abstract void deathAction();
//	public abstract void behaviorAction();
}
