package main.collections;

import fileio.input.LibraryInput;
import fileio.input.UserInput;
import main.users.User;

import java.util.ArrayList;

public final class Users {
    private static final Users INSTANCE = new Users();
    private static final ArrayList<User> USERS = new ArrayList<>();

    /**
     * default constructor
     */
    private Users() {

    }

    /**
     * gets the instance of the class
     */
    public static Users getInstance() {
        return INSTANCE;
    }

    /**
     * store the users from the input
     * @param library the input
     */
    public static void storeUsers(final LibraryInput library) {
        ArrayList<UserInput> userInputs = library.getUsers();
//        storing users
        for (UserInput userInput : userInputs) {
            Users.addUser(new User(userInput.getUsername(), userInput.getAge(),
                    userInput.getCity(), Songs.getSongs(), Podcasts.getPodcasts()));
        }
    }

    /**
     * reset the users after every test
     */
    public static void reset() {
        USERS.clear();
    }

    /**
     * add a user to the list
     * @param user the user to be added
     */
    public static void addUser(final User user) {
        USERS.add(user);
    }

    /**
     * get the list of users
     * @return the list of users
     */
    public static ArrayList<User> getUsers() {
        return USERS;
    }

    /**
     * remove a user from the list
     * @param user the user to be removed
     */
    public static void removeUser(final User user) {
        USERS.remove(user);
    }

    /**
     * get a user by username
     * @param username the username of the user
     */
    public static User getUser(final String username) {
        for (User user : USERS) {
            if (user.getUsername().equals(username)) {
                return user;
            }
        }
        return null;
    }

}
