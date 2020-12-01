package model.builderStrategy;

import java.awt.Color;

import model.Power;

// provides temporary shield from bombs
public class ShieldPowerBuilder extends PowerBuilder {
	
	@Override
	public void buildFallSpeed() {
		power.setFallSpeed(10);
	}

	@Override
	public void buildColor() {
		power.setColor(Color.green);
	}

	@Override
	public void buildSize() {
		power.setSize(Power.WIDTH, Power.HEIGHT);
	}

	@Override
	public void buildPowerType() {
		power.setPowerType(Power.PowerType.SHIELD);
	}

}
