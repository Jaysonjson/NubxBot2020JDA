package jda.jayson;

import com.jayson.nubx.*;
import jda.jayson.contest.CommandContestEntry;
import jda.jayson.contest.CommandContestTop;
import jda.jayson.contest.ContestReaction;
import jda.jayson.contest.ContestSave;
import jda.jayson.file.BotCMD;
import jda.jayson.guilds.all.commands.currency.CommandGiveCurrency;
import jda.jayson.guilds.all.commands.filemanagement.CommandEditJson;
import jda.jayson.guilds.all.commands.filemanagement.CommandSetCurrency;
import jda.jayson.guilds.all.commands.help.CommandHelp;
import jda.jayson.guilds.all.commands.nubox.CommandGiveNubox;
import jda.jayson.guilds.all.commands.nubox.CommandOpenNubox;
import jda.jayson.guilds.all.commands.other.CommandPortfolio;
import jda.jayson.guilds.all.commands.utility.*;
import jda.jayson.guilds.nubx3d.commands.CommandSkateBoard;
import jda.jayson.guilds.nullbloxme.commands.fun.SlotMachine.CommandSlotMachine;
import jda.jayson.guilds.nullbloxme.commands.other.CommandRole;
import jda.jayson.guilds.nullbloxme.commands.other.inventory.*;
import jda.jayson.guilds.nullbloxme.commands.fun.Fight.CommandFight;
import jda.jayson.guilds.nullbloxme.commands.fun.TicTacToe.CommandTicTacToe;
import jda.jayson.guilds.all.commands.minecraft.*;
import jda.jayson.guilds.all.commands.other.CommandAddOwn;
import jda.jayson.guilds.nullbloxme.commands.fun.TicTacToeMP.CommandTicTacToeMP;
import jda.jayson.guilds.nullbloxme.commands.other.CommandRoles;
import jda.jayson.guilds.nullbloxme.commands.shop.CommandAddShopItem;
import jda.jayson.guilds.nullbloxme.commands.shop.CommandBuyShopItem;
import jda.jayson.guilds.nullbloxme.events.EventMessage;
import jda.jayson.guilds.all.events.message.MessageCurrency;
import jda.jayson.guilds.nullbloxme.events.message.MessageDollarToEuro;
import jda.jayson.guilds.nullbloxme.events.message.MessageORCheck;
import jda.jayson.guilds.nullbloxme.events.message.MessageRandom;
import jda.jayson.guilds.nullbloxme.events.other.*;
import jda.jayson.guilds.nullbloxme.events.reaction.ReactionArist;
import jda.jayson.guilds.nullbloxme.events.reaction.ReactionOR;
import jda.jayson.guilds.nullbloxme.events.reaction.ReactionUser;
import jda.jayson.id.Guilds;
import jda.jayson.id.ID;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.GenericEvent;
import net.dv8tion.jda.api.events.ReadyEvent;
import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.api.events.guild.member.GuildMemberLeaveEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.events.message.priv.PrivateMessageReceivedEvent;
import net.dv8tion.jda.api.events.message.react.MessageReactionAddEvent;
import net.dv8tion.jda.api.events.message.react.MessageReactionRemoveEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.apache.commons.io.FileUtils;
import org.jetbrains.annotations.NotNull;
import javax.security.auth.login.LoginException;
import java.io.File;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Nubx extends ListenerAdapter
{
    public static Logger logger_json = new Logger(ANSI.BOLD + ANSI.GREEN + "JSON" + ANSI.RESET, Arrays.logger);
    public static Logger logger = new Logger(ANSI.BOLD + ANSI.CYAN + "NUBX" + ANSI.RESET, Arrays.logger);
    public static Logger logger_message = new Logger(ANSI.BOLD + ANSI.RED + "MESSAGE" + ANSI.RESET, Arrays.logger);
    public static String activity = "!help | " + ID.version + "";

    public static void main(String[] args) throws LoginException
    {
        ArrayCommand.register();
        BotCMD.load();
        if(!new File("bot/shop_example_zip.zip").exists()) {
            NubxURL.create("https://www.dropbox.com/s/7crmvxxl8xqwf9y/shop_example_zip.zip?dl=1").downloadToFile("bot/shop_example_zip.zip");
        }
        if(!new File("bot/contest/contest.json").exists()) {
            ContestSave.save();
        }
        new JDABuilder(ID.token)
                .addEventListeners(new Nubx())
                .setAutoReconnect(true)
                .setActivity(Activity.playing(activity))
                .build();
            new File("bot/logs/").mkdirs();
        ScheduledExecutorService ses = Executors.newSingleThreadScheduledExecutor();
            ses.scheduleAtFixedRate(() -> {
                if (logger.getSize() >= 4) {
                    NubxUtil.writeLog(new File("bot/logs/" + NubxUtil.getCurrentDate("MM_dd_HH_mm_ss") + "_nubxutil"),"");
                    logger.write(new File("bot/logs/" + NubxUtil.getCurrentDate("MM_dd_HH_mm_ss") + "_log"), "");
                }
            }, 0, 24, TimeUnit.HOURS);
            Runtime.getRuntime().addShutdownHook(new Thread());
    }

    @Override
    public void onGenericEvent(GenericEvent event)
    {
        if(event instanceof ReadyEvent) {
            logger.print("API is ready!");
            StringBuilder guilds = new StringBuilder("> ");
            for (Guild guild : event.getJDA().getGuildCache()) {
                guilds.append(guild.getName()).append(" [`").append(guild.getIdLong()).append("`] \n> ");
               // guilds += "> ";
                if(guild.getRolesByName(ID.bot_role_name, true).size() < 1) {
                    ID.bot_role = guild.createRole().setName(ID.bot_role_name).complete();
                } else {
                    ID.bot_role = guild.getRolesByName(ID.bot_role_name, true).get(0);
                }
            }
            Objects.requireNonNull(Objects.requireNonNull(event.getJDA().getGuildCache().getElementById(Guilds.Nullbloxme)).getTextChannelById(460468603402321923L)).editMessageById(653063147879530517L,"Bot serving on " + ((ReadyEvent) event).getGuildTotalCount() + " Guilds! " + "\n" + guilds + "Running on NubxUtil " + NubxUtil.version + "\n> " + NubxUtil.getCurrentDate("dd.MM.YY HH:mm:ss")).complete();
        }
    }

    @Override
    public void onMessageReactionAdd(MessageReactionAddEvent event) {
        if(event.getUser().isBot()) return;
        ReactionUser.React(event);
        ReactionOR.onEvent(event);
        ContestReaction.onEvent(event);
        ReactionArist.onReact(event);
        CommandFight.onReaction(event);
    }
    @Override
    public void onMessageReactionRemove(MessageReactionRemoveEvent event) {
        if(event.getUser().isBot()) return;
        ReactionUser.unReact(event);
        ReactionArist.onUnReact(event);
    }
    @Override
    public void onGuildMemberJoin(@NotNull GuildMemberJoinEvent event) {
        EventJoin.onEvent(event);
    }
    @Override
    public void onGuildMemberLeave(@NotNull GuildMemberLeaveEvent event) {
        EventLeave.onEvent(event);
    }

    @Override
    public void onMessageReceived(MessageReceivedEvent event)
    {
        if(event.getAuthor().isBot()) return;
        logger_message.print("Received Message: " + event.getMessage().getContentRaw() + " || By: " + event.getAuthor().getName() + " || In: " + event.getChannel().getName());
        if(event.isFromType(ChannelType.PRIVATE)) return;
        MessageCurrency.onEvent(event);
        CommandAddOwn.onEvent(event);
        CommandMe.onEvent(event);
        CommandNameMC.onEvent(event);
        CommandLinkMC.onEvent(event);
        CommandGiveCurrency.onEvent(event);
        CommandGiveNubox.onEvent(event);
        CommandOpenNubox.onEvent(event);
        CommandHelp.onEvent(event);
        CommandOther.onEvent(event);
        RepostCheck.onEvent(event);
        CommandPermissionCheck.onEvent(event);
        if(event.getGuild().getMembersWithRoles(ID.bot_role).contains(event.getGuild().getMemberById(event.getAuthor().getIdLong()))) {
            CommandSetCurrency.onEvent(event);
            CommandEditJson.onEvent(event);
            CommandWiki.onEvent(event);
            if(event.getGuild().getIdLong() == Guilds.Nullbloxme) {
                    CommandLog.onEvent(event);
                    CommandContestTop.onEvent(event);
                    HighScoreNew.onEvent(event);
                    try {
                        MessageDollarToEuro.onEvent(event);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
        } else {
            //event.getChannel().sendMessage("> You're not allowed to use this Command!").complete();
        }
        CommandFight.onEvent(event);
        CommandTicTacToeMP.onEvent(event);
        CommandInventory.onEvent(event);
        CommandInventoryItem.onEvent(event);
        CommandSlotMachine.onEvent(event);
        CommandPortfolio.onEvent(event);
        CommandRole.onEvent(event);
         if(event.getGuild().getIdLong() == Guilds.Nullbloxme) {
             MessageRandom.onEvent(event);
            EventMessage.onEvent(event);
            EventShowcase.onEvent(event);
            MessageORCheck.onEvent(event);
            CommandRoles.onEvent(event);
            CommandTicTacToe.onEvent(event);
            CommandBuyShopItem.onEvent(event);
            CommandCreation.onEvent(event);
        }
        if(event.getGuild().getIdLong() == Guilds.Nubx3D) {
            CommandSkateBoard.onEvent(event);
        }
        if(event.getMessage().getContentRaw().equalsIgnoreCase("!MSGTED")) {
            event.getChannel().sendMessage("placeholder").complete();
        }
        if(event.getMessage().getContentRaw().equalsIgnoreCase("!USERFILES")) {
            List<File> files = (List<File>) FileUtils.listFiles(new File("bot/users"), new String[]{"json"}, true);
            System.out.println(files);
            StringBuilder str = new StringBuilder("`");
            for (File file : files) {
                str.append(file.getName()).append("`,\n`");
            }
            event.getChannel().sendMessage(str.toString()).complete();
        }
    }
    @Override
    public void onPrivateMessageReceived(PrivateMessageReceivedEvent event) {
        for (Guild mutualGuild : event.getAuthor().getMutualGuilds()) {
            if(mutualGuild.getIdLong() == Guilds.Nullbloxme) {
                CommandAddShopItem.onEvent(event);
                CommandContestEntry.onEvent(event);
                break;
            }
        }
    }
}

