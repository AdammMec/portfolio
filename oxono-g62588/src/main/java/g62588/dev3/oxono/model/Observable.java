package g62588.dev3.oxono.model;

import g62588.dev3.oxono.model.event.Event;

/**
 * Interface which represent an observable, it's an object that can notify observers object to update them, and register or
 * remove object of the observer list
 */
public interface Observable {
    /**
     * Register an object to the observer list
     * @param o An observer
     */
    void registerObserver(Observer o);

    /**
     * Remove an object from the observer lsit
     * @param o an observer
     */
    void removeObserver(Observer o);

    /**
     * Notify the observer that a new event has been invoked
     * @param event an object with information to update the view
     */
    void notifyObservers(Event event);

}
