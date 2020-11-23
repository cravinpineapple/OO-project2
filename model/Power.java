package model;

import java.awt.Graphics2D;
import java.awt.Color;

public class Power extends GameElement {

	public enum PowerType {
		SPEED, SHIELD, EXTRA_BULLETS, EXTRA_SHOOTER
	}

	private int fallSpeed;
	private PowerType powerType;

	public Power() {
		filled = true;
	}

	public void setPosition(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public void setSize(int width, int height) {
		this.width = width;
		this.height = height;
	}
	
	public void setFallSpeed(int fallSpeed) {
		this.fallSpeed = fallSpeed;
	}

	public void setPowerType(PowerType powerType) {
		this.powerType = powerType;
	}

	public void activatePower(Shooter shooter) {
		// startPowerTimer
		switch (powerType) {
			case SPEED:
				Shooter.SPEED_BOOST = Shooter.UNIT_MOVE;
				break;
			case SHIELD:
				shooter.activateShield();
				break;
			case EXTRA_BULLETS:
				Shooter.EXTRA_BULLETS = Shooter.MAX_BULLETS;
				break;
			case EXTRA_SHOOTER:
				shooter.activateExtraShooter();
				break;
		}
	}

	@Override
	public void render(Graphics2D g2) {
		g2.setColor(color);
		if (filled) {
			g2.fillOval(x, y, width, height);
		}
		else {
			g2.drawOval(x, y, width, height);
		}
	}

	@Override
	public void animate() {
		super.y += fallSpeed;
	}

	@Override
	public String toString() {
		return "PowerType: " + powerType + " || Fall Speed: " + fallSpeed + " || Color: " + color + 
				" || Position(x, y): (" + x + ", " + y + ") || Size(W, H): (" + width + ", " 
				+ height + ")";
	}

}
