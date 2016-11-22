package DMMC.Screen;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import DMMC.Game;
import DMMC.Physics.Entity;
import DMMC.Physics.Player;
import acm.graphics.GImage;

public class LevelScreen extends Screen implements ActionListener {
	
	private ArrayList <Entity> entities; 

	private short frameNum; 
	
	private int curWave;
	
	public LevelScreen(int sizeX, int sizeY) {
		super(sizeX, sizeY);
		entities = new ArrayList<Entity>();
		Entity e = new Player(new GImage("player-0.png"), Game.getAnime("player-0"));
		e.getScreenObj().setSize(e.getScreenObj().getSize().getWidth() * 2, e.getScreenObj().getSize().getHeight() * 2);
		e.setScreenPosX(e.getScreenPosX() + 10);
		entities.add(e);
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
	
	public short getFrame() 
	{
		return frameNum;
		
	}
	
	public ArrayList<Entity> getEntities(){
		return entities;
	}

	@Override
	public void inputRight() 
	{
		for (Entity e: entities){
			if (e instanceof Player){
				e.setVelX(1);
				break;
			}
		}
	}

	@Override
	public void inputZ() 
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void inputEnter() 
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void inputUp() 
	{
		for (Entity e: entities){
			if (e instanceof Player){
				e.setVelY(30);
				break;
			}
		}
	}

	@Override
	public void inputDown() 
	{
		// TODO Auto-generated method stub
		
	}
	
	
	@Override
	public void inputReleased()
	{
		for (Entity e: entities){
			if (e instanceof Player){
				e.setVelX(0);
				break;
			}
		}
	}

	@Override
	public void inputLeft() 
	{
		for (Entity e: entities){
			if (e instanceof Player){
				e.setVelX(-1);
				break;
			}
		}
	}
	
	@Override
	public void inputEsc() 
	{
		// TODO Auto-generated method stub
		
	}
}