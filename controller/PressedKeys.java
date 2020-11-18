package controller;

public class PressedKeys {

	public enum Keys {
		LEFT, RIGHT, SPACE
	}

	public static boolean isLeftPressed = false;
	public static boolean isRightPressed = false;
	public static boolean isSpacePressed = false;
	

	public PressedKeys() {

	}

	public static void keyPressed(Keys key) {
		 switch (key) {
			 case LEFT:
				 isLeftPressed = true;
				 break;
			case RIGHT:
				 isRightPressed = true;
				 break;
			case SPACE:
				 isSpacePressed	= true;
				 break;
		 }
	}

	public static void keyReleased(Keys key) {
		switch (key) {
			case LEFT:
				isLeftPressed = false;
				break;
		   case RIGHT:
				isRightPressed = false;
				break;
		   case SPACE:
				isSpacePressed	= false;
				break;
		}
	}
	
}
