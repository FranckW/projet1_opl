package view;

import java.awt.Graphics;
import javax.swing.JPanel;

import model.Model;
import model.Snake;

/** Class representing the grid's panel */
public class GridPanel extends JPanel {
	
	// Eclipse told me to do it... :)
	private static final long serialVersionUID = 1L;

	/**
	 * paint the components of the panel
	 * @param g the Graphics object that will draw the components
	 */
	protected void paintComponent(Graphics g) {
		// we draw the model, the pause message if the game is paused, and the game over message if the game is over
		super.paintComponent(g);
		Model.THE_MODEL.drawModel(g);
		if (!View.THE_VIEW.isRunning())
			Model.THE_MODEL.drawPause(g);
		if (Snake.THE_SNAKE.isDead())
			Model.THE_MODEL.drawGameOver(g);
	}

}
