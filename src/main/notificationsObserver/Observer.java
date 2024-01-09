package main.notificationsObserver;

import java.util.Map;

public interface Observer {
    /**
     * updates the observer
     */
    void update(Map<String, String> message);
}
