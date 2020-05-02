package jda.jayson.guilds.all.commands.utility;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.awt.*;
import java.util.Random;

public class CommandOther {
    public static void onEvent(MessageReceivedEvent event) {
        Message msg = event.getMessage();
        String[] argument = msg.getContentRaw().split(" ");
        if(msg.getContentRaw().equalsIgnoreCase("@someone")) {
            Random r = new Random();
            Integer i = r.nextInt(event.getGuild().getMembers().size());
               Member user = event.getGuild().getMembers().get(i);
                    EmbedBuilder builder = new EmbedBuilder();
                    builder.addField("Name",user.getUser().getName(),true);
                    builder.setThumbnail(user.getUser().getEffectiveAvatarUrl());
                    builder.addField("ID", String.valueOf(user.getIdLong()),true);
                    builder.addField("Number", String.valueOf(i),true);
                    builder.setColor(new Color(69, 65, 94));
                    msg.getChannel().sendMessage(builder.build()).complete();
            }
        if(msg.getContentRaw().contains("say")) {
            String joined = String.join(" ", argument);
            joined = joined.substring(argument[0].length());
            event.getChannel().sendMessage(joined).complete();
        }
    }
}