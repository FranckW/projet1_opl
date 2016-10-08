package model;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;

import model.observer.Event;

/** Singleton-class representing the fruit (part of the model in the MVC design pattern) */
public class Fruit extends Content {
	
	// a random number generator
	private static final Random alea = new Random();
	
	/** the fruit-singleton */
	protected static Fruit THE_FRUIT = new Fruit(alea.nextInt(Grid.NUMBER_OF_CELLS),alea.nextInt(Grid.NUMBER_OF_CELLS));
	
	// private constructor assuring us that we can only have one instance of the class Fruit
	private Fruit(int x, int y) {
		super(x,y);
	}
	
	/**
	 * sets the position of the fruit (after it has been eaten by the snake)
	 */
	protected void newFruit() {
		Cell c = null;
		do {
			// we search for a new position for the fruit while its positions is occupied by a ring of the snake
			c = new Cell(alea.nextInt(Grid.NUMBER_OF_CELLS),alea.nextInt(Grid.NUMBER_OF_CELLS));
		} while (Snake.THE_SNAKE.occupyCell(c));
		// once we have a valid position, we fix it as the position of the fruit
		this.setContentPosition(c.getCellAbscissa(),c.getCellOrdinate());
		// we notify the observers (the view) that an event corresponding to a position change has been launched, and the view has to redraw its content
		Event e = new Event(this);
		Model.THE_MODEL.notifyObservers(e);
	}

	/**
	 * @see model.Content#drawContent(Graphics)
	 */
	@Override
	protected void drawContent(Graphics g) {
		// we draw and fill a red circle with a certain length
		g.setColor(Color.RED);
		g.drawOval(this.getContentPixelsAbscissa(),this.getContentPixelsOrdinate(),Cell.CELL_SIZE - 2,Cell.CELL_SIZE - 2);
		g.fillOval(this.getContentPixelsAbscissa(),this.getContentPixelsOrdinate(),Cell.CELL_SIZE - 2,Cell.CELL_SIZE - 2);
	}

}
