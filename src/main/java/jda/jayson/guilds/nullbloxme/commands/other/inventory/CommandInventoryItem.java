package jda.jayson.guilds.nullbloxme.commands.other.inventory;

import jda.jayson.file.JSON;
import jda.jayson.id.ID;
import jda.jayson.id.References;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class CommandInventoryItem {
    public static void onEvent(MessageReceivedEvent event) {
        Message msg = event.getMessage();
        String content = msg.getContentRaw();
        String[] argument = content.split(" ");
        if(content.equalsIgnoreCase("!items")) {
            StringBuilder items = new StringBuilder();
            int i = 0;
            for (InventoryItem value : InventoryItem.values()) {
                i++;
                if(items.length() < 1500) {
                    if (i != InventoryItem.values().length) {
                        items.append(value.getEmoji()).append(" **").append(value.getName()).append("** -- ID: `").append(value.getId()).append("` -- Price: `").append(value.getPrice()).append("`,\n");
                    } else {
                        items.append(value.getEmoji()).append(" **").append(value.getName()).append("** -- ID: `").append(value.getId()).append("` -- Price: `").append(value.getPrice()).append("`");
                    }
                }
                if (items.length() > 1499) {
                    event.getChannel().sendMessage(items.toString()).complete();
                    items = new StringBuilder();
                }
            }
            if(!items.toString().equalsIgnoreCase("")) {
                event.getChannel().sendMessage(items.toString()).complete();
            }
        }

        if (content.contains("!item")) {
            if(content.contains("buy") || content.contains("sell")) {
                JSON.load(String.valueOf(event.getAuthor().getIdLong()));
                    Integer amount = 1;
                    InventoryItem item;
                    if(argument.length > 3) {
                        amount = Integer.valueOf(argument[3]);
                    }
                    try {
                        item = InventoryItem.valueOf(argument[2].toUpperCase());
                        if(content.contains("buy")) {
                            if (References.currency > item.getPrice() || References.currency == item.getPrice()) {
                                References.currency -= item.getPrice() * amount;
                                References.addInventoryItem(item, amount);
                                event.getChannel().sendMessage("> Bought " + amount + "x " + item.getEmoji() + " " + item.getName() + " for " + item.getPrice() * amount + "!").complete();
                            } else {
                                event.getChannel().sendMessage("> You don't have enough " + ID.currency + " to buy " + item.getEmoji() + " " + item.getName() + "!").complete();
                            }
                        }
                        if(content.contains("sell")) {
                            if(References.InventoryItemAmount(item) > amount || References.InventoryItemAmount(item) == amount) {
                                References.currency += item.getSellValue() * amount;
                                References.removeInventoryItem(item,amount);
                                event.getChannel().sendMessage("> Sold " + amount + "x " + item.getEmoji() + " " + item.getName() + " for " + item.getSellValue() * amount + "!").complete();
                            }
                        }
                    } catch (Exception exc) {
                        event.getChannel().sendMessage("> Item `" + argument[2].toUpperCase() + "` not found!").complete();
                    }
                }
                JSON.save(String.valueOf(event.getAuthor().getIdLong()));
        }
    }
}
