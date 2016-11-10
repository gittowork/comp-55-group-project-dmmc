package DMMC.Screen;

import java.awt.Button;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class GuiScreen extends Screen 
{
	private int cursorPosX;
	private ArrayList<Button> button;

	public GuiScreen(int sizeX, int sizeY) 
	{
		super(sizeX, sizeY);
	}

	
	public void keyPressed(KeyEvent e)
	{
		//int input = e.getKeyCode()
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
	
	public void inputUp()
	{
		
	}
	
	public void inputDown()
	{
		
	}
	
	public void inputLeft()
	{
		
	}
	
	public void inputRight()
	{
		
	}
	
	public void inputZ()
	{
		
	}
	
	public void inputEnter()
	{
		
	}


}
