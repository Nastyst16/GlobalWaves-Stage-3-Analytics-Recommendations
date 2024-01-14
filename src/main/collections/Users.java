package main.collections;

import fileio.input.LibraryInput;
import fileio.input.UserInput;
import main.users.User;

import java.util.ArrayList;

public final class Users {
    private static final Users INSTANCE = new Users();
    private final ArrayList<User> users = new ArrayList<>();

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
    public void storeUsers(final LibraryInput library) {
        ArrayList<UserInput> userInputs = library.getUsers();
//        storing users
        for (UserInput userInput : userInputs) {
            Users.getInstance().addUser(new User(userInput.getUsername(),
                    userInput.getAge(), userInput.getCity(), Songs.getInstance().getSongs(),
                    Podcasts.getInstance().getPodcasts()));
        }
    }

    /**
     * reset the users after every test
     */
    public void reset() {
        users.clear();
    }

    /**
     * add a user to the list
     * @param user the user to be added
     */
    public void addUser(final User user) {
        users.add(user);
    }

    /**
     * get the list of users
     * @return the list of users
     */
    public ArrayList<User> getUsers() {
        return users;
    }

    /**
     * remove a user from the list
     * @param user the user to be removed
     */
    public void removeUser(final User user) {
        users.remove(user);
    }

    /**
     * get a user by username
     * @param username the username of the user
     */
    public User getUser(final String username) {
        for (User user : users) {
            if (user.getUsername().equals(username)) {
                return user;
            }
        }
        return null;
    }

}
