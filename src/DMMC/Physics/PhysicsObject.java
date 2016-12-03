package DMMC.Physics;

import java.awt.Image;

import acm.graphics.GImage;
import acm.graphics.GPoint;

public abstract class PhysicsObject {

	protected GImage screenObj;
	protected Image[] curAnimation;
	protected int curAnimeIndex;
	
	public PhysicsObject(GImage i, Image[] initAnimation) 
	{
		screenObj = i;
		curAnimation = initAnimation;
		curAnimeIndex = 0;
	}
	
	public PhysicsObject(GImage i)
	{
		screenObj = i;
		curAnimation = null;
		curAnimeIndex = -1;
	}

	public double getScreenPosX(){return screenObj.getX();}
	public double getScreenPosY(){return screenObj.getY();}
	public GPoint getScreenPos(){return screenObj.getLocation();}
	public GImage getScreenObj(){return screenObj;}
	
	public void setScreenPosX(double x){screenObj.setLocation(x, getScreenPosY());}
	public void setScreenPosY(double y){screenObj.setLocation(getScreenPosX(), y);}
	public void setScreenPos(GPoint p){screenObj.setLocation(p);}
	
	public void setAnimation(Image[] anime)
	{
		curAnimeIndex = -1;
		curAnimation = anime;
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
