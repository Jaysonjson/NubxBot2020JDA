package jda.jayson.guilds.nullbloxme.events.other;

import jda.jayson.Nubx;
import jda.jayson.command.Progress;
import jda.jayson.file.BotCMD;
import jda.jayson.file.JSON;
import jda.jayson.file.user.DiscordUser;
import jda.jayson.id.References;
import jda.jayson.id.Roles;
import jda.jayson.id.Users;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.apache.commons.io.FileUtils;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class HighScoreNew {

    public static HashMap<Integer, String> points = new HashMap<>();
    public static HashMap<String, Integer> special = new HashMap<>();
    public static HashMap<String,Integer> remover = new HashMap<>();
    public static ArrayList<Integer> sorted = new ArrayList<>();
    public static void onEvent(MessageReceivedEvent e) {

        if(e.getMessage().getContentRaw().equalsIgnoreCase("!updatehighscore_new") && e.getGuild().getMembersWithRoles(e.getGuild().getRoleById(Roles.Staff)).contains(e.getMember())) {
        }
        if (e.getMessage().getContentRaw().equalsIgnoreCase("!highscoreinit_new") && e.getGuild().getMembersWithRoles(e.getGuild().getRoleById(Roles.Staff)).contains(e.getMember())) {
            try {
                ScheduledExecutorService ses = Executors.newSingleThreadScheduledExecutor();
                ses.scheduleAtFixedRate(() -> {
                    try {
                        updateHighscore(e);
                    } catch (Exception e1) {
                        e1.printStackTrace();
                    }
                }, 0, 3, TimeUnit.HOURS);
            } catch (ArrayIndexOutOfBoundsException ex) {
                ex.getCause();
                ex.getLocalizedMessage();
            }
        }
    }
    public static void updateHighscore(MessageReceivedEvent e) {
        points.clear();
        sorted.clear();
        List<File> files = (List<File>) FileUtils.listFiles(new File("bot/users"), new String[]{"json"}, true);
        for (File file : files) {
            String filename = file.getName();
            filename = filename.replaceAll(".json","");
            DiscordUser discordUser = JSON.loadUser(filename);
            String user_name;
            try {
                user_name = e.getGuild().getMemberById(filename).getUser().getName();
            } catch (Exception e1) {
                user_name = (String) discordUser.discord.name;
            }
            String content = null;
            try {
                content = FileUtils.readFileToString(file, "utf-8");
            } catch (IOException ec) {
                discordUser.inventory.clear();
                Nubx.logger_json.error(ec);
                ec.printStackTrace();
            }
            if(discordUser.points != 0 && !filename.equalsIgnoreCase("f" + Users.Jayson_json) && !filename.equals("" + Users.Keksefreak) && !filename.equals("" + Users.Jayson_Alt) && !filename.equals("" + Users.Jayson_Alt1) && !filename.equals("" + Users.Test_Bot) && !filename.equals("" + Users.Nubx)) {
                if (remover.containsKey(user_name)) {
                    points.remove(remover.get(user_name));
                    sorted.remove(remover.get(user_name));
                }
                remover.put(user_name, discordUser.points);
                points.put(discordUser.points, user_name);
                sorted.add(discordUser.points);
            }
            if(filename.equals("" + Users.Jayson_json) || filename.equals("" + Users.Keksefreak) || filename.equals("" + Users.Jayson_Alt) || filename.equals("" + Users.Jayson_Alt1) || filename.equals("" + Users.Test_Bot) || filename.equals("" + Users.Nubx)) {
                special.put(user_name,discordUser.points);
            }
        }
        //BotCMD.save();
        Collections.sort(sorted);
        Collections.reverse(sorted);
        StringBuilder s = new StringBuilder("1: (" + References.formatInteger(sorted.get(0)) + ") " + points.get(sorted.get(0)));
        int num = 1;
        for (int i = 1; i < sorted.size(); i++) {
            num++;
            String p = points.get(sorted.get(i));
            String user_name = "nullpointer_catch";
            try {
                user_name = p;
            } catch (NullPointerException e1) {
            }
            if (user_name != null && !user_name.equalsIgnoreCase("nullpointer_catch")) {
                s.append("\n").append(num).append(": (").append(References.formatInteger(sorted.get(i))).append(")  ").append(user_name);
            }
            if (i == 26) {
                break;
            }
        }
        s.append("\nOther (Removed for Fairness): ");
        for (String s1 : special.keySet()) {
            s.append(s1).append(": (").append(References.formatInteger(special.get(s1))).append("), ");
        }
        Date date = new Date();
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/YY HH:mm:ss");
        String d = "\n > Last update: " + dateFormat.format(date);
        Objects.requireNonNull(e.getGuild().getTextChannelById(641041535894552607L)).editMessageById(641270194639208508L, "```" + s + "```" + d).complete();
        //e.getChannel().sendMessage(s).complete();
    }
}
