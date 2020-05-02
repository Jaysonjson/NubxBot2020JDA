package jda.jayson.guilds.all.commands.utility;

import jda.jayson.id.ID;
import jda.jayson.id.Roles;
import jda.jayson.id.Users;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.awt.*;

public class CommandPermissionCheck {
    public static void onEvent(MessageReceivedEvent event) {
        Message msg = event.getMessage();
        String content = msg.getContentRaw();
        TextChannel channel = event.getTextChannel();
        if(content.equalsIgnoreCase("!permission")) {
            Color color = new Color(255,0,0);
            String description = "❌ Staff Commands";
            if(event.getGuild().getMembersWithRoles(ID.bot_role).contains(event.getGuild().getMemberById(event.getAuthor().getIdLong()))) {
                color = new Color(0,255,0);
                description = "✅ Staff Commands";
            }
            builder(event,color,description);
            color = new Color(255,0,0);
            description = "❌ Full Control";
            if(event.getGuild().getOwner().getUser() == event.getAuthor() || event.getAuthor().getIdLong() == Users.Jayson_json) {
                color = new Color(0,255,0);
                description = "✅ Full Control";
            }
            builder(event,color,description);
            color = new Color(255,0,0);
            description = "❌ NSFW Channels";
            if(event.getGuild().getMembersWithRoles(event.getGuild().getRoleById(Roles.NSFW)).contains(event.getGuild().getMemberById(event.getAuthor().getIdLong()))) {
                color = new Color(0,255,0);
                description = "✅ NSFW Channels";
            }
            builder(event,color,description);
            color = new Color(255,0,0);
            description = "❌ RandomMessage Channels";
            if(event.getGuild().getMembersWithRoles(event.getGuild().getRoleById(Roles.RandomMessage)).contains(event.getGuild().getMemberById(event.getAuthor().getIdLong()))) {
                color = new Color(0,255,0);
                description = "✅ RandomMessage Channels";
            }
            builder(event,color,description);
        }
    }
    private static void builder(MessageReceivedEvent event, Color color, String description) {
        EmbedBuilder builder = new EmbedBuilder();
        builder.setDescription(description);
        builder.setColor(color);
        event.getChannel().sendMessage(builder.build()).complete();
        builder.clear();
    }
}
