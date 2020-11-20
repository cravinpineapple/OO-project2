package model;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;

import view.GameBoard;

public class Shooter extends GameElement {

	public static final int UNIT_MOVE = 5;
	public static final int MAX_BULLETS = 3;
	public static int SPEED_BOOST = 0;
	public static int EXTRA_BULLETS = 0; 

	private ArrayList<GameElement> components = new ArrayList<>();
	private ArrayList<GameElement> weapons = new ArrayList<>();
	private Shield shield;
	public Shooter extraShooter;
	private boolean isExtraShooter = false;

	public Shooter(int x, int y) {
		super(x, y, 0, 0);

		var size = ShooterElement.SIZE;
		var s1 = new ShooterElement(x - size, y - size, Color.white, false);    // top left
		var s2 = new ShooterElement(x, y - size, Color.white, false);		    // top right
		var s3 = new ShooterElement(x - size, y, Color.white, false); 			// bottom left
		var s4 = new ShooterElement(x, y, Color.white, false); 					// bottom right
		components.add(s1);
		components.add(s2);
		components.add(s3);
		components.add(s4);
	}

	// constructor for extra shooter
	public Shooter(int x, int y, int size) {
		super(x, y, 0, 0);

		isExtraShooter = true;
		var s1 = new ShooterElement(x - size, y - size, Color.white, false);    // top left

		components.add(s1);
	}

	public void moveRight() {
		super.x += UNIT_MOVE + SPEED_BOOST;
		for (var c: components) {
			c.x += UNIT_MOVE + SPEED_BOOST;
		}

		if (shield != null) 
			shield.x += UNIT_MOVE + SPEED_BOOST;

		if (extraShooter != null) {
			extraShooter.x += UNIT_MOVE + SPEED_BOOST;
			extraShooter.components.get(0).x += UNIT_MOVE + SPEED_BOOST;
		}
	}

	public void moveLeft() {
		super.x -= UNIT_MOVE + SPEED_BOOST;
		for (var c: components) {
			c.x -= UNIT_MOVE + SPEED_BOOST;
		}

		if (shield != null)
			shield.x -= UNIT_MOVE + SPEED_BOOST;

		if (extraShooter != null) {
			extraShooter.x -= UNIT_MOVE + SPEED_BOOST;
			extraShooter.components.get(0).x -= UNIT_MOVE + SPEED_BOOST;
		}
	}

	public boolean canFireMoreBullets() {
		if (isExtraShooter)
			return weapons.size() < MAX_BULLETS - 2; 

		return weapons.size() < MAX_BULLETS + EXTRA_BULLETS;
	}

	public void removeBulletsOutOfBound() {
		var remove = new ArrayList<GameElement>();

		for (var w: weapons) {
			if (w.y < 0)
				remove.add(w);
		}

		weapons.removeAll(remove);
	}

	@Override
	public void render(Graphics2D g2) {

		// shield render
		if (shield != null && !isExtraShooter)
			shield.render(g2);

		// extra shooter render
		if (extraShooter != null && !isExtraShooter)
			extraShooter.render(g2);

		// body render
		for (var c: components) {
			c.render(g2);
		}

		// bullets render
		for (var w: weapons) {
			w.render(g2);
		}
	}

	public ArrayList<GameElement> getWeapons() {
		return weapons;
	}

	public ArrayList<GameElement> getComponents() {
		return components;
	}

	// shield power activate
	public void activateShield() {
		shield = new Shield(this);
	}

	// shield power deactivate
	public void deactivateShield() {
		shield = null;
	}

	// extra shooter activate
	public void activateExtraShooter() {
		extraShooter = new Shooter(x + ShooterElement.SIZE * 2 + 10, y - 3, ShooterElement.SIZE / 4);
	}
	
	// extra shooter deactivate
	public void deactivateExtraShooter() {
		extraShooter = null;
	}
	
	public void checkPlayerComponents() {
		if (components.isEmpty())
			GameBoard.setGameWon(false);
	}

	@Override
	public void animate() {
		if (shield != null)
			shield.animate();

		// calling so each bullet's animate is called  
		for (var w: weapons) {
			w.animate();
		}

		if (extraShooter != null) {
			for (var w: extraShooter.weapons)
				w.animate();
		}
	}

	public Shield getShield() {
		return shield;
	}

	public Shooter getExtraShooter() {
		return extraShooter;
	}
}
