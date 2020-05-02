package jda.jayson.guilds.nullbloxme.commands.fun.Fight;

import net.dv8tion.jda.api.entities.Message;

public class FightGame {
    FightPlayer player1;
    FightPlayer player2;
    Message message;
    Boolean end = false;
    public FightGame(FightPlayer p1, FightPlayer p2) {
        this.player1 = p1;
        this.player2 = p2;
    }

    public void setEnd(Boolean value) {
        this.end = value;
    }
    public boolean isEnd() {
        return this.end;
    }
    public void setMessage(Message message) {
        this.message = message;
    }

}
