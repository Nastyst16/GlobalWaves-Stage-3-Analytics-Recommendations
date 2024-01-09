package main.notificationsObserver;

public interface Subject {
    /**
     * adds an observer to the list
     */
    void addObserver(Observer observer);

    /**
     * removes an observer from the list
     */
    void removeObserver(Observer observer);

    /**
     * notifies the users
     */
    void notifyUsers(String name, String description);
}
