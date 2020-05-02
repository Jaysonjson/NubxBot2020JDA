package jda.jayson.contest;

import jda.jayson.id.Channels;
import jda.jayson.id.Emojis;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.react.MessageReactionAddEvent;

import java.util.Objects;

public class ContestReaction {
    public static void onEvent(MessageReactionAddEvent event) {
        if(event.getChannel().getIdLong() == Channels.Contest_Voting) {
            if (event.getReactionEmote().getIdLong() == Emojis.Plus_One_ID || event.getReactionEmote().getIdLong() == Emojis.Minus_One_ID) {
                ContestSave.load();
                for (Long aLong : ContestSave.contest_plus_count.keySet()) {
                    if(Objects.requireNonNull(event.getMember()).getIdLong() == aLong) {
                        event.getMember().getUser().openPrivateChannel().complete().sendMessage("> Sorry, you can't vote!").complete();
                        return;
                    }
                }
            if(ContestSave.contest_message.contains(event.getMessageIdLong())) {
                   User user = event.getGuild().getMemberById(ContestSave.contest_message_user.get(event.getMessageIdLong())).getUser();
                   Integer points = ContestSave.contest_plus_count.get(user.getIdLong());
                    if(ContestSave.contest_reaction_plus.containsKey(event.getUser().getIdLong()) && ContestSave.contest_reaction_minus.containsKey(event.getUser().getIdLong())) {
                        return;
                    }
                    if(!ContestSave.contest_reaction_plus.containsKey(event.getUser().getIdLong())) {
                        if (event.getReactionEmote().getIdLong() == Emojis.Plus_One_ID) {
                            points++;
                            ContestSave.contest_reaction_plus.put(event.getUser().getIdLong(),true);
                        }
                    } else {
                        return;
                    }
                    if(!ContestSave.contest_reaction_minus.containsKey(event.getUser().getIdLong())) {
                        if (event.getReactionEmote().getIdLong() == Emojis.Minus_One_ID) {
                            points--;
                            ContestSave.contest_reaction_minus.put(event.getUser().getIdLong(),true);
                        }
                    } else {
                        return;
                    }
                   ContestSave.contest_plus_count.put(user.getIdLong(),points);
                   event.getUser().openPrivateChannel().complete().sendMessage("> Successfully voted!").complete();
                }
                ContestSave.save();
            }
        }
    }
}
