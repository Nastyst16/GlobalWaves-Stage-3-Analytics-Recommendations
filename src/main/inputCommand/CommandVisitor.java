package main.inputCommand;

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

public interface CommandVisitor {
//    visit methods for all commands


    /**
     * Visits the Search command.
     * @param search The Search command to visit.
     */
    void visit(Search search);

    /**
     * Visits the Select command.
     * @param select The Select command to visit.
     */
    void visit(Select select);

    /**
     * Visits the Load command.
     * @param load The Load command to visit.
     */
    void visit(Load load);

    /**
     * Visits the PlayPause command.
     * @param playPause The PlayPause command to visit.
     */
    void visit(PlayPause playPause);

    /**
     * Visits the Repeat command.
     * @param repeat The Repeat command to visit.
     */
    void visit(Repeat repeat);

    /**
     * Visits the Status command.
     * @param status The Status command to visit.
     */
    void visit(Status status);

    /**
     * Visits the Shuffle command.
     * @param shuffle The Shuffle command to visit.
     */
    void visit(Shuffle shuffle);

    /**
     * Visits the CreatePlayList command.
     * @param createPlayList The CreatePlayList command to visit.
     */
    void visit(CreatePlayList createPlayList);

    /**
     * Visits the AddRemoveInPlaylist command.
     * @param addRemoveInPlaylist The AddRemoveInPlaylist command to visit.
     */
    void visit(AddRemoveInPlaylist addRemoveInPlaylist);

    /**
     * Visits the Like command.
     * @param like The Like command to visit.
     */
    void visit(Like like);

    /**
     * Visits the ShowPlaylists command.
     * @param showPlaylists The ShowPlaylists command to visit.
     */
    void visit(ShowPlaylists showPlaylists);

    /**
     * Visits the ShowPreferredSongs command.
     * @param showPreferredSongs The ShowPreferredSongs command to visit.
     */
    void visit(ShowPreferredSongs showPreferredSongs);

    /**
     * Visits the Next command.
     * @param next The Next command to visit.
     */
    void visit(Next next);

    /**
     * Visits the Prev command.
     * @param prev The Prev command to visit.
     */
    void visit(Prev prev);

    /**
     * Visits the Forward command.
     * @param forward The Forward command to visit.
     */
    void visit(Forward forward);

    /**
     * Visits the Backward command.
     * @param backward The Backward command to visit.
     */
    void visit(Backward backward);

    /**
     * Visits the Follow command.
     * @param follow The Follow command to visit.
     */
    void visit(Follow follow);

    /**
     * Visits the SwitchVisibility command.
     * @param switchVisibility The SwitchVisibility command to visit.
     */
    void visit(SwitchVisibility switchVisibility);

    /**
     * Visits the GetTop5Playlists command.
     * @param getTop5Playlists The GetTop5Playlists command to visit.
     */
    void visit(GetTop5Playlists getTop5Playlists);

    /**
     * Visits the GetTop5Songs command.
     * @param getTop5Songs The GetTop5Songs command to visit.
     */
    void visit(GetTop5Songs getTop5Songs);

// Stage 2:

    /**
     * Visits the ChangePage command.
     * @param changePage The ChangePage command to visit.
     */
    void visit(ChangePage changePage);

    /**
     * Visits the SwitchConnectionStatus command.
     * @param switchConnectionStatus The SwitchConnectionStatus command to visit.
     */
    void visit(SwitchConnectionStatus switchConnectionStatus);

    /**
     * Visits the GetOnlineUsers command.
     * @param getOnlineUsers The GetOnlineUsers command to visit.
     */
    void visit(GetOnlineUsers getOnlineUsers);

    /**
     * Visits the AddUser command.
     * @param addUser The AddUser command to visit.
     */
    void visit(AddUser addUser);

    /**
     * Visits the AddAlbum command.
     * @param addAlbum The AddAlbum command to visit.
     */
    void visit(AddAlbum addAlbum);

    /**
     * Visits the ShowAlbums command.
     * @param showAlbums The ShowAlbums command to visit.
     */
    void visit(ShowAlbums showAlbums);

    /**
     * Visits the PrintCurrentPage command.
     * @param printCurrentPage The PrintCurrentPage command to visit.
     */
    void visit(PrintCurrentPage printCurrentPage);

    /**
     * Visits the AddEvent command.
     * @param addEvent The AddEvent command to visit.
     */
    void visit(AddEvent addEvent);

    /**
     * Visits the AddMerch command.
     * @param addMerch The AddMerch command to visit.
     */
    void visit(AddMerch addMerch);

    /**
     * Visits the GetAllUsers command.
     * @param getAllUsers The GetAllUsers command to visit.
     */
    void visit(GetAllUsers getAllUsers);

    /**
     * Visits the DeleteUser command.
     * @param deleteUser The DeleteUser command to visit.
     */
    void visit(DeleteUser deleteUser);

    /**
     * Visits the AddPodcast command.
     * @param addPodcast The AddPodcast command to visit.
     */
    void visit(AddPodcast addPodcast);

    /**
     * Visits the AddAnnouncement command.
     * @param addAnnouncement The AddAnnouncement command to visit.
     */
    void visit(AddAnnouncement addAnnouncement);

    /**
     * Visits the RemoveAnnouncement command.
     * @param removeAnnouncement The RemoveAnnouncement command to visit.
     */
    void visit(RemoveAnnouncement removeAnnouncement);

    /**
     * Visits the ShowPodcasts command.
     * @param showPodcasts The ShowPodcasts command to visit.
     */
    void visit(ShowPodcasts showPodcasts);

    /**
     * Visits the RemoveAlbum command.
     * @param removeAlbum The RemoveAlbum command to visit.
     */
    void visit(RemoveAlbum removeAlbum);

    /**
     * Visits the RemovePodcast command.
     * @param removePodcast The RemovePodcast command to visit.
     */
    void visit(RemovePodcast removePodcast);

    /**
     * Visits the RemoveEvent command.
     * @param removeEvent The RemoveEvent command to visit.
     */
    void visit(RemoveEvent removeEvent);

    /**
     * Visits the GetTop5Albums command.
     * @param getTop5Albums The GetTop5Albums command to visit.
     */
    void visit(GetTop5Albums getTop5Albums);

    /**
     * Visits the GetTop5Artists command.
     * @param getTop5Artists The GetTop5Artists command to visit.
     */
    void visit(GetTop5Artists getTop5Artists);

}
