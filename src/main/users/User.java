package main.users;

import com.fasterxml.jackson.annotation.JsonIgnore;

import main.collections.Albums;
import main.collections.Songs;
import main.commands.searchBar.Search;
import main.commands.searchBar.Select;
import main.commands.types.Type;
import main.commands.types.Song;
import main.commands.types.Podcast;
import main.commands.types.Episode;
import main.commands.types.Album;
import main.commands.types.Playlist;
import main.commands.types.Merch;
import main.mementoPattern.Page;
import main.mementoPattern.PageCareTaker;
import main.notificationsObserver.Observer;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

public class User implements Observer {
    private String username;
    private final int age;
    private final String city;
    private int loadMade;
    private boolean paused;
    private int secondsGone;
    private int repeatStatus;
    private int typeFoundBySearch;
    private int typeSelected;
    private int typeLoaded;
    private boolean shuffle;
    private int remainingTime;
    private String repeatString;
    private boolean isNext;

    private Search currentSearch;
    private ArrayList<Song> everySong;
    private ArrayList<Podcast> everyPodcast;

    private Type currType;
    private int prevTimestamp;
    private Podcast currentPodcast;
    private Playlist currentPlaylist;
    private ArrayList<Integer> originalIndices;
    private ArrayList<Integer> shuffledIndices;
    private String selectedName;
    @JsonIgnore
    private String selectedAlbum;
    private int shuffleSeed;
    private Select currentSelect;
    private ArrayList<Playlist> playListList;
    private ArrayList<Song> likedSongs;
    private ArrayList<Playlist> followedPlaylists;
    private ArrayList<String> likedPlaylists;
    private ArrayList<Podcast> podcastsPlayed;
    private Playlist selectedPlaylist;

//    Stage 2 variables
    private boolean online;

//    Stage 3 variables
    private ArrayList<Album> everyAlbum;
    private Song searchedSong;
    private boolean premium = false;
    private ArrayList<Map<String, String>> notifications;
    private ArrayList<Merch> boughtMerchandise;
    private LinkedHashMap<Episode, Integer> lisenedEpisodes;
    private PageCareTaker pageCareTaker = new PageCareTaker();
    private Page currentPage = new Page();

    public User(final String username, final int age, final String city,
                final ArrayList<Song> everySong, final ArrayList<Podcast> everyPodcast) {
        this.username = username;
        this.age = age;
        this.city = city;

        loadMade = 0;
        paused = false;
        secondsGone = 0;
        repeatStatus = -1;
        typeFoundBySearch = -1;
        typeSelected = -1;
        typeLoaded = -1;
        shuffle = false;
        currType = null;

        currentSelect = null;
        currentSearch = null;
        playListList = new ArrayList<>();
        shuffledIndices = new ArrayList<>();
        originalIndices = new ArrayList<>();
        likedSongs = new ArrayList<>();
        likedPlaylists = new ArrayList<>();
        podcastsPlayed = new ArrayList<>();
        followedPlaylists = new ArrayList<>();


//        copy the podcasts
        this.everyPodcast = new ArrayList<>();
        for (Podcast podcast : everyPodcast) {
            Podcast copyPodcast = new Podcast();
            copyPodcast.setName(podcast.getName());
            copyPodcast.setOwner(podcast.getOwner());
            ArrayList<Episode> episodes = new ArrayList<>();
            for (Episode episode : podcast.getEpisodesList()) {
                Episode copyEpisode = new Episode(episode.getName(), episode.getDuration(),
                        episode.getDescription(), episode.getOwner());

                episodes.add(copyEpisode);
            }
            copyPodcast.setEpisodesList(episodes);

            this.everyPodcast.add(copyPodcast);
        }

//        copy the songs
        this.everySong = new ArrayList<>();
        for (Song song : Songs.getInstance().getSongs()) {
            Song copySong = new Song(song.getName(), song.getDuration(), song.getAlbum(),
                    song.getTags(), song.getLyrics(), song.getGenre(),
                    song.getReleaseYear(), song.getArtist());

            this.everySong.add(copySong);
        }

//        Stage 2:
        currentPage.setCurrentPage("Home");

        online = true;
        currentPage.setSelectedPageOwner("");

//        Stage 3:

//        copy the albums
        this.everyAlbum = new ArrayList<>();
        for (Album album : Albums.getInstance().getAlbums()) {
            Album copyAlbum = new Album(album.getUser(), album.getName(), album.getReleaseYear(),
                    album.getDescription(), album.getAlbumSongs());

            this.everyAlbum.add(copyAlbum);
        }

        this.notifications = new ArrayList<>();
        this.boughtMerchandise = new ArrayList<>();
        this.lisenedEpisodes = new LinkedHashMap<>();
    }


