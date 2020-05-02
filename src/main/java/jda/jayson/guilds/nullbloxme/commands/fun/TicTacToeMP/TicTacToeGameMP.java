package jda.jayson.guilds.nullbloxme.commands.fun.TicTacToeMP;

import jda.jayson.guilds.nullbloxme.commands.fun.TicTacToe.TTTContainer;
import jda.jayson.guilds.nullbloxme.commands.fun.TicTacToe.TTTField;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class TicTacToeGameMP {
    TTTContainer fieldContainer = new TTTContainer(3);
    TicTacToePlayer player1;
    TicTacToePlayer player2;
    Boolean end = false;
    public TicTacToeGameMP create(TicTacToePlayer p1, TicTacToePlayer p2) {
        for (int i = 0; i < 9; i++) {
            fieldContainer.addField(new TTTField(i));
        }
            this.player1 = p1;
            this.player2 = p2;
        return this;
    }

    public void checkWin(MessageReceivedEvent event) {
        checkLine(event,0,1,2);
        checkLine(event,3,4,5);
        checkLine(event,6,7,8);
        checkLine(event,0,3,6);
        checkLine(event,1,4,7);
        checkLine(event,2,5,8);
        checkLine(event,0,4,8);
        checkLine(event,2,4,6);
    }
    private void checkLine(MessageReceivedEvent event, Integer... i) {
        if(fieldContainer.getField(i[0]).getContent().equals(fieldContainer.getField(i[1]).getContent()) && fieldContainer.getField(i[1]).getContent().equals(fieldContainer.getField(i[2]).getContent())) {
            win(event,fieldContainer.getField(i[0]).getContent().toString());
        }
        Integer fd = 0;
        for (int j = 0; j < fieldContainer.fields.size(); j++) {
            if(fieldContainer.getField(j).getContent().toString().equalsIgnoreCase("X") || fieldContainer.getField(j).getContent().toString().equalsIgnoreCase("O")) {
                fd++;
            }
            if(fd.equals(fieldContainer.fields.size())) {
                event.getChannel().sendMessage("Draw!").complete();
                CommandTicTacToeMP.gameHashMap.remove(player1.user);
                CommandTicTacToeMP.gameHashMap.remove(player2.user);
            }
        }
    }
    private void win(MessageReceivedEvent event, String winner) {
        String name = winner;
        if (winner.equalsIgnoreCase("X")) {
            name = player1.user.getName();
        }
        if (winner.equalsIgnoreCase("O")) {
            name = player2.user.getName();
        }
        setEnd(true);
        event.getChannel().sendMessage("> " + name + " has Won!").complete();
        CommandTicTacToeMP.gameHashMap.remove(player1.user);
        CommandTicTacToeMP.gameHashMap.remove(player2.user);
    }

    public TTTContainer container() {
        return this.fieldContainer;
    }
    public void setEnd(Boolean value) {
        this.end = value;
    }
    public boolean isEnd() {
        return this.end;
    }
}
