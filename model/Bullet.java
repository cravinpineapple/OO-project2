package model;

import java.awt.Graphics2D;

import pictures.PictureStore;
import view.GameBoard;

import java.awt.Color;

public class Bullet extends GameElement {

	public static final int WIDTH = 20;
	public static final int UNIT_MOVE = 10;

	public Bullet(int x, int y) {
		super(x, y, Color.red, true, WIDTH, WIDTH);
		image = PictureStore.bullet;
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
		super.y -= 10;
	}
	
}
