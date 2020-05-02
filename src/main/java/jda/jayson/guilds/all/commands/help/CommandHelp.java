package jda.jayson.guilds.all.commands.help;

import com.jayson.nubx.NubxUtil;

import jda.jayson.ArrayCommand;
import jda.jayson.Nubx;
import jda.jayson.id.ID;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDAInfo;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;


import java.awt.*;
import java.io.File;
import java.util.ArrayList;

public class CommandHelp {
    private static Color purple = new Color(69, 65, 94);
    public static void onEvent(MessageReceivedEvent event) {
        Message msg = event.getMessage();
        String content = msg.getContentRaw();
        switch (content.toLowerCase()) {
            case "!help nubox": onDefaultHelp(event,ArrayCommand.nubox,"\uD83D\uDCE6 Nubox");
            break;
            case "!help currency": onDefaultHelp(event,ArrayCommand.currency,"<:nubx:363393495186145280> Currency");
            break;
            case "!help minecraft": onDefaultHelp(event,ArrayCommand.minecraft,"<:minecraft:643808914575458376> Minecraft");
            break;
            case "!help utility": onDefaultHelp(event,ArrayCommand.utility,"⚙ Utility");
            break;
            case "!help staff": onDefaultHelp(event,ArrayCommand.staff, "⭐ Staff");
            break;
            case "!help skateboard": onDefaultHelp(event,ArrayCommand.skateboard, "\uD83D\uDEF9 Skateboard");
            break;
            case "!help fun": onDefaultHelp(event,ArrayCommand.fun,"\uD83E\uDD21 Fun");
            break;
            case "!help file": onDefaultHelp(event,ArrayCommand.file,"\uD83D\uDCBE File Management");
            event.getChannel().sendMessage("> Alternate (Terminal): \n> `cd Desktop/NubxBot` \n> `tree` \n> `nano (file)`").complete();
            break;
            case "!help shop": onShopHelp(event);
            break;
            case "!shop zip example": event.getChannel().sendFile(new File("bot/shop_example_zip.zip")).complete();
            break;
            case "!help bot": onBotHelp(event);
            break;
            case "!help contest": onContestHelp(event);
            break;
            case "!help inventory": onDefaultHelp(event,ArrayCommand.inventory, "\uD83D\uDCBC Inventory");
            break;
            case "!help rm": onRMHelp(event);
            break;
            case "!help portfolio": onPortfolioHelp(event);
            break;
            case "!help": onDefault(event);
            break;
        }
    }
    private static void onDefault(MessageReceivedEvent event) {
        EmbedBuilder builder = new EmbedBuilder();
        builder.setTitle("Help - " + ID.version);
        builder.addField("<:nubx:363393495186145280> Currency","`!help currency`\nGuild(s): all",true);
        builder.addField("\uD83D\uDCE6 Nubox","`!help nubox`\nGuild(s): all",true);
        builder.addField("<:minecraft:643808914575458376> Minecraft", "`!help minecraft`\nGuild(s): all",true);
        builder.addField("⚙ Utility", "`!help utility`\nGuild(s): all",true);
        builder.addField("⭐ Staff", "`!help staff`\nGuild(s): all",true);
        builder.addField("\uD83E\uDD21 Fun", "`!help fun`\nGuild(s): all",true);
        builder.addField("\uD83D\uDEF9 Skateboard", "`!help skateboard`\nGuild(s): nubx3d",true);
        builder.addField("\uD83D\uDED2 Shop", "`!help shop`\nGuild(s): Nullblox.me",true);
        builder.addField("\uD83E\uDD47 Contest","`!help contest`\nGuild(s): Nullblox.me", true);
        builder.addField("\uD83E\uDD16 Bot Informations","`!help bot`\nGuild(s): all", true);
        builder.addField("\uD83D\uDCBE File Management","`!help file`\nGuild(s): all", true);
        builder.addField("\uD83D\uDCBC Inventory","`!help inventory`\nGuild(s): all", true);
        builder.addField("\uD83D\uDD22 Random Message","`!help rm`\nGuild(s): Nullblox.me", true);
        builder.addField("\uD83C\uDF10 Portfolio", "`!help portfolio`\nGuild(s): all",true);
        builder.addBlankField(true);
        builder.setColor(purple);
        event.getChannel().sendMessage(builder.build()).complete();
    }
    private static void onDefaultHelp(MessageReceivedEvent event, ArrayList array, String name) {
        EmbedBuilder builder = new EmbedBuilder();
        String content = "`";
        Integer i = 0;
        for (Object o : array) {
            i++;
            if(i != array.size()) {
                content += o.toString() + "`, `";
            } else {
                content += o.toString() + "`";
            }
        }
        builder.addField(name, content,true);
        builder.setColor(purple);
        event.getChannel().sendMessage(builder.build()).complete();
    }

