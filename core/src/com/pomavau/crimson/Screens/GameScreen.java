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
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.pomavau.crimson.Controller.Animation;
import com.pomavau.crimson.Controller.CameraController;
import com.pomavau.crimson.Controller.PlayerController;
import com.pomavau.crimson.Controller.BotController;
import com.pomavau.crimson.Controller.ShowMenu;
import com.pomavau.crimson.Model.LevelWorld;
import com.pomavau.crimson.Model.Player;
import com.pomavau.crimson.Model.World;
import com.pomavau.crimson.View.ImageActor;
import com.pomavau.crimson.crimsonTD;
//import com.sun.javafx.scene.control.skin.TableHeaderRow;

import java.awt.Image;
import java.io.FileNotFoundException;
import java.util.HashMap;

//import javafx.animation.Animation;

/**
 * Created by Pomavau on 18.01.2016.
 */
public class GameScreen implements Screen {
    public SpriteBatch batch;

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

    final float WIDTH = Gdx.graphics.getWidth();
    final float HEIGHT = Gdx.graphics.getHeight();

    LevelWorld world;
    PlayerController playerController;
    CameraController cameraController;
    BotController botController;
    InputMultiplexer multiplexer;

    protected Label FPSlabel;
    private BitmapFont font;

    HashMap<Integer, TextureRegion> textureRegions;


    public GameScreen(SpriteBatch batch, ShapeRenderer shape, BitmapFont font, HashMap<Integer, TextureRegion> textureRegions) throws FileNotFoundException {
        animation = new Animation(new TextureRegion(new Texture("android/assets/hero/heromove_atlas.png"), 1565, 824), 5, 3);
        bullets = new Array<ImageActor>();
        font = new BitmapFont();
        camera = new OrthographicCamera();
        this.textureRegions = textureRegions;
        TextureRegion region;
        region = textureRegions.get(0);

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
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(multiplexer);
        batch = new SpriteBatch();
    }

    @Override
    public void render(float delta) {
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
            System.out.format("bot RD: %s bot MD: %s\r\n", world.getBot().getRotationDirection(), world.getBot().getMovementDirection()) ;

        animation.update(delta);

        world.getMoveAnimation().update(delta);
      //  world.playerUpdate();

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


        world.getPlayer().rotateBy(touchpadRight.getKnobPercentX() * -3);


        //SHOOTING
        if (Gdx.input.isKeyPressed(Input.Keys.Q) || (touchpadRight.getKnobPercentX() != 0) || touchpadRight.getKnobPercentY() != 0) { //System.out.println("Shooting..");
            if (TimeUtils.nanoTime() - shotTime > 100000000) {      // default: 200000000
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
}

