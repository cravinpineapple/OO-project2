package model;

import java.awt.image.BufferedImage;
import java.awt.Graphics2D;
import java.awt.Color;

public class ShooterElement extends GameElement {

	// keeping reference for quick access to x & y
	private Shooter shooter;

	public static final int SIZE = 20;
	public ShooterElement(int x, int y, Color color, boolean filled) {
		super(x, y, color, filled, SIZE, SIZE);
	}

	// constructor w/ image (passing shooter ref for quick access to true x & y);
	public ShooterElement(Shooter shooter, int x, int y, Color color, boolean filled, BufferedImage image) {
		super(x, y, color, filled, SIZE, SIZE, image);
		this.shooter = shooter;
	}

	@Override
	public void render(Graphics2D g2) {
		g2.setColor(color);

		g2.drawImage(image, null, shooter.x - width, shooter.y - width);

		// hit box rendering
		
		if (filled) {
			g2.fillRect(x, y, width, height);
		}
		else {
			g2.drawRect(x, y, width, height);
		}
		
	}

	@Override
	public void animate() { }
	
}
