package jda.jayson;

import jda.jayson.id.ID;

import java.util.ArrayList;

public class ArrayCommand {
    public static ArrayList<String> currency = new ArrayList<String>();
    public static ArrayList<String> nubox = new ArrayList<String>();
    public static ArrayList<String> minecraft = new ArrayList<>();
    public static ArrayList<String> utility = new ArrayList<>();
    public static ArrayList<String> staff = new ArrayList<String>();
    public static ArrayList<String> skateboard = new ArrayList<String>();
    public static ArrayList<String> fun = new ArrayList<String>();
    public static ArrayList<String> file = new ArrayList<String>();
    public static ArrayList<String> inventory = new ArrayList<String>();

    public static void register() {
        ArrayCommand.currency.add("!" + ID.currency.toLowerCase() + " give (user) (amount)");

        ArrayCommand.nubox.add("!nubox open");
        ArrayCommand.nubox.add("!nubox give (user) (amount)");

        ArrayCommand.minecraft.add("!minecraft link (minecraft username)");
        ArrayCommand.minecraft.add("!mc (minecraft username)");

        ArrayCommand.utility.add("!me");
        ArrayCommand.utility.add("!me (user)");
        ArrayCommand.utility.add("!rndmsg (content)");
        ArrayCommand.utility.add("!creation (number)");
        ArrayCommand.utility.add("!creation player (user)");
        ArrayCommand.utility.add("!inventory");
        ArrayCommand.utility.add("!inventory (page)");
        ArrayCommand.utility.add("!permission");

        ArrayCommand.staff.add("!cc (input) (output)");
        ArrayCommand.staff.add("!cc list all");
        ArrayCommand.staff.add("!log write");
        ArrayCommand.staff.add("!log list");
        ArrayCommand.staff.add("!log send (log name)");
        ArrayCommand.staff.add("!admin name (name)");
        ArrayCommand.staff.add("!admin color (Red Color) (Green Color) (Blue Color)");
        ArrayCommand.staff.add("!highscoreinit_new");
        ArrayCommand.staff.add("!updatehighscore_new");
        ArrayCommand.staff.add("!DOLLARTOEURO");
        ArrayCommand.staff.add("!" + ID.currency.toLowerCase() + " set (user) (amount))");
        ArrayCommand.staff.add("!award add (user) (award id)");
        ArrayCommand.fun.add("!tictactoe bot");
        ArrayCommand.fun.add("!tictactoe (player)");
        ArrayCommand.fun.add("@someone");
        ArrayCommand.fun.add("!fight (player)");
        ArrayCommand.fun.add("!slotmachine");

        ArrayCommand.skateboard.add("!skateboard (id)");
        ArrayCommand.skateboard.add("!skateboard add (id)");
        ArrayCommand.skateboard.add("!skateboard installations (installation)");
        ArrayCommand.skateboard.add("!skateboard embedded color preview");
        ArrayCommand.skateboard.add("!skateboard type (type)");
        ArrayCommand.skateboard.add("!skateboard picture (attached picture file / url link)");
        ArrayCommand.skateboard.add("!skateboard embedded color (Red Color) (Green Color) (Blue Color)");

        ArrayCommand.file.add("!currency set (user) (amount)");
        ArrayCommand.file.add("!json keys (user)");
        ArrayCommand.file.add("!json edit (user) (key) (content)");

        ArrayCommand.inventory.add("!inventory");
        ArrayCommand.inventory.add("!inventory (page)");
        ArrayCommand.inventory.add("!item sell (item)");
        ArrayCommand.inventory.add("!item buy (item)");
        ArrayCommand.inventory.add("!items");

    }
}
