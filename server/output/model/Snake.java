

package model;


public class Snake {
    private java.util.LinkedList<model.Ring> theRings;

    private model.Direction direction;

    private boolean dead;

    private int score;

    public static final model.Snake THE_SNAKE = new model.Snake();

    private Snake() {
        model.Snake.this.theRings = new java.util.LinkedList<model.Ring>();
        model.Snake.this.theRings.addFirst(new model.Ring(3, 15));
        model.Snake.this.theRings.addFirst(new model.Ring(4, 15));
        model.Snake.this.theRings.addFirst(new model.Ring(5, 15));
        model.Snake.this.direction = model.Direction.EAST;
        model.Snake.this.dead = false;
        model.Snake.this.score = 0;
    }

    public void changeDirection(model.Direction d) {
        if (((model.Snake.this.direction) == (model.Direction.NORTH)) || ((model.Snake.this.direction) == (model.Direction.SOUTH))) {
            switch (d) {
                case WEST :
                    model.Snake.this.direction = model.Direction.WEST;
                    break;
                case EAST :
                    model.Snake.this.direction = model.Direction.EAST;
                    break;
                default :
                    break;
            }
        } else {
            switch (d) {
                case NORTH :
                    model.Snake.this.direction = model.Direction.NORTH;
                    break;
                case SOUTH :
                    model.Snake.this.direction = model.Direction.SOUTH;
                    break;
                default :
                    break;
            }
        }
    }

    protected boolean occupyCell(model.Cell c) {
        boolean cellOccupied = false;
        java.util.Iterator<model.Ring> it = model.Snake.this.theRings.iterator();
        while (it.hasNext()) {
            model.Ring r = it.next();
            if (r.getContentCell().equals(c))
                cellOccupied = true;
            
        }
        return cellOccupied;
    }

    protected model.Cell getNextCell() {
        model.Ring snakeHead = model.Snake.this.theRings.getFirst();
        int snakeHeadAbscissa = snakeHead.getContentAbscissa();
        int snakeHeadOrdinate = snakeHead.getContentOrdinate();
        switch (model.Snake.this.direction) {
            case NORTH :
                return new model.Cell(snakeHeadAbscissa, (snakeHeadOrdinate - 1));
            case SOUTH :
                return new model.Cell(snakeHeadAbscissa, (snakeHeadOrdinate + 1));
            case EAST :
                return new model.Cell((snakeHeadAbscissa + 1), snakeHeadOrdinate);
            default :
                return new model.Cell((snakeHeadAbscissa - 1), snakeHeadOrdinate);
        }
    }

    public boolean canMove() {
        model.Cell c = model.Snake.this.getNextCell();
        return (c.isAValidCell()) && (!(model.Snake.this.occupyCell(c)));
    }

    protected void move() {
        model.Ring newSnakeHead = model.Snake.this.theRings.getLast();
        newSnakeHead.setContentPosition(model.Snake.this.getNextCell().getCellAbscissa(), model.Snake.this.getNextCell().getCellOrdinate());
        model.Snake.this.theRings.addFirst(newSnakeHead);
        model.Snake.this.theRings.removeLast();
    }

    protected void die() {
        model.Snake.this.dead = true;
    }

    public boolean isDead() {
        return (model.Snake.this.dead) == true;
    }

    private void addRing() {
        model.Ring newSnakeHead = new model.Ring(model.Snake.this.getNextCell().getCellAbscissa(), model.Snake.this.getNextCell().getCellOrdinate());
        model.Snake.this.theRings.addFirst(newSnakeHead);
    }

    protected boolean canEat(model.Fruit f) {
        return f.getContentCell().equals(model.Snake.this.getNextCell());
    }

    protected void eat(model.Fruit f) {
        model.Snake.this.addRing();
        model.Snake.this.updateScore();
        f.newFruit();
    }

    public int getScore() {
        return model.Snake.this.score;
    }

    private void updateScore() {
        model.Snake.this.score += 9;
    }

    protected void drawSnake(java.awt.Graphics g) {
        java.util.Iterator<model.Ring> it = model.Snake.this.theRings.iterator();
        while (it.hasNext()) {
            it.next().drawContent(g);
        }
        g.setColor(java.awt.Color.WHITE);
        g.setFont(new java.awt.Font(java.awt.Font.SANS_SERIF, java.awt.Font.BOLD, 15));
        g.drawString(".", ((model.Snake.this.theRings.peekFirst().getContentPixelsAbscissa()) + 8), ((model.Snake.this.theRings.peekFirst().getContentPixelsOrdinate()) + 8));
    }
}

