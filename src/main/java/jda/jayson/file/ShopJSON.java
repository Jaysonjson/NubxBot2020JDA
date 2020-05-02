package jda.jayson.file;

import jda.jayson.Nubx;
import jda.jayson.id.References;
import net.dv8tion.jda.api.EmbedBuilder;
import org.apache.commons.io.FileUtils;
import org.json.JSONObject;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;

public class ShopJSON {
    private static void reset() {
        References.shop_price = 0;
        References.shop_owner = 0L;
        References.shop_download_amount = 0;
        References.shop_total_nubx_earned = 0;
        Nubx.logger_json.print("Resetted Shop JSON data");
    }


    public static void save(String id) {
        JSONObject json = new JSONObject();
        json.put("price", References.shop_price);
        json.put("owner", References.shop_owner);
        json.put("download_amount", References.shop_download_amount);
        json.put("total_nubx_earned", References.shop_total_nubx_earned);


        new File("bot/shop/models/" + id + "/extracted").mkdirs();
        try (
                BufferedWriter writer = Files.newBufferedWriter(Paths.get("bot/shop/models/" + id + ".json"))) {
            json.write(writer, 2, 0);
            writer.write("\n");
            Nubx.logger_json.print("Saved JSON for Shop ID " + id);
        } catch (Exception ex) {
            Nubx.logger_json.print("Error at writing JSON for Shop ID " + id);
            Nubx.logger_json.error(ex);
        }
    }

    public static void load(String id) {
            File file = new File("bot/shop/models/" + id + ".json");

            if (!file.exists()) {
                reset();
                save(id);
                Nubx.logger_json.print("Created JSON for Shop ID " + id);
            }
            String content = null;
            try {
                content = FileUtils.readFileToString(file, "utf-8");
            } catch (IOException e) {
                Nubx.logger_json.error(e);
                e.printStackTrace();
            }
            try {
                JSONObject json = new JSONObject(content);
                References.shop_price = json.getInt("price");
                References.shop_owner = json.getLong("owner");
                References.shop_download_amount = json.getInt("download_amount");
                References.shop_total_nubx_earned = json.getInt("total_nubx_earned");
            } catch (Exception ex) {
                Nubx.logger_json.error(ex);
            }
    }


    public static void load_add(String id, EmbedBuilder builder) {
        File file = new File("bot/shop/models/" + id + "/extracted/additional.json");
        if(file.exists()) {
            String content = null;
            try {
                content = FileUtils.readFileToString(file, "utf-8");
            } catch (IOException e) {
                Nubx.logger_json.error(e);
                e.printStackTrace();
            }
            try {
                JSONObject json = new JSONObject(content);
                for (String s : json.keySet()) {
                    if(!s.equalsIgnoreCase("")) {
                        builder.addField(s, json.get(s).toString(), true);
                    }
                }
            } catch (Exception ex) {
                Nubx.logger_json.error(ex);
            }
        }
    }
}
