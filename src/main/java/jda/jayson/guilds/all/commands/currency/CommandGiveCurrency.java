package jda.jayson.guilds.all.commands.currency;

import jda.jayson.ArrayCommand;
import jda.jayson.file.JSON;
import jda.jayson.id.ID;
import jda.jayson.id.References;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class CommandGiveCurrency {

    public static void onEvent(MessageReceivedEvent event) {
        Message msg = event.getMessage();
        String content = msg.getContentRaw();
        String argument[] = content.split( " ");
        if(msg.getContentRaw().contains("!" + ID.currency.toLowerCase() + " give")) {
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
            JSON.load(String.valueOf(event.getAuthor().getIdLong()));
            Integer currency_to_give;
            try {
              currency_to_give = Integer.parseInt(argument[3]);
          } catch (Exception e) {
              event.getChannel().sendMessage("> Please use the correct Arguments! (!" + ID.currency + " give (user) (amount))").complete();
              return;
          }
            if (References.currency > currency_to_give || References.currency == currency_to_give) {
                References.currency -= currency_to_give;
                JSON.save(String.valueOf(event.getAuthor().getIdLong()));
                JSON.load(String.valueOf(user.getIdLong()));
                References.currency += currency_to_give;
                JSON.save(String.valueOf(user.getIdLong()));
                event.getChannel().sendMessage("> Gave " + user.getAsMention() + " " + currency_to_give + " " + ID.currency + "!").complete();
            } else {
                event.getChannel().sendMessage("> You don't have enough " + ID.currency + "!").complete();
            }
        }
    }
}
