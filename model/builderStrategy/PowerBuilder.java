package model.builderStrategy;

import model.Power;

// builder in builder strategy
public abstract class PowerBuilder {

	// power we are creating
	Power power;

	public PowerBuilder() {

	}

	public void createPower() {
		power = new Power();
	}

	public Power getPower() {
		return power;
	}

	public void setPosition(int x, int y) {
		power.setPosition(x, y);
	}

	// must override in child classes
	public abstract void buildFallSpeed();
	public abstract void buildColor();
	public abstract void buildSize();
	public abstract void buildPowerType();
}
