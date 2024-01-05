package main.commands.types;

public class Announcement {
    private final String name;
    private final String description;
    private final String owner;

    public Announcement(final String name, final String description, final String owner) {
        this.name = name;
        this.description = description;
        this.owner = owner;
    }

    /**
     * Getter for name
     * @return name of announcement
     */
    public String getName() {
        return name;
    }

    /**
     * Getter for description
     * @return description of announcement
     */
    public String getDescription() {
        return description;
    }

    /**
     * Getter for owner
     * @return owner of announcement
     */
    public String getOwner() {
        return owner;
    }
}
