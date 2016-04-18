package com.pomavau.crimson.Model;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.physics.box2d.World;
import com.pomavau.crimson.Controller.Animation;
import com.pomavau.crimson.Controller.MyContactListener;
import com.pomavau.crimson.View.ImageActor;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import java.util.HashMap;

/**
 * Created by Гриша on 07.02.2016.
 */
public class LevelWorld extends Stage {
    private Player player;
   // private PhWorld phWorld;
    private Bot bot;
    private Bot bot1;
    private Bot[] botsArray;
    private HashMap<Integer, Bot> bots;
    private ImageActor background;
    private Rectangle worldBorders;
    private TextureRegion image;
    private Animation moveAnimation;
    private Animation zombiemoveAnimation;
    private Animation reloadAnimation;
    private World physicsWorld;
    private Body body;
    public LevelWorld(ScreenViewport screenViewport, SpriteBatch batch) {
        super(screenViewport, batch);
        physicsWorld = new World(new Vector2(0, -10), true);
        physicsWorld.setContactListener(new MyContactListener(physicsWorld));
      //  phWorld = new PhWorld();

        botsArray = new Bot[10];
        bots = new HashMap<Integer, Bot>();
        moveAnimation = new Animation(new TextureRegion(new Texture("android/assets/hero/heromove_atlas.png"), 6260, 206), 20, 3);
        reloadAnimation = new Animation(new TextureRegion(new Texture("android/assets/hero/heroreload_atlas.png"), 6440, 217), 20, 1);
        zombiemoveAnimation = new Animation(new TextureRegion(new Texture("android/assets/zombie/zombiemove_atlas.png"), 256, 64), 4, (float)0.5);
        background = new ImageActor(new Texture("android/assets/background1.png"),0,0);
        worldBorders = new Rectangle(0, 0, this.getWidth(), this.getHeight());
        addActor(background);
     //   player = new Player(new Texture("android/assets/hero.png"), Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2, 100, 60, 60, 70); //50/30 default w/h
        player = new Player(new Texture("android/assets/hero.png"), Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2, 100, 60, 60, 70, physicsWorld); //50/30 default w/h
/*
       BodyDef def = new BodyDef();
        def.type = BodyDef.BodyType.DynamicBody;
        body = physicsWorld.createBody(def);
        def.position.set(player.getX(), player.getY());
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(player.getWidth(), player.getHeight());
*/
        //  player = new Player(moveAnimation.getFrame(), Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2, 90, 50, 60, 70); //50/30 default w/h
     //   player.setOrigin(player.getWidth()/2, player.getHeight() / 2);

        //bot = new Bot(new Texture("android/assets/hero.png"), 0, 0, 100, 60, 60, 70);
        bot = new Bot(zombiemoveAnimation.getFrame(), 0, 0, 64, 64, 60, 70);
        bot1 = new Bot(zombiemoveAnimation.getFrame(), 100, 100, 64, 64, 60, 70);
        for(int i = 0; i < botsArray.length; i++)
        {
            botsArray[i] = new Bot(zombiemoveAnimation.getFrame(), 50 * i, 50* i, 64, 64, 60, 70);
            bots.put(i, botsArray[i]);
            addActor(botsArray[i]);
        }
        //bots.put(1, bot);
        //bots.put(2, bot1);
       // bots[] = new Bot[1];
      //  bots[0] = bot;
      //  bots[1] = bot1;
        bot.setOrigin(bot.getWidth() / 2, bot.getHeight() /2);
        addActor(player);

        background.setScale((float)1.3);
        //addActor(bot);
        //addActor(bot1);

       // background.setScale(2);
    }

    public Player getPlayer() {
        return player;
    }

    public Bot getBot() {return bot;}
    public Bot getBot1() {return bot1;}

    public Bot getBotbyIndex(int index)
    {
        return bots.get(index);
    }
/*
    public Bot[] getBots()
    {
        return bots;
    }
*/
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

    public Animation getZombieMoveAnimation()
    {
        return zombiemoveAnimation;
    }

    public Animation getReloadAnimation()
    {
        return reloadAnimation;
    }

    public void playerUpdate(Animation animation, float delta)
    {
        player.setTexture(animation.getFrame());
    }

    public void botUpdate(Bot bot, float delta){bot.setTexture(zombiemoveAnimation.getFrame());};

    public void botsUpdate(int i, float delta)
    {
        bots.get(i).setTexture(zombiemoveAnimation.getFrame());
    }

    public World getPhysicsWorld()
    {
        return physicsWorld;
    }
}
