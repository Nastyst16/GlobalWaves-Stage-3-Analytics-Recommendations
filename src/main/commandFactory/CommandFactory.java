package main.commandFactory;

import main.SearchBar;
import main.commands.GetNotifications;
import main.commands.Subscribe;
import main.commands.UpdateRecom;
import main.commands.monetization.BuyMerch;
import main.commands.monetization.BuyPremium;
import main.commands.monetization.CancelPremium;
import main.commands.monetization.SeeMerch;
import main.commands.pageSystem.ChangePage;
import main.commands.pageSystem.PreviousNextPage;
import main.commands.pageSystem.PrintCurrentPage;
import main.commands.player.*;
import main.commands.player.admin.AddUser;
import main.commands.player.admin.DeleteUser;
import main.commands.player.admin.ShowAlbums;
import main.commands.player.admin.ShowPodcasts;
import main.commands.player.artist.*;
import main.commands.player.host.AddAnnouncement;
import main.commands.player.host.AddPodcast;
import main.commands.player.host.RemoveAnnouncement;
import main.commands.player.host.RemovePodcast;
import main.commands.player.statistics.*;
import main.commands.player.user.SwapOnlineOfline;
import main.commands.searchBar.Search;
import main.commands.searchBar.Select;
import main.inputCommand.Command;

public class CommandFactory implements Factory {
    /**
     * creates a command
     * @param command the command
     * @return the command
     */
    @Override
    public Command createCommand(String command, SearchBar input) {
        return switch (command) {
            case "search" ->                  new Search(input);
            case "select" ->                  new Select(input);
            case "load" ->                    new Load(input);
            case "playPause" ->               new PlayPause(input);
            case "repeat" ->                  new Repeat(input);
            case "status" ->                  new Status(input);
            case "shuffle" ->                 new Shuffle(input);
            case "createPlaylist" ->          new CreatePlayList(input);
            case "addRemoveInPlaylist" ->     new AddRemoveInPlaylist(input);
            case "like" ->                    new Like(input);
            case "showPlaylists" ->           new ShowPlaylists(input);
            case "showPreferredSongs" ->      new ShowPreferredSongs(input);
            case "next" ->                    new Next(input);
            case "prev" ->                    new Prev(input);
            case "forward" ->                 new Forward(input);
            case "backward" ->                new Backward(input);
            case "follow" ->                  new Follow(input);
            case "switchVisibility" ->        new SwitchVisibility(input);
            case "getTop5Playlists" ->        new GetTop5Playlists(input);
            case "getTop5Songs" ->            new GetTop5Songs(input);

//                Stage 2 ->
            case "switchConnectionStatus" ->  new SwapOnlineOfline(input);
            case "getOnlineUsers" ->          new GetOnlineUsers(input);
            case "changePage" ->              new ChangePage(input);
            case "addUser" ->                 new AddUser(input);
            case "addAlbum" ->                new AddAlbum(input);
            case "showAlbums" ->              new ShowAlbums(input);
            case "printCurrentPage" ->        new PrintCurrentPage(input);
            case "addEvent" ->                new AddEvent(input);
            case "addMerch" ->                new AddMerch(input);
            case "getAllUsers" ->             new GetAllUsers(input);
            case "deleteUser" ->              new DeleteUser(input);
            case "addPodcast" ->              new AddPodcast(input);
            case "addAnnouncement" ->         new AddAnnouncement(input);
            case "removeAnnouncement" ->      new RemoveAnnouncement(input);
            case "showPodcasts" ->            new ShowPodcasts(input);
            case "removeAlbum" ->             new RemoveAlbum(input);
            case "removePodcast" ->           new RemovePodcast(input);
            case "removeEvent" ->             new RemoveEvent(input);
            case "getTop5Albums" ->           new GetTop5Albums(input);
            case "getTop5Artists" ->          new GetTop5Artists(input);

//                Stage 3 ->
            case "wrapped" ->                 new Wrapped(input);
            case "buyPremium" ->              new BuyPremium(input);
            case "cancelPremium" ->           new CancelPremium(input);
            case "subscribe" ->               new Subscribe(input);
            case "getNotifications" ->        new GetNotifications(input);
            case "buyMerch" ->                new BuyMerch(input);
            case "seeMerch" ->                new SeeMerch(input);
            case "updateRecommendations" ->   new UpdateRecom(input);
            case "previousPage" ->            new PreviousNextPage(input);
            case "loadRecommendations" ->     new LoadRecomm(input);
            case "nextPage" ->                new PreviousNextPage(input);

            default -> null;
        };
    }
}
