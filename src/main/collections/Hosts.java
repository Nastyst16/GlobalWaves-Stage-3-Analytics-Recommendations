package main.collections;

import main.users.Host;

import java.util.ArrayList;

public final class Hosts {
    private static final ArrayList<Host> HOSTS = new ArrayList<>();

    /**
     * Default constructor.
     */
    private Hosts() {
    }

    /**
     * Resets the list of hosts.
     */
    public static void reset() {
        HOSTS.clear();
    }

    /**
     * Adds a host to the list of hosts.
     * @param host the host to be added
     */
    public static void addHost(final Host host) {
        HOSTS.add(host);
    }

    /**
     * gets the list of hosts.
     * @return the list of hosts
     */
    public static ArrayList<Host> getHosts() {
        return HOSTS;
    }

    /**
     * Removes a host from the list of hosts.
     * @param host the host to be removed
     */
    public static void removeHost(final Host host) {
        HOSTS.remove(host);
    }

    /**
     * Gets a host from the list of hosts.
     * @param username the username of the host to be found
     * @return the host if found, null otherwise
     */
    public static Host getHost(final String username) {
        for (Host host : HOSTS) {
            if (host.getUsername().equals(username)) {
                return host;
            }
        }
        return null;
    }
}
