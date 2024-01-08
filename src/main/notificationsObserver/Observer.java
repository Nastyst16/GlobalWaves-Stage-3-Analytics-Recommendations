package main.notificationsObserver;

import java.util.Map;

public interface Observer {
    void update(Map<String, String> message);
}
