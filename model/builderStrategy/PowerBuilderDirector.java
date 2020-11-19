package model.builderStrategy;

import view.GameBoard;

public class PowerBuilderDirector {
	
	private PowerBuilder powerBuilder;
	private GameBoard gameBoard;

	public PowerBuilderDirector(GameBoard gameBoard) {
		this.gameBoard = gameBoard;
	}

	public Power getPower() {
		return powerBuilder.getPower();
	}

	public void setPowerBuilder(PowerBuilder powerBuilder) {
		this.powerBuilder = powerBuilder;
	}

	// x and y passed for spawn position
	public void createPower(int x, int y) {
		powerBuilder.createBomb();
		powerBuilder.buildFallSpeed();
		powerBuilder.buildSize();
		powerBuilder.buildStrategy();
		powerBuilder.buildColor();
		powerBuilder.setPosition(x, y);
	}

}
