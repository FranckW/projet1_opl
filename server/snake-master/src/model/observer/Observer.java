package model.observer;

/** Interface representing an Observer in the Observer design pattern */ 
public interface Observer {

	/**
	 * update itself when an event is launched
	 * @param e the launched event
	 */
	public void update(Event e);

}
