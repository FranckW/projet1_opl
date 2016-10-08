package model;

/** Class representing a cell (part of the model in the MVC design pattern) */
public class Cell {
	
	// the abscissa of the cell
	private int cellAbscissa;
	
	// the ordinate of the cell
	private int cellOrdinate;
	
	// the content of the cell
	@SuppressWarnings("unused")
	private Content content;
	
	/** the size of a cell in pixels */
	protected static final int CELL_SIZE = 15;
	
	/**
	 * construct an empty cell at the position (<code> x </code>,<code> y </code>)
	 * @param x the abscissa of the cell
	 * @param y the ordinate of the cell
	 */
	protected Cell(int x, int y) {
		this.cellAbscissa = x;
		this.cellOrdinate = y;
		this.content = null;
	}
	
	/**
	 * sets the content of the cell
	 * @param c the new content of the cell
	 */
	protected void setContent(Content c) {
		this.content = c;
	}
	
	/**
	 * @return the abscissa of the cell
	 */
	protected int getCellAbscissa() {
		return this.cellAbscissa;
	}
	
	/**
	 * @return the ordinate of the cell
	 */
	protected int getCellOrdinate() {
		return this.cellOrdinate;
	}
	
	/**
	 * @return the abscissa of the cell in pixels
	 */
	protected int getCellPixelsAbscissa() {
		// the cell's abscissa in pixels is obtained by multiplying the cell's abscissa and the size of the cell
		return (this.cellAbscissa * Cell.CELL_SIZE);
	}
	
	/**
	 * @return the ordinate of the cell in pixels
	 */
	protected int getCellPixelsOrdinate() {
		// the cell's ordinate in pixels is obtained by multiplying the cell's ordinate and the size of the cell
		return (this.cellOrdinate * Cell.CELL_SIZE);
	}
	
	/**
	 * @return <code> true </code> if this cell is a valid cell, <code> false </code> if not
	 */
	protected boolean isAValidCell() {
		// a cell is valid if its abscissa and its ordinate are between 0 (included) and the number of cells (excluded)
		return ((this.cellAbscissa >= 0) && (this.cellAbscissa < Grid.NUMBER_OF_CELLS) && (this.cellOrdinate < Grid.NUMBER_OF_CELLS) && (this.cellOrdinate >= 0) && (this.cellOrdinate < Grid.NUMBER_OF_CELLS));
	}
	
	/**
	 * compare the object in parameter to this cell
	 * @param o the object to be compared to this cell
	 * @return returns <code> true </code> if <code> o </code> is a cell at the same position as this cell, <code> false </code> if not
	 */
	public boolean equals(Object o) {
		if (o instanceof Cell) {
			// if o is an instance of the class Cell, we cast it and check if this and o have the same coordinates
			Cell c = (Cell) o;
			return ((c.getCellAbscissa() == this.getCellAbscissa()) && (c.getCellOrdinate() == this.getCellOrdinate()));
		}
		// false whether the object o is not an instance of the class Cell
		else {
			return false;
		}
	}

}
