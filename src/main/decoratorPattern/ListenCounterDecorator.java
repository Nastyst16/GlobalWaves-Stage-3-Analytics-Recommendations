package main.decoratorPattern;

import main.collections.Albums;
import main.collections.Artists;
import main.collections.Songs;
import main.commands.types.*;
import main.users.Artist;
import main.users.User;

import java.util.ArrayList;
import java.util.LinkedHashMap;

public class ListenCounterDecorator {
    private final ArrayList<Type> types;

    public ListenCounterDecorator() {
        this.types = new ArrayList<>();
    }

    private void addType(Type type) {

//        adding the type only if it doesn't exist
        for (Type t : types) {
            if (t.getName().equals(type.getName())) {
                return;
            }
        }
        types.add(type);
        type.addListen();
    }


//    @Override
    public void listen(Type type, User user) {

//        if (type instanceof Song) {
//            if (((Song) type).getAlbum().equalsIgnoreCase("the 50th anniversary collection")
//                    && user.getUsername().equals("jack29")) {
//                int x = 5;
////                printing the song
//                System.out.println("Song: " + type.getName());
//            }
//        }

//                searching the song
        for (Song s : user.getEverySong()) {
            if (s.getName().equals(type.getName())
                && s.getAlbum().equals(((Song)type).getAlbum())) {

//                if (user.getCurrentPlaylist() != null)
//                    if (!s.getAlbum().equals(user.getCurrentPlaylist().getName()))
//                        continue;


                s.addListen();

//                addlisten to artist
                for (Artist a : Artists.getArtists()) {
                    if (a.getUsername().equals(s.getArtist())) {
                        a.addListen();
                        break;
                    }
                }

//                adding the listen to the album
                boolean found = false;

                for (Album a : Albums.getAlbums()) {


                    if (!s.getAlbum().equals(a.getName()))
                        continue;

                    for (Song song : a.getSongList()) {

                        if (song.getName().equals(s.getName())) {
                            a.addListen();
                            found = true;
                            break;
                        }
                    }
                    if (found)
                        break;
                }
                break;
            }
        }

        for (Song s : Songs.getSongs()) {
            if (s.getName().equals(type.getName())
                    && s.getAlbum().equals(((Song)type).getAlbum())) {
                s.addListen();
            }
        }


        if (user.getUsername().equals("alice22") && user.getPodcastsPlayed() != null) {
            int x = 5;
        }


////                searching the podcast
        for (Podcast p : user.getPodcastsPlayed()) {

            boolean found = false;

            for (Episode e : p.getEpisodesList()) {
                if (e.getName().equals(type.getName())) {
                    e.addListen();
                    p.addListen();

                    LinkedHashMap<Episode, Integer> map = new LinkedHashMap<>();
                    map.put(e, 1);

                    user.addLisenedEpisode(map);

                    found = true;
                    break;
                }
            }
            if (found)
                break;
        }



        int x = 5;



    }

    public ArrayList<Type> getTypes() {
        return types;
    }


}
