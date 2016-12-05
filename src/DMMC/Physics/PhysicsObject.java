package DMMC.Physics;

import java.awt.Image;

import DMMC.Game;
import acm.graphics.GImage;
import acm.graphics.GPoint;

public abstract class PhysicsObject {

	protected GImage screenObj;
	protected Image[] curAnimation;
	protected int curAnimeIndex;
	
	public PhysicsObject(Image[] initAnimation) 
	{
		screenObj = new GImage(initAnimation[0]);
		curAnimation = initAnimation;
		curAnimeIndex = 0;
	}
	
	public PhysicsObject(GImage i) 
	{
		screenObj = i;
		curAnimation = null;
		curAnimeIndex = 0;
	}
	

	public double getScreenPosX(){return screenObj.getX();}
	public double getScreenPosY(){return screenObj.getY();}
	public GPoint getScreenPos(){return screenObj.getLocation();}
	public GImage getScreenObj(){return screenObj;}
	
	public void setScreenPosX(double x){screenObj.setLocation(x, getScreenPosY());}
	public void setScreenPosY(double y){screenObj.setLocation(getScreenPosX(), y);}
	public void setScreenPos(GPoint p){screenObj.setLocation(p);}
	
	public void setAnimation(String animeKey)
	{
		Image[] images = Game.getAnime(animeKey);
		
		if(images != null)
		{
			curAnimeIndex = 0;
			curAnimation = images;
		}
		else 
			curAnimeIndex = -1;
	}
	
	//Called every few seconds in Level.java using Timer
	public void iterAnimantion() {
		// index is -1 if no animation is loaded
		if(curAnimeIndex != -1)
		{
			screenObj.setImage(curAnimation[curAnimeIndex++]);
			scaleScreenObj();
		}
		
		//loop back to first image
		if(curAnimeIndex == curAnimation.length)
			curAnimeIndex = 0;
	}
	
	protected void scaleScreenObj()
	{
		screenObj.setSize(this.getScreenObj().getSize().getWidth()*2, this.getScreenObj().getSize().getWidth()*2);
	}
}
