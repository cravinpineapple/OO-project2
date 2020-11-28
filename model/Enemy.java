package model;

import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.Random;

import model.observerStrategy.Observer;
import pictures.PictureStore;
import view.GameBoard;


public class Enemy extends GameElement implements Observer {

	// temp holds for normal and angry to swap between!
	private BufferedImage normalImage;
	private BufferedImage angryImage;

	public Enemy(int x, int y, int size, Color color, boolean filled) {
		super(x, y, color, filled, size, size);
		setEnemyImages();
	}

	private void setEnemyImages() {
		Random rand = new Random();
		switch (rand.nextInt(3)) { // **temporarily generating 3 until 4th graphic complete
			case 0:
				image = normalImage = PictureStore.normalCarrot;
				angryImage = PictureStore.angryCarrot;
				break;
			case 1:
				image = normalImage = PictureStore.normalStrawberry;
				angryImage = PictureStore.angryStrawberry;
				break;
			case 2:
				image = normalImage = PictureStore.normalOnion;
				angryImage = PictureStore.angryOnion;
				break;
			case 3:
				// image = PictureStore. 4th normal enemy image
				// image2 = PictureStore. 4th angry enemy image
				break;

		}
	}

	@Override
	public void render(Graphics2D g2) {
		g2.setColor(color);

		g2.drawImage(image, null, x - 5, y - 5);

		
		if (GameBoard.showHitBox)
			g2.fillRect(x, y, width, height);
		
	}

	@Override
	public void animate() {
		// composite group of enemies
	}

	@Override
	public void actionPerformed(boolean hasPower) {
		if (hasPower)
			image = angryImage;
		else
			image = normalImage;
	}
}
