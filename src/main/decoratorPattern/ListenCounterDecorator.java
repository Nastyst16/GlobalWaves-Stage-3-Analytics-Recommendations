package main.decoratorPattern;

import main.collections.Albums;
import main.collections.Songs;
import main.commands.types.*;
import main.users.User;

import java.util.ArrayList;

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

        if (type instanceof Song) {
            if (((Song) type).getAlbum().equalsIgnoreCase("the 50th anniversary collection")
                    && user.getUsername().equals("jack29")) {
                int x = 5;
//                printing the song
                System.out.println("Song: " + type.getName());
            }
        }

//                searching the song
        for (Song s : user.getEverySong()) {
            if (s.getName().equals(type.getName())
                && s.getAlbum().equals(((Song)type).getAlbum())) {

//                if (user.getCurrentPlaylist() != null)
//                    if (!s.getAlbum().equals(user.getCurrentPlaylist().getName()))
//                        continue;


                s.addListen();
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


////                searching the podcast
//        for (Podcast p : user.getEveryPodcast()) {
//            if (p.getName().equals(user.getCurrentType().getName())) {
//                p.addListen();
//            }
//        }

//                searching the episode
        for (Podcast p : user.getEveryPodcast()) {
            for (Episode e : p.getEpisodesList()) {
                if (e.getName().equals(user.getCurrentType().getName())) {
                    e.addListen();
                }
            }
        }





        int x = 5;










//        for (Type t : types) {
//            if (t.getName().equals(type.getName())) {
//                t.addListen();
//            }
//        }
//
////        if we didn't find the type, we add it
//        addType(type);
    }

    public ArrayList<Type> getTypes() {
        return types;
    }


}
