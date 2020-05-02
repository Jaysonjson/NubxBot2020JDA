package jda.jayson.guilds.nullbloxme.commands.shop;


import com.jayson.nubx.NubxURL;
import jda.jayson.file.ShopJSON;
import jda.jayson.id.Channels;
import jda.jayson.id.Guilds;
import jda.jayson.id.ID;
import jda.jayson.id.References;
import net.dv8tion.Uploader;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.message.priv.PrivateMessageReceivedEvent;
import net.lingala.zip4j.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import org.apache.commons.io.FileUtils;
import org.json.JSONObject;

import java.awt.*;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;


public class CommandAddShopItem {
    public static void onEvent(PrivateMessageReceivedEvent event) {
        Guild guild = event.getJDA().getGuildById(Guilds.Nullbloxme);
        TextChannel shop = guild.getTextChannelById(Channels.Nubx_Shop);
        Message msg = event.getMessage();
        User user = event.getAuthor();
        String content = msg.getContentRaw();
        String argument[] = content.split(" ");

        if (content.contains("!shop add")) {
            if (argument[4].toLowerCase().contains("sketchfab") || argument[4].toLowerCase().contains("skfb.ly")) {
                if (msg.getAttachments().size() > 0) {
                    if (msg.getAttachments().get(0).getFileExtension().equalsIgnoreCase("zip")) {
                        new File("bot/shop/models").mkdirs();
                        if (argument.length > 2) {
                            File file = new File("bot/shop/models/" + argument[2] + ".zip");
                            if (!file.exists()) {
                                NubxURL.create(msg.getAttachments().get(0).getUrl()).downloadToFile("bot/shop/models/" + argument[2] + ".zip");
                                event.getChannel().sendMessage("> Downloaded!").complete();
                                ZipFile zipFile = new ZipFile(new File("bot/shop/models/" + argument[2] + ".zip"));
                                try {
                                    new File("bot/shop/models/" + argument[2] + "/extracted").mkdirs();
                                    zipFile.extractAll("bot/shop/models/" + argument[2] + "/extracted");
                                    event.getChannel().sendMessage("> Extracted content!").complete();
                                    File preview = new File("bot/shop/models/" + argument[2] + "/extracted/preview.png");
                                    if (preview.exists()) {
                                        event.getChannel().sendMessage("> Removed files!").complete();
                                        References.shop_price = Integer.valueOf(argument[3]);
                                        References.shop_owner = user.getIdLong();
                                        EmbedBuilder builder = new EmbedBuilder();
                                        builder.addField("Author", user.getAsMention(), true);
                                        builder.addField("Price", String.valueOf(References.shop_price) + " " + ID.currency, true);
                                        builder.addField("Sketchfab", argument[4], true);
                                        builder.addField("ID", argument[2], true);
                                        Long size = zipFile.getFile().length() / (1024 * 1024);
                                        if (size > 0.9) {
                                            builder.addField("Size", size + " MB", true);
                                        } else {
                                            builder.addField("Size", zipFile.getFile().length() / 1024 + " KB", true);
                                        }
                                        ShopJSON.load_add(argument[2], builder);
                                        JSONObject json = new JSONObject(Uploader.upload(new File("bot/shop/models/" + argument[2] + "/extracted/preview.png")));
                                        JSONObject data = json.getJSONObject("data");
                                        builder.setThumbnail(data.getString("link"));
                                        builder.setColor(new Color(62, 240, 252));
                                        assert shop != null;
                                        shop.sendMessage(builder.build()).complete();
                                        ShopJSON.save(argument[2]);
                                        FileUtils.deleteDirectory(new File("bot/shop/models/" + argument[2] + "/"));
                                    } else {
                                        event.getChannel().sendMessage("> Missing \"preview.png\" file inside .zip!").complete();
                                        FileUtils.deleteDirectory(new File("bot/shop/models/" + argument[2] + "/"));
                                        new File("bot/shop/models/" + argument[2] + ".zip").delete();
                                    }
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            } else {
                                event.getChannel().sendMessage("> A Model with that name already exists!").complete();
                            }
                        } else {
                            event.getChannel().sendMessage("> Not enough Arguments! !shop add model_id price sketchfab_link_to_model").complete();
                        }
                    } else {
                        event.getChannel().sendMessage("> Please attach a .zip file!").complete();
                    }
                } else {
                    event.getChannel().sendMessage("> Please attach a .zip file!").complete();
                }
            } else {
                event.getChannel().sendMessage("> Not a valid Sketchfab link!").complete();
            }
        }
    }
}


