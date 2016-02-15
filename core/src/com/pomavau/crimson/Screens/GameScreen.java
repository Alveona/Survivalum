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
import com.pomavau.crimson.View.ImageActor;
import com.pomavau.crimson.crimsonTD;

import java.io.FileNotFoundException;
import java.util.HashMap;

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

    protected Label FPSlabel;
    private BitmapFont font;
    //batch = new SpriteBatch();

    HashMap<Integer, TextureRegion> textureRegions;

World world;
    public GameScreen(SpriteBatch batch, ShapeRenderer shape, BitmapFont font, HashMap<Integer, TextureRegion> textureRegions) throws FileNotFoundException {
       // crimsonTD.instance.transform.setToRotation(Vector3.Z, 90).translate(-5,0,0);
            /*
                OrthographicCamera camera = new OrthographicCamera();
            camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
            stage = new Stage(new ScreenViewport(camera), batch);
            */
        camera = new OrthographicCamera();
        float aspectRatio = (float) Gdx.graphics.getWidth() / (float) Gdx.graphics.getHeight();
        camera.setToOrtho(false, 10f * aspectRatio, 10f);

        try {
            world = new World(new ScreenViewport(camera), batch, textureRegions);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        batch = new SpriteBatch();
        this.textureRegions = textureRegions;
        TextureRegion region;
        ImageActor temp;

        region = textureRegions.get(0);
        hero = new ImageActor(region, 400, 300);


           //camera.setToOrtho(false, blockSprite.getX(), blockSprite.getY());
                touchpadSkin = new Skin();
                touchpadSkin.add("touchBackground", new Texture("android//assets//joypad//touchBackground.png"));
                touchpadSkin.add("touchKnob", new Texture("android//assets//joypad//touchKnob.png"));
                touchpadStyle = new Touchpad.TouchpadStyle();
                touchBackground = touchpadSkin.getDrawable("touchBackground");
                touchKnob = touchpadSkin.getDrawable("touchKnob");
                touchpadStyle.background = touchBackground;
                touchpadStyle.knob = touchKnob;
                touchpadLeft = new Touchpad(10, touchpadStyle);
                //touchpadLeft.setBounds(15, 15, 200, 200);
                 touchpadLeft.setBounds(15, 15, 200, 200);
                touchpadRight = new Touchpad(10, touchpadStyle);
                touchpadRight.setBounds(585, 15, 200, 200);
               // stage = new Stage(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), true, batch);
                stage = new Stage(new ScreenViewport(camera), batch);
               stage.addActor(touchpadLeft);
                stage.addActor(touchpadRight);
        stage.addActor(hero);
                //Gdx.input.setInputProcessor(UIstage);
                Gdx.input.setInputProcessor(stage);


                blockTexture = new Texture(Gdx.files.internal("android//assets//hero.png"));
        //blockTexture = new Imag
           // blockTexture = new Texture(100, 151 , Gdx.files.internal("android//assets//hero.png"));

           // blockSprite = new Sprite(blockTexture);
             //   blockSprite.setPosition(Gdx.graphics.getWidth() / 2 - blockSprite.getWidth() / 2, Gdx.graphics.getHeight() / 2 - blockSprite.getHeight() / 2);
            //camera.setToOrtho(false, blockSprite.getX(), blockSprite.getY());

            heroSpeed = 3;
        UIcamera = new OrthographicCamera();
       UIcamera.setToOrtho(false, 10f * aspectRatio, 10f);
        font = new BitmapFont();
        //stage.getCamera().
        FPSlabel = new Label(" ", new Label.LabelStyle(font, Color.WHITE));
       // stage.addActor(FPSlabel);
        UIstage = new Stage(new ScreenViewport(UIcamera), batch);
        UIstage.addActor(FPSlabel);
        //FPSlabel.setPosition(0, 0);
       //UIstage.addActor(touchpadLeft);
       //UIstage.addActor(touchpadRight);
       // UIstage.addActor(touchpadRight);
        }
        @Override
        public void show() {
            Gdx.input.setInputProcessor(stage);
           // Gdx.input.setInputProcessor(UIstage);
            batch = new SpriteBatch();
           // Group group = new  Group();
          //  group.addActor(touchpadLeft);
          //  group.addActor(touchpadRight);
            bullet = new Sprite(new Texture("android//assets//joypad//touchKnob.png"));
        }

       @Override
        public void render(float delta) {

           StringBuilder builder = new StringBuilder();
           builder.append(" FPS: ").append(Gdx.graphics.getFramesPerSecond());
            //touchpadLeft.setPosition(100,100);
           FPSlabel.setText(builder);
           // batch = new SpriteBatch();
           camera.position.set(hero.getX(), hero.getY(), 0);
           // touchpadLeft.setPosition(camera.)
           // camera.update();
           UIcamera.update();
           camera.update();
            //GameScreen.
            //Gdx.gl.glClearColor(0.5f, 1, 0, 1);
           Gdx.gl.glClearColor(0.294f, 0.294f, 0.294f, 1f);
            Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
          //  Gdx.input.setInputProcessor(stage);
           if(Gdx.input.isKeyPressed(Input.Keys.W)) hero.setY(hero.getY() + 4);
           if(Gdx.input.isKeyPressed(Input.Keys.S)) hero.setY(hero.getY() - 4);
           if(Gdx.input.isKeyPressed(Input.Keys.D)) hero.setX(hero.getX() + 4);
           if(Gdx.input.isKeyPressed(Input.Keys.A)) hero.setX(hero.getX() - 4);
          // Gdx.input.setInputProcessor(UIstage);
           camera.update();

           if(touchpadLeft.getKnobPercentX() != 0.0) hero.setX(hero.getX() + touchpadLeft.getKnobPercentX() * heroSpeed);
           if(touchpadLeft.getKnobPercentY() != 0.0) hero.setY(hero.getY() + touchpadLeft.getKnobPercentY() * heroSpeed);
           System.out.println(touchpadLeft.getKnobPercentX());
           hero.setOrigin(hero.getWidth() / 2 - hero.getWidth() / 4 - hero.getWidth() / 8, hero.getHeight() / 2 - hero.getHeight() / 4);
           //hero.rotate(touchpadRight.getKnobPercentX() * -3);
        //   hero.rotateBy(touchpadRight.getKnobPercentX() * -3);
          // hero.rotate(touchpadRight.getKnobPercentX() * -3);
                hero.setSize(152, 100);

           // batch.begin();
          //  blockSprite.draw(batch);
          // batch.end();

           stage.act(Gdx.graphics.getDeltaTime());
            stage.draw();
           UIstage.act(Gdx.graphics.getDeltaTime());
           UIstage.draw();

                   /*
            Gdx.gl.glClearColor(0.294f, 0.294f, 0.294f, 1f);
           // Gdx.gl.glClearColor( 0.5f, 1, 0, 1 );
            Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
            camera.update();

            blockSprite.setX(blockSprite.getX() + touchpad.getKnobPercentX()*blockSpeed);
            blockSprite.setY(blockSprite.getY() + touchpad.getKnobPercentY() * blockSpeed);

            batch.begin();
            blockSprite.draw(batch);
            batch.end();
            //stage.act(Gdx.graphics.getDeltaTime());
            stage.act(delta);
            stage.draw();
                */
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

