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
		updateCursorPos(-1);
	}

	@Override
	public void inputDown()
	{
		System.out.println("down we goo");
		updateCursorPos(1);
	}

	@Override
	public void inputLeft()
	{
		System.out.println("now left");
		updateCursorPos(2);	//??????
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
		if(getGButton() != null){
			GButton b = getGButton();
			b.fireActionEvent(b.getGLabel().getLabel());
		}
	}
	@Override
	public void inputEsc()
	{
			if(getGButton() != null ){
				GButton b = getGButton();
				if("Go Back(Esc)".equals(b.getGLabel().getLabel()))
					b.fireActionEvent(b.getGLabel().getLabel());
		}
	}


	public void updateCursorPos(int update) {
		if(gButtons==null|| gButtons.size()==0)
			return; 
		cursorPosX+=update;

		if(cursorPosX<0)
			cursorPosX=gButtons.size()-1;
		if(cursorPosX >= gButtons.size())
			cursorPosX=0;
		drawCursorAtNewPos();	
	}
	public void drawCursorAtNewPos(){
		if(gButtons.size()==0)
			return;
		for(GButton b : gButtons)
			b.removeCursor();
		gButtons.get(cursorPosX).drawCursor();
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

	public GButton getGButton(){ //returns button at current cursor
		if(gButtons.size()==0)
			return null;
		return gButtons.get(cursorPosX); //using cursor position now 
	}

	public void addGButton(GButton b){
		gButtons.add(b);
	}


}
