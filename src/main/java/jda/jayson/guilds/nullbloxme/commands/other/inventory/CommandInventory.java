package jda.jayson.guilds.nullbloxme.commands.other.inventory;

import jda.jayson.file.JSON;
import jda.jayson.file.user.DiscordUser;
import jda.jayson.id.References;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class CommandInventory {

    public static void onEvent(MessageReceivedEvent event) {
        Message msg = event.getMessage();
        String content = msg.getContentRaw();
        String argument[] = content.split(" ");
        if(content.contains("!inventory")) {
            DiscordUser discordUser = JSON.loadUser(event.getAuthor().getIdLong());
            EmbedBuilder builder = new EmbedBuilder();
            InventoryPageContainer pageContainer = new InventoryPageContainer();
            Integer p_i = 0;
            Integer page = 0;
            StringBuilder page_content = new StringBuilder();
            Integer current_page = 0;
            Integer page_check = discordUser.inventory.size();
            if(argument.length == 1) {
                current_page = 0;
            } else if (argument.length > 1){
                current_page = Integer.parseInt(argument[1]) - 1;
            }
            if(discordUser.inventory.size() < 1) {
                event.getChannel().sendMessage("> You don't own any items!").complete();
                return;
            }
            //SortedSet<InventoryItem> keys = new TreeSet<>(References.inventory.keySet());
            for (InventoryItem inventoryItem : discordUser.inventory.keySet()) {
                if (!discordUser.inventory.get(inventoryItem).equals(0)){
                    p_i++;
                    if (p_i < 6) {
                        page_content.append("**").append(inventoryItem.getEmoji()).append(" ").append(inventoryItem.getName()).append("**").append("\nAmount: `").append(References.formatInteger(discordUser.inventory.get(inventoryItem))).append("` **--** B/S/T: `").append(inventoryItem.getPrice()).append("`,`").append(inventoryItem.getSellValue()).append("`,`").append(inventoryItem.getTransferValue()).append("` **--** Rarity: `").append(inventoryItem.getRarity()).append("` **--** ID: `").append(inventoryItem.getId()).append("`\n\n");
                    }
                    if (p_i > 4 || p_i.equals(discordUser.inventory.size()) || p_i.equals(page_check)) {
                        page++;
                        page_check -= 5;
                        pageContainer.addPage(new InventoryPage(page_content.toString(), page));
                        page_content = new StringBuilder();
                        p_i = 0;
                    }
                }
            }
            if (current_page > pageContainer.size()) {
                event.getChannel().sendMessage("Page does not exist! [" + pageContainer.size() + "]").complete();
                return;
            }
            builder.appendDescription("B = Buy, S = Sell, T = Transfer");
            builder.addField("Inventory", pageContainer.getPage(current_page).getContent(),true);
            builder.setFooter("Page " + pageContainer.getPage(current_page).getIndex() + " of " + pageContainer.pages.size());
            event.getChannel().sendMessage(builder.build()).complete();
        }
    }
}
