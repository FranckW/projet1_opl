package model;

/** Singleton-class representing the Grid (part of the model in MVC design pattern) */
public class Grid {

	/** the cells of the grid */
	public static Cell[][] theCells;
	
	/** the size of the grid in pixels */
	public static final int GRID_SIZE = 450;
	
	/** the number of cells of the grid */
	public static final int NUMBER_OF_CELLS = Grid.GRID_SIZE / Cell.CELL_SIZE;
	
	/** the grid-singleton */
	@SuppressWarnings("unused")
	private static final Grid THE_GRID = new Grid();
	
	// private constructor assuring us that we can only have one instance of the class Grid
	private Grid() {
		Grid.theCells = new Cell[Grid.GRID_SIZE][Grid.GRID_SIZE];
		for (int i = 0 ; i < GRID_SIZE ; i++) {
			for (int j = 0 ; j < GRID_SIZE ; j++) {
				Grid.theCells[i][j] = new Cell(i,j);
			}
		}
	}

}
