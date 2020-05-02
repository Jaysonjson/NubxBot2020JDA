package jda.jayson.contest;

import jda.jayson.file.JSON;
import jda.jayson.file.user.DiscordUser;
import jda.jayson.id.Channels;
import jda.jayson.id.References;
import jda.jayson.id.Users;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class CommandContestTop {
    public static HashMap<Integer, Long> points = new HashMap<>();
    public static HashMap<Long,Integer> remover = new HashMap<>();
    public static ArrayList<Integer> sorted = new ArrayList<>();
    public static void onEvent(MessageReceivedEvent event) {
        if(event.getMessage().getContentRaw().contains("!contestwinner") && event.getMember().getIdLong() == Users.Jayson_json) {
            DiscordUser discordUser = JSON.loadUser(event.getMessage().getContentRaw().split(" ")[1]);
            discordUser.contests_won++;
            JSON.saveUser(discordUser);
        }
        if(event.getMessage().getContentRaw().equalsIgnoreCase("!contestinit")) {
            try {
                ScheduledExecutorService ses = Executors.newSingleThreadScheduledExecutor();
                ses.scheduleAtFixedRate(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            updateContest(event);
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
        if(event.getMessage().getContentRaw().equalsIgnoreCase("!updatecontest")) {
            updateContest(event);
        }

    }
    public static void updateContest(MessageReceivedEvent e) {
        ContestSave.load();
        sorted.clear();
        points.clear();
        for (Long aLong : ContestSave.contest_plus_count.keySet()) {
            points.put(ContestSave.contest_plus_count.get(aLong), e.getAuthor().getIdLong());
            sorted.add(ContestSave.contest_plus_count.get(aLong));
        }
        Collections.sort(sorted);
        Collections.reverse(sorted);
        String s = "1: (" + sorted.get(0) + ") " + e.getGuild().getMemberById(points.get(sorted.get(0))).getUser().getName();
        int num = 1;
        for (int i = 1; i < sorted.size(); i++) {
            num++;
            Long p = points.get(sorted.get(i));
            String user = "nullpointer_catch";
            try {
                user = Objects.requireNonNull(e.getGuild().getMemberById(p)).getUser().getName();
            } catch (NullPointerException e1) {
            }
            if (!user.equalsIgnoreCase("nullpointer_catch")) {
                s += "\n" + num + ": (" + sorted.get(i) + ")  " + user;
            }
            if (i == 25) {
                break;
            }
        }
        Date date = new Date();
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/YY HH:mm:ss");
        String d = "\n > Last update: " + dateFormat.format(date);
        Objects.requireNonNull(e.getGuild().getTextChannelById(Channels.Contest_Top)).editMessageById(648625711569174528L, "```" + s + "```" + d).complete();
    }
}
