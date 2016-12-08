package DMMC.Physics;

import java.awt.Image;

import com.sun.javafx.sg.prism.web.NGWebView;
import com.sun.org.apache.bcel.internal.generic.RETURN;

import DMMC.Game;
import acm.graphics.GImage;
import acm.graphics.GPoint;
import sun.tools.jar.resources.jar;

public abstract class Entity extends PhysicsObject{

	private static final double colPointPadding = 1;
	public static int maxVelX = 3;

	protected GPoint velocity;
	protected GPoint acceleration;
	private int id; // should match position in entity list
	protected boolean weightless;
	protected boolean collidable;
	protected CollisionPoint[] colPoints;
	protected byte[] colIndex;
	protected byte[] indexOrder;
	private GPoint collisionTilePoint;
	private GPoint lastFreePoint; // last location entity was not colliding
	private boolean grounded;
	private boolean forced; // true if object is going to move
	private boolean colliding;
	protected boolean living;

	
	public Entity(String animationKey,int ID, boolean w, boolean c) {
		super(animationKey);
		forced = false;
		collidable = c;
		weightless = w;
		id = ID;
		living = true;
		
		initPoints();
		
		if(collidable)
			setColPoint();
	}
	
	public Entity(String animationKey, int ID) {
		super(animationKey);
		forced = false;
		collidable = true;
		id = ID;
		living = true;
		
		initPoints();
		
		if(collidable)
			setColPoint();
	}

	public double getVelX(){return velocity.getX();}
	public double getVelY(){return velocity.getY();}
	public double getAccX(){return acceleration.getX();}
	public double getAccY(){return acceleration.getY();}
	public int getId(){return id;}
	public boolean isWeightless() {return weightless;}
	public boolean isCollideable() {return collidable;}
	public boolean isGrounded(){return grounded;}
	public boolean isForced(){return forced;}
	public boolean isLiving(){return living;}

	public void setVelX(double x){ velocity.setLocation(x,getVelY());}
	public void setVelY(double y){ velocity.setLocation(getVelX(),y);}
	public void setAccX(double x){ acceleration.setLocation(x,getAccY());}
	public void setAccY(double y){ acceleration.setLocation(getAccX(),y);}
	public void setWeightless(boolean b){weightless = b;}
	public void setCollideable(boolean b){collidable = b;} 
	public void setForced(boolean f){forced = f;}
	public void setLiving(boolean l){living = l;}

	public void update(){

		//set desired velocity before collision
		if(!isWeightless())
			setAccY(Game.GRAVITY / Game.FPS);
		else
			setAccY(0);
		setVelX(getVelX() + getAccX());
		setVelY(getVelY() + getAccY());

		if(collidable)
			repositionAfterCol();

		screenObj.move(getVelX(), getVelY());

		if(!forced && getVelX() != 0)
			setVelX(getVelX() * 0.7);
		
		//check if offscreen
		if(screenObj.getX() < 0)
			setLiving(false);
		if(screenObj.getX() > Game.windowWidth)
			setLiving(false);
		
		if(screenObj.getY() < 0)
			setLiving(false);
		if(screenObj.getY() > Game.windowHeight)
			setLiving(false);
		
		behaviorAction();
	}

	//gets an array of the indexes of colindex in order of its contents
	private void sortColIndex(byte[] indexOrder)
	{
		boolean sorting = true;
		short lookNum = 2;
		short j = 0;

		while(sorting)
		{
			for(byte i = 0; i < indexOrder.length; i ++)
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
		if(collidable)
		{
			colPoints = new CollisionPoint[8];
			for(int i = 0; i < colPoints.length; i ++)
				colPoints[i] = new CollisionPoint(0,0);

			//last free point 
			lastFreePoint = new GPoint(getScreenPosX(), getScreenPosY());

			//index for every side of the object, counts how many collisions per side
			colIndex = new byte[4];

			// stores indexes of colIndex^
			indexOrder = new byte[4];
		}

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
			indexOrder[i] = -1;

		//reset colindex
		for(short i = 0; i < colIndex.length; i ++)
			colIndex[i] = 0;

		//get collisions
		for(short i = 0; i < colPoints.length; i ++)
		{
			colPoints[i].setColliding(Game.isPointOnSolid(colPoints[i]));

			//if collision, add to side index
			if(colPoints[i].getColliding())
			{
				colliding = true;
				colIndex[Math.floorDiv(i, 2)] ++; 
			}
		}

		//fix indexing

		//get last free location
		if(!colliding)
			lastFreePoint.setLocation(getScreenPos());
		else
		{
			//collision happened 

			//if a index 2 is there, there will be no error
			boolean flag = false;
			for(int i = 0; i < colIndex.length; i++)
				if(colIndex[i] == 2)
				{
					flag = true;
					break;
				}

			if(!flag)
			{
				// check last free location

				//case 1: top left collision
				if(colPoints[7].getColliding() 
						&& colPoints[0].getColliding())
				{
					collisionTilePoint = Game.tilePosToScreen(colPoints[7].getTileX(), colPoints[7].getTileY());
					if(screenObj.getWidth() <  Math.abs(collisionTilePoint.getX() + Game.tileWidth - (lastFreePoint.getX() + screenObj.getWidth())))
					{
						colIndex[3]++;
					}
				}

				//case 2: bot left collision
				else if(colPoints[6].getColliding() 
						&& colPoints[5].getColliding())
				{
					collisionTilePoint = Game.tilePosToScreen(colPoints[6].getTileX(), colPoints[6].getTileY());
					if(screenObj.getWidth() <  Math.abs(collisionTilePoint.getX() + Game.tileWidth - (lastFreePoint.getX() + screenObj.getWidth())))
					{
						colIndex[3]++;
					}
				}

				//case 3: bot right collision
				else if(colPoints[3].getColliding() 
						&& colPoints[4].getColliding())
				{
					collisionTilePoint = Game.tilePosToScreen(colPoints[3].getTileX(), colPoints[3].getTileY());

					if(screenObj.getHeight() <  Math.abs(collisionTilePoint.getY() - lastFreePoint.getY()))
					{
						colIndex[2]++;
					}
				}

				//case 4: top right collision
				else if(colPoints[1].getColliding() 
						&& colPoints[2].getColliding())
				{
					collisionTilePoint = Game.tilePosToScreen(colPoints[1].getTileX(), colPoints[1].getTileY());
					if(screenObj.getWidth() <  Math.abs(screenObj.getX() - collisionTilePoint.getX()))
					{
						colIndex[1]++;
					}
				}
			}

		}

		//sort indexes
		sortColIndex(indexOrder);

		//is grounded?
		if(colIndex[2] != 0)
			grounded = true;
		else
			grounded = false;
	}

	private void repositionAfterCol()
	{

		//reset every frame
		colliding = false;

		for(short i = 0; i < indexOrder.length; i ++)
		{
			//reset collision points to see if any more repositioning is needed
			setColPoint();
			checkForCollision();

			//if not collision on side, leave
			if(!colliding)
				break;

			//Top Collision
			if(indexOrder[i] == 0 && getVelY() < 0)
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
			else if(indexOrder[i] == 1 && getVelX() > 0)
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
			else if(indexOrder[i] == 2 && getVelY() > 0)
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
			else if(indexOrder[i] == 3 && getVelX() < 0)
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
