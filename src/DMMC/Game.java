package DMMC;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Queue;
import java.util.Stack;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;
import javax.swing.Timer;

import DMMC.Physics.Brussel;
import DMMC.Physics.Entity;
import DMMC.Physics.GButton;
import DMMC.Physics.Ghost;
import DMMC.Physics.Player;
import DMMC.Screen.GuiScreen;
import DMMC.Screen.LevelScreen;
import DMMC.Screen.Screen;
import acm.graphics.GImage;
import acm.graphics.GLabel;
import acm.graphics.GPoint;
import acm.program.GraphicsProgram;
import javafx.scene.media.MediaPlayer;

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

	private int mapIndex=0;
	private boolean gameSoundOff=false;//to check if sound is on or off in if statem.
	private boolean gamePaused = false; //to check if game is paused or not

	GLabel waveLabel;

	static String[] UserSelectScreenData = {
			"New Run",
			"Finishers",  //all button names
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
			{"player-idle-right", "player-1"},
			{"player-idle-left", "player-4"},
			{"player-run-right", "player-0", "player-1", "player-2"},
			{"player-run-left", "player-3", "player-4", "player-5"},
			{"player-attack-right", "player-8"},
			{"player-attack-left", "player-7"},
			{"player-dead", "player-6"},

			{"ghost-idle","ghost-0","ghost-1"},

			{"brussel-top","brussel-0"},
			{"brussel-right","brussel-1"},
			{"brussel-bot","brussel-2"},
			{"brussel-left","brussel-3"},

			{"corn-idle-right", "corn-0"},
			{"corn-idle-left", "corn-3"},
			{"corn-attack-right", "corn-1"},
			{"corn-attack-left", "corn-4"},
			{"corn-dead-right", "corn-2"},
			{"corn-dead-left", "corn-5"},

			{"kernel-run", "kernel-0"},

			{"sword-left", "sword-0"},
			{"sword-right", "sword-1"},

			{"heart", "heart"},

			{"default", "empty"}
	};		//made this a 2d array so that when i load the files into the animation hashmap, its easier to call and organize

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
	GImage giraffe;
	GImage girafe;
	GImage howto;
	GImage credits;
	GImage leaderboards;

	public void init()
	{
		this.resize(windowWidth, windowHeight);
		profiles = new ArrayList<Profile>();

		//read in all the pictures
		giraffe =  new GImage("../media/Images/warrior.png");
		girafe = new GImage("../media/Images/girafe.png");
		howto = new GImage("../media/Images/howto.png");
		credits = new GImage("../media/Images/credits.png");
		leaderboards = new GImage("../media/Images/leaderboards.png");


		//read the profiles text file
		try {
			for (String line : Files.readAllLines(Paths.get("../bin/profiles.txt")))
			{
				profiles.add(new Profile(line));
			}
		} catch (IOException e1) {
			System.err.println("Profiles file not found");
		}

		currentUser = -1;
		animations = new HashMap<String, Image[]>();
		gameState = GameState.Init;
		timer = new Timer((int)(1000/FPS), this);	
		addKeyListeners();
		timer.start();

		Image[] pics;	//array of images to hold all the animation pics for each character

		for (int i = 0; i < imageNames.length; i++)
		{
			pics = new Image[imageNames[i].length - 1];
			for (int j = 0; j < imageNames[i].length - 1; j++)
			{
				//read file
				try 
				{
					pics[j] = ImageIO.read(new File("../media/Images/" + imageNames[i][j + 1] + ".png"));;
				}
				catch (IOException e) {
					System.err.println("Could not find image: " + imageNames[i][j + 1]);
				}
			}
			animations.put(imageNames[i][0], pics);	//Puts the array into the hashmap
		}			
	}

	public static Image[] getAnime(String k)
	{
		if(animations.containsKey(k))
			return animations.get(k);
		else
		{
			System.err.println("No Animation: " + k);
			return animations.get("default");
		}
	}


	private void loadNewGame()
	{		
		if(ifEnterPressed){

			storeGameState.push(gameState);
			storeScreen.push(currentScreen);
		}

		currentScreen = new LevelScreen(mapIndex);

		for(int x = 0; x < currentScreen.levelSizeX; x ++)
			for(int y = 0; y < currentScreen.levelSizeY; y ++)
				add(currentScreen.getTitleMap()[x][y].getScreenObj());

		LevelScreen temp = (LevelScreen)currentScreen;
		for(Entity e: temp.getEntities())
			add(e.getScreenObj());
		player = temp.getPlayerEntity();
		//GButton button = new GButton("Pause", 50 , 50, 50, 50);
		//add(button);
		//button.drawCursor();
		//button.addActionListener(this);
		//((LevelScreen)currentScreen).setPauseButton(button);
	}

	private void showPauseDialog(){
		gamePaused=true;
		String[] buttons = { "Exit", "Continue"};    //exit and continue for pop ups 
		int returnValue = JOptionPane.showOptionDialog(null, "Press exit to go to main menu, and continue to keep playing the game!", "Options",
				JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, buttons, buttons[0]);
		if(returnValue==0){
			//storeGameState=new Stack<GameState>();
			//storeScreen=new Stack<Screen>();
			removeAll();
			loadMainMenu();
			playMainSound(); //to get back to main menu song, and not have the game song keep playing after exiting.
		}
		gamePaused=false;
	}

	//when user presses tutorial, it still continues to new game, but it should go to how to
	private int showAnnoyingPop()
	{
		String[] buttons = {"Continue (we warned you)", "Tutorial"};
		int returnValue = JOptionPane.showOptionDialog(null, "You should probably read the how-to first. Press tutorial to read it. Go.", "STOP", 
				JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, buttons, buttons[0]);
		if(returnValue == 1)
		{
			//doesnt work???
			//storeGameState = new Stack<GameState>();
			//storeScreen = new Stack<Screen>();
			removeAll();
			loadHowTo();
			playMainSound();
			profiles.get(currentUser).setHelpPageStatus(true);
		}
		return returnValue;


	}

	private void loadMainMenu(){

		int levelX = windowWidth/tileWidth;
		int levelY = windowHeight/tileHeight;
		GuiScreen tmp = new GuiScreen(levelX, levelY, girafe); //peek returns whatever is on top of the stack
		if(!storeGameState.empty() && storeGameState.peek()==GameState.MainMenuScreen){ // checking if top screen is the screen we are loading 
			storeGameState.pop(); 
			GuiScreen tmp1=(GuiScreen)storeScreen.pop();   //if yes, set previous cursor pos
			tmp.setCursorPos(tmp1.getCursorPos());
		}

		add(tmp.getTile(0,0).getScreenObj());
		currentScreen=tmp;
		gameState=GameState.MainMenuScreen;
		GLabel title = new GLabel("Super Siege Smores", windowWidth/2-100, windowHeight/6 -50);

		title.setFont(new Font("SketchFlow Print Regular", Font.BOLD, 36));

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
			GButton button = new GButton(UserSelectScreenData[(buttonOffset+i)], windowWidth/2-80, posY, 170, height); 

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
		else if(g == GameState.OptionsScreen)
			loadOptions();
		else if(g== GameState.MapSelect)
			loadMapScreen();
	}

	//made this class because load credits, options, and leaderboards have the same code
	private void loadBasic(Boolean back, GImage bg)
	{
		removeAll();

		int levelX = windowWidth/tileWidth;
		int levelY = windowHeight/tileHeight;
		GuiScreen tmp = new GuiScreen(levelX, levelY, bg);
		currentScreen=tmp;
		GButton button1;

		add(currentScreen.getTile(0, 0).getScreenObj());


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
		GuiScreen tmp = new GuiScreen(levelX, levelY, girafe);
		if(!storeGameState.empty() && storeGameState.peek()==GameState.MapSelect){
			storeGameState.pop();
			GuiScreen tmp1=(GuiScreen)storeScreen.pop();
			tmp.setCursorPos(tmp1.getCursorPos());
		}

		add(tmp.getTile(0, 0).getScreenObj());
		if(ifEnterPressed){
			storeGameState.push(gameState);
			storeScreen.push(currentScreen);
		}
		currentScreen=tmp;
		gameState=GameState.MapSelect;

		GButton button1 = new GButton("Go Back(Esc)", 0, 0, 100, 50);
		add(button1);
		button1.addActionListener(this);
		tmp.addGButton(button1);

		int buttonOffset=8;
		if(newGameSelected)
			buttonOffset=5;
		GLabel title = new GLabel("Maps", windowWidth/2-30, windowHeight/6 -50);
		title.setFont(new Font("Calibri", Font.BOLD, 24));
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
		loadBasic(false, giraffe);
		addUsers();
		GLabel title = new GLabel("Welcome!", windowWidth/2-70, 50);
		title.setFont(new Font("Calibri", Font.BOLD, 24));
		add(title);

	}

	//helper function for the user select screen
	private void addUsers()
	{
		int levelX = windowWidth/tileWidth; 
		int levelY = windowHeight/tileHeight;
		int posX = windowWidth/2 - 200;

		GuiScreen temp = new GuiScreen(levelX, levelY);
		if(!profiles.isEmpty())
		{
			GButton user;
			for(int i = 0; i < profiles.size(); i++)
			{
				posX = (i+1)*(windowWidth/7);
				user = new GButton(profiles.get(i).getName(), posX, 150, 100, 100);
				add(user);
				temp.addGButton(user);
				user.addActionListener(this);
			}
		}

		//when theres too many profiles, new user button goes away
		if (profiles.size() < 4)
		{
			GButton newUser = new GButton("New User", posX+=100, 150, 100, 100);
			add(newUser);
			temp.addGButton(newUser);
			newUser.addActionListener(this);
		}
		temp.getGButton().drawCursor();
		currentScreen = temp;
	}

	private void addNewUser()
	{
		//also do we want to write on text file every time a new user logs in?
		String name = "";

		name = JOptionPane.showInputDialog("Type in Name: ");

		if (name != null)
		{
			loadScreen(GameState.MainMenuScreen);
			Profile newUser = new Profile(name);
			profiles.add(newUser);
			try
			{
				String filename = "profiles.txt";
				FileWriter fw = new FileWriter(filename,true); //the true will append the new data
				fw.write(name + "\n");//appends the string to the file
				currentUser = profiles.indexOf(newUser);
				fw.close();
			}
			catch(IOException ioe)
			{
				System.err.println("IOException: " + ioe.getMessage());
			}
		}
	}
	//dont know if i should get rid of these functions since the labels are needed...

	private void loadOptions()
	{
		if(ifEnterPressed){
			storeGameState.push(gameState);
			storeScreen.push(currentScreen);
		}
		loadBasic(true, giraffe);
		final GuiScreen tmp = (GuiScreen)currentScreen;
		String suffix = gameSoundOff?"On":"Off";
		final GButton sound = new GButton("Game Sound " + suffix, windowWidth/2-50, 10, 100, 50); //button to turn game sound on/off 
		sound.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				gameSoundOff=!gameSoundOff; 
				if(gameSoundOff){
					sound.getGLabel().setLabel("Game Sound On"); 
					AudioPlayer player = AudioPlayer.getInstance();
					player.stopAllSounds();
				}
				else{
					sound.getGLabel().setLabel("Game Sound Off");
					playMainSound();
				}

			}
		});
		add(sound);
		tmp.addGButton(sound);
	}

	private void loadCredits() 
	{
		if(ifEnterPressed){
			storeGameState.push(gameState);
			storeScreen.push(currentScreen);
		}
		loadBasic(true, credits);
	}

	private void loadHowTo() 
	{
		if(ifEnterPressed){
			storeGameState.push(gameState);
			storeScreen.push(currentScreen);
		}
		loadBasic(true, howto);
	}

	private void loadLeaderboards(String s)
	{
		if(ifEnterPressed){
			storeGameState.push(gameState);
			storeScreen.push(currentScreen);
		}
		loadBasic(true, leaderboards);

		//read in high scores from file, based on the map the user selects
		try {
			int i = 0;
			GLabel nameLabel;
			ArrayList<String> names = new ArrayList<String>();
			for (String line : Files.readAllLines(Paths.get("../media/" + s + ".txt")))
			{
				
				names.add(line);
				
			}
			for (int k = 0; k < names.size(); k++)
			{
				if (names.size() - k < 0)
				{
					break;
				}
				else if(k == 5)
				{
					break;
				}
				else
				{
					nameLabel= new GLabel(names.get(names.size()-k-1), 300, 120+i);
					nameLabel.setFont(new Font("Calibri", Font.PLAIN, 20));
					add(nameLabel);
					i+=50;
				}
			}
		
		} catch (IOException e1) {
			System.err.println("leaderboard file not found");
		}
		
		
	}

	public void run()
	{

		//use this to test the different screens. so for example 
		//if you're making the main menu, just change the gamestate to MainMenuScreen and then just load it
		gameState = GameState.UserSelectScreen;
		loadScreen(gameState);
		playMainSound();
	}

	@Override
	public void actionPerformed(ActionEvent event)
	{	
		if("New User".equals(event.getActionCommand())){
			addNewUser();

		}

		if("Pause".equals(event.getActionCommand())){
			showPauseDialog();
		}

		// load profiles
		for(Profile p: profiles)
		{
			if(p.getName().equals(event.getActionCommand()))
			{
				loadScreen(GameState.MainMenuScreen);
				currentUser = profiles.indexOf(p);
			}
		}

		if("New Run".equals(event.getActionCommand())){
			newGameSelected=true;
			loadScreen(GameState.MapSelect);

		}
		else if(event.getActionCommand() != null && event.getActionCommand().contains("GMap")){
			mapIndex=Integer.parseInt(event.getActionCommand().substring(event.getActionCommand().length()-1))-1;
			if (!profiles.get(currentUser).getHelpPageStatus()) //moved this part from loadGame function
			{                            //earlier it kept executing loadgame funct. was not considering what user selected. changed to int funct and moved it 
				int val=showAnnoyingPop();  
				if(val==1)
					return;
			}
			loadScreen(GameState.GameScreen);
			playSoundForMap(event.getActionCommand());
		}
		else if("Credits".equals(event.getActionCommand())){
			loadScreen(GameState.CreditsScreen);

		}
		else if("Options".equals(event.getActionCommand())){
			loadScreen(GameState.OptionsScreen);

		}
		else if("How To".equals(event.getActionCommand())){
			loadScreen(GameState.HowToScreen);

		}
		else if("Finishers".equals(event.getActionCommand())){
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
			LevelScreen temp = (LevelScreen)currentScreen;

			if(!gamePaused)  //when not gamepaused, screen is updated(freeze screen)
				temp.update();

			//temp.update(); //may be repeated code

			//add wave label
			if(waveLabel == null)
			{
				waveLabel = new GLabel("Wave: " + temp.getCurWave(), tileWidth/ 2, tileHeight / 2);
			}
			else if(temp.gameState() != 4 || temp.gameState() != 3)
			{
				waveLabel.setLabel("Wave: " + (temp.getCurWave() + 1));
			}
			waveLabel.setFont(new Font("Calibri", Font.PLAIN, 20));
			waveLabel.setColor(Color.white);
			//waveLabel.setLocation(new GPoint(0,0));

			if(temp.needsUpdating())
			{
				for(Entity e: temp.getEntities())
				{
					if(e.isLiving())
						//keep if alive
						add(e.getScreenObj());
					else 
						//remove else
						remove(e.getScreenObj());
					if(e instanceof Player)
						player = temp.getPlayerEntity();
				}

				for(Entity e: temp.lastWaveEs)
				{
					remove(e.getScreenObj());
					e = null;
				}
				temp.lastWaveEs.clear();


				temp.setNeedsUpdating(false);
			}
			add(waveLabel);
			waveLabel.sendForward();

			//IT WORKS
			if (temp.gameState() == 3)
			{
				String[] buttons = {"Darn"};    //exit and continue for pop ups 
				JOptionPane.showOptionDialog(null, "YOU DED. \n Wave: " +  temp.getCurWave(), "GAME OVER",
						JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, buttons, buttons[0]);

				removeAll();
				loadLeaderboards("LMap" + (mapIndex+1));
				playMainSound();
				//to get back to main menu song, and not have the game song keep playing after exiting.
				playMainSound(); //to get back to main menu song, and not have the game song keep playing after exiting.

			}
			else if(temp.gameState() == 4)
			{
				//won
				try {
					updateHighScores("LMap" + (mapIndex+1), profiles.get(currentUser).getName());
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				String[] buttons = {"Yay!"};    //exit and continue for pop ups 
				JOptionPane.showOptionDialog(null, "YOU WON!!! \n Wave: " +  temp.getCurWave(), "CONGRATULATIONS",
						JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, buttons, buttons[0]);

				removeAll();
				loadLeaderboards("LMap" + (mapIndex+1));
				playMainSound(); //to get back to main menu song, and not have the game song keep playing after exiting.

			}
		}
	}
	
	public void updateHighScores(String map, String name) throws IOException
	{
		/*ArrayList<String> names = new ArrayList<String>();
		names.add(name);
		for (int i = 1; i < 5; i ++)
		{
			try {
		
				for (String line : Files.readAllLines(Paths.get("../bin/" + map + ".txt")))
				{
					names.add(line);
				}
			} catch (IOException e1) {
				System.err.println("leaderboard file not found");
			}
		}
		
		FileOutputStream writer = new FileOutputStream(map);
		writer.write((names.get(0) + "\n").getBytes());
		for (int i = 1; i < names.size(); i++)
		{
			writer.append(names.get(i) + "\n");
			FileWriter fw = new FileWriter(map,true); //the true will append the new data
			fw.write(names.get(i) + "\n");//appends the string to the file
			fw.close();
		}*/
		
		try
		{
			FileWriter fw = new FileWriter(map,true); //the true will append the new data
			fw.write(name + "\n");//appends the string to the file
			fw.close();
		}
		catch(IOException ioe)
		{
			System.err.println("IOException: " + ioe.getMessage());
		}
		
		
		
	}

	public void keyPressed(KeyEvent e)
	{
		try
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
			case KeyEvent.VK_Z:
				currentScreen.inputZ();
				break;
			default:
				break;
			}
		}
		catch(NullPointerException n)
		{

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
		case KeyEvent.VK_UP:
			currentScreen.inputUpReleased();
			break;
		case KeyEvent.VK_Z:
			currentScreen.inputZReleased();
			break;
		default:
			break;
		}
	}

	public void inputEsc()
	{
		if(currentScreen instanceof LevelScreen){
			showPauseDialog(); //for game screen esc
			return;
		}
		if(!storeGameState.empty())
			loadScreen(storeGameState.peek());
		playMainSound();
	}

	private void clearCurScreen()
	{
		removeAll();
		if(currentScreen != null)
			currentScreen.clear();
	}

	private void playMainSound(){
		if(gameSoundOff)
			return;
		AudioPlayer player = AudioPlayer.getInstance();
		if(player.getMediaPlayer("../sounds", "UserSound.mp3").getStatus() != MediaPlayer.Status.PLAYING){
			player.stopAllSounds();
			player.playSoundInLoop("../sounds", "UserSound.mp3");
		}
	}

	private void playSoundForMap(String mapName){
		if(gameSoundOff)
			return;
		AudioPlayer player = AudioPlayer.getInstance();
		String fileName = "";
		if("GMap1".equals(mapName)){
			fileName = "Map1Song.mp3";
		}
		else if("GMap2".equals(mapName)){
			fileName = "Map2Song.mp3";
		}
		else
			fileName= "Map3Song.mp3";

		if(player.getMediaPlayer("../sounds", fileName).getStatus() != MediaPlayer.Status.PLAYING){
			player.stopAllSounds();
			player.playSoundInLoop("../sounds", fileName); 
		}
	}
}