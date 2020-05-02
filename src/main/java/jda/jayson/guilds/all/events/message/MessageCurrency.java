package jda.jayson.guilds.all.events.message;

import jda.jayson.file.JSON;
import jda.jayson.file.user.DiscordUser;
import jda.jayson.guilds.nullbloxme.commands.other.inventory.InventoryItem;
import jda.jayson.id.Arrays;
import jda.jayson.id.ID;
import jda.jayson.id.References;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.util.Timer;
import java.util.TimerTask;

public class MessageCurrency {
    public static void onEvent(MessageReceivedEvent event) {
        DiscordUser discordUser = JSON.loadUser(event.getAuthor().getIdLong());
        discordUser.points = (discordUser.model_count * 3) + (discordUser.message_count / 2) + (discordUser.currency / 3) + (discordUser.shop_items_sold * 2) + (discordUser.contests_won * 8);
        if (event.getChannel().getIdLong() != 582281584020029472L) {
            discordUser.message_currency_count++;
            discordUser.message_nubox_count++;
            discordUser.message_count++;
            discordUser.message_item_count++;
            discordUser.discord.name = event.getAuthor().getName();
            if (discordUser.message_currency_count > 45) {
                boolean banned_nubx = false;
                discordUser.currency += 25;
                discordUser.message_currency_count = 0;
                for (Long aLong : Arrays.currency_banned_channel) {
                    if (event.getChannel().getIdLong() != aLong) {
                        banned_nubx = false;
                    }
                    if (event.getChannel().getIdLong() == aLong) {
                        banned_nubx = true;
                    }
                }

                if (!banned_nubx) {
                    Message nubx_message = event.getChannel().sendMessage("You have earned 25 " + ID.currency + "!").complete();
                    Timer t = new Timer();
                    t.schedule(new TimerTask() {
                        @Override
                        public void run() {
                            nubx_message.delete().complete();
                            t.cancel();
                        }
                    }, 80 * 100, 1);
                }
            }

            if (discordUser.message_nubox_count > 100) {
                discordUser.nubox++;
                discordUser.message_nubox_count = 0;
                Message nubx_message = event.getChannel().sendMessage("You have earned 1 Nubox!").complete();
                Timer t = new Timer();
                t.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        nubx_message.delete().complete();
                        t.cancel();
                    }
                }, 80 * 100, 1);
            }
            if (discordUser.message_item_count > 69) {
                discordUser.message_item_count = 0;
                InventoryItem item = References.randomInventoryItem();
                Message nubx_message = event.getChannel().sendMessage("> You earned " + "1" + "x  " + item.getEmoji() + " " + item.getName() + "!").complete();
                References.addInventoryItem(item, 1, discordUser);
                Timer t = new Timer();
                t.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        nubx_message.delete().complete();
                        t.cancel();
                    }
                }, 125 * 100, 1);
            }
            JSON.saveUser(discordUser);
        }
    }
}
