package model;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.LinkedList;
import java.util.List;

import view.View;

import model.observer.Event;
import model.observer.Observable;
import model.observer.Observer;

/** Singleton-class representing the model of the game (the observable in the Observer pattern and the model of the MVC design pattern) */ 
public class Model implements Observable {
	
	// the list of the observers of the model
	private List<Observer> observers;
	
	/** the model-singleton */
	public static final Model THE_MODEL = new Model();
	
	// private constructor assuring us that we can't only have one instance of the class Model
	private Model() {
		// we add the view to the list of the model's observers
		this.observers = new LinkedList<Observer>();
		this.addObserver(View.THE_VIEW);
	}
	
	/**
	 * @see model.observer.Observable#addObserver(Observer)
	 */
	@Override
	public void addObserver(Observer obs) {
		this.observers.add(obs);
	}

	/**
	 * @see model.observer.Observable#removeObserver(Observer)
	 */
	@Override
	public void removeObserver(Observer obs) {
		this.observers.remove(obs);
	}
	
	/**
	 * @see model.observer.Observable#notifyObservers(Event)
	 */
	@Override
	public void notifyObservers(Event e) {
		for (Observer o : this.observers) {
			o.update(e);
		}
	}
	
	/**
	 * the behavior of the model
	 */
	public void behave() {
		// we test first if the snake can eat the fruit (make it eat it if yes), otherwise we check if the snake can move or not
		// if the snake can't move, it mean that it has hit a wall or its own body, and died
		if (Snake.THE_SNAKE.canEat(Fruit.THE_FRUIT)) {
			Snake.THE_SNAKE.eat(Fruit.THE_FRUIT);
			Fruit.THE_FRUIT.newFruit();
		}
		else {
			if (Snake.THE_SNAKE.canMove())
				Snake.THE_SNAKE.move();
			else
				Snake.THE_SNAKE.die();
		}
		// we notify the view about this state change, it will update itself and redraw its contents
		Event e = new Event(this);
		Model.THE_MODEL.notifyObservers(e);
	}
	
	/**
	 * draw the model
	 * @param g the Graphics object that will draw the model
	 */
	public void drawModel(Graphics g) {
		// draw the model equals to draw the snake and the fruit
		Graphics2D g2 = (Graphics2D) g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
		Snake.THE_SNAKE.drawSnake(g);
		Fruit.THE_FRUIT.drawContent(g);
	}
	
	/**
	 * draw a message that indicates the end of the game
	 * @param g the Graphics object that will draw this message
	 */
	public void drawGameOver(Graphics g) {
		g.setColor(Color.BLACK);
		String endMessage = "GAME OVER";
		g.setFont(new Font(Font.SANS_SERIF,Font.BOLD,50));
		Graphics2D g2 = (Graphics2D) g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
		FontMetrics fm = g.getFontMetrics();
		int x = (g.getClipBounds().width - fm.stringWidth(endMessage)) / 2;
        int y = (g.getClipBounds().height / 2) + fm.getMaxDescent();
        g.drawString(endMessage, x, y);
	}
	
	/**
	 * draw a message indicating that the game is paused
	 * @param g the Graphics object that will draw this message
	 */
	public void drawPause(Graphics g) {
		g.setColor(Color.BLACK);
		String pauseMessage = "JEU EN PAUSE";
		g.setFont(new Font(Font.SANS_SERIF,Font.BOLD,50));
		Graphics2D g2 = (Graphics2D) g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
		FontMetrics fm = g.getFontMetrics();
		int x = (g.getClipBounds().width - fm.stringWidth(pauseMessage)) / 2;
        int y = (g.getClipBounds().height / 2) + fm.getMaxDescent();
        g.drawString(pauseMessage, x, y);
	}

}
