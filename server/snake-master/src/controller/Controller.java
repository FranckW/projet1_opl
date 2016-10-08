package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JOptionPane;
import view.View;

import model.Direction;
import model.Model;
import model.Snake;

/** Singleton-class representing the controller of the game, following the MVC design pattern */
public class Controller implements KeyListener, ActionListener {
	
	// the code of the pressed key
	private int pressedKeyCode;
	
	/** the controller-singleton */
	public static final Controller THE_CONTROLLER = new Controller();
	
	
	// private constructor of the controller (we are assured to have only one instance of this class)
	private Controller() {
		this.pressedKeyCode = KeyEvent.VK_RIGHT;
	}
	
	/**
	 * indicates what to do if a key is pressed
	 * @param e the event corresponding to a pressure on a key
	 */
	@Override
	public void keyPressed(KeyEvent e) {
		this.handlePressedKey(e.getKeyCode());
	}

	// changes the view or the model if a key is pressed
	// @param kc the code of the pressed key
	private void handlePressedKey(int kc) {
		// we update our attribute
		this.pressedKeyCode = kc;
		switch (this.pressedKeyCode) {
			// we change the direction of the Snake or pause the game, depending on the key pressed
			case KeyEvent.VK_UP :
				if ((!View.THE_VIEW.isRunning())) {
					View.THE_VIEW.restart();
				}
				Snake.THE_SNAKE.changeDirection(Direction.NORTH);
				break;
			case KeyEvent.VK_DOWN :
				if ((!View.THE_VIEW.isRunning())) {
					View.THE_VIEW.restart();
				}
				Snake.THE_SNAKE.changeDirection(Direction.SOUTH);
				break;
			case KeyEvent.VK_RIGHT :
				if ((!View.THE_VIEW.isRunning())) {
					View.THE_VIEW.restart();
				}
				Snake.THE_SNAKE.changeDirection(Direction.EAST);
				break;
			case KeyEvent.VK_LEFT :
				if ((!View.THE_VIEW.isRunning())) {
					View.THE_VIEW.restart();
				}
				Snake.THE_SNAKE.changeDirection(Direction.WEST);
				break;
			case KeyEvent.VK_SPACE :
				if (View.THE_VIEW.isRunning()) {
					View.THE_VIEW.stop();
					View.THE_VIEW.redrawContents();
				}
				break;
			default : break; // nothing has to be done if the user presses a key other than the ones below
		}
	}
	
	// controls the model and the view
	private void control() {
		// we handle the pressed key, tell the model to start its "behavior" and show the updated score (if the snake ate the fruit)
		this.handlePressedKey(this.pressedKeyCode);
		Model.THE_MODEL.behave();
		View.THE_VIEW.showScore();
	}

	// We don't need this method, it's here only because this class overrides the interface KeyListener
	@Override
	public void keyTyped(KeyEvent e) {}

	// We don't need this method, it's here only because this class overrides the interface KeyListener
	@Override
	public void keyReleased(KeyEvent e) {}

	/**
	 * actions to do at every delay of the view's timer*
	 * @param e the event corresponding to the action
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		// we invoke the control method if the snake is still alive, otherwise we stop the game and show a dialog message 2 seconds and a half later to inform the user about his score
		if (!Snake.THE_SNAKE.isDead()) {
			this.control();
		}
		else {
			View.THE_VIEW.stop();
			try {
				Thread.sleep(2500);
				View.THE_VIEW.dispose();
				JOptionPane.showMessageDialog(null,"Vous terminez la partie avec un score de "+Snake.THE_SNAKE.getScore(),"Fin du jeu",JOptionPane.INFORMATION_MESSAGE);	
			}
			catch (InterruptedException e1) {}
		}
	}

}
