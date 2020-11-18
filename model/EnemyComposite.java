package model;

import java.awt.Graphics2D;
import java.awt.Color;

import java.util.ArrayList;
import java.util.Random;

import view.GameBoard;
import view.GameBoard.ScoreCategory;

public class EnemyComposite extends GameElement {

	public static final int NROWS = 2;
	public static final int NCOLS = 1;
	public static final int ENEMY_SIZE = 20;
	public static final int UNIT_MOVE = 5;

	private ArrayList<ArrayList<GameElement>> rows;
	private ArrayList<GameElement> bombs;
	private boolean movingToRight = true;

	private Random random = new Random();
	
	public EnemyComposite() {
		rows = new ArrayList<>();
		bombs = new ArrayList<>();

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
					}
				}
			}

			row.removeAll(removeEnemies);
		}

		shooter.getWeapons().removeAll(removeBullets);

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
		}

		shooter.getWeapons().removeAll(removeBullets);
		bombs.removeAll(removeBombs);

		// enemy vs bottom
		if (reachedBottom())
			GameBoard.setGameWon(false);
			
	}
	
}
