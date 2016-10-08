

package model.observer;


public interface Observable {
    public void addObserver(model.observer.Observer obs);

    public void removeObserver(model.observer.Observer obs);

    public void notifyObservers(model.observer.Event e);
}

