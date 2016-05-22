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
import com.pomavau.crimson.Controller.SettingsController;
import com.pomavau.crimson.Controller.SettingsType;
import com.pomavau.crimson.Controller.ShowMenu;
import com.pomavau.crimson.Controller.ShowStatistics;
import com.pomavau.crimson.Controller.SoundToggle;
import com.pomavau.crimson.View.FontActor;
import com.pomavau.crimson.View.ImageActor;
import com.pomavau.crimson.crimsonTD;

import java.awt.Font;
import java.awt.Image;
import java.io.FileNotFoundException;


/**
 * Created by Pomavau on 18.01.2016.
 */
public class MainMenuScreen implements Screen{
    private ImageActor background;
    private ImageActor playButton;
    private ImageActor backgroundBorder;
    private ImageActor settingsButton;
    private ImageActor ratesButton;
    //private ImageActor musicButton;
    private ImageActor titleText;
    private ImageActor difficultyBG;
    private ImageActor easyDFbutton;
    private ImageActor mediumDFbutton;
    private ImageActor hardDFbutton;
    private Group difficultyScreen;

    private Group settingScreen;
    //private ImageActor settingsBG;
    private Music music;
    private Stage stage;
    //statistic screen:
    private ImageActor statBG;
    private ImageActor statOK;
    private ImageActor favWeaponICERF;
    private FontActor stringSTATISTIC;
    private FontActor stringLEADERBOADRS;
    private FontActor stringLB1;
    private FontActor stringLB2;
    private FontActor stringLB3;
    private FontActor stringLB4;
    private FontActor stringFAVWEAPON;
    private FontActor stringMAXTIME;
    private FontActor MAXTIME;
    private FontActor FAVWEAPON;
    private Group statScreen;
    //options screen;
    private ImageActor settBG;
    private ImageActor settOK;
    private ImageActor stBloodON_E;
    private ImageActor stBloodOFF_E;
    private ImageActor stBloodON_D;
    private ImageActor stBloodOFF_D;
    private ImageActor stSFXON_E;
    private ImageActor stSFXOFF_E;
    private ImageActor stSFXON_D;
    private ImageActor stSFXOFF_D;
    private ImageActor stMusicON_E;
    private ImageActor stMusicOFF_E;
    private ImageActor stMusicON_D;
    private ImageActor stMusicOFF_D;
    private FontActor stBloodON_string;
    private FontActor stBloodOFF_string;
    private FontActor stSFXON_string;
    private FontActor stSFXOFF_string;
    private FontActor stMusicON_string;
    private FontActor stMusicOFF_string;
    private FontActor stringBLOOD;
    private FontActor stringSFXSOUND;
    private FontActor stringMUSIC;
    private FontActor stringOPTIONS;
    private FontActor settOKString;
    private Group stBloodON;
    private Group stBloodOFF;
    private Group stSFXON;
    private Group stSFXOFF;
    private Group stMusicON;
    private Group stMusicOFF;

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
    public MainMenuScreen(SpriteBatch batch) throws FileNotFoundException {
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


        //musicButton = new ImageActor(new Texture("android//assets//mainmenu//mainmenu_btnMusic.png"), settingsBG.getX() + 120, settingsBG.getY() + 120);
        background = new ImageActor(new Texture("android//assets//mainmenu//mainmenu_art.png"), 0, 0, 1145, 616);
        backgroundBorder = new ImageActor(new Texture("android//assets//mainmenu//mainmenu_bg.png"), 0, 0, 1145, 616);
        playButton = new ImageActor(new Texture("android//assets//mainmenu//mainmenu_btnPlay.png"), 325, 59); //557
        settingsButton = new ImageActor(new Texture("android//assets//mainmenu//mainmenu_btnSet.png"), 513, 59);
        ratesButton = new ImageActor(new Texture("android//assets//mainmenu//mainmenu_btnStat2.png"), 695, 59);
        //titleText = new ImageActor(new Texture("android//assets//mainmenu//title.png"), 182, 616-224);
        titleText = new ImageActor(new Texture("android//assets//mainmenu//title2.png"), 54, 616-186);
        difficultyBG = new ImageActor(new Texture("android//assets//Menu3.png"), 384, 616-534);
        easyDFbutton = new ImageActor(new Texture("android//assets//mainmenu//mainmenu_btn.png"),467, 616-308 );
        mediumDFbutton = new ImageActor(new Texture("android//assets//mainmenu//mainmenu_btn.png"),467, 616-384 );
        hardDFbutton = new ImageActor(new Texture("android//assets//mainmenu//mainmenu_btn.png"),467, 616-459 );


        stringDifficulty = new FontActor("android//assets//nasalization-rg.ttf", 48, fontColorBlue, "DIFFICULTY", 428, 616-169);
        stringEasy = new FontActor("android//assets//nasalization-rg.ttf", 36, fontColorWhite, "EASY", 517, 616-258);
        stringHard = new FontActor("android//assets//nasalization-rg.ttf", 36, fontColorWhite, "HARD", 517, 616-409);
        stringMedium = new FontActor("android//assets//nasalization-rg.ttf", 36, fontColorWhite, "MEDIUM", 483.2f, (float)(616-334.5));
/*
        settingScreen = new Group();
        settingScreen.addActor(settingsBG);
        settingScreen.addActor(musicButton);*/

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
       // musicButton.addListener(new SoundToggle());
       // settingsButton.addListener(new ShowMenu(settingScreen));


        statBG = new ImageActor(new Texture("android//assets//mainmenu//InventoryMenu2.png"), 346, 616 - 534);
        stringSTATISTIC = new FontActor("android//assets//nasalization-rg.ttf", 60, fontColorBlue, "STATISTICS", 400, 616-154);
        stringMAXTIME = new FontActor("android//assets//nasalization-rg.ttf", 30, fontColorBlue, "TIME RECORD:", 408, 616-207);
        stringFAVWEAPON = new FontActor("android//assets//nasalization-rg.ttf", 30, fontColorBlue, "FAV WEAPON:", 408, 616-254);
        stringLEADERBOADRS = new FontActor("android//assets//nasalization-rg.ttf", 30, fontColorBlue, "LEADERBOARDS:", 408, 616-297);
        statOK = new ImageActor(new Texture("android//assets//mainmenu//mainmenu_btn.png"), 473, 616 - 533);
        stringLB1 = new FontActor("android//assets//nasalization-rg.ttf", 24, fontColorWhite, "1. ", 405, 616 - 329);
        stringLB2 = new FontActor("android//assets//nasalization-rg.ttf", 24, fontColorWhite, "2. ", 405, 616 - 363);
        stringLB3 = new FontActor("android//assets//nasalization-rg.ttf", 24, fontColorWhite, "3. ", 405, 616 - 397);
        stringLB4 = new FontActor("android//assets//nasalization-rg.ttf", 24, fontColorWhite, "4. ", 405, 616 - 433);
        MAXTIME = new FontActor("android//assets//nasalization-rg.ttf", 30, fontColorWhite, "08.47.2", 639, 616 - 209);
        FAVWEAPON = new FontActor("android//assets//nasalization-rg.ttf", 24, fontColorWhite, "ICERIFLE", 642, 616 - 257);
        //stringOK = new FontActor("android//assets//nasalization-rg.ttf", 36, fontColorWhite, "EXIT", 530f, 616 - 483);
        statScreen = new Group();
        statScreen.addActor(statBG);
        statScreen.addActor(stringSTATISTIC);
        statScreen.addActor(stringMAXTIME);
        statScreen.addActor(stringFAVWEAPON);
        statScreen.addActor(stringLEADERBOADRS);
        statScreen.addActor(statOK);
        statScreen.addActor(stringLB1);
        statScreen.addActor(stringLB2);
        statScreen.addActor(stringLB3);
        statScreen.addActor(stringLB4);
        statScreen.addActor(MAXTIME);
        statScreen.addActor(FAVWEAPON);
        statScreen.setVisible(false);
        ratesButton.addListener(new ShowStatistics(statScreen, stringLB1, stringLB2, stringLB3, stringLB4));

        settBG = new ImageActor(new Texture("android//assets//Menu3.png"), 384, 616-534);
        settOK = new ImageActor(new Texture("android//assets//mainmenu//mainmenu_btn.png"), 473, 616 - 533);
        stringOPTIONS = new FontActor("android//assets//nasalization-rg.ttf", 48, fontColorBlue, "OPTIONS", 460.7f, 616-152);
        stringBLOOD = new FontActor("android//assets//nasalization-rg.ttf", 36, fontColorBlue, "BLOOD:", 504, 616-204);
        stringSFXSOUND = new FontActor("android//assets//nasalization-rg.ttf", 36, fontColorBlue, "SFX SOUND:", 457, 616-286);
        stringMUSIC = new FontActor("android//assets//nasalization-rg.ttf", 36, fontColorBlue, "MUSIC:", 501, 616-371);
        stBloodON_E = new ImageActor(new Texture("android//assets//mainmenu//mainmenu_settings_enabled.png"), 437, 616 - 280);
        stBloodON_D = new ImageActor(new Texture("android//assets//mainmenu//mainmenu_settings_disabled.png"), 437, 616 - 280);
        stBloodOFF_E = new ImageActor(new Texture("android//assets//mainmenu//mainmenu_settings_enabled.png"), 582, 616 - 280);
        stBloodOFF_D = new ImageActor(new Texture("android//assets//mainmenu//mainmenu_settings_disabled.png"), 582, 616 - 280);
        stSFXON_E = new ImageActor(new Texture("android//assets//mainmenu//mainmenu_settings_enabled.png"), 437, 616 - 363);
        stSFXON_D = new ImageActor(new Texture("android//assets//mainmenu//mainmenu_settings_disabled.png"), 437, 616 - 363);
        stSFXOFF_E = new ImageActor(new Texture("android//assets//mainmenu//mainmenu_settings_enabled.png"), 582, 616 - 363);
        stSFXOFF_D = new ImageActor(new Texture("android//assets//mainmenu//mainmenu_settings_disabled.png"), 582, 616 - 363);
        stMusicON_E = new ImageActor(new Texture("android//assets//mainmenu//mainmenu_settings_enabled.png"), 437, 616 - 450);
        stMusicON_D = new ImageActor(new Texture("android//assets//mainmenu//mainmenu_settings_disabled.png"), 437, 616 - 450);
        stMusicOFF_E = new ImageActor(new Texture("android//assets//mainmenu//mainmenu_settings_enabled.png"), 582, 616 - 450);
        stMusicOFF_D = new ImageActor(new Texture("android//assets//mainmenu//mainmenu_settings_disabled.png"), 582, 616 - 450);
        stBloodON_string = new FontActor("android//assets//nasalization-rg.ttf", 36, fontColorWhite, "ON", 464, 616 - 244);
        stBloodOFF_string = new FontActor("android//assets//nasalization-rg.ttf", 36, fontColorWhite, "OFF", 607.5f, 616 - 244);
        stSFXON_string = new FontActor("android//assets//nasalization-rg.ttf", 36, fontColorWhite, "ON", 464, 616 - 326);
        stSFXOFF_string = new FontActor("android//assets//nasalization-rg.ttf", 36, fontColorWhite, "OFF", 607.5f, 616 - 326);
        stMusicON_string = new FontActor("android//assets//nasalization-rg.ttf", 36, fontColorWhite, "ON", 464, 616 - 414);
        stMusicOFF_string = new FontActor("android//assets//nasalization-rg.ttf", 36, fontColorWhite, "OFF", 607.5f, 616 - 414);
        settOKString = new FontActor("android//assets//nasalization-rg.ttf", 36, fontColorWhite, "APPLY", 510.7f, 616 - 484);

        stBloodON = new Group();
        stBloodON.addActor(stBloodON_E);
        stBloodON.addActor(stBloodON_D);
        stBloodON_D.setVisible(false);
        stBloodON.addActor(stBloodON_string);
        stBloodOFF = new Group();
        stBloodOFF.addActor(stBloodOFF_E);
        stMusicOFF_E.setVisible(false);
        stBloodOFF.addActor(stBloodOFF_D);
        stBloodOFF.addActor(stBloodOFF_string);
        stSFXON = new Group();
        stSFXON.addActor(stSFXON_E);
        stSFXON.addActor(stSFXON_D);
        stSFXON_D.setVisible(false);
        stSFXON.addActor(stSFXON_string);
        stSFXOFF = new Group();
        stSFXOFF.addActor(stSFXOFF_E);
        stSFXOFF_E.setVisible(false);
        stSFXOFF.addActor(stSFXOFF_D);
        stSFXOFF.addActor(stSFXOFF_string);
        stMusicON = new Group();
        stMusicON.addActor(stMusicON_E);
        stMusicON.addActor(stMusicON_D);
        stMusicON_D.setVisible(false);
        stMusicON.addActor(stMusicON_string);
        stMusicOFF = new Group();
        stMusicOFF.addActor(stMusicOFF_E);
        stMusicOFF_E.setVisible(false);
        stMusicOFF.addActor(stMusicOFF_D);
        stMusicOFF.addActor(stMusicOFF_string);

        stBloodON.addListener(new SettingsController(stBloodON, stBloodOFF, SettingsType.BLOOD, "on"));
        stBloodOFF.addListener(new SettingsController(stBloodOFF, stBloodON, SettingsType.BLOOD, "off"));

        settingScreen = new Group();
        settingScreen.addActor(settBG);
        settingScreen.addActor(settOK);
        settingScreen.addActor(stBloodON);
        settingScreen.addActor(stBloodOFF);
        settingScreen.addActor(stSFXON);
        settingScreen.addActor(stSFXOFF);
        settingScreen.addActor(stMusicON);
        settingScreen.addActor(stMusicOFF);
        settingScreen.addActor(stringBLOOD);
        settingScreen.addActor(stringMUSIC);
        settingScreen.addActor(stringSFXSOUND);
        settingScreen.addActor(stringOPTIONS);
        settingScreen.addActor(settOKString);

        settingsButton.addListener(new ShowMenu(settingScreen));

        stage = new Stage(new ScreenViewport(camera), batch);
        stage.addActor(background);
        stage.addActor(backgroundBorder);
        stage.addActor(playButton);
        stage.addActor(ratesButton);
        stage.addActor(titleText);
        stage.addActor(settingsButton);
        stage.addActor(settingScreen);
        stage.addActor(difficultyScreen);
        stage.addActor(statScreen);
        stage.addActor(settingScreen);
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
        //Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(delta);
        stage.draw();
    }
    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
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


    public Group getSettingScreen() {
        return settingScreen;
    }

}
