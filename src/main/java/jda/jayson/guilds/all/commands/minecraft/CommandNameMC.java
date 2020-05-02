package jda.jayson.guilds.all.commands.minecraft;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.plugins.jpeg.JPEGImageWriteParam;
import javax.imageio.stream.FileImageOutputStream;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

public class CommandNameMC {
    public static void onEvent(MessageReceivedEvent e) {
        String[] argument = e.getMessage().getContentRaw().split(" ");
        Message msg = e.getMessage();
        String name = null;
        String skin;
        String skin_url = null;
        String UUID = null;
        ArrayList name_history = new ArrayList();
        String name_histroy_s;
        Timer t3 = new Timer();
        if (msg.getContentRaw().contains("!mc")) {
            Boolean done = false;
            Message inf = e.getChannel().sendMessage("Trying to get Informations...").complete();
            Timer t2 = new Timer();
            t2.schedule(new TimerTask() {
                @Override
                public void run() {
                    inf.delete();
                    t2.cancel();
                }
            }, 30 * 100, 1);

            if (argument.length < 1) {
                msg.getChannel().sendMessage("Usage !mc (playername)").complete();
            }
            try {
                URL url = new URL("https://namemc.com/profile/" + argument[1]);
                HttpURLConnection httpCon = (HttpURLConnection) url.openConnection();
                t3.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        httpCon.disconnect();
                        e.getChannel().sendMessage("> Timed out!").complete();
                    }
                },10000);
                httpCon.addRequestProperty("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_12_2) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/55.0.2883.95 Safari/537.36");
                Scanner s = new Scanner(httpCon.getInputStream(), "UTF-8");
                t3.cancel();
                while (s.hasNextLine()) {
                    String line = s.nextLine();
                    //System.out.println(s.nextLine());
                    if (line.contains("profile:username")) {
                        name = line;
                        String output = name.substring(name.indexOf("content=") + 9, name.indexOf(">") - 1);
                        // System.out.println(output);
                        name = output;
                    }
                    if (line.contains("/name/")) {
                        name_histroy_s = line;
                        // System.out.println(name_histroy_s);
                        String output = name_histroy_s.substring(name_histroy_s.indexOf("href=") + 12, name_histroy_s.lastIndexOf("\">"));
                        // System.out.println(output);
                        if (!name_history.contains(name_histroy_s)) {
                            name_history.add(output);
                        }
                    }
                    if (line.contains("name=\"profile\" value=\"")) {
                        UUID = line;
                        String output = UUID.substring(UUID.indexOf("value=") + 7, UUID.indexOf(">") - 1);
                        //  System.out.println(output);
                        UUID = output;
                    }


                    if (line.contains("shortcut icon")) {
                        skin = line;
                        String output = skin.substring(skin.indexOf("href=") + 6, skin.indexOf(">") - 1);
                        // System.out.println(output);
                        skin_url = output;
                        URL url2 = new URL(output);
                        URLConnection openConnection = url2.openConnection();
                        boolean check = true;
                        try {

                            openConnection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95 Safari/537.11");
                            openConnection.connect();

                            if (openConnection.getContentLength() > 8000000) {
                                //System.out.println(" file size is too big.");
                                check = false;
                            }
                        } catch (Exception ey) {
                            System.out.println("Couldn't create a connection to the link, please recheck the link.");
                            check = false;
                            ey.printStackTrace();
                        }
                        if (check) {
                            BufferedImage img = null;
                            try {
                                InputStream in = new BufferedInputStream(openConnection.getInputStream());
                                ByteArrayOutputStream out = new ByteArrayOutputStream();
                                byte[] buf = new byte[1024];
                                int n = 0;
                                while (-1 != (n = in.read(buf))) {
                                    out.write(buf, 0, n);
                                }
                                out.close();
                                in.close();
                                byte[] response = out.toByteArray();
                                img = ImageIO.read(new ByteArrayInputStream(response));
                            } catch (Exception ec) {
                                System.out.println(" couldn't read an image from this link.");
                                ec.printStackTrace();
                            }
                            JPEGImageWriteParam jpegParams = new JPEGImageWriteParam(null);
                            jpegParams.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
                            jpegParams.setCompressionQuality(0.5f);
                            new File("bot/files/skins/" + e.getAuthor().getIdLong()).mkdirs();
                            File file_img = new File("bot/files/skins/" + e.getAuthor().getIdLong() + "/skin_head.png");
                            try {
                                final ImageWriter writer = ImageIO.getImageWritersByFormatName("png").next();
                                writer.setOutput(new FileImageOutputStream(file_img));
                                writer.write(null, new IIOImage(img, null, null), jpegParams);
                            } catch (IOException exc) {
                                exc.printStackTrace();
                            }

                        }

                    }
                }
                s.close();
                done = true;
                t3.cancel();
            } catch (MalformedURLException xe) {
                xe.printStackTrace();
            } catch (IOException ce) {
                ce.printStackTrace();
            }
            if (done) {
                File skin_head = new File("bot/files/skins/" + e.getAuthor().getIdLong() + "/skin_head.png");
                BufferedImage image = null;
                try {
                    image = ImageIO.read(skin_head);
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
                int clr = image.getRGB(1, 1);
                int r = (clr & 0x00ff0000) >> 16;
                int g = (clr & 0x0000ff00) >> 8;
                int b = clr & 0x000000ff;
                Integer length_nh = String.valueOf(name_history).length();
                length_nh -= 1;
                String name_histroy_t = String.valueOf(name_history).substring(1, length_nh);
                String skin_url_sized = skin_url.substring(0, skin_url.lastIndexOf("&amp"));
                skin_url_sized += "&scale=64";
                EmbedBuilder builder = new EmbedBuilder();
                builder.addField("Name", name, true);
                builder.addField("UUID", UUID, true);
                String final_name_history = name_histroy_t.replaceAll("_", "＿");
                builder.addField("Name History", final_name_history, true);
                builder.setColor(new Color(r, g, b));
                builder.setFooter("Taken from NameMC","https://pbs.twimg.com/profile_images/916322265762955264/exVHzb6M_400x400.jpg");
                builder.setThumbnail(skin_url_sized);
                builder.setAuthor("NameMC","https://namemc.com/profile/" + argument[1]);
                msg.getChannel().sendMessage(builder.build()).complete();
                inf.delete().complete();
                t3.cancel();
            } else {
                e.getChannel().sendMessage("> Something went wrong!").complete();
            }
        }
    }
}
