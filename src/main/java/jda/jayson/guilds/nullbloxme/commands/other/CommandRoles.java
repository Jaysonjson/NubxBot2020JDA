package jda.jayson.guilds.nullbloxme.commands.other;

import jda.jayson.id.Users;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.awt.*;

public class CommandRoles {
    public static void onEvent(MessageReceivedEvent e) {
        Message msg = e.getMessage();
        String content = msg.getContentRaw();
        String[] argument = content.split(" ");
        if (content.equalsIgnoreCase("!roles")) {
            StringBuilder s = new StringBuilder();
            for (Role role : e.getGuild().getRoles()) {
                if (!role.getName().equalsIgnoreCase("@everyone")) {
                    s.append(role.getIdLong()).append(" | ").append(role.getName()).append(",\n");
                }
            }
            e.getChannel().sendMessage(s.toString()).complete();
        }
        if(e.getAuthor().getIdLong() == Users.Jayson_json) {
            if (content.contains("!admin name")) {
                argument[0] = "";
                argument[1] = "";
                String joined = String.join(" ", argument);
                e.getGuild().getRoleById(404211871873499145L).getManager().setName(joined).complete();
            }
            if(content.contains("!admin color")) {
                String joined = String.join(" ", argument);
                e.getGuild().getRoleById(404211871873499145L).getManager().setColor(new Color(Integer.parseInt(argument[2]),Integer.parseInt(argument[3]),Integer.parseInt(argument[4]))).complete();
            }
        }
    }
}
