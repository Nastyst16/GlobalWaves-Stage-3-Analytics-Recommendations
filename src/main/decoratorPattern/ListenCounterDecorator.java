package main.decoratorPattern;

import main.commands.types.Podcast;
import main.commands.types.Song;
import main.commands.types.Type;
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
                s.addListen();
                break;
            }
        }

//                searching the podcast
        for (Podcast p : user.getEveryPodcast()) {
            if (p.getName().equals(user.getCurrentType().getName())) {
                p.addListen();
            }
        }

////                searching the episode
//                for (Episode e : user) {
//                    if (e.getName().equals(user.getCurrentType().getName())) {
//                        e.addListen();
//                    }
//                }

        int pula = 5;














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
