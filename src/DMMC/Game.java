package DMMC;


import java.awt.Image;
import java.util.ArrayList;
import java.util.HashMap;

import DMMC.Screen.Screen;
import acm.graphics.GPoint;
import acm.program.GraphicsProgram;

public class Game extends GraphicsProgram{

	
	private static final long serialVersionUID = 1L;
	public static final double GRAVITY = 9.8;
	
	private Screen currentScreen; 
	private ArrayList<Profile> profiles;
	private int currentUser; // position of user in list^
	private HashMap<String, Image[]> animations;
	private GameState gameState;
	
	public void init()
	{
		
		profiles = new ArrayList<Profile>();
		currentUser = -1;
		animations = new HashMap<String, Image[]>();
		gameState = GameState.Init;
		
		System.out.println(gameState.toString());
		
	}
	
	public void run()
	{
		System.out.println("RUN");
		
	}

}
