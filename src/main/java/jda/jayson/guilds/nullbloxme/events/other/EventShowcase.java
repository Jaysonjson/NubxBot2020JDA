package jda.jayson.guilds.nullbloxme.events.other;

import jda.jayson.file.BotCMD;
import jda.jayson.file.JSON;
import jda.jayson.file.user.DiscordUser;
import jda.jayson.id.Channels;
import jda.jayson.id.Emojis;
import jda.jayson.id.References;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class EventShowcase {
    public static void onEvent(MessageReceivedEvent e) {
        Message msg = e.getMessage();
        User user = e.getAuthor();
        Long user_id = user.getIdLong();
        boolean enabled = false;
        if (e.getChannel().getIdLong() == Channels.Showcase || e.getChannel().getIdLong() == 467093973656272926L) {
            if(!msg.getContentRaw().contains("[P]")) {
                if (msg.getContentRaw().contains("imgur") || msg.getContentRaw().contains("sketchfab.com") || msg.getContentRaw().contains("skfb.ly") || msg.getContentRaw().contains("https://")) {
                    enabled = true;
                }
                if (msg.getAttachments().size() > 0 || enabled) {
                    BotCMD.load();
                    References.creationcount_showcase++;
                    References.creation_player.put(References.creationcount_showcase, user_id);
                    if (msg.getAttachments().size() > 0) {
                        References.creation_player_url.put(References.creationcount_showcase, msg.getAttachments().get(0).getUrl());
                    } else {
                        References.creation_player_url.put(References.creationcount_showcase, msg.getContentRaw());
                    }
                    DiscordUser discordUser = JSON.loadUser(user_id);
                    discordUser.model_count++;
                    JSON.saveUser(String.valueOf(user_id), discordUser);
                    String count = String.valueOf(References.creationcount_showcase);
                    String a;
                    a = count.replace("0", "0⃣ ");
                    a = a.replace("1", "1⃣ ");
                    a = a.replace("2", "2⃣ ");
                    a = a.replace("3", "3⃣ ");
                    a = a.replace("4", "4⃣ ");
                    a = a.replace("5", "5⃣ ");
                    a = a.replace("6", "6⃣ ");
                    a = a.replace("7", "7⃣ ");
                    a = a.replace("8", "8⃣ ");
                    a = a.replace("9", "9⃣ ");

                    String finalA = a;
                    msg.addReaction(Emojis.EmojiBuilder(Emojis.Plus_One, Emojis.Plus_One_ID)).complete();
                    msg.addReaction(Emojis.EmojiBuilder(Emojis.Minus_One, Emojis.Minus_One_ID)).complete();
                    for (String s : finalA.split(" ")) {
                        msg.addReaction((s)).complete();
                    }
                    BotCMD.save();
                }
            }
        }
    }
}
