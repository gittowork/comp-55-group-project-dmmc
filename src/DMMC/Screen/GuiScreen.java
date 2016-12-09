package DMMC.Screen;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import com.sun.javafx.geom.PickRay;

import DMMC.Physics.GButton;
import acm.graphics.GImage;


public class GuiScreen extends Screen implements ActionListener
{
	private int cursorPosX;				//which button the cursor is at
	private ArrayList<GButton> gButtons; //

	public GuiScreen(int sizeX, int sizeY) 
	{
		super(sizeX, sizeY);
		
		
		cursorPosX = 0;
		//ArrayList<Button> button = new ArrayList<Button>();	
		gButtons=new ArrayList<GButton>();
		
	}
	
	public GuiScreen(int sizeX, int sizeY, GImage pic)
	{
		super(sizeX, sizeY, pic);
		cursorPosX = 0;
		gButtons = new ArrayList<GButton>();
	}

	public void updateButtonList()
	{

	}

	//currscreen.inputUp() in GAME(move it) 

	@Override
	public void inputUp()
	{
		updateCursorPos(-1);
	}

	@Override
	public void inputDown()
	{
		updateCursorPos(1);
	}

	@Override
	public void inputLeft()
	{
		updateCursorPos(-1);
	}

	@Override
	public void inputRight()
	{
		updateCursorPos(1);
	}

	@Override
	public void inputZ()
	{

	}

	@Override
	public void inputEnter()
	{
		if(getGButton() != null){
			GButton b = getGButton();
			b.fireActionEvent(b.getGLabel().getLabel());
		}
	}
	@Override
	public void inputEsc()
	{
		
		
	}
	
	@Override
	public void inputLeftReleased()
	{
		
	}
	@Override
	public void inputRightReleased()
	{
		
	}
	
	@Override
	public void inputUpReleased()
	{
		
	}
	@Override
	public void inputZReleased()
	{
		
	}
	
	
	@Override
	public void update() {
		
		
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
			if(x >= startX 
					&& y >= startY 
					&& x <= startX+width 
					&& y <= startY+h)
				return b;

		}
		return null;
	}

	public GButton getGButton(){ //returns button at current cursor
		if(gButtons.size()==0)
		{
			return null;
		}
		return gButtons.get(cursorPosX); //using cursor position now 
	}

	public void addGButton(GButton b){
		gButtons.add(b);
	}

	public int getCursorPos(){
		return cursorPosX;
	}
	public void setCursorPos(int pos){
		this.cursorPosX=pos;
	}
	
	@Override
	public void clear()
	{
		tileMap = null;
		gButtons = null;
	}
}
