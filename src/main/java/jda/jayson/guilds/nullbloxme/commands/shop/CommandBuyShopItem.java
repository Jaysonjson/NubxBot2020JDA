package jda.jayson.guilds.nullbloxme.commands.shop;

import jda.jayson.file.JSON;
import jda.jayson.file.ShopJSON;
import jda.jayson.file.user.DiscordUser;
import jda.jayson.id.Channels;
import jda.jayson.id.Guilds;
import jda.jayson.id.ID;
import jda.jayson.id.References;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.io.File;

public class CommandBuyShopItem {
    public static void onEvent(MessageReceivedEvent event) {
        Guild guild = event.getJDA().getGuildById(Guilds.Nullbloxme);
        TextChannel shop = guild.getTextChannelById(Channels.Nubx_Shop_Bot);
        Message msg = event.getMessage();
        User user = event.getAuthor();
        String content = msg.getContentRaw();
        String argument[] = content.split(" ");
        if(content.contains("!shop buy") && event.getChannel().getIdLong() == shop.getIdLong()) {
            String shop_item = argument[2];
            File item = new File("bot/shop/models/" + shop_item + ".zip");
            if (item.exists()) {
                DiscordUser discordUser = JSON.loadUser(user.getIdLong());
                ShopJSON.load(shop_item);
                if(discordUser.currency > References.shop_price || discordUser.currency == References.shop_price) {
                    discordUser.currency -= References.shop_price;
                    Message msg_f = user.openPrivateChannel().complete().sendFile(item).complete();
                    event.getChannel().sendMessage("> Successfully bought " + shop_item + " for " + References.shop_price + " " + ID.currency + "! Check your PMs for the Files! Or click on the Jump URL! [" + msg_f.getJumpUrl() + "]").complete();
                    JSON.saveUser(discordUser);
                    Member item_owner = event.getGuild().getMemberById(References.shop_owner);
                    DiscordUser itemOwnerDiscordUser = JSON.loadUser(item_owner.getIdLong());
                    itemOwnerDiscordUser.currency += References.shop_price;
                    References.shop_download_amount++;
                    References.shop_total_nubx_earned += References.shop_price;
                    item_owner.getUser().openPrivateChannel().complete().sendMessage("> " + user.getAsMention() + " has bought " + shop_item + "! \nTotal Download Amount: " + References.shop_download_amount + "\nTotal " + ID.currency +" Earned: " + References.shop_total_nubx_earned).complete();
                    JSON.saveUser(itemOwnerDiscordUser);
                    ShopJSON.save(shop_item);
                } else {
                    event.getChannel().sendMessage("> You don't have enough " + ID.currency + " to buy that!").complete();
                }
            } else {
                event.getChannel().sendMessage("> Shop Item not found!").complete();
            }
        }
    }
}
