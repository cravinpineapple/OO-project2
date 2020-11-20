package model;

import java.awt.Graphics2D;
import java.awt.Color;

import java.util.ArrayList;
import java.util.Random;

import model.builderStrategy.DoubleBulletsPowerBuilder;
import model.builderStrategy.ExtraShooterPowerBuilder;
import model.builderStrategy.Power;
import model.builderStrategy.PowerBuilderDirector;
import model.builderStrategy.ShieldPowerBuilder;
import model.builderStrategy.SpeedPowerBuilder;
import view.GameBoard;
import view.GameBoard.ScoreCategory;

public class EnemyComposite extends GameElement {

	public static final int NROWS = 2;
	public static final int NCOLS = 10;
	public static final int ENEMY_SIZE = 20;
	public static final int UNIT_MOVE = 5;

	private ArrayList<ArrayList<GameElement>> rows;
	private ArrayList<GameElement> bombs;
	private ArrayList<GameElement> powers;
	private boolean movingToRight = true;

	private Random random = new Random();
	private GameBoard gameBoard;
	
	public EnemyComposite() {
		rows = new ArrayList<>();
		bombs = new ArrayList<>();
		powers = new ArrayList<>();

		for (int r = 0; r < NROWS; r++) {
			var oneRow = new ArrayList<GameElement>();
			rows.add(oneRow);
			for (int c = 0; c < NCOLS; c++) {
				oneRow.add(new Enemy(
					c * ENEMY_SIZE * 2, r * ENEMY_SIZE * 2, ENEMY_SIZE, Color.yellow, true));
			}
		}
	}

	@Override
	public void render(Graphics2D g2) {
		// renders composite enemies
		for (var r: rows) {
			for (var e: r) {
				e.render(g2);
			}
		}

		// renders bombs
		for (var b: bombs) {
			b.render(g2);
		}

		// renders powerups
		for (var p: powers) {
			p.render(g2);
		}
	}

	@Override
	public void animate() {
		int tempEnemySize = ENEMY_SIZE;
		int dx = UNIT_MOVE;
		int dy = 0;
		if (movingToRight) {
			// if enemy is colliding with right wall, start moving left and lower composite by enemy size
			if (rightEnd() >= GameBoard.WIDTH) {
				dx = -dx;
				dy = tempEnemySize; //ENEMY_SIZE;
				movingToRight = false;
			}
		}
		else {
			dx = -dx;
			// if enemy is colliding with left wall, start moving left and lower composite by enemy size
			if (leftEnd() <= 0) {
				dx = -dx;
				dy = tempEnemySize; //ENEMY_SIZE;
				movingToRight = true;
			}
		}

		// update x loc and y loc
		for (var row: rows) {
			for (var e: row) {
				e.x += dx;
				e.y += dy;
			}
		}

		// animate bombs
		for (var b: bombs)
			b.animate();

		for (var p: powers)
			p.animate();
	}

	private int rightEnd() {
		int xEnd = -100;
		for (var row: rows) {
			if (row.size() == 0)
				continue;
			
			int x = row.get(row.size() - 1).x + ENEMY_SIZE;
			if (x > xEnd)
				xEnd = x;
		}

		return xEnd;
	}

	private int bottomEnd() {
		int yEnd = -100;
		for (var row: rows) {
			if (row.size() == 0)
				continue;
			
			int y = row.get(row.size() - 1).y + ENEMY_SIZE;
			if (y > yEnd)
				yEnd = y;
		}

		return yEnd;
	}

	private int leftEnd() {
		int xEnd = 9000;
		for (var row: rows) {
			if (row.size() == 0)
				continue;
			
			int x = row.get(0).x;
			if (x < xEnd)
				xEnd = x;
		}

		return xEnd;
	}

	public void dropBombs() {
		for (var row: rows) {
			for (var e: row) {
				if (random.nextFloat() < 0.1F) {
					bombs.add(new Bomb(e.x, e.y));
				}
			}
		}
	}

	public void removeBombsOutOfBound() {
		var remove = new ArrayList<GameElement>();

		// checks y position if out of bound and adds to remove array
		for (var b: bombs) {
			if (b.y >= GameBoard.HEIGHT)
				remove.add(b);
		}

		// removes bombs in remove array from bombs array
		bombs.removeAll(remove);
	}

	public boolean reachedBottom() {
		return bottomEnd() >= GameBoard.HEIGHT - ENEMY_SIZE;
	}

	public void checkAllEnemiesKilled() {
		boolean enemiesGone = true;
		for (var r: rows) {
			if (!r.isEmpty())
				enemiesGone = false;
		}

		if (enemiesGone)
			GameBoard.setGameWon(true);
	}

