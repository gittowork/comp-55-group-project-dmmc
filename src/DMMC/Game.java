package DMMC;


import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.Timer;
import javax.xml.parsers.DocumentBuilder;

import org.omg.PortableServer.ServantActivator;

import DMMC.Physics.Button;
import DMMC.Physics.Entity;
import DMMC.Physics.GButton;
import DMMC.Physics.Tile;
import DMMC.Physics.TileType;
import DMMC.Screen.GuiScreen;
import DMMC.Screen.Screen;
import acm.graphics.GImage;
import acm.graphics.GPoint;
import acm.graphics.GRect;
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
		addMouseListeners();
		addKeyListeners();
		timer.start();
	}
	
	
	private void loadNewGame(){ //when a user clicks new run, loads game.
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
		arr[(int) (getNumTiles(false) - 1)][(int)(getNumTiles(true) - 2)] = "1";
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
		
		
	}
	
	
	private void loadMainMenu(){
		int levelX = windowWidth/tileWidth;
		int levelY = windowHeight/tileHeight;
		GuiScreen tmp = new GuiScreen(levelX, levelY);
		currentScreen=tmp;
		GButton button1 = new GButton("New Run", windowWidth/2-50, 50, 100, 50);
		GButton button2 = new GButton("Leaderboard", windowWidth/2-50, 150, 100, 50);
		GButton button3 = new GButton("Options", windowWidth/2-50, 250, 100, 50);	
		GButton button4 = new GButton("How To", windowWidth/2-50, 350, 100, 50);	
		GButton button5 = new GButton("Credits", windowWidth/2-50, 450, 100, 50);	
		button1.addActionListener(this);
		button2.addActionListener(this);
		button3.addActionListener(this);
		button4.addActionListener(this);
		button5.addActionListener(this);
		add(button1);
		add(button2);
		add(button3);
		add(button4);
		add(button5);
		tmp.addGButton(button1);
		tmp.addGButton(button2);
		tmp.addGButton(button3);
		tmp.addGButton(button4);
		tmp.addGButton(button5);

	}
	
	
	
	//to malvika :D
	//hardcoded load screen for the buttons on each screen
	private void loadScreen(GameState g)
	{
		GButton buton;
		
		//this is the hardcoded part. for now, we're just gunna have an if statement 
		//for every screen and then put buttons in manually like i have
		if (g == GameState.UserSelectScreen)
		{
			//note: instead of using our button class, i decided to use osvaldo's gbutton class. i already added it
			buton = new GButton("New User", 100, 200, 100, 50);	//makes the button with the text you want inside
			add(buton);
		}
		else if(g==GameState.MainMenuScreen){
			loadMainMenu();
		}
		else if(g==GameState.GameScreen)
			loadNewGame();
	}
	
	
	public void run()
	{
		System.out.println("RUN");
		
		//use this to test the different screens. so for example 
		//if you're making the main menu, just change the gamestate to MainMenuScreen and then just load it
		gameState = GameState.MainMenuScreen;
		loadScreen(gameState);
		
		//note: you might want to comment out all of pranav's stuff so its not in the way lol
		
		
	}
	@Override
	public void actionPerformed(ActionEvent event)
	{
		if("New Run".equals(event.getActionCommand()))
			loadScreen(GameState.GameScreen);
		
		if(e != null)
		e.update();
	}
	
	@Override
	public void mousePressed(MouseEvent e)
	{
		
		if(currentScreen instanceof GuiScreen){
			GuiScreen tmp = (GuiScreen) currentScreen;
			if(tmp.getGButton(e.getX(),e.getY()) != null){
				GButton b = tmp.getGButton(e.getX(),e.getY());
				b.fireActionEvent(b.getGLabel().getLabel());
			}
		}
		
		System.out.println("X: " + screenPosToTile(e.getX(), false));
		System.out.println("Y: " + screenPosToTile(e.getY(), true));
	}


	public void keyPressed(KeyEvent e)
	{
		switch (e.getKeyCode())
		{
		case KeyEvent.VK_ENTER:
			inputEnter();
			break;
		case KeyEvent.VK_ESCAPE:
			inputEsc();
			break;
		case KeyEvent.VK_LEFT:
			inputLeft();
			break;
		case KeyEvent.VK_UP:
			inputUp();
			break;
		case KeyEvent.VK_RIGHT:
			inputRight();
			break;
		case KeyEvent.VK_DOWN:
			inputDown();
			break;
		default:
			break;
		}
		
	}
	
	//similar to code in inputEnter() for all other arrow keys
	public void inputUp()
	{
		System.out.println("uppp");
	}
	
	public void inputDown()
	{
		System.out.println("down we goo");
	}
	
	public void inputLeft()
	{
		System.out.println("now left");
	}
	
	public void inputRight()
	{
		System.out.println("now to the right");
	}
	
	
	public void inputZ()
	{
		
	}

	
	public void inputEnter()
	{
		System.out.println("enterrrr");
		if(currentScreen instanceof GuiScreen){
			GuiScreen tmp = (GuiScreen) currentScreen;
			if(tmp.getGButton() != null){
				GButton b = tmp.getGButton();
				b.fireActionEvent(b.getGLabel().getLabel());
			}
		}
	}
	public void inputEsc()
	{
		
	}

	
}
