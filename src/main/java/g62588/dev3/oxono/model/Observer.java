package g62588.dev3.oxono.model;


import g62588.dev3.oxono.model.event.Event;

import java.util.Objects;

/**
 * Wait for call from observable to modify the view
 */
public interface Observer {
    /**
     * a method that update the view with the event
     * @param values event who contain values
     */
    void update(Event values);
}
