package model;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.Iterator;
import java.util.LinkedList;

/** Singleton-class representing the snake (part of the model in the MVC design pattern) */
public class Snake {

	// the rings that compose the snake
	private LinkedList<Ring> theRings;
	
	// the direction where the snake goes
	private Direction direction;
	
	// tells us if the snake is dead or not
	private boolean dead;
	
	// the score of the snake
	private int score;
	
	/** the snake-singleton */
	public static final Snake THE_SNAKE = new Snake();
	
	// private constructor assuring us that we can only have one instance of the class Snake
	private Snake() {
		// an alive snake, with 3 rings and moving to the east, is created
		this.theRings = new LinkedList<Ring>();
		this.theRings.addFirst(new Ring(3,15));
		this.theRings.addFirst(new Ring(4,15));
		this.theRings.addFirst(new Ring(5,15));
		this.direction = Direction.EAST;
		this.dead = false;
		this.score = 0;
	}
	
	/**
	 * changes the direction of the snake
	 * @param d the new direction
	 */
	public void changeDirection(Direction d) {
		// regardless of its position, the snake can only move in two new directions
		// it can't move in the opposite direction and he is already moving in its actual position
		if (this.direction == Direction.NORTH || this.direction == Direction.SOUTH) {
			switch (d) {
				case WEST : this.direction = Direction.WEST; break;
				case EAST : this.direction = Direction.EAST; break;
				default : break; // it moves in the same direction as it was before entering this method
			}
		}
		else {
			switch (d) {
				case NORTH : this.direction = Direction.NORTH; break;
				case SOUTH : this.direction = Direction.SOUTH; break;
				default : break; // it moves in the same direction as it was before entering this method
			}
		}
	}
	
	/**
	 * returns <code> true </code> if the snake occupy the cell <code> c </code>, <code> false <code> if not
	 * @param c the cell in question
	 * @return <code> true </code> if the snake occupy the cell <code> c </code>, <code> false <code> if not
	 */
	protected boolean occupyCell(Cell c) {
		boolean cellOccupied = false;
		// we browse through the snake's rings to check if one of them is in the cell c
		Iterator<Ring> it = this.theRings.iterator();
		while (it.hasNext()) {
			Ring r = it.next();
			// if a ring of the snake is in the cell c, then c is occupied by the snake
			if (r.getContentCell().equals(c))
				cellOccupied = true;
		}
		return cellOccupied;
	}
	
	/**
	 * returns the next cell that will be reached by the snake, following its direction
	 * @return the next cell that will be reached by the snake, following its direction
	 */
	protected Cell getNextCell() {
		// the next cell is obtained with the help of the position of the snake's head and the direction of the snake
		Ring snakeHead = this.theRings.getFirst();
		int snakeHeadAbscissa = snakeHead.getContentAbscissa();
		int snakeHeadOrdinate = snakeHead.getContentOrdinate();
		switch (this.direction) {
			case NORTH : return new Cell(snakeHeadAbscissa,snakeHeadOrdinate - 1);
			case SOUTH : return new Cell(snakeHeadAbscissa,snakeHeadOrdinate + 1);
			case EAST : return new Cell(snakeHeadAbscissa + 1,snakeHeadOrdinate);
			default : return new Cell(snakeHeadAbscissa - 1,snakeHeadOrdinate);
		}
	}
	
	/**
	 * @return <code> true </code> if the snake can move, <code> false </code> otherwise
	 */
	public boolean canMove() {
		// the snake can move in the next cell if this cell is valid and not occupied by the snake itself
		Cell c = this.getNextCell();
		return (c.isAValidCell() && !this.occupyCell(c));
	}
	
	/**
	 *the snake moves
	 */
	protected void move() {
		// moving the snake equals to make his tail the new head (see "plan.jpg")
		Ring newSnakeHead = this.theRings.getLast();
		newSnakeHead.setContentPosition(this.getNextCell().getCellAbscissa(),this.getNextCell().getCellOrdinate());
		this.theRings.addFirst(newSnakeHead);
		this.theRings.removeLast();
	}
	
	/**
	 * the snake dies
	 */
	protected void die() {
		this.dead = true;
	}
	
	/**
	 * @return <code> true </code> if the snake is dead, <code> false </code> otherwise
	 */
	public boolean isDead() {
		return (this.dead == true);
	}
	
	/**
	 * add a ring to the snake (the ring becomes the snake's head)
	 */
	private void addRing() {
		// the position of the new ring is the position of the next cell
		Ring newSnakeHead = new Ring(this.getNextCell().getCellAbscissa(),this.getNextCell().getCellOrdinate());
		this.theRings.addFirst(newSnakeHead);
	}
	
	/**
	 * returns <code> true </code> if the snake can eat the fruit, <code> false </code> if not
	 * @param f the fruit
	 * @return <code> true </code> if the snake can eat the fruit, <code> false </code> if not
	 */
	protected boolean canEat(Fruit f) {
		// the fruit can be eaten if its in the next cell that the snake will pass into
		return (f.getContentCell().equals(this.getNextCell()));
	}
	
	/**
	 * eats the fruit
	 * @param f the fruit
	 */
	protected void eat(Fruit f) {
		// a ring is added and become the head of the snake, while the fruit changes its position
		this.addRing();
		this.updateScore();
		f.newFruit();
	}
	
	/**
	 * @return the score of the snake
	 */
	public int getScore() {
		return this.score;
	}
	
	/**
	 * updates the score
	 */
	private void updateScore() {
		this.score += 9;
	}
	
	/**
	 * draw the snake
	 * @param g the Graphics object that will draw the snake
	 */
	protected void drawSnake(Graphics g) {
		// drawing the snake equals to draw all of its rings
		Iterator<Ring> it = this.theRings.iterator();
		while (it.hasNext()) {
			it.next().drawContent(g);
		}
		// we draw a point on the head to help the user to identify it (he won't be lost if the game is paused)
		g.setColor(Color.WHITE);
		g.setFont(new Font(Font.SANS_SERIF,Font.BOLD,15));
		g.drawString(".",this.theRings.peekFirst().getContentPixelsAbscissa() + 8,this.theRings.peekFirst().getContentPixelsOrdinate() + 8);
	}
	
}
