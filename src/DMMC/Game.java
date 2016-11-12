package DMMC;


import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.Timer;

import DMMC.Physics.Entity;
import DMMC.Physics.Tile;
import DMMC.Screen.GuiScreen;
import DMMC.Screen.Screen;
import acm.graphics.GImage;
import acm.program.GraphicsProgram;

public class Game extends GraphicsProgram implements ActionListener{

	
	private static final long serialVersionUID = 1L;
	public static final double GRAVITY = 9.8;
	public static final int windowHeight = 600, windowWidth = 400;
	public static final int tileHeight=50, tileWidth=50;
	public static final int FPS = 60;
	
	private Screen currentScreen; 
	private ArrayList<Profile> profiles;
	private int currentUser; // position of user in list^
	private HashMap<String, Image[]> animations;
	private GameState gameState;
	private Timer timer;
	
	Entity e;
	
	public void init()
	{
		this.resize(windowWidth, windowHeight);
		profiles = new ArrayList<Profile>();
		currentUser = -1;
		animations = new HashMap<String, Image[]>();
		gameState = GameState.Init;
		timer = new Timer((int)(1000/FPS), this);
		
		// drawing tiles on the sample input
/********************************************************************/
		int levelX = windowWidth/tileWidth;
		int levelY = windowHeight/tileHeight;
		
		currentScreen = new GuiScreen(levelX, levelY);
		String arr[][] = new String[levelX][levelY];
		
		//Set tile type array
		for(int i=0;i<levelX;i++)
		{
			for(int j=0;j<levelY;j++)
			{
				arr[i][j]="0";
				if(j==levelY-1)
					arr[i][j]="1";
			}
		}
		currentScreen.initTiles(arr);
		Tile[][] t = currentScreen.getTitleMap();
		for(int i=0;i<levelX;i++){
			for(int j=0;j<levelY;j++){
				GImage image = new GImage(t[i][j].getScreenObj().getImage(),i*tileWidth,j*tileHeight);
				image.setSize(tileWidth, tileHeight);
				add(image);
			}
		}		
/*************************** End of Drawing tiles on sample input ***************/		

		System.out.println(gameState.toString());
		
		
		e = new Entity(new GImage("player.png"));
		add(e.getScreenObj());
		
		timer.start();
	}
	
	public void run()
	{
		System.out.println("RUN");
		
	}
	public void actionPerformed(ActionEvent event)
	{
		e.update();
	}
}
