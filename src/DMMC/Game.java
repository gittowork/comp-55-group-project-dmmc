package DMMC;

/*
 * ArrayList<string> a[] = new ArrayList<String>[]; 
 * 
 * */


import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Stack;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;
import javax.swing.Timer;

import DMMC.Physics.Entity;
import DMMC.Physics.GButton;
import DMMC.Screen.GuiScreen;
import DMMC.Screen.LevelScreen;
import DMMC.Screen.Screen;
import acm.graphics.GImage;
import acm.graphics.GLabel;
import acm.graphics.GPoint;
import acm.program.GraphicsProgram;

public class Game extends GraphicsProgram implements ActionListener{

	public boolean demo = false;
	private Stack<Screen> storeScreen = new Stack<Screen>(); // store screen
	private Stack<GameState> storeGameState = new Stack<GameState>(); // store game state
	private boolean ifEnterPressed=false; //detecting if we press enter so that esc does not store previous screen
	private boolean newGameSelected=false;
	//Static*******************************************************
	private static final long serialVersionUID = 1L;
	public static final double GRAVITY = 9.8;
	public static final int windowHeight = 500, windowWidth = 700;
	public static final int tileHeight=50, tileWidth=50;
	public static final int FPS = 60;
		
	static String[] UserSelectScreenData = {
			"New Run",
			"Leaderboard",  //all button names
			"Options",
			"How To",
			"Credits",
			"GMap1",
			"GMap2",
			"GMap3",
			"LMap1",
			"LMap2",
			"LMap3"
	};
	

	public static final String[][] imageNames = {
			{"player-0","player-1"},
			{"ghost-0","ghost-1"}
	};		//made this a 2d array so that when i load the files into the animation hashmap, its easier to call and organize
	
	public static final int[] animationLengths = {
			2,
			2
	};
	public static Entity player;
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
		//if (currentScreen != null)
			//return currentScreen.getTile(x, y).getScreenPos();
		
