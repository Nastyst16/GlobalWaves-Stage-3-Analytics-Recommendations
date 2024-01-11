//package main.decoratorPattern;
//
//import main.collections.Albums;
//import main.collections.Artists;
//import main.collections.Songs;
//import main.commands.types.Song;
//import main.commands.types.Type;
//import main.commands.types.Episode;
//import main.commands.types.Podcast;
//import main.commands.types.Album;
//
//import main.users.Artist;
//import main.users.User;
//
//import java.util.ArrayList;
//import java.util.LinkedHashMap;
//
//public class ListenCounterDecorator {
//    private final ArrayList<Type> types;
//
//    public ListenCounterDecorator() {
//        this.types = new ArrayList<>();
//    }
//
//    /**
//     * this method adds a listen to the current Type
//     */
//    public void listen(final Type type, final User user) {
////
//////                searching the song
////        for (Song s : user.getEverySong()) {
////            if (s.getName().equals(type.getName())
////                && s.getAlbum().equals(((Song) type).getAlbum())) {
////
////                s.addListen();
////
//////                addlisten to artist
////                for (Artist a : Artists.getArtists()) {
////                    if (a.getUsername().equals(s.getArtist())) {
////                        a.addListen();
////                        break;
////                    }
////                }
////
//////                adding the listen to the album
////                boolean found = false;
////
////                for (Album a : Albums.getAlbums()) {
////
////
////                    if (!s.getAlbum().equals(a.getName())) {
////                        continue;
////                    }
////
////                    for (Song song : a.getSongList()) {
////
////                        if (song.getName().equals(s.getName())) {
////                            a.addListen();
////                            found = true;
////                            break;
////                        }
////                    }
////                    if (found) {
////                        break;
////                    }
////                }
////                break;
////            }
////        }
////
////        for (Song s : Songs.getSongs()) {
////            if (s.getName().equals(type.getName())
////                    && s.getAlbum().equals(((Song) type).getAlbum())) {
////                s.addListen();
////            }
////        }
//
////////                searching the podcast
////        for (Podcast p : user.getPodcastsPlayed()) {
////
////            boolean found = false;
////
////            for (Episode e : p.getEpisodesList()) {
////                if (e.getName().equals(type.getName())) {
////                    e.addListen();
////                    p.addListen();
////
////                    LinkedHashMap<Episode, Integer> map = new LinkedHashMap<>();
////                    map.put(e, 1);
////
////                    user.addLisenedEpisode(map);
////
////                    found = true;
////                    break;
////                }
////            }
////            if (found) {
////                break;
////            }
////        }
//    }
//
//    /**
//     * this method returns the types
//     */
//    public ArrayList<Type> getTypes() {
//        return types;
//    }
//}
