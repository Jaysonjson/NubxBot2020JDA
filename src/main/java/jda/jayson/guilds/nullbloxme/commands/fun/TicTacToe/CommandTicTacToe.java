package jda.jayson.guilds.nullbloxme.commands.fun.TicTacToe;

import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.util.HashMap;
import java.util.Random;
import java.util.Timer;

public class CommandTicTacToe {

    static HashMap<Long,TicTacToeGame> gameHashMap = new HashMap<>();

    public static void onEvent(MessageReceivedEvent event) {
        Message msg = event.getMessage();
        if(msg.getContentRaw().equalsIgnoreCase("!tictactoe bot")) {
            if (!gameHashMap.containsKey(event.getAuthor().getIdLong())) {
                TicTacToeGame game = new TicTacToeGame();
                game.create();
                gameHashMap.put(event.getAuthor().getIdLong(), game);
                kiFirst(game);
                Timer t = new Timer();
                t.schedule(
                        new java.util.TimerTask() {
                            @Override
                            public void run() {
                                if (gameHashMap.containsKey(event.getAuthor().getIdLong())) {
                                    event.getChannel().sendMessage("> You took to long! Game has ended...").complete();
                                    gameHashMap.remove(event.getAuthor().getIdLong());
                                    t.cancel();
                                }
                            }
                        }, 55000);
                event.getChannel().sendMessage(game.container().Fields()).complete();
                event.getChannel().sendMessage("> Your Turn!").complete();
            } else {
                event.getChannel().sendMessage("> Theres already a game running under your ID...").complete();
            }
        }
        gameCheck(event);
    }

    private static void gameCheck(MessageReceivedEvent event) {
       if(gameHashMap.containsKey(event.getAuthor().getIdLong())) {
           TicTacToeGame game = gameHashMap.get(event.getAuthor().getIdLong());
           game.checkWin(event);
           for (int i = 0; i < 9; i++) {
               if (event.getMessage().getContentRaw().contains(String.valueOf(i))) {
                   if (!game.container().getField(i).getContent().toString().equalsIgnoreCase("O") && !game.container().getField(i).getContent().toString().equalsIgnoreCase("X")) {
                       game.container().getField(i).setContent("X");
                       kiCheck(game, event);
                       event.getChannel().sendMessage(game.container().Fields()).complete();
                   } else {
                       event.getChannel().sendMessage("> Field " + i + " is already taken!").complete();
                   }
               }
           }
       }
    }
    private static void kiCheck(TicTacToeGame game, MessageReceivedEvent event) {
        Random r = new Random();
        Integer i = r.nextInt(9);
        game.checkWin(event);
        if(!game.container().getField(i).getContent().toString().equalsIgnoreCase("X")) {
              game.container().getField(i).setContent("O");
        }
    }
    private static void kiFirst(TicTacToeGame game) {
        Random r = new Random();
        Integer i = r.nextInt(9);
        if(!game.container().getField(i).getContent().toString().equalsIgnoreCase("X")) {
            game.container().getField(i).setContent("O");
        }
    }
}
