package controller;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import view.GameBoard;

public class KeyController implements KeyListener {

	//private GameBoard gameBoard;

	public KeyController(GameBoard gameBoard) {
		//this.gameBoard = gameBoard;
	}

	@Override
	public void keyPressed(KeyEvent e) {
		int keyCode = e.getKeyCode();

		switch (keyCode) {
			case KeyEvent.VK_LEFT:
				PressedKeys.keyPressed(PressedKeys.Keys.LEFT);
				break;
			case KeyEvent.VK_RIGHT:
				PressedKeys.keyPressed(PressedKeys.Keys.RIGHT);
				break;
			case KeyEvent.VK_SPACE:
				PressedKeys.keyPressed(PressedKeys.Keys.SPACE);
				break;
		}
	}

	@Override
	public void keyTyped(KeyEvent e) { }

	@Override
	public void keyReleased(KeyEvent e) {
		int keyCode = e.getKeyCode();

		switch (keyCode) {
			case KeyEvent.VK_LEFT:
				PressedKeys.keyReleased(PressedKeys.Keys.LEFT);
				break;
			case KeyEvent.VK_RIGHT:
				PressedKeys.keyReleased(PressedKeys.Keys.RIGHT);
				break;
			case KeyEvent.VK_SPACE:
				PressedKeys.keyReleased(PressedKeys.Keys.SPACE);
				break;
		}
	}
	
}
