package jda.jayson.guilds.nullbloxme.events.reaction;

import jda.jayson.id.Channels;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.react.MessageReactionAddEvent;

public class ReactionOR {
    public static void onEvent(MessageReactionAddEvent e) {
        MessageChannel channel = e.getChannel();
        Member user = e.getMember();
        if(channel == e.getGuild().getTextChannelById(Channels.Rules_Offering)) {
            e.getGuild().addRoleToMember(user, e.getGuild().getRoleById(608492293334237195L)).complete();
        }
        if(channel == e.getGuild().getTextChannelById(Channels.Rules_Recruitment)) {
            e.getGuild().addRoleToMember(user, e.getGuild().getRoleById(608492325487771669L)).complete();
        }
    }
}
