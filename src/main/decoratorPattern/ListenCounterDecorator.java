package main.decoratorPattern;

import main.collections.Albums;
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


//                searching the song
        for (Song s : user.getEverySong()) {
            if (s.getName().equals(type.getName())) {

                if (user.getCurrentPlaylist() != null)
                    if (!s.getAlbum().equals(user.getCurrentPlaylist().getName()))
                        continue;

                s.addListen();
//                adding the listen to the album
                for (Album a : Albums.getAlbums()) {

                    if (s.getAlbum().equals("Chuck Berry Is On Top")) {
                        int x = 5;
                    }

                    if (!s.getAlbum().equals(a.getName()))
                        continue;

//                    if (a.getName().equals(s.getAlbum())
//                        && a.getReleaseYear() == s.getReleaseYear()) {
//                        a.addListen();
//                        break;
//                    }
                    for (Song song : a.getSongList()) {

                        if (song.getName().equals(s.getName())) {
                            a.addListen();
                            break;
                        }
                    }
                }
                break;
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
