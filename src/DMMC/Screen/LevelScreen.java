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
	
	private Entity player;
	
	public LevelScreen(int sizeX, int sizeY) {
		super(sizeX, sizeY);
		entities = new ArrayList<Entity>();
		Entity e = new Player(new GImage("player-0.png"), Game.getAnime("player-0"));
		e.getScreenObj().setSize(e.getScreenObj().getSize().getWidth() * 2, e.getScreenObj().getSize().getHeight() * 2);
		e.setScreenPosX(Game.windowWidth/2);
		e.setScreenPosY(Game.windowHeight/2);
		entities.add(e);
		player = e;
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
		player.setVelX(Entity.maxVelX);
		player.setForced(true);
	}

	
	@Override
	public void inputLeft() 
	{
		player.setVelX(-Entity.maxVelX);
		player.setForced(true);
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
		if (player.isGrounded()){
			player.setVelY(-6);
		}
	}

	@Override
	public void inputDown() 
	{
		// TODO Auto-generated method stub
		
	}
	
	
	@Override
	public void inputLeftReleased()
	{
		player.setForced(false);
	}
	
	@Override
	public void inputRightReleased()
	{
		player.setForced(false);
	}

	@Override
	public void inputEsc() 
	{
		// TODO Auto-generated method stub
		
	}
	
	public Entity getPlayerEntity(){
		return player;
	}
}