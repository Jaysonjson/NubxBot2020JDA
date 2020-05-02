package jda.jayson.file.user;

import jda.jayson.guilds.nullbloxme.commands.other.inventory.InventoryItem;
import jda.jayson.id.References;

import java.util.ArrayList;
import java.util.HashMap;

public class DiscordUser {
    public Integer points = 0;
    public Integer currency = 0;
    public Integer message_count = 0;
    public Integer model_count = 0;
    public Integer nubox = 3;
    public Integer message_currency_count = 0;
    public Integer message_nubox_count = 0;
    public Integer message_item_count = 0;
    public Integer shop_items_sold = 0;
    public HashMap<InventoryItem, Integer> inventory = new HashMap<>();
    public Integer contests_won = 0;
    public ArrayList<Long> user_creations = new ArrayList<>();
    public HashMap<String, Object> minecraft = new HashMap<>();
    public HashMap<String, Object> discord = new HashMap<>();

    public DiscordUser() {

    }

    public String getDiscordArrayDCName() {
        return discord.get("name").toString();
    }

    public void setDiscordArrayDCName(Object content) {
        discord.put("name", content);
    }
}
