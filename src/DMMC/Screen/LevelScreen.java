package DMMC.Screen;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.Timer;

import DMMC.Physics.Entity;

public class LevelScreen extends Screen implements ActionListener {
	
	private ArrayList <Entity> entities; 
	
	private Timer updateTimer;

	private short frameNum; 
	
	private int curWave;
	
	public LevelScreen(int sizeX, int sizeY) {
		super(sizeX, sizeY);
		updateTimer=new Timer(1000, this); //do we need an initial delay?
		updateTimer.setInitialDelay(3000);
	}

	public void drawEntities() {
		// TODO Auto-generated method stub
		
	}


	public void collisionUpdate() {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	public short getFrame() {
		return frameNum;
		
	}


/*
	public void inputRight() {
		// TODO Auto-generated method stub
		
	}


	public void inputZ() {
		// TODO Auto-generated method stub
		
	}


	public void inputEnter() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void inputUp() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void inputDown() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void inputLeft() {
		// TODO Auto-generated method stub
		
	}
*/
	

}