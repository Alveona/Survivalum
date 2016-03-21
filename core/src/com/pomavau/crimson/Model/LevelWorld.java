package com.pomavau.crimson.Model;

import com.pomavau.crimson.View.ImageActor;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

/**
 * Created by Гриша on 07.02.2016.
 */
public class LevelWorld extends Stage {
    private Player player;
    private Bot bot;
    private ImageActor background;

    public LevelWorld(ScreenViewport screenViewport, SpriteBatch batch) {
        super(screenViewport, batch);
        background = new ImageActor(new Texture("android/assets/background1.png"),0,0);
        addActor(background);
        player = new Player(new Texture("android/assets/hero.png"), Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2, 100, 60, 60, 70); //50/30 default w/h
        bot = new Bot(new Texture("android/assets/hero.png"), 0, 0, 100, 60, 60, 70);
        addActor(player);
        addActor(bot);
    }

    public Player getPlayer() {
        return player;
    }

    public Bot getBot() {return bot;}

    public float getWidth () {return background.getWidth();
    //return 1145;
     }

    public float getHeight () {
       // return 616;
        return background.getHeight();
    }
    public void setBackgroundtoBack()
    {
        background.toBack();
    }
}
