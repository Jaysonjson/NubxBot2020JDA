package jda.jayson.command;


import net.dv8tion.jda.api.entities.User;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class Cooldown {
    private static ArrayList<User> users = new ArrayList<>();
    User user;
    Integer time;
    Integer fakeTime;

        public Cooldown(User user, Integer time) {
            this.time = time;
            this.user = user;
        }
        public Cooldown(Integer time) {
        this.time = time;
        }

        public Cooldown create() {
            return this;
        }
        @Deprecated
        public User setUser(User user) {
            this.user = user;
            return user;
        }
        public User addUser(User user) {
            return setUser(user);
        }

    public void start() {
        Timer t = new Timer();
        if(!users.contains(user)) {
            users.add(user);
        }
        t.schedule(new TimerTask() {
            Integer i = 0;
            @Override
            public void run() {
                i++;
                if(i % time == 0) {
                    users.remove(user);
                    t.cancel();
                } else {
                    fakeTime = (time - (i % time));
                }
            }
        }, 0,1000);
    }
    public boolean cooldowned() {
        return users.contains(user);
    }
    public Integer getTime() {
        return time;
    }
    public Integer getRemainingTime() {
        return fakeTime;
    }
}
