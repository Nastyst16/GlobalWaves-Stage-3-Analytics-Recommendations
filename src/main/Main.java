package main;

import checker.Checker;
import checker.CheckerConstants;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.node.ArrayNode;
import fileio.input.LibraryInput;
import main.collections.Users;
import main.collections.Albums;
import main.collections.Artists;
import main.collections.Hosts;
import main.collections.Playlists;
import main.collections.Podcasts;
import main.collections.Songs;
import main.commands.pageSystem.ChangePage;
import main.commands.pageSystem.PrintCurrentPage;
import main.commands.player.admin.AddUser;
import main.commands.player.admin.DeleteUser;
import main.commands.player.admin.ShowAlbums;
import main.commands.player.admin.ShowPodcasts;
import main.commands.player.artist.AddAlbum;
import main.commands.player.artist.AddEvent;
import main.commands.player.artist.AddMerch;
import main.commands.player.artist.RemoveAlbum;
import main.commands.player.artist.RemoveEvent;
import main.commands.player.host.AddAnnouncement;
import main.commands.player.host.AddPodcast;
import main.commands.player.host.RemoveAnnouncement;
import main.commands.player.host.RemovePodcast;
import main.commands.player.statistics.GetTop5Albums;
import main.commands.player.statistics.GetTop5Artists;
import main.commands.player.statistics.GetTop5Playlists;
import main.commands.player.statistics.GetTop5Songs;
import main.commands.searchBar.Search;
import main.commands.searchBar.Select;
import main.commands.types.Type;
import main.commands.player.AddRemoveInPlaylist;
import main.commands.player.CreatePlayList;
import main.commands.player.Like;
import main.commands.player.Next;
import main.commands.player.Prev;
import main.commands.player.PlayPause;
import main.commands.player.Repeat;
import main.commands.player.Shuffle;
import main.commands.player.Status;
import main.commands.player.Backward;
import main.commands.player.Forward;
import main.commands.player.Follow;
import main.commands.player.SwitchVisibility;
import main.commands.player.Load;
import main.commands.player.statistics.GetOnlineUsers;
import main.commands.player.statistics.GetAllUsers;
import main.commands.player.ShowPlaylists;
import main.commands.player.ShowPreferredSongs;
import main.commands.player.user.SwitchConnectionStatus;
import main.inputCommand.Command;
import main.inputCommand.ConcreteCommandVisitor;
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

//        reading input test files
        ArrayList<SearchBar> searchBarInputs = objectMapper.
                readValue(new File(CheckerConstants.TESTS_PATH
                        + filePathInput), new TypeReference<>() { });

//        storing the collections from the library
        storeCollections(library);

//        creating the list of commands
        ArrayList<Command> commands = new ArrayList<>();

//        creating the executor
        ConcreteCommandVisitor executor = new ConcreteCommandVisitor();

//        parsing the Json content into corresponding commands
        for (SearchBar input : searchBarInputs) {

            String command = input.getCommand();

            User user = findingUser(input, Users.getUsers());
            Artist artist = findingArtist(user, input);
            Host host = findingHost(user, input);

//            calculating how many seconds have gone sine the last command for every user
            howManySecsGone(input);

            int index = commands.size();

//            creating the commands
            executor.setExecutor(commands, input, user, artist, host);

            switch (command) {
                case "search":              commands.add(new Search(input));                 break;
                case "select":              commands.add(new Select(input));                 break;
                case "load":                commands.add(new Load(input));                   break;
                case "playPause":           commands.add(new PlayPause(input));              break;
                case "repeat":              commands.add(new Repeat(input));                 break;
                case "status":              commands.add(new Status(input));                 break;
                case "shuffle":             commands.add(new Shuffle(input));                break;
                case "createPlaylist":      commands.add(new CreatePlayList(input));         break;
                case "addRemoveInPlaylist": commands.add(new AddRemoveInPlaylist(input));    break;
                case "like":                commands.add(new Like(input));                   break;
                case "showPlaylists":       commands.add(new ShowPlaylists(input));          break;
                case "showPreferredSongs":  commands.add(new ShowPreferredSongs(input));     break;
                case "next":                commands.add(new Next(input));                   break;
                case "prev":                commands.add(new Prev(input));                   break;
                case "forward":             commands.add(new Forward(input));                break;
                case "backward":            commands.add(new Backward(input));               break;
                case "follow":              commands.add(new Follow(input));                 break;
                case "switchVisibility":    commands.add(new SwitchVisibility(input));       break;
                case "getTop5Playlists":    commands.add(new GetTop5Playlists(input));       break;
                case "getTop5Songs":        commands.add(new GetTop5Songs(input));           break;

//                Stage 2:
                case "switchConnectionStatus":
                                            commands.add(new SwitchConnectionStatus(input)); break;
                case "getOnlineUsers":      commands.add(new GetOnlineUsers(input));         break;
                case "changePage":          commands.add(new ChangePage(input));             break;
                case "addUser":             commands.add(new AddUser(input));                break;
                case "addAlbum":            commands.add(new AddAlbum(input));               break;
                case "showAlbums":          commands.add(new ShowAlbums(input));             break;
                case "printCurrentPage":    commands.add(new PrintCurrentPage(input));       break;
                case "addEvent":            commands.add(new AddEvent(input));               break;
                case "addMerch":            commands.add(new AddMerch(input));               break;
                case "getAllUsers":         commands.add(new GetAllUsers(input));            break;
                case "deleteUser":          commands.add(new DeleteUser(input));             break;
                case "addPodcast":          commands.add(new AddPodcast(input));             break;
                case "addAnnouncement":     commands.add(new AddAnnouncement(input));        break;
                case "removeAnnouncement":  commands.add(new RemoveAnnouncement(input));     break;
                case "showPodcasts":        commands.add(new ShowPodcasts(input));           break;
                case "removeAlbum":         commands.add(new RemoveAlbum(input));            break;
                case "removePodcast":       commands.add(new RemovePodcast(input));          break;
                case "removeEvent":         commands.add(new RemoveEvent(input));            break;
                case "getTop5Albums":       commands.add(new GetTop5Albums(input));          break;
                case "getTop5Artists":      commands.add(new GetTop5Artists(input));         break;

                default: break;
            }
//            we have the command created, now we use the visitor design pattern
            commands.get(index).accept(executor);
        }
//        reseting the collections after every test
        resetCollections();

//        parsing the requeriments
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
            for (Artist a : Artists.getArtists()) {
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
            for (Host h : Hosts.getHosts()) {
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
        for (User u : Users.getUsers()) {
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
        Songs.storeSongs(library);

//        reading Podcasts && Episodes
        Podcasts.storePodcasts(library);

//        reading users
        Users.storeUsers(library);
    }

    /**
     * this method resets the collections
     * after every test
     */
    public static void resetCollections() {
        Songs.reset();
        Podcasts.reset();
        Playlists.reset();
        Users.reset();
        Artists.reset();
        Hosts.reset();
        Albums.reset();
    }

}
