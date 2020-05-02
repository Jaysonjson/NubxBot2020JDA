package jda.jayson.guilds.nullbloxme.commands.fun.Fight;


import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.events.message.react.MessageReactionAddEvent;

import java.util.HashMap;
import java.util.Random;
import java.util.Timer;

public class CommandFight {
    static HashMap<User, FightGame> fightHashMap = new HashMap<>();
    public static void onEvent(MessageReceivedEvent event) {
        Message msg = event.getMessage();
        String[] argument = msg.getContentRaw().split(" ");
        if (msg.getContentRaw().contains("!fight")) {
            Member user;
            if(msg.getMentionedMembers().size() > 0) {
                user = msg.getMentionedMembers().get(0);
            } else {
                user = msg.getGuild().getMembersByName(argument[1],true).get(0);
            }
            if(user.getIdLong() == event.getAuthor().getIdLong()) {
                event.getChannel().sendMessage("> Do you really want to fight yourself?").complete();
                return;
            }
            if(event.getAuthor().isBot()) {
                event.getChannel().sendMessage("> Most Robots can't fight...").complete();
                return;
            }
            if(fightHashMap.containsKey(user)) {
                event.getChannel().sendMessage("> This user is already fighting!").complete();
            }
            if (!fightHashMap.containsKey(event.getAuthor())) {
                FightPlayer player = new FightPlayer(event.getAuthor());
                FightPlayer player2 = new FightPlayer(user.getUser());
                FightGame game = new FightGame(player,player2);
                fightHashMap.put(event.getAuthor(), game);
                fightHashMap.put(user.getUser(), game);
                player.setTurn(true);
                Timer t = new Timer();
                t.schedule(
                        new java.util.TimerTask() {
                            @Override
                            public void run() {
                                if (fightHashMap.containsKey(event.getAuthor())) {
                                    event.getChannel().sendMessage("> You took to long! Fight has ended...").complete();
                                    fightHashMap.remove(event.getAuthor());
                                    fightHashMap.remove(user.getUser());
                                    t.cancel();
                                }
                            }
                        }, 170000);
                Message reaction_msg = event.getChannel().sendMessage("> " + event.getAuthor().getName() + "'s Turn! (React: ⚔️ = ATTACK **|** \uD83D\uDEE1️ = DEFEND)").complete();
                reaction_msg.addReaction("⚔️").complete();
                reaction_msg.addReaction("\uD83D\uDEE1️").complete();
                game.setMessage(reaction_msg);            } else {
                event.getChannel().sendMessage("> You are fighting already!").complete();
            }
        }
        player1(event);
    }


