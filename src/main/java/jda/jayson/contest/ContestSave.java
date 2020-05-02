package jda.jayson.contest;

import com.iwebpp.crypto.TweetNaclFast;
import jda.jayson.Nubx;
import jda.jayson.id.References;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.User;
import org.apache.commons.io.FileUtils;
import org.json.JSONObject;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;

public class ContestSave {

   public static HashMap<Long, Integer> contest_plus_count = new HashMap<>();
   public static ArrayList<Long> contest_message = new ArrayList<>();
   public static HashMap<Long, Long> contest_message_user = new HashMap<>();
   public static HashMap<Long, Boolean> contest_reaction_plus = new HashMap<>();
    public static HashMap<Long, Boolean> contest_reaction_minus = new HashMap<>();
    public static void save() {
        String id = "contest";
        JSONObject json = new JSONObject();
        json.put("plus_count", contest_plus_count);
        json.put("message", contest_message);
        json.put("message_author", contest_message_user);
        json.put("plus_reaction", contest_reaction_plus);
        json.put("minus_reaction", contest_reaction_minus);
        new File("bot/contest/").mkdir();
        try (
                BufferedWriter writer = Files.newBufferedWriter(Paths.get("bot/contest/" + id + ".json"))) {
            json.write(writer, 2, 0);
            writer.write("\n");
            Nubx.logger_json.print("Saved JSON for ID " + id);
        } catch (Exception ex) {
            Nubx.logger_json.print("Error at writing JSON for ID " + id);
            Nubx.logger_json.error(ex);
        }
    }

    public static void load() {
        String id = "contest";
        File file = new File("bot/contest/" + id + ".json");

            if (!file.exists()) {
                save();
                Nubx.logger_json.print("Created JSON for ID " + id);
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
                for (String plus_count : json.getJSONObject("plus_count").keySet()) {
                    contest_plus_count.put(Long.valueOf(plus_count),json.getJSONObject("plus_count").getInt(plus_count));
                }
                for (Object message : json.getJSONArray("message").toList()) {
                    contest_message.add((Long) message);
                }
                for (String message_author : json.getJSONObject("message_author").keySet()) {
                    contest_message_user.put(Long.valueOf(message_author),json.getJSONObject("message_author").getLong(message_author));
                }
                for (String aLong : json.getJSONObject("plus_reaction").keySet()) {
                    contest_reaction_plus.put(Long.valueOf(aLong),json.getJSONObject("plus_reaction").getBoolean(aLong));
                }
                for (String aLong : json.getJSONObject("minus_reaction").keySet()) {
                    contest_reaction_minus.put(Long.valueOf(aLong),json.getJSONObject("minus_reaction").getBoolean(aLong));

                }

                Nubx.logger_json.print("Loaded JSON for ID " + id);
            } catch (Exception ex) {
                Nubx.logger_json.error(ex);
                ex.printStackTrace();
            }
        }
}
