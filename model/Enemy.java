package model;

import java.awt.Graphics2D;

import model.observerStrategy.Observer;

import java.awt.Color;

public class Enemy extends GameElement implements Observer {

	public Enemy(int x, int y, int size, Color color, boolean filled) {
		super(x, y, color, filled, size, size);
	}

	@Override
	public void render(Graphics2D g2) {
		g2.setColor(color);

		if (filled)
			g2.fillRect(x, y, width, height);
		else
			g2.drawRect(x, y, width, height);
	}

	@Override
	public void animate() {
		// composite group of enemies
	}

	@Override
	public void actionPerformed(boolean hasPower) {
		if (hasPower)
			color = Color.red;
		else
			color = Color.yellow;
	}
}
