package model;

import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Color;

import javax.swing.Timer;

import model.observerStrategy.Observer;
import view.GameBoard;

public class PowerUpMeter extends GameElement implements Observer {

	// reference to gameBoard so we can turn off shooter power ups
	GameBoard gameBoard;
	
	// x, y, height, color shared by filled and empty rectangle
	// 		width and filled only for filled rectangle
	// 		width2 only for empty rectangle
	private int width2;

	// timer to for meter animation and when to remove power up from player
	Timer timer = new Timer(70, new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent e) {
			width -= 5;

			if (width == 0) {
				gameBoard.getShooter().deactivatePower();
				gameBoard.getShooter().notifyListener();
				timer.stop();
			}
		}
	});

	public PowerUpMeter(int x, int y, int width, int height, Color color) {

		this.color = color;
		this.x = x;
		this.y = y;
		this.height = height;

		this.width = 0;

		width2 = width;

		
	}

	public void startPowerUpMeter() {
		width = width2;
		timer.start();
	}
	
	public void setGameBoard(GameBoard gameBoard) {
		this.gameBoard = gameBoard;
	}

	@Override
	public void render(Graphics2D g2) {
		g2.setColor(color);

		g2.fillRect(x, y, width, height);
		g2.drawRect(x, y, width2, height);
	}

	@Override
	public void animate() {

	}

	@Override
	public void actionPerformed(boolean hasPower) {
		// start power up meter
		if (hasPower)
			startPowerUpMeter();
	}	
}
