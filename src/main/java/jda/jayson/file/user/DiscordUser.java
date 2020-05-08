package jda.jayson.file.user;

import com.google.gson.annotations.SerializedName;
import jda.jayson.file.user.arrays.DiscordUserDiscordArray;
import jda.jayson.file.user.arrays.DiscordUserMinecraftArray;
import jda.jayson.guilds.nullbloxme.commands.other.award.Award;
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
    @SerializedName(value = "message_currency_count", alternate = "message_currency")
    public Integer message_currency_count = 0;
    @SerializedName(value = "message_nubox_count", alternate = "message_nubox")
    public Integer message_nubox_count = 0;
    @SerializedName(value = "message_item_count", alternate = "message_item")
    public Integer message_item_count = 0;
    public Integer shop_items_sold = 0;
    public HashMap<InventoryItem, Integer> inventory = new HashMap<>();
    public Integer contests_won = 0;
    public ArrayList<Long> user_creations = new ArrayList<>();
    public DiscordUserDiscordArray discord = new DiscordUserDiscordArray();
    public DiscordUserMinecraftArray minecraft = new DiscordUserMinecraftArray();
    public ArrayList<Award> awards = new ArrayList<>();
    private transient Long longID = 0L;

    public Long getLongID() {
        return this.longID;
    }

    public void setLongID(Long longID) {
        this.longID = longID;
    }

    public DiscordUser() {

    }
}
