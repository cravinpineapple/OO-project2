package model.strategyPattern;

import model.EnemyComposite;
import view.GameBoard;
import view.MyCanvas;
import view.TextDraw;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Color;

import javax.swing.Timer;

public class Level3 implements LevelActivator {

	private GameBoard gameBoard;
	private MyCanvas canvas;

	// displaying level text
	Color clearColor = new Color(0, 0, 0, 200);
	Color currentColor = Color.white;
	int textX = 275;
	int textY = 275;
	int textSize = 60;

	// timer to flash level and give brief intermission between levels
	int timerCount = 1300;
	Timer timer = new Timer(100, new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent e) {
			// flashes white/clear text
			if (timerCount % 100 == 0 && timerCount >= 800)
				currentColor = clearColor;
			else
				currentColor = Color.white;
			
			canvas.levelText = new TextDraw("Level 3", textX, textY, currentColor, textSize);

			// starts level once animation is complete
			if (timerCount == 0) {
				timerCount = 1000;
				GameBoard.changingLevel = false;
				canvas.levelText = null;
				gameBoard.beginLevel();
				timer.stop();
			}

			timerCount -= 50;
		}
	});

	public Level3(GameBoard gameBoard) {
		this.gameBoard = gameBoard;
		canvas = gameBoard.getCanvas();
		canvas.levelText = new TextDraw("Level 3", textX, textY, currentColor, textSize);
		GameBoard.changingLevel = true;
		timer.start();
	}

	@Override
	public void setLevelSettings() {
		EnemyComposite.UNIT_MOVE = 5;
		EnemyComposite.NCOLS = 7;
	}

	@Override
	public void startLevel() {
		gameBoard.getEnemyComposite().initEnemyComposite();
	}
}