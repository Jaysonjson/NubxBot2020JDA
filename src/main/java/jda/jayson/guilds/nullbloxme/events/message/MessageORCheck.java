package jda.jayson.guilds.nullbloxme.events.message;

import jda.jayson.id.Channels;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class MessageORCheck {
    public static void onEvent(MessageReceivedEvent e) {
        Message msg = e.getMessage();

        if(e.getMessage().getChannel().getIdLong() == Channels.Recruitment) {
            if (!msg.getContentRaw().toLowerCase().contains("deadline") || !msg.getContentRaw().toLowerCase().contains("budget") || !msg.getContentRaw().toLowerCase().contains("contact")) {
                e.getAuthor().openPrivateChannel().complete().sendMessage("> Your Message in " + e.getGuild().getTextChannelById(Channels.Recruitment).getAsMention() + " is missing atleast one requirement. (Budget, Contact, Deadline)").complete();
               // e.getAuthor().openPrivateChannel().complete().sendMessage("\n" + e.getMessage().getContentRaw()).complete();
              //  msg.delete().complete();
            }
        }
        if(e.getMessage().getChannel().getIdLong() == Channels.Offering) {
            if(!msg.getContentRaw().toLowerCase().contains("contact") || !msg.getContentRaw().toLowerCase().contains("cost to hire") || !msg.getContentRaw().toLowerCase().contains("production time") || !msg.getContentRaw().toLowerCase().contains("portfolio")) {
                e.getAuthor().openPrivateChannel().complete().sendMessage("> Your Message in " + e.getGuild().getTextChannelById(Channels.Recruitment).getAsMention() + " is missing atleast one requirement. (Portfolio, Production Time, Cost To Hire, Contact").complete();
               // msg.delete().complete();
              //  e.getAuthor().openPrivateChannel().complete().sendMessage(e.getMessage().getContentRaw()).complete();
            }
        }
        if(e.getChannel() == e.getGuild().getTextChannelById(Channels.Recruitment)) {
            ScheduledExecutorService ses = Executors.newSingleThreadScheduledExecutor();
            ses.scheduleAtFixedRate(new Runnable() {
                @Override
                public void run() {
                   // e.getAuthor().openPrivateChannel().complete().sendMessage("> Your Message in " + e.getGuild().getTextChannelById(e.getChannel().getIdLong()).getAsMention() + " has been removed. \n It was older than 30 Days. You can copy&paste it back!").complete();
                   // e.getAuthor().openPrivateChannel().complete().sendMessage(e.getMessage().getContentRaw()).complete();
                }
            }, 0, 30, TimeUnit.DAYS);
        }
    }
}
