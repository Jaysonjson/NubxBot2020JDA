package jda.jayson.command.ignore;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class Commands {
    public static CommandRegistry<MessageReceivedEvent> MESSAGE_RECEIVED = new CommandRegistry<>();
    public static Command<ICommand> OTHER = MESSAGE_RECEIVED.register();
}
