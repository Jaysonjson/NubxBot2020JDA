package jda.jayson.guilds.nullbloxme.commands.fun.TicTacToe;

import jda.jayson.file.JSON;
import jda.jayson.id.ID;
import jda.jayson.id.References;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class TicTacToeGame {
    TTTContainer fieldContainer = new TTTContainer(3);
    public TicTacToeGame create() {
        for (int i = 0; i < 9; i++) {
            fieldContainer.addField(new TTTField(i));
        }
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
                CommandTicTacToe.gameHashMap.remove(event.getAuthor().getIdLong());
            }
        }
    }
    private void win(MessageReceivedEvent event, String winner) {
        event.getChannel().sendMessage("> " + winner + " has Won!").complete();
        if(winner.equalsIgnoreCase("X")) {
            JSON.load(String.valueOf(event.getAuthor().getIdLong()));
            References.currency += 5;
            event.getChannel().sendMessage("You have earned 5 " + ID.currency + "!").complete();
            JSON.save(String.valueOf(event.getAuthor().getIdLong()));
        }
        CommandTicTacToe.gameHashMap.remove(event.getAuthor().getIdLong());
    }

    public TTTContainer container() {
        return this.fieldContainer;
    }
}
