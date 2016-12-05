package DMMC.Physics;

import javax.annotation.PostConstruct;

import com.sun.tracing.dtrace.ProviderAttributes;

import DMMC.Game;
import acm.graphics.GImage;
import acm.graphics.GPoint;

public class Brussel extends Entity{

	private static final int sizeX = 30;
	private static final int sizeY = 30;
	static boolean lastVel = false; // used to toggle movement

	/*
	 * -1: null
	 * 
	 * 0: top stuck
	 * 1: right stuck
	 * 2: bot stuck
	 * 3: left stuck
	 * 
	 * */
	private byte sideStuck;
	private boolean movement;
	private byte speed = 3;
	private GPoint stuckTilePos;

	// false for left, true for right
	public Brussel() {
		super(Game.getAnime("brussel-down"), false, true);
		scaleScreenObj();

		sideStuck = -1;

		lastVel = !lastVel;
		movement = lastVel;

	}

	@Override
	public void spawnAction() {

	}

	@Override
	public void deathAction() {

	}

	@Override
	public void behaviorAction() {

		boolean debug = false;
		for(int i = 0; i < 4; i ++);
			//System.out.print(colIndex[i] + ", ");
		//System.out.println();
		
		//used to get correct tile for repositioning; depending on movement
		byte switchToggle = (movement) ? (byte) 0 : -1;
		
		//only triggered one falling
		if(sideStuck == -1)
		{
			//find side stuck
			for(byte i = 0; i < colIndex.length; i ++)
			{
				if(colIndex[i] != 0)
				{
					sideStuck = i;
					break;
				}
			}
		}
		else
			setWeightless(true);

		// move in right direction
		switch (sideStuck) {
		case 0:
			//Top  stuck
			setVelX(-speed);
			
			//store stuck tile
			if(colIndex[0] == 1)
				//only one col point is triggered
				stuckTilePos = Game.tilePosToScreen(colPoints[1 + switchToggle].getTileX(),
						colPoints[1 + switchToggle].getTileY());

			if(movement)
			{
				//check for wall collision
				if(colIndex[3] != 0)
				{
					sideStuck = 3;
					setVelX(0);
				}
				
				//left
				if(colIndex[0] == 0)
				{
					//change sides
					sideStuck = 1;
					setVelX(0);

					//set position
					setScreenPosX(stuckTilePos.getX() - screenObj.getWidth());
					setScreenPosY(stuckTilePos.getY() + Game.tileHeight - 2);
				}
			}
			else
			{
				//right
				setVelX(getVelX() * -1);
				
				//check for wall collision
				if(colIndex[1] != 0)
				{
					sideStuck = 1;
					setVelX(0);
				}

				if(colIndex[0] == 0)
				{
					//change sides
					sideStuck = 3;
					setVelX(0);
					
					//set position
					setScreenPosX(stuckTilePos.getX() + Game.tileWidth);
					setScreenPosY(stuckTilePos.getY() + Game.tileHeight - 2);
				}
			}

			if(debug)
				System.out.println("TOP");
			break;
		case 1:
			//Right  stuck
			setVelY(-speed);
					
			if(colIndex[1] == 1)
				//only one col point is triggered
				stuckTilePos = Game.tilePosToScreen(colPoints[3 + switchToggle].getTileX(),
						colPoints[3 + switchToggle].getTileY());
			
			if(movement)
			{
				//check for wall collision
				if(colIndex[0] != 0)
				{
					sideStuck = 0;
					setVelY(0);
				}
				
				//up
				if(colIndex[1] == 0)
				{
					//change sides
					sideStuck = 2;
					setVelY(0);
					
					//set position
					setScreenPosX(stuckTilePos.getX() - screenObj.getWidth() + 2);
					setScreenPosY(stuckTilePos.getY() - screenObj.getHeight());
				}
			}
			else
			{
				//down
				setVelY(getVelY() * -1);
				
				//check for wall collision
				if(colIndex[2] != 0)
				{
					sideStuck = 2;
					setVelY(0);
				}

				if(colIndex[1] == 0)
				{
					//change sides
					sideStuck = 0;
					setVelY(0);
					
					//set position
					setScreenPosX(stuckTilePos.getX() - screenObj.getWidth() + 2);
					setScreenPosY(stuckTilePos.getY() + Game.tileHeight);
				}
			}


			if(debug)
				System.out.println("RGH");
			break;
		case 2:
			//Bot stuck
			setVelX(speed);

			//store stuck tile				
			if(colIndex[2] == 1)
				//only one col point is triggered
				stuckTilePos = Game.tilePosToScreen(colPoints[5 + switchToggle].getTileX(),
						colPoints[5 + switchToggle].getTileY());
			
			if(movement)
			{
				//check for wall collision
				if(colIndex[1] != 0)
				{
					sideStuck = 1;
					setVelX(0);
				}
				
				//right
				if(colIndex[2] == 0)
				{
					//change sides
					sideStuck = 3;
					setVelX(0);

					//set position
					setScreenPosX(stuckTilePos.getX() + Game.tileWidth);
					setScreenPosY(stuckTilePos.getY() - screenObj.getHeight() + 2);
				}
			}
			else
			{
				//left
				setVelX(getVelX() * -1);
				
				//check for wall collision
				if(colIndex[3] != 0)
				{
					sideStuck = 3;
					setVelX(0);
				}

				if(colIndex[2] == 0)
				{
					//change sides
					sideStuck = 1;
					setVelX(0);
					
					
					//set position
					setScreenPosX(stuckTilePos.getX() - screenObj.getWidth());
					setScreenPosY(stuckTilePos.getY() - screenObj.getHeight() + 2);
				}
			}

			if(debug)
				System.out.println("BOT");
			break;
		case 3:
			//Left  stuck
			setVelY(speed);
			
			if(colIndex[3] == 1)
				//only one col point is triggered
				stuckTilePos = Game.tilePosToScreen(colPoints[7 + switchToggle].getTileX(),
						colPoints[7 + switchToggle].getTileY());
						
			if(movement)
			{
				//check for wall collision
				if(colIndex[2] != 0)
				{
					sideStuck = 2;
					setVelY(0);
				}
				
				//down
				if(colIndex[3] == 0)
				{
					//change sides
					sideStuck = 0;
					setVelY(0);
					
					//set position
					setScreenPosX(stuckTilePos.getX() + Game.tileWidth - 2);
					setScreenPosY(stuckTilePos.getY() + Game.tileHeight);
				}
			}
			else
			{
				//up
				setVelY(getVelY() * -1);
				
				//check for wall collision
				if(colIndex[0] != 0)
				{
					sideStuck = 0;
					setVelY(0);
				}

				if(colIndex[3] == 0)
				{
					//change sides
					sideStuck = 2;
					setVelY(0);
					
					//set position
					setScreenPosX(stuckTilePos.getX() + Game.tileWidth - 2);
					setScreenPosY(stuckTilePos.getY() - screenObj.getHeight());
				}
			}

			if(debug)
				System.out.println("LFT");
			break;

		default:
			break;
		}

	}

	@Override
	protected void scaleScreenObj()
	{
		screenObj.setSize(sizeX, sizeY);
	}

}
