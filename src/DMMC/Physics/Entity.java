package DMMC.Physics;

import java.awt.Image;

import DMMC.Game;
import acm.graphics.GImage;
import acm.graphics.GPoint;

public class Entity extends PhysicsObject{
	
	private static int lastId = 0;
	private static double colPointPadding = 0;

	
	protected GPoint velocity;
	protected GPoint acceleration;
	private int id;
	protected int curHealth;
	protected int maxHealth;
	protected boolean weightless;
	protected boolean collidable;
	protected CollisionPoint[] colPoints;
	private GPoint collisionTilePoint;

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
		setVelX(0.1);
		
		if(colPoints[0].getColliding())
		{
			collisionTilePoint = Game.tilePosToScreen(colPoints[0].getTileX(), colPoints[0].getTileY());
			if(getVelY() < 0)
			{
				screenObj.setLocation(getScreenPosX(), collisionTilePoint.getY() + Game.tileHeight);
				setVelY(0);
			}
			if(getVelX() < 0)
			{
				screenObj.setLocation(collisionTilePoint.getX() + Game.tileWidth, getScreenPosY());
				setVelX(0);
			}
			
			checkForCollision();
		}
		if(colPoints[1].getColliding())
		{
			collisionTilePoint = Game.tilePosToScreen(colPoints[1].getTileX(), colPoints[1].getTileY());
			if(getVelY() < 0)
			{
				screenObj.setLocation(getScreenPosX(), collisionTilePoint.getY() + Game.tileHeight);
				setVelY(0);
			}
			if(getVelX() > 0)
			{
				screenObj.setLocation(collisionTilePoint.getX() - screenObj.getWidth(), getScreenPosY());
				setVelX(0);
			}
			
			checkForCollision();
		}
		if(colPoints[2].getColliding())
		{
			collisionTilePoint = Game.tilePosToScreen(colPoints[2].getTileX(), colPoints[2].getTileY());
			System.out.println(collisionTilePoint.getX() + " : " + collisionTilePoint.getY());
			if(getVelY() > 0)
			{
				screenObj.setLocation(screenObj.getX(), collisionTilePoint.getY() - screenObj.getHeight());
				setVelY(0);
			}
			if(getVelX() > 0)
			{
				screenObj.setLocation(collisionTilePoint.getX() - screenObj.getWidth(), getScreenPosY());
				setVelX(0);
			}
			
			checkForCollision();

		}
		if(colPoints[3].getColliding())
		{
			collisionTilePoint = Game.tilePosToScreen(colPoints[3].getTileX(), colPoints[3].getTileY());
			if(getVelY() > 0)
			{
				screenObj.setLocation(screenObj.getX(), collisionTilePoint.getY() - screenObj.getHeight());
				setVelY(0);
			}
			if(getVelX() < 0)
			{
				screenObj.setLocation(collisionTilePoint.getX() + Game.tileWidth , getScreenPosY());
				setVelX(0);
			}
				
			checkForCollision();
		}
		screenObj.move(getVelX(), getVelY());

	}
	
	private void initPoints(){
		//Vector Points
		velocity = new GPoint(0, 0);
		acceleration = new GPoint(0, 0);
		
		//Collision Points
		colPoints = new CollisionPoint[4];
		for(int i = 0; i < colPoints.length; i ++)
			colPoints[i] = new CollisionPoint(0,0);
			
	}
	
	private void setColPoint()
	{
//		//top
//		colPoints[0].setLocation(getScreenPosX(), getScreenPosY() - colPointPadding);
//		colPoints[1].setLocation(getScreenPosX() + screenObj.getWidth(), getScreenPosY() - colPointPadding);
//				
//		//left
//		colPoints[2].setLocation(getScreenPosX() + screenObj.getWidth() + colPointPadding, getScreenPosY());
//		colPoints[3].setLocation(getScreenPosX() + screenObj.getWidth() + colPointPadding, getScreenPosY() + screenObj.getHeight());
//				
//		//bottom
//		colPoints[4].setLocation(getScreenPosX() + screenObj.getWidth(), getScreenPosY() + screenObj.getHeight() + colPointPadding);
//		colPoints[5].setLocation(getScreenPosX(), getScreenPosY() + screenObj.getHeight() + colPointPadding);
//				
//		//right
//		colPoints[6].setLocation(getScreenPosX() - colPointPadding, getScreenPosY() + screenObj.getHeight());
//		colPoints[7].setLocation(getScreenPosX() - colPointPadding, getScreenPosY());
		
		colPoints[0].setLocation(getScreenPos());
		colPoints[1].setLocation(getScreenPosX() + screenObj.getWidth(), getScreenPosY());
		colPoints[2].setLocation(getScreenPosX() + screenObj.getWidth(), getScreenPosY() + screenObj.getHeight());
		colPoints[3].setLocation(getScreenPosX(), getScreenPosY() + screenObj.getHeight());
		
		checkForCollision();
	}
	private void checkForCollision()
	{
		for(int i = 0; i < colPoints.length; i ++)
			colPoints[i].setColliding(Game.isPointOnSolid(colPoints[i]));
	}
//	public abstract void spawnAction();
//	public abstract void deathAction();
//	public abstract void behaviorAction();
}
