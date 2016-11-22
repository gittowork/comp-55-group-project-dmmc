package DMMC;


import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;
import javax.swing.Timer;
import javax.xml.parsers.DocumentBuilder;

import org.omg.PortableServer.ServantActivator;

import DMMC.Physics.Button;
import DMMC.Physics.Entity;
import DMMC.Physics.GButton;
import DMMC.Physics.Player;
import DMMC.Physics.Tile;
import DMMC.Physics.TileType;
import DMMC.Screen.GuiScreen;
import DMMC.Screen.LevelScreen;
import DMMC.Screen.Screen;
import acm.graphics.GImage;
import acm.graphics.GLabel;
import acm.graphics.GPoint;
import acm.graphics.GRect;
import acm.program.GraphicsProgram;

public class Game extends GraphicsProgram implements ActionListener{

	//Static*******************************************************
	private static final long serialVersionUID = 1L;
	public static final double GRAVITY = 5;
	public static final int windowHeight = 500, windowWidth = 700;
	public static final int tileHeight=50, tileWidth=50;
	public static final int FPS = 60;
	public static final String[] imageNames = {	
			"player-0",
			"player-1"
	};
	public static final int[] animationLengths = {
			2
	};

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
	private static HashMap<String, Image[]> animations;
	private GameState gameState;
	private Timer timer;
	Entity e;
	private int timerIndex;


	public void init()
	{
		this.resize(windowWidth, windowHeight);
		profiles = new ArrayList<Profile>();

		//add us to existing profiles
		Profile maxine = new Profile("Maxine");
		Profile pranav = new Profile("Peanut");
		Profile malvika = new Profile("Malvika");
		Profile brendan = new Profile("Brendan");
		profiles.add(maxine);
		profiles.add(pranav);
		profiles.add(malvika);
		profiles.add(brendan);

		currentUser = -1;
		animations = new HashMap<String, Image[]>();
		gameState = GameState.Init;
		timer = new Timer((int)(1000/FPS), this);	
		addMouseListeners();
		addKeyListeners();
		timer.start();
		timerIndex = 0;

		Image img;
		Image[] pics;
		try {
			for (int i = 0; i < animationLengths.length; i++)
			{
				pics = new Image[animationLengths[i]];
				for (int j = 0; j < animationLengths[i]; j++)
				{
					img = ImageIO.read(new File("../media/Images/" + imageNames[j] + ".png"));
					pics[j] = img;
					System.out.println(img.toString());
				}
				animations.put(imageNames[i], pics);	//the key isnt right and wont work for all
			}			
		} catch (IOException e) {
		}
	}

	public static Image[] getAnime(String k)
	{
		return animations.get(k);
	}


	private void loadNewGame(){ //when a user clicks new run, loads game.
		// drawing tiles on the sample input
		/********************************************************************/

		int levelX = windowWidth/tileWidth;
		int levelY = windowHeight/tileHeight;


		currentScreen = new LevelScreen(levelX, levelY);
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
		LevelScreen temp = (LevelScreen)currentScreen;
		for(Entity e: temp.getEntities())
			add(e.getScreenObj());

	}


	private void loadMainMenu(){
		removeAll();
		int levelX = windowWidth/tileWidth;
		int levelY = windowHeight/tileHeight;
		GuiScreen tmp = new GuiScreen(levelX, levelY);
		currentScreen=tmp;
		GLabel title = new GLabel("Super Siege Smores", windowWidth/2-50, windowHeight/6 -50);
		GButton button1 = new GButton("New Run", windowWidth/2-50, 2*(windowHeight/6)-50, 100, 50);
		GButton button2 = new GButton("Leaderboard", windowWidth/2-50, 3*(windowHeight/6)-50, 100, 50);
		GButton button3 = new GButton("Options", windowWidth/2-50, 4*(windowHeight/6)-50, 100, 50);	
		GButton button4 = new GButton("How To", windowWidth/2-50, 5*(windowHeight/6)-50, 100, 50);	
		GButton button5 = new GButton("Credits", windowWidth/2-50, windowHeight-50, 100, 50);	
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
		add(title);
		tmp.addGButton(button1);
		tmp.addGButton(button2);
		tmp.addGButton(button3);
		tmp.addGButton(button4);
		tmp.addGButton(button5);
		button1.drawCursor();
	}



	//to malvika :D
	//hardcoded load screen for the buttons on each screen
	private void loadScreen(GameState g)
	{

		//this is the hardcoded part. for now, we're just gunna have an if statement 
		//for every screen and then put buttons in manually like i have
		if (g == GameState.UserSelectScreen)
		{
			loadUserSelect();
		}
		else if(g==GameState.MainMenuScreen)
			loadMainMenu();
		else if(g==GameState.GameScreen)
			loadNewGame();
		else if(g==GameState.CreditsScreen)
			loadCredits();
		else if(g==GameState.HowToScreen)
			loadHowTo();
		else if(g==GameState.Leaderboards)
			loadLeaderboards();
		else if(g == GameState.OptionsScreen)
			loadOptions();
	}

