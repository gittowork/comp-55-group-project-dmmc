package DMMC;


import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.Timer;
import javax.xml.parsers.DocumentBuilder;

import org.omg.PortableServer.ServantActivator;

import DMMC.Physics.Entity;
import DMMC.Physics.Tile;
import DMMC.Screen.GuiScreen;
import DMMC.Screen.Screen;
import acm.graphics.GImage;
import acm.graphics.GPoint;
import acm.program.GraphicsProgram;

public class Game extends GraphicsProgram implements ActionListener{

	//Static*******************************************************
	private static final long serialVersionUID = 1L;
	public static final double GRAVITY = 1;
	public static final int windowHeight = 600, windowWidth = 400;
	public static final int tileHeight=50, tileWidth=50;
	public static final int FPS = 60;
	
	private static Screen currentScreen;
	
	// False = X; True = Y
	public static int screenPosToTile(double pos, boolean vert)
	{
		if(vert)
		{	
			int y = (int)(pos/tileHeight);
			
			if(y < 0)
				return 0;
			else if(y >= windowHeight/tileHeight)
				return (int) Math.floor(getNumTiles(true) - 1);
			
			return y;
		}
		else
		{
			int x = (int)(pos/tileWidth);
			
			if(x < 0)
				return 0;
			else if(x >= windowWidth/tileHeight)
				return (int) Math.floor(getNumTiles(false) - 1);
			
			return x;
		}
	}
	
	public static GPoint tilePosToScreen(int x, int y)
	{
		return currentScreen.getTile(x, y).getScreenPos();
	}
	
	public static boolean isPointOnSolid(GPoint p)
	{
		//gets tile from point p using ScreePostToTile, gets type, checks isSolid
		return (currentScreen.getTile(screenPosToTile(p.getX(), false), screenPosToTile(p.getY(), true)).getType().isSolid());
	}
	
	//False = X; True = Y
	public static double getNumTiles(boolean vert)
	{
		if(vert)
			return (windowHeight/tileHeight);
		else
			return (windowWidth/tileWidth);
	}
	
	//Non-Static***************************************************
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

		for(int x = 0; x < levelX; x ++)
			for(int y = 0; y < levelY; y ++)
				add(currentScreen.getTitleMap()[x][y].getScreenObj());
/*************************** End of Drawing tiles on sample input ***************/		

		System.out.println(gameState.toString());
		
		
		e = new Entity(new GImage("player.png"));
		e.getScreenObj().setSize(e.getScreenObj().getSize().getWidth() * 2, e.getScreenObj().getSize().getHeight() * 2);
		e.setScreenPosX(e.getScreenPosX() + 10);
		add(e.getScreenObj());
		
		addMouseListeners();
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
	
	@Override
	public void mousePressed(MouseEvent e)
	{
		System.out.println("X: " + screenPosToTile(e.getX(), false));
		System.out.println("Y: " + screenPosToTile(e.getY(), true));
	}
	
}
