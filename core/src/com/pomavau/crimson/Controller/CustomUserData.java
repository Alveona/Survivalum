package com.pomavau.crimson.Controller;

import com.pomavau.crimson.Model.Bot;
import com.pomavau.crimson.Model.Bullet;
import com.pomavau.crimson.Model.Player;

/**
 * Created by Pomavau on 10.05.16.
 */
public class CustomUserData {
    private Bot bot;
    private Bullet bullet;
    private String string;



    private Player player;

    public CustomUserData(String string, Bot actor) {
        this.string = string;
        this.bot = actor;
    }

    public CustomUserData(String string, Bullet actor) {
        this.string = string;
        this.bullet = actor;
    }
    public CustomUserData(String string, Player actor) {
        this.string = string;
        this.player = actor;
    }
    public CustomUserData()
    {

    }



    public Bot getBot() {
        return bot;
    }

    public void setBot(Bot bot) {
        this.bot = bot;
    }



    public Bullet getBullet() {
        return bullet;
    }

    public void setBullet(Bullet bullet) {
        this.bullet = bullet;
    }



    public String getString() {
        return string;
    }

    public void setString(String string) {
        this.string = string;
    }


    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }


}
