package jda.jayson.guilds.all.commands.minecraft;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class CommandWiki {
    public static void onEvent(MessageReceivedEvent event) {
        if (event.getMessage().getContentRaw().equalsIgnoreCase("!IWKWI")) {
            try {
                String block = "Dirt";

                String description = "Description Not Found";
                String thumbnail_url = "";
                URL url = new URL("https://minecraft.gamepedia.com/" + block);
                HttpURLConnection httpCon = (HttpURLConnection) url.openConnection();
                httpCon.addRequestProperty("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_12_2) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/55.0.2883.95 Safari/537.36");
                Scanner s = new Scanner(httpCon.getInputStream(), "UTF-8");
                while (s.hasNextLine()) {
                    String line = s.nextLine();
                    System.out.println(line);
                    if(line.contains("<p><b>" + block + "</b>")) {
                        description = filter(line);
                    }
                    if(line.contains("<meta property=\"og:image\" content=\"")) {
                        thumbnail_url = filter2(line);
                    }
                }
                EmbedBuilder builder = new EmbedBuilder();
                builder.setDescription(description);
                builder.setThumbnail(thumbnail_url);
                BufferedImage image = null;
                try { image = ImageIO.read(new URL(thumbnail_url));
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
                int clr = image.getRGB(image.getHeight() / 2, image.getWidth() / 2);
                int r = (clr & 0x00ff0000) >> 16;
                int g = (clr & 0x0000ff00) >> 8;
                int b = clr & 0x000000ff;
                builder.setColor(new Color(r,g,b));
                event.getChannel().sendMessage(builder.build()).complete();
            } catch (Exception ex) {

            }
        }
    }
    private static String filter(String line) {
        line = line.replaceAll("<p>","");
        line = line.replaceAll("<b>","");
        line = line.replaceAll("</b>","");
        line = line.replaceAll("</b>","");
        line = line.replaceAll("</b>","");
        line = line.replaceAll("<a","");
        line = line.replaceAll("href=\"","");
        line = line.replaceAll("title=\"","");
        line = line.replaceAll("\"","");
        line = line.replaceAll("Block","");
        line = line.replaceAll("/ >","");
        line = line.replaceAll("</a>","");
        line = line.replaceAll("/Biome","");
        line = line.replaceAll("Biome","biomes");
        line = line.replaceAll("/Biome","");
        line = line.replaceAll(">biomes","");
        line = line.replaceAll("/Overworld ","");
        line = line.replaceAll(">Overworld","");
        line = line.replaceAll("<meta property=og:image content=", "");
        line = line.replaceAll("   ", " ");
        line = line.replaceAll("  ", " ");
        return line;
    }
    private static String filter2(String line) {
        line = line.replaceAll("\"/>", "");
        line = line.replaceAll("<meta property=\"og:image\" content=\"", "");
        line = line.replaceAll("   ", " ");
        line = line.replaceAll("  ", " ");
        return line;
    }
}
