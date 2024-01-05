package main.commands.types;

public class Event {
    private final String user;
    private final int timestamp;
    private final String name;
    private final String description;
    private final String date;

    /**
     * Constructor for the Event class
     * @param user the user who created the event
     * @param timestamp the timestamp of the event
     * @param name the name of the event
     * @param description the description of the event
     * @param date the date of the event
     */
    public Event(final String user, final int timestamp, final String name,
                 final String description, final String date) {
        this.user = user;
        this.timestamp = timestamp;
        this.name = name;
        this.description = description;
        this.date = date;
    }


    /**
     * get the user who created the event
     * @return the user who created the event
     */
    public String getUser() {
        return user;
    }

    /**
     * get the timestamp of the event
     * @return the timestamp of the event
     */
    public int getTimestamp() {
        return timestamp;
    }

    /**
     * get the name of the event
     * @return the name of the event
     */
    public String getName() {
        return name;
    }

    /**
     * get the description of the event
     * @return the description of the event
     */
    public String getDescription() {
        return description;
    }

    /**
     * get the date of the event
     * @return the date of the event
     */
    public String getDate() {
        return date;
    }
}
