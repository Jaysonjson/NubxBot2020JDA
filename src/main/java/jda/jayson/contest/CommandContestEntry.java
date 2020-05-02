package jda.jayson.contest;

import jda.jayson.id.Channels;
import jda.jayson.id.Emojis;
import jda.jayson.id.Guilds;
import jda.jayson.id.Roles;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.priv.PrivateMessageReceivedEvent;

import java.util.Objects;

public class CommandContestEntry {
    public static void onEvent(PrivateMessageReceivedEvent event) {
        String[] argument = event.getMessage().getContentRaw().split(" ");
        if(event.getMessage().getContentRaw().contains("!contest")) {
            ContestSave.load();
            for (Long aLong : ContestSave.contest_plus_count.keySet()) {
                if(aLong == event.getAuthor().getIdLong()) {
                    event.getChannel().sendMessage("> You're in the Contest already!").complete();
                    return;
                }
            }
            if (event.getMessage().getAttachments().size() > 0) {
                if(argument.length < 2) {
                    event.getChannel().sendMessage("> Not enough Arguments! : !contest (title)").complete();
                    return;
                }
                    String img_url = event.getMessage().getAttachments().get(0).getUrl();
                    String title = String.join(" ", argument);
                    title = title.substring(argument[0].length());
                    Guild guild = event.getJDA().getGuildById(Guilds.Nullbloxme);
                assert guild != null;
                TextChannel contest_ch = guild.getTextChannelById(Channels.Contest_Voting);
                assert contest_ch != null;
                Message img_msg = contest_ch.sendMessage(event.getAuthor().getName() + "'s Contest Entry : " + title + "\n" + img_url).complete();
                    img_msg.addReaction(Emojis.EmojiBuilder(Emojis.Plus_One,Emojis.Plus_One_ID)).complete();
                    img_msg.addReaction(Emojis.EmojiBuilder(Emojis.Minus_One,Emojis.Minus_One_ID)).complete();
                    ContestSave.contest_message.add(img_msg.getIdLong());
                    ContestSave.contest_plus_count.put(event.getAuthor().getIdLong(), 0);
                    ContestSave.contest_message_user.put(img_msg.getIdLong(), event.getAuthor().getIdLong());
                    guild.addRoleToMember(Objects.requireNonNull(guild.getMemberById(event.getAuthor().getIdLong())), Objects.requireNonNull(guild.getRoleById(Roles.Contest))).complete();
                    event.getChannel().sendMessage("Successfully sent Entry to Channel!").complete();
            } else {
                event.getChannel().sendMessage("> Please provide an Image!").complete();
            }
            ContestSave.save();
        }
    }
}
