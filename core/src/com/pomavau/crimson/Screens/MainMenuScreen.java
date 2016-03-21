package com.pomavau.crimson.Screens;

import com.badlogic.gdx.Audio;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.pomavau.crimson.Controller.ShowMenu;
import com.pomavau.crimson.Controller.SoundToggle;
import com.pomavau.crimson.View.ImageActor;
import com.pomavau.crimson.crimsonTD;

/**
 * Created by Pomavau on 18.01.2016.
 */
public class MainMenuScreen implements Screen{
    private ImageActor background;
    private ImageActor playButton;
    private ImageActor playText;
    private ImageActor settingsButton;
    private ImageActor ratesButton;
    private ImageActor musicButton;
    private Group settingScreen;
    private ImageActor settingsBG;
    private Music music;
    private Stage stage;
    class MoveToGame extends ClickListener {
        public void clicked(InputEvent event, float x, float y){
            crimsonTD.getInstance().showGame();
        }
    }
    public MainMenuScreen(SpriteBatch batch)
    {
        OrthographicCamera camera = new OrthographicCamera();
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        music = Gdx.audio.newMusic(new FileHandle("android//assets//mainmenu//music.mp3"));
        music.setLooping(true);
        music.play();

        settingsBG = new ImageActor(new Texture("android//assets//mainmenu//SettingsScreen.png"), 384, 616-534);
        musicButton = new ImageActor(new Texture("android//assets//mainmenu//mainmenu_btnMusic.png"), settingsBG.getX() + 120, settingsBG.getY() + 120);
        background = new ImageActor(new Texture("android//assets//mainmenu//mainmenu_bg.png"), 0, 0, 1145, 616);
        playButton = new ImageActor(new Texture("android//assets//mainmenu//mainmenu_btnPlay.png"), 325, 59); //557
        settingsButton = new ImageActor(new Texture("android//assets//mainmenu//mainmenu_btnSet.png"), 513, 59);
        ratesButton = new ImageActor(new Texture("android//assets//mainmenu//mainmenu_btnStat.png"), 695, 59);

        settingScreen = new Group();
        settingScreen.addActor(settingsBG);
        settingScreen.addActor(musicButton);

        playButton.addListener(new MoveToGame());
        musicButton.addListener(new SoundToggle());
        settingsButton.addListener(new ShowMenu(settingScreen));

        stage = new Stage(new ScreenViewport(camera), batch);
        stage.addActor(background);
        stage.addActor(playButton);
        stage.addActor(ratesButton);

        stage.addActor(settingsButton);
        stage.addActor(settingScreen);

    }
    @Override
    public void show() {
        settingScreen.setVisible(false);
        Gdx.input.setInputProcessor(stage);
        Gdx.gl.glClearColor(0.294f, 0.294f, 0.294f, 1f);    //Gdx.gl.glClearColor(0.5f, 1, 0, 1); - green
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
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
    public void pause() {}
    @Override
    public void resume() {}
    @Override
    public void hide() {
        music.stop();
    }
    @Override
    public void dispose() {}
}