	// passes in cords for spawn location (at enemy death location)
	private void powerUpChance(int spawnX, int spawnY) {
		if (true) {
			PowerBuilderDirector pbDirector = gameBoard.getPowerBuilderDirector();
			float randomPowerUp = random.nextFloat();
			
			if (randomPowerUp < 0.1F)
				pbDirector.setPowerBuilder(new ExtraShooterPowerBuilder());
			else if (randomPowerUp < 0.4F)
				pbDirector.setPowerBuilder(new SpeedPowerBuilder());
			else if (randomPowerUp < 0.7F)
				pbDirector.setPowerBuilder(new DoubleBulletsPowerBuilder());
			else
				pbDirector.setPowerBuilder(new ShieldPowerBuilder());

			pbDirector.createPower(spawnX, spawnY);
			powers.add(pbDirector.getPower());
		} 
	}

	public void processCollision(Shooter shooter) {
		var removeBullets = new ArrayList<GameElement>();

		// bullets vs enemies
		for (var row: rows) {
			var removeEnemies = new ArrayList<GameElement>();
			for (var e: row) {
				for (var bullet: shooter.getWeapons()) {
					if (e.collideWith(bullet)) {
						removeEnemies.add(e);
						removeBullets.add(bullet);
						GameBoard.increaseScore(ScoreCategory.ENEMY_KILL);
						powerUpChance(bullet.x, bullet.y);
					}
				}

				if (shooter.getExtraShooter() != null) {
					for (var bullet: shooter.getExtraShooter().getWeapons()) {
						if (e.collideWith(bullet)) {
							removeEnemies.add(e);
							removeBullets.add(bullet);
							GameBoard.increaseScore(ScoreCategory.ENEMY_KILL);
							powerUpChance(bullet.x, bullet.y);
						}
					}
				}
			}

			row.removeAll(removeEnemies);
		}

		shooter.getWeapons().removeAll(removeBullets);
		if (shooter.getExtraShooter() != null)
			shooter.getExtraShooter().getWeapons().removeAll(removeBullets);

		// bullets vs bombs
		var removeBombs = new ArrayList<GameElement>();
		removeBullets.clear();

		for (var b: bombs) {
			for (var bullet: shooter.getWeapons()) {
				if (b.collideWith(bullet)) {
					removeBombs.add(b);
					removeBullets.add(bullet);
					GameBoard.increaseScore(ScoreCategory.BULLET_KILL);
				}
			}

			if (shooter.getExtraShooter() != null) {
				for (var bullet: shooter.getExtraShooter().getWeapons()) {
					if (b.collideWith(bullet)) {
						removeBullets.add(bullet);
						removeBombs.add(b);
						GameBoard.increaseScore(ScoreCategory.BULLET_KILL);
					}
				}
			}
		}

		shooter.getWeapons().removeAll(removeBullets);
		if (shooter.getExtraShooter() != null)
			shooter.getExtraShooter().getWeapons().removeAll(removeBullets);
		bombs.removeAll(removeBombs);

		// bombs vs shield
		removeBombs.clear();

		if (shooter.getShield() != null) {
			for (var b: bombs) {
				if (b.collideWith(shooter.getShield()))
					removeBombs.add(b);
			}
		}

		bombs.removeAll(removeBombs);

		// bombs vs shooter
		var removeComponents = new ArrayList<GameElement>();
		removeBombs.clear();

		// for every bomb, check if collide with any of player components
		for (var b: bombs) {
			// getting player components
			for (var c: shooter.getComponents()) {
				if (b.collideWith(c)) {
					removeBombs.add(b);
					removeComponents.add(c);
				}
			}
		}

		bombs.removeAll(removeBombs);
		shooter.getComponents().removeAll(removeComponents);

		// bombs vs extra shooter
		removeBombs.clear();

		if (shooter.getExtraShooter() != null) {
			for (var b: bombs) {
				if (b.collideWith(shooter.getExtraShooter().getComponents().get(0))) {
					removeBombs.add(b);
					shooter.deactivateExtraShooter();
					break;
				}
			}
		}

		bombs.removeAll(removeBombs);

		// powers vs shooter
		var removePowers = new ArrayList<GameElement>(); // to remove power ups once collided with shooter

		for (var p: powers) {
			for (var c: shooter.getComponents()) {
				if (p.collideWith(c)) {
					removePowers.add(p);
					// type cast GameElemnt p to Power p so we can access activatePower(shooter)
					Power power = (Power) p;
					power.activatePower(shooter);
				}
				else if (p.y >= GameBoard.HEIGHT)
					removePowers.add(p);
			}
		}
		
		powers.removeAll(removePowers);


		// enemy vs bottom
		if (reachedBottom())
			GameBoard.setGameWon(false);
	}

	public void setGameBoard(GameBoard gameBoard) {
		this.gameBoard = gameBoard;
	}
	
}