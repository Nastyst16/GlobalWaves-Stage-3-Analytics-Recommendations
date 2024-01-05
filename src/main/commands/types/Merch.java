package main.commands.types;

public class Merch {
    private final String user;
    private final String name;
    private final String description;
    private final int price;


    public Merch(final String user, final String name,
                  final String description, final int price) {
        this.user = user;
        this.name = name;
        this.description = description;
        this.price = price;
    }

    /**
     * get the user who created the merch
     * @return the user who created the merch
     */
    public String getUser() {
        return user;
    }

    /**
     * get the name of the merch
     * @return the name of the merch
     */
    public String getName() {
        return name;
    }

    /**
     * get the description of the merch
     * @return the description of the merch
     */
    public String getDescription() {
        return description;
    }

    /**
     * get the price of the merch
     * @return the price of the merch
     */
    public int getPrice() {
        return price;
    }
}
