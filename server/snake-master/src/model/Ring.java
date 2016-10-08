package model;

import java.awt.Color;
import java.awt.Graphics;

/** Class representing a ring of the snake (part of the model in the MVC design pattern) */
public class Ring extends Content {

	/**
	 * construct a ring at the position (<code> x </code>,<code> y </code>
	 * @param x the abscissa of the ring
	 * @param y the ordinate of the ring
	 */
	public Ring(int x, int y) {
		super(x,y);
	}
	
	/**
	 * @see model.Content#drawContent(Graphics)
	 */
	protected void drawContent(Graphics g) {
		// we draw and fill a dark green round rectangle
		g.setColor(new Color(0,100,0));
		g.drawRoundRect(this.getContentPixelsAbscissa(),this.getContentPixelsOrdinate(),Cell.CELL_SIZE,Cell.CELL_SIZE,10,10);
		g.fillRoundRect(this.getContentPixelsAbscissa(),this.getContentPixelsOrdinate(),Cell.CELL_SIZE,Cell.CELL_SIZE,10,10);
	}

}
