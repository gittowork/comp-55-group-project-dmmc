package DMMC.Physics;

import java.awt.Image;

import DMMC.Game;
import acm.graphics.GImage;
import acm.graphics.GPoint;

public class Entity extends PhysicsObject{

	private static int lastId = 0;
	private static double colPointPadding = 1;


	protected GPoint velocity;
	protected GPoint acceleration;
	private short id;
	protected short curHealth;
	protected short maxHealth;
	protected boolean weightless;
	protected boolean collidable;
	protected CollisionPoint[] colPoints;
	protected CollisionPoint[] cornerPoints;
	protected short[] colIndex;
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
	public short getId(){return id;}
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
		setVelX(0.5);
		
		while(cornerPoints[0].getColliding()
				|| cornerPoints[1].getColliding()
				|| cornerPoints[2].getColliding()
				|| cornerPoints[3].getColliding())
		{
			short biggestIndex = 0;
			for(short i = 0; i < colIndex.length; i ++)
				if(colIndex[biggestIndex] < colIndex[i])
					biggestIndex = i;
						
			//TODO: Positioning BROKEN!
			switch (biggestIndex) {
			case 0: // Top
				if(colPoints[0].getColliding())
					collisionTilePoint =  Game.tilePosToScreen(colPoints[0].getTileX(), colPoints[0].getTileY());
				else 
					collisionTilePoint =  Game.tilePosToScreen(colPoints[1].getTileX(), colPoints[1].getTileY()); 

				screenObj.setLocation(getScreenPosX(), collisionTilePoint.getY() + Game.tileHeight);
				System.out.println("TOP");
				setVelY(0);
				break;
			case 1: // Right
				if(colPoints[2].getColliding())
					collisionTilePoint =  Game.tilePosToScreen(colPoints[2].getTileX(), colPoints[2].getTileY());
				else 
					collisionTilePoint =  Game.tilePosToScreen(colPoints[3].getTileX(), colPoints[3].getTileY()); 
				
				screenObj.setLocation(collisionTilePoint.getX() - screenObj.getWidth(), getScreenPosY());
				System.out.println("RIGHT");
				setVelX(0);
				break;
			case 2: // Bot
				if(colPoints[4].getColliding())
					collisionTilePoint =  Game.tilePosToScreen(colPoints[4].getTileX(), colPoints[4].getTileY());
				else 
					collisionTilePoint =  Game.tilePosToScreen(colPoints[5].getTileX(), colPoints[5].getTileY()); 
				
				screenObj.setLocation(screenObj.getX(), collisionTilePoint.getY() - screenObj.getHeight());
				System.out.println("BOT");
				setVelY(0);
				break;
			case 3: // Left
				if(colPoints[6].getColliding())
					collisionTilePoint =  Game.tilePosToScreen(colPoints[6].getTileX(), colPoints[6].getTileY());
				else 
					collisionTilePoint =  Game.tilePosToScreen(colPoints[7].getTileX(), colPoints[7].getTileY()); 

				screenObj.setLocation(collisionTilePoint.getX() + Game.tileWidth , getScreenPosY());
				System.out.println("LEFT");
				setVelX(0);
				break;

			default:
				//No collision
				break;
			}
			System.out.println("SET");
			setColPoint();
		}
		screenObj.move(getVelX(), getVelY());
		
		System.out.println("OUT");

	}

	private void initPoints(){
		//Vector Points
		velocity = new GPoint(0, 0);
		acceleration = new GPoint(0, 0);

		//Collision Points
		colPoints = new CollisionPoint[8];
		for(int i = 0; i < colPoints.length; i ++)
			colPoints[i] = new CollisionPoint(0,0);

		//Corner Points
		cornerPoints = new CollisionPoint[4];
		for(int i = 0; i < cornerPoints.length; i ++)
			cornerPoints[i] = new CollisionPoint(0,0);

		//index for every side of the object, counts how many collisions per side
		colIndex = new short[4];

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

		//Top Right
		cornerPoints[0].setLocation(getScreenPosX() + 1, getScreenPosY() + 1);

		//Top Left
		cornerPoints[1].setLocation(getScreenPosX() + screenObj.getWidth() - 1, getScreenPosY() + 1);

		//Bot Left
		cornerPoints[2].setLocation(getScreenPosX() + screenObj.getWidth() - 1, getScreenPosY() + screenObj.getHeight() - 1);

		//Bot Right
		cornerPoints[3].setLocation(getScreenPosX() + 1,  getScreenPosY() + screenObj.getHeight() - 1);

		checkForCollision();
	}
	private void checkForCollision()
	{
		for(short i = 0; i < colIndex.length; i ++)
			colIndex[i] = 0;

		for(short i = 0; i < colPoints.length; i ++)
		{
			colPoints[i].setColliding(Game.isPointOnSolid(colPoints[i]));

			//if collision, add to side index
			if(colPoints[i].getColliding())
				colIndex[Math.floorDiv(i, 2)] ++; 
		}
		for(short i = 0; i < cornerPoints.length; i ++)
			cornerPoints[i].setColliding(Game.isPointOnSolid(cornerPoints[i]));
	}
	//	public abstract void spawnAction();
	//	public abstract void deathAction();
	//	public abstract void behaviorAction();
}
