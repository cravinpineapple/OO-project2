package model;

import java.awt.Graphics2D;

import pictures.PictureStore;
import view.GameBoard;

import java.awt.Color;

public class Power extends GameElement {

	public enum PowerType {
		SPEED, SHIELD, EXTRA_BULLETS, EXTRA_SHOOTER
	}

	public static final int WIDTH = 30;
	public static final int HEIGHT = 30;

	private int fallSpeed;
	private PowerType powerType;

	public Power() {
		filled = true;
	}

	private void setPowerImage() {
		switch (powerType) {
			case SPEED:
				image = PictureStore.speedPower;
				break;
			case SHIELD:
				image = PictureStore.shieldPower;
				break;
			case EXTRA_BULLETS:
				image = PictureStore.bulletPower;
				break;
			case EXTRA_SHOOTER:
				image = PictureStore.shooterPower;
				break;
		}
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
		setPowerImage();
	}

	public void activatePower(Shooter shooter) {
		// startPowerTimer
		shooter.activatePower(powerType);
	}

	@Override
	public void render(Graphics2D g2) {
		g2.setColor(color);

		g2.drawImage(image, null, x, y);
		
		if (GameBoard.showHitBox)
			g2.fillRect(x, y, width, height);
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
