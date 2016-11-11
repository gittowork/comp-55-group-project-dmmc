package DMMC.Screen;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import DMMC.Physics.Button;
public class GuiScreen extends Screen 
{
	private int cursorPosX;				//which button the cursor is at
	private ArrayList<Button> button;	//stores the buttons on the screen

	public GuiScreen(int sizeX, int sizeY) 
	{
		super(sizeX, sizeY);		
	}

	
	public void keyPressed(KeyEvent e)
	{
		if (e.getKeyCode() == 38) //38 is up
		{
			//do up things
			inputUp();
		}
		
		else if (e.getKeyCode() == 40) //40 is down
		{
			//do down things
			inputDown();
		}
		
		else if (e.getKeyCode() == 37) //37 is left
		{
			//do left things
			inputLeft();
		}
		
		else if (e.getKeyCode() == 39) //39 is right
		{
			//do right things
			inputRight();
		}
		
		else if (e.getKeyCode() == 13) //13 is enter
		{
			//do enter things...
			inputEnter();
		}
		
		else
			return;
	}
	
	@Override
	public void inputUp()
	{
		System.out.println("uppp");
	}
	
	@Override
	public void inputDown()
	{
		System.out.println("down we goo");
	}
	
	@Override
	public void inputLeft()
	{
		System.out.println("now left");
	}
	
	@Override
	public void inputRight()
	{
		System.out.println("now to the right");
	}
	
	@Override
	public void inputZ()
	{
		
	}

	@Override
	public void inputEnter()
	{
		System.out.println("enterrrr");
	}


}
