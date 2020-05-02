package jda.jayson.guilds.nullbloxme.events.other;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;

import java.awt.*;
import java.time.Instant;

public class EventJoin {
    public static void onEvent(GuildMemberJoinEvent event) {
       TextChannel channel = event.getGuild().getTextChannelById(404645248040435713L);
       User user = event.getMember().getUser();
        EmbedBuilder builder = new EmbedBuilder();
        builder.setTimestamp(Instant.now());
        builder.setTitle(user.getName());
        builder.setColor(new Color(69, 65, 94));
        builder.setDescription(user.getAsMention() + " has joined " + event.getGuild().getName() + "!");
        builder.setAuthor(user.getName(),user.getAvatarUrl(),user.getAvatarUrl());
        builder.addField("ID", user.getId(),true);
        assert channel != null;
        channel.sendMessage(builder.build()).complete();
    }
}