    /**
     * setting the liked songs
     * @param song the song liked
     * @return
     */
    public boolean setLikedSongs(final Song song) {

        boolean exists = false;
        for (Song tmp : likedSongs) {
            if (tmp.getName().equals(song.getName())) {
                exists = true;
                break;
            }
        }


        if (exists) {

//            removing the song from the likedSongs
            for (Song tmp : likedSongs) {
                if (tmp.getName().equals(song.getName())
                        && tmp.getAlbum().equals(song.getAlbum())) {
                    likedSongs.remove(tmp);
                    break;
                }
            }

            song.setNumberOfLikes(song.getNumberOfLikes() - 1);

            for (Song tmp : Songs.getInstance().getSongs()) {
                if (tmp.getName().equals(song.getName())
                        && tmp.getAlbum().equals(song.getAlbum())) {
                    tmp.setNumberOfLikes(tmp.getNumberOfLikes() - 1);
                    break;
                }
            }
            return false;

        } else {

            for (Song tmp : Songs.getInstance().getSongs()) {
                if (tmp.getName().equals(song.getName())
                        && tmp.getAlbum().equals(song.getAlbum())) {
                    this.likedSongs.add(tmp);
                    tmp.setNumberOfLikes(tmp.getNumberOfLikes() + 1);
                    break;
                }
            }
            return true;
        }
    }

    /**
     * likes of dislikes a playlist
     * @return true if the playlist was liked, false otherwise
     */
    public boolean setLikedPlaylist() {
        if (this.likedPlaylists.contains(this.selectedName)) {
            likedPlaylists.remove(this.selectedName);
            return false;
        } else {
            likedPlaylists.add(this.selectedName);
            return true;
        }
    }

    /**
     * treating the repeat status
     * @param user current user
     */
    public void treatingRepeatStatus(final User user) {
        Type currentType = user.getCurrentType();

        user.setRemainingTime(currentType.getDuration() - currentType.getSecondsGone());

//        if type loaded is a playlist and repeat status is on repeat current song
//        and the time remaining <= 0 we whould update the seconds;
        if (user.getTypeLoaded() == 2 && user.getRepeatStatus() == 2
                && user.getRemainingTime() <= 0) {

            while (currentType.getSecondsGone() >= currentType.getDuration()) {
                currentType.setSecondsGone(currentType.getSecondsGone()
                        - currentType.getDuration());
            }
            user.setRemainingTime(currentType.getDuration());
            this.currType = currentType;
            user.setCurrentType(currentType);
        }

        if (user.getTypeLoaded() == 0 && user.getRepeatStatus() == 0
                && user.getRemainingTime() < 0) {

            this.setNull(user);
            return;
        }

//        if the type loaded is a song or a podcast
        loadedSongOrPodcast(user, currentType);

        if (user.getTypeLoaded() == 1) {
//            comutam in episodul urmator pana cand este nevoie
            while (user.getRemainingTime() <= 0) {
                Podcast podcast = user.getCurrentPodcast();

                user.getCurrentPodcast().setLastRemainingEpisode(user.
                        getCurrentPodcast().getLastRemainingEpisode() + 1);
                int indexEpisode = user.getCurrentPodcast().getLastRemainingEpisode();

//                if it is the last episode in podcast
                if (user.getCurrentPodcast().getEpisodesList().size() == indexEpisode) {

                    this.setNull(user);
                    return;
                }

                Episode newEpisode = user.getCurrentPodcast().getEpisodesList().get(indexEpisode);

                currentType = newEpisode;
                user.setCurrentType(currentType);
                user.getCurrentType().listen(user);
                currentType.setSecondsGone(Math.abs(user.getRemainingTime()));
                user.setRemainingTime(currentType.getDuration() - currentType.getSecondsGone());
            }
        }

        if (user.getTypeLoaded() == 2) {

//            commuting the next song in playlist
            while (user.getRemainingTime() <= 0) {
                Playlist playlist = user.getCurrentPlaylist();

                int index = user.getCurrentPlaylist().getSongList().size() - 1;
                Song lastSong = user.getCurrentPlaylist().getSongList().get(index);

//                if it is the last song in playlist
                if (lastSong.getName().equals(currentType.getName()) && !user.isShuffle()) {
                    if (user.getRepeatStatus() == 0) {
                        this.setNull(user);
                        return;
                    }
                }

                if (user.getRepeatStatus() == 1 && user.getCurrentPlaylist().
                        getSongList().get(index).getName().equals(currentType.getName())) {

                    int secsGone = currentType.getSecondsGone() - currentType.getDuration();

                    currentType = user.getCurrentPlaylist().getSongList().get(0);
                    user.setCurrentType(currentType);
                    user.getCurrentType().listen(user);

                    currentType.setSecondsGone(secsGone);
                    user.setRemainingTime(currentType.getDuration() - currentType.getSecondsGone());

                    break;
                }

                int indexSong = playlist.getSongList().indexOf((Song) (currentType));

                Song newSong = null;
                if (user.isShuffle()) {

                    int nextShuffledIndex = user.getShuffledIndices().indexOf(indexSong) + 1;

                    if (nextShuffledIndex == user.getShuffledIndices().size()
                            && user.getRepeatStatus() == 1) {

                        int firstIndex = user.getShuffledIndices().get(0);
                        currentType = user.getCurrentPlaylist().getSongList().get(firstIndex);
                        user.setCurrentType(currentType);
                        user.getCurrentType().listen(user);

                        currentType.setSecondsGone(Math.abs(user.getRemainingTime()));
                        user.setRemainingTime(currentType.getDuration()
                                - currentType.getSecondsGone());

                        continue;

                    } else if (nextShuffledIndex == user.getShuffledIndices().size()) {
//                        end of playlist;
                        this.setNull(user);
                        return;
                    }
//
                    nextShuffledIndex = user.getShuffledIndices().get(nextShuffledIndex);

                    newSong = user.getCurrentPlaylist().getSongList().get(nextShuffledIndex);
                    currentType = newSong;
                    user.setCurrentType(currentType);
                    user.getCurrentType().listen(user);

//                if repeat current song we won't change the currentType
                } else if (user.getRepeatStatus() != 2) {
                    if (playlist.getSongList().size() - 1 > indexSong) {

                        currentType.setSecondsGone(0);

                        newSong = playlist.getSongList().get(indexSong + 1);
                        currentType = newSong;
                        user.setCurrentType(currentType);
                        user.getCurrentType().listen(user);
                    } else {
                        break;
                    }
                }

                currentType.setSecondsGone(Math.abs(user.getRemainingTime()));

                user.setRemainingTime(currentType.getDuration() - currentType.getSecondsGone());

                if (user.isNext()) {
                    this.currType = currentType;
                    user.setCurrentType(currentType);
                    return;
                }
            }
        }
        this.currType = currentType;
        user.setCurrentType(currentType);
    }

    /**
     * if the type loaded is a song or a podcast
     */
    private void loadedSongOrPodcast(final User user, final Type type) {
        Type currentType = type;

        if (user.getTypeLoaded() == 0 || user.getTypeLoaded() == 1) {

            if (user.getRepeatStatus() == 1 && user.getRemainingTime() < 0) {
                user.setRepeatStatus(0);
                user.setRepeatString("No Repeat");

                currentType.setSecondsGone(currentType.getSecondsGone()
                        - currentType.getDuration());

                user.setRemainingTime((currentType.getDuration())
                        - currentType.getSecondsGone());
            } else if (user.getRepeatStatus() == 2) {
//                if repeat infinite
                while (user.getRemainingTime() < 0) {

                    currentType.setSecondsGone(currentType.getSecondsGone()
                            - currentType.getDuration());
                    user.setRemainingTime((currentType.getDuration())
                            - currentType.getSecondsGone());
                }
            }
//            if the type loaded is a playlist
        } else if (user.getTypeLoaded() == 2) {

            if (user.getRepeatStatus() == 1 && user.getRemainingTime() < 0) {

                int index = user.getCurrentPlaylist().getSongList().size() - 1;
//                if the last song is playing
                if (user.getCurrentPlaylist().getSongList().get(index).
                        getName().equals(user.getSelectedName())) {
                    currentType = user.getCurrentPlaylist().getSongList().get(0);
                    user.setCurrentType(currentType);
                    user.getCurrentType().listen(user);

                    currentType.setSecondsGone(currentType.getDuration()
                            + currentType.getSecondsGone());
                }
            }
        }
    }

    private void setNull(final User user) {
        user.setCurrentType(null);
        user.setCurrentPodcast(null);
        user.setCurrentPlaylist(null);

        user.setTypeLoaded(-1);
        user.setShuffle(false);
    }

