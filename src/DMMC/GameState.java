package DMMC;

public enum GameState {
	Init("Init"),
	UserSelectScreen("UserSelectScreen"),
	MainMenuScreen("MainMenuScreen"),
	CreditsScreen("CreditsScreen"),
	HowToScreen("HowToScreen"),
	Leaderboards("Leaderboards"),
	OptionsScreen("OptionsScreen"),
	LevelSelectScreen("LevelSelect"),
	GameScreen("GameScreen");
	
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
