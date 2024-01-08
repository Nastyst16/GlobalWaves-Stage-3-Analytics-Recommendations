package main.notificationsObserver;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class NotificationService implements Subject {
    private List<Observer> observers = new ArrayList<>();

    @Override
    public void addObserver(Observer observer) {
        observers.add(observer);
    }

    @Override
    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }

    /**
     * boolean method that checks if the observer is in the list
     */
    public boolean containsObserver(Observer observer) {
        return observers.contains(observer);
    }

    public void notifyUsers(String name, String description) {
        LinkedHashMap<String, String> firstMes = new LinkedHashMap<>();
        firstMes.put("name", name);
        firstMes.put("description", description);
        for (Observer observer : observers) {
            observer.update(firstMes);
        }
    }

}
