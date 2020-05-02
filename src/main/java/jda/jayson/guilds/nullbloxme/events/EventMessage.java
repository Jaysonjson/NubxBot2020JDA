package jda.jayson.guilds.nullbloxme.events;

import jda.jayson.id.Users;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.util.Random;

public class EventMessage {
    public static void onEvent(MessageReceivedEvent event) {
        if(event.getAuthor().getIdLong() == Users.Jayson_json) {
            Random r = new Random();
            if(r.nextInt(100) == 1) {
                event.getMessage().addReaction("\uD83D\uDE21").complete();
            }
        }
    }
}
