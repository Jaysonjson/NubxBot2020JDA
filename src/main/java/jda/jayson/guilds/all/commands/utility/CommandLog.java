package jda.jayson.guilds.all.commands.utility;

import com.jayson.nubx.NubxUtil;
import jda.jayson.Nubx;
import jda.jayson.id.Roles;
import net.dv8tion.jda.api.entities.Invite;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class CommandLog {
    public static void onEvent(MessageReceivedEvent e) {
        Message msg = e.getMessage();
        MessageChannel channel = e.getChannel();
        String[] argument = msg.getContentRaw().split(" ");
            if (msg.getContentRaw().equalsIgnoreCase("!log list")) {
                if (e.getGuild().getMembersWithRoles(e.getGuild().getRoleById(Roles.Staff)).contains(e.getMember())) {
                    ArrayList<String> f = new ArrayList<>();
                    File dir = new File("bot/logs/");
                    File[] files = dir.listFiles();
                    assert files != null;
                    for (File file : files) {
                        if (file.isFile() && !file.getName().contains(".DS_Store")) {
                            f.add("\n" + file.getName());
                        }
                    }
                    int length = String.valueOf(f).length();
                    length -= 1;
                    String w = String.valueOf(f).substring(1, length);
                    channel.sendMessage(w).complete();
                } else {
                    e.getChannel().sendMessage("> Not enough Permissions!").complete();
                }
            }
        if (e.getGuild().getMembersWithRoles(e.getGuild().getRoleById(Roles.Staff)).contains(e.getMember())) {
            if (msg.getContentRaw().contains("!log send")) {
                if (e.getGuild().getMembersWithRoles(e.getGuild().getRoleById(Roles.Staff)).contains(e.getMember())) {
                    e.getChannel().sendFile(new File("bot/logs/" + argument[2])).complete();
                } else {
                     e.getChannel().sendMessage("> Not enough Permissions!").complete();
                }
            }
        } else {
            //e.getChannel().sendMessage("> Not enough Permissions!").complete();
        }
        if (e.getGuild().getMembersWithRoles(e.getGuild().getRoleById(Roles.Staff)).contains(e.getMember())) {
            if (msg.getContentRaw().equalsIgnoreCase("!log write")) {
                if (e.getGuild().getMembersWithRoles(e.getGuild().getRoleById(Roles.Staff)).contains(e.getMember())) {
                    DateFormat dateFormat = new SimpleDateFormat("MM_dd_HH_mm_ss");
                    Date date = new Date();
                    Nubx.logger.write(new File("bot/logs/" + dateFormat.format(date) + "_log"), "");
                    NubxUtil.writeLog(new File("bot/logs/" + dateFormat.format(date) + "_log"), "");
                } else {
                    e.getChannel().sendMessage("> Not enough Permissions!").complete();
                }
            }
        } else {
               // e.getChannel().sendMessage("> Not enough Permissions!").complete();
            }
    }
}
