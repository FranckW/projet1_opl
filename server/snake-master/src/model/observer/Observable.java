package model.observer;

/** Interface representing an Observable in the design pattern Observer */
public interface Observable {
	
	/**
	 * adds an observer
	 * @param obs the observer to add
	 */
	public void addObserver(Observer obs);
	
	/**
	 * removes an observer
	 * @param obs the observer to remove
	 */
	public void removeObserver(Observer obs);
	
	/**
	 * tells all its observers that an event has been launched
	 * @param e the launched event
	 */
	public void notifyObservers(Event e);

}
