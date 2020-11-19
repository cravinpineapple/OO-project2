package model.builderStrategy;

public abstract class PowerBuilder {

	Power power;

	public PowerBuilder() {

	}

	public void createBomb() {
		power = new Power();
	}

	public Power getPower() {
		return power;
	}

	public void setPosition(int x, int y) {
		power.setPosition(x, y);
	}

	public abstract void buildFallSpeed();
	public abstract void buildColor();
	public abstract void buildSize();
	public abstract void buildStrategy();
}
