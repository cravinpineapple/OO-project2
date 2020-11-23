package model.builderStrategy;

import java.awt.Color;
import model.Power;

// gives extra shooter that flies near ship
public class ExtraShooterPowerBuilder extends PowerBuilder {

	@Override
	public void buildFallSpeed() {
		power.setFallSpeed(10);
	}

	@Override
	public void buildColor() {
		power.setColor(Color.blue);
	}

	@Override
	public void buildSize() {
		power.setSize(10, 5);
	}

	@Override
	public void buildPowerType() {
		power.setPowerType(Power.PowerType.EXTRA_SHOOTER);
	}
	
}
