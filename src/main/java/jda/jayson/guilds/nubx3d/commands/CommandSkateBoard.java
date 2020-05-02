package jda.jayson.guilds.nubx3d.commands;

import jda.jayson.file.SkateboardVoid;
import jda.jayson.id.References;
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
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.sql.Ref;

public class CommandSkateBoard {
    public static void onEvent(MessageReceivedEvent e) {
        {
            Message msg = e.getMessage();
            String[] argument = e.getMessage().getContentRaw().split(" ");
            boolean selected = false;


            if(msg.getContentRaw().contains("!skateboard") && !msg.getContentRaw().contains("add") && !msg.getContentRaw().contains("color") && !msg.getContentRaw().contains("type") && !msg.getContentRaw().contains("picture") && !msg.getContentRaw().contains("select")) {
                File file = new File("bot/nubx3d/skateboards/" + argument[1] + ".json");
                if(file.exists()) {
                    SkateboardVoid.load(argument[1]);
                    EmbedBuilder builder = new EmbedBuilder();
                    if (!References.skateboard_color_url.equalsIgnoreCase("")) {
                       // builder.setImage(References.skateboard_color_url);
                    }
                    builder.setColor(new Color(References.skateboard_num1, References.skateboard_num2, References.skateboard_num3));
                    builder.addField("Owner", e.getGuild().getMemberById(References.skateboard_owner).getAsMention(), true);
                    if (!References.skateboard_type.equalsIgnoreCase("")) {
                        builder.addField("Type", References.skateboard_type, true);
                    }
                    if (!References.skateboard_picture.equalsIgnoreCase("")) {
                        builder.setThumbnail(References.skateboard_picture);
                    }
                    builder.addField("Snub Installed", String.valueOf(References.skateboard_snub),true);
                    builder.addField("Med-Kit Installed", String.valueOf(References.skateboard_med_kit),true);
                    builder.addField("Light-Kit Installed", String.valueOf(References.skateboard_light_kit),true);
                    builder.addField("Katstop Installed", String.valueOf(References.skateboard_katstop),true);
                    builder.addField("Shield Installed", String.valueOf(References.skateboard_shield),true);
                    msg.getChannel().sendMessage(builder.build()).complete();
                }
            }

            if(msg.getContentRaw().contains("!skateboard") && msg.getContentRaw().contains("installations")) {
                File file = new File("skateboards/" + argument[1] + ".json");
                if(file.exists()) {
                    SkateboardVoid.load(argument[1]);
                    if (e.getAuthor().getIdLong() == References.skateboard_owner) {
                        switch (argument[3]) {
                            case "medkit": References.skateboard_med_kit = true;
                            case "snub": References.skateboard_snub = true;
                            case "lightkit": References.skateboard_light_kit = true;
                            case "katstop": References.skateboard_katstop = true;
                            case "shield": References.skateboard_shield = true;
                        }
                        SkateboardVoid.save(argument[1]);
                    } else {
                        e.getChannel().sendMessage("> You're not the Owner of this Skateboard!").complete();
                    }
                } else {
                    e.getChannel().sendMessage("> No skateboard with ID " + argument[1] + " found!").complete();
                }
            }

            if(msg.getContentRaw().contains("!skateboard add")) {
                File file = new File("bot/nubx3d/skateboards/" + argument[2] + ".json");
                if(file.exists()) {
                    e.getChannel().sendMessage("> Skateboard with the ID " + argument[2] + " does already exist!").complete();
                } else if(!file.exists()) {
                    References.skateboard_owner = e.getAuthor().getIdLong();
                    SkateboardVoid.save(argument[2]);
                    e.getChannel().sendMessage("> Created Skateboard with the ID " + argument[2] + "!").complete();
                }else {
                    e.getChannel().sendMessage("> No skateboard with ID " + argument[1] + " found!").complete();
                }
            }

            if(msg.getContentRaw().contains("!skateboard") && msg.getContentRaw().contains("embedded") && msg.getContentRaw().contains("color") && msg.getContentRaw().contains("preview")) {
                File file = new File("bot/nubx3d/skateboards/" + argument[1] + ".json");

                if(file.exists()) {
                    SkateboardVoid.load(argument[1]);
                    if(e.getAuthor().getIdLong() == References.skateboard_owner) {
                        EmbedBuilder builder = new EmbedBuilder();
                        if (!References.skateboard_color_url.equalsIgnoreCase("")) {
                            builder.setImage(References.skateboard_color_url);
                        }
                        builder.setColor(new Color(References.skateboard_num1, References.skateboard_num2, References.skateboard_num3));
                        builder.addField("EmbeddedMessage example", "EmbeddedMessage example", true);
                        if (!References.skateboard_color_url.equalsIgnoreCase("")) {
                            e.getChannel().sendMessage(References.skateboard_color_url);
                        }
                        msg.getChannel().sendMessage(builder.build()).complete();
                    }
                    else {
                        e.getChannel().sendMessage("> You're not the Owner of this Skateboard!").complete();
                    }
                } else {
                    e.getChannel().sendMessage("> No skateboard with ID " + argument[1] + " found!").complete();
                }
            }
            if(msg.getContentRaw().contains("!skateboard") && msg.getContentRaw().contains("type")) {
                File file = new File("bot/nubx3d/skateboards/" + argument[1] + ".json");
                if(file.exists()) {

                    SkateboardVoid.load(argument[1]);
                    if(e.getAuthor().getIdLong() == References.skateboard_owner) {
                        if (argument[3].equalsIgnoreCase("longboard")) {
                            References.skateboard_type = "longboard";
                            e.getChannel().sendMessage("> Changed type to longboard!").complete();
                        }
                        if (argument[3].equalsIgnoreCase("shortboard")) {
                            References.skateboard_type = "shortboard";
                            e.getChannel().sendMessage("> Changed type to shortboard!").complete();
                        }
                    }
                    else {
                        e.getChannel().sendMessage("> You're not the Owner of this Skateboard!").complete();
                    }
                    SkateboardVoid.save(argument[1]);
                } else {
                    e.getChannel().sendMessage("> No Skateboard with the ID " + argument[1] + " found!").complete();
                }
            }

            if(msg.getContentRaw().contains("!skateboard") && msg.getContentRaw().contains("picture") && msg.getAttachments().size() > 0) {
                File file = new File("bot/nubx3d/skateboards/" + argument[1] + ".json");
                if(file.exists()) {
                    SkateboardVoid.load(argument[1]);
                    if(e.getAuthor().getIdLong() == References.skateboard_owner) {
                        References.skateboard_picture = msg.getAttachments().get(0).getUrl();
                        try {
                            URL url = new URL(References.skateboard_picture);
                            URLConnection openConnection = url.openConnection();
                            boolean check = true;
                            try {

                                openConnection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95 Safari/537.11");
                                openConnection.connect();

                                if (openConnection.getContentLength() > 8000000) {
                                    System.out.println(" file size is too big.");
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
                                jpegParams.setCompressionQuality(0.1f);
                                new File("bot/nubx3d/skateboards/pictures/").mkdir();
                                File file_img = new File("bot/nubx3d/skateboards/pictures/" + argument[1] + ".jpg");
                                try {
                                    final ImageWriter writer = ImageIO.getImageWritersByFormatName("jpg").next();
                                    writer.setOutput(new FileImageOutputStream(file_img));
                                    writer.write(null, new IIOImage(img, null, null), jpegParams);
                                } catch (IOException exc) {
                                    exc.printStackTrace();
                                    e.getChannel().sendMessage("> Couldn't save Image! Using URL!").complete();
                                }

                            }
                            File img = new File("bot/nubx3d/skateboards/pictures/" + argument[1] + ".jpg");
                            if (img.exists()) {
                                e.getChannel().sendMessage("> Picture has been updated to:").complete();
                                e.getChannel().sendFile(img).complete();
                            } else {
                                e.getChannel().sendMessage("> Picture has been updated to " + References.skateboard_picture).complete();
                            }
                            SkateboardVoid.save(argument[1]);
                        } catch (MalformedURLException e1) {
                            e1.printStackTrace();
                        } catch (IOException e1) {
                            e1.printStackTrace();
                        }
                    }
                    else {
                        e.getChannel().sendMessage("> You're not the Owner of this Skateboard!").complete();
                    }
                } else {
                    e.getChannel().sendMessage("> No Skateboard with the ID " + argument[1] + " found!").complete();
                }
            }
            if(msg.getContentRaw().contains("!skateboard") && msg.getContentRaw().contains("picture") && msg.getAttachments().size() < 1)  {
                File file = new File("bot/nubx3d/skateboards/" + argument[1] + ".json");
                if(file.exists()) {
                    SkateboardVoid.load(argument[1]);
                    if(e.getAuthor().getIdLong() == References.skateboard_owner) {
                        References.skateboard_picture = argument[3];
                        try {
                            URL url = new URL(References.skateboard_picture);
                            InputStream in = new BufferedInputStream(url.openStream());
                            ByteArrayOutputStream out = new ByteArrayOutputStream();
                            byte[] buf = new byte[1024];
                            int n = 0;
                            while (-1 != (n = in.read(buf))) {
                                out.write(buf, 0, n);
                            }
                            out.close();
                            in.close();
                            byte[] response = out.toByteArray();
                            new File("bot/nubx3d/skateboards/pictures").mkdir();
                            FileOutputStream fos = new FileOutputStream("bot/nubx3d/skateboards/pictures/" + argument[1] + ".png");
                            fos.write(response);
                            fos.close();
                        } catch (Exception exc) {
                            e.getChannel().sendMessage("> Couldn't save image to a File! Saved URL link!").complete();
                        }
                        e.getChannel().sendMessage("> Picture has been updated to " + References.skateboard_picture).complete();
                        SkateboardVoid.save(argument[1]);
                    }  else {
                        e.getChannel().sendMessage("> You're not the Owner of this Skateboard!").complete();
                    }
                    } else {
                    e.getChannel().sendMessage("> No Skateboard with the ID " + argument[1] + " found!").complete();
                }
            }

            if(msg.getContentRaw().contains("!skateboard") && !msg.getContentRaw().contains("preview") && msg.getContentRaw().contains("embedded") && msg.getContentRaw().contains("color")) {
                File file = new File("bot/nubx3d/skateboards/" + argument[1] + ".json");
                if (file.exists()) {

                    new File("bot/files/").mkdirs();
                    SkateboardVoid.load(argument[1]);
                    if(e.getAuthor().getIdLong() == References.skateboard_owner) {
                        References.skateboard_num1 = Integer.parseInt(argument[4]);
                        References.skateboard_num2 = Integer.parseInt(argument[5]);
                        References.skateboard_num3 = Integer.parseInt(argument[6]);
                        BufferedImage img = new BufferedImage(64, 64, BufferedImage.TYPE_INT_RGB);
                        int rgb = new Color(References.skateboard_num1, References.skateboard_num2, References.skateboard_num3).getRGB();
                        for (int i = 0; i < 64; i++) {
                            for (int j = 0; j < 64; j++) {
                                img.setRGB(i, j, rgb);
                            }
                        }
                        try {
                            File img_file = new File("bot/files/skateboard_colorsample.png");
                            ImageIO.write(img, "png", img_file);
                            e.getChannel().sendMessage("> Skateboard embedded color has been changed to: ").complete();
                            Message esmg = msg.getChannel().sendFile(img_file).complete();
                            References.skateboard_color_url = esmg.getAttachments().get(0).getUrl();
                        } catch (IOException exc) {
                            System.out.println(exc);
                        }
                        SkateboardVoid.save(argument[1]);
                    }  else {
                        e.getChannel().sendMessage("> You're not the Owner of this Skateboard!").complete();
                    }
                } else {
                    e.getChannel().sendMessage("> No Skateboard with the ID " + argument[1] + " found!").complete();
                }
            }
        }
    }
}
