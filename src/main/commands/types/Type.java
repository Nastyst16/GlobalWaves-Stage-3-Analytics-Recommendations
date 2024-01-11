package main.commands.types;

import main.users.User;

public interface Type {

    /**
     * get name
     * @return
     */
    int getSecondsGone();

    /**
     * set seconds gone
     * @param secondsGone
     */
    void setSecondsGone(int secondsGone);

    /**
     * execute
     */
    void execute();

    /**
     * get duration
     * @return
     */
    int getDuration();

    /**
     * get name
     * @return
     */
    String getName();

    /**
     * listen the type
     */
    void listen(User user);

    /**
     * add listen
     */
    void addListen();
}
