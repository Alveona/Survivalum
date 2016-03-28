package com.pomavau.crimson.Model;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.pomavau.crimson.Controller.Animation;
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
    private Rectangle worldBorders;
    private TextureRegion image;
    private Animation moveAnimation;
    private Animation zombiemoveAnimation;
    public LevelWorld(ScreenViewport screenViewport, SpriteBatch batch) {
        super(screenViewport, batch);
        moveAnimation = new Animation(new TextureRegion(new Texture("android/assets/hero/heromove_atlas.png"), 6260, 206), 20, 3);
        zombiemoveAnimation = new Animation(new TextureRegion(new Texture("android/assets/zombie/zombiemove_atlas.png"), 256, 64), 4, 3);
        background = new ImageActor(new Texture("android/assets/background1.png"),0,0);
        worldBorders = new Rectangle(0, 0, this.getWidth(), this.getHeight());
        addActor(background);
        //player = new Player(new Texture("android/assets/hero.png"), Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2, 100, 60, 60, 70); //50/30 default w/h
        player = new Player(moveAnimation.getFrame(), Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2, 90, 50, 60, 70); //50/30 default w/h


        //bot = new Bot(new Texture("android/assets/hero.png"), 0, 0, 100, 60, 60, 70);
        bot = new Bot(zombiemoveAnimation.getFrame(), 0, 0, 64, 64, 60, 70);
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

    public Rectangle getWorldBorders() {return worldBorders;}

    public Animation getMoveAnimation()
    {
        return moveAnimation;
    }

    public void playerUpdate()
    {
      //  player = new Player(moveAnimation.getFrame(), player.getX(), player.getY(), 100, 60, 60, 70);
        addActor(player);
    }
}
