package DMMC.Physics;

import java.awt.Image;

import com.sun.javafx.sg.prism.web.NGWebView;

import DMMC.Game;
import acm.graphics.GImage;
import acm.graphics.GPoint;
import sun.tools.jar.resources.jar;

public class Entity extends PhysicsObject{

	private static int lastId = 0;
	private static final double colPointPadding = 1;


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

		short indexOrder[] = new short[4];
		for(int i = 0; i < indexOrder.length; i ++)
		{
			indexOrder[i] = -1;
		}

		shortColIndex(indexOrder);
		System.out.println("FRAME:");
		for(short i = 0; i < indexOrder.length; i ++)
		{
			if(colIndex[indexOrder[i]] == 0)
				break;
			if(indexOrder[i] == 0)
			{
				if(colPoints[0].getColliding())
					collisionTilePoint =  Game.tilePosToScreen(colPoints[0].getTileX(), colPoints[0].getTileY());
				else if(colPoints[1].getColliding())
					collisionTilePoint =  Game.tilePosToScreen(colPoints[1].getTileX(), colPoints[1].getTileY()); 
				else 
					break;
				
				screenObj.setLocation(getScreenPosX(), collisionTilePoint.getY() + Game.tileHeight);
				System.out.println("TOP");
				setVelY(0);
			}
			else if(indexOrder[i] == 1)
			{
				if(colPoints[2].getColliding())
					collisionTilePoint =  Game.tilePosToScreen(colPoints[2].getTileX(), colPoints[2].getTileY());
				else if(colPoints[3].getColliding())
					collisionTilePoint =  Game.tilePosToScreen(colPoints[3].getTileX(), colPoints[3].getTileY()); 
				else 
					break;

				screenObj.setLocation(collisionTilePoint.getX() - screenObj.getWidth(), getScreenPosY());
				System.out.println("RIGHT");
				setVelX(0);
			}
			else if(indexOrder[i] == 2)
			{
				if(colPoints[4].getColliding())
					collisionTilePoint =  Game.tilePosToScreen(colPoints[4].getTileX(), colPoints[4].getTileY());
				else if(colPoints[5].getColliding())
					collisionTilePoint =  Game.tilePosToScreen(colPoints[5].getTileX(), colPoints[5].getTileY()); 
				else 
					break;

				screenObj.setLocation(screenObj.getX(), collisionTilePoint.getY() - screenObj.getHeight());
				System.out.println("BOT");
				setVelY(0);
			}
			else if(indexOrder[i] == 3)
			{
				if(colPoints[6].getColliding())
					collisionTilePoint =  Game.tilePosToScreen(colPoints[6].getTileX(), colPoints[6].getTileY());
				else if(colPoints[7].getColliding())
					collisionTilePoint =  Game.tilePosToScreen(colPoints[7].getTileX(), colPoints[7].getTileY()); 
				else 
					break;

				screenObj.setLocation(collisionTilePoint.getX() + Game.tileWidth , getScreenPosY());
				System.out.println("LEFT");
				setVelX(0);
			}
			System.out.println("SET");
			setColPoint();
			shortColIndex(indexOrder);
		}
		screenObj.move(getVelX(), getVelY());


		System.out.println("OUT\n");

	}
	
	private void shortColIndex(short[] indexOrder)
	{
		boolean sorting = true;
		short lookNum = 2;
		short j = 0;
		while(sorting)
		{
			for(short i = 0; i < indexOrder.length; i ++)
			{
				if(colIndex[i] == lookNum)
				{
					indexOrder[j++] = i;
				}
			}
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

		//Top Right
		cornerPoints[0].setLocation(getScreenPosX(), getScreenPosY());

		//Top Left
		cornerPoints[1].setLocation(getScreenPosX() + screenObj.getWidth(), getScreenPosY());

		//Bot Left
		cornerPoints[2].setLocation(getScreenPosX() + screenObj.getWidth(), getScreenPosY() + screenObj.getHeight());

		//Bot Right
		cornerPoints[3].setLocation(getScreenPosX(),  getScreenPosY() + screenObj.getHeight());

		checkForCollision();
	}
	private void checkForCollision()
	{
		//reset col index
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
		
		System.out.println("Cur Col: ");
		for(int i = 0; i < colIndex.length; i ++)
			System.out.print(colIndex[i]);
		System.out.println("");
	}
	//	public abstract void spawnAction();
	//	public abstract void deathAction();
	//	public abstract void behaviorAction();
}
