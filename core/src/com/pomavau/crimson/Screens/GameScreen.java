package com.pomavau.crimson.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.pomavau.crimson.Model.World;
import com.pomavau.crimson.View.Animator;
import com.pomavau.crimson.View.ImageActor;
import com.pomavau.crimson.crimsonTD;

import java.io.FileNotFoundException;
import java.util.HashMap;

//import javafx.animation.Animation;

/**
 * Created by Pomavau on 18.01.2016.
 */
public class GameScreen  implements Screen {
       //final crimsonTD gam;
        private Stage stage;
        private Stage UIstage;
        private OrthographicCamera UIcamera;
        private OrthographicCamera camera;
        public SpriteBatch batch;
        private Touchpad touchpadLeft, touchpadRight;
        private Touchpad.TouchpadStyle touchpadStyle;
        private Skin touchpadSkin;
        private Drawable touchBackground;
        private Drawable touchKnob;
        private Texture blockTexture;
        private Sprite blockSprite;
        private Sprite bullet;
        private ImageActor hero;
        private float heroSpeed;
        private Animator animator;

    protected Label FPSlabel;
    private BitmapFont font;
    //batch = new SpriteBatch();

    HashMap<Integer, TextureRegion> textureRegions;

World world;
    public GameScreen(SpriteBatch batch, ShapeRenderer shape, BitmapFont font, HashMap<Integer, TextureRegion> textureRegions) throws FileNotFoundException {

        font = new BitmapFont();
        camera = new OrthographicCamera();
        this.textureRegions = textureRegions;
        TextureRegion region;
        region = textureRegions.get(0);


        float aspectRatio = (float) Gdx.graphics.getWidth() / (float) Gdx.graphics.getHeight(); //camera viewport set
        camera.setToOrtho(false, 10f * aspectRatio, 10f);

        try {
            world = new World(new ScreenViewport(camera), batch, textureRegions);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        batch = new SpriteBatch();

        //choosing from atlas


        hero = new ImageActor(region, 400, 300);

                // touchpads
                touchpadSkin = new Skin();
                touchpadSkin.add("touchBackground", new Texture("android//assets//joypad//touchBackground.png"));
                touchpadSkin.add("touchKnob", new Texture("android//assets//joypad//touchKnob.png"));
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
              /*
               stage.addActor(touchpadLeft);
               stage.addActor(touchpadRight);
              */
                stage.addActor(hero);

                /*
                Gdx.input.setInputProcessor(UIstage);
                Gdx.input.setInputProcessor(stage);
                */

                blockTexture = new Texture(Gdx.files.internal("android//assets//hero.png"));
                heroSpeed = 3;


        UIcamera = new OrthographicCamera();
       UIcamera.setToOrtho(false, 10f * aspectRatio, 10f);

        FPSlabel = new Label(" ", new Label.LabelStyle(font, Color.WHITE));
        UIstage = new Stage(new ScreenViewport(UIcamera), batch);

        UIstage.addActor(FPSlabel);
        UIstage.addActor(touchpadLeft);
        UIstage.addActor(touchpadRight);
        }
        @Override
        public void show() {
            //Gdx.input.setInputProcessor(stage);   // input metod choose
            Gdx.input.setInputProcessor(UIstage);
            batch = new SpriteBatch();
          //  bullet = new Sprite(new Texture("android//assets//joypad//touchKnob.png"));
        }

       @Override
        public void render(float delta) {


           StringBuilder builder = new StringBuilder();
           builder.append(" FPS: ").append(Gdx.graphics.getFramesPerSecond()); //fps label
           FPSlabel.setText(builder);

           camera.position.set(hero.getX(), hero.getY(), 0);    //camera following

           UIcamera.update();
           camera.update();

           Gdx.gl.glClearColor(0.294f, 0.294f, 0.294f, 1f);
            Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
          //  Gdx.input.setInputProcessor(stage);
           if(Gdx.input.isKeyPressed(Input.Keys.W)) hero.setY(hero.getY() + 4);
           if(Gdx.input.isKeyPressed(Input.Keys.S)) hero.setY(hero.getY() - 4);
           if(Gdx.input.isKeyPressed(Input.Keys.D)) hero.setX(hero.getX() + 4);
           if(Gdx.input.isKeyPressed(Input.Keys.A)) hero.setX(hero.getX() - 4);
          // Gdx.input.setInputProcessor(UIstage);
           camera.update();

           hero.setX(hero.getX() + touchpadLeft.getKnobPercentX() * heroSpeed);
           hero.setY(hero.getY() + touchpadLeft.getKnobPercentY() * heroSpeed);
           System.out.println("X:" + hero.getX() + " Y:" + hero.getY()); //debug
           hero.setOrigin(hero.getWidth() / 2 - hero.getWidth() / 4 - hero.getWidth() / 8, hero.getHeight() / 2 - hero.getHeight() / 4);
          hero.rotateBy(touchpadRight.getKnobPercentX() * -3);
                hero.setSize(152, 100);



           stage.act(Gdx.graphics.getDeltaTime());
            stage.draw();
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

