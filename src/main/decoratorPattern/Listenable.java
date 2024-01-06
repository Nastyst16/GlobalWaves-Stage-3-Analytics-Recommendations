package main.decoratorPattern;

import main.commands.types.Type;

public interface Listenable {
    void listen(Type type);
}
