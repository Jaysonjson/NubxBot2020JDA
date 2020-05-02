package jda.jayson.guilds.nullbloxme.commands.fun.SlotMachine;

import com.jayson.nubx.ANSI;
import com.jayson.nubx.NubxUtil;
import jda.jayson.command.Cooldown;
import jda.jayson.file.JSON;
import jda.jayson.id.ID;
import jda.jayson.id.References;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class CommandSlotMachine {
    private static Cooldown cooldown = new Cooldown(45);

    public static void onEvent(MessageReceivedEvent event) {
        Message msg = event.getMessage();
        String content = msg.getContentRaw();
        if (content.equalsIgnoreCase("!slotmachine")) {
            cooldown.addUser(event.getAuthor());
            if (cooldown.cooldowned()) {
                event.getChannel().sendMessage("> You're cooldowned! " + References.formatTime(cooldown.getRemainingTime())).complete();
            } else {
                cooldown.start();
                JSON.load(event.getAuthor().getId());
                if (References.currency > 99) {
                    final SlotMachine[] slot0 = {SlotMachine.randomSlot()};
                    final SlotMachine[] slot1 = {SlotMachine.randomSlot()};
                    final SlotMachine[] slot2 = {SlotMachine.randomSlot()};
                    Message slot_message = event.getChannel().sendMessage(slot0[0].getEmoji() + " **|** " + slot1[0].getEmoji() + " **|** " + slot2[0].getEmoji() + "\n" + getProgress(0)).complete();
                    final Integer[] i = {0};
                    ScheduledExecutorService ses = Executors.newSingleThreadScheduledExecutor();
                    ses.scheduleAtFixedRate(() -> {
                        i[0]++;
                        slot0[0] = SlotMachine.randomSlot();
                        slot1[0] = SlotMachine.randomSlot();
                        slot2[0] = SlotMachine.randomSlot();
                        slot_message.editMessage(slot0[0].getEmoji() + " **|** " + slot1[0].getEmoji() + " **|** " + slot2[0].getEmoji() + "\n" + getProgress(i[0])).complete();
                        if (i[0] > 7) {
                            if (slot0[0].equals(slot1[0]) && slot1[0].equals(slot2[0])) {
                                event.getChannel().sendMessage("> You won " + slot0[0].getPrice() + ID.currency + "!").complete();
                                References.currency += slot0[0].getPrice();
                            } else {
                                event.getChannel().sendMessage("> You lost 100 " + ID.currency + "!").complete();
                                References.currency -= 100;
                            }
                            ses.shutdown();
                        }
                    }, 0,1, TimeUnit.SECONDS);
                } else {
                    event.getChannel().sendMessage("> You don't have enough " + ID.currency + " to use the Slot Machine!").complete();
                    return;
                }
                JSON.save(event.getAuthor().getId());
            }
        }
    }
    private static String getProgress(Integer integer) {
        StringBuilder str = new StringBuilder("[");
        int remaining = 0;
        int percentage = (integer * 100) / 8;
        for (int i = 0; i < integer; i++) {
            remaining++;
            if(i == (8 / 2)) {
                str.append(percentage + "").append("%");
            }
            str.append("=");
        }
        for (int i = remaining; i < 8; i++) {
            if(i == (8 / 2)) {
                str.append(percentage + "").append("%");
            }
            str.append("-");
        }
        str.append("]");
        return str.toString();
    }
}
