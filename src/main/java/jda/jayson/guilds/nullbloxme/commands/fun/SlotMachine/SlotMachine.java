package jda.jayson.guilds.nullbloxme.commands.fun.SlotMachine;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public enum SlotMachine {
    CUBIK_MESH("<:cubik_mesh:362665552319348737>", 250),
    CUBIK_ELEMENT("<:cubik_element:362665520685907978>", 250),
    CUBIK_VOXEL("<:cubik_voxel:362665588079984640>", 250);
   // CHERRIES("\uD83C\uDF52", 500);

    public String emoji;
    public Integer price;
    SlotMachine(String emoji, Integer price) {
        this.emoji = emoji;
        this.price = price;
    }

    public Integer getPrice() {
        return price;
    }

    public String getEmoji() {
        return emoji;
    }

    public void setEmoji(String emoji) {
        this.emoji = emoji;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }
    public static SlotMachine randomSlot() {
        Random r4 = new Random();
        List<SlotMachine> VALUES = Collections.unmodifiableList(Arrays.asList((SlotMachine.values())));
        SlotMachine slot = VALUES.get(r4.nextInt(SlotMachine.values().length));
        return slot;
    }
}
