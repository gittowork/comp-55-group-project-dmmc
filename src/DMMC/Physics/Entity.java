package DMMC.Physics;

import java.awt.Image;

import acm.graphics.GImage;
import acm.graphics.GPoint;

public abstract class Entity extends PhysicsObject{
	
	private static int lastId = 0;
	
	protected GPoint velocity;
	protected GPoint acceleration;
	private int id;
	protected int curHealth;
	protected int maxHealth;
	protected boolean weightless;
	protected boolean collidable;
	protected CollisionPoint[] colPoints;

	public Entity(GImage i, Image[] initAnimation) {
		super(i, initAnimation);
		
	}
	
	public Entity(GImage i) {
		super(i);
	}
}
