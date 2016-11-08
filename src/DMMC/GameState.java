package DMMC;

public enum GameState {
	Init("Init"),
	UserSelectScreen("UserSelectScreen"),
	MainMenuScreen("MainMenuScreen"),
	GameScreen("GameScreen");
	//...
	
	private String name;
	
	private GameState(String n) {
		name= n;
	}
	
	@Override
	public String toString()
	{
		return name;
	}
}
