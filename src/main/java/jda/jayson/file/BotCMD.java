package jda.jayson.file;

import jda.jayson.Nubx;
import jda.jayson.guilds.nullbloxme.events.other.EventHighScore;
import jda.jayson.id.ID;
import jda.jayson.id.References;
import org.apache.commons.io.FileUtils;
import org.json.JSONObject;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;

public class BotCMD {
    public static void save_old() {
        new File("bot/").mkdirs();
        try{
            FileOutputStream saveFile = new FileOutputStream("bot/nullbloxme/bot.set");
            ObjectOutputStream save = new ObjectOutputStream(saveFile);
            save.writeObject(References.creation_player);
            save.writeObject(References.creation_player_url);
            save.writeObject(References.creationcount_showcase);
            save.writeObject(EventHighScore.sorted);
            save.writeObject(EventHighScore.remover);
            save.writeObject(EventHighScore.points);
            save.writeObject(ID.currency);
        } catch (Exception exc) {
            exc.printStackTrace();
        }
    }

    public static void load_old() {
        try {
            FileInputStream saveFile = new FileInputStream("bot/nullbloxme/bot.set");
            ObjectInputStream save = new ObjectInputStream(saveFile);
            References.creation_player = (HashMap<Integer, Long>) save.readObject();
            References.creation_player_url = (HashMap<Integer, String>) save.readObject();
            References.creationcount_showcase = (Integer) save.readObject();
            ID.currency = (String) save.readObject();
            EventHighScore.sorted = (ArrayList<Integer>) save.readObject();
            EventHighScore.remover = (HashMap<Long, Integer>) save.readObject();
            EventHighScore.points = (HashMap<Integer, Long>) save.readObject();
        } catch (Exception exc) {
            exc.printStackTrace();
            save();
        }
    }
    public static void load() {
        File file = new File("bot/bot.json");
        if (!file.exists()) {
            load_old();
            save();
            Nubx.logger_json.print("Created JSON for Bot");
        }
        String content = null;
        try {
            content = FileUtils.readFileToString(file, "utf-8");
        } catch (IOException e) {
            References.inventory.clear();
            Nubx.logger_json.error(e);
            e.printStackTrace();
        }
       try {

           JSONObject json = new JSONObject(content);
           JSONObject creations = json.getJSONObject("creations");
           JSONObject highscore = json.getJSONObject("highscore");
           ID.currency = json.getString("currency");
           for (String s : creations.getJSONObject("players").keySet()) {
               References.creation_player.put(Integer.valueOf(s), json.getJSONObject("players").getLong(s));
           }
           for (String url : creations.getJSONObject("url").keySet()) {
               References.creation_player_url.put(Integer.parseInt(url), json.getJSONObject("url").getString(url));
           }
           References.creationcount_showcase = creations.getInt("numbers");
           for (Object sorted : highscore.getJSONArray("sorted").toList()) {
               EventHighScore.sorted.add((Integer) sorted);
           }
           for (String remover : highscore.getJSONObject("remover").keySet()) {
               EventHighScore.remover.put(Long.valueOf(remover), json.getJSONObject("remover").getInt(remover));
           }
           for (String points : highscore.getJSONObject("points").keySet()) {
               EventHighScore.points.put(Integer.valueOf(points), json.getJSONObject("points").getLong(points));
           }
       } catch (Exception exc) {

       }
    }
    public static void save() {
        JSONObject json = new JSONObject();
        JSONObject creations = new JSONObject();
        JSONObject highscore = new JSONObject();
        json.put("currency", ID.currency);
        creations.put("players", References.creation_player);
        creations.put("url", References.creation_player_url);
        creations.put("numbers", References.creationcount_showcase);
        highscore.put("sorted", EventHighScore.sorted);
        highscore.put("remover", EventHighScore.remover);
        highscore.put("points", EventHighScore.points);

        json.put("creations",creations);
        json.put("highscore",highscore);
        try (
                BufferedWriter writer = Files.newBufferedWriter(Paths.get("bot/bot.json"))) {
            json.write(writer, 2, 0);
            writer.write("\n");
            Nubx.logger_json.print("Saved JSON for Bot");
        } catch (Exception ex) {
            Nubx.logger_json.print("Error at writing JSON for Bot");
            Nubx.logger_json.error(ex);
        }
    }
}
