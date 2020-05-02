package jda.jayson;

import com.jayson.nubx.NubxURL;
import net.dv8tion.Uploader;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.apache.commons.io.FileUtils;
import org.json.JSONObject;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.File;
import java.util.Objects;

public class RepostCheck {
    public static void onEvent(MessageReceivedEvent event) {
        if (event.getMessage().getAttachments().size() > 0 && event.getChannel().getIdLong() == 644642629597921285L && event.getMessage().getAttachments().get(0).isImage()) {
            try {
                new File("bot/temporary/memes").mkdirs();
                new File("bot/memes").mkdirs();
                File original = new File("null");
                new NubxURL(event.getMessage().getAttachments().get(0).getUrl()).downloadToFile("bot/temporary/memes/" + event.getMessage().getIdLong() + ".png");
                for (File listFile : Objects.requireNonNull(new File("bot/memes").listFiles())) {
                    //System.out.println(new File("bot/temporary/memes/" + event.getMessage().getIdLong() + ".png").length() + ", " + listFile.length() + "," + listFile.getName());
                    if (listFile.toString().contains(".png")) {
                        original = listFile;
                        BufferedImage img0 = null;
                        img0 = ImageIO.read(original);
                        BufferedImage img1 = null;
                        img1 = ImageIO.read(new File("bot/temporary/memes/" + event.getMessage().getIdLong() + ".png"));
                        //System.out.println(img0.getRGB(img0.getHeight() / 2, img0.getWidth() / 2) + "," + img1.getRGB(img1.getHeight() / 2, img1.getWidth() / 2));
                        if (img0.getRGB(img0.getHeight() / 2, img0.getWidth() / 2) == img1.getRGB(img1.getHeight() / 2, img1.getWidth() / 2) ||  new File("bot/temporary/memes/" + event.getMessage().getIdLong() + ".png").length() == listFile.length()) {
                            int same_pxs = 0;
                            int pxs = ((DataBufferByte) img0.getRaster().getDataBuffer()).getData().length;
                            for (int i = 0; i < img0.getHeight() / 2; i++) {
                                for (int i1 = 0; i1 < img1.getHeight() / 2; i1++) {
                                    if (img0.getRGB(i, i) == img1.getRGB(i1, i1) && img0.getRGB(i, i) != 0xffffffff) {
                                        same_pxs++;
                                    }
                                }
                            }
                            if (same_pxs > 100) {
                                EmbedBuilder builder = new EmbedBuilder();
                                builder.setDescription("This is probably a repost!");
                                builder.addField("Total Pixels", "`" + pxs + "`", true);
                                builder.addField("Same Pixels", "`" + same_pxs + "`", true);
                                JSONObject json = new JSONObject(Uploader.upload(original));
                                JSONObject data = json.getJSONObject("data");
                                builder.setThumbnail(data.getString("link"));
                                JSONObject json1 = new JSONObject(new File("bot/temporary/memes/" + event.getMessage().getIdLong() + ".png"));
                                JSONObject data1 = json1.getJSONObject("data");
                                builder.setImage(data1.getString("link"));
                                event.getChannel().sendMessage(builder.build()).complete();
                                // event.getChannel().sendMessage("> This is probably a repost!\n> Total Pixels: " + pxs + "\n> Same Pixels: " + same_pxs + " (Only Checked a Few)").complete();
                                original.delete();
                            }
                            FileUtils.deleteDirectory(new File("bot/temporary/memes/"));
                            break;
                        }
                     }
                    }
                    new NubxURL(event.getMessage().getAttachments().get(0).getUrl()).downloadToFile("bot/memes/" + event.getMessage().getIdLong() + ".png");
                } catch(Exception exc){
                    exc.printStackTrace();
                }
        }
    }
}
