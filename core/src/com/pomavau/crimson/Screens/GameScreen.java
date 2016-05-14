package com.pomavau.crimson.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.pomavau.crimson.Controller.Animation;
import com.pomavau.crimson.Controller.CameraController;
import com.pomavau.crimson.Controller.ObjectState;
import com.pomavau.crimson.Controller.PlayerController;
import com.pomavau.crimson.Controller.BotController;
import com.pomavau.crimson.Controller.ShowMenu;
import com.pomavau.crimson.Model.Bullet;
import com.pomavau.crimson.Model.LevelWorld;
import com.pomavau.crimson.Model.PhWorld;
import com.pomavau.crimson.View.ImageActor;
import com.pomavau.crimson.crimsonTD;
//import com.sun.javafx.scene.control.skin.TableHeaderRow;

import java.io.FileNotFoundException;
import java.util.HashMap;

//import javafx.animation.Animation;

/**
 * Created by Pomavau on 18.01.2016.
 */
public class GameScreen implements Screen {
    public SpriteBatch batch;
    private Box2DDebugRenderer debugRenderer;
    private Stage stage;
    private Stage UIstage;

    private OrthographicCamera UIcamera;
    private OrthographicCamera camera;

    private Animation animation;

    private Touchpad touchpadLeft, touchpadRight;
    private Touchpad.TouchpadStyle touchpadStyle;
    private Skin touchpadSkin;
    private ImageActor pauseButton;
    private ImageActor pauseBG;
    private Group pauseScreen;

    private ImageActor inventoryButton;
    private ImageActor inventoryBG;
    private ImageActor inventorySlot;
    private ImageActor m4a4hud;
    private ImageActor icegunhud;
    private ImageActor firegunhud;
    private Group inventoryScreen;

    private Drawable touchBackground;
    private Drawable touchKnob;

    private Bullet bullet;
    private int bulletSpeed = 300;
    Array<Bullet> bullets;
    long shotTime;
    private int currentBot = 2;

    final float WIDTH = Gdx.graphics.getWidth();
    final float HEIGHT = Gdx.graphics.getHeight();

    private int bulletsLeft;
    private int bulletsCountDefault = 30;
    private boolean isReloading = false;
    private Group bulletsCounter;
    private ImageActor bulletsCounterBG;
    private ImageActor[] bulletsCounterCounters;
    private int lastBulletY = 0;
    private int bulletsShooted = 0;

    private long currenttime = 0;
    private long deltatime;

    private long currenttimeSec = (long)(currenttime / 1E9);
    private long deltatimeSec;
    private boolean timerIsOn = false;

    private float spawntime = 1;
    private float nextspawn = 0;


    ClickListener clickListener = new ClickListener();



    LevelWorld world;
    PhWorld phworld;
    PlayerController playerController;
    CameraController cameraController;
    BotController botController;
    InputMultiplexer multiplexer;

    private Contact contact;

    protected Label FPSlabel;
    private BitmapFont font;

    HashMap<Integer, TextureRegion> textureRegions;


