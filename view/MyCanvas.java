package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;

import javax.swing.JPanel;

import model.GameElement;

public class MyCanvas extends JPanel {
	
	private GameBoard gameBoard;
	private ArrayList<GameElement> gameElements = new ArrayList<>();

	public MyCanvas(GameBoard gameBoard, int width, int height) {
		this.gameBoard = gameBoard;
		setBackground(Color.black);
		setPreferredSize(new Dimension(width, height));
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;

		// displaying game over information
		if (GameBoard.isGameOver) {
			String gameOutcomeText = GameBoard.gameWon ? "You Won!" : "You Lost!";
			Color gameOutcomeColor = GameBoard.gameWon ? Color.blue : Color.red;
			TextDraw gameOverText = new TextDraw("Game Over, " + gameOutcomeText, 144, 190, gameOutcomeColor, 30);
			TextDraw scoreText = new TextDraw("Score: " + "XXX", gameOverText.x + 120, gameOverText.y + 30, Color.white, 15);
			gameOverText.render(g2);
			scoreText.render(g2);

			return;
		}

		for (var e: gameElements) {
			e.render(g2);
		}
	}

	public ArrayList<GameElement> getGameElements() {
		return gameElements;
	}

}
