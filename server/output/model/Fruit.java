

package model;


public class Fruit extends model.Content {
    private static final java.util.Random alea = new java.util.Random();

    protected static model.Fruit THE_FRUIT = new model.Fruit(model.Fruit.alea.nextInt(model.Grid.NUMBER_OF_CELLS), model.Fruit.alea.nextInt(model.Grid.NUMBER_OF_CELLS));

    private Fruit(int x, int y) {
        super(x, y);
    }

    protected void newFruit() {
        model.Cell c = null;
        do {
            c = new model.Cell(model.Fruit.alea.nextInt(model.Grid.NUMBER_OF_CELLS), model.Fruit.alea.nextInt(model.Grid.NUMBER_OF_CELLS));
        } while (model.Snake.THE_SNAKE.occupyCell(c) );
        model.Fruit.this.setContentPosition(c.getCellAbscissa(), c.getCellOrdinate());
        model.observer.Event e = new model.observer.Event(model.Fruit.this);
        model.Model.THE_MODEL.notifyObservers(e);
    }

    @java.lang.Override
    protected void drawContent(java.awt.Graphics g) {
        g.setColor(java.awt.Color.RED);
        g.drawOval(model.Fruit.this.getContentPixelsAbscissa(), model.Fruit.this.getContentPixelsOrdinate(), ((model.Cell.CELL_SIZE) - 2), ((model.Cell.CELL_SIZE) - 2));
        g.fillOval(model.Fruit.this.getContentPixelsAbscissa(), model.Fruit.this.getContentPixelsOrdinate(), ((model.Cell.CELL_SIZE) - 2), ((model.Cell.CELL_SIZE) - 2));
    }
}

