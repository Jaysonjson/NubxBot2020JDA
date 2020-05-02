package jda.jayson.guilds.all.commands.utility;

import jda.jayson.file.JSON;
import jda.jayson.file.user.DiscordUser;
import jda.jayson.id.ID;
import jda.jayson.id.References;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.temporal.TemporalAccessor;
import java.time.temporal.TemporalField;
import java.util.Random;

public class CommandMe {
    public static void onEvent(MessageReceivedEvent event) {
        Message msg = event.getMessage();
        String content = msg.getContentRaw();
        String argument[] = content.split(" ");
        if(content.contains("!me")) {
            User user = event.getAuthor();
            if(msg.getMentionedMembers().size() > 0 && msg.getMentionedMembers().size() != 0) {
                user = msg.getMentionedMembers().get(0).getUser();
            } else if(argument.length > 1) {
                try {
                    user = event.getGuild().getMembersByName(argument[1],true).get(0).getUser();
                } catch (Exception exc) {
                    event.getChannel().sendMessage("> User not found!").complete();
                    return;
                }
            }
            DiscordUser discordUser = JSON.loadUser(user.getIdLong());
            EmbedBuilder builder = new EmbedBuilder();
            builder.setTitle(user.getName() + "'s Informations");
            builder.addField("\uD83C\uDFF7️ Name",user.getAsMention(),true);
            builder.addField("<:nubx:363393495186145280> " + ID.currency, References.formatInteger(discordUser.currency),true);
            builder.addField("\uD83D\uDCE6 Nuboxes", References.formatInteger(discordUser.nubox),true);
            builder.addField("\uD83D\uDD8A ️Message Count", References.formatInteger(discordUser.message_count),true);
            builder.addField("<:cubik_element:362665520685907978> Model Count", String.valueOf(discordUser.model_count),true);
            builder.addField("\uD83D\uDED2 Shop Items Sold", References.formatInteger(discordUser.shop_items_sold),true);
            builder.addField("\uD83E\uDD47 Contests Won", String.valueOf(discordUser.contests_won),true);
            builder.addField("\uD83C\uDF7E Points", References.formatInteger(discordUser.points),true);
            builder.addField("\uD83D\uDCC7 ID",  user.getId(),true);
            builder.setTimestamp(Instant.now());
            //builder.addBlankField(true);
            if(!discordUser.minecraft.uuid.equalsIgnoreCase("")) {
                builder.addField("<:minecraft:643808914575458376> Minecraft UUID", discordUser.minecraft.uuid, true);
            }
            if(!References.skin_url_sized.equalsIgnoreCase("")) {
                builder.setImage(References.skin_url_sized);
            }
            BufferedImage image = null;

            try {
                URL avatar_url = new URL(user.getAvatarUrl());
                URLConnection openConnection = avatar_url.openConnection();
                openConnection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95 Safari/537.11");
                openConnection.connect();
                image = ImageIO.read(openConnection.getInputStream());
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            int clr = image.getRGB(image.getHeight() / 2, image.getWidth() / 2);
            int r = (clr & 0x00ff0000) >> 16;
            int g = (clr & 0x0000ff00) >> 8;
            int b = clr & 0x000000ff;
            builder.setColor(new Color(r,g,b));
            builder.setThumbnail(user.getAvatarUrl());
            event.getChannel().sendMessage(builder.build()).complete();
        }
    }
}
