package model;

import java.awt.Graphics;

/** Abstract class representing the content of a cell (part of the model in the MVC design pattern) */
public abstract class Content {
	
	// the cell of the content
	private Cell contentCell;
	
	/**
	 * construct a content at the position (<code> x </code>,<code> y </code>)
	 * @param x the abscissa of the cell containing the content
	 * @param y the ordinate of the cell containing the content
	 */
	protected Content(int x, int y) {
		this.contentCell = Grid.theCells[x][y];
	}
	
	/**
	 * @return the cell of the content
	 */
	protected Cell getContentCell() {
		return this.contentCell;
	}
	
	/**
	 * @return the abscissa of the cell containing the content
	 */
	protected int getContentAbscissa() {
		return this.contentCell.getCellAbscissa();
	}
	
	/**
	 * @return the abscissa of the cell containing the content
	 */
	protected int getContentOrdinate() {
		return this.contentCell.getCellOrdinate();
	}
	
	/**
	 * sets the position of the content
	 * @param newAbscissa the abscissa of the new cell that will contains the content
	 * @param newOrdinate the ordinate of the new cell that will contains the content
	 */
	protected void setContentPosition(int newAbscissa, int newOrdinate) {
		// we empty the old cell, set the position of our content and set the content of the new cell
		this.contentCell.setContent(null);
		this.contentCell = Grid.theCells[newAbscissa][newOrdinate];
		this.contentCell.setContent(this);
	}
	
	/**
	 * @return the abscissa, in pixels, of the cell containing the content
	 */
	protected int getContentPixelsAbscissa() {
		return (this.contentCell.getCellPixelsAbscissa());
	}
	
	/**
	 * @return the ordinate, in pixels, of the cell containing the content
	 */
	protected int getContentPixelsOrdinate() {
		return (this.contentCell.getCellPixelsOrdinate());
	}
	
	/**
	 * draw the content
	 * @param g the Graphics object that will draw the content
	 */
	protected abstract void drawContent(Graphics g);
	
	/**
	 * compare the object in parameter to this content
	 * @param o the object to be compared to this content
	 * @return returns <code> true </code> if <code> o </code> is a content at the same position as this content, <code> false </code> if not
	 */
	public boolean equals(Object o) {
		if (o instanceof Content) {
			// if o is an instance of the class Content, we cast it and check if this and o have the same coordinates
			Content c = (Content) o;
			return ((c.getContentAbscissa() == this.getContentAbscissa()) && (c.getContentOrdinate() == this.getContentOrdinate()));
		}
		// false whether the object o is not an instance of the class Cell
		else {
			return false;
		}
	}

}
