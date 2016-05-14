package com.pomavau.crimson.Model;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Timer;
import com.pomavau.crimson.Controller.Animation;
import com.pomavau.crimson.Controller.MyContactListener;
import com.pomavau.crimson.Controller.ObjectState;
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
    private ArenaBorders arenaBorders;
   // private PhWorld phWorld;
    private Bot bot;
    private Bot[] botsArray;
    private Array<Bot> botArray;
    private HashMap<Integer, Bot> bots;
    private ImageActor background;
    private Rectangle worldBorders;
    private TextureRegion image;

    private Animation moveAnimation;
    private Animation moveAnimation_icegun;
    private Animation reloadAnimation;
    private Animation reloadAnimation_icegun;

    private Animation zombiemoveAnimation;
    private Animation zombieattackAnimation;
    private Animation zombiespawnAnimation;

    private World physicsWorld;
    private int botscount = 1;
    private int botscurrentcount = 0;
    private Body body;

    public LevelWorld(ScreenViewport screenViewport, SpriteBatch batch) {
        super(screenViewport, batch);

        physicsWorld = new World(new Vector2(0, 0), true);

        physicsWorld.setContactListener(new MyContactListener(physicsWorld));
      //  phWorld = new PhWorld();

        botsArray = new Bot[botscount];
        botArray = new Array<Bot>();
        bots = new HashMap<Integer, Bot>();
        moveAnimation = new Animation(new TextureRegion(new Texture("android/assets/hero/heromove_atlas.png"), 6260, 206), 20, 3);
        moveAnimation_icegun = new Animation(new TextureRegion(new Texture("android/assets/hero/heromove_icegun_atlas.png"), 6260, 206), 20, 3);
        reloadAnimation = new Animation(new TextureRegion(new Texture("android/assets/hero/heroreload_atlas.png"), 6440, 217), 20, 1);
        reloadAnimation_icegun = new Animation(new TextureRegion(new Texture("android/assets/hero/heroreload_icegun_atlas.png"), 6440, 217), 20, 1);
        zombiemoveAnimation = new Animation(new TextureRegion(new Texture("android/assets/zombie/zombiemove_atlas.png"), 256, 64), 4, (float)0.5);
        zombiespawnAnimation = new Animation(new TextureRegion(new Texture("android/assets/zombie/zombiespawn_atlas.png"), 128, 64), 2, 1f);
        zombieattackAnimation = new Animation(new TextureRegion(new Texture("android/assets/zombie/zombieattack_atlas.png"), 128, 64), 2, 0.5f);

        background = new ImageActor(new Texture("android/assets/background4.jpg"),0,0);
        worldBorders = new Rectangle(0, 0, this.getWidth(), this.getHeight());
        addActor(background);
     //   player = new Player(new Texture("android/assets/hero.png"), Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2, 100, 60, 60, 70); //50/30 default w/h
        player = new Player(new Texture("android/assets/hero.png"), Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2, 100, 60, 60, 70, physicsWorld); //50/30 default w/h
       // arenaBorders = new ArenaBorders(new Texture("android/assets/arenaborders.png"), 0, 0, 1145, 616, physicsWorld);
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
        //bot = new Bot(zombiemoveAnimation.getFrame(), 0, 0, 64, 64, 60, 70, physicsWorld);
        //bot1 = new Bot(zombiemoveAnimation.getFrame(), 100, 100, 64, 64, 60, 70, physicsWorld);


        //SPAWNING BOTS
        /*
        for(int i = 0; i < botsArray.length; i++)
        {
            botsArray[i] = new Bot(zombiemoveAnimation.getFrame(), 50 * i, 50* i, 64, 64, 60, 70, physicsWorld);
            bots.put(i, botsArray[i]);
            addActor(botsArray[i]);
        }
        */
        //for(int i = 0; i < botscount; i++)
        spawnbot();


        //bots.put(1, bot);
        //bots.put(2, bot1);
       // bots[] = new Bot[1];
      //  bots[0] = bot;
      //  bots[1] = bot1;
        //bot.setOrigin(bot.getWidth() / 2, bot.getHeight() /2);
        addActor(player);
        addActor(player.getShootingPoint());
        //addActor(arenaBorders);
        //background.setScale((float)1.3);
        //addActor(bot);
        //addActor(bot1);

       // background.setScale(2);
    }

    public Player getPlayer() {
        return player;
    }

    //public Bot getBot() {return bot;}
    //public Bot getBot1() {return bot1;}

    public Bot getBotbyIndex(int index)
    {
        return bots.get(index - 1);
        //return botArray.get(index);
    }
/*
    public Bot[] getBots()
    {
        return bots;
    }
*/
    public float getWidth () {
        return 960;
        //return background.getWidth();
    //return 1145;
     }

    public float getHeight () {
       return 540;
       // return 616;
       // return background.getHeight();
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
    public Animation getMoveAnimation_icegun()
    {
        return moveAnimation_icegun;
    }

    public Animation getZombieMoveAnimation()
    {
        return zombiemoveAnimation;
    }
    public Animation getZombieAttackAnimation()
    {
        return zombieattackAnimation;
    }
    public Animation getZombieSpawnAnimation()
    {
        return zombiespawnAnimation;
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

    public void botsUpdate(int i, float delta, ObjectState objectState)
    {
        switch(objectState)
        {
           case MOVING: bots.get(i - 1).setTexture(zombiemoveAnimation.getFrame()); break;

            case SPAWNING:
                if(zombiespawnAnimation.isFinishedOnce()){
                bots.get(i - 1).setTexture(zombiespawnAnimation.getFrame()); }
                else
                {bots.get(i - 1).setCurrentState(ObjectState.MOVING);}
                break;

            case ATTACKING: bots.get(i - 1).setTexture(zombieattackAnimation.getFrame()); break;

            default: bots.get(i - 1).setTexture(zombiemoveAnimation.getFrame());
        }

        //botArray.get(i).setTexture(zombiemoveAnimation.getFrame());
    }

    public World getPhysicsWorld()
    {
        return physicsWorld;
    }

    public int getBotscount()
    {
        return botArray.size;
    }


    public void spawnbot()
    {
        bot = new Bot(zombiespawnAnimation.getFrame(), (float) Math.random() * 1145, (float) Math.random() * 616, 64, 64, 60, 70, physicsWorld);
        bot.setCurrentState(ObjectState.SPAWNING);
        zombiespawnAnimation.setFinishedOnce(false);
        if(zombiespawnAnimation.isFinishedOnce())
            bot.setCurrentState(ObjectState.MOVING);
        //botArray.add(new Bot(zombiemoveAnimation.getFrame(), (float) Math.random() * 1145, (float) Math.random() * 616, 64, 64, 60, 70, physicsWorld));
        botArray.add(bot);
        //botsArray[botscurrentcount] = new Bot(zombiemoveAnimation.getFrame(), (float)Math.random()*1145, (float)Math.random()*616, 64, 64, 60, 70, physicsWorld);
        bots.put(botscurrentcount, botArray.get(botscurrentcount));
       // bots.put(botscurrentcount, botsArray[botscurrentcount]);
        addActor(botArray.get(botscurrentcount));
        //addActor(botsArray[botscurrentcount]);
        botscurrentcount++;
        botscount++;

       // botArray.size++;
    }
}
