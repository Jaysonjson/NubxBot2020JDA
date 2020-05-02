package jda.jayson.guilds.all.commands.utility;

import jda.jayson.file.BotCMD;
import jda.jayson.id.References;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class CommandCreation {
    public static void onEvent(MessageReceivedEvent event) {
        Message msg = event.getMessage();
        String[] argument = msg.getContentRaw().split(" ");
        if (msg.getContentRaw().contains("!creation") && !msg.getContentRaw().contains("Print") && !msg.getContentRaw().contains("player") && !msg.getContentRaw().contains("creations")) {
            try {
                BotCMD.load();
                event.getChannel().sendMessage("> The Creation with the Number #" + argument[1] + " was sent by " + event.getGuild().getMemberById(References.creation_player.get(Integer.parseInt(argument[1]))).getUser().getAsTag()).complete();
                event.getChannel().sendMessage(References.creation_player_url.get(Integer.parseInt(argument[1]))).complete();
                BotCMD.save();
            } catch (Exception exc) {
                event.getChannel().sendMessage("> No Creation with Number #" + argument[1] + " found").complete();
            }
        }
        if(msg.getContentRaw().contains("!creation player")) {
            Member user;
            if(msg.getMentionedMembers().size() > 0) {
                user = msg.getMentionedMembers().get(0);
            } else {
                user = msg.getGuild().getMembersByName(argument[2],true).get(0);
            }
            BotCMD.load();
           // Map<Long, Integer> mapInversed = References.creation_player.entrySet().stream().collect(Collectors.toMap(Map.Entry::getValue, Map.Entry::getKey));
            List<Integer> list = new ArrayList<>();
            for (Integer integer : References.creation_player.keySet()) {
                if(References.creation_player.get(integer).equals(user.getIdLong())) {
                    list.add(integer);
                }
            }
            Integer length = String.valueOf(list).length();
            length -= 1;
            String w = String.valueOf(list).substring(1, length);
            event.getChannel().sendMessage("> Found Numbers: `" + w + "`").complete();
        }
        if(msg.getContentRaw().equalsIgnoreCase("!creations")) {
            BotCMD.load();
            String string = "";
            Integer i = 0;
            String creations = "";
            Integer last = 0;
            Long current_player = 0L;
            for (Integer integer : References.creation_player.keySet()) {
                i++;
                current_player = References.creation_player.get(integer);
                if (string.length() < 1500) {
                    for(Integer integer1 : References.creation_player.keySet()) {
                        for (Integer integer2 : References.creation_player.keySet()) {
                            if(References.creation_player.get(integer).equals(current_player)) {
                                creations += integer2 + ",";
                                last = integer2;
                            }
                        }
                    }
                    if (i != References.creation_player.size() && last.equals(integer)) {
                        string += "**" + event.getGuild().getMemberById(References.creation_player.get(integer)).getUser().getName() + "** -- `" + creations + "`,\n";
                    } else if (i == References.creation_player.size() && last.equals(integer)) {
                        string += "**" + event.getGuild().getMemberById(References.creation_player.get(integer)).getUser().getName() + "** -- `" + creations + "`";
                    }
                    creations = "";
                }
                if (string.length() > 1499) {
                    event.getChannel().sendMessage(string).complete();
                    string = "";
                }
            }
            if (!string.equalsIgnoreCase("")) {
                event.getChannel().sendMessage(string).complete();
            }
        }
    }
}
