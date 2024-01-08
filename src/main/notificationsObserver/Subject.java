package main.notificationsObserver;

import java.util.Map;

public interface Subject {
    void addObserver(Observer observer);
    void removeObserver(Observer observer);
    void notifyUsers(String name, String description);
}
