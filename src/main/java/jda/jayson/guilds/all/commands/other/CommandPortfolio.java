package jda.jayson.guilds.all.commands.other;

import com.jayson.nubx.NubxURL;
import com.moandjiezana.toml.Toml;
import jda.jayson.Nubx;

import jda.jayson.file.JSON;
import jda.jayson.id.References;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.apache.commons.io.FileUtils;
import org.json.JSONObject;

import javax.print.attribute.HashAttributeSet;
import javax.xml.ws.Response;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;


public class CommandPortfolio {
    private static Message message;
    private static Integer progress = 1;
    public static void onEvent(MessageReceivedEvent event) {
        Message msg = event.getMessage();
        String content = msg.getContentRaw();
        String[] argument = content.split(" ");
        progress = 1;
        if(content.equalsIgnoreCase("!portfolio create")) {
            if(msg.getAttachments().size() > 0) {
                if(Objects.requireNonNull(msg.getAttachments().get(0).getFileExtension()).equalsIgnoreCase("toml")) {
                    new File("bot/portfolios").mkdirs();
                    NubxURL.create(msg.getAttachments().get(0).getUrl()).downloadToFile("bot/portfolios/" + event.getAuthor().getIdLong() + ".toml");
                    message = event.getChannel().sendMessage(getProgress(1, "Creating")).complete();
                    event.getChannel().sendMessage(portfolio(event.getAuthor().getId(),event).build()).complete();
                    editProgress("Done");
                } else {
                    event.getChannel().sendMessage("> Please provide a TOML file!").complete();
                }
            } else {
                event.getChannel().sendMessage("> Please provide a TOML file!").complete();
            }
        }
        if(content.contains("!portfolio") && !content.contains("create") && !content.contains("list")) {
            Member user;
            if(msg.getMentionedMembers().size() > 0) {
                user = msg.getMentionedMembers().get(0);
            } else {
               try { user = msg.getGuild().getMembersByName(argument[1],true).get(0); } catch (Exception e) { user = event.getGuild().getMemberById(argument[1]); }
            }
            if(user == null) {
                event.getChannel().sendMessage("> User not found!").complete();
                return;
            }
            event.getChannel().sendMessage(portfolio(user.getId(),event).build()).complete();
        }
        if(content.equalsIgnoreCase("!portfolio list")) {
            StringBuilder builder = new StringBuilder();
            List<File> files = (List<File>) FileUtils.listFiles(new File("bot/portfolios"), new String[]{"toml"}, true);
            int i = 0;
            for (File file : files) {
                String id = file.getName().replaceAll(".toml","");
                i++;
                if(builder.length() < 1500) {
                    try {
                        builder.append("`").append(event.getGuild().getMemberById(id).getUser().getName()).append("` `[").append(id).append("]`");
                    } catch (Exception exc) {
                        JSON.load(id);
                        builder.append("`").append(References.discord_name).append("` [*").append(id).append("*]");
                    }
                }
                if(builder.length() > 1499) {
                    event.getChannel().sendMessage(builder).complete();
                    builder = new StringBuilder();
                }
                if(i != files.size()) {
                    builder.append(",");
                }
            }
            event.getChannel().sendMessage(builder).complete();
        }
    }

    private static EmbedBuilder portfolio(String id, MessageReceivedEvent event) {
        EmbedBuilder builder = new EmbedBuilder();
        load(id,builder);
        builder.setTitle(event.getGuild().getMemberById(id).getUser().getName() + "'s Portfolio");
        File file = new File("bot/portfolios/" + id + ".toml");
        if (file.exists()) {
            Toml toml = new Toml().read(file);
            editProgress("Creating Builder");
            if(toml.contains("thumbnail")) {
                String thumbnail = toml.getString("thumbnail");
                thumbnail = thumbnail.replaceAll("%AVATAR%", Objects.requireNonNull(Objects.requireNonNull(event.getGuild().getMemberById(id)).getUser().getAvatarUrl()));
                builder.setThumbnail(thumbnail);
            }
            if(toml.contains("color")) {
                String[] color = toml.getString("color").split(" ");
                builder.setColor(new Color(Integer.valueOf(color[0]), Integer.valueOf(color[1]), Integer.valueOf(color[2])));
            }
            if(toml.contains("footer")) {
                builder.setFooter(toml.getString("footer"));
                if(toml.contains("footer-icon")) {
                    String icon = toml.getString("footer-icon");
                    icon = icon.replaceAll("%AVATAR%", Objects.requireNonNull(Objects.requireNonNull(event.getGuild().getMemberById(id)).getUser().getAvatarUrl()));
                    builder.setFooter(toml.getString("footer"),icon);
                }
            }
            if(toml.contains("description")) {
                builder.setDescription(toml.getString("description"));
            }
            if(toml.contains("author")) {
                builder.setAuthor(toml.getString("author"));
            }
            if(toml.contains("author-url") && toml.contains("author")) {
                builder.setAuthor(toml.getString("author"), toml.getString("author-url"));
            }
            if(toml.contains("author") && toml.contains("author-icon")) {
                String icon = toml.getString("author-icon");
                icon = icon.replaceAll("%AVATAR%", Objects.requireNonNull(Objects.requireNonNull(event.getGuild().getMemberById(id)).getUser().getAvatarUrl()));
                if(toml.contains("author-url")) {
                    builder.setAuthor(toml.getString("author"), toml.getString("author-url"), icon);
                } else {
                    builder.setAuthor(toml.getString("author"), "http://www.example.com", icon);
                }
            }
            if(toml.contains("image")) {
                String image = toml.getString("image");
                image = image.replaceAll("%AVATAR%", Objects.requireNonNull(Objects.requireNonNull(event.getGuild().getMemberById(id)).getUser().getAvatarUrl()));
                builder.setImage(image);
            }
        }
        editProgress("Completing Builder");
        editProgress("Cleaning");
        return builder;
    }
    private static void load(String id, EmbedBuilder builder) {
        File file = new File("bot/portfolios/" + id + ".toml");
        editProgress("Loading");
        if (file.exists()) {
            Toml toml = new Toml().read(file);
           try {
               editProgress("Adding Unordered Fields");
               for (Map.Entry<String, Object> s : toml.getTable("fields").entrySet()) {
                   if (!s.getKey().equalsIgnoreCase("")) {
                       if (!s.getKey().equalsIgnoreCase("blank")) {
                           builder.addField(s.getKey().replaceAll("\"", ""), s.getValue() + "", true);
                       } else {
                           builder.addBlankField(true);
                       }
                   }
               }
           } catch (Exception exc) {
               editProgress("Adding Ordered Fields");
               for (Toml fields : toml.getTables("fields")) {
                   for (Map.Entry<String, Object> s : fields.entrySet()) {
                       if (s.getKey().equalsIgnoreCase("blank")) {
                           builder.addBlankField(true);
                       } else {
                           builder.addField(s.getKey().replaceAll("\"", ""), s.getValue() + "", true);
                       }
                   }
               }
           }
        }
    }
    private static String getProgress(Integer integer, String objective) {
        StringBuilder str = new StringBuilder("[");
        Integer remaining = 0;
        for (int i = 0; i < integer; i++) {
            remaining++;
            if(i == (8 / 2)) {
                str.append(objective);
            }
            str.append("=");
        }
        for (Integer i = remaining; i < 8; i++) {
            if(i == (8 / 2)) {
                str.append(objective);
            }
            str.append("-");
        }
        str.append("]");
        return str.toString();
    }
    private static void editProgress(String string) {
        progress++;
        if(message != null) { message.editMessage(getProgress(progress,string)).complete(); }
    }
}
