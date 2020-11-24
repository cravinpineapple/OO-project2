package model;

import java.awt.Color;
import java.awt.Graphics2D;

public class Shield extends GameElement {

	public Shield(Shooter shooter) {
		filled = true;
		x = shooter.x - 30;
		y = shooter.y - 40;
		width = ShooterElement.SIZE * 2 + 20;
		height = 5;
		color = Color.cyan;
	}

	@Override
	public void render(Graphics2D g2) {
		g2.setColor(color);
		if (filled) {
			g2.fillRect(x, y, width, height);
		}
		else {
			g2.drawRect(x, y, width, height);
		}
	}

	// animation done with moveRight / moveLeft in Shooter.java
	@Override
	public void animate() { }
	
}