    public GameScreen(SpriteBatch batch, ShapeRenderer shape, BitmapFont font, HashMap<Integer, TextureRegion> textureRegions) throws FileNotFoundException {
        debugRenderer = new Box2DDebugRenderer();
        phworld = new PhWorld();
        animation = new Animation(new TextureRegion(new Texture("android/assets/hero/heromove_atlas.png"), 1565, 824), 5, 3);
        bullets = new Array<Bullet>();
        font = new BitmapFont();
        camera = new OrthographicCamera();
        this.textureRegions = textureRegions;
        TextureRegion region;
        region = textureRegions.get(0);
        bulletsLeft = 30;

        world = new LevelWorld(new ScreenViewport(camera), batch);

        pauseButton = new ImageActor(new Texture("android/assets/gamescreen_btnPause.png"), 0, world.getHeight(), 75, 75);
        pauseBG = new ImageActor(new Texture("android//assets//Menu2.png"), 384, 616 - 534);
        pauseScreen = new Group();
        pauseScreen.setVisible(false);
        pauseScreen.addActor(pauseBG);
        pauseButton.addListener(new ShowMenu(pauseScreen));

        //inventoryButton = new ImageActor(new Texture("android//assets//gamescreen_bulletsCount.png"), 325, (int)world.getHeight(), 75, 165);
        inventoryButton = new ImageActor(new Texture("android//assets//gamescreen_btnInventory.png"), 100, (int)world.getHeight());
        inventoryBG = new ImageActor(new Texture("android//assets//mainmenu//InventoryMenu.png"), 346, 616 - 534);
        inventorySlot = new ImageActor(new Texture("android//assets//mainmenu//InventorySlot.png"), 507, 616 - 282);
        m4a4hud = new ImageActor(new Texture("android//assets//mainmenu//m4a4_hud.png"),519, 616 - 266, 113, 75);
        icegunhud = new ImageActor(new Texture("android//assets//mainmenu//icegun_hud.png"),519, 616 - 266, 113, 75);

        inventoryScreen = new Group();
        inventoryScreen.setVisible(false);
        inventoryScreen.addActor(inventoryBG);
        inventoryScreen.addActor(m4a4hud);
        inventoryScreen.addActor(icegunhud);
        inventoryScreen.addActor(inventorySlot);

        inventoryButton.addListener(new ShowMenu(inventoryScreen));

        float aspectRatio = (float) Gdx.graphics.getWidth() / (float) Gdx.graphics.getHeight(); //camera viewport set
        camera.setToOrtho(false, 10f * aspectRatio, 10f);

        playerController = new PlayerController(world);
        cameraController = new CameraController(world);
        botController = new BotController(world);
        multiplexer = new InputMultiplexer();
//        world.getBot().setController(botController);
    //    world.getBot1().setController(botController);

        bulletsCounter = new Group();
        bulletsCounterBG = new ImageActor(new Texture("android//assets//gamescreen_bulletsCount.png"), 1145 - 117, (int)world.getHeight() /2);
        bulletsCounterCounters = new ImageActor[30];
        lastBulletY = (int)bulletsCounterBG.getY() + 25;
        bulletsCounter.addActor(bulletsCounterBG);
        for(int i = 0; i < bulletsCounterCounters.length - 1; i++)
        {
           // bulletsCounterCounters[i] = new ImageActor(new Texture("android//assets//bullet.png"), bulletsCounterBG.getWidth() / 2, bulletsCounterBG.getHeight() + 10);
            bulletsCounterCounters[i] = new ImageActor(new Texture("android//assets//bullet.png"), 1145 - bulletsCounterBG.getWidth() / 2, lastBulletY + 7, 25, 5);
            lastBulletY = (int)bulletsCounterCounters[i].getY();
            bulletsCounter.addActor(bulletsCounterCounters[i]);
        }

        bulletsCounterBG.toBack();

        batch = new SpriteBatch();
        // touchpads
        touchpadSkin = new Skin();
        touchpadSkin.add("touchBackground", new Texture("android//assets//joypad//touchBackgroundnew.png"));
        touchpadSkin.add("touchKnob", new Texture("android//assets//joypad//touchKnobnew.png"));
        touchpadStyle = new Touchpad.TouchpadStyle();
        touchBackground = touchpadSkin.getDrawable("touchBackground");
        touchKnob = touchpadSkin.getDrawable("touchKnob");
        touchpadStyle.background = touchBackground;
        touchpadStyle.knob = touchKnob;
        touchpadLeft = new Touchpad(10, touchpadStyle);
        touchpadLeft.setBounds(15, 15, 200, 200);
        touchpadRight = new Touchpad(10, touchpadStyle);
        //touchpadRight.setBounds(585, 15, 200, 200);
        touchpadRight.setBounds(Gdx.graphics.getWidth() - 215, 15, 200, 200);
        stage = new Stage(new ScreenViewport(camera), batch);
        UIcamera = new OrthographicCamera();
        UIcamera.setToOrtho(false, 10f * aspectRatio, 10f);
        FPSlabel = new Label(" ", new Label.LabelStyle(font, Color.WHITE));
        UIstage = new Stage(new ScreenViewport(UIcamera), batch);
        UIstage.addActor(FPSlabel);
        UIstage.addActor(touchpadLeft);
        UIstage.addActor(touchpadRight);
        UIstage.addActor(pauseButton);
        UIstage.addActor(pauseScreen);
        UIstage.addActor(bulletsCounter);
        UIstage.addActor(inventoryButton);
        UIstage.addActor(inventoryScreen);
        pauseButton.toFront();
        world.setBackgroundtoBack();
        //Multiplexer filling
        multiplexer.addProcessor(UIstage);
        multiplexer.addProcessor(world);
        multiplexer.addProcessor(playerController);
        multiplexer.addProcessor(cameraController);
        // multiplexer.addProcessor(botController);



    }

