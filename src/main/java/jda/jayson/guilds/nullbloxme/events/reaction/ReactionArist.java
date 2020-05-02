package jda.jayson.guilds.nullbloxme.events.reaction;

import jda.jayson.id.Channels;
import jda.jayson.id.Roles;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.events.message.react.MessageReactionAddEvent;
import net.dv8tion.jda.api.events.message.react.MessageReactionRemoveEvent;

public class ReactionArist {

    public static void onReact(MessageReactionAddEvent e) {
        MessageChannel channel = e.getChannel();
        Member user = e.getMember();
        if(channel == e.getGuild().getTextChannelById(Channels.Artist_Set)) {
            if(e.getMessageIdLong() == 649744339194609689L) {
                if(!e.getMember().getRoles().contains(e.getGuild().getRoleById(Roles.Artist_To_Hire))) {
                    user.getUser().openPrivateChannel().complete().sendMessage("> You now receive pings inside " + e.getGuild().getTextChannelById(Channels.Recruitment).getAsMention() + "!").complete();
                    e.getGuild().addRoleToMember(user, e.getGuild().getRoleById(Roles.Artist_To_Hire)).complete();
                } else {
                    user.getUser().openPrivateChannel().complete().sendMessage("> You receive pings already!").complete();
                }
            }
            if(e.getMessageIdLong() == 649746180560912424L) {
                if(!e.getMember().getRoles().contains(e.getGuild().getRoleById(Roles.ThreeD_Artist))) {
                    user.getUser().openPrivateChannel().complete().sendMessage("> You now have the 3D Artist Role!").complete();
                    e.getGuild().addRoleToMember(user, e.getGuild().getRoleById(Roles.ThreeD_Artist)).complete();
                } else {
                    user.getUser().openPrivateChannel().complete().sendMessage("> You have the 3D Artist role already! Unreact if you want to remove it!").complete();
                }
            }
            if(e.getMessageIdLong() == 649746203067809796L) {
                if(!e.getMember().getRoles().contains(e.getGuild().getRoleById(Roles.TwoD_Artist))) {
                    user.getUser().openPrivateChannel().complete().sendMessage("> You now have the 2D Artist Role!").complete();
                    e.getGuild().addRoleToMember(user, e.getGuild().getRoleById(Roles.TwoD_Artist)).complete();
                } else {
                    user.getUser().openPrivateChannel().complete().sendMessage("> You have the 2D Artist role already! Unreact if you want to remove it!").complete();
                }
            }
        }
    }
    public static void onUnReact(MessageReactionRemoveEvent e) {
        MessageChannel channel = e.getChannel();
        Member user = e.getMember();
        if(channel == e.getGuild().getTextChannelById(Channels.Artist_Set)) {
            if(e.getMessageIdLong() == 649744339194609689L) {
                if(e.getMember().getRoles().contains(e.getGuild().getRoleById(Roles.Artist_To_Hire))) {
                    user.getUser().openPrivateChannel().complete().sendMessage("> You no longer receive pings inside " + e.getGuild().getTextChannelById(Channels.Recruitment).getAsMention() + "!").complete();
                    e.getGuild().removeRoleFromMember(user, e.getGuild().getRoleById(Roles.Artist_To_Hire)).complete();
                }
            }
            if(e.getMessageIdLong() == 649746180560912424L) {
                if(e.getMember().getRoles().contains(e.getGuild().getRoleById(Roles.ThreeD_Artist))) {
                    user.getUser().openPrivateChannel().complete().sendMessage("> You no longer have the 3D Artist Role!").complete();
                    e.getGuild().removeRoleFromMember(user, e.getGuild().getRoleById(Roles.ThreeD_Artist)).complete();
                }
            }
            if(e.getMessageIdLong() == 649746203067809796L) {
                if(e.getMember().getRoles().contains(e.getGuild().getRoleById(Roles.TwoD_Artist))) {
                    user.getUser().openPrivateChannel().complete().sendMessage("> You no longer have the 2D Artist Role!").complete();
                    e.getGuild().removeRoleFromMember(user, e.getGuild().getRoleById(Roles.TwoD_Artist)).complete();
                }
            }
        }
    }
}
