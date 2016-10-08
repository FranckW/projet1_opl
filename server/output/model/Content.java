

package model;


public abstract class Content {
    private model.Cell contentCell;

    protected Content(int x, int y) {
        model.Content.this.contentCell = model.Grid.theCells[x][y];
    }

    protected model.Cell getContentCell() {
        return model.Content.this.contentCell;
    }

    protected int getContentAbscissa() {
        return model.Content.this.contentCell.getCellAbscissa();
    }

    protected int getContentOrdinate() {
        return model.Content.this.contentCell.getCellOrdinate();
    }

    protected void setContentPosition(int newAbscissa, int newOrdinate) {
        model.Content.this.contentCell.setContent(null);
        model.Content.this.contentCell = model.Grid.theCells[newAbscissa][newOrdinate];
        model.Content.this.contentCell.setContent(model.Content.this);
    }

    protected int getContentPixelsAbscissa() {
        return model.Content.this.contentCell.getCellPixelsAbscissa();
    }

    protected int getContentPixelsOrdinate() {
        return model.Content.this.contentCell.getCellPixelsOrdinate();
    }

    protected abstract void drawContent(java.awt.Graphics g);

    public boolean equals(java.lang.Object o) {
        if (o instanceof model.Content) {
            model.Content c = ((model.Content) (o));
            return ((c.getContentAbscissa()) == (model.Content.this.getContentAbscissa())) && ((c.getContentOrdinate()) == (model.Content.this.getContentOrdinate()));
        } else {
            return false;
        }
    }
}

