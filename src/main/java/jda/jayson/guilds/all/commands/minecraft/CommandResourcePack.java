package jda.jayson.guilds.all.commands.minecraft;

import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class CommandResourcePack
{
    public static void onEvent(MessageReceivedEvent e) {
        Message msg = e.getMessage();
        String content = msg.getContentRaw();
        if(content.contains("!resourcepack pack")) {

        }
    }
}
