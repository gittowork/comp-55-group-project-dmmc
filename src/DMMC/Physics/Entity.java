package DMMC.Physics;

import java.awt.Image;

import com.sun.javafx.sg.prism.web.NGWebView;

import DMMC.Game;
import acm.graphics.GImage;
import acm.graphics.GPoint;
import sun.tools.jar.resources.jar;

public abstract class Entity extends PhysicsObject{

	private static int lastId = 0;
	private static final double colPointPadding = 1;


	protected GPoint velocity;
	protected GPoint acceleration;
	private char id;
	protected char curHealth;
	protected char maxHealth;
	protected boolean weightless;
	protected boolean collidable;
	protected CollisionPoint[] colPoints;
	protected char[] colIndex;
	protected char[] indexOrder;
	private GPoint collisionTilePoint;
	private boolean drawable;

	public Entity(GImage i, Image[] initAnimation) {
		super(i, initAnimation);
		initPoints();
		setColPoint();
	}

	public Entity(GImage i) {
		super(i);
		initPoints();
		setColPoint();
	}

	public double getVelX(){return velocity.getX();}
	public double getVelY(){return velocity.getY();}
	public double getAccX(){return acceleration.getX();}
	public double getAccY(){return acceleration.getY();}
	public char getId(){return id;}
	public boolean isWeightless() {return weightless;}
	public boolean isCollideable() {return collidable;}

	public void setVelX(double x){ velocity.setLocation(x,getVelY());}
	public void setVelY(double y){ velocity.setLocation(getVelX(),y);}
	public void setAccX(double x){ acceleration.setLocation(x,getAccY());}
	public void setAccY(double y){ acceleration.setLocation(getAccX(),y);}
	public void setWeightless(boolean b){weightless = b;}
	public void setCollideable(boolean b){collidable = b;}

	public void update(){

		//set desired velocity before collision
		setAccY(Game.GRAVITY /Game.FPS);
		setVelX(getVelX() + getAccX());
		setVelY(getVelY() + getAccY());
		repositionAfterCol();
		
		screenObj.move(getVelX(), getVelY());

	}
	
	//gets an array of the indexes of colindex in order of its contents
	private void sortColIndex(char[] indexOrder)
	{
		boolean sorting = true;
		short lookNum = 2;
		short j = 0;
		
		while(sorting)
		{
			for(char i = 0; i < indexOrder.length; i ++)
				if(colIndex[i] == lookNum)
					indexOrder[j++] = i;
			lookNum--;
			if(j == 4)
				sorting = false;
		}
	}

	private void initPoints(){
		//Vector Points
		velocity = new GPoint(0, 0);
		acceleration = new GPoint(0, 0);

		//Collision Points
		colPoints = new CollisionPoint[8];
		for(int i = 0; i < colPoints.length; i ++)
			colPoints[i] = new CollisionPoint(0,0);
		
		//index for every side of the object, counts how many collisions per side
		colIndex = new char[4];
		
		// stores indexes of colIndex^
		indexOrder = new char[4];

	}

