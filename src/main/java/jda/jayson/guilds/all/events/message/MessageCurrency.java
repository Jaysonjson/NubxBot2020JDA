package jda.jayson.guilds.all.events.message;

import jda.jayson.file.JSON;
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
        JSON.load(String.valueOf(event.getAuthor().getIdLong()));
        References.points = (References.model_count * 3) + (References.message_count / 2) + (References.currency / 3) + (References.shop_items_sold * 2) + (References.contests_won * 8);
        if (event.getChannel().getIdLong() != 582281584020029472L) {
            References.message_currency++;
            References.message_nubox++;
            References.message_count++;
            References.message_item++;
            References.discord_name = event.getAuthor().getName();
            if (References.message_currency > 45) {
                boolean banned_nubx = false;
                References.currency += 25;
                References.message_currency = 0;
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

            if (References.message_nubox > 100) {
                References.nubox++;
                References.message_nubox = 0;
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
            if (References.message_item > 69) {
                References.message_item = 0;
                InventoryItem item = References.randomInventoryItem();
                Message nubx_message = event.getChannel().sendMessage("> You earned " + "1" + "x  " + item.getEmoji() + " " + item.getName() + "!").complete();
                References.addInventoryItem(item, 1);
                Timer t = new Timer();
                t.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        nubx_message.delete().complete();
                        t.cancel();
                    }
                }, 125 * 100, 1);
            }
            JSON.save(String.valueOf(event.getAuthor().getIdLong()));
        }
    }
}
