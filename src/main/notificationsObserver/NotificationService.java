package main.notificationsObserver;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

public class NotificationService implements Subject {
    private final List<Observer> observers = new ArrayList<>();

    /**
     * adds an observer to the list
     * @param observer the observer
     */
    @Override
    public void addObserver(final Observer observer) {
        observers.add(observer);
    }

    /**
     * removes an observer from the list
     * @param observer the observer
     */
    @Override
    public void removeObserver(final Observer observer) {
        observers.remove(observer);
    }

    /**
     * boolean method that checks if the observer is in the list
     */
    public boolean containsObserver(final Observer observer) {
        return observers.contains(observer);
    }

    /**
     * notifies the users
     */
    public void notifyUsers(final String name, final String description) {
        LinkedHashMap<String, String> firstMes = new LinkedHashMap<>();
        firstMes.put("name", name);
        firstMes.put("description", description);
        for (Observer observer : observers) {
            observer.update(firstMes);
        }
    }

}
