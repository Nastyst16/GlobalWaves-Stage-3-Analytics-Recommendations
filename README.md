#
<div align="center"><img src="https://i.ytimg.com/vi/VZ1d_M4coQ4/maxresdefault.jpg" width="500px"></div>

# Proiect GlobalWaves  - Etapa 2

#### I used my own code from the previous stage.

### Description

     The project is a music player that can play songs, podcasts, playlists.
    It simulates different actions made by an user. These actions will be simulated using
    some commands given in Json files. This project is the perspective of an admin.

### Commands:

1. SearchBar
   * Search: Is used to find in the library a song, or a playlist, or a podcast.
       The user can search by simple or multiple filters. The programm displays maximum
       of 5 results.
   * Select: The user chooses one of the results from the current search.
2. Player
    * Load: Runs the specific type selected (song/podcast/playlist).
    * PlayPause: Making the transition between *play* and *pause* state.
    * Repeat: Toggling the different repeat statuses:
      * Songs and Podcasts: No Repeat, Repeat Once, Repeat Infinite.
      * Playlists: No Repeat, Repeat All, Repeat Current Song.
    * Shuffle: Only when a playlist is currently running and toggles the shuffle state.
    * Forward/Backward: Only when a podcast is currently running and goes forward/backward 90 seconds.
    * Like: Likes or Dislikes the running song.
    * Next: Go to the next track.
    * Prev: Go to the previous track.
    * AddRemoveInPlaylist: Adding or removing the current playing song to a playlist given by user.
    * Status: Shows the status of the *player*.
3. Playlist
    * CreatePlaylist: The user creates his own playlist.
    * SwitchVisibility: The user can choose if his playlist is public or private for the other users.
    * FollowPlaylist: Follows the selected playlist.
    * ShowPlaylists: Shows all the playlists owned by the user
4. Statistics
    * GetTop5Songs: The most 5 liked songs will be shown
    * GetTop5Playlists: The most 5 followed playlists will be shown
    * GetTop5Albums: The 5 albums with the most liked songs will be shown
    * GetTop5Artists: The artist with the most liked songs will be shown
    * GetAllUsers: All the users will be shown
    * GetOnlineUsers: All the online users will be shown
5. Admin
   * AddUser: The admin can add a new user. It can be a simple user, an artist or a host.
   * DeleteUser: The admin can delete a user/artist/host.
        * removeUser: if a user is listening to one of removeUser's playlists, we can't delete it.
        * removeArtist: if a user is listening to removeArtist's songs, or has selected it's page, we can't delete it.
        * removeHost: if a user is listening to removeHost's podcasts, or has selected it's page, we can't delete it.
   * ShowAlbums: Shows all the albums from the library.
   * ShowPodcasts: Shows all the podcasts from the library.
6. Artist
   * AddAlbum: adds a new album.
   * AddEvent: adds a new event.
   * AddMerch: adds a new merch.
   * RemoveAlbum: removes an album. If a user is currently listening to one of the songs from the album, we can't delete it.
   * RemoveEvent: removes an event.
7. Host
   * AddAnnouncement: adds a new announcement.
   * AddPodcast: adds a new podcast.
   * RemoveAnnouncement: removes an announcement.
   * RemovePodcast: removes a podcast. If a user is currently listening to the podcast, we can't delete it.
8. User
   * SwitchConnectionStatus: The user can choose if he is online or offline.
9. PageSystem:
   * ChangePage: The user can change the page he is currently on. The pages are: Home, LikedSongs.
   * PrintCurrentPage: The user can see the current page he is on. 
        * The available pages are: Home, LikedSongs, ArtistPage, HostPage.
        * The user can search an artist or host and select it's page.
        * "Home": display liked songs sorted by number of likes and followed playlists
        * "LikedSongs": display most recent liked songs and followed playlists
        * "ArtistPage": display artist's albums, merch and events
        * "HostPage": display host's podcasts and announcements

    

