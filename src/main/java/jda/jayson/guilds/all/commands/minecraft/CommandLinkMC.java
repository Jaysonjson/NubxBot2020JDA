package jda.jayson.guilds.all.commands.minecraft;

import jda.jayson.file.JSON;
import jda.jayson.id.References;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.plugins.jpeg.JPEGImageWriteParam;
import javax.imageio.stream.FileImageOutputStream;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

public class CommandLinkMC {
    public static void onEvent(MessageReceivedEvent e) {
        String[] argument = e.getMessage().getContentRaw().split(" ");
        Message msg = e.getMessage();
        Timer t = new Timer();
        if(msg.getContentRaw().contains("!minecraft link")) {
                try {
                    URL url = new URL("https://namemc.com/profile/" + argument[2]);
                    HttpURLConnection httpCon = (HttpURLConnection) url.openConnection();
                    t.schedule(new TimerTask() {
                        @Override
                        public void run() {
                            httpCon.disconnect();
                            e.getChannel().sendMessage("> Timed out!").complete();
                        }
                    },20000);
                    e.getChannel().sendMessage("Linking...").complete();
                    httpCon.addRequestProperty("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_12_2) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/55.0.2883.95 Safari/537.36");
                    Scanner s = new Scanner(httpCon.getInputStream(), "UTF-8");
                    t.cancel();
                    while (s.hasNextLine()) {
                        String line = s.nextLine();
                        String skin;
                        String skin_url;
                        JSON.load(String.valueOf(e.getAuthor().getIdLong()));
                        if (line.contains("name=\"profile\" value=\"")) {
                            References.minecraft_uuid = line.substring(line.indexOf("value=") + 7, line.indexOf(">") - 1);
                            e.getChannel().sendMessage("> " + argument[2] + "\n > " + References.minecraft_uuid).complete();
                            break;
                        }
                        if (line.contains("shortcut icon")) {
                            skin = line;
                            String output = skin.substring(skin.indexOf("href=") + 6, skin.indexOf(">") - 1);
                            skin_url = output;
                            References.skin_url_sized = skin_url.substring(0, skin_url.lastIndexOf("&amp"));
                            References.skin_url_sized += "&scale=8";
                        }
                    }
                    s.close();
                    JSON.save(String.valueOf(e.getAuthor().getIdLong()));
                    t.cancel();
                } catch (Exception exc) {
                    exc.printStackTrace();
                    e.getChannel().sendMessage("Something happened!").complete();}
            }
    }
}