	//made this class because load credits, options, and leaderboards have the same code
	private void loadBasic(Boolean back)
	{
		removeAll();
		int levelX = windowWidth/tileWidth;
		int levelY = windowHeight/tileHeight;
		GuiScreen tmp = new GuiScreen(levelX, levelY);
		currentScreen=tmp;
		GButton button1;
		if (back)
		{
			button1 = new GButton("Go Back(Esc)", 0, 0, 100, 50);
		}

		else
		{
			button1 = new GButton("New User", windowWidth/2 + 50, 300, 100, 100);
		}

		add(button1);
		button1.addActionListener(this);
		button1.drawCursor();
		tmp.addGButton(button1);

	}

	private void loadUserSelect()
	{
		loadBasic(false);
		addExistingUsers();
		GLabel title = new GLabel("Welcome!", windowWidth/2, 50);
		add(title);


	}

	//helper function for the user select screen
	private void addExistingUsers()
	{
		int levelX = windowWidth/tileWidth; 
		int levelY = windowHeight/tileHeight;
		GuiScreen temp = new GuiScreen(levelX, levelY);
		if(!profiles.isEmpty())
		{
			for(int i = 0; i < profiles.size(); i++)
			{
				int posX = i*100 + 150;
				GButton user = new GButton(profiles.get(i).getName(), posX, 150, 100, 100);
				add(user);
				temp.addGButton(user);
			}
		}
	}

	private void addNewUser()
	{
		//if user presses cancel, null pointer exception
		String name = new String(JOptionPane.showInputDialog("Type in Name: "));
		if (name != null)
		{
			loadScreen(GameState.MainMenuScreen);
			Profile newUser = new Profile(name);
			profiles.add(newUser);
		}

	}

	private void loadOptions()
	{
		loadBasic(true);
		GLabel label = new GLabel("Game Music Volume: ", 0, 100);
		add(label);
	}

	private void loadCredits() 
	{
		loadBasic(true);
		GLabel label = new GLabel("Programmers: Malvika Sriram, Pranav Thirunavukkarasu, Maxine Lien, Brendan Ahdoot", 0, 100);
		add (label);
	}

	private void loadHowTo() 
	{
		loadBasic(true);
		GLabel label1 = new GLabel("How to Play Super S'more Seige:", 0, 100);
		add(label1);
	}

	private void loadLeaderboards()
	{
		loadBasic(true);
		GLabel label1 = new GLabel("Leaderboards", 0, 100);
		add(label1);
	}

	public void run()
	{
		System.out.println("RUN");

		//use this to test the different screens. so for example 
		//if you're making the main menu, just change the gamestate to MainMenuScreen and then just load it
		gameState = GameState.UserSelectScreen;
		loadScreen(gameState);

		//note: you might want to comment out all of pranav's stuff so its not in the way lol


	}

	//dont need for keyboard
	@Override
	public void actionPerformed(ActionEvent event)
	{	
		if("New User".equals(event.getActionCommand()))
			addNewUser();
		if("New Run".equals(event.getActionCommand()))
			loadScreen(GameState.GameScreen);
		if("Credits".equals(event.getActionCommand()))
			loadScreen(GameState.CreditsScreen);
		if("Options".equals(event.getActionCommand()))
			loadScreen(GameState.OptionsScreen);
		if("How To".equals(event.getActionCommand()))
			loadScreen(GameState.HowToScreen);
		if("Leaderboard".equals(event.getActionCommand()))
			loadScreen(GameState.Leaderboards);
		if("Go Back(Esc)".equals(event.getActionCommand()))
			loadScreen(GameState.MainMenuScreen);
		
		if(currentScreen instanceof LevelScreen)
		{
			timerIndex++;
			LevelScreen temp = (LevelScreen)currentScreen;
			for(Entity e: temp.getEntities())
			{
				e.update();
				if (timerIndex == 60)
				{
					e.iterAnimantion();	//the animation shouldn't be in timer.
					timerIndex = 0;
				}
			}
		}

	}


	//the following function is for mouse use. code was used to test the screen. Use if needed. 
	/*
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
	 */

	public void keyPressed(KeyEvent e)
	{
		switch (e.getKeyCode())
		{
		case KeyEvent.VK_ENTER:
			currentScreen.inputEnter();
			break;
		case KeyEvent.VK_ESCAPE:
			currentScreen.inputEsc();
			break;
		case KeyEvent.VK_LEFT:
			currentScreen.inputLeft();
			break;
		case KeyEvent.VK_UP:
			currentScreen.inputUp();
			break;
		case KeyEvent.VK_RIGHT:
			currentScreen.inputRight();
			break;
		case KeyEvent.VK_DOWN:
			currentScreen.inputDown();
			break;
		default:
			break;
		}

	}
	public void keyReleased(KeyEvent e)
	{
		currentScreen.inputReleased();
	}
}
