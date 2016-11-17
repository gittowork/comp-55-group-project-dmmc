package DMMC.Screen;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import DMMC.Physics.Button;

public class GuiScreen extends Screen implements ActionListener
{
	private int cursorPosX;				//which button the cursor is at
	private ArrayList<Button> button;	//stores the buttons on the screen

	public GuiScreen(int sizeX, int sizeY) 
	{
		super(sizeX, sizeY);	
		cursorPosX = 0;
		ArrayList<Button> button = new ArrayList<Button>();
	}

	public void updateButtonList()
	{
		
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
		button.get(cursorPosX).buttonAction();
		System.out.println("enterrrr");
	}
	@Override
	public void inputEsc()
	{
		
	}

	
	


	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}


}
