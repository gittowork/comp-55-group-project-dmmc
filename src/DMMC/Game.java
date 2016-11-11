package DMMC;


import java.awt.Image;
import java.util.ArrayList;
import java.util.HashMap;

import DMMC.Screen.Screen;
import DMMC.Physics.*;
import acm.graphics.GImage;
import acm.program.GraphicsProgram;

public class Game extends GraphicsProgram{

	
	private static final long serialVersionUID = 1L;
	public static final double GRAVITY = 9.8;
	public static final int windowHeight = 600, windowWidth = 400;
	
	private Screen currentScreen; 
	private ArrayList<Profile> profiles;
	private int currentUser; // position of user in list^
	private HashMap<String, Image[]> animations;
	private GameState gameState;
	Entity e;
	
	public void init()
	{
		this.resize(windowWidth, windowHeight);
		profiles = new ArrayList<Profile>();
		currentUser = -1;
		animations = new HashMap<String, Image[]>();
		gameState = GameState.Init;
		
		System.out.println(gameState.toString());
		
		e = new Entity(new GImage("TESTIMAGE.jpg"));
		add(e.getScreenObj());
	}
	
	public void run()
	{
		System.out.println("RUN");
		while(true){
			e.update();
		}
		
	}
	
	public static double deltaTime(){
		return 1/60;
	}

}
