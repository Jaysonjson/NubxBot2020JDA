package jda.jayson.guilds.nullbloxme.events.other;

import jda.jayson.file.JSON;
import jda.jayson.file.user.DiscordUser;
import jda.jayson.id.References;
import jda.jayson.id.Roles;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class EventHighScore {
    public static HashMap<Integer, Long> points = new HashMap<>();
    public static HashMap<Long,Integer> remover = new HashMap<>();
    public static ArrayList<Integer> sorted = new ArrayList<>();

    public static void onEvent(MessageReceivedEvent e) {
        User user = e.getAuthor();
        Long user_id = user.getIdLong();
        DiscordUser discordUser = JSON.loadUser(user_id);
        if(e.getMessage().getContentRaw().equalsIgnoreCase("!updatehighscore") && e.getGuild().getMembersWithRoles(e.getGuild().getRoleById(Roles.Staff)).contains(e.getMember())) {
            updateHighscore(e);
        }
        // BotCMD.load();
        if (remover.containsKey(user_id)) {
            points.remove(remover.get(user_id));
            sorted.remove(remover.get(user_id));
        }
        remover.put(e.getAuthor().getIdLong(), discordUser.points);
        points.put(discordUser.points, e.getAuthor().getIdLong());
        sorted.add(discordUser.points);
        System.out.println(sorted);
        //BotCMD.save();
        Collections.sort(sorted);
        Collections.reverse(sorted);

        if (e.getMessage().getContentRaw().equalsIgnoreCase("!highscoreinit") && e.getGuild().getMembersWithRoles(e.getGuild().getRoleById(Roles.Staff)).contains(e.getMember())) {
            try {
                ScheduledExecutorService ses = Executors.newSingleThreadScheduledExecutor();
                ses.scheduleAtFixedRate(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            updateHighscore(e);
                        } catch (Exception e1) {
                            e1.printStackTrace();
                        }
                    }
                }, 0, 3, TimeUnit.HOURS);
            } catch (ArrayIndexOutOfBoundsException ex) {
                ex.getCause();
                ex.getLocalizedMessage();
            }
        }
        JSON.saveUser(String.valueOf(user_id), discordUser);
    }
    public static void updateHighscore(MessageReceivedEvent e) {
        String s = "1: (" + References.formatInteger(sorted.get(0)) + ") " + e.getGuild().getMemberById(points.get(sorted.get(0))).getUser().getName();
        int num = 1;
        for (int i = 1; i < sorted.size(); i++) {
            num++;
            Long p = points.get(sorted.get(i));
            String user = "nullpointer_catch";
            try {
                user = Objects.requireNonNull(e.getGuild().getMemberById(p)).getUser().getName();
            } catch (NullPointerException e1) {
            }
            if (user != null && !user.equalsIgnoreCase("nullpointer_catch")) {
                s += "\n" + num + ": (" + References.formatInteger(sorted.get(i)) + ")  " + user;
            }
            if (i == 25) {
                break;
            }
        }
        Date date = new Date();
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/YY HH:mm:ss");
        String d = "\n > Last update: " + dateFormat.format(date);
        e.getGuild().getTextChannelById(641041535894552607L).editMessageById(641270194639208508L, "```" + s + "```" + d).complete();
    }
}