    public static void onReaction(MessageReactionAddEvent event) {
        if(fightHashMap.containsKey(event.getUser())) {
            FightGame game = fightHashMap.get(event.getUser());
            if (event.getMessageIdLong() == game.message.getIdLong()) {
                Random r = new Random();
                Integer max_dmg = r.nextInt(45);
                //FightPlayer current_player = game.player1;
                if (event.getReaction().getReactionEmote().getEmoji().equalsIgnoreCase("\uD83D\uDEE1️")) {
                    max_dmg = r.nextInt(10);
                    if (event.getUser() == game.player1.user && game.player1.turn()) {
                        game.player1.setDMGTake(max_dmg);
                        game.player2.setTurn(true);
                        game.player1.setTurn(false);
                        event.getChannel().sendMessage("> " + game.player1.user.getName() + " is Defending!").complete();
                        Message reaction_msg = event.getChannel().sendMessage("> " + game.player2.user.getName() + "'s Turn! (React: ⚔️ = ATTACK **|** \uD83D\uDEE1️ = DEFEND)").complete();
                        reaction_msg.addReaction("⚔️").complete();
                        reaction_msg.addReaction("\uD83D\uDEE1️").complete();
                        game.setMessage(reaction_msg);
                    }
                    if (event.getUser() == game.player2.user && game.player2.turn()) {
                        game.player2.setDMGTake(max_dmg);
                        game.player1.setTurn(true);
                        game.player2.setTurn(false);
                        event.getChannel().sendMessage("> " + game.player2.user.getName() + " is Defending!").complete();
                        Message reaction_msg = event.getChannel().sendMessage("> " + game.player1.user.getName() + "'s Turn! (React: ⚔️ = ATTACK **|** \uD83D\uDEE1️ = DEFEND)").complete();
                        reaction_msg.addReaction("⚔️").complete();
                        reaction_msg.addReaction("\uD83D\uDEE1️").complete();
                        game.setMessage(reaction_msg);                    }
                    return;
                }
                if (event.getReaction().getReactionEmote().getEmoji().equalsIgnoreCase("⚔️")) {
                    if (event.getUser() == game.player1.user) {
                        game.player1.setDMGTake(max_dmg);
                    }
                    if (event.getUser() == game.player2.user) {
                        game.player2.setDMGTake(max_dmg);
                    }
                    if (game.player1.turn() && event.getUser() == game.player1.user) {
                        game.player2.setHealth(game.player2.getHealth() - game.player2.dmgtake);
                        event.getChannel().sendMessage("> Hit! " + game.player2.dmgtake + " Damage! " + game.player2.user.getName() + " has " + game.player2.getHealth() + "HP left! \n" + game.player2.user.getName() + "'s Health:  " + game.player2.getHealth() + " / " + game.player2.getMaxHealth() + "\n" + getHealthBar(game.player2)).complete();
                        game.player2.setTurn(true);
                        game.player1.setTurn(false);
                        checkHP(game);
                        if (checkHP(game) != null) {
                            event.getChannel().sendMessage("> " + checkHP(game).user.getName() + " has lost!").complete();
                        }
                        if (!game.isEnd()) {
                            Message reaction_msg = event.getChannel().sendMessage("> " + game.player2.user.getName() + "'s Turn! (React: ⚔️ = ATTACK **|** \uD83D\uDEE1️ = DEFEND)").complete();
                            reaction_msg.addReaction("⚔️").complete();
                            reaction_msg.addReaction("\uD83D\uDEE1️").complete();
                            game.setMessage(reaction_msg);
                        }
                        return;
                    }
                    if (game.player2.turn() && event.getUser() == game.player2.user) {
                        game.player1.setHealth(game.player1.getHealth() - game.player1.dmgtake);
                        event.getChannel().sendMessage("> Hit! " + game.player1.dmgtake + " Damage! " + game.player1.user.getName() + " has " + game.player1.getHealth() + "HP left! \n" + game.player1.user.getName() + "'s Health:  " + game.player1.getHealth() + " / " + game.player1.getMaxHealth() + "\n" + getHealthBar(game.player1)).complete();
                        game.player1.setTurn(true);
                        game.player2.setTurn(false);
                        if (checkHP(game) != null) {
                            event.getChannel().sendMessage("> " + checkHP(game).user.getName() + " has lost!").complete();
                        }
                        if (!game.isEnd()) {
                            Message reaction_msg = event.getChannel().sendMessage("> " + game.player1.user.getName() + "'s Turn! (React: ⚔️ = ATTACK **|** \uD83D\uDEE1️ = DEFEND)").complete();
                            reaction_msg.addReaction("⚔️").complete();
                            reaction_msg.addReaction("\uD83D\uDEE1️").complete();
                            game.setMessage(reaction_msg);
                        }
                        return;
                    }
                }
            }
        }
    }
    public static void player1(MessageReceivedEvent event) {
        if(fightHashMap.containsKey(event.getAuthor())) {
            if (event.getMessage().getContentRaw().equalsIgnoreCase("attack") || event.getMessage().getContentRaw().equalsIgnoreCase("defend")) {

            }
        }
    }
    private static void checkHP(FightGame game, MessageReceivedEvent event) {
        if(game.player1.getHealth() < 1) {
            fightHashMap.remove(game.player1.user);
            fightHashMap.remove(game.player2.user);
            event.getChannel().sendMessage("> " + game.player1.user.getName() + " has lost!").complete();
            game.setEnd(true);
            return;
        }
        if(game.player2.getHealth() < 1) {
            fightHashMap.remove(game.player1.user);
            fightHashMap.remove(game.player2.user);
            event.getChannel().sendMessage("> " + game.player2.user.getName() + " has lost!").complete();
            game.setEnd(true);
            return;
        }
    }
    private static FightPlayer checkHP(FightGame game) {
        if(game.player1.getHealth() < 1) {
            fightHashMap.remove(game.player1.user);
            fightHashMap.remove(game.player2.user);
            game.setEnd(true);
            return game.player1;
        }
        if(game.player2.getHealth() < 1) {
            fightHashMap.remove(game.player1.user);
            fightHashMap.remove(game.player2.user);
            game.setEnd(true);
            return game.player2;
        }
        return null;
    }
    private static String getHealthBar(FightPlayer player) {
        StringBuilder healthbar = new StringBuilder();
        int i_h = 0;
        for (int i = 0; i < player.getHealth() / 10; i++) {
            healthbar.append("\uD83D\uDFE9");
            i_h++;
        }
        for (int integer = i_h; integer < player.getMaxHealth() / 10; integer++) {
            healthbar.append("\uD83D\uDFE5");
        }
        return healthbar.toString();
    }
}
