package jda.jayson.command.ignore;

import java.util.ArrayList;

public class CommandRegistry<T> {
    ArrayList<ICommand> commands = new ArrayList<>();

    private T event;

    public CommandRegistry(T event) {
        this.event = event;
    }

    @Deprecated
    public CommandRegistry() {

    }

    public T getEvent() {
        return event;
    }

    public Command<ICommand> register()
    {
        Command<ICommand> ret = new Command<>();
        if(!commands.contains(ret)) {
            commands.add((ICommand) ret);
        }
        return ret;
    }
}
