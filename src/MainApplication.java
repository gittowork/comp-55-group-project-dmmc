import java.awt.*;

import DMMC.Screen.GuiScreen;
import acm.graphics.*;
import acm.program.*;

public class MainApplication extends GraphicsApplication {
	public static final int WINDOW_WIDTH = 800;
	public static final int WINDOW_HEIGHT = 600;
	
	private SomePane somePane;
	private MenuPane menu;
	private int count = 0;
	
	public void init() {
		setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
	}
	
	public void run() {
		System.out.println("Hello, world!");
		somePane = new SomePane(this);
		menu = new MenuPane(this);
		setupInteractions();
		switchToSome();
		System.out.println("Yo Sup");
	}
	
	/* Method: setupInteractions
	 * -------------------------
	 * must be called before switching to another
	 * pane to make sure that interactivity
	 * is setup and ready to go.
	 */
	private void setupInteractions() {
		requestFocus();
		addKeyListeners();
		addMouseListeners();
	}
	
	public void switchBack() {
		AudioPlayer audio = AudioPlayer.getInstance();
		switch(count % 2) {
			case 0: audio.stopSound("sounds", "r2d2.mp3"); break;
			case 1: audio.stopSound("sounds", "somethinlikethis.mp3"); break;
		}
		count++;
		switchToScreen(menu);
	}
	
	public void switchToSome() {
		AudioPlayer audio = AudioPlayer.getInstance();
		switch(count % 2) {
			case 0: audio.playSound("sounds", "r2d2.mp3"); break;
			case 1: audio.playSound("sounds", "somethinlikethis.mp3"); break;
		}
		switchToScreen(somePane);
	}
}
