package model;

import java.awt.Graphics2D;

import pictures.PictureStore;
import view.GameBoard;

import java.awt.Color;

public class Bomb extends GameElement {

	public static final int SIZE = 10;
	public static final int UNIT_MOVE = 5;

	public Bomb(int x, int y) {
		super(x, y, Color.green, true, SIZE, SIZE * 2);
		image = PictureStore.bomb;
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
		super.y += UNIT_MOVE;
	}
	
}