    private void shot() {

        // bullet = new ImageActor (new Texture("android/assets/bullet.png"), (int)(world.getPlayer().getX() + world.getPlayer().getWidth()*Math.cos((double)(world.getPlayer().getRotation()))),(int)(world.getPlayer().getY() + world.getPlayer().getHeight()*Math.sin((double)(world.getPlayer().getRotation()))), 14 , 3);
       // bullet = new ImageActor(new Texture("android/assets/bullet.png"), world.getPlayer().getX(), world.getPlayer().getY(), 14, 3);
        bullet = new Bullet(new Texture("android/assets/bullet.png"), world.getPlayer().getX(), world.getPlayer().getY(), 14f, 3f, world.getPhysicsWorld()); //DEFAULT : 14X3
        bulletsShooted++;
       // Player player = world.getPlayer();

        bullet.setPosition(world.getPlayer().getX() + (float) world.getPlayer().getWidth() * (float) Math.cos(world.getPlayer().getRotation() / 180 * Math.PI ) - 10,
                world.getPlayer().getY() + (float) world.getPlayer().getHeight() * (float) Math.sin(world.getPlayer().getRotation() / 180 * Math.PI ) + 10);
        //bullet.setPosition(world.getPlayer().getBody().getPosition().x + world.getPlayer().getWidth() / 2, world.getPlayer().getBody().getPosition().y);

        //bullet.setPosition(world.getPlayer().getPosition().x + world.getPlayer().getWidth() / 2 - 10, world.getPlayer().getPosition().y);
        //System.out.println(player.getRotation());
        // System.out.println(world.getPlayer().getRotation());
        world.addActor(bullet);
        bullets.add(bullet);
        shotTime = TimeUtils.nanoTime();
        if (Math.abs(world.getPlayer().getRotation() - world.getPlayer().getDestinationAngle()) < world.getPlayer().getRotationStep()) {
            bullet.setRotation(world.getPlayer().getDestinationAngle());
        }
        bullet.rotateBy(world.getPlayer().getRotationStep());
        bullet.setRotation(world.getPlayer().getRotation() % 360);
        if (Math.abs(world.getPlayer().getRotation() - world.getPlayer().getDestinationAngle()) < world.getPlayer().getRotationStep()) {
            bullet.setRotation(world.getPlayer().getDestinationAngle());
        }
        bullet.rotateBy(-world.getPlayer().getRotationStep());
        bullet.setRotation((360 + world.getPlayer().getRotation()) % 360);
        bulletsLeft--;
        if(bulletsLeft > 0)
        bulletsCounterCounters[bulletsLeft - 1].setVisible(false);
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(multiplexer);
        batch = new SpriteBatch();
    }

    public void reload()
    {

            //bulletsLeft = bulletsCountDefault;
            isReloading = true;
        if(world.getReloadAnimation().isFinishedOnce() == true) {
            for(int i = 0; i < bulletsCounterCounters.length - 1; i++)
            {
                bulletsCounterCounters[i].setVisible(true);
            }
            bulletsLeft = bulletsCountDefault;
            world.getReloadAnimation().setFinishedOnce(false);
        }

    }




