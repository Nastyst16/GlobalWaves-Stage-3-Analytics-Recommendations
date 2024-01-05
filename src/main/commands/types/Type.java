package main.commands.types;

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
}
