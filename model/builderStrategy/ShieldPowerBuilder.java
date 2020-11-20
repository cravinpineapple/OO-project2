package model.builderStrategy;

import java.awt.Color;

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
		power.setSize(10, 5);
	}

	@Override
	public void buildPowerType() {
		power.setPowerType(Power.PowerType.SHIELD);
	}

}
