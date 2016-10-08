

package model;


public class Cell {
    private int cellAbscissa;

    private int cellOrdinate;

    @java.lang.SuppressWarnings(value = "unused")
    private model.Content content;

    protected static final int CELL_SIZE = 15;

    protected Cell(int x, int y) {
        model.Cell.this.cellAbscissa = x;
        model.Cell.this.cellOrdinate = y;
        model.Cell.this.content = null;
    }

    protected void setContent(model.Content c) {
        model.Cell.this.content = c;
    }

    protected int getCellAbscissa() {
        return model.Cell.this.cellAbscissa;
    }

    protected int getCellOrdinate() {
        return model.Cell.this.cellOrdinate;
    }

    protected int getCellPixelsAbscissa() {
        return (model.Cell.this.cellAbscissa) * (model.Cell.CELL_SIZE);
    }

    protected int getCellPixelsOrdinate() {
        return (model.Cell.this.cellOrdinate) * (model.Cell.CELL_SIZE);
    }

    protected boolean isAValidCell() {
        return (((((model.Cell.this.cellAbscissa) >= 0) && ((model.Cell.this.cellAbscissa) < (model.Grid.NUMBER_OF_CELLS))) && ((model.Cell.this.cellOrdinate) < (model.Grid.NUMBER_OF_CELLS))) && ((model.Cell.this.cellOrdinate) >= 0)) && ((model.Cell.this.cellOrdinate) < (model.Grid.NUMBER_OF_CELLS));
    }

    public boolean equals(java.lang.Object o) {
        if (o instanceof model.Cell) {
            model.Cell c = ((model.Cell) (o));
            return ((c.getCellAbscissa()) == (model.Cell.this.getCellAbscissa())) && ((c.getCellOrdinate()) == (model.Cell.this.getCellOrdinate()));
        } else {
            return false;
        }
    }
}

