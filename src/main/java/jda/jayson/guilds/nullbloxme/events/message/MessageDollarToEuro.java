package jda.jayson.guilds.nullbloxme.events.message;

import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class MessageDollarToEuro {

    public static void onEvent(MessageReceivedEvent e) throws Exception {
        if (e.getMessage().getContentRaw().equalsIgnoreCase("!DOLLARTOEURO")) {
            DateFormat dateFormat = new SimpleDateFormat("dd/MM/YY HH:mm:ss");
            Date date = new Date();
            Message msg = e.getChannel().sendMessage("`$7.25 = " + DATA() + "€" + "\nDifference: " + DATA_WEAK() + "`\nInfos taken from http://liveusd.com/EUR/7.25" + "\nLast Updated: "  + dateFormat.format(date)).complete();
            ScheduledExecutorService ses = Executors.newSingleThreadScheduledExecutor();
            ses.scheduleAtFixedRate(new Runnable() {
                @Override
                public void run() {
                    try {
                        DateFormat dateFormat = new SimpleDateFormat("dd/MM/YY HH:mm:ss");
                        Date date = new Date();
                        String m = "`$7.25 = " + DATA() + "€" + "\nDifference: " + DATA_WEAK() + "`\nInfos taken from http://liveusd.com/EUR/7.25" + "\nLast Updated: "  + dateFormat.format(date);
                        try {
                            msg.editMessage(m).complete();
                        } catch (Exception exc){
                            ses.shutdown();
                        }
                    } catch (Exception e1) {
                        e1.printStackTrace();
                    }
                }
            }, 0, 2, TimeUnit.HOURS);
        }
    }

    private static String DATA_GOOGLE() throws Exception {
        URL url = new URL("https://www.google.com/search?ei=KxhBXajbDuyFggeSkbiICQ&q=7.25+dollar+in+euro&oq=7.25+dollar+in+euro&gs_l=psy-ab.3..0i8i30.1974.1974..2137...0.0..0.70.70.1......0....1..gws-wiz.........12%3A0j13%3A0.rqc-kyixGJI&ved=0ahUKEwjonKeYqN7jAhXsguAKHZIIDpEQ4dUDCAo&uact=5");
        HttpURLConnection httpCon = (HttpURLConnection) url.openConnection();
        httpCon.addRequestProperty("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_12_2) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/55.0.2883.95 Safari/537.36");
        Scanner s = new Scanner(httpCon.getInputStream(), "UTF-8");

        String  str = s.nextLine();
        System.out.println(str);

        if (str.contains("DFlfde SwHCTb")) {
            str = str.substring(str.indexOf("DFlfde SwHCTb") + 58, str.indexOf("<span class=\"MWvIVe\"") - 8);
        } else {
        }
        return str;
    }
    private static String DATA() throws Exception {
        URL url = new URL("http://liveusd.com/EUR/7.25");
        HttpURLConnection httpCon = (HttpURLConnection) url.openConnection();
        httpCon.addRequestProperty("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_12_2) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/55.0.2883.95 Safari/537.36");
        Scanner s = new Scanner(httpCon.getInputStream(), "UTF-8");
        String str = "0";
        while (s.hasNext()) {
            str = s.nextLine();

            if (str.contains("<span id=\"7.25USDEUR\"><b id=\"price\">")) {
                str = str.substring(str.indexOf("<span id=\"7.25USDEUR\"><b id=\"price\">") + 36, str.indexOf("</b></span> Euro</a>"));
                break;
            }
        }
        return str;
    }
    private static String DATA_WEAK() throws Exception {
        URL url = new URL("http://liveusd.com/EUR/7.25");
        HttpURLConnection httpCon = (HttpURLConnection) url.openConnection();
        httpCon.addRequestProperty("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_12_2) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/55.0.2883.95 Safari/537.36");
        Scanner s = new Scanner(httpCon.getInputStream(), "UTF-8");
        String str = "0";
        while (s.hasNext()) {
            str = s.nextLine();
            if (str.contains("<td class=\"weak\">")) {
                str = str.substring(str.indexOf("<td class=\"weak\">") + 17, str.indexOf("</td></tr>"));
                break;
            }
        }
        return str;
    }
}
