package jda.jayson.guilds.all.commands.other;

import jda.jayson.Nubx;
import jda.jayson.id.Roles;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.apache.commons.io.FileUtils;
import org.json.JSONObject;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class CommandAddOwn {
    public static void onEvent(MessageReceivedEvent e) {
        Message msg = e.getMessage();
        MessageChannel channel = e.getMessage().getChannel();
        String[] argument = msg.getContentRaw().split(" ");
         if (msg.getContentRaw().contains("!cc") && !msg.getContentRaw().contains("list all")) {
             if(e.getGuild().getMembersWithRoles(e.getGuild().getRoleById(Roles.Staff)).contains(e.getMember())) {
                 File file = new File("bot/" + e.getGuild().getIdLong() + "/commands/" + "custom" + ".json");

                String content = "{\"rip\": {\n" +
                        "  \"creator\": \"Jayson.json\",\n" +
                        "  \"aliases\": \"none\",\n" +
                        "  \"content\": \"F.\"\n" +
                        "}}";
                try {
                    content = FileUtils.readFileToString(file, "utf-8");
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                JSONObject json = new JSONObject();
                JSONObject main = new JSONObject(content);
                String joined = String.join(" ", argument);
                joined = joined.substring(argument[0].length() + 1 + argument[1].length());
                json.put("aliases", "none");
                json.put("content", joined);
                json.put("creator", msg.getAuthor().getName());
                main.put(argument[1], json);
                new File("bot/" + e.getGuild().getIdLong() + "/commands/").mkdirs();
                try (
                        BufferedWriter writer = Files.newBufferedWriter(Paths.get("bot/" + e.getGuild().getIdLong() + "/commands/" + "custom" + ".json"))) {
                    main.write(writer, 2, 0);
                    writer.write("\n");
                    channel.sendMessage("> Created command " + argument[1] + " with content " + joined).complete();
                } catch (Exception ex) {
                    Nubx.logger_json.print("Error at writing JSON on addown");
                    //Nubx.logger.error(ex);
                }
            } else {
                 e.getChannel().sendMessage("> Not enough Permissions!").complete();
             }
         }
            File file = new File("bot/" + e.getGuild().getIdLong() + "/commands/" + "custom" + ".json");
            if (file.exists()) {
                String content = null;
                try {
                    content = FileUtils.readFileToString(file, "utf-8");
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                JSONObject json = new JSONObject(content);
                if (msg.getContentRaw().equalsIgnoreCase("!cc list all")) {
                    if(e.getGuild().getMembersWithRoles(e.getGuild().getRoleById(Roles.Staff)).contains(e.getMember())) {
                        channel.sendMessage(json.names().toString()).complete();
                    }
                }
                if (json.has(argument[0])) {
                    JSONObject main = json.getJSONObject(argument[0]);
                    channel.sendMessage(main.getString("content")).complete();
                }
            }
    }
}
