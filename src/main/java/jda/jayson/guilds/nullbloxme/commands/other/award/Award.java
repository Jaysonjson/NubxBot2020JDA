package jda.jayson.guilds.nullbloxme.commands.other.award;

import jda.jayson.file.user.DiscordUser;

public enum Award {

    JAYSON_CONTEST("jayson_contest_award",":diamond_shape_with_a_dot_inside: Jayson Contest Award");

    String id;
    String name;
    Award(String id, String name) {
        this.id = id;
        this.name = name;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void addAward(DiscordUser discordUser) {
        AwardUtility.addAward(discordUser, this);
    }

    public void removeAward(DiscordUser discordUser) {
        AwardUtility.removeAward(discordUser, this);
    }
}