		return new GPoint(x * tileWidth, y * tileHeight);
	}

	public static boolean isPointOnSolid(GPoint p)
	{
		//gets tile from point p using ScreePostToTile, gets type, checks isSolid
		return (currentScreen.getTile(screenPosToTile(p.getX(), false), screenPosToTile(p.getY(), true)).getType().isSolid());
	}
	
	public static Screen getCurScreen()
	{
		return currentScreen;
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

		//read the profiles text file intead of hardcoding
		try {
			for (String line : Files.readAllLines(Paths.get("../media/profiles.txt")))
			{
				profiles.add(new Profile(line));
			}
		} catch (IOException e1) {
			System.out.println("oh no");
		}
		
		currentUser = -1;
		animations = new HashMap<String, Image[]>();
		gameState = GameState.Init;
		timer = new Timer((int)(1000/FPS), this);	
		addMouseListeners();
		addKeyListeners();
		timer.start();
		timerIndex = 0;

		Image img;	//variable to temporarily hold the new picture being read in
		Image[] pics;	//array of images to hold all the animation pics for each character
		try {
			for (int i = 0; i < animationLengths.length; i++)
			{
				pics = new Image[animationLengths[i]];
				for (int j = 0; j < animationLengths[i]; j++)
				{
					//read file
					img = ImageIO.read(new File("../media/Images/" + imageNames[i][j] + ".png"));
					pics[j] = img;
					System.out.println(img.toString());	//test output
				}
				animations.put(imageNames[i][0], pics);	//Puts the array into the hashmap
			}			
		} catch (IOException e) {
			System.out.println("oh no");	//catch failure to read file
		}
	}

	public static Image[] getAnime(String k)
	{
		return animations.get(k);
	}


	private void loadNewGame(){
		
		currentScreen = new LevelScreen(0);

		for(int x = 0; x < currentScreen.levelSizeX; x ++)
			for(int y = 0; y < currentScreen.levelSizeY; y ++)
				add(currentScreen.getTitleMap()[x][y].getScreenObj());

		LevelScreen temp = (LevelScreen)currentScreen;
		for(Entity e: temp.getEntities())
			add(e.getScreenObj());
		player = temp.getPlayerEntity();
	}

	private void loadMainMenu(){

		int levelX = windowWidth/tileWidth;
		int levelY = windowHeight/tileHeight;
		GuiScreen tmp = new GuiScreen(levelX, levelY); //peek returns whatever is on top of the stack
		if(!storeGameState.empty() && storeGameState.peek()==GameState.MainMenuScreen){ // checking if top screen is the screen we are loading 
			storeGameState.pop(); 
			GuiScreen tmp1=(GuiScreen)storeScreen.pop();   //if yes, set previous cursor pos
			tmp.setCursorPos(tmp1.getCursorPos());
		}
		currentScreen=tmp;
		gameState=GameState.MainMenuScreen;
		GLabel title = new GLabel("Super Siege Smores", windowWidth/2-50, windowHeight/6 -50);
		add(title);
		
		//in the previous version, there were a lot of added buttons.
		//those buttons have been removed and added using a for loop.
		
		int buttonOffset=0; //loading button from offset index
		int numberOfButton=5; //number of buttons for the screen 
		int posY=0;
		int height = windowHeight/(numberOfButton+2);
		for(int i = 0; i < numberOfButton; i++)
		{
			//did not use math.floor because for int division, 10/3 is going to be 3 anyway
			posY = (i+1)*(windowHeight/(numberOfButton+2)); //finding position
			GButton button = new GButton(UserSelectScreenData[(buttonOffset+i)], windowWidth/2-50, posY, 100, height); 
		
			add(button);
			tmp.addGButton(button);
			
			button.addActionListener(this);
		}
		tmp.getGButton().drawCursor();
	}



	//to malvika :D
	//hardcoded load screen for the buttons on each screen
	private void loadScreen(GameState g)
	{
		clearCurScreen();
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
		else if(g== GameState.MapSelect)
			loadMapScreen();
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
			add(button1);
			button1.addActionListener(this);
			tmp.addGButton(button1);
			tmp.getGButton().drawCursor();
		}
	}

	private void loadMapScreen(){
		int levelX = windowWidth/tileWidth;
		int levelY = windowHeight/tileHeight;
		GuiScreen tmp = new GuiScreen(levelX, levelY);
		if(!storeGameState.empty() && storeGameState.peek()==GameState.MapSelect){
			storeGameState.pop();
			GuiScreen tmp1=(GuiScreen)storeScreen.pop();
			tmp.setCursorPos(tmp1.getCursorPos());
		}
		if(ifEnterPressed){
			storeGameState.push(gameState);
			storeScreen.push(currentScreen);
		}
		currentScreen=tmp;
		gameState=GameState.MapSelect;
		
		int buttonOffset=8;
		if(newGameSelected)
			buttonOffset=5;
		GLabel title = new GLabel("Maps", windowWidth/2-50, windowHeight/6 -50);
		add(title);
		int numberOfButton=3;
		int posY=0;
		int height = windowHeight/(numberOfButton+2);
		for(int i = 0; i < numberOfButton; i++)
		{
			
			posY = (i+1)*(windowHeight/(numberOfButton+2));
			GButton button = new GButton(UserSelectScreenData[(buttonOffset+i)], windowWidth/2-50, posY, 100, height);
		
			add(button);
			tmp.addGButton(button);
			
			button.addActionListener(this);
		}
		tmp.getGButton().drawCursor();
		
		//reason for cursor bug:
		//tmp.getGButton().drawCursor();
	}


	private void loadUserSelect()
	{
		loadBasic(false);
		GImage giraffe = new GImage("../media/Images/warrior marshmallow riding giraffe.png");
		giraffe.scale(0.5);

		add(giraffe);

		addUsers();
		GLabel title = new GLabel("Welcome!", windowWidth/2, 50);
		add(title);


	}

	//helper function for the user select screen
	private void addUsers()
	{
		int levelX = windowWidth/tileWidth; 
		int levelY = windowHeight/tileHeight;
		int posX = 0;

		GuiScreen temp = new GuiScreen(levelX, levelY);
		if(!profiles.isEmpty())
		{
			for(int i = 0; i < profiles.size(); i++)
			{

				posX = (i+1)*(windowWidth/(profiles.size()+3));
				GButton user = new GButton(profiles.get(i).getName(), posX, 150, 100, 100);

				add(user);
				temp.addGButton(user);

				user.addActionListener(this);
			}
		}
		GButton newUser = new GButton("New User", posX+=100, 150, 100, 100);
		add(newUser);
		temp.addGButton(newUser);
		newUser.addActionListener(this);
		temp.getGButton().drawCursor();
		currentScreen = temp;
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
	//dont know if i should get rid of these functions since the labels are needed...

	private void loadOptions()
	{
		if(ifEnterPressed){
			storeGameState.push(gameState);
			storeScreen.push(currentScreen);
		}
		loadBasic(true);
		GLabel label = new GLabel("Game Music Volume: ", 0, 100);
		add(label);
	}

	private void loadCredits() 
	{
		if(ifEnterPressed){
			storeGameState.push(gameState);
			storeScreen.push(currentScreen);
		}
		loadBasic(true);
		GLabel label = new GLabel("Programmers: Malvika Sriram, Pranav Thirunavukkarasu, Maxine Lien, Brendan Ahdoot", 0, 100);
		add (label);
	}

	private void loadHowTo() 
	{
		if(ifEnterPressed){
			storeGameState.push(gameState);
			storeScreen.push(currentScreen);
		}
		loadBasic(true);
		GLabel label1 = new GLabel("How to Play Super S'more Seige:", 0, 100);
		add(label1);
	}

	private void loadLeaderboards()
	{
		if(ifEnterPressed){
			storeGameState.push(gameState);
			storeScreen.push(currentScreen);
		}
		loadBasic(true);
		GLabel label1 = new GLabel("Leaderboards", 0, 100);
		add(label1);
	}

	private void loadLeaderboards(String scores)
	{
		if(ifEnterPressed){
			storeGameState.push(gameState);
			storeScreen.push(currentScreen);
		}
		loadBasic(true);
		GLabel label1 = new GLabel("Leaderboards: "+ scores, 0, 100);
		add(label1);
	}

	public void run()
	{
		System.out.println("RUN");

		//use this to test the different screens. so for example 
		//if you're making the main menu, just change the gamestate to MainMenuScreen and then just load it
		gameState = GameState.UserSelectScreen;
		loadScreen(gameState);


	}

	@Override
	public void actionPerformed(ActionEvent event)
	{	
		if("New User".equals(event.getActionCommand()))
			addNewUser();
		
		// load profiles
		for(Profile p: profiles)
		{
			if(p.getName().equals(event.getActionCommand()))
			{
				loadScreen(GameState.MainMenuScreen);
			}
		}

		if("New Run".equals(event.getActionCommand())){
			newGameSelected=true;
			loadScreen(GameState.MapSelect);
		}
		else if(event.getActionCommand() != null && event.getActionCommand().contains("GMap")){
			loadScreen(GameState.GameScreen);
		}
		else if("Credits".equals(event.getActionCommand()))
			loadScreen(GameState.CreditsScreen);
		else if("Options".equals(event.getActionCommand()))
			loadScreen(GameState.OptionsScreen);
		else if("How To".equals(event.getActionCommand()))
			loadScreen(GameState.HowToScreen);
		else if("Leaderboard".equals(event.getActionCommand())){
			newGameSelected=false;
			loadScreen(GameState.MapSelect);

		}
		else if("Go Back(Esc)".equals(event.getActionCommand()))
			inputEsc();
		if(event.getActionCommand() != null && event.getActionCommand().contains("LMap")){
			loadLeaderboards(event.getActionCommand());
		}		


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

	public void keyPressed(KeyEvent e)
	{
		switch (e.getKeyCode())
		{
		case KeyEvent.VK_ENTER:
			ifEnterPressed=true;
			currentScreen.inputEnter();
			ifEnterPressed=false;
			break;
		case KeyEvent.VK_ESCAPE:
			inputEsc();
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
		switch (e.getKeyCode())
		{
		case KeyEvent.VK_LEFT:
			currentScreen.inputLeftReleased();
			break;
		case KeyEvent.VK_RIGHT:
			currentScreen.inputRightReleased();
			break;
		default:
			break;
		}

	}
	public void inputEsc()
	{
		if(!storeGameState.empty())
			loadScreen(storeGameState.peek());
	}
	
	private void clearCurScreen()
	{
		removeAll();
		if(currentScreen != null)
			currentScreen.clear();
	}
}