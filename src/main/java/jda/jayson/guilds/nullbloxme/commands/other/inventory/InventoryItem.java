package jda.jayson.guilds.nullbloxme.commands.other.inventory;

import jda.jayson.id.References;

public enum InventoryItem {
    BONE("bone", "Bone", "\uD83E\uDDB4", 93, 9),
    SNOWFLAKE("snowflake", "Snowflake", "❄️", 4, 11),
    FIRECRACKER("firecracker", "Firecracker", "\uD83E\uDDE8", 120, 4),
    CHAINS("chains", "Chains", "⛓️",453, 7),
    BASEBALL("baseball", "Baseball", "⚾",724,6),
    BASKETBALL("basketball", "Basketball", "\uD83C\uDFC0", 724,6),
    VOLLEYBALL("volleyball", "Volleyball", "\uD83C\uDFD0", 724,6),
    STOPWATCH("stopwatch", "Stopwatch", "⏱️", 1263, 12),
    YOYO("yoyo", "Yo-Yo", "\uD83E\uDE80", 500, 16),
    ATOM("atom", "Atom", "<:atom:647841723065565214>", 1267,9),
    EIGHTBALL("eightball", "8Ball", "\uD83C\uDFB1", 152, 4),
    EGG("egg", "Egg", "\uD83E\uDD5A", 5, 12),
    SALT("salt", "Salt", "\uD83E\uDDC2", 115, 6),
    DICE("dice", "Dice", "\uD83C\uDFB2", 120, 5),
    MICROBE("microbe", "Microbe", "\uD83E\uDDA0", 3523, 11),
    COFFIN("coffin", "Coffin", "⚰️", 11250, 15),
    THERMOMETER("thermometer", "Thermometer", "\uD83C\uDF21️", 500, 7),
    BOX("box", "Box", "\uD83D\uDCE6", 325, 5),
    VHS("vhs","VHS", "\uD83D\uDCFC", 400, 4),
    FILM_FRAME("film_frame", "Film Frame", "\uD83C\uDF9E️", 600,6),
    COMPASS("compass", "Compass", "\uD83E\uDDED", 170,5),
    ROLL_OF_PAPER("roll_of_paper", "Roll of Paper", "\uD83E\uDDFB", 60, 3),
    TICKET("ticket", "Ticket", "\uD83C\uDFAB",729, 4),
    ANTIQUE_KEY("antique_key", "Antique Key", "\uD83D\uDDDD️",689, 4),
    KEY("key","Key", "\uD83D\uDD11", 325, 4),
    MAGNET("magnet", "Magnet", "\uD83E\uDDF2", 400, 4),
    TOOLBOX("toolbox", "Toolbox", "\uD83E\uDDF0", 500, 4),
    LABEL("label", "Label", "\uD83C\uDFF7️", 150, 4),
    GEM("gem","Gem", "\uD83D\uDC8E️", 6300, 12),
    OIL("oil","Oil", "\uD83D\uDEE2️", 800, 5),
    NUT_AND_BOLT("nut_and_bolt", "Nut'n'Bold", "\uD83D\uDD29", 56,3),
    DNA("dna", "DNA", "\uD83E\uDDEC", 7000, 12),
    HAMMER("hammer", "Hammer", "\uD83D\uDD28", 370, 4),
    WRENCH("wrench", "Wrench", "\uD83D\uDD27", 370, 4),
    BATTERY("battery", "Battery", "\uD83D\uDD0B", 60, 2),
    PAPER_CLIP("paper_clip", "Paper Clip", "\uD83D\uDCCE", 60, 2),
    BRICK("brick", "Brick", "\uD83E\uDDF1", 250, 5),
    DVD("dvd", "DVD", "\uD83D\uDCC0️", 324, 4),
    CD("cd", "CD", "\uD83D\uDCBF️", 350, 4),
    GEAR("gear", "Gear", "⚙️", 146, 3),
    FLOPPY_DISK("floppy_disk", "Floppy Disk", "\uD83D\uDCBE", 400, 4),
    LAPTOP("laptop","Laptop", "<:pc:410279583573606400>",2000,7),
    WUMPUS("wumpus", "Wumpus", "<:wumpus:414567915065704458>",7000, 12),
    AZTEC_CROSSBOW("aztec_crossbow", "Aztec Crossbow", "<:aztec_crossbow:410625791806799874>", 2250, 12),
    PLASMA_CASTER("plasma_caster", "Plasma Caster","<:plasma_caster:410626862629388288>",2250,11),
    GREEDO("greedo", "Greedo","<:greedo:409169547329798154>",3250,10),
    PILL("pill","Pill","\uD83D\uDC8A",90,2),
    COOKIE("cookie","Cookie","\uD83C\uDF6A",5,1);

    String name;
    String id;
    Integer price;
    Integer rarity;
    String emoji;
    InventoryItem(String id, String name, String emoji,Integer price, Integer rarity) {
        this.name = name;
        this.id = id;
        this.price = price;
        this.rarity = rarity;
        this.emoji = emoji;
    }

    public String getName() {
        return this.name;
    }
    public Integer getPrice() {
        return this.price;
    }
    public String getPriceAsFormattedString() {
        return References.formatInteger(this.price);
    }
    public String getSellValueAsFormattedString() { return References.formatInteger(this.getSellValue()); }
    public Integer getRarity() {
        return this.rarity;
    }
    public String getEmoji() {
        return this.emoji;
    }
    public String getId() {
        return this.id;
    }
    public Integer getSellValue() {
        return (int) Math.round(this.price / 1.8);
    }
    public Integer getTransferValue() { return (int) Math.round(this.getSellValue() / 2.59); }
}
