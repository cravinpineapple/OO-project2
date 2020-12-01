package view;

import java.awt.Container;
import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

import controller.KeyController;
import controller.TimerListener;
import model.Enemy;
import model.EnemyComposite;
import model.PowerUpMeter;
import model.Shooter;
import model.ShooterElement;
import model.builderStrategy.PowerBuilderDirector;
import model.strategyPattern.Level1;
import model.strategyPattern.Level2;
import model.strategyPattern.Level3;
import model.strategyPattern.Level4;
import model.strategyPattern.Level5;
import model.strategyPattern.LevelActivator;

public class GameBoard {
	
	public static final int MENU_SCREEN_HEIGHT = 100;
	public static final int MENU_SCREEN_WIDTH = 0;
	public static final int GAME_SCREEN_HEIGHT = 500; // orig: 400
	public static final int GAME_SCREEN_WIDTH = 750;  // orig: 600
	
	public static final int WIDTH = MENU_SCREEN_WIDTH + GAME_SCREEN_WIDTH;
	public static final int HEIGHT = MENU_SCREEN_HEIGHT + GAME_SCREEN_HEIGHT;

	public static boolean isGameOver = false;
	public static boolean isGameWaiting = true;
	public static boolean gameWon = false;
	public static boolean showHitBox = false;
	public static int score = 0;

	// strategyPattern
	private LevelActivator levelActivator;
	public static int levelCount = 0; // tracks what level we are currently on
	public static boolean changingLevel = true;


	public enum ScoreCategory {
		ENEMY_KILL, BULLET_KILL, POWERUP_GET
	}

	// fps 
	public static final int FPS = 20;
	public static final int DELAY = 1000 / FPS;

	private JFrame window;
	private MyCanvas canvas;
	private Timer timer;
	private TimerListener timerListener;

	private Shooter shooter;
	private EnemyComposite enemyComposite;
	private PowerBuilderDirector powerBuilderDirector;
	private PowerUpMeter powerUpMeter;
	private TextDraw scoreText = new TextDraw("Score: " + score, 10, GameBoard.GAME_SCREEN_HEIGHT + 70, Color.white, 23);
	private TextDraw meterText = new TextDraw("PowerUp Meter", 150 + 20, GameBoard.GAME_SCREEN_HEIGHT + 35, Color.white, 10);

	JButton startButton;

	public GameBoard(JFrame window) {
		this.window = window;
	}

	public void init() {
		Container cp = window.getContentPane();

		canvas = new MyCanvas(this, WIDTH, HEIGHT);
		canvas.addKeyListener(new KeyController(this));
		canvas.requestFocusInWindow();
		canvas.setFocusable(true);
		cp.add(BorderLayout.CENTER, canvas);

		startButton = new JButton("Start");
		JButton quitButton = new JButton("Quit");
		startButton.setFocusable(false);
		quitButton.setFocusable(false);

		JPanel southPanel = new JPanel();
		southPanel.add(startButton);
		southPanel.add(quitButton);
		cp.add(BorderLayout.SOUTH, southPanel);

		canvas.getGameElements().add(new TextDraw("Click <Start> to Play", 100, 100, Color.yellow, 30));

		timerListener = new TimerListener(this);
		timer = new Timer(DELAY, timerListener);
		
		startButton.addActionListener(e -> {
			if (isGameOver) {
				gameWon = false;
				isGameOver = false;
				score = 0;
				levelCount = 0;
			}

			// setting up game elements
			shooter = new Shooter(GameBoard.GAME_SCREEN_WIDTH / 2, GameBoard.GAME_SCREEN_HEIGHT - ShooterElement.SIZE);
			enemyComposite = new EnemyComposite();
			enemyComposite.setGameBoard(this);
			powerBuilderDirector = new PowerBuilderDirector(this);
			powerUpMeter = new PowerUpMeter(150 + 20, GameBoard.GAME_SCREEN_HEIGHT + 45, GAME_SCREEN_WIDTH - 190, 40, Color.white);
			powerUpMeter.setGameBoard(this);

			// adding game elements to to-be-rendered array list
			canvas.getGameElements().clear();
			canvas.getGameElements().add(powerUpMeter);
			canvas.getGameElements().add(scoreText);
			canvas.getGameElements().add(meterText);
			canvas.getGameElements().add(shooter);
			canvas.getGameElements().add(enemyComposite);
			shooter.addListener(powerUpMeter);

			isGameWaiting = false;
			timer.start();

			startNextLevel();
		});

		quitButton.addActionListener(e -> System.exit(0));
	}

	// using strategy
	//	sets the next level activator 
	public void startNextLevel() {
		switch (levelCount) {
			case 0:
				levelActivator = new Level1(this);
				break;
			case 1:
				levelActivator = new Level2(this);
				break;
			case 2:
				levelActivator = new Level3(this);
				break;
			case 3:
				levelActivator = new Level4(this);
				break;
			case 4:
				levelActivator = new Level5(this);
				break;
			default:
				setGameWon(true);
				gameOver();
				break;
		}

		// incrementing for next level if all enemies killed
		levelCount++;
	}

	// assigning listeners to subject (observerStrategy)
		// each enemy in enemyComposite added
	public void assignEnemyListeners() {
		for (var r: enemyComposite.getRows()) {
			for (var c: r) {
				var enemy = (Enemy) c;
				shooter.addListener(enemy); 
			}
		}
	}

	public void beginLevel() {
		levelActivator.setLevelSettings();
		levelActivator.startLevel();

		// assigning enemy listeners b/c new ones made each level
		assignEnemyListeners();
	}

	// ends the game, passed if user won or lost
	public static void setGameWon(boolean gameWon) {
		GameBoard.gameWon = gameWon;
		GameBoard.isGameOver = true;
	}

	public void gameOver() {;
		startButton.setText("Play Again");
		timer.stop();
	}

	public static void increaseScore(ScoreCategory pointCategory) {
		switch (pointCategory) {
			case ENEMY_KILL:
				score += 10;
				break;
			case BULLET_KILL:
				score += 1;
				break;
			case POWERUP_GET:
				score += 10;
				break;
		}
	}

	public MyCanvas getCanvas() {
		return canvas;
	}

	public TimerListener getTimerListener() {
		return timerListener;
	}

	public Timer getTimer() {
		return timer;
	}
	
	public Shooter getShooter() {
		return shooter;
	}

	public EnemyComposite getEnemyComposite() {
		return enemyComposite;
	}

	public PowerBuilderDirector getPowerBuilderDirector() {
		return powerBuilderDirector;
	}
	
	public PowerUpMeter getPowerUpMeter() {
		return powerUpMeter;
	}

	public TextDraw getScoreText() {
		return scoreText;
	}

	public TextDraw getMeterText() {
		return meterText;
	}

}
