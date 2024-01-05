package main.commands.player.artist;

import com.fasterxml.jackson.annotation.JsonIgnore;
import main.inputCommand.Command;
import main.inputCommand.CommandVisitor;
import main.SearchBar;
import main.users.User;
import main.commands.types.Event;
import main.users.Artist;
import main.users.Host;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public final class AddEvent implements Command {
    private final String command;
    private final String user;
    private final int timestamp;
    @JsonIgnore
    private final String name;
    @JsonIgnore
    private final String description;
    @JsonIgnore
    private final String date;
    private String message;

    /**
     * executes the setEvent method
     */
    public void execute(final User currUser, final Artist artist, final Host host) {
        this.setEvent(currUser, artist, host);
    }

    /**
     * sets the event for the artist
     * @param currUser the current user
     * @param artist the artist
     * @param host the host
     */
    public void setEvent(final User currUser, final Artist artist, final Host host) {

        if (currUser != null || host != null) {
            this.setMessage(this.user + " is not an artist.");
            return;
        } else if (artist == null) {
            this.setMessage("The username " + this.user + " doesn't exist.");
            return;
        }

        for (Event event : artist.getEvents()) {
            if (event.getName().equals(this.name)) {
                this.setMessage(this.user + "has another event with the same name.");
                return;
            }
        }

        if (!verifyingFormatDate(this.date)) {
            return;
        }

//        february case, 1 < day < 28, 1 < month < 12, 1900 < year < 2023
        if (!additionalDateVerification()) {
            return;
        }

        artist.getEvents().add(new Event(this.user, this.timestamp,
                this.name, this.description, this.date));
        this.setMessage(this.user + " has added new event successfully.");
    }


    /**
     * verifies if the date has the correct format or not
     * the format should be dd-mm-yyyy
     * @param data the date
     * @return
     */
    public boolean verifyingFormatDate(final String data) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-mm-yyyy");
        dateFormat.setLenient(false);

        try {
            Date parseData = dateFormat.parse(data);
            return true;

        } catch (ParseException e) {
            this.message = "Event for " + this.user + " does not have a valid date.";
            return false;
        }
    }

    /**
     * verifies if the date is valid or not
     * day should be between 1 and 31
     * month should be between 1 and 12
     * year should be between 1900 and 2023
     * if month is february, day should be between 1 and 28
     * @return
     */
    public boolean additionalDateVerification() {

//        magic numbers
        final int february = 2;
        final int maxDayFebruary = 28;
        final int minDay = 1;
        final int maxDay = 31;
        final int minMonth = 1;
        final int maxMonth = 12;
        final int minYear = 1900;
        final int maxYear = 2023;

//        the date format is dd-mm-yyyy verifying if dd is between 1 and 31
//        and month is between 1 and 12 and year is between 1900 and 2023
        String[] dateSplit = this.date.split("-");
        int day = Integer.parseInt(dateSplit[0]);
        int month = Integer.parseInt(dateSplit[1]);
        int year = Integer.parseInt(dateSplit[2]);
        if (day < minDay || day > maxDay || month < minMonth || month > maxMonth
                || year < minYear || year > maxYear) {
            this.setMessage("Event for " + this.user + " does not have a valid date.");
            return false;
        }
//        for february
        if (month == february && day > maxDayFebruary) {
            this.setMessage("Event for " + this.user + " does not have a valid date.");
            return false;
        }

        return true;
    }

    /**
     * constructor for the AddEvent class
     * @param input the input given
     */
    public AddEvent(final SearchBar input) {
        this.command = input.getCommand();
        this.user = input.getUsername();
        this.timestamp = input.getTimestamp();
        this.name = input.getName();
        this.description = input.getDescription();
        this.date = input.getDate();
    }

    /**
     * accepts the visitor for the AddEvent class
     * @param visitor the visitor
     */
    @Override
    public void accept(final CommandVisitor visitor) {
        visitor.visit(this);
    }

    /**
     * getter for the command
     * @return the command
     */
    public String getCommand() {
        return command;
    }

    /**
     * getter for the user
     * @return the user
     */
    public String getUser() {
        return user;
    }

    /**
     * getter for the timestamp
     * @return the timestamp
     */
    public int getTimestamp() {
        return timestamp;
    }

    /**
     * getter for the name
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * getter for the description
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * getter for the message
     * @return the message
     */
    public String getMessage() {
        return message;
    }

    /**
     * setter for the message
     * @param message the message
     */
    public void setMessage(final String message) {
        this.message = message;
    }
}
