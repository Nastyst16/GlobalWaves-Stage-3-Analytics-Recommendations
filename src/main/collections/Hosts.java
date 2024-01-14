package main.collections;

import main.users.Host;

import java.util.ArrayList;

public final class Hosts {
    private static final Hosts INSTANCE = new Hosts();
    private final ArrayList<Host> hosts = new ArrayList<>();

    /**
     * Default constructor.
     */
    private Hosts() {
    }

    /**
     * Gets the instance of the class.
     */
    public static Hosts getInstance() {
        return INSTANCE;
    }

    /**
     * Resets the list of hosts.
     */
    public void reset() {
        hosts.clear();
    }

    /**
     * Adds a host to the list of hosts.
     * @param host the host to be added
     */
    public void addHost(final Host host) {
        hosts.add(host);
    }

    /**
     * gets the list of hosts.
     * @return the list of hosts
     */
    public ArrayList<Host> getHosts() {
        return hosts;
    }

    /**
     * Removes a host from the list of hosts.
     * @param host the host to be removed
     */
    public void removeHost(final Host host) {
        hosts.remove(host);
    }

    /**
     * Gets a host from the list of hosts.
     * @param username the username of the host to be found
     * @return the host if found, null otherwise
     */
    public Host getHost(final String username) {
        for (Host host : hosts) {
            if (host.getUsername().equals(username)) {
                return host;
            }
        }
        return null;
    }
}
