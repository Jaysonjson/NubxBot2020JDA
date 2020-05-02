package jda.jayson.guilds.nullbloxme.commands.fun.Fight;

import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.User;

import java.util.Random;

public class FightPlayer {
    User user;
    Integer max_hp = 100;
    Integer hp = max_hp;
    Boolean turn;
    Integer dmgtake = new Random().nextInt(50);
    public FightPlayer(User user) {
        this.user = user;
    }
    public Integer getHealth() {
        return hp;
    }
    public void setHealth(Integer value) {
        hp = value;
    }
    public Integer getMaxHealth() {
        return max_hp;
    }
    public boolean turn() {
        return turn;
    }
    public void setTurn(Boolean turn) {
        this.turn = turn;
    }
    public Integer getDmgTaked() {
        return dmgtake;
    }

    public void setDMGTake(Integer value) {
        this.dmgtake = value;
    }
}
