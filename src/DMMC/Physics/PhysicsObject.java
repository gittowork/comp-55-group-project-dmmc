package DMMC.Physics;

import java.awt.Image;

import DMMC.Game;
import acm.graphics.GImage;
import acm.graphics.GPoint;

public abstract class PhysicsObject {

	protected GImage screenObj;
	protected Image[] curAnimation;
	protected int curAnimeIndex;
	protected String animationName;
	
	public PhysicsObject(String animationKey) 
	{
		screenObj = new GImage(Game.getAnime("default")[0]);
		setAnimation(animationKey);		
	}
	
	public PhysicsObject(GImage i) 
	{
		screenObj = i;
		curAnimation = Game.getAnime("default");
		curAnimeIndex = -1;
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
		
		curAnimeIndex = 0;
		curAnimation = images;
		animationName = animeKey;
		iterAnimantion();
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
