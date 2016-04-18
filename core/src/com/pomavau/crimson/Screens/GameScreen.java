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
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.pomavau.crimson.Controller.Animation;
import com.pomavau.crimson.Controller.CameraController;
import com.pomavau.crimson.Controller.Direction;
import com.pomavau.crimson.Controller.PlayerController;
import com.pomavau.crimson.Controller.BotController;
import com.pomavau.crimson.Controller.ShowMenu;
import com.pomavau.crimson.Model.LevelWorld;
import com.pomavau.crimson.Model.PhWorld;
import com.pomavau.crimson.Model.Player;
import com.pomavau.crimson.Model.World;
import com.pomavau.crimson.View.ImageActor;
import com.pomavau.crimson.crimsonTD;
//import com.sun.javafx.scene.control.skin.TableHeaderRow;

import java.awt.Image;
import java.io.FileNotFoundException;
import java.util.EventListener;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

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
    private Drawable touchBackground;
    private Drawable touchKnob;

    private ImageActor bullet;
    private int bulletSpeed = 300;
    Array<ImageActor> bullets;
    long shotTime;
    private int currentBot = 1;

    final float WIDTH = Gdx.graphics.getWidth();
    final float HEIGHT = Gdx.graphics.getHeight();

    private int bulletsLeft;
    private int bulletsCountDefault = 30;
    private boolean isReloading = false;
    private Group bulletsCounter;
    private ImageActor bulletsCounterBG;
    private ImageActor[] bulletsCounterCounters;
    private int lastBulletY = 0;

    LevelWorld world;
    PhWorld phworld;
    PlayerController playerController;
    CameraController cameraController;
    BotController botController;
    InputMultiplexer multiplexer;

    protected Label FPSlabel;
    private BitmapFont font;

    HashMap<Integer, TextureRegion> textureRegions;


    public GameScreen(SpriteBatch batch, ShapeRenderer shape, BitmapFont font, HashMap<Integer, TextureRegion> textureRegions) throws FileNotFoundException {
        debugRenderer = new Box2DDebugRenderer();
        phworld = new PhWorld();
        animation = new Animation(new TextureRegion(new Texture("android/assets/hero/heromove_atlas.png"), 1565, 824), 5, 3);
        bullets = new Array<ImageActor>();
        font = new BitmapFont();
        camera = new OrthographicCamera();
        this.textureRegions = textureRegions;
        TextureRegion region;
        region = textureRegions.get(0);
        bulletsLeft = 30;

        world = new LevelWorld(new ScreenViewport(camera), batch);

        pauseButton = new ImageActor(new Texture("android/assets/gamescreen_btnPause.png"), 0, world.getHeight(), 50, 50);
        pauseBG = new ImageActor(new Texture("android//assets//Menu.png"), 384, 616 - 534);
        pauseScreen = new Group();
        pauseScreen.setVisible(false);
        pauseScreen.addActor(pauseBG);
        pauseButton.addListener(new ShowMenu(pauseScreen));

        float aspectRatio = (float) Gdx.graphics.getWidth() / (float) Gdx.graphics.getHeight(); //camera viewport set
        camera.setToOrtho(false, 10f * aspectRatio, 10f);

        playerController = new PlayerController(world);
        cameraController = new CameraController(world);
        botController = new BotController(world);
        multiplexer = new InputMultiplexer();
        world.getBot().setController(botController);
        world.getBot1().setController(botController);

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
        bullet = new ImageActor(new Texture("android/assets/bullet.png"), world.getPlayer().getX(), world.getPlayer().getY(), 14, 3);
       // Player player = world.getPlayer();
        bullet.setPosition(world.getPlayer().getX() + (float) world.getPlayer().getWidth() * (float) Math.cos(world.getPlayer().getRotation() / 180 * Math.PI),
                world.getPlayer().getY() + (float) world.getPlayer().getWidth() * (float) Math.sin(world.getPlayer().getRotation() / 180 * Math.PI));
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

            bulletsLeft = bulletsCountDefault;
            isReloading = true;
        for(int i = 0; i < bulletsCounterCounters.length - 1; i++)
        {
           bulletsCounterCounters[i].setVisible(true);
        }
    }


    @Override
    public void render(float delta) {

        debugRenderer.render(world.getPhysicsWorld(), UIcamera.combined);
        currentBot++;
            if(currentBot > 9)
                currentBot = 1;


            //System.out.println(isObjectTouched(bulletsCounter) + " " + Gdx.input.getX() + " " + Gdx.input.getY() + " " + Gdx.input.isTouched());
       // if (crimsonTD.getInstance().getMenuVisibility(pauseScreen) == true)
          //  System.out.println("paused");
        if (crimsonTD.getInstance().getMenuVisibility(pauseScreen) != true) {
            playerController.update(world);
            cameraController.update(world);
            botController.update(world);
        }
        else
        {

        }
            //System.out.format("isBlockedX: %b isBlockedY: %b \r\n", world.getPlayer().getBlockedX(), world.getPlayer().getBlockedY());
            //System.out.format("bot RD: %s bot MD: %s\r\n", world.getBot().getRotationDirection(), world.getBot().getMovementDirection()) ;
              //  System.out.println(world.getPlayer().getViewDirection());
        //animation.update(delta);
       if(Gdx.input.isKeyPressed(Input.Keys.R)) {
          // while(world.getReloadAnimation().getCurrentFrame() != world.getReloadAnimation().getFrameCount()) {
               //world.getReloadAnimation().update(delta);
               reload();
                if (isReloading)
           {
               world.playerUpdate(world.getReloadAnimation(), delta);
              // isReloading = false;
               if(world.getReloadAnimation().isFinishedOnce() == true)
                   isReloading = false;
           }
          // }
       }
        world.getReloadAnimation().update(delta);
        world.getMoveAnimation().update(delta);
        world.getZombieMoveAnimation().update(delta);
/*
        if(isReloading)
        {
            world.playerUpdate(world.getReloadAnimation(), delta);
       }
       else
        {*/
           if(isReloading = false)
            world.playerUpdate(world.getMoveAnimation(), delta);
      //  }
       // world.botUpdate(world.getBot(), delta);
        world.botsUpdate(currentBot, delta);

        StringBuilder builder = new StringBuilder();
        builder.append(" FPS: ").append(Gdx.graphics.getFramesPerSecond()); //fps label
        FPSlabel.setText(builder);
        UIcamera.update();
        camera.update();
        Gdx.gl.glClearColor(0.294f, 0.294f, 0.294f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();
        // hero.setX(hero.getX() + touchpadLeft.getKnobPercentX() * heroSpeed);
        // hero.setY(hero.getY() + touchpadLeft.getKnobPercentY() * heroSpeed);
        if (world.getPlayer().getBlockedX() != true)
        world.getPlayer().setX(world.getPlayer().getX() + touchpadLeft.getKnobPercentX() * world.getPlayer().getMovementStep() / 50);
        if (world.getPlayer().getBlockedY() != true)
        world.getPlayer().setY(world.getPlayer().getY() + touchpadLeft.getKnobPercentY() * world.getPlayer().getMovementStep() / 50);
        //world.getPlayer().moveBy((world.getPlayer().getMovementStep() * delta * (float) Math.cos(world.getPlayer().getRotation() / 180 * Math.PI)), world.getPlayer().getMovementStep() * delta * (float) Math.sin(world.getPlayer().getRotation() / 180 * Math.PI) * touchpadLeft.getKnobPercentX());
        //System.out.println("X:" + hero.getX() + " Y:" + hero.getY()); //debug
        // hero.setOrigin(hero.getWidth() / 2 - hero.getWidth() / 4 - hero.getWidth() / 8, hero.getHeight() / 2 - hero.getHeight() / 4);
        //hero.rotateBy(touchpadRight.getKnobPercentX() * -3);


        world.getPlayer().rotateBy(touchpadRight.getKnobPercentX() * -5);

        if (touchpadLeft.getKnobPercentX() > 0 && touchpadLeft.getKnobPercentY() > 0)
            {

                world.getPlayer().setMovementDirection(Direction.FORWARD);
            }
        else
        {
            if(touchpadLeft.getKnobPercentX() != 0 && touchpadLeft.getKnobPercentY() != 0)
            world.getPlayer().setMovementDirection(Direction.BACKWARD);

        }
        if (touchpadLeft.getKnobPercentX() < 0 && touchpadLeft.getKnobPercentY() < 0)
        {
            world.getPlayer().setMovementDirection(Direction.BACKWARD);
        }
        else {
            if(touchpadLeft.getKnobPercentX() != 0 && touchpadLeft.getKnobPercentY() != 0)
            world.getPlayer().setMovementDirection(Direction.FORWARD);
        }

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
            bullets.get(i).moveBy(bulletSpeed * delta * (float) Math.cos(bullets.get(i).getRotation() / 180 * Math.PI), bulletSpeed * delta * (float) Math.sin(bullets.get(i).getRotation() / 180 * Math.PI));
            if (bullets.get(i).getY() >= 750) {
                bullets.get(i).remove();
                bullets.removeIndex(i);
            }
        }

            if(Math.abs(world.getBot().getX() - world.getPlayer().getX()) < 10) {
              //  world.getBot().setRotationStep(0);
            }
        else
            {

            }

        //hero.setSize(152, 100);
        //stage.act(Gdx.graphics.getDeltaTime());
        //stage.draw();
        world.act(Gdx.graphics.getDeltaTime());
        world.draw();
        UIstage.act(Gdx.graphics.getDeltaTime());
        UIstage.draw();
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


}

