package main.decoratorPattern;

import main.commands.types.Type;
import main.users.User;

public abstract class TypeDecorator implements Type {
    protected Type decoratedType;

    public TypeDecorator(Type decoratedType) {
        this.decoratedType = decoratedType;
    }

    @Override
    public void listen(final User user) {
        decoratedType.listen(user);
    }
}
