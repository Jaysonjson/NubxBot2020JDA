package jda.jayson.guilds.all.commands.nubox;

import jda.jayson.file.JSON;
import jda.jayson.file.user.DiscordUser;
import jda.jayson.guilds.nullbloxme.commands.other.inventory.InventoryItem;
import jda.jayson.id.ID;
import jda.jayson.id.References;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.util.Random;

public class CommandOpenNubox {
    private static void PresentContent(Integer n, Integer nm, MessageReceivedEvent e, Integer p, String c, DiscordUser discordUser) {
        if (n.equals(nm)) {
            switch (c) {
                case "price":
                    Random r2 = new Random();
                    int price = r2.nextInt(p);
                    discordUser.currency += price;
                    if (price > 0) {
                        e.getChannel().sendMessage("> In there was " + References.formatInteger(price) + " " + ID.currency + "!").complete();
                    } else {
                        e.getChannel().sendMessage("> Nothing was in there!").complete();
                    }
                    break;
                case "nothing":
                e.getChannel().sendMessage("> Nothing was in there!").complete();
                break;
                case "nubox":
                    Random r3 = new Random();
                    int nubox = r3.nextInt(p);
                    discordUser.nubox += nubox;
                    e.getChannel().sendMessage("> You earned " + nubox + " new Nuboxes").complete();
                    break;
                case "inventory_item":
                    Random r4 = new Random();
                    int ii_amount = r4.nextInt(p);
                    if(ii_amount == 0) {
                        ii_amount = 1;
                    }
                    InventoryItem ii_win = References.randomInventoryItem();
                    e.getChannel().sendMessage("> You earned " + ii_amount + "x  " + ii_win.getEmoji() + " " + ii_win.getName() + "!").complete();
                    References.addInventoryItem(ii_win,ii_amount, discordUser);
                    break;
            }
        }
    }
    public static void onEvent(MessageReceivedEvent event) {
        Message msg = event.getMessage();
        String content = msg.getContentRaw();
        if (content.equalsIgnoreCase("!nubox open")) {
            DiscordUser discordUser = JSON.loadUser(event.getAuthor().getIdLong());
            if (discordUser.nubox > 0) {
                discordUser.nubox--;
                Random r = new Random();
                int rn = r.nextInt(6);
                PresentContent(rn,0,event,2000,"price", discordUser);
                PresentContent(rn,1,event,5,"inventory_item", discordUser);
                PresentContent(rn,2,event,2,"inventory_item", discordUser);
                PresentContent(rn,3,event,1,"inventory_item", discordUser);
                PresentContent(rn,4,event,0,"nothing", discordUser);
                PresentContent(rn,5,event,3,"nubox", discordUser);
                PresentContent(rn,6, event, 3, "inventory_item", discordUser);
                PresentContent(rn,7, event, 3, "inventory_item", discordUser);
                PresentContent(rn,8, event, 3, "inventory_item", discordUser);
                PresentContent(rn,9,event,0,"nothing", discordUser);
                PresentContent(rn,10,event,0,"nothing", discordUser);
                PresentContent(rn,11,event,0,"nothing", discordUser);
                event.getChannel().sendMessage("> You have " + discordUser.nubox + " Nuboxes left!").complete();
            } else {
                event.getChannel().sendMessage("> You don't have enough Nuboxes!").complete();
            }
            JSON.saveUser(discordUser);
        }
    }
}
