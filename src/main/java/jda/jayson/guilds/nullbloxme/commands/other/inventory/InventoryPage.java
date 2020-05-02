package jda.jayson.guilds.nullbloxme.commands.other.inventory;


public class InventoryPage {
    String content;
    Integer index;
    InventoryPage(String content, Integer index) {
        this.content = content;
        this.index = index;
    }
    public String getContent() {
        return content;
    }
    Integer getIndex() {
        return index;
    }
}
