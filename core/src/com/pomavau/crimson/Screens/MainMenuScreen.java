package com.pomavau.crimson.Screens;

import com.badlogic.gdx.Audio;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.pomavau.crimson.Controller.GameDifficulty;
import com.pomavau.crimson.Controller.MoveToHardGame;
import com.pomavau.crimson.Controller.MoveToMediumGame;
import com.pomavau.crimson.Controller.ShowMenu;
import com.pomavau.crimson.Controller.SoundToggle;
import com.pomavau.crimson.View.FontActor;
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
    private ImageActor titleText;
    private ImageActor difficultyBG;
    private ImageActor easyDFbutton;
    private ImageActor mediumDFbutton;
    private ImageActor hardDFbutton;
    private Group difficultyScreen;
    private Group settingScreen;
    private ImageActor settingsBG;
    private Music music;
    private Stage stage;

   // private FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("android//assets//nasalization-rg.ttf"));
   // private FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
   // private BitmapFont font = generator.generateFont(parameter); // font size 12 pixels


    private FontActor stringDifficulty;
    private FontActor stringEasy;
    private FontActor stringHard;
    private FontActor stringMedium;
    private Color fontColorBlue;
    private Color fontColorWhite;

    class MoveToGame extends ClickListener {
        public void clicked(InputEvent event, float x, float y){
            crimsonTD.getInstance().showGame();
        }
    }
    public MainMenuScreen(SpriteBatch batch)
    {
      //fontColorBlue = new Color(0, 108, 159, 1);
        //fontColorBlue = new Color(0, 126, 187, 1f);
        fontColorBlue = new Color(0, 0.494f, 0.73f, 1f);
        fontColorWhite = new Color(0.62f, 0.658f, 0.67f, 1f);
        //fontColorBlue.add(0, 126, 187, 1f);


      //  parameter.size = 24;
        OrthographicCamera camera = new OrthographicCamera();
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        music = Gdx.audio.newMusic(new FileHandle("android//assets//mainmenu//music.mp3"));
        music.setLooping(true);
        music.play();

        settingsBG = new ImageActor(new Texture("android//assets//Menu2.png"), 384, 616-534);
        musicButton = new ImageActor(new Texture("android//assets//mainmenu//mainmenu_btnMusic.png"), settingsBG.getX() + 120, settingsBG.getY() + 120);
        background = new ImageActor(new Texture("android//assets//mainmenu//mainmenu_bg.png"), 0, 0, 1145, 616);
        playButton = new ImageActor(new Texture("android//assets//mainmenu//mainmenu_btnPlay.png"), 325, 59); //557
        settingsButton = new ImageActor(new Texture("android//assets//mainmenu//mainmenu_btnSet.png"), 513, 59);
        ratesButton = new ImageActor(new Texture("android//assets//mainmenu//mainmenu_btnStat.png"), 695, 59);
        titleText = new ImageActor(new Texture("android//assets//mainmenu//title.png"), 182, 616-224);
        difficultyBG = new ImageActor(new Texture("android//assets//Menu2.png"), 384, 616-534);
        easyDFbutton = new ImageActor(new Texture("android//assets//mainmenu//mainmenu_btn.png"),467, 616-308 );
        mediumDFbutton = new ImageActor(new Texture("android//assets//mainmenu//mainmenu_btn.png"),467, 616-384 );
        hardDFbutton = new ImageActor(new Texture("android//assets//mainmenu//mainmenu_btn.png"),467, 616-459 );


        stringDifficulty = new FontActor("android//assets//nasalization-rg.ttf", 48, fontColorBlue, "DIFFICULTY", 428, 616-169);
        stringEasy = new FontActor("android//assets//nasalization-rg.ttf", 36, fontColorWhite, "EASY", 517, 616-258);
        stringHard = new FontActor("android//assets//nasalization-rg.ttf", 36, fontColorWhite, "HARD", 517, 616-409);
        stringMedium = new FontActor("android//assets//nasalization-rg.ttf", 36, fontColorWhite, "MEDIUM", 483.2f, (float)(616-334.5));

        settingScreen = new Group();
        settingScreen.addActor(settingsBG);
        settingScreen.addActor(musicButton);

        difficultyScreen = new Group();
        difficultyScreen.addActor(difficultyBG);
        difficultyScreen.addActor(easyDFbutton);
        difficultyScreen.addActor(mediumDFbutton);
        difficultyScreen.addActor(hardDFbutton);
        difficultyScreen.addActor(stringDifficulty);
        difficultyScreen.addActor(stringEasy);
        difficultyScreen.addActor(stringMedium);
        difficultyScreen.addActor(stringHard);

        playButton.addListener(new ShowMenu(difficultyScreen));
        easyDFbutton.addListener(new MoveToGame());
        mediumDFbutton.addListener(new MoveToMediumGame());
        hardDFbutton.addListener(new MoveToHardGame());
        musicButton.addListener(new SoundToggle());
        settingsButton.addListener(new ShowMenu(settingScreen));

        stage = new Stage(new ScreenViewport(camera), batch);
        stage.addActor(background);
        stage.addActor(playButton);
        stage.addActor(ratesButton);
        stage.addActor(titleText);
        stage.addActor(settingsButton);
        stage.addActor(settingScreen);
        stage.addActor(difficultyScreen);


    }
    @Override
    public void show() {
        settingScreen.setVisible(false);
        difficultyScreen.setVisible(false);
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