    /**
     * get the age
     * @return the age
     */
    public void addPlaylistToList(final Playlist playList) {
        playListList.add(playList);
    }

    /**
     * get the username
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * sets the username
     * @param username
     */
    public void setUsername(final String username) {
        this.username = username;
    }

    /**
     * gets the load made
     * @return the load made
     */
    public int getLoadMade() {
        return loadMade;
    }

    /**
     * set the loadMade
     * @param loadMade the loadMade
     */
    public void setLoadMade(final int loadMade) {
        this.loadMade = loadMade;
    }

    /**
     * get the pause status
     * @return the pause status
     */
    public boolean isPaused() {
        return paused;
    }

    /**
     * set the paused
     * @param paused the paused
     */
    public void setPaused(final boolean paused) {
        this.paused = paused;
    }

    /**
     * get the secondsGone
     * @return the secondsGone
     */
    public int getSecondsGone() {
        return secondsGone;
    }

    /**
     * set the secondsGone
     * @param secondsGone the secondsGone
     */
    @JsonIgnore
    public void setSecondsGone(final int secondsGone) {
        this.secondsGone = secondsGone;
    }

    /**
     * get the repeatStatus
     * @return the repeatStatus
     */
    public int getRepeatStatus() {
        return repeatStatus;
    }

    /**
     * set the repeatStatus
     * @param repeatStatus the repeatStatus
     */
    @JsonIgnore
    public void setRepeatStatus(final int repeatStatus) {
        this.repeatStatus = repeatStatus;
    }

    /**
     * get the typeFoundBySearch
     * @return the typeFoundBySearch
     */
    public int getTypeFoundBySearch() {
        return typeFoundBySearch;
    }

    /**
     * set the typeFoundBySearch
     * @param typeFoundBySearch the typeFoundBySearch
     */
    @JsonIgnore
    public void setTypeFoundBySearch(final int typeFoundBySearch) {
        this.typeFoundBySearch = typeFoundBySearch;
    }

    /**
     * get the typeSelected
     * @return the typeSelected
     */
    public int getTypeSelected() {
        return typeSelected;
    }

    /**
     * set the typeSelected
     * @param typeSelected the typeSelected
     */
    @JsonIgnore
    public void setTypeSelected(final int typeSelected) {
        this.typeSelected = typeSelected;
    }

    /**
     * get the typeLoaded
     * @return the typeLoaded
     */
    public int getTypeLoaded() {
        return typeLoaded;
    }

    /**
     * set the typeLoaded
     * @param typeLoaded the typeLoaded
     */
    @JsonIgnore
    public void setTypeLoaded(final int typeLoaded) {
        this.typeLoaded = typeLoaded;
    }

    /**
     * get the currentSelect
     * @return the currentSelect
     */
    public Select getCurrentSelect() {
        return currentSelect;
    }

    /**
     * set the currentSelect
     * @param currentSelect the currentSelect
     */
    public void setCurrentSelect(final Select currentSelect) {
        this.currentSelect = currentSelect;
    }

    /**
     * get the likedSongs
     * @return the likedSongs
     */
    public ArrayList<Song> getLikedSongs() {
        return likedSongs;
    }

    /**
     * set the likedSongs
     * @param likedSongs the likedSongs
     */
    public void setLikedSongs(final ArrayList<Song> likedSongs) {
        this.likedSongs = likedSongs;
    }

    /**
     * get the shuffle
     * @return the shuffle
     */
    public boolean isShuffle() {
        return shuffle;
    }

    /**
     * set the shuffle
     * @param shuffle the shuffle
     */
    public void setShuffle(final boolean shuffle) {
        this.shuffle = shuffle;
    }

    /**
     * get the currentSearch
     * @return the currentSearch
     */
    public Search getCurrentSearch() {
        return currentSearch;
    }

    /**
     * set the currentSearch
     * @param currentSearch the currentSearch
     */
    public void setCurrentSearch(final Search currentSearch) {
        this.currentSearch = currentSearch;
    }

    /**
     * get the playListList
     * @return the playListList
     */
    public ArrayList<Playlist> getPlayListList() {
        return playListList;
    }

    /**
     * set the playListList
     * @param playListList the playListList
     */
    public void setPlayListList(final ArrayList<Playlist> playListList) {
        this.playListList = playListList;
    }

    /**
     * get the likedPlaylists
     * @return the likedPlaylists
     */
    public ArrayList<String> getLikedPlaylists() {
        return likedPlaylists;
    }

