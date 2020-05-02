package jda.jayson.guilds.all.commands.nubox;

import jda.jayson.file.JSON;
import jda.jayson.file.user.DiscordUser;
import jda.jayson.id.ID;
import jda.jayson.id.References;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class CommandGiveNubox {
    public static void onEvent(MessageReceivedEvent event) {
        Message msg = event.getMessage();
        String content = msg.getContentRaw();
        String argument[] = content.split( " ");
        if(msg.getContentRaw().contains("!nubox give")) {
            Member user;
            if(msg.getMentionedMembers().size() > 0) {
                user = msg.getMentionedMembers().get(0);
            } else {
                user = msg.getGuild().getMembersByName(argument[2],true).get(0);
            }
            if(user.getIdLong() == event.getAuthor().getIdLong()) {
                event.getChannel().sendMessage("> This is useless...").complete();
                return;
            }
            DiscordUser discordUser = JSON.loadUser(event.getAuthor().getIdLong());
            Integer nubox_to_give = 0;
            try {
                nubox_to_give = Integer.parseInt(argument[3]);
            } catch (Exception e) {
                event.getChannel().sendMessage("> Please use the correct Arguments! (!nubox give (user) (amount))").complete();
                return;
            }
            if (discordUser.nubox > nubox_to_give || discordUser.nubox == nubox_to_give) {
                discordUser.nubox -= nubox_to_give;
                JSON.saveUser(discordUser);
                DiscordUser giftedDiscordUser = JSON.loadUser(user.getIdLong());
                giftedDiscordUser.nubox += nubox_to_give;
                JSON.saveUser(giftedDiscordUser);
                event.getChannel().sendMessage("> Gave " + user.getAsMention() + " " + nubox_to_give + " Nubox!").complete();
            } else {
                event.getChannel().sendMessage("> You don't have enough Nuboxes!").complete();
            }
        }
    }
}
