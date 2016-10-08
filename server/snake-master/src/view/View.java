package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;

import controller.Controller;

import model.Grid;
import model.Snake;
import model.observer.Event;
import model.observer.Observer;

/** Singleton-class representing the view, following the MVC design pattern */
public class View extends JFrame implements Observer, Runnable {
	
	// You know what it is lol
	private static final long serialVersionUID = 1L;
	
	// the grid's panel and the informations' panel
	private JPanel gridPanel, infosPanel;
	
	// the score label
	private JLabel scoreLabel;
	
	// the explanations shown in the message dialog
	private String explanationsDialog;
	
	// the timer of the game
	private Timer timer;
	
	// the delay
	private final int DELAY = 65;
	
	/** the view-singleton */
	public static final View THE_VIEW = new View();
	
	// private constructor assuring us that we can only have one instance of the class View
	private View() {
		// we set the components of the window
		super("Snake");
		this.setFrame();
		this.setGrid();
		this.setInfos();
		// we add the key listener (the controller)
		this.addKeyListener(Controller.THE_CONTROLLER);
		// we set the dialog of the explanations and the timer
		this.explanationsDialog = this.setExplanationsMessage();
		this.timer = new Timer(this.DELAY,Controller.THE_CONTROLLER);
	}
	
	// sets the frame
	private void setFrame() {
		this.setSize(Grid.GRID_SIZE + 5,Grid.GRID_SIZE + 50);
		this.setLocationRelativeTo(null);
		this.setResizable(false);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	
	// sets the grid
	private void setGrid() {
		this.gridPanel = new GridPanel();
		this.gridPanel.setPreferredSize(new Dimension(Grid.GRID_SIZE,Grid.GRID_SIZE));
		this.gridPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		this.add(this.gridPanel,BorderLayout.NORTH);
	}
	
	// sets the infos
	private void setInfos() {
		this.infosPanel = new JPanel();
		this.scoreLabel = new JLabel();
		this.showScore();
		this.infosPanel.add(scoreLabel);
		this.add(this.infosPanel);
	}
	
	/**
	 * redraw the contents of the window
	 */
	public void redrawContents() {
		this.gridPanel.repaint();
	}
	
	/**
	 * shows the score
	 */
	public void showScore() {
		this.scoreLabel.setText("SCORE : "+Snake.THE_SNAKE.getScore());
	}
	
	/**
	 * runs the game by making visible the frame and starting the timer
	 */
	public void run() {
		this.setVisible(true);
		this.timer.start();
	}
	
	/**
	 * stops the timer
	 */
	public void stop() {
		this.timer.stop();
	}
	
	/**
	 * restarts the timer
	 */
	public void restart() {
		this.timer.restart();
	}
	
	/**
	 * @return <code> true </code> if the game is running, <code> false </code> otherwise
	 */
	public boolean isRunning() {
		return this.timer.isRunning();
	}
	
	/**
	 * sets the explanations
	 * @return the explanations
	 */
	public String setExplanationsMessage() {
		this.explanationsDialog = "";
		try {
			BufferedReader br = new BufferedReader(new FileReader("principe.txt"));
			String currentLine;
			while ((currentLine = br.readLine()) != null) {
				this.explanationsDialog += currentLine+"\n";
			}
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		return this.explanationsDialog;
	}


	/**
	 * main method of our class (method that launches the game)
	 * @param args the parameters array
	 */
	public static void main(String[] args) {
		JOptionPane.showMessageDialog(null, View.THE_VIEW.explanationsDialog,"Début du jeu",JOptionPane.INFORMATION_MESSAGE);
		View.THE_VIEW.run();
	}

	/**
	 * @see model.observer.Observer#update(Event)
	 */
	@Override
	public void update(Event e) {
		this.redrawContents();
	}

}