    private static void onShopHelp(MessageReceivedEvent event) {
        EmbedBuilder builder = new EmbedBuilder();
        builder.addField("General", "A shop item can be added via: `!shop add (id) (price) (sketchfab link)`. This command only works in a Private Channel (Private Message) with the Bot. **You must attach a ZIP file!**",true);
        builder.addField("ZIP Content","The zip must contain: `preview.png`. That's the image which will be shown as a thumbnail. You can also add own Fields by creation a json file with the name: `additional.json`. Example: ```JSON\n { \"Field Name\": \"Field Content\", \"Amount\": 0 } ```Empty Field Names will be ignored!",true);
        builder.addField("Buying","You can buy an Shop Item by doing: `!shop buy (item id)` inside " + event.getGuild().getTextChannelById(642079571193233431L).getAsMention() + ". The bot will send you a PM with the ZIP file",true);
        builder.appendDescription("https://streamable.com/cnwzb");
        //builder.addField("ZIP Example", "!shop zip example - will send a zip example",true);
        builder.setFooter("!shop zip example - will send a zip example");
        builder.setColor(purple);
        event.getChannel().sendMessage(builder.build()).complete();
    }
    private static void onPortfolioHelp(MessageReceivedEvent event) {
        EmbedBuilder builder = new EmbedBuilder();
        builder.addField("General", "You can get a users portfolio by doing: `!portfolio (user)`. `!portfolio list` will list all portfolios. You can create a portfolio by doing `!portfolio create`. **You must attach a TOML file!**",false);
        builder.addField("TOML Content (Unordered)","Example: ```TOML\nthumbnail = \"%AVATAR%\" #Thumbnail - Can be an URL. %AVATAR% is the users avatar\n" +
                "color = \"53 125 45\" #Color in RGB \n" +
                "image = \"%AVATAR\" #Thumbnail - Can be an URL.\n" +
                "footer = \"Footer Text\" #Footer\n" +
                "footer-icon = \"%AVATAR%\" #Footer Icon - Can be an URL.\n" +
                "description = \"Description Text\" #Description\n" +
                "author-url = \"http://www.example.com\" #Author Url - Can also use %AVATAR%. URL must include http://\n" +
                "author = \"Jayson\" #Author Name\n" +
                "author-icon = \"%AVATAR%\" #Author Icon - Can be an URL.\n" +
                "[fields] #Fields - Unordered. Means Fields are put in randomly\n" +
                "    Name = \"Jayson\"\n" +
                "    \"Age (2019)\" = 15 #Fields with spaces, special chars, etc. needs to be \"quoted\"```Empty Field Names will be ignored!",false);
        builder.addField("TOML Content (Ordered)", "```TOML\n#same as unordered, things like \"thumbnail\" can be used\n[[fields]]\n" +
                " \"Name\" = \"Jayson\"\n" +
                "[[fields]]\n" +
                " \"Age\" = 15\n" +
                " [[fields.blank]] #creates a blank between \"Name\" and \"Age\"\n" +
                "[[fields]]\n" +
                " \"Country\" = \"Germany :flag_de:\"\n" +
                "[[fields]]\n" +
                " \"Hobby\" = \"Programming, Modelling, driving Motorbike\"```", false);
        builder.setColor(purple);
        builder.setImage("https://i.imgur.com/pzCUg5s.png");
        event.getChannel().sendMessage(builder.build()).complete();
    }
    private static void onRMHelp(MessageReceivedEvent event) {
        EmbedBuilder builder = new EmbedBuilder();
        builder.addField("General", "Random Lines from an editable text line",true);
        builder.addField("Adding a line","!rndmsg add (content)",true);
        builder.addField("IDs","You can create special things such like user mentions using them: `%AUTHOR%` - Name of the Author, `%AUTHOR_MENTION%` - Mention, `%AUTHOR_ID%` - Long ID, `%AUTHOR_AVATAR_URL%` - Avatar URL, `%LINE%` - New Line, `%AUTHOR_CREATIONDATE%` - Creation date of Author, `%MESSAGE_CONTENT%` - Content of sent Message",true);
        builder.setColor(purple);
        event.getChannel().sendMessage(builder.build()).complete();
    }
    private static void onBotHelp(MessageReceivedEvent event) {
        EmbedBuilder builder = new EmbedBuilder();
        builder.setAuthor(event.getJDA().getSelfUser().getName(),event.getJDA().getSelfUser().getAvatarUrl(),event.getJDA().getSelfUser().getAvatarUrl());
        builder.setThumbnail(event.getJDA().getSelfUser().getAvatarUrl());
        builder.addField("Bot Version", "`" + ID.version + "`",true);
        builder.addField("NubxUtil Version", "`" + NubxUtil.version + "`",true);
        builder.addField("JDA Version", "`" + JDAInfo.VERSION + "`",true);
        builder.addField("Guilds", "`Total: " + event.getJDA().getGuilds().size() + "`",true);
        builder.addField("Activity", "`" + Nubx.activity + "`",true);
        builder.addBlankField(true);
        builder.setColor(purple);
        event.getChannel().sendMessage(builder.build()).complete();
    }
    private static void onContestHelp(MessageReceivedEvent event) {
        EmbedBuilder builder = new EmbedBuilder();
        builder.addField("General", "You can participate by using the command : !contest (title) (img_attachment). You need to do this within a Bot PM.",true);
        builder.addField("Voting", "1+ Brings 1 Point, 1- Removes 1 Point. Participants can't vote",true);
        //builder.setImage("https://imgur.com/snpojK0");
        //builder.setThumbnail("https://imgur.com/undefined");
        builder.appendDescription("https://streamable.com/q7mfn");
        builder.setColor(purple);
        event.getChannel().sendMessage(builder.build()).complete();
    }
}
