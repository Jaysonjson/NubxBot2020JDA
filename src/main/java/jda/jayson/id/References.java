package jda.jayson.id;

import jda.jayson.guilds.nullbloxme.commands.other.inventory.InventoryItem;

import java.util.*;
import java.util.Arrays;

public class References {
    public static HashMap<Integer, Long> creation_player = new HashMap<>();
    public static HashMap<Integer, String> creation_player_url = new HashMap<>();
    public static int creationcount_showcase = 0;
    public static ArrayList<Long> dollar_to_euro_messages = new ArrayList<>();

    public static String skateboard_type = "";
    public static Integer skateboard_num1 = 0;
    public static Integer skateboard_num2 = 0;
    public static Integer skateboard_num3 = 0;
    public static String skateboard_color_url = "";
    public static Long skateboard_owner = 0L;
    public static String skateboard_picture = "";

    public static Boolean skateboard_snub = false;
    public static Boolean skateboard_med_kit = false;
    public static Boolean skateboard_light_kit = false;
    public static Boolean skateboard_shield = false;
    public static Boolean skateboard_katstop = false;
    public static String skin_url_sized = "";

    public static Integer shop_price = 0;
    public static Long shop_owner = 0L;
    public static Integer shop_download_amount = 0;
    public static Integer shop_total_nubx_earned = 0;

    public static HashMap<InventoryItem, Integer> inventory = new HashMap<>();
    public static ArrayList<Long> user_creations = new ArrayList<>();

    public static void addInventoryItem(InventoryItem item, Integer amount) {
        if(inventory.containsKey(item)) {
            inventory.put(item,inventory.get(item) + amount);
        } else {
            inventory.put(item,amount);
        }
    }
    public static void removeInventoryItem(InventoryItem item, Integer amount) {
        if(inventory.containsKey(item)) {
            inventory.put(item,inventory.get(item) - amount);
            if(InventoryItemAmount(item) == 0) {
                inventory.remove(item);
            }
        }
    }
    public static Integer InventoryItemAmount(InventoryItem item) {
        if(inventory.containsKey(item)) {
            return inventory.get(item);
        }
        return 0;
    }
    public static InventoryItem randomInventoryItem() {
        Random r4 = new Random();
       List<InventoryItem> VALUES = Collections.unmodifiableList(Arrays.asList((InventoryItem.values())));
        InventoryItem item = VALUES.get(r4.nextInt(InventoryItem.values().length));
        if(item.getRarity() > 5) {
         //   return randomInventoryItem();
            for (InventoryItem value : InventoryItem.values()) {
                if (r4.nextInt((int) Math.round(value.getRarity() / 1.01)) == 1) {
                    if(r4.nextInt(1) == 1) {
                        item = value;
                        return item;
                    } else {
                        //randomInventoryItem();
                    }
                }
            }
        }
        return item;
    }

    public static String formatInteger(Integer integer) {
        String string = String.valueOf(integer);
        if(integer > 1000) string = String.format("%.2fk", integer / 1000.0);
        if(integer > 1000000) string = String.format("%.2fM", integer / 1000000.0);
        if(integer > 1000000000) string = String.format("%.2fG", integer / 1000000000.0);
        return string;
    }
    public static String formatTime(Integer integer) {
        String string = integer + "s";
        if(integer > 60) string = String.format("%.1fm", (integer / 60.0) % 60);
        return string;
    }
}
