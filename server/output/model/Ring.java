

package model;


public class Ring extends model.Content {
    public Ring(int x, int y) {
        super(x, y);
    }

    protected void drawContent(java.awt.Graphics g) {
        g.setColor(new java.awt.Color(0, 100, 0));
        g.drawRoundRect(model.Ring.this.getContentPixelsAbscissa(), model.Ring.this.getContentPixelsOrdinate(), model.Cell.CELL_SIZE, model.Cell.CELL_SIZE, 10, 10);
        g.fillRoundRect(model.Ring.this.getContentPixelsAbscissa(), model.Ring.this.getContentPixelsOrdinate(), model.Cell.CELL_SIZE, model.Cell.CELL_SIZE, 10, 10);
    }
}

