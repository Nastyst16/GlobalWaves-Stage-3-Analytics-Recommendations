#
<div align="center"><img src="https://i.ytimg.com/vi/VZ1d_M4coQ4/maxresdefault.jpg" width="500px"></div>

# Proiect GlobalWaves  - Etapa 3

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

Updates for stage 3:

New commands:
* Wrapped: a user/artist/host can see a personalized statistics based on their activity
  * User: the user sees top 5 from the following categories: artists, genres, songs, albums, episodes
  * Artist: the artist sees top 5 from the following categories: albums, songs, fans, listeners
  * Host: the host sees top 5 from the following categories: episodes, listeners
* BuyPremium: a user can buy a premium subscription, so he can listen to songs without ads
  * this also makes the monetization of the app possible, because the user has a balance that is shared with the artists/hosts based on an well defined algorithm
* CancelPremium: a user can cancel his premium subscription
  * at the end of this command, the artists/host receive their share of the user's balance
* Subscribe: a user can subscribe to an artist/host
  * the user will receive notifications when the artist/host releases a new album/podcast
  * this command is implemented using the Observer design pattern (more details in the implementation section)
  * this command toggles the subscription status
* GetNotifications: a user can see his notifications
  * the notifications are displayed in the order they were received
  * the notifications are deleted after they are displayed
* BuyMerch: a user can buy an item from an artist's merch
  * the artist's balance is updated
* SeeMerchant: a user can see his bought items
  * the items are displayed in the order they were bought
* UpdateRecommendations: only for users: possible commands:
  * "random_song": the user receives a random song recommendation
    * generated besed on a random song with same genre as the current listening song
  * "random_playlist": the user receives a random playlist recommendation
    * generated based on the user's liked songs, playlists created and followed playlists
  * "fans_playlist": the user receives a playlist recommendation
    * generated based on the current song top 5 fans
* PreviousPage: the user can go back to the previous page
* NextPage: the user can go to the next page
  * the previous and next page commands are connected toghether using the Memento design pattern (more details in the implementation section)
  * the prev and next pages are saved in a stack
* LoadRecommendations: the user can listen to the last recommended song or playlist
  * in the loadrecommendations class I am just doing the setup for the load command

### Implementation:

        The project starts by reading and storing in the library the Users, Songs, Podcasts and Episodes
    The searchBarInputs variable reresents every command made by the user, so we start reading all of these
    commands one by one in the for at the line 135 from main.

        Before making any move the code sees how many seconds have passed since the last command (howManySecsGone method).

        Next in the factory there is a switch case that creates the command based on the command requested.
    The command is executed.

        The types by number: -1 - empty, 0 - song, 1 - podcast, 2 - playlist
        The repeat status by number: Song/Podcast-> 0 - no_repeat, 1 - repeat_once, 2 - repeat_infinite
                                     Playlist-> 0 - no_repeat, 1 - repeat_all, 2 - repeat_current_song

        For every user i storred the necessary statuses (like repeatStatus, typeFoundBySearch, typeSelected,
    typeLoaded, ... ) that allows the program to know every detail about the actions and activities of the user.
        The "currentType" variable i call it "generic", because in it i can store a song or a podcast or an episode.
    I made this generic because i wanted that the set of instructions to be made only once, not three times.

        In contrast to the stage 2, besides of the required commands,
    I implemented 4 design patterns: Singleton, Factory, Observer and Memento.

    The Singleton pattern is in the package "collections" classes, there are 7 of them, one for each type of object.

    The Factory pattern is sored in the package "commandFactory", there i am creating the commands.

    The Observer pattern is in the package "notificationsObserver". In the artist class i have a variable named
    notificationService, which is an instance of the NotificationService class. The NotificationService class
    has a list of observers (the user class implements the Observer interface). So the user is an observer.
    This list helps the code know what users should be notified when an artist releases a new album, new merch, new event.

    The Memento pattern is in the package "mementoPattern", this is used for the previous and next page commands.
    In my code the currentPage consists in: String currentPage, Object currentRecommendation (this thing is for the
    loadRecommendation command, i have to know what was the last recommendation made, so i can load it),
    Playlist recommendedPlaylist, Song recommendedSong, String selectedPageOwner. These things are stored in a class
    named Page, which has every field modifiable, because this is the variable that we are doing operations on.
    The PageCareTaker class switches between pages, and saves the current page in a stack. The PageMemento class is
    just like a page copy, but it is immutable, so it can't be modified. The PageMemento class is used to save the
    pages in stacks.

        Lines 132-138: Some strange lines. Sorry for them, but for my implementation for parsing the commands at the end (line 148 main)
    it was impossible for me to have in "Wrapped" class the result field and also the message field. If the result is empty
    i should have the message field, and if the message field is empty i should have the result field. So i made a
    different class for the Wrapped command when i have the result empty, so i can show the message field. I know it's
    not the best solution, but i couldn't find another one. I hope you understand. So, basically, if i had both fields
    declared in the Wrapped class, in the json file the both fields are present (not OK). I couldnt use JsonIgnore based
    on a condition.


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
      * mementoPattern/
          * Page
          * PageCareTaker
          * PageMemento
      * notificationsObserver/
          * NotificationService
          * Observer
          * Subject
      * monetization/
          * BuyMerch
          * BuyPremium
          * CancelPremium
          * SeeMerch
      * pageSystem/
          * ChangePage.java
          * PrintCurrentPage
          * PreviousNextPage
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
              * Wrapped
              * WrappedMessage
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
      * GetNotifications
      * Subscribe
      * UpdateRecom
      * LoadRecomm
  * inputCommand/
      * Command.java
      * CommandVisitor.java
      * ConcreteCommandVisitor.java
  * users/
      * Artist.java
      * Host.java
      * User.java


#### Assignment Link: https://ocw.cs.pub.ro/courses/poo-ca-cd/teme/proiect/etapa3
