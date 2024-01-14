package main;

import checker.Checker;
import checker.CheckerConstants;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.node.ArrayNode;
import fileio.input.LibraryInput;
import main.commandFactory.CommandFactory;
import main.commandFactory.Factory;
import main.collections.Users;
import main.collections.Albums;
import main.collections.Artists;
import main.collections.Hosts;
import main.collections.Playlists;
import main.collections.Podcasts;
import main.collections.Songs;
import main.commands.player.EndProgram;
import main.commands.player.statistics.Wrapped;
import main.commands.player.statistics.WrappedMessage;
import main.commands.types.Type;
import main.inputCommand.Command;
import main.users.Artist;
import main.users.Host;
import main.users.User;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Objects;

/**
 * The entry point to this homework. It runs the checker that tests your implentation.
 */
public final class Main {
    static final String LIBRARY_PATH = CheckerConstants.TESTS_PATH + "library/library.json";

    /**
     * for coding style
     */
    private Main() {
    }

    /**
     * DO NOT MODIFY MAIN METHOD
     * Call the checker
     * @param args from command line
     * @throws IOException in case of exceptions to reading / writing
     */
    public static void main(final String[] args) throws IOException {
        File directory = new File(CheckerConstants.TESTS_PATH);
        Path path = Paths.get(CheckerConstants.RESULT_PATH);

        if (Files.exists(path)) {
            File resultFile = new File(String.valueOf(path));
            for (File file : Objects.requireNonNull(resultFile.listFiles())) {
                file.delete();
            }
            resultFile.delete();
        }
        Files.createDirectories(path);

        for (File file : Objects.requireNonNull(directory.listFiles())) {
            if (file.getName().startsWith("library")) {
                continue;
            }

            String filepath = CheckerConstants.OUT_PATH + file.getName();
            File out = new File(filepath);
            boolean isCreated = out.createNewFile();
            if (isCreated) {
                action(file.getName(), filepath);
            }
        }

        Checker.calculateScore();
    }

    /**
     * @param filePathInput for input file
     * @param filePathOutput for output file
     * @throws IOException in case of exceptions to reading / writing
     */
    public static void action(final String filePathInput,
                              final String filePathOutput) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();

        LibraryInput library = objectMapper.readValue(new File(LIBRARY_PATH), LibraryInput.class);

        ArrayNode outputs = objectMapper.createArrayNode();

        // reading input test files
        ArrayList<SearchBar> searchBarInputs = objectMapper.
                readValue(new File(CheckerConstants.TESTS_PATH
                        + filePathInput), new TypeReference<>() { });

        // storing the collections from the library
        storeCollections(library);

        // creating the list of commands
        ArrayList<Command> commands = new ArrayList<>();

        // parsing the Json content into corresponding commands
        for (SearchBar input : searchBarInputs) {

            String command = input.getCommand();

            User user = findingUser(input, Users.getInstance().getUsers());
            Artist artist = findingArtist(user, input);
            Host host = findingHost(user, input);

            // calculating how many seconds have gone sine the last command for every user
            howManySecsGone(input);

            int index = commands.size();

            // design pattern: factory
            Factory factory = new CommandFactory();
            commands.add(factory.createCommand(command, input));

            if (index == commands.size() || command.equals("adBreak")) {
                continue;
            }

            // executing the command
            commands.get(index).execute(input, user, artist, host);

            if (commands.get(index) instanceof Wrapped) {
                if (((Wrapped) commands.get(index)).getResult() == null) {
                    commands.remove(index);
                    commands.add(new WrappedMessage(input));
                    commands.get(index).execute(input, user, artist, host);
                }
            }
        }

        commands.add(new EndProgram("endProgram"));
        commands.get(commands.size() - 1).execute();

        // reseting the collections after every atest
        resetCollections();

        // parsing the requeriments
        parsingReq(commands, filePathOutput, outputs, objectMapper);
    }

    /**
     * this method finds the user
     * @param input the current input
     * @param users the list of users
     * @return the user
     */
    public static User findingUser(final SearchBar input, final ArrayList<User> users) {
        User user = null;
        for (User u : users) {
            if (u.getUsername().equals(input.getUsername())) {
                user = u;
                break;
            }
        }
        return user;
    }

    /**
     * this method finds the artist
     * @param user the current user
     * @param input the current input
     * @return the artist
     */
    public static Artist findingArtist(final User user, final SearchBar input) {
        Artist artist = null;
        if (user == null) {
            for (Artist a : Artists.getInstance().getArtists()) {
                if (a.getUsername().equals(input.getUsername())) {
                    artist = a;
                    break;
                }
            }
        }
        return artist;
    }

    /**
     * this method finds the host
     * @param user the current user
     * @param input the current input
     * @return the host
     */
    public static Host findingHost(final User user, final SearchBar input) {
        Host host = null;
        if (user == null) {
            for (Host h : Hosts.getInstance().getHosts()) {
                if (h.getUsername().equals(input.getUsername())) {
                    host = h;
                    break;
                }
            }
        }
        return host;
    }

    /**
     * this method calculates how many seconds have gone since the last command
     * @param input the current input
     */
    public static void howManySecsGone(final SearchBar input) {
        for (User u : Users.getInstance().getUsers()) {
            if (u.getCurrentType() != null && !u.isPaused() && u.getOnline()) {
                int newSecsGone = input.getTimestamp() - u.getPrevTimestamp();

                Type currentType = u.getCurrentType();
                u.getCurrentType().setSecondsGone(u.
                        getCurrentType().getSecondsGone() + newSecsGone);

                u.setRemainingTime(currentType.getDuration() - currentType.getSecondsGone());
            }

            if (u.getCurrentType() != null) {
                u.treatingRepeatStatus(u);
            }

            u.setPrevTimestamp(input.getTimestamp());
        }

    }

    /**
     * this method writes every command into the output file
     * @param commands the list of commands
     * @param filePathOutput the path to the output file
     * @param outputs the array node
     * @param objectMapper the object mapper
     * @throws IOException in case of exceptions to reading / writing
     */
    public static void parsingReq(final ArrayList<Command> commands, final String filePathOutput,
                                  final ArrayNode outputs, final ObjectMapper objectMapper)
                                    throws IOException {
        if (!commands.isEmpty()) {
            for (Command comm : commands) {
                outputs.add(objectMapper.valueToTree(comm));
            }
        }
        ObjectWriter objectWriter = objectMapper.writerWithDefaultPrettyPrinter();
        objectWriter.writeValue(new File(filePathOutput), outputs);
    }

    /**
     * this method stores the collections
     * @param library the library
     */
    private static void storeCollections(final LibraryInput library) {
//        storing songs
        Songs.getInstance().storeSongs(library);

//        reading Podcasts && Episodes
        Podcasts.getInstance().storePodcasts(library);

//        reading users
        Users.getInstance().storeUsers(library);
    }

    /**
     * this method resets the collections
     * after every test
     */
    public static void resetCollections() {
        Songs.getInstance().reset();
        Podcasts.getInstance().reset();
        Playlists.getInstance().reset();
        Users.getInstance().reset();
        Artists.getInstance().reset();
        Hosts.getInstance().reset();
        Albums.getInstance().reset();
    }

}
