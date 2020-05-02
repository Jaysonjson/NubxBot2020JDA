package jda.jayson.guilds.nullbloxme.commands.fun.TicTacToe;

public class TTTField implements IField {
    Integer index;
    Object content;

    public TTTField(Object content) {
        this.content = content;
    }

    public TTTField() {
        this.content = "#";
    }

    @Override
    public Integer index() {
        return null;
    }

    @Override
    public Object getContent() {
        return this.content;
    }

    @Override
    public void setContent(Object content) {
        this.content = content;
    }
}
