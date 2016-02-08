package com.pomavau.crimson.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.pomavau.crimson.View.ImageActor;
import com.pomavau.crimson.crimsonTD;

/**
 * Created by Pomavau on 18.01.2016.
 */
public class MainMenuScreen implements Screen{
   //final crimsonTD game;
    private ImageActor background;
    private ImageActor playButton;
    private ImageActor playText;
    private ImageActor settingsButton;
    private ImageActor ratesButton;
    private int language = 0;
    private Stage stage;
    class MoveToGame extends ClickListener {
        public void clicked(InputEvent event, float x, float y){
            crimsonTD.getInstance().showGame();
        }
    }
    public MainMenuScreen(SpriteBatch batch)
    {
        background = new ImageActor(new Texture("android//assets//mainmenu//mainmenu_bg.png"), 0, 0, 800, 600);
        playButton = new ImageActor(new Texture("android//assets//mainmenu//mainmenu_play.png"), 521, 316);
        //if (language == 0)
            playText = new ImageActor(new Texture("android//assets//mainmenu//mainmenu_playEN.png"), 583, 336);
       // playButton.setPosition(110,40);
        playButton.addListener(new MoveToGame());
        playText.addListener(new MoveToGame());
        settingsButton = new ImageActor(new Texture("android//assets//mainmenu//mainmenu_btnSet.png"), 664, 179);
        //settingsButton.setPosition(540,40);

        ratesButton = new ImageActor(new Texture("android//assets//mainmenu//mainmenu_btnStat.png"), 526, 179);

        //ratesButton.setPosition(327,40);
        OrthographicCamera camera = new OrthographicCamera();
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        stage = new Stage(new ScreenViewport(camera), batch);
        stage.addActor(background);
        stage.addActor(playButton);
        stage.addActor(ratesButton);
        stage.addActor(playText);
        stage.addActor(settingsButton);


    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width,height,true);
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
