package DMMC.Physics;

import java.awt.Image;

import acm.graphics.GImage;
import acm.graphics.GLabel;
import acm.graphics.GLine;
import acm.graphics.GRect;

public class Button extends Tile {

	public Button(GImage i, Image[] initAnimation, TileType t) 
	{
		super(i, initAnimation, t);
		// TODO Auto-generated constructor stub
		GRect button = new GRect(200,50);
	}
	
	public Button(GImage i, TileType t, String text)
	{
		super(i, t);
	
	}
	
	public void buttonAction()
	{
		//called when player selects button, redirect to another screen
		
	}

	
	private void drawCursor()
	{
		GLine cursor = new GLine(0,0,100,100);
		
	}
	
	public static void main(String[] args) 
	{
		// TODO Auto-generated method stub

	}

}
