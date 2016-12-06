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
	protected int animationSpeed; // 1 - 60, higher the number, slower the animation
	
	public PhysicsObject(String animationKey) 
	{
		screenObj = new GImage(Game.getAnime("default")[0]);
		setAnimation(animationKey);		
		animationSpeed = 3;
	}
	
	public PhysicsObject(GImage i) 
	{
		screenObj = i;
		curAnimation = Game.getAnime("default");
		curAnimeIndex = -1;
		animationSpeed = -1;
	}
	

	public double getScreenPosX(){return screenObj.getX();}
	public double getScreenPosY(){return screenObj.getY();}
	public GPoint getScreenPos(){return screenObj.getLocation();}
	public GImage getScreenObj(){return screenObj;}
	public int getAnimationSpeed(){return animationSpeed;}
	public String getCurAnimationName(){return animationName;}
	
	public void setScreenPosX(double x){screenObj.setLocation(x, getScreenPosY());}
	public void setScreenPosY(double y){screenObj.setLocation(getScreenPosX(), y);}
	public void setScreenPos(GPoint p){screenObj.setLocation(p);}
	public void setAnimationSpeed(int s){animationSpeed = s;}
	
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
		// index is -1 if No Animation is loaded
		if(curAnimeIndex != -1)
		{
			
//			if(this instanceof Player)
//				System.out.println("SET: " + curAnimeIndex + " AT: " + System.currentTimeMillis());
			
			screenObj.setImage(curAnimation[curAnimeIndex++]);
			scaleScreenObj();
			
			
		}
		
		//loop back to first image
		if(curAnimeIndex == curAnimation.length)
		{
//			if(this instanceof Player)
//				System.out.println("RESET: " + curAnimeIndex);
			curAnimeIndex = 0;
		}
	}
	
	protected void scaleScreenObj()
	{
		screenObj.setSize(this.getScreenObj().getSize().getWidth()*2, this.getScreenObj().getSize().getWidth()*2);
	}
}
