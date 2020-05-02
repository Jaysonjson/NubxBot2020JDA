package jda.jayson.guilds.all.commands.filemanagement;

import jda.jayson.file.JSON;
import jda.jayson.id.ID;
import jda.jayson.id.References;
import jda.jayson.id.Roles;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class CommandSetCurrency {
    public static void onEvent(MessageReceivedEvent event) {
        Message msg = event.getMessage();
        String content = msg.getContentRaw();
        String argument[] = content.split(" ");
            if (msg.getContentRaw().contains("!" + ID.currency.toLowerCase() + " set")) {
                Member user;
                if (msg.getMentionedMembers().size() > 0) {
                    user = msg.getMentionedMembers().get(0);
                } else {
                    user = msg.getGuild().getMembersByName(argument[2], true).get(0);
                }
                Integer currency_to_give = 0;
                try {
                    currency_to_give = Integer.parseInt(argument[3]);
                } catch (Exception e) {
                    event.getChannel().sendMessage("> Please use the correct Arguments! (!" + ID.currency + " set (user) (amount))").complete();
                    return;
                }
                JSON.load(String.valueOf(user.getIdLong()));
                References.currency = currency_to_give;
                JSON.save(String.valueOf(user.getIdLong()));
                event.getChannel().sendMessage("> Set " + user.getAsMention() + "'s " + ID.currency + " to `" + currency_to_give + "`!").complete();
            }
    }
}
