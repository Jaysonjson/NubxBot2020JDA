package jda.jayson.guilds.nullbloxme.events.message;

import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.io.*;
import java.nio.LongBuffer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Random;

public class MessageRandom {

    public static void onEvent(MessageReceivedEvent event) {
        Message msg = event.getMessage();
        String content = msg.getContentRaw();
        String[] argument = content.split(" ");
        if (event.getChannel().getIdLong() == 582281584020029472L && !event.getAuthor().isBot()) {
            try {
                List<String> hello = Files.readAllLines(Paths.get("bot/randommessage/hello.txt"));
                List<String> goodbye = Files.readAllLines(Paths.get("bot/randommessage/goodbye.txt"));
                Random r = new Random();
                if (hello.contains(event.getMessage().getContentRaw()) || hello.contains(event.getMessage().getContentRaw().toUpperCase()) || hello.contains(event.getMessage().getContentRaw().toLowerCase())) {
                    event.getChannel().sendMessage(hello.get(r.nextInt(hello.size()))).complete();
                } else if (goodbye.contains(event.getMessage().getContentRaw()) || goodbye.contains(event.getMessage().getContentRaw().toUpperCase()) || goodbye.contains(event.getMessage().getContentRaw().toLowerCase())) {
                    event.getChannel().sendMessage(goodbye.get(r.nextInt(goodbye.size()))).complete();
                } else {
                    List<String> lines = Files.readAllLines(Paths.get("bot/randommessage/randommessage.txt"));
                    String out = lines.get(r.nextInt(lines.size()));
                    out = out.replaceAll("%AUTHOR%", event.getAuthor().getName());
                    out = out.replaceAll("%PLAYER%", event.getAuthor().getName());
                    out = out.replaceAll("%AUTHOR_MENTION%", event.getAuthor().getAsMention());
                    out = out.replaceAll("%PLAYER_MENTION%", event.getAuthor().getAsMention());
                    out = out.replaceAll("%AUTHOR_ID%", event.getAuthor().getId());
                    out = out.replaceAll("%PLAYER_ID%", event.getAuthor().getId());
                    out = out.replaceAll("%AUTHOR_AVATAR_URL%", Objects.requireNonNull(event.getAuthor().getAvatarUrl()));
                    out = out.replaceAll("%PLAYER_AVATAR_URL%", event.getAuthor().getAvatarUrl());
                    out = out.replaceAll("%LINE%", "\n");
                    out = out.replaceAll("%IS_BOT%", String.valueOf(event.getAuthor().isBot()));
                    out = out.replaceAll("%MESSAGE_CONTENT%",event.getMessage().getContentRaw());
                    out = out.replaceAll("\\n", "\n");
                    out = out.replaceAll("%AUTHOR_CREATIONDATE%", String.valueOf(event.getAuthor().getTimeCreated().getDayOfMonth() + event.getAuthor().getTimeCreated().getMonthValue() + event.getAuthor().getTimeCreated().getYear()));
                    out = out.replaceAll("%PLAYER_CREATIONDATE%", String.valueOf(event.getAuthor().getTimeCreated().getDayOfMonth() + event.getAuthor().getTimeCreated().getMonthValue() + event.getAuthor().getTimeCreated().getYear()));
                    DateFormat dateFormat = new SimpleDateFormat("dd.MM.YY HH:mm:ss");
                    Date date = new Date();
                    if(out.contains("%TIME:")) {
                        dateFormat = new SimpleDateFormat(out.substring(out.indexOf("%TIME:"), out.indexOf("_TIME%")));
                        date = new Date();
                    }
                    out = out.replaceAll("%CURRENT_TIME%", dateFormat.format(date));
                    event.getChannel().sendMessage(out).complete();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        if(event.getMessage().getContentRaw().contains("!rndmsg add")) {
            argument[0] = "";
            argument[1] = "";
            String joined = String.join(" ", argument);
            try {
                joined = joined.substring(2,joined.length());
                Writer output;
                output = new BufferedWriter(new FileWriter("bot/randommessage/randommessage.txt", true));
                output.append("\n" + joined);
                output.close();
                List<String> lines = Files.readAllLines(Paths.get("bot/randommessage/randommessage.txt"));
                event.getChannel().sendMessage("> Added line \"" + joined + "\" in Line "+ lines.size() + "!").complete();
        } catch (IOException e) {
                e.printStackTrace();
            }
    }
        }
    }
