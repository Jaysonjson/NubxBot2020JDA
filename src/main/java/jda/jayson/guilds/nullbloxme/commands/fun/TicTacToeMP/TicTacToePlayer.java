package jda.jayson.guilds.nullbloxme.commands.fun.TicTacToeMP;

import net.dv8tion.jda.api.entities.User;


public class TicTacToePlayer {
    User user;
    Boolean turn;
    public TicTacToePlayer(User user) {
        this.user = user;
    }
    public boolean turn() {
        return turn;
    }
    public void setTurn(Boolean turn) {
        this.turn = turn;
    }
}
