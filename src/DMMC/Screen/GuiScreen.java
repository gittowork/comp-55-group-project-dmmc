package DMMC.Screen;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import DMMC.Physics.Button;
import DMMC.Physics.GButton;

public class GuiScreen extends Screen implements ActionListener
{
	private int cursorPosX;				//which button the cursor is at
	//private ArrayList<Button> button;	(stores the buttons on the screen)
	private ArrayList<GButton> gButtons; //

	public GuiScreen(int sizeX, int sizeY) 
	{
		super(sizeX, sizeY);	
		cursorPosX = 0;
		//ArrayList<Button> button = new ArrayList<Button>();	
		gButtons=new ArrayList<GButton>();
		
	}

	public void updateButtonList()
	{
		
	}
	
	//currscreen.inputUp() in GAME(move it) 
	

	
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
	@Override
	public void inputEsc()
	{
		
	}

	
	


	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}

	public GButton getGButton(double x, double y){
		for(GButton b : gButtons){
			double startX = b.getX();
			double startY = b.getY();
			double width = b.getWidth();
			double h = b.getHeight();
			if(x>=startX && y>=startY && x<=startX+width && y<=startY+h)
				return b;
					
		}
		return null;
	}
	
	public GButton getGButton(){
		if(gButtons.size()==0)
			return null;
		return gButtons.get(0);
	}
	
	public void addGButton(GButton b){
		gButtons.add(b);
	}


}
