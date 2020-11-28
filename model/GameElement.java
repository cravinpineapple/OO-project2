package model;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;


public abstract class GameElement {

	protected BufferedImage image;
	
	public int x;
	public int y;
	public Color color;
	public boolean filled;
	public int width;
	public int height;

	public GameElement(int x, int y, Color color, boolean filled, int width, int height) {
		this.x = x;
		this.y = y;
		this.color = color;
		this.filled = filled;
		this.width = width;
		this.height = height;
	}

	// constructor w/ image
	public GameElement(int x, int y, Color color, boolean filled, int width, int height, BufferedImage image) {
		this.x = x;
		this.y = y;
		this.color = color;
		this.filled = filled;
		this.width = width;
		this.height = height;
		this.image = image;
	}

	public GameElement() {
		this(0, 0, Color.white, false, 0, 0);
	}

	public GameElement(int x, int y, int width, int height) {
		this(x, y, Color.white, false, width, height);
	}

	public boolean collideWith(GameElement another) {
		if (another.x > x + width || another.x + another.width < x ||
			y + height < another.y || another.y + another.height < y) {
				return false;
		}
		else 
			return true;
	}

	public void setImage(BufferedImage image) {
		this.image = image;
	}

	public abstract void render(Graphics2D g2);
	public abstract void animate();

}