    /**
     * set the likedPlaylists
     * @param likedPlaylists the likedPlaylists
     */
    public void setLikedPlaylists(final ArrayList<String> likedPlaylists) {
        this.likedPlaylists = likedPlaylists;
    }

    /**
     * get the remainingTime
     * @return the remainingTime
     */
    public int getRemainingTime() {
        return remainingTime;
    }

    /**
     * set the remainingTime
     * @param remainingTime the remainingTime
     */
    public void setRemainingTime(final int remainingTime) {
        this.remainingTime = remainingTime;
    }

    /**
     * get the currentType
     * @return the currentType
     */
    public Type getCurrentType() {
        return currType;
    }

    /**
     * set the currentType
     * @param currentType the currentType
     */
    public void setCurrentType(final Type currentType) {
        this.currType = currentType;
    }

    /**
     * get the repeatString
     * @return the repeatString
     */
    public String getRepeatString() {
        return repeatString;
    }

    /**
     * add a podcast to the podcastsPlayed
     * @param podcast the podcast to be added
     */
    public void addPodcastPlayed(final Podcast podcast) {
        this.podcastsPlayed.add(podcast);
    }

    /**
     * get the podcastsPlayed
     * @return the podcastsPlayed
     */
    public ArrayList<Podcast> getPodcastsPlayed() {
        return podcastsPlayed;
    }

    /**
     * set the podcastsPlayed
     * @param podcastsPlayed the podcastsPlayed
     */
    public void setPodcastsPlayed(final ArrayList<Podcast> podcastsPlayed) {
        this.podcastsPlayed = podcastsPlayed;
    }

    /**
     * set the repeatString
     * @param repeatString the repeatString
     */
    public void setRepeatString(final String repeatString) {
        this.repeatString = repeatString;
    }

    /**
     * get the previous timestamp
     * @return the prevTimestamp
     */
    public int getPrevTimestamp() {
        return prevTimestamp;
    }

    /**
     * set the previous timestamp
     * @param prevTimestamp the previous timestamp
     */
    public void setPrevTimestamp(final int prevTimestamp) {
        this.prevTimestamp = prevTimestamp;
    }

    /**
     * get the currentPodcast
     * @return the currentPodcast
     */
    public Podcast getCurrentPodcast() {
        return currentPodcast;
    }

    /**
     * set the currentPodcast
     * @param currentPodcast the currentPodcast
     */
    public void setCurrentPodcast(final Podcast currentPodcast) {
        this.currentPodcast = currentPodcast;
    }

    /**
     * get the currentPlaylist
     * @return the currentPlaylist
     */
    public Playlist getCurrentPlaylist() {
        return currentPlaylist;
    }

    /**
     * set the currentPlaylist
     * @param currentPlaylist the currentPlaylist
     */
    public void setCurrentPlaylist(final Playlist currentPlaylist) {
        this.currentPlaylist = currentPlaylist;
    }

    /**
     * get the selectedName
     * @return the selectedName
     */
    public String getSelectedName() {
        return selectedName;
    }

    /**
     * set the selectedName
     * @param selectedName the selectedName
     */
    public void setSelectedName(final String selectedName) {
        this.selectedName = selectedName;
    }

    /**
     * get the shuffleSeed
     * @return the shuffleSeed
     */
    public int getShuffleSeed() {
        return shuffleSeed;
    }

    /**
     * set the shuffleSeed
     * @param shuffleSeed the shuffleSeed
     */
    public void setShuffleSeed(final int shuffleSeed) {
        this.shuffleSeed = shuffleSeed;
    }

    /**
     * get the shuffledIndices
     * @return the shuffledIndices
     */
    public ArrayList<Integer> getShuffledIndices() {
        return shuffledIndices;
    }

    /**
     * set the shuffledIndices
     * @param shuffledIndices the shuffledIndices
     */
    public void setShuffledIndices(final ArrayList<Integer> shuffledIndices) {
        this.shuffledIndices = shuffledIndices;
    }

    /**
     * get the selectedPlaylist
     * @return the selectedPlaylist
     */
    public Playlist getSelectedPlaylist() {
        return selectedPlaylist;
    }

    /**
     * set the selectedPlaylist
     * @param selectedPlaylist the selectedPlaylist
     */
    public void setSelectedPlaylist(final Playlist selectedPlaylist) {
        this.selectedPlaylist = selectedPlaylist;
    }

    /**
     * get the followedPlaylists
     * @return the followedPlaylists
     */
    public ArrayList<Playlist> getFollowedPlaylists() {
        return followedPlaylists;
    }