	private void setColPoint()
	{
		//top
		colPoints[0].setLocation(getScreenPosX() + colPointPadding, getScreenPosY() - colPointPadding);
		colPoints[1].setLocation(getScreenPosX() + screenObj.getWidth() - colPointPadding, getScreenPosY() - colPointPadding);

		//left
		colPoints[2].setLocation(getScreenPosX() + screenObj.getWidth() + colPointPadding, getScreenPosY() + colPointPadding);
		colPoints[3].setLocation(getScreenPosX() + screenObj.getWidth() + colPointPadding, getScreenPosY() + screenObj.getHeight() - colPointPadding);

		//bottom
		colPoints[4].setLocation(getScreenPosX() + screenObj.getWidth() - colPointPadding, getScreenPosY() + screenObj.getHeight() + colPointPadding);
		colPoints[5].setLocation(getScreenPosX() + colPointPadding, getScreenPosY() + screenObj.getHeight() + colPointPadding);

		//right
		colPoints[6].setLocation(getScreenPosX() - colPointPadding, getScreenPosY() + screenObj.getHeight() - colPointPadding);
		colPoints[7].setLocation(getScreenPosX() - colPointPadding, getScreenPosY() + colPointPadding);

		//checkForCollision();
	}
	private void checkForCollision()
	{
		//reset indexOrder
		for(char i = 0; i < indexOrder.length; i ++)
			indexOrder[i] = (char) -1;
		
		//reset colindex
		for(short i = 0; i < colIndex.length; i ++)
			colIndex[i] = 0;

		//get collisions
		for(short i = 0; i < colPoints.length; i ++)
		{
			colPoints[i].setColliding(Game.isPointOnSolid(colPoints[i]));

			//if collision, add to side index
			if(colPoints[i].getColliding())
				colIndex[Math.floorDiv(i, 2)] ++; 
		}
		
		//sort indexes
		sortColIndex(indexOrder);
	}
	
	private void repositionAfterCol()
	{
		for(short i = 0; i < indexOrder.length; i ++)
		{
			//reset collision points to see if any more repositioning is needed
			//TODO: Being called every 4 times a frame every time, make set location function, call only when location is set or moved
			setColPoint();
			checkForCollision();
			
			//if not collision on side, leave
			if(colIndex[indexOrder[i]] == 0)
				break;
			
			//Top Collision
			if(indexOrder[i] == 0)
			{
				//Get tile that is colliding
				if(colPoints[0].getColliding())
					collisionTilePoint =  Game.tilePosToScreen(colPoints[0].getTileX(), colPoints[0].getTileY());
				else if(colPoints[1].getColliding())
					collisionTilePoint =  Game.tilePosToScreen(colPoints[1].getTileX(), colPoints[1].getTileY()); 
				else 
					break;
				
				//reset position
				screenObj.setLocation(getScreenPosX(), collisionTilePoint.getY() + Game.tileHeight);
				setVelY(0);
			}
			
			//Right Collision
			else if(indexOrder[i] == 1)
			{
				//Get tile that is colliding
				if(colPoints[2].getColliding())
					collisionTilePoint =  Game.tilePosToScreen(colPoints[2].getTileX(), colPoints[2].getTileY());
				else if(colPoints[3].getColliding())
					collisionTilePoint =  Game.tilePosToScreen(colPoints[3].getTileX(), colPoints[3].getTileY()); 
				else 
					break;

				//reset position
				screenObj.setLocation(collisionTilePoint.getX() - screenObj.getWidth(), getScreenPosY());
				setVelX(0);
			}
			
			//Bot Collision
			else if(indexOrder[i] == 2)
			{
				//Get tile that is colliding
				if(colPoints[4].getColliding())
					collisionTilePoint =  Game.tilePosToScreen(colPoints[4].getTileX(), colPoints[4].getTileY());
				else if(colPoints[5].getColliding())
					collisionTilePoint =  Game.tilePosToScreen(colPoints[5].getTileX(), colPoints[5].getTileY()); 
				else 
					break;

				//reset position
				screenObj.setLocation(screenObj.getX(), collisionTilePoint.getY() - screenObj.getHeight());
				setVelY(0);
			}
			
			//Left Collision
			else if(indexOrder[i] == 3)
			{
				//Get tile that is colliding
				if(colPoints[6].getColliding())
					collisionTilePoint =  Game.tilePosToScreen(colPoints[6].getTileX(), colPoints[6].getTileY());
				else if(colPoints[7].getColliding())
					collisionTilePoint =  Game.tilePosToScreen(colPoints[7].getTileX(), colPoints[7].getTileY()); 
				else 
					break;

				//reset position
				screenObj.setLocation(collisionTilePoint.getX() + Game.tileWidth , getScreenPosY());
				setVelX(0);
			}
		}
	}
		public abstract void spawnAction();
		public abstract void deathAction();
		public abstract void behaviorAction();
}
