package com.pomavau.crimson.Model;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Timer;
import com.pomavau.crimson.Controller.Animation;
import com.pomavau.crimson.Controller.BorderType;
import com.pomavau.crimson.Controller.BotType;
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
    private Animation moveAnimation_pistol;
    private Animation moveAnimation_thrower;
    private Animation reloadAnimation;
    private Animation reloadAnimation_icegun;
    private Animation reloadAnimation_pistol;
    private Animation reloadAnimation_thrower;

    private Animation zombiemoveAnimation;
    private Animation zombieattackAnimation;
    private Animation zombiespawnAnimation;

    private Animation zombierangemoveAnimation;
    private Animation zombierangeattackAnimation;
    private Animation zombierangespawnAnimation;

    private Animation zombiedoctormoveAnimation;
    private Animation zombiedoctorattackAnimation;
    private Animation zombiedoctorspawnAnimation;

    private Animation zombiepyromoveAnimation;
    private Animation zombiepyroattackAnimation;
    private Animation zombiepyrospawnAnimation;

    private Animation zombiepudgemoveAnimation;
    private Animation zombiepudgeattackAnimation;
    private Animation zombiepudgespawnAnimation;

    private Animation zombiehulkmoveAnimation;
    private Animation zombiehulkattackAnimation;
    private Animation zombiehulkspawnAnimation;

    private Animation zombiewitchmoveAnimation;
    private Animation zombiewitchattackAnimation;
    private Animation zombiewitchspawnAnimation;


    private Animation flameAnimation;

    private ArenaBorders northborder;
    private ArenaBorders southborder;
    private ArenaBorders eastborder;
    private ArenaBorders westborder;

    private World physicsWorld;
    private int botscount = 1;
    private int botscurrentcount = 0;
    private Body body;

    private int randomzombietype;

    private HPcircle hPcircle;


    private float spawntimer = 1;
    private float beforespawn;

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
        moveAnimation_thrower = new Animation(new TextureRegion(new Texture("android/assets/hero/heromove_firegun_atlas.png"), 6260, 206), 20, 3);
        reloadAnimation = new Animation(new TextureRegion(new Texture("android/assets/hero/heroreload_atlas.png"), 6440, 217), 20, 1);
        reloadAnimation_icegun = new Animation(new TextureRegion(new Texture("android/assets/hero/heroreload_icegun_atlas.png"), 6440, 217), 20, 1.5f);
        reloadAnimation_thrower = new Animation(new TextureRegion(new Texture("android/assets/hero/heroreload_firegun_atlas.png"), 6440, 217), 20, 1.5f);
        moveAnimation_pistol = new Animation(new TextureRegion(new Texture("android/assets/hero/heromove_pistol.png"), 5160, 220), 20, 3);

        zombiemoveAnimation = new Animation(new TextureRegion(new Texture("android/assets/zombie/zombiemove_atlas.png"), 256, 64), 4, 0.5f);
        zombiespawnAnimation = new Animation(new TextureRegion(new Texture("android/assets/zombie/zombiespawn_atlas.png"), 128, 64), 2, 1f);
        zombieattackAnimation = new Animation(new TextureRegion(new Texture("android/assets/zombie/zombieattack_atlas.png"), 128, 64), 2, 0.5f);

        zombierangemoveAnimation = new Animation(new TextureRegion(new Texture("android/assets/rangezombie/zombiemove_range_atlas.png"), 256, 64), 4, 0.5f);
        zombierangespawnAnimation = new Animation(new TextureRegion(new Texture("android/assets/rangezombie/zombiespawn_range_atlas.png"), 128, 64), 2, 1f);
        zombierangeattackAnimation = new Animation(new TextureRegion(new Texture("android/assets/rangezombie/zombieattack_range_atlas.png"), 128, 64), 2, 1f);

        zombiedoctormoveAnimation = new Animation(new TextureRegion(new Texture("android/assets/doctorzombie/zombiemove_doctor.png"), 256, 64), 4, 0.5f);
        zombiedoctorspawnAnimation = new Animation(new TextureRegion(new Texture("android/assets/doctorzombie/zombiespawn_doctor.png"), 128, 64), 2, 1f);
        zombiedoctorattackAnimation = new Animation(new TextureRegion(new Texture("android/assets/doctorzombie/zombieattack_doctor.png"), 128, 64), 2, 1f);

        zombiepyromoveAnimation = new Animation(new TextureRegion(new Texture("android/assets/pyrozombie/zombiemove_pyro.png"), 256, 64), 4, 0.5f);
        zombiepyrospawnAnimation = new Animation(new TextureRegion(new Texture("android/assets/pyrozombie/zombiespawn_pyro.png"), 128, 64), 2, 1f);
        zombiepyroattackAnimation = new Animation(new TextureRegion(new Texture("android/assets/pyrozombie/zombieattack_pyro.png"), 128, 64), 2, 1f);

        zombiepudgemoveAnimation = new Animation(new TextureRegion(new Texture("android/assets/pudge/zombiemove_pudge.png"), 256, 64), 4, 0.5f);
        zombiepudgespawnAnimation = new Animation(new TextureRegion(new Texture("android/assets/pudge/zombiespawn_pudge.png"), 128, 64), 2, 1f);
        zombiepudgeattackAnimation = new Animation(new TextureRegion(new Texture("android/assets/pudge/zombieattack_pudge.png"), 128, 64), 2, 1f);

        zombiehulkmoveAnimation = new Animation(new TextureRegion(new Texture("android/assets/hulk/zombiemove_hulk.png"), 256, 64), 4, 0.5f);
        zombiehulkspawnAnimation = new Animation(new TextureRegion(new Texture("android/assets/hulk/zombiespawn_hulk.png"), 128, 64), 2, 1f);
        zombiehulkattackAnimation = new Animation(new TextureRegion(new Texture("android/assets/hulk/zombieattack_hulk.png"), 128, 64), 2, 1f);

        zombiewitchmoveAnimation = new Animation(new TextureRegion(new Texture("android/assets/witch/zombiemove_witch.png"), 256, 64), 4, 0.5f);
        zombiewitchspawnAnimation = new Animation(new TextureRegion(new Texture("android/assets/witch/zombiespawn_witch.png"), 128, 64), 2, 1f);
        zombiewitchattackAnimation = new Animation(new TextureRegion(new Texture("android/assets/witch/zombieattack_witch.png"), 128, 64), 2, 1f);

        flameAnimation = new Animation((new TextureRegion(new Texture("android/assets/fire_animation.png"), 1200, 62)), 12, 1f);

        southborder = new ArenaBorders((new Texture("android/assets/arenaborders.png")), 0, 0, 1145, 0, physicsWorld, BorderType.HORIZONTAL);
        northborder = new ArenaBorders((new Texture("android/assets/arenaborders.png")), 0, 616, 1145, 616, physicsWorld, BorderType.HORIZONTAL);
        westborder = new ArenaBorders((new Texture("android/assets/arenaborders.png")), 0, 0, 0, 614, physicsWorld, BorderType.VERTICAL);
        eastborder = new ArenaBorders((new Texture("android/assets/arenaborders.png")), 1145, 0, 1145, 616, physicsWorld, BorderType.VERTICAL);
        int rndtemp = (int)Math.random()* 3;
        switch (rndtemp)
        {
            case 0: background = new ImageActor(new Texture("android/assets/background5.jpg"),0,0);
                break;
            case 1: background = new ImageActor(new Texture("android/assets/background6.jpg"),0,0);
                break;
            default: background = new ImageActor(new Texture("android/assets/background6.jpg"),0,0);
        }

       // worldBorders = new Rectangle(0, 0, this.getWidth(), this.getHeight());
        addActor(background);
     //   player = new Player(new Texture("android/assets/hero.png"), Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2, 100, 60, 60, 70); //50/30 default w/h
        player = new Player(new Texture("android/assets/hero.png"), Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2, 100, 60, 60, 70, physicsWorld); //50/30 default w/h
       hPcircle = new HPcircle(new Texture("android/assets/100.png"), 0, 0, 70, 70, this);
       /// / arenaBorders = new ArenaBorders(new Texture("android/assets/arenaborders.png"), 0, 0, 1145, 616, physicsWorld);
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
        spawnbot(Gdx.graphics.getDeltaTime());


        //bots.put(1, bot);
        //bots.put(2, bot1);
       // bots[] = new Bot[1];
      //  bots[0] = bot;
      //  bots[1] = bot1;
        //bot.setOrigin(bot.getWidth() / 2, bot.getHeight() /2);
        addActor(hPcircle);
        addActor(player);
        addActor(player.getShootingPoint());
        addActor(northborder);
        addActor(southborder);
        addActor(eastborder);
        addActor(westborder);
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
        //return 616;
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
    public Animation getMoveAnimation_thrower()
    {
        return moveAnimation_thrower;
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

    public Animation getZombieRangeMoveAnimation()
    {
        return zombierangemoveAnimation;
    }
    public Animation getZombieRangeAttackAnimation()
    {
        return zombierangeattackAnimation;
    }
    public Animation getZombieRangeSpawnAnimation()
    {
        return zombierangespawnAnimation;
    }

    public Animation getZombieDoctorMoveAnimation()
    {
        return zombiedoctormoveAnimation;
    }
    public Animation getZombieDoctorAttackAnimation()
    {
        return zombiedoctorattackAnimation;
    }
    public Animation getZombieDoctorSpawnAnimation()
    {
        return zombiedoctorspawnAnimation;
    }

    public Animation getZombiePyroMoveAnimation()
    {
        return zombiepyromoveAnimation;
    }
    public Animation getZombiePyroAttackAnimation()
    {
        return zombiepyroattackAnimation;
    }
    public Animation getZombiePyroSpawnAnimation()
    {
        return zombiepyrospawnAnimation;
    }

    public Animation getZombiePudgeMoveAnimation()
    {
        return zombiepudgemoveAnimation;
    }
    public Animation getZombiePudgeAttackAnimation()
    {
        return zombiepudgeattackAnimation;
    }
    public Animation getZombiePudgeSpawnAnimation()
    {
        return zombiepudgespawnAnimation;
    }

    public Animation getZombieHulkMoveAnimation()
    {
        return zombiehulkmoveAnimation;
    }
    public Animation getZombieHulkAttackAnimation()
    {
        return zombiehulkattackAnimation;
    }
    public Animation getZombieHulkSpawnAnimation()
    {
        return zombiehulkspawnAnimation;
    }

    public Animation getZombieWitchMoveAnimation()
    {
        return zombiewitchmoveAnimation;
    }
    public Animation getZombieWitchAttackAnimation()
    {
        return zombiewitchattackAnimation;
    }
    public Animation getZombieWitchSpawnAnimation()
    {
        return zombiewitchspawnAnimation;
    }

    public Animation getFlameAnimation()
    {
        return flameAnimation;
    }

    public Animation getReloadAnimation()
    {
        switch (getPlayer().getCurrentWeapon()) {
            case ASSAULTRIFLE:
            {return reloadAnimation;}
            case ICERIFLE:
            {return reloadAnimation_icegun ;}
            case FLAMETHROWER:
            {return reloadAnimation_thrower;}
            default:
                return reloadAnimation;
        }
    }

    public void playerUpdate(Animation animation, float delta)
    {
        player.setTexture(animation.getFrame());
    }

    public void botUpdate(Bot bot, float delta){bot.setTexture(zombiemoveAnimation.getFrame());};

    public void botsUpdate(int i, float delta, ObjectState objectState)
    {
        switch(bots.get(i-1).getBotType()) {
            case ZOMBIE:
                    switch (objectState) {
                case MOVING:
                    bots.get(i - 1).setTexture(zombiemoveAnimation.getFrame());
                    break;

                case SPAWNING:
                    if (zombiespawnAnimation.isFinishedOnce() != true) {
                        bots.get(i - 1).setTexture(zombiespawnAnimation.getFrame());
                    } else {
                        bots.get(i - 1).setCurrentState(ObjectState.MOVING);
                    }
                    break;

                case ATTACKING:
                    bots.get(i - 1).setTexture(zombieattackAnimation.getFrame());
                    break;
                        case FREEZED: break;
                default:
                    bots.get(i - 1).setTexture(zombiemoveAnimation.getFrame()); break;
            }
                break;
            case ZOMBIE_RANGE:
                switch (objectState) {
                    case MOVING:
                        bots.get(i - 1).setTexture(zombierangemoveAnimation.getFrame());
                        break;

                    case SPAWNING:
                        if (zombierangespawnAnimation.isFinishedOnce() != true) {
                            bots.get(i - 1).setTexture(zombierangespawnAnimation.getFrame());
                        } else {
                            bots.get(i - 1).setCurrentState(ObjectState.MOVING);
                        }
                        break;

                    case ATTACKING:
                        bots.get(i - 1).setTexture(zombierangeattackAnimation.getFrame());
                        break;
                    case FREEZED: break;

                    default:
                        bots.get(i - 1).setTexture(zombierangemoveAnimation.getFrame()); break;
                }
                break;
            case DOCTOR:
                switch (objectState) {
                    case MOVING:
                        bots.get(i - 1).setTexture(zombiedoctormoveAnimation.getFrame());
                        break;

                    case SPAWNING:
                        if (zombiedoctorspawnAnimation.isFinishedOnce() != true) {
                            bots.get(i - 1).setTexture(zombiedoctorspawnAnimation.getFrame());
                        } else {
                            bots.get(i - 1).setCurrentState(ObjectState.MOVING);
                        }
                        break;
                    case FREEZED: break;

                    case ATTACKING:
                        bots.get(i - 1).setTexture(zombiedoctorattackAnimation.getFrame());
                        break;

                    default:
                        bots.get(i - 1).setTexture(zombiedoctormoveAnimation.getFrame()); break;
                }
                break;
            case PYRO:
                switch (objectState) {
                    case MOVING:
                        bots.get(i - 1).setTexture(zombiepyromoveAnimation.getFrame());
                        break;

                    case SPAWNING:
                        if (zombiepyrospawnAnimation.isFinishedOnce() != true) {
                            bots.get(i - 1).setTexture(zombiepyrospawnAnimation.getFrame());
                        } else {
                            bots.get(i - 1).setCurrentState(ObjectState.MOVING);
                        }
                        break;
                    case FREEZED: break;

                    case ATTACKING:
                        bots.get(i - 1).setTexture(zombiepyroattackAnimation.getFrame());
                        break;

                    default:
                        bots.get(i - 1).setTexture(zombiepyromoveAnimation.getFrame()); break;
                }
                break;
            case PUDGE:
                switch (objectState) {
                    case MOVING:
                        bots.get(i - 1).setTexture(zombiepudgemoveAnimation.getFrame());
                        break;

                    case SPAWNING:
                        if (zombiepudgespawnAnimation.isFinishedOnce() != true) {
                            bots.get(i - 1).setTexture(zombiepudgespawnAnimation.getFrame());
                        } else {
                            bots.get(i - 1).setCurrentState(ObjectState.MOVING);
                        }
                        break;
                    case FREEZED: break;

                    case ATTACKING:
                        bots.get(i - 1).setTexture(zombiepudgeattackAnimation.getFrame());
                        break;

                    default:
                        bots.get(i - 1).setTexture(zombiepudgemoveAnimation.getFrame()); break;
                }
                break;
            case HULK:
                switch (objectState) {
                    case MOVING:
                        bots.get(i - 1).setTexture(zombiehulkmoveAnimation.getFrame());
                        break;

                    case SPAWNING:
                        if (zombiehulkspawnAnimation.isFinishedOnce() != true) {
                            bots.get(i - 1).setTexture(zombiehulkspawnAnimation.getFrame());
                        } else {
                            bots.get(i - 1).setCurrentState(ObjectState.MOVING);
                        }
                        break;
                    case FREEZED: break;

                    case ATTACKING:
                        bots.get(i - 1).setTexture(zombiehulkattackAnimation.getFrame());
                        break;

                    default:
                        bots.get(i - 1).setTexture(zombiehulkmoveAnimation.getFrame()); break;
                }
                break;
            case WITCH:
                switch (objectState) {
                    case MOVING:
                        bots.get(i - 1).setTexture(zombiewitchmoveAnimation.getFrame());
                        break;

                    case SPAWNING:
                        if (zombiewitchspawnAnimation.isFinishedOnce() != true) {
                            bots.get(i - 1).setTexture(zombiewitchspawnAnimation.getFrame());
                        } else {
                            bots.get(i - 1).setCurrentState(ObjectState.MOVING);
                        }
                        break;
                    case FREEZED: break;

                    case ATTACKING:
                        bots.get(i - 1).setTexture(zombiewitchattackAnimation.getFrame());
                        break;

                    default:
                        bots.get(i - 1).setTexture(zombiewitchmoveAnimation.getFrame()); break;
                }
                break;
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


    public HPcircle getHPcircle() {
        return hPcircle;
    }
    public Animation getReloadAnimation_icegun() {
        return reloadAnimation_icegun;
    }
    public Animation getReloadAnimation_thrower(){return reloadAnimation_thrower;}




    public void spawnbot(float delta)
    {
        randomzombietype = (int)(Math.random() * 6 + 1);
        switch (randomzombietype) {
            case 1: {
                bot = new Bot(zombiespawnAnimation.getFrame(), (float) Math.random() * 1145, (float) Math.random() * 616, 64, 64, 60, 70, physicsWorld, BotType.ZOMBIE, this);
                // beforespawn += delta;
                //if(beforespawn<=spawntimer)
                //bot.setCurrentState(ObjectState.SPAWNING);
                // else{bot.setCurrentState(ObjectState.MOVING); beforespawn=0;}
                zombiespawnAnimation.setFinishedOnce(false);
                //if(zombiespawnAnimation.isFinishedOnce())
                //  bot.setCurrentState(ObjectState.MOVING);
                //botArray.add(new Bot(zombiemoveAnimation.getFrame(), (float) Math.random() * 1145, (float) Math.random() * 616, 64, 64, 60, 70, physicsWorld));
                botArray.add(bot);
                //botsArray[botscurrentcount] = new Bot(zombiemoveAnimation.getFrame(), (float)Math.random()*1145, (float)Math.random()*616, 64, 64, 60, 70, physicsWorld);
                bots.put(botscurrentcount, botArray.get(botscurrentcount));
                // bots.put(botscurrentcount, botsArray[botscurrentcount]);
                addActor(botArray.get(botscurrentcount));
                //addActor(botsArray[botscurrentcount]);
                botscurrentcount++;
                botscount++;
                break;
            }
            // botArray.size++;
            case 2:
                bot = new Bot(zombierangespawnAnimation.getFrame(), (float) (Math.random() * 1100 + 15), (float) (Math.random() * 590 + 15), 64, 64, 60, 70, physicsWorld, BotType.ZOMBIE_RANGE, this);
                zombierangespawnAnimation.setFinishedOnce(false);
                botArray.add(bot);
                bots.put(botscurrentcount, botArray.get(botscurrentcount));
                addActor(botArray.get(botscurrentcount));
                botscurrentcount++;
                botscount++;
                break;
            case 3:
                bot = new Bot(zombiedoctorspawnAnimation.getFrame(), (float) (Math.random() * 1100 + 15), (float) (Math.random() * 590 + 15), 64, 64, 60, 70, physicsWorld, BotType.DOCTOR, this);
                zombiedoctorspawnAnimation.setFinishedOnce(false);
                botArray.add(bot);
                bots.put(botscurrentcount, botArray.get(botscurrentcount));
                addActor(botArray.get(botscurrentcount));
                botscurrentcount++;
                botscount++;
                break;
            case 4:
                bot = new Bot(zombiepyrospawnAnimation.getFrame(), (float) (Math.random() * 1100 + 15), (float) (Math.random() * 590 + 15), 64, 64, 60, 70, physicsWorld, BotType.PYRO, this);
                zombiepyrospawnAnimation.setFinishedOnce(false);
                botArray.add(bot);
                bots.put(botscurrentcount, botArray.get(botscurrentcount));
                addActor(botArray.get(botscurrentcount));
                botscurrentcount++;
                botscount++;
            case 5:
                bot = new Bot(zombiepudgespawnAnimation.getFrame(), (float) (Math.random() * 1100 + 15), (float) (Math.random() * 590 + 15), 64, 64, 60, 70, physicsWorld, BotType.PUDGE, this);
                zombiepudgespawnAnimation.setFinishedOnce(false);
                botArray.add(bot);
                bots.put(botscurrentcount, botArray.get(botscurrentcount));
                addActor(botArray.get(botscurrentcount));
                botscurrentcount++;
                botscount++;
            case 6:
                bot = new Bot(zombiehulkspawnAnimation.getFrame(), (float) (Math.random() * 1100 + 15), (float) (Math.random() * 590 + 15), 64, 64, 60, 70, physicsWorld, BotType.HULK, this);
                zombiehulkspawnAnimation.setFinishedOnce(false);
                botArray.add(bot);
                bots.put(botscurrentcount, botArray.get(botscurrentcount));
                addActor(botArray.get(botscurrentcount));
                botscurrentcount++;
                botscount++;
            case 7:
                bot = new Bot(zombiewitchspawnAnimation.getFrame(), (float) (Math.random() * 1100 + 15), (float) (Math.random() * 590 + 15), 64, 64, 60, 70, physicsWorld, BotType.WITCH, this);
                zombiewitchspawnAnimation.setFinishedOnce(false);
                botArray.add(bot);
                bots.put(botscurrentcount, botArray.get(botscurrentcount));
                addActor(botArray.get(botscurrentcount));
                botscurrentcount++;
                botscount++;
            default:
                bot = new Bot(zombierangespawnAnimation.getFrame(), (float) (Math.random() * 1100 + 15), (float) (Math.random() * 590 + 15), 64, 64, 60, 70, physicsWorld, BotType.ZOMBIE_RANGE, this);
                zombierangespawnAnimation.setFinishedOnce(false);
                botArray.add(bot);
                bots.put(botscurrentcount, botArray.get(botscurrentcount));
                addActor(botArray.get(botscurrentcount));
                botscurrentcount++;
                botscount++;
                break;
        }

    }
}
