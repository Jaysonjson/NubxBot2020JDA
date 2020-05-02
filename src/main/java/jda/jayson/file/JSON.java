package jda.jayson.file;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import jda.jayson.file.user.DiscordUser;
import jda.jayson.guilds.nullbloxme.commands.other.inventory.InventoryItem;
import jda.jayson.Nubx;
import jda.jayson.id.References;
import org.apache.commons.io.FileUtils;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

public class JSON {

    public static void saveUser(String id, DiscordUser user) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String json = gson.toJson(user);
        new File("bot/users/").mkdir();
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(new File("bot/users/" + id + ".json"));
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fileOutputStream);
            outputStreamWriter.append(json);
            outputStreamWriter.close();
            Nubx.logger_json.print("Saved JSON for ID " + id);
        } catch (Exception ex) {
                Nubx.logger_json.print("Error at writing JSON for ID " + id);
                Nubx.logger_json.error(ex);
        }
    }

    public static void saveUser(DiscordUser discordUser) {
        saveUser(String.valueOf(discordUser.getLongID()), discordUser);
    }

    public static DiscordUser loadUser(String id) {
        Gson gson = new Gson();
        String content = "{}";
        try {
            content = FileUtils.readFileToString(new File("bot/users/" + id + ".json"), "utf-8");
            return gson.fromJson(content, DiscordUser.class);
        } catch (IOException ioexc) {
            Nubx.logger_json.print("Error at loading JSON for ID " + id);
        }
        DiscordUser discordUser = new DiscordUser();
        discordUser.setLongID(Long.valueOf(id));
        return discordUser;
    }

    public static DiscordUser loadUser(Long id) {
        return loadUser(String.valueOf(id));
    }
}
