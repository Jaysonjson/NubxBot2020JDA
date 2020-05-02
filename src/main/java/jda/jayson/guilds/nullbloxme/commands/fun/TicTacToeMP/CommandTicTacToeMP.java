package jda.jayson.guilds.nullbloxme.commands.fun.TicTacToeMP;

import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.util.HashMap;
import java.util.Timer;

public class CommandTicTacToeMP {
    static HashMap<User, TicTacToeGameMP> gameHashMap = new HashMap<>();

    public static void onEvent(MessageReceivedEvent event) {
        Message msg = event.getMessage();
        String argument[] = msg.getContentRaw().split(" ");
        if (msg.getContentRaw().contains("!tictactoe") && !msg.getContentRaw().contains("bot")) {
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
            if(gameHashMap.containsKey(user)) {
                event.getChannel().sendMessage("> This user is already fighting!").complete();
            }
            if (!gameHashMap.containsKey(event.getAuthor())) {
                TicTacToePlayer player = new TicTacToePlayer(event.getAuthor());
                TicTacToePlayer player2 = new TicTacToePlayer(user.getUser());
                TicTacToeGameMP game = new TicTacToeGameMP();
                game.create(player,player2);
                gameHashMap.put(player.user,game);
                gameHashMap.put(player2.user,game);
                player.setTurn(true);
                Timer t = new Timer();
                t.schedule(
                        new java.util.TimerTask() {
                            @Override
                            public void run() {
                                if (gameHashMap.containsKey(event.getAuthor())) {
                                    event.getChannel().sendMessage("> You took to long! Game has ended...").complete();
                                    gameHashMap.remove(player.user);
                                    gameHashMap.remove(player2.user);
                                    t.cancel();
                                }
                            }
                        }, 120000);
                event.getChannel().sendMessage(game.container().Fields()).complete();
                event.getChannel().sendMessage("> " + player.user.getName() + "'s Turn!").complete();
            } else {
                event.getChannel().sendMessage("> Theres already a game running under your ID...").complete();
            }
        }
        gameCheck(event);
    }





    private static void gameCheck(MessageReceivedEvent event) {
        if (gameHashMap.containsKey(event.getAuthor())) {
            for (int i = 0; i < 9; i++) {
                if (event.getMessage().getContentRaw().equalsIgnoreCase(String.valueOf(i))) {
                    TicTacToeGameMP game = gameHashMap.get(event.getAuthor());
                    if (game.player1.turn() && event.getAuthor() == game.player1.user) {
                        game.player2.setTurn(true);
                        game.player1.setTurn(false);
                        if (!game.container().getField(i).getContent().toString().equalsIgnoreCase("O") && !game.container().getField(i).getContent().toString().equalsIgnoreCase("X")) {
                            game.container().getField(i).setContent("X");
                        } else {
                            event.getChannel().sendMessage("> Field " + i + " is already taken!").complete();
                        }
                        game.checkWin(event);
                        if (!game.isEnd()) {
                            event.getChannel().sendMessage(game.fieldContainer.Fields()).complete();
                            event.getChannel().sendMessage("> " + game.player2.user.getName() + "'s Turn! (Type: number 0-9)").complete();
                        }
                        return;
                    }
                    if (game.player2.turn() && event.getAuthor() == game.player2.user) {
                        game.player2.setTurn(false);
                        game.player1.setTurn(true);
                        if (!game.container().getField(i).getContent().toString().equalsIgnoreCase("O") && !game.container().getField(i).getContent().toString().equalsIgnoreCase("X")) {
                            game.container().getField(i).setContent("O");
                        } else {
                            event.getChannel().sendMessage("> Field " + i + " is already taken!").complete();
                        }
                        game.checkWin(event);
                        if (!game.isEnd()) {
                            event.getChannel().sendMessage(game.fieldContainer.Fields()).complete();
                            event.getChannel().sendMessage("> " + game.player1.user.getName() + "'s Turn! (Type: number 0-9)").complete();
                        }
                        return;
                    }
                }
            }
        }
    }
}