### Implementation:

        The project starts by reading and storing in the library the Users, Songs, Podcasts and Episodes
    The searchBarInputs variable reresents every command made by the user, so we start reading all of these
    commands one by one in the for at the line 135 from main.

        Before making any move the code sees how many seconds have passed since the last command: lines 146-163.

        Next there is a chain of if-elses that treats every command, adding the output to the "commands" array
    and doing all the necessary actions through the ("setSearch", "setSelect", "setLoad", ... ) corresponding
    set method.

        The types by number: -1 - empty, 0 - song, 1 - podcast, 2 - playlist
        The repeat status by number: Song/Podcast-> 0 - no_repeat, 1 - repeat_once, 2 - repeat_infinite
                                     Playlist-> 0 - no_repeat, 1 - repeat_all, 2 - repeat_current_song

        For every user i storred the necessary statuses (like repeatStatus, typeFoundBySearch, typeSelected,
    typeLoaded, ... ) that allows the program to know every detail about the actions and activities of the user.
        The "currentType" variable i call it "generic", because in it i can store a song or a podcast or an episode.
    I made this generic because i wanted that the set of instructions to be made only once, not three times.

        In contrast to the stage 1, besides of the required commands, I implemented 2 design patterns: Singleton and Visitor.
    The Singleton pattern is in the "Collections" classes, there are 7 of them, one for each type of object.
    The Visitor pattern is in the "inputCommand" package.

        At the end of main, we write everything into the Json file.


## Project Structure;

* src/
    * main/
        * Commands/
            * Command
            * Main
            * SearchBar
            * Test
            * User
            * SearchBar/
                * Search
                * SearchPlaylist
            * Types/
                * Episode
                * Playlist
                * Podcast
                * Song
                * Type
            * Player/
                * AddRemoveInPlaylist
                * Backward
                * CreatePlaylist
                * Follow
                * Forward
                * GetTop5Playlists
                * GetTop5Songs
                * Like
                * Load
                * Next
                * PlayPause
                * Prev
                * Repeat
                * ShowPlaylist
                * Shuffle
                * Status
                * SwitchVisibility
        * Collections/
            * Albums.java
            * Artists.java
            * Hosts.java
            * Playlists.java
            * Podcasts.java
            * Songs.java
            * Users.java
        * Main.java
        * SearchBar.java
        * Test.java
  * commands/
      * pageSystem/
          * ChangePage.java
          * PrintCurrentPage.java
      * player/
          * AddRemoveInPlaylist.java
          * Backward.java
          * CreatePlayList.java
          * Follow.java
          * Forward.java
          * Like.java
          * Load.java
          * Next.java
          * PlayPause.java
          * Prev.java
          * Repeat.java
          * ShowPlaylists.java
          * ShowPreferredSongs.java
          * Shuffle.java
          * Status.java
          * SwitchVisibility.java
          * admin/
              * AddUser.java
              * DeleteUser.java
              * ShowAlbums.java
              * ShowPodcasts.java
          * artist/
              * AddAlbum.java
              * AddEvent.java
              * AddMerch.java
              * RemoveAlbum.java
              * RemoveEvent.java
          * host/
              * AddAnnouncement.java
              * AddPodcast.java
              * RemoveAnnouncement.java
              * RemovePodcast.java
          * statistics/
              * GetAllUsers.java
              * GetOnlineUsers.java
              * GetTop5Albums.java
              * GetTop5Artists.java
              * GetTop5Playlists.java
              * GetTop5Songs.java
          * user/
              * SwitchConnectionStatus.java
      * searchBar/
          * Search.java
          * Select.java
      * types/
          * Album.java
          * Announcement.java
          * Episode.java
          * Event.java
          * Merch.java
          * Playlist.java
          * Podcast.java
          * Song.java
          * Type.java
  * inputCommand/
      * Command.java
      * CommandVisitor.java
      * ConcreteCommandVisitor.java
  * users/
      * Artist.java
      * Host.java
      * User.java


#### Assignment Link: [https://ocw.cs.pub.ro/courses/poo-ca-cd/teme/proiect/etapa1](https://ocw.cs.pub.ro/courses/poo-ca-cd/teme/proiect/etapa1)
