package model.builderStrategy;

import java.awt.Color;

public class DoubleBulletsPowerBuilder extends PowerBuilder {

	@Override
	public void buildFallSpeed() {
		power.setFallSpeed(5);
	}

	@Override
	public void buildColor() {
		power.setColor(Color.red);
	}

	@Override
	public void buildSize() {
		power.setSize(10, 5);
	}

	@Override
	public void buildStrategy() {
		
	}
	
}
