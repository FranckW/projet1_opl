package model.observer;

import java.util.EventObject;

/** Class representing an event in the design pattern Observer (not an obligation because actually it encapsulates nothing) */
public class Event extends EventObject {

	// Eclipse told me to add this...
	private static final long serialVersionUID = 1L;

	/**
	 * construct an event
	 * @param source the source of the event
	 */
	public Event(Object source) {
		super(source);
	}

}
