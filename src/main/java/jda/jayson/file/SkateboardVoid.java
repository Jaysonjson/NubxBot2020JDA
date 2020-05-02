package jda.jayson.file;

import jda.jayson.Nubx;
import jda.jayson.id.References;
import org.apache.commons.io.FileUtils;
import org.json.JSONObject;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class SkateboardVoid {
    public static void resetJson() {
        References.skateboard_type = "";
        References.skateboard_num1 = 0;
        References.skateboard_num2 = 0;
        References.skateboard_num3 = 0;
        References.skateboard_color_url = "";
        References.skateboard_owner = 0L;
        References.skateboard_picture = "";
        References.skateboard_snub = false;
        References.skateboard_med_kit = false;
        References.skateboard_light_kit = false;
        References.skateboard_shield = false;
        References.skateboard_katstop = false;
    }

    public static void save(String name) {
        JSONObject json = new JSONObject();
        json.put("type", References.skateboard_type);
        json.put("rgb_red", References.skateboard_num1);
        json.put("rgb_green", References.skateboard_num2);
        json.put("rgb_blue", References.skateboard_num3);
        json.put("color_url", References.skateboard_color_url);
        json.put("owner",References.skateboard_owner);
        json.put("picture",References.skateboard_picture);
        json.put("snub",References.skateboard_snub);
        json.put("medkit",References.skateboard_med_kit);
        json.put("lightkit",References.skateboard_light_kit);
        json.put("shield",References.skateboard_shield);
        json.put("katstop",References.skateboard_katstop);
        new File("bot/nubx3d/skateboards/").mkdirs();
        try (
                BufferedWriter writer = Files.newBufferedWriter(Paths.get("bot/nubx3d/skateboards/" + name + ".json"))) {
            json.write(writer, 2, 0);
            writer.write("\n");
        } catch (Exception ex) {
            ex.printStackTrace();
            Nubx.logger_json.print("Error at printing Skateboard JSON!");
        }
    }

    public static void load(String name) {
        File file = new File("bot/nubx3d/skateboards/" + name + ".json");
        File old_file = new File("bot/nubx3d/skateboards/" + name);
        if(!file.exists()) {
            resetJson();
            save(name);
            Nubx.logger_json.print("Created Skateboard JSON for ID " + name);
        }
        String content = null;
        try {
            content = FileUtils.readFileToString(file, "utf-8");
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            JSONObject json = new JSONObject(content);
            References.skateboard_type = json.getString("type");
            References.skateboard_num1 = json.getInt("rgb_red");
            References.skateboard_num2 = json.getInt("rgb_green");
            References.skateboard_num3 = json.getInt("rgb_blue");
            References.skateboard_color_url = json.getString("color_url");
            References.skateboard_owner = json.getLong("owner");
            References.skateboard_picture = json.getString("picture");
            References.skateboard_snub = json.getBoolean("snub");
            References.skateboard_med_kit = json.getBoolean("medkit");
            References.skateboard_light_kit = json.getBoolean("lightkit");
            References.skateboard_shield = json.getBoolean("shield");
            References.skateboard_katstop = json.getBoolean("katstop");
        } catch (Exception ex) {
        }
    }
}
