

package model;


public class Model implements model.observer.Observable {
    private java.util.List<model.observer.Observer> observers;

    public static final model.Model THE_MODEL = new model.Model();

    private Model() {
        model.Model.this.observers = new java.util.LinkedList<model.observer.Observer>();
        model.Model.this.addObserver(view.View.THE_VIEW);
    }

    @java.lang.Override
    public void addObserver(model.observer.Observer obs) {
        model.Model.this.observers.add(obs);
    }

    @java.lang.Override
    public void removeObserver(model.observer.Observer obs) {
        model.Model.this.observers.remove(obs);
    }

    @java.lang.Override
    public void notifyObservers(model.observer.Event e) {
        for (model.observer.Observer o : model.Model.this.observers) {
            o.update(e);
        }
    }

    public void behave() {
        if (model.Snake.THE_SNAKE.canEat(model.Fruit.THE_FRUIT)) {
            model.Snake.THE_SNAKE.eat(model.Fruit.THE_FRUIT);
            model.Fruit.THE_FRUIT.newFruit();
        } else {
            if (model.Snake.THE_SNAKE.canMove())
                model.Snake.THE_SNAKE.move();
            else
                model.Snake.THE_SNAKE.die();
            
        }
        model.observer.Event e = new model.observer.Event(model.Model.this);
        model.Model.THE_MODEL.notifyObservers(e);
    }

    public void drawModel(java.awt.Graphics g) {
        java.awt.Graphics2D g2 = ((java.awt.Graphics2D) (g));
        g2.setRenderingHint(java.awt.RenderingHints.KEY_ANTIALIASING, java.awt.RenderingHints.VALUE_ANTIALIAS_ON);
        model.Snake.THE_SNAKE.drawSnake(g);
        model.Fruit.THE_FRUIT.drawContent(g);
    }

    public void drawGameOver(java.awt.Graphics g) {
        g.setColor(java.awt.Color.BLACK);
        java.lang.String endMessage = "GAME OVER";
        g.setFont(new java.awt.Font(java.awt.Font.SANS_SERIF, java.awt.Font.BOLD, 50));
        java.awt.Graphics2D g2 = ((java.awt.Graphics2D) (g));
        g2.setRenderingHint(java.awt.RenderingHints.KEY_ANTIALIASING, java.awt.RenderingHints.VALUE_ANTIALIAS_ON);
        java.awt.FontMetrics fm = g.getFontMetrics();
        int x = ((g.getClipBounds().width) - (fm.stringWidth(endMessage))) / 2;
        int y = ((g.getClipBounds().height) / 2) + (fm.getMaxDescent());
        g.drawString(endMessage, x, y);
    }

    public void drawPause(java.awt.Graphics g) {
        g.setColor(java.awt.Color.BLACK);
        java.lang.String pauseMessage = "JEU EN PAUSE";
        g.setFont(new java.awt.Font(java.awt.Font.SANS_SERIF, java.awt.Font.BOLD, 50));
        java.awt.Graphics2D g2 = ((java.awt.Graphics2D) (g));
        g2.setRenderingHint(java.awt.RenderingHints.KEY_ANTIALIASING, java.awt.RenderingHints.VALUE_ANTIALIAS_ON);
        java.awt.FontMetrics fm = g.getFontMetrics();
        int x = ((g.getClipBounds().width) - (fm.stringWidth(pauseMessage))) / 2;
        int y = ((g.getClipBounds().height) / 2) + (fm.getMaxDescent());
        g.drawString(pauseMessage, x, y);
    }
}