    /**
     * set the followedPlaylists
     * @param followedPlaylists the followedPlaylists
     */
    public void setFollowedPlaylists(final ArrayList<Playlist> followedPlaylists) {
        this.followedPlaylists = followedPlaylists;
    }

    /**
     * get the everySong
     * @return the everySong
     */
    public ArrayList<Song> getEverySong() {
        return everySong;
    }

    /**
     * set the everySong
     * @param everySong the everySong
     */
    public void setEverySong(final ArrayList<Song> everySong) {
        this.everySong = everySong;
    }

    /**
     * get the everyPodcast
     * @return the everyPodcast
     */
    public ArrayList<Podcast> getEveryPodcast() {
        return everyPodcast;
    }

    /**
     * set the everyPodcast
     * @param everyPodcast the everyPodcast
     */
    public void setEveryPodcast(final ArrayList<Podcast> everyPodcast) {
        this.everyPodcast = everyPodcast;
    }

    /**
     * get the next boolean
     * @return the next boolean
     */
    public boolean isNext() {
        return isNext;
    }

    /**
     * set the next boolean
     * @param next the next boolean
     */
    public void setNext(final boolean next) {
        isNext = next;
    }

    /**
     * get the original indices of the songs in the playlist
     * @return the original indices of the songs in the playlist
     */
    public ArrayList<Integer> getOriginalIndices() {
        return originalIndices;
    }

    /**
     * get the online status
     * @return the online status
     */
    public boolean getOnline() {
        return online;
    }

    /**
     * set the online status
     * @param online the online status
     */
    public void setOnline(final boolean online) {
        this.online = online;
    }

    /**
     * get the everyAlbum
     * @return the everyAlbum
     */
    public ArrayList<Album> getEveryAlbum() {
        return everyAlbum;
    }

    /**
     * gets selected album
     */
    public String getSelectedAlbum() {
        return selectedAlbum;
    }

    /**
     * get the searched song
     */
    public void setSelectedAlbum(final String selectedAlbum) {
        this.selectedAlbum = selectedAlbum;
    }

    /**
     * sets the searched song
     */
    public void setSearchedSong(final Song searchedSong) {
        this.searchedSong = searchedSong;
    }

    /**
     * verify if the user is premium
     */
    public boolean isPremium() {
        return premium;
    }

    /**
     * set the premium status
     * @param premium
     */
    public void setPremium(final boolean premium) {
        this.premium = premium;
    }

    /**
     * add notification
     */
    public void addNotification(final Map<String, String> notification) {
        this.notifications.add(notification);
    }

    /**
     * get notifications
     */
    public ArrayList<Map<String, String>> getNotifications() {
        return notifications;
    }

    /**
     * add notification
     */
    @Override
    public void update(final Map<String, String> notification) {
        this.addNotification(notification);
    }

    /**
     * get the bought merchandise
     */
    public ArrayList<Merch> getBoughtMerchandise() {
        return boughtMerchandise;
    }

    /**
     * add a bought merchandise
     */
    public void addBoughtMerchandise(final Merch merch) {
        this.boughtMerchandise.add(merch);
    }

    /**
     * get the listened episodes
     */
    public LinkedHashMap<Episode, Integer> getLisenedEpisodes() {
        return lisenedEpisodes;
    }

    /**
     * add a listened episode
     */
    public void addLisenedEpisode(final LinkedHashMap<Episode, Integer> listenedEpisode) {

//        if already exists update the number
        for (Map.Entry<Episode, Integer> entry : listenedEpisode.entrySet()) {
            Episode episode = entry.getKey();
            Integer number = entry.getValue();

            if (this.lisenedEpisodes.containsKey(episode)) {
                this.lisenedEpisodes.put(episode, this.lisenedEpisodes.get(episode) + number);
            } else {
                this.lisenedEpisodes.put(episode, number);
            }
        }
    }

    /**
     * set the listened episodes
     */
    public void setLisenedEpisodes(final LinkedHashMap<Episode, Integer> lisenedEpisodes) {
        this.lisenedEpisodes = lisenedEpisodes;
    }

    /**
     * get the page care taker
     */
    public PageCareTaker getPageCareTaker() {
        return pageCareTaker;
    }

    /**
     * get the current page
     */
    public Page getCurrentPage() {
        return currentPage;
    }

    /**
     * sets the current page
     */
    public void setCurrentPage(final Page currentPage) {
        this.currentPage = currentPage;
    }

}