    @Override
    public void render(float delta) {

        //Timer timer = new Timer();
        //timer.start();

        //SPAWNING BOTS
        /*if(!timerIsOn) {
            timerIsOn = true;*/
        nextspawn+=delta;
        if(nextspawn>=spawntime){
            nextspawn=0;
            crimsonTD.getInstance().spawnbots();
            //if(world.getZombieSpawnAnimation().getCurrentFrame() == 2)

        }
        isBotNearPlayer();
        botsAttacking();
        //System.out.println(nextspawn);
        /*
        Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                crimsonTD.getInstance().spawnbots();
                Timer.instance().clear();
            }
        }, 5);
*/

       // }
        world.getReloadAnimation().setFinishedOnce(true);
        //isBotNearPlayer();
        world.getPhysicsWorld().step(delta, 6, 2);
        currentBot++;
            if(currentBot > world.getBotscount() - 1)
                currentBot = 1;


            //System.out.println(isObjectTouched(bulletsCounter) + " " + Gdx.input.getX() + " " + Gdx.input.getY() + " " + Gdx.input.isTouched());
       // if (crimsonTD.getInstance().getMenuVisibility(pauseScreen) == true)
          //  System.out.println("paused");


            //System.out.format("isBlockedX: %b isBlockedY: %b \r\n", world.getPlayer().getBlockedX(), world.getPlayer().getBlockedY());
            //System.out.format("bot RD: %s bot MD: %s\r\n", world.getBot().getRotationDirection(), world.getBot().getMovementDirection()) ;
              //  System.out.println(world.getPlayer().getViewDirection());
        //animation.update(delta);



       if(Gdx.input.isKeyPressed(Input.Keys.R) && bulletsLeft < bulletsCountDefault) {
          // while(world.getReloadAnimation().getCurrentFrame() != world.getReloadAnimation().getFrameCount()) {
               //world.getReloadAnimation().update(delta);
               reload();
              //  if (isReloading)
         //  {
               //world.playerUpdate(world.getReloadAnimation(), delta);
              // isReloading = false;
               //if(world.getReloadAnimation().isFinishedOnce() == true)
                  // isReloading = false;
         //  }
          // }
       }
        //isReloading = false;
        System.out.println(world.getZombieSpawnAnimation().getCurrentFrame());
        world.getReloadAnimation().update(delta);
        world.getMoveAnimation().update(delta);
        world.getMoveAnimation_icegun().update(delta);
        world.getZombieMoveAnimation().update(delta);
        world.getZombieAttackAnimation().update(delta);
        world.getZombieSpawnAnimation().update(delta);

/*
        if(isReloading)
        {
            world.playerUpdate(world.getReloadAnimation(), delta);
       }
       else
        {*/
           //if(isReloading = false) {
        switch(world.getPlayer().getCurrentWeapon()) {
            case ASSAULTRIFLE: world.playerUpdate(world.getMoveAnimation(), delta); break;
            case ICERIFLE: world.playerUpdate(world.getMoveAnimation_icegun(), delta); break;
        }
          // }
      //  else
         //  {
          //     if (world.getReloadAnimation().isFinishedOnce() == false)
         //      world.playerUpdate(world.getReloadAnimation(), delta);
         //  }
      //  }

       // world.botUpdate(world.getBot(), delta);
        world.botsUpdate(currentBot, delta, world.getBotbyIndex(currentBot).getCurrentState());
        if (crimsonTD.getInstance().getMenuVisibility(pauseScreen) != true) {
            playerController.update(world);
            cameraController.update(world);
            botController.update(world);
        }
        StringBuilder builder = new StringBuilder();
        builder.append(" FPS: ").append(Gdx.graphics.getFramesPerSecond()); //fps label
        FPSlabel.setText(builder);
        UIcamera.update();
        camera.update();
        Gdx.gl.glClearColor(0.294f, 0.294f, 0.294f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();



        world.getPlayer().rotateBy(touchpadRight.getKnobPercentX() * -5);
        world.getPlayer().setBoxRotation((float)Math.toRadians(touchpadRight.getKnobPercentX() * -5));



        //CONTACTS
        //Array<Contact> contactList = world.getPhysicsWorld().getContactList();
        //for(int i = 0; i < contactList.size; i++) {
            //contact = contactList.get(i);
            //System.out.println("FIXTURE A USER DATA: " + contact.getFixtureA().getUserData());
            //System.out.println("FIXTURE B USER DATA: " + contact.getFixtureB().getUserData());
            //System.out.println(contact);
            /*
            if(contact != null) {
                if (contact.isTouching()
                        && contact.getFixtureA().getUserData().equals("bullet")
                        && contact.getFixtureB().getUserData().equals("bot")) {
                    Body body1 = null;
                    Body body2 = null;
                    if (contact.getFixtureA() != null && contact.getFixtureA().getUserData() != null && contact.getFixtureA().getUserData().equals("bullet"))
                        body1 = contact.getFixtureA().getBody();

                    if (contact.getFixtureB() != null && contact.getFixtureB().getUserData() != null && contact.getFixtureB().getUserData().equals("bot"))
                        body2 = contact.getFixtureB().getBody();

                    if (body1 != null) {

                        body1.setActive(false);
                        world.getPhysicsWorld().destroyBody(body1);
                    }

                }
            }
            */
        //}
/*
        if(world.getPlayer().getViewDirection() == Direction.FORWARD && getLeftTouchpadKnobY() > 0)
            world.getPlayer().setMovementDirection(Direction.FORWARD);                                      //THIS
        if(world.getPlayer().getViewDirection() == Direction.RIGHT && getLeftTouchpadKnobX() > 0)
            world.getPlayer().setMovementDirection(Direction.RIGHT);
*/


/*
        if (touchpadLeft.getKnobPercentX() > 0 && touchpadLeft.getKnobPercentY() > 0)
            {
                world.getPlayer().setState(ObjectState.MOVING);

                world.getPlayer().setMovementDirection(Direction.FORWARD);
            }
        else
        {
            if(touchpadLeft.getKnobPercentX() != 0 && touchpadLeft.getKnobPercentY() != 0) {
                world.getPlayer().setMovementDirection(Direction.BACKWARD);
                world.getPlayer().setState(ObjectState.MOVING);
            }

        }
        if (touchpadLeft.getKnobPercentX() < 0 && touchpadLeft.getKnobPercentY() < 0)
        {
            world.getPlayer().setMovementDirection(Direction.BACKWARD);
            world.getPlayer().setState(ObjectState.MOVING);
        }
        else {
            if(touchpadLeft.getKnobPercentX() != 0 && touchpadLeft.getKnobPercentY() != 0) {
                world.getPlayer().setState(ObjectState.MOVING);
                world.getPlayer().setMovementDirection(Direction.FORWARD);

            }
        }
*/
        switch (world.getPlayer().getCurrentWeapon())
        {
            case ASSAULTRIFLE:
                m4a4hud.setVisible(true);
                icegunhud.setVisible(false);
               // firegunhud.setVisible(false);
                break;
            case ICERIFLE: icegunhud.setVisible(true);
                m4a4hud.setVisible(false);
             //   firegunhud.setVisible(false);
                break;
        }

       // if (touchpadLeft.getKnobPercentX() == 0 && touchpadLeft.getKnobPercentY() == 0)
           // world.getPlayer().setState(ObjectState.STAYING);

          //  System.out.println(world.getPlayer().getMovementDirection());
        //SHOOTING
        if (Gdx.input.isKeyPressed(Input.Keys.Q) || (touchpadRight.getKnobPercentX() != 0) || touchpadRight.getKnobPercentY() != 0) { //System.out.println("Shooting..");
            if (TimeUtils.nanoTime() - shotTime > 100000000 && bulletsLeft != 0) {      // default: 200000000
                //System.out.println("Shooting..");
                shot();
            }
        }

        for (int i = 0; i < bullets.size; i++) {
            // System.out.println("Shooting..");
            //bullets.get(i).setY(bullets.get(i).getY() + 10);
            //System.out.println(bullets.get(i).getBox());
            bullets.get(i).moveBy(bulletSpeed * delta * (float) Math.cos(bullets.get(i).getRotation() / 180 * Math.PI), bulletSpeed * delta * (float) Math.sin(bullets.get(i).getRotation() / 180 * Math.PI));
            if (bullets.get(i).getY() >= 1000) {
                bullets.get(i).remove();
                bullets.removeIndex(i);
            }
        }
/*
            if(Math.abs(world.getBot().getX() - world.getPlayer().getX()) < 10) {
              //  world.getBot().setRotationStep(0);
            }
        else
            {

            }*/

        //hero.setSize(152, 100);
        //stage.act(Gdx.graphics.getDeltaTime());
        //stage.draw();
        world.act(Gdx.graphics.getDeltaTime());
        world.draw();
        UIstage.act(Gdx.graphics.getDeltaTime());
        UIstage.draw();
        batch.begin();
        debugRenderer.SHAPE_STATIC.set(new Color(Color.RED));
        debugRenderer.SHAPE_AWAKE.set(new Color(Color.RED));
        debugRenderer.SHAPE_NOT_ACTIVE.set(new Color(Color.RED));
        debugRenderer.SHAPE_NOT_AWAKE.set(new Color(Color.RED));
        debugRenderer.SHAPE_KINEMATIC.set(new Color(Color.RED));
        debugRenderer.VELOCITY_COLOR.set(new Color(Color.RED));
        //debugRenderer.render(world.getPhysicsWorld(), camera.combined);
        batch.end();
        camera.update();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }

    public boolean isObjectTouched(Actor actor)
    {
        if(Gdx.input.isTouched() && (Gdx.input.getX() > actor.getX() && Gdx.input.getX() < actor.getWidth() + actor.getX()) && (Gdx.input.getY() < actor.getY() && Gdx.input.getY() > actor.getY() + actor.getHeight()) )
        return true;
            return false;
    }

    public float getLeftTouchpadKnobX()
    {
        return touchpadLeft.getKnobPercentX();
    }
    public float getLeftTouchpadKnobY()
    {
        return touchpadLeft.getKnobPercentY();
    }

    public float getRightTouchpadKnobX()
    {
        return touchpadRight.getKnobPercentX();
    }
    public float getRightTouchpadKnobY()
    {
        return touchpadRight.getKnobPercentY();
    }

    public void isBotNearPlayer()
    {



        for(int i = 1; i < world.getBotscount(); i++)
       {
           //System.out.println((Math.abs(world.getBotbyIndex(i).getX() - world.getPlayer().getX()))+ " " + (Math.abs(world.getBotbyIndex(i).getY() - world.getPlayer().getY())));
           if((Math.abs(world.getBotbyIndex(i).getX() - world.getPlayer().getX()) < 70) && (Math.abs(world.getBotbyIndex(i).getY() - world.getPlayer().getY()) < 70) && world.getBotbyIndex(i).getCurrentState()!= ObjectState.DISABLED)
           {
               world.getBotbyIndex(i).setNearplayer(true);
           }
           else
           {
               world.getBotbyIndex(i).setNearplayer(false);
           }

           //System.out.println("BOT # " + i + " NEAR PLAYER?: "+world.getBotbyIndex(i).isNearPlayer());
       }

    }
    public LevelWorld getWorld() {
        return world;
    }

    public void botsAttacking()
    {
        for(int i = 1; i < world.getBotscount(); i++)
        {
            if(world.getBotbyIndex(i).getCurrentState() == ObjectState.ATTACKING)
            {
                world.getPlayer().setCurrentHP(world.getPlayer().getCurrentHP() - 10);
            }
        }
        System.out.println(world.getPlayer().getCurrentHP());
    }
}

