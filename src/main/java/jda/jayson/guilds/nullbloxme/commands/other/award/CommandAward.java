package jda.jayson.guilds.nullbloxme.commands.other.award;

import jda.jayson.file.JSON;
import jda.jayson.file.user.DiscordUser;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class CommandAward {

    public static void onEvent(MessageReceivedEvent e) {
        Message msg = e.getMessage();
        String content = msg.getContentRaw();
        String[] argument = content.split(" ");
        MessageChannel channel = e.getChannel();
        if (argument[0].equalsIgnoreCase("!award")) {
            Member user;
            if (msg.getMentionedMembers().size() > 0) {
                user = msg.getMentionedMembers().get(0);
            } else {
                user = msg.getGuild().getMembersByName(argument[2], true).get(0);
            }
            DiscordUser discordUser = JSON.loadUser(user.getId());

            if(argument[1].equalsIgnoreCase("add")) {
                Award award = AwardUtility.getAward(argument[3]);
                if(award != null) {
                    award.addAward(discordUser);
                    channel.sendMessage("gave Award \"" + award.getId() + "\" (" + award.getName() + ") to " + user.getAsMention()).complete();
                    JSON.saveUser(discordUser);
                } else {
                    channel.sendMessage("Award: " + argument[3] + " not found!").complete();
                }
            }

            if(argument[1].equalsIgnoreCase("remove")) {
                Award award = AwardUtility.getAward(argument[3]);
                if(award != null) {
                    award.removeAward(discordUser);
                    channel.sendMessage("removed Award \"" + award.getId() + "\" (" + award.getName() + ") from " + user.getAsMention()).complete();
                    JSON.saveUser(discordUser);
                } else {
                    channel.sendMessage("Award: " + argument[3] + " not found!").complete();
                }
            }
        }
    }

}
