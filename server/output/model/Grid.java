

package model;


public class Grid {
    public static model.Cell[][] theCells;

    public static final int GRID_SIZE = 450;

    public static final int NUMBER_OF_CELLS = (model.Grid.GRID_SIZE) / (model.Cell.CELL_SIZE);

    @java.lang.SuppressWarnings(value = "unused")
    private static final model.Grid THE_GRID = new model.Grid();

    private Grid() {
        model.Grid.theCells = new model.Cell[model.Grid.GRID_SIZE][model.Grid.GRID_SIZE];
        for (int i = 0; i < (model.Grid.GRID_SIZE); i++) {
            for (int j = 0; j < (model.Grid.GRID_SIZE); j++) {
                model.Grid.theCells[i][j] = new model.Cell(i, j);
            }
        }
    }
}

