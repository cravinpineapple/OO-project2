package model.builderStrategy;

import java.awt.Graphics2D;
import java.awt.Color;

import model.GameElement;

public class Power extends GameElement {

	private int fallSpeed;

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

}
