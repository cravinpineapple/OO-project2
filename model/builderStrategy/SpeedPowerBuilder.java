package model.builderStrategy;

import java.awt.Color;

// provides temporary speed boost
public class SpeedPowerBuilder extends PowerBuilder {

	@Override
	public void buildFallSpeed() {
		power.setFallSpeed(5);
	}

	@Override
	public void buildColor() {
		power.setColor(Color.yellow);
	}

	@Override
	public void buildSize() {
		power.setSize(10, 5);
	}

	@Override
	public void buildPowerType() {
		power.setPowerType(Power.PowerType.SPEED);
	}

	
}
