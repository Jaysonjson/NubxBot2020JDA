package jda.jayson.guilds.nullbloxme.commands.other.award;

import jda.jayson.file.user.DiscordUser;

public class AwardUtility {

    public static Award getAward(String id) {
        for (Award value : Award.values()) {
            if(value.getId().equalsIgnoreCase(id)) {
                return value;
            }
        }
        return null;
    }

    public static void addAward(DiscordUser discordUser, Award award) {
        discordUser.awards.add(award);
    }

    public static void removeAward(DiscordUser discordUser, Award award) {
        discordUser.awards.add(award);
    }
}
