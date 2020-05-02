package jda.jayson.guilds.all.commands.filemanagement;

import jda.jayson.Nubx;
import jda.jayson.file.JSON;
import jda.jayson.id.ID;
import jda.jayson.id.Roles;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.apache.commons.io.FileUtils;
import org.json.JSONObject;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class CommandEditJson {
    public static void onEvent(MessageReceivedEvent event) {
        Message msg = event.getMessage();
        String content = msg.getContentRaw();
        String argument[] = content.split(" ");
        if (event.getGuild().getMembersWithRoles(event.getGuild().getRoleById(Roles.Staff)).contains(event.getMember())) {
            if (msg.getContentRaw().contains("!json keys") && !msg.getContentRaw().contains("edit")) {
                Member user;
                if (msg.getMentionedMembers().size() > 0) {
                    user = msg.getMentionedMembers().get(0);
                } else {
                    user = msg.getGuild().getMembersByName(argument[2], true).get(0);
                }
                String c = null;
                try {
                    c = FileUtils.readFileToString(new File("bot/users/" + user.getIdLong() + ".json"), "utf-8");
                    event.getChannel().sendMessage(c).complete();
                } catch (IOException e) {
                    event.getChannel().sendMessage("> Couldn't get File!").complete();
                    e.printStackTrace();
                }
            }
            if(msg.getContentRaw().contains("!json edit") && !msg.getContentRaw().contains("keys")) {
                Member user;
                if (msg.getMentionedMembers().size() > 0) {
                    user = msg.getMentionedMembers().get(0);
                } else {
                    user = msg.getGuild().getMembersByName(argument[2], true).get(0);
                }
                String c = null;
                try {
                    c = FileUtils.readFileToString(new File("bot/users/" + user.getIdLong() + ".json"), "utf-8");
                    JSONObject json = new JSONObject(c);
                    json.put(argument[3],argument[4]);
                    try {
                            BufferedWriter writer = Files.newBufferedWriter(Paths.get("bot/users/" + user.getIdLong() + ".json"));
                        json.write(writer, 2, 0);
                        writer.write("\n");
                        Nubx.logger_json.print("Saved JSON for ID " + user.getIdLong() + " (Command)");
                    } catch (Exception ex) {
                        Nubx.logger_json.print("Error at writing JSON for ID " + user.getIdLong() + " (Command)");
                        Nubx.logger_json.error(ex);
                        ex.printStackTrace();
                    }
                   // event.getChannel().sendMessage(c).complete();
                } catch (IOException e) {
                    event.getChannel().sendMessage("> Couldn't get File! Or Key!").complete();
                    e.printStackTrace();
                }
            }
        }
    }
}
