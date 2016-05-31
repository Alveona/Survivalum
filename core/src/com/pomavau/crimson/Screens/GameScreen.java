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
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.pomavau.crimson.Controller.Animation;
import com.pomavau.crimson.Controller.ApplyPerk;
import com.pomavau.crimson.Controller.ApplyWeapon;
import com.pomavau.crimson.Controller.BulletType;
import com.pomavau.crimson.Controller.CameraController;
import com.pomavau.crimson.Controller.GameDifficulty;
import com.pomavau.crimson.Controller.InventoryDecrease;
import com.pomavau.crimson.Controller.InventoryIncrease;
import com.pomavau.crimson.Controller.MoveToMenu;
import com.pomavau.crimson.Controller.MovementControlStyle;
import com.pomavau.crimson.Controller.ObjectState;
import com.pomavau.crimson.Controller.PerkType;
import com.pomavau.crimson.Controller.PerksClick;
import com.pomavau.crimson.Controller.PlayerController;
import com.pomavau.crimson.Controller.BotController;
import com.pomavau.crimson.Controller.SetReloading;
import com.pomavau.crimson.Controller.SettingsController;
import com.pomavau.crimson.Controller.SettingsType;
import com.pomavau.crimson.Controller.ShowMenu;
import com.pomavau.crimson.Controller.Weapon;
import com.pomavau.crimson.Model.Bullet;
import com.pomavau.crimson.Model.FlameTank;
import com.pomavau.crimson.Model.HPcircle;
import com.pomavau.crimson.Model.LevelWorld;
import com.pomavau.crimson.Model.Perk;
import com.pomavau.crimson.Model.PhWorld;
import com.pomavau.crimson.View.FontActor;
import com.pomavau.crimson.View.ImageActor;
import com.pomavau.crimson.crimsonTD;
//import com.sun.javafx.scene.control.skin.TableHeaderRow;

import java.awt.Image;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Scanner;

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
    private FontActor stringresumebutton;
    private FontActor stringoptionsbutton;
    private FontActor stringexitbutton;
    private FontActor stringPause;
    private ImageActor resumebutton;
    private ImageActor optionsbutton;
    private ImageActor exitbutton;
    private Group pauseScreen;

    private ImageActor deathBG;
    private ImageActor deathButton;
    private ImageActor deathIcon;
    private FontActor deathstring;
    private FontActor deathstringLB;
    private FontActor deathstringApply;
    private FontActor deathLB1;
    private FontActor deathLB2;
    private FontActor deathLB3;
    private FontActor deathLB4;
    private FontActor deathLB5;
    private Group ApplyDeathGroup;
    private Group deathScreen;
    Scanner sc;

    BufferedReader br;
    FileWriter fw;
    private String tempstringnames;
    private int currentscoreindex;
    private int[] leaderboards = new int[10];
    DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
    Date date = new Date();

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


    private ImageActor inventoryButton;
    private ImageActor inventoryBG;
    private ImageActor inventorySlot;

    //perks screen
    private ImageActor perksBG;
    private ImageActor perksApply;
    private Group perksScreen;
    private Perk perkMS;
    private Perk perkSS;
    private Perk perkRS;
    private Perk perkLvL;
    private Perk perkAmmo;
    private Perk perkFifty;
    private Perk perkFreeze;
    private Perk perkCamera;
    private Perk perkInvul;
    private Perk perkHeal;
    private Perk perkDeath;
    private Perk perkEnabled1;
    private Perk perkEnabled2;
    private Perk perkEnabled3;
    private Array<Perk> perkArray;
    private Vector2 perkPos1;
    private Vector2 perkPos2;
    private Vector2 perkPos3;
    private int randomPerk;
    private int randomTemp;

    private ImageActor m4a4hud;
    private ImageActor icegunhud;
    private ImageActor throwerhud;
    private ImageActor firegunhud;
    private ImageActor inventoryApply;
    private ImageActor inventoryLeft;
    private ImageActor inventoryRight;
    private FontActor inventoryApplystring;
    private ImageActor rifleInfo;
    private ImageActor iceInfo;
    private ImageActor flameInfo;
    private Group ApplyInvGroup;
    private Group inventoryScreen;

    private ImageActor rifleEnabled;
    private ImageActor icegunEnabled;
    private ImageActor throwerEnabled;

    private Drawable touchBackground;
    private Drawable touchKnob;

    private Group transparentGroup;

    private Bullet bullet;
    private int bulletSpeed = 300; //d:300
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
    private FlameTank bulletsCounterCountersThrower;
    private ImageActor[] bulletsCounterCountersRifle;
    private ImageActor[] bulletsCounterCountersIcegun;
    private ImageActor[] bulletsCounterCountersFiregun;
    private ImageActor[] bulletsCounterCountersPistol;
    private int lastBulletY = 0;
    private int bulletsShooted = 0;
    private ImageActor scoresBG;
    private long currenttime = 0;
    private long deltatime;
    private int temp = 0;
    private float timereloading;
    private float reloadtimer = 1;

    private long currenttimeSec = (long) (currenttime / 1E9);
    private long deltatimeSec;
    private boolean timerIsOn = false;

    private float spawntime = 5;
    private float nextspawn = 0;
    private Color fontColorBlue;
    private Color fontColorWhite;
    private FontActor stringInventory;
    private FontActor stringScore;
    private ImageActor riflesignleft;
    private ImageActor riflesignright;
    private ImageActor riflesignmiddle;
    private Group inventoryButtonGroup;
    private FontActor stringScoresCount;
    private int inventoryCurrentChoose = 1;
    StringBuilder FPSbuilder = new StringBuilder();

    private Group settingsScreen;
    private GameDifficulty gameDifficulty = GameDifficulty.EASY;




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


    public GameScreen(SpriteBatch batch, ShapeRenderer shape, BitmapFont font, HashMap<Integer, TextureRegion> textureRegions) throws IOException {
        switch (gameDifficulty) {
            case EASY:
                spawntime = 5;
                break;
            case MEDIUM:
                spawntime = 4;
                break;
            case HARD:
                spawntime = 3;
                break;
        }
        debugRenderer = new Box2DDebugRenderer();
        phworld = new PhWorld();
       // animation = new Animation(new TextureRegion(new Texture(crimsonTD.getInstance().resolvePath("heromove_atlas.png")), 1565, 824), 5, 3);
        bullets = new Array<Bullet>();
        font = new BitmapFont();
        camera = new OrthographicCamera();
        this.textureRegions = textureRegions;
     //   TextureRegion region;
      //  region = textureRegions.get(0);
        world = new LevelWorld(new ScreenViewport(camera), batch);
        switch (world.getPlayer().getCurrentWeapon()) {
            case ASSAULTRIFLE:
                bulletsLeft = 30;
                bulletsCountDefault = 30;
                inventoryCurrentChoose = 1;
                break;
            case ICERIFLE:
                bulletsLeft = 5;
                bulletsCountDefault = 5;
                inventoryCurrentChoose = 2;
            case FLAMETHROWER:
                bulletsLeft = 8;
                bulletsCountDefault = 8;
                inventoryCurrentChoose = 3;
                break;
        }
        fontColorBlue = new Color(0, 0.494f, 0.73f, 1f);
        fontColorWhite = new Color(0.62f, 0.658f, 0.67f, 1f);

        stringInventory = new FontActor(crimsonTD.getInstance().resolvePath("nasalization-rg.ttf"), 28, fontColorWhite, "INVENTORY", 115, 616 - 24);
        riflesignleft = new ImageActor(new Texture(crimsonTD.getInstance().resolvePath("inventory_bg_rifle.png")), 133, (float) (616 - 65.5));
        riflesignright = new ImageActor(new Texture(crimsonTD.getInstance().resolvePath("inventory_bg_rifle.png")), 234, (float) (616 - 65.5));
        riflesignmiddle = new ImageActor(new Texture(crimsonTD.getInstance().resolvePath("inventory_bg_rifle.png")), 184, (float) (616 - 65.5));

        stringScore = new FontActor(crimsonTD.getInstance().resolvePath("nasalization-rg.ttf"), 24, fontColorWhite, "SCORE:", 431, 616 - 7);
        stringScoresCount = new FontActor(crimsonTD.getInstance().resolvePath("nasalization-rg.ttf"), 30, fontColorWhite, String.format("%06d", 0), 542, 616 - 6);


        pauseButton = new ImageActor(new Texture(crimsonTD.getInstance().resolvePath("/gamescreen_btnPause.png")), 0, world.getHeight(), 75, 75);
        pauseBG = new ImageActor(new Texture(crimsonTD.getInstance().resolvePath("Menu3.png")), 384, 616 - 534);
        stringPause = new FontActor(crimsonTD.getInstance().resolvePath("nasalization-rg.ttf"), 48, fontColorBlue, "PAUSE", 487, 616-169);
        stringresumebutton = new FontActor(crimsonTD.getInstance().resolvePath("nasalization-rg.ttf"), 36, fontColorWhite, "RESUME", 487, 616-258);
        stringexitbutton = new FontActor(crimsonTD.getInstance().resolvePath("nasalization-rg.ttf"), 36, fontColorWhite, "EXIT", 526, 616-409);
        stringoptionsbutton = new FontActor(crimsonTD.getInstance().resolvePath("nasalization-rg.ttf"), 36, fontColorWhite, "OPTIONS", 484, (float)(616-334.5));
        resumebutton = new ImageActor(new Texture(crimsonTD.getInstance().resolvePath("mainmenu//mainmenu_btn.png")),467, 616-308 );
        optionsbutton = new ImageActor(new Texture(crimsonTD.getInstance().resolvePath("mainmenu//mainmenu_btn.png")),467, 616-384 );
        exitbutton = new ImageActor(new Texture(crimsonTD.getInstance().resolvePath("mainmenu//mainmenu_btn.png")),467, 616-459 );
        pauseScreen = new Group();
        pauseScreen.setVisible(false);
        pauseScreen.addActor(pauseBG);
        pauseScreen.addActor(resumebutton);
        pauseScreen.addActor(optionsbutton);
        pauseScreen.addActor(exitbutton);
        pauseScreen.addActor(stringPause);
        pauseScreen.addActor(stringresumebutton);
        pauseScreen.addActor(stringexitbutton);
        pauseScreen.addActor(stringoptionsbutton);

        pauseButton.addListener(new ShowMenu(pauseScreen));
        resumebutton.addListener(new ShowMenu(pauseScreen));
        exitbutton.addListener(new MoveToMenu());

        //inventoryButton = new ImageActor(new Texture(crimsonTD.getInstance().resolvePath("gamescreen_bulletsCount.png"), 325, (int)world.getHeight(), 75, 165);
        inventoryButton = new ImageActor(new Texture(crimsonTD.getInstance().resolvePath("gamescreen_btnInventory.png")), 100, (int) world.getHeight());
        inventoryApply = new ImageActor(new Texture(crimsonTD.getInstance().resolvePath("mainmenu//mainmenu_btn.png")), 473, 616 - 533);
        inventoryBG = new ImageActor(new Texture(crimsonTD.getInstance().resolvePath("mainmenu//InventoryMenu2.png")), 346, 616 - 534);
        inventorySlot = new ImageActor(new Texture(crimsonTD.getInstance().resolvePath("mainmenu//InventorySlot.png")), 507, 616 - 282);
        m4a4hud = new ImageActor(new Texture(crimsonTD.getInstance().resolvePath("mainmenu//m4a4_hud2.png")), 519, 616 - 266, 113, 75);
        icegunhud = new ImageActor(new Texture(crimsonTD.getInstance().resolvePath("mainmenu//icegun_hud.png")), 519, 616 - 266, 113, 75);
        throwerhud = new ImageActor(new Texture(crimsonTD.getInstance().resolvePath("mainmenu//firegun_hud.png")), 519, 616 - 266, 113, 75);
        inventoryApplystring = new FontActor(crimsonTD.getInstance().resolvePath("nasalization-rg.ttf"), 36, fontColorWhite, "APPLY", 510.7f, 616 - 484);
        ApplyInvGroup = new Group();
        ApplyInvGroup.addActor(inventoryApply);
        ApplyInvGroup.addActor(inventoryApplystring);
        inventoryLeft = new ImageActor(new Texture(crimsonTD.getInstance().resolvePath("mainmenu//inventory_btnleft.png")), 413, 616 - 265);
        inventoryRight = new ImageActor(new Texture(crimsonTD.getInstance().resolvePath("mainmenu//inventory_btnright.png")), 663, 616 - 265);
        inventoryRight.addListener(new InventoryIncrease());
        inventoryLeft.addListener(new InventoryDecrease());

        rifleInfo = new ImageActor(new Texture(crimsonTD.getInstance().resolvePath("mainmenu//InventoryInfo_RIFLE.png")), 386, 616 - 443);
        rifleInfo.setVisible(false);
        iceInfo = new ImageActor(new Texture(crimsonTD.getInstance().resolvePath("mainmenu//InventoryInfo_ICEGUN.png")), 386, 616 - 443);
        iceInfo.setVisible(false);
        flameInfo = new ImageActor(new Texture(crimsonTD.getInstance().resolvePath("mainmenu//InventoryInfo_THROWER.png")), 386, 616 - 443);
        flameInfo.setVisible(false);

        bullet = new Bullet(new Texture(crimsonTD.getInstance().resolvePath("/fire1.png")), world.getPlayer().getX(), world.getPlayer().getY(), 100f, 62f, world.getPhysicsWorld(), BulletType.FLAME);
        bullet.getBox().setActive(false);

        inventoryScreen = new Group();
        inventoryScreen.setVisible(false);
        inventoryScreen.addActor(inventoryBG);
        inventoryScreen.addActor(m4a4hud);
        inventoryScreen.addActor(icegunhud);
        inventoryScreen.addActor(throwerhud);
        inventoryScreen.addActor(inventorySlot);
        //inventoryScreen.addActor(inventoryApply);
        inventoryScreen.addActor(ApplyInvGroup);
        inventoryScreen.addActor(inventoryLeft);
        inventoryScreen.addActor(inventoryRight);
        inventoryScreen.addActor(rifleInfo);
        inventoryScreen.addActor(iceInfo);
        inventoryScreen.addActor(flameInfo);
        ApplyInvGroup.addListener(new ApplyWeapon(inventoryCurrentChoose, inventoryScreen));
        //inventoryButton.addListener(new ShowMenu(inventoryScreen));

        deathBG = new ImageActor(new Texture(crimsonTD.getInstance().resolvePath("mainmenu//InventoryMenu2.png")), 346, 616 - 534);
        deathButton = new ImageActor(new Texture(crimsonTD.getInstance().resolvePath("mainmenu//mainmenu_btn.png")), 473, 616 - 533);
        deathIcon = new ImageActor(new Texture(crimsonTD.getInstance().resolvePath("mainmenu//deathIcon.png")),  551, 616-208);
        deathLB1 = new FontActor(crimsonTD.getInstance().resolvePath("nasalization-rg.ttf"), 24, fontColorWhite, "1. ", 406, 616 - 308);
        deathLB2 = new FontActor(crimsonTD.getInstance().resolvePath("nasalization-rg.ttf"), 24, fontColorWhite, "2. ", 406, 616 - 342);
        deathLB3 = new FontActor(crimsonTD.getInstance().resolvePath("nasalization-rg.ttf"), 24, fontColorWhite, "3. ", 406, 616 - 378);
        deathLB4 = new FontActor(crimsonTD.getInstance().resolvePath("nasalization-rg.ttf"), 24, fontColorWhite, "4. ", 406, 616 - 414);
        deathstring = new FontActor(crimsonTD.getInstance().resolvePath("nasalization-rg.ttf"), 48, fontColorBlue, "YOU'RE DEAD", 406, 616-209);
        deathstringLB = new FontActor(crimsonTD.getInstance().resolvePath("nasalization-rg.ttf"), 30, fontColorBlue, "LEADERBOARDS:", 440, 616-265);
        deathstringApply = new FontActor(crimsonTD.getInstance().resolvePath("nasalization-rg.ttf"), 36, fontColorWhite, "EXIT", 530f, 616 - 483);
        ApplyDeathGroup = new Group();
        ApplyDeathGroup.addActor(deathButton);
        ApplyDeathGroup.addActor(deathstringApply);
        ApplyDeathGroup.addListener(new MoveToMenu());
        sc = new Scanner(new File(crimsonTD.getInstance().resolvePath("stats//stats.txt")));
        //sc = new Scanner(crimsonTD.getInstance().resolvePath("stats//stats.txt");
     //   br = new BufferedReader(new FileReader(new File(crimsonTD.getInstance().resolvePath("stats//stats.txt")));
        fw = new FileWriter(new File(crimsonTD.getInstance().resolvePath("stats//stats.txt")), true);
/*
        fw.append("350\n" +
                "75\n" +
                "889\n" +
                "250\n" +
                "550\n" +
                "25\n" +
                "552\n" +
                "311\n" +
                "120\n" +
                "450");
        fw.close();
        */
       // deathstringApply = new ImageActor(new Texture(crimsonTD.getInstance().resolvePath("mainmenu//mainmenu_btn.png"), 473, 616 - 533);
        rifleEnabled = new ImageActor(new Texture(crimsonTD.getInstance().resolvePath("rifle_enabled.png")), 79, 616 - 145);
        icegunEnabled = new ImageActor(new Texture(crimsonTD.getInstance().resolvePath("icegun_enabled.png")), 79, 616 - 145);
        throwerEnabled = new ImageActor(new Texture(crimsonTD.getInstance().resolvePath("falmethrower_enabled.png")), 79, 616 - 145);

        deathScreen = new Group();
        deathScreen.addActor(deathBG);
        //deathScreen.addActor(deathButton);
        deathScreen.addActor(ApplyDeathGroup);
        deathScreen.addActor(deathLB1);
        deathScreen.addActor(deathLB2);
        deathScreen.addActor(deathLB3);
        deathScreen.addActor(deathLB4);
        deathScreen.addActor(deathIcon);
        deathScreen.addActor(deathstring);
        deathScreen.addActor(deathstringLB);
        deathScreen.setVisible(false);

        settingsScreen = new Group();
        settBG = new ImageActor(new Texture(crimsonTD.getInstance().resolvePath("Menu3.png")), 384, 616-534);
        settOK = new ImageActor(new Texture(crimsonTD.getInstance().resolvePath("mainmenu//mainmenu_btn.png")), 473, 616 - 533);
        stringOPTIONS = new FontActor(crimsonTD.getInstance().resolvePath("nasalization-rg.ttf"), 48, fontColorBlue, "OPTIONS", 460.7f, 616-152);
        stringBLOOD = new FontActor(crimsonTD.getInstance().resolvePath("nasalization-rg.ttf"), 36, fontColorBlue, "BLOOD:", 504, 616-204);
        stringSFXSOUND = new FontActor(crimsonTD.getInstance().resolvePath("nasalization-rg.ttf"), 36, fontColorBlue, "SFX SOUND:", 457, 616-286);
        stringMUSIC = new FontActor(crimsonTD.getInstance().resolvePath("nasalization-rg.ttf"), 36, fontColorBlue, "MUSIC:", 501, 616-371);
        stBloodON_E = new ImageActor(new Texture(crimsonTD.getInstance().resolvePath("mainmenu//mainmenu_settings_enabled.png")), 437, 616 - 280);
        stBloodON_D = new ImageActor(new Texture(crimsonTD.getInstance().resolvePath("mainmenu//mainmenu_settings_disabled.png")), 437, 616 - 280);
        stBloodOFF_E = new ImageActor(new Texture(crimsonTD.getInstance().resolvePath("mainmenu//mainmenu_settings_enabled.png")), 582, 616 - 280);
        stBloodOFF_D = new ImageActor(new Texture(crimsonTD.getInstance().resolvePath("mainmenu//mainmenu_settings_disabled.png")), 582, 616 - 280);
        stSFXON_E = new ImageActor(new Texture(crimsonTD.getInstance().resolvePath("mainmenu//mainmenu_settings_enabled.png")), 437, 616 - 363);
        stSFXON_D = new ImageActor(new Texture(crimsonTD.getInstance().resolvePath("mainmenu//mainmenu_settings_disabled.png")), 437, 616 - 363);
        stSFXOFF_E = new ImageActor(new Texture(crimsonTD.getInstance().resolvePath("mainmenu//mainmenu_settings_enabled.png")), 582, 616 - 363);
        stSFXOFF_D = new ImageActor(new Texture(crimsonTD.getInstance().resolvePath("mainmenu//mainmenu_settings_disabled.png")), 582, 616 - 363);
        stMusicON_E = new ImageActor(new Texture(crimsonTD.getInstance().resolvePath("mainmenu//mainmenu_settings_enabled.png")), 437, 616 - 450);
        stMusicON_D = new ImageActor(new Texture(crimsonTD.getInstance().resolvePath("mainmenu//mainmenu_settings_disabled.png")), 437, 616 - 450);
        stMusicOFF_E = new ImageActor(new Texture(crimsonTD.getInstance().resolvePath("mainmenu//mainmenu_settings_enabled.png")), 582, 616 - 450);
        stMusicOFF_D = new ImageActor(new Texture(crimsonTD.getInstance().resolvePath("mainmenu//mainmenu_settings_disabled.png")), 582, 616 - 450);
        stBloodON_string = new FontActor(crimsonTD.getInstance().resolvePath("nasalization-rg.ttf"), 36, fontColorWhite, "ON", 464, 616 - 244);
        stBloodOFF_string = new FontActor(crimsonTD.getInstance().resolvePath("nasalization-rg.ttf"), 36, fontColorWhite, "OFF", 607.5f, 616 - 244);
        stSFXON_string = new FontActor(crimsonTD.getInstance().resolvePath("nasalization-rg.ttf"), 36, fontColorWhite, "ON", 464, 616 - 326);
        stSFXOFF_string = new FontActor(crimsonTD.getInstance().resolvePath("nasalization-rg.ttf"), 36, fontColorWhite, "OFF", 607.5f, 616 - 326);
        stMusicON_string = new FontActor(crimsonTD.getInstance().resolvePath("nasalization-rg.ttf"), 36, fontColorWhite, "ON", 464, 616 - 414);
        stMusicOFF_string = new FontActor(crimsonTD.getInstance().resolvePath("nasalization-rg.ttf"), 36, fontColorWhite, "OFF", 607.5f, 616 - 414);
        settOKString = new FontActor(crimsonTD.getInstance().resolvePath("nasalization-rg.ttf"), 36, fontColorWhite, "APPLY", 510.7f, 616 - 484);

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


        settingsScreen = new Group();
        settingsScreen.addActor(settBG);
        settingsScreen.addActor(settOK);
        settingsScreen.addActor(stBloodON);
        settingsScreen.addActor(stBloodOFF);
        settingsScreen.addActor(stSFXON);
        settingsScreen.addActor(stSFXOFF);
        settingsScreen.addActor(stMusicON);
        settingsScreen.addActor(stMusicOFF);
        settingsScreen.addActor(stringBLOOD);
        settingsScreen.addActor(stringMUSIC);
        settingsScreen.addActor(stringSFXSOUND);
        settingsScreen.addActor(stringOPTIONS);
        settingsScreen.addActor(settOKString);

        settingsScreen.setVisible(false);
        settOK.addListener(new ShowMenu(settingsScreen));

        //PERKS
        perksBG = new ImageActor(new Texture(crimsonTD.getInstance().resolvePath("mainmenu//InventoryMenu2.png")), 346, 616 - 534);
        perksApply =  new ImageActor(new Texture(crimsonTD.getInstance().resolvePath("mainmenu//mainmenu_btn.png")), 473, 616 - 533);
        perkMS = new Perk(new TextureRegion(new Texture(crimsonTD.getInstance().resolvePath("perks/perk_ms.png"))),
                new TextureRegion(new Texture(crimsonTD.getInstance().resolvePath("perks/perk_ms_d.png"))),
                0, 0 , PerkType.MSUP);
        perkMS.setVisible(false);
        perkSS = new Perk(new TextureRegion(new Texture(crimsonTD.getInstance().resolvePath("perks/perk_ss.png"))),
                new TextureRegion(new Texture(crimsonTD.getInstance().resolvePath("perks/perk_ss_d.png"))),
                0, 0 , PerkType.SSUP);
        perkSS.setVisible(false);
        perkRS = new Perk(new TextureRegion(new Texture(crimsonTD.getInstance().resolvePath("perks/perk_rs.png"))),
                new TextureRegion(new Texture(crimsonTD.getInstance().resolvePath("perks/perk_rs_d.png"))),
                0, 0 , PerkType.RSUP);
        perkRS.setVisible(false);
        perkLvL = new Perk(new TextureRegion(new Texture(crimsonTD.getInstance().resolvePath("perks/perk_lvl.png"))),
                new TextureRegion(new Texture(crimsonTD.getInstance().resolvePath("perks/perk_lvl_d.png"))),
                0, 0 , PerkType.LVLUP);
        perkLvL.setVisible(false);
        perkAmmo = new Perk(new TextureRegion(new Texture(crimsonTD.getInstance().resolvePath("perks/perk_reload.png"))),
                new TextureRegion(new Texture(crimsonTD.getInstance().resolvePath("perks/perk_reload_d.png"))),
                0, 0 , PerkType.INFAMMO);
        perkAmmo.setVisible(false);
        perkFifty = new Perk(new TextureRegion(new Texture(crimsonTD.getInstance().resolvePath("perks/perk_fifty.png"))),
                new TextureRegion(new Texture(crimsonTD.getInstance().resolvePath("perks/perk_fifty_d.png"))),
                0, 0 , PerkType.FIFTYFIFTY);
        perkFifty.setVisible(false);
        perkFifty = new Perk(new TextureRegion(new Texture(crimsonTD.getInstance().resolvePath("perks/perk_fifty.png"))),
                new TextureRegion(new Texture(crimsonTD.getInstance().resolvePath("perks/perk_fifty_d.png"))),
                0, 0 , PerkType.FIFTYFIFTY);
        perkFifty.setVisible(false);
        perkFreeze = new Perk(new TextureRegion(new Texture(crimsonTD.getInstance().resolvePath("perks/perk_freeze.png"))),
                new TextureRegion(new Texture(crimsonTD.getInstance().resolvePath("perks/perk_freeze_d.png"))),
                0, 0 , PerkType.FREEZE);
        perkFreeze.setVisible(false);
        perkCamera = new Perk(new TextureRegion(new Texture(crimsonTD.getInstance().resolvePath("perks/perk_camera.png"))),
                new TextureRegion(new Texture(crimsonTD.getInstance().resolvePath("perks/perk_camera_d.png"))),
                0, 0 , PerkType.ZOOM);
        perkCamera.setVisible(false);
        perkInvul = new Perk(new TextureRegion(new Texture(crimsonTD.getInstance().resolvePath("perks/perk_invul.png"))),
                new TextureRegion(new Texture(crimsonTD.getInstance().resolvePath("perks/perk_invul_d.png"))),
                0, 0 , PerkType.INVUL);
        perkInvul.setVisible(false);
        perkHeal = new Perk(new TextureRegion(new Texture(crimsonTD.getInstance().resolvePath("perks/perk_heal.png"))),
                new TextureRegion(new Texture(crimsonTD.getInstance().resolvePath("perks/perk_heal_d.png"))),
                0, 0 , PerkType.HEAL);
        perkHeal.setVisible(false);
        perkDeath = new Perk(new TextureRegion(new Texture(crimsonTD.getInstance().resolvePath("perks/perk_death.png"))),
                new TextureRegion(new Texture(crimsonTD.getInstance().resolvePath("perks/perk_death_d.png"))),
                0, 0 , PerkType.DEATH);
        perkDeath.setVisible(false);
        perkArray = new Array<Perk>();
        perkArray.add(perkMS);
        perkArray.add(perkSS);
        perkArray.add(perkRS);
        perkArray.add(perkLvL);
        perkArray.add(perkAmmo);
        perkArray.add(perkFifty);
        perkArray.add(perkFreeze);
        perkArray.add(perkCamera);
        perkArray.add(perkInvul);
        perkArray.add(perkHeal);
        perkArray.add(perkDeath);
        /*
        for(int i = 0; i < perkArray.size - 1; i++)
        {
            perkArray.get(i).addListener(new PerksClick(perkArray.get(i)));
        }*/
        perkEnabled1 = new Perk(new TextureRegion(new Texture(crimsonTD.getInstance().resolvePath("arenaborders.png"))), new TextureRegion(new Texture(crimsonTD.getInstance().resolvePath("arenaborders.png"))), 0, 0, PerkType.NULL);
        perkEnabled2 = new Perk(new TextureRegion(new Texture(crimsonTD.getInstance().resolvePath("arenaborders.png"))), new TextureRegion(new Texture(crimsonTD.getInstance().resolvePath("arenaborders.png"))), 0, 0, PerkType.NULL);
        perkEnabled3 = new Perk(new TextureRegion(new Texture(crimsonTD.getInstance().resolvePath("arenaborders.png"))), new TextureRegion(new Texture(crimsonTD.getInstance().resolvePath("arenaborders.png"))), 0, 0, PerkType.NULL);
        perkEnabled1.setVisible(false);
        perkEnabled2.setVisible(false);
        perkEnabled3.setVisible(false);

        perksScreen = new Group();
        perksScreen.addActor(perksBG);
        perksScreen.addActor(perksApply);
        perksScreen.addActor(perkMS);
        perksScreen.addActor(perkSS);
        perksScreen.addActor(perkRS);
        perksScreen.addActor(perkLvL);
        perksScreen.addActor(perkAmmo);
        perksScreen.addActor(perkFifty);
        perksScreen.addActor(perkFreeze);
        perksScreen.addActor(perkCamera);
        perksScreen.addActor(perkInvul);
        perksScreen.addActor(perkHeal);
        perksScreen.addActor(perkDeath);
        perksScreen.addActor(perkEnabled1);
        perksScreen.addActor(perkEnabled2);
        perksScreen.addActor(perkEnabled3);
        perksScreen.setVisible(false);
        perksApply.addListener(new ApplyPerk(perksScreen));
        perkPos1 = new Vector2(389, 616 - 308);
        perkPos2 = new Vector2(514, 616 - 308);
        perkPos3 = new Vector2(641, 616 - 308);
        //settingsButton.addListener(new ShowMenu(settingsScreen));
        //settingsScreen = crimsonTD.getInstance().getSettingScreen();

        optionsbutton.addListener(new ShowMenu(settingsScreen));

        float aspectRatio = (float) Gdx.graphics.getWidth() / (float) Gdx.graphics.getHeight(); //camera viewport set
        camera.setToOrtho(false, 10f * aspectRatio, 10f);

        playerController = new PlayerController(world);
        cameraController = new CameraController(world);
        botController = new BotController(world);
        multiplexer = new InputMultiplexer();
//        world.getBot().setController(botController);
        //    world.getBot1().setController(botController);

        bulletsCounter = new Group();

                bulletsCounterBG = new ImageActor(new Texture(crimsonTD.getInstance().resolvePath("gamescreen_bulletsCount.png")), 1145 - 117, (int) world.getHeight() / 2);
                bulletsCounterCountersRifle = new ImageActor[30];
                lastBulletY = (int) bulletsCounterBG.getY() + 18;
                bulletsCounter.addActor(bulletsCounterBG);
                for (int i = 0; i < bulletsCounterCountersRifle.length; i++) {
                    // bulletsCounterCounters[i] = new ImageActor(new Texture(crimsonTD.getInstance().resolvePath("bullet.png"), bulletsCounterBG.getWidth() / 2, bulletsCounterBG.getHeight() + 10);
                    bulletsCounterCountersRifle[i] = new ImageActor(new Texture(crimsonTD.getInstance().resolvePath("bullet.png")), 1145 - bulletsCounterBG.getWidth() / 2, lastBulletY + 7, 25, 5);
                    lastBulletY = (int) bulletsCounterCountersRifle[i].getY();
                    bulletsCounter.addActor(bulletsCounterCountersRifle[i]);
                }

                //bulletsCounterBG = new ImageActor(new Texture(crimsonTD.getInstance().resolvePath("gamescreen_bulletsCount.png"), 1145 - 117, (int) world.getHeight() / 2);
                bulletsCounterCountersIcegun = new ImageActor[5];
                lastBulletY = (int) bulletsCounterBG.getY() - 10;
                bulletsCounter.addActor(bulletsCounterBG);
                for (int i = 0; i < bulletsCounterCountersIcegun.length; i++) {
                    // bulletsCounterCounters[i] = new ImageActor(new Texture(crimsonTD.getInstance().resolvePath("bullet.png"), bulletsCounterBG.getWidth() / 2, bulletsCounterBG.getHeight() + 10);
                    bulletsCounterCountersIcegun[i] = new ImageActor(new Texture(crimsonTD.getInstance().resolvePath("Iceball.png")), 1145 - bulletsCounterBG.getWidth() / 2, lastBulletY + 40, 40, 40);
                    lastBulletY = (int) bulletsCounterCountersIcegun[i].getY();
                    bulletsCounter.addActor(bulletsCounterCountersIcegun[i]);
                }

                bulletsCounterCountersThrower = new FlameTank(new Texture(crimsonTD.getInstance().resolvePath("arenaborders.png")), 1145 - bulletsCounterBG.getWidth() / 2, lastBulletY - 120, 58, 98);
                bulletsCounter.addActor(bulletsCounterCountersThrower);



        bulletsCounterBG.toBack();
        scoresBG = new ImageActor(new Texture(crimsonTD.getInstance().resolvePath("gamescreen_scores.png")), 411, 616 - 43);
        batch = new SpriteBatch();
        // touchpads
        touchpadSkin = new Skin();
        touchpadSkin.add("touchBackground", new Texture(crimsonTD.getInstance().resolvePath("joypad//touchBackgroundnew.png")));
        touchpadSkin.add("touchKnob", new Texture(crimsonTD.getInstance().resolvePath("joypad//touchKnobnew.png")));
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
        inventoryButtonGroup = new Group();
        inventoryButtonGroup.addActor(inventoryButton);
        inventoryButtonGroup.addActor(riflesignleft);
        inventoryButtonGroup.addActor(riflesignright);
        inventoryButtonGroup.addActor(riflesignmiddle);
        inventoryButtonGroup.addActor(stringInventory);
        inventoryButtonGroup.addListener(new ShowMenu(inventoryScreen));

        bulletsCounter.addListener(new SetReloading());
        /*
        transparentGroup = new Group();
        transparentGroup.addActor(bulletsCounter);
        transparentGroup.addActor(scoresBG);
        transparentGroup.addActor(stringScoresCount);
        transparentGroup.addActor(stringScore);
        transparentGroup.addActor(pauseButton);
        transparentGroup.addActor(inventoryButtonGroup);*/
        //inventoryButtonGroup.addAction(Actions.alpha(0.5f));
        UIstage.addActor(FPSlabel);
        if(crimsonTD.getInstance().getMovementControlStyle() == MovementControlStyle.JOYPAD) {
            UIstage.addActor(touchpadLeft);
            UIstage.addActor(touchpadRight);
        }
        UIstage.addActor(pauseButton);
        UIstage.addActor(pauseScreen);
        UIstage.addActor(bulletsCounter);
        UIstage.addActor(inventoryScreen);
        UIstage.addActor(scoresBG);
        UIstage.addActor(inventoryButtonGroup);
        UIstage.addActor(stringScore);
        UIstage.addActor(stringScoresCount);
        UIstage.addActor(deathScreen);
        UIstage.addActor(settingsScreen);
        UIstage.addActor(rifleEnabled);
        UIstage.addActor(icegunEnabled);
        UIstage.addActor(throwerEnabled);
        UIstage.addActor(perksScreen);
//        UIstage.addActor(transparentGroup);
        pauseButton.toFront();
        world.setBackgroundtoBack();
        //Multiplexer filling
        multiplexer.addProcessor(UIstage);
        multiplexer.addProcessor(world);
        multiplexer.addProcessor(playerController);
        multiplexer.addProcessor(cameraController);
        // multiplexer.addProcessor(botController);
        appleWeapon();
        debugRenderer.SHAPE_STATIC.set(new Color(Color.RED));
        debugRenderer.SHAPE_AWAKE.set(new Color(Color.RED));
        debugRenderer.SHAPE_NOT_ACTIVE.set(new Color(Color.GREEN));
        debugRenderer.SHAPE_NOT_AWAKE.set(new Color(Color.RED));
        debugRenderer.SHAPE_KINEMATIC.set(new Color(Color.RED));
        debugRenderer.VELOCITY_COLOR.set(new Color(Color.RED));
        //transparentGroup.addAction(Actions.alpha(0.5f));

    }

    private void shot() {

        // bullet = new ImageActor (new Texture(crimsonTD.getInstance().resolvePath("/bullet.png"), (int)(world.getPlayer().getX() + world.getPlayer().getWidth()*Math.cos((double)(world.getPlayer().getRotation()))),(int)(world.getPlayer().getY() + world.getPlayer().getHeight()*Math.sin((double)(world.getPlayer().getRotation()))), 14 , 3);
        // bullet = new ImageActor(new Texture(crimsonTD.getInstance().resolvePath("/bullet.png"), world.getPlayer().getX(), world.getPlayer().getY(), 14, 3);
        switch (world.getPlayer().getCurrentWeapon()) {
            case ASSAULTRIFLE:/*
                bullet = new Bullet(new Texture(crimsonTD.getInstance().resolvePath("/bullet.png"),
                        world.getPlayer().getX(),
                        world.getPlayer().getY(),
                        14f, 3f,
                        world.getPhysicsWorld(),
                        BulletType.BULLET); //DEFAULT : 14X3*/
                bullet = new Bullet((new Texture(crimsonTD.getInstance().resolvePath("/bullet.png"))),
                        world.getPlayer().getX() + world.getPlayer().getOriginX()
                                + 70 * (float) Math.cos(Math.toRadians(world.getPlayer().getRotation()))
                                + 10 * (float) Math.sin(Math.toRadians(world.getPlayer().getRotation())),
                        world.getPlayer().getY() + world.getPlayer().getOriginY()
                                + 70 * (float) Math.sin(Math.toRadians(world.getPlayer().getRotation()))
                                - 10 * (float) Math.cos(Math.toRadians(world.getPlayer().getRotation())),
                        14f,3f,
                        world.getPhysicsWorld(),
                        BulletType.BULLET

                );
               /*
                bullet = new Bullet(new Texture(crimsonTD.getInstance().resolvePath("/fire1.png"),
                        world.getPlayer().getX(),
                        world.getPlayer().getY(),
                        100f, 62f,
                        world.getPhysicsWorld(),
                        BulletType.BULLET);
                        */
                break;
            case ICERIFLE:
                //bullet = new Bullet(new Texture(crimsonTD.getInstance().resolvePath("/Iceball.png"), world.getPlayer().getX(), world.getPlayer().getY(), 12, 12, world.getPhysicsWorld(), BulletType.ICEBALL);
                bullet = new Bullet((new Texture(crimsonTD.getInstance().resolvePath("/Iceball.png"))),
                        world.getPlayer().getX() + world.getPlayer().getOriginX()
                                + 70 * (float) Math.cos(Math.toRadians(world.getPlayer().getRotation()))
                                + 10 * (float) Math.sin(Math.toRadians(world.getPlayer().getRotation())),
                        world.getPlayer().getY() + world.getPlayer().getOriginY()
                                + 70 * (float) Math.sin(Math.toRadians(world.getPlayer().getRotation()))
                                - 10 * (float) Math.cos(Math.toRadians(world.getPlayer().getRotation())),
                        12f,12f,
                        world.getPhysicsWorld(),
                        BulletType.ICEBALL

                );
                break;
            case FLAMETHROWER:
                break;
        }
        bulletsShooted++;
        if(bullet.getBulletType() == BulletType.FLAME){
        bullet.setPosition(
                world.getPlayer().getX() + world.getPlayer().getOriginX()
                        + 70 * (float) Math.cos(Math.toRadians(world.getPlayer().getRotation()))
                        + 10 * (float) Math.sin(Math.toRadians(world.getPlayer().getRotation())),
                world.getPlayer().getY() + world.getPlayer().getOriginY()
                        + 70 * (float) Math.sin(Math.toRadians(world.getPlayer().getRotation()))
                        - 10 * (float) Math.cos(Math.toRadians(world.getPlayer().getRotation()))
        );}


        // bullet.setPosition(world.getPlayer().getX() + world.getPlayer().getWidth() * (float) Math.cos(Math.toRadians(world.getPlayer().getBody().getAngle())),
        //   world.getPlayer().getY() + world.getPlayer().getHeight()  * (float) Math.sin(Math.toRadians(world.getPlayer().getBody().getAngle())));

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
        switch(world.getPlayer().getCurrentWeapon()) {
            case ASSAULTRIFLE: if (bulletsLeft >= 0)
                bulletsCounterCountersRifle[bulletsLeft].setVisible(false);

               // System.out.println(bulletsLeft);
                break;
            case ICERIFLE: if (bulletsLeft >= 0)
                bulletsCounterCountersIcegun[bulletsLeft].setVisible(false);
                //System.out.println(bulletsLeft);
                break;
            case FLAMETHROWER:
            if (bulletsLeft >= 0)
                bulletsCounterCountersThrower.update(bulletsLeft, bulletsCountDefault);
                //System.out.println(bulletsLeft);
                break;
        }

    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(multiplexer);
        batch = new SpriteBatch();
    }

    public void reloading(float delta) {

        //bulletsLeft = bulletsCountDefault;
        if(isReloading)
        {
            timereloading+= delta;
            //System.out.println(timereloading);
        }
        if(timereloading >= reloadtimer) {
            isReloading = false;
            timereloading = 0;
            switch (world.getPlayer().getCurrentWeapon()) {
                case ASSAULTRIFLE:
                    if (world.getReloadAnimation().isFinishedOnce() == true) {
                        for (int i = 0; i < bulletsCounterCountersRifle.length; i++) {
                            bulletsCounterCountersRifle[i].setVisible(true);
                        }
                       // bulletsCounterCountersThrower.setVisible(false);
                        bulletsLeft = bulletsCountDefault;
                        world.getReloadAnimation().setFinishedOnce(false);
                        break;
                    }
                case ICERIFLE:
                    if (world.getReloadAnimation_icegun().isFinishedOnce() == true) {
                        for (int i = 0; i < bulletsCounterCountersIcegun.length; i++) {
                            bulletsCounterCountersIcegun[i].setVisible(true);
                        }
                       // bulletsCounterCountersThrower.setVisible(false);
                        bulletsLeft = bulletsCountDefault;
                        world.getReloadAnimation_icegun().setFinishedOnce(false);
                        break;
                    }
                case FLAMETHROWER:
                    if (world.getReloadAnimation_thrower().isFinishedOnce() == true) {
                        bulletsLeft = bulletsCountDefault;
                        bulletsCounterCountersThrower.setVisible(true);
                        bulletsCounterCountersThrower.update(bulletsLeft, bulletsCountDefault);
                        world.getReloadAnimation_thrower().setFinishedOnce(false);
                        break;
                    }
            }


        }
    }

    @Override
    public void render(float delta) {
    //System.out.println(twoActorsCross(world.getPlayer(), bulletsCounter));
        if (!inventoryScreen.isVisible() && !pauseScreen.isVisible() && !deathScreen.isVisible() && !perksScreen.isVisible()) {
            spawningBots(delta);
            isBotNearPlayer();
            try {
                botsAttacking();
            } catch (IOException e) {
                e.printStackTrace();
            }
            reloading(delta);
            switchingweapons();
        }
        world.getReloadAnimation().setFinishedOnce(true);
        //isBotNearPlayer();
        if (!inventoryScreen.isVisible() && !pauseScreen.isVisible()  && !deathScreen.isVisible() && !perksScreen.isVisible()) {
        world.getPhysicsWorld().step(delta, 6, 2);}
        currentBot++;
        if (currentBot > world.getBotscount() - 1)
            currentBot = 1;

        stringScoresCount.update(String.format("%07d", crimsonTD.getInstance().getScore()));
       // System.out.println(isReloading);
        //System.out.println(Gdx.input.isKeyPressed(Input.Keys.R) && bulletsLeft < bulletsCountDefault);
        if (Gdx.input.isKeyPressed(Input.Keys.R) && bulletsLeft != bulletsCountDefault || bulletsLeft == 0) {
            isReloading = true;
        }
        if (!inventoryScreen.isVisible() && !pauseScreen.isVisible() && !deathScreen.isVisible() && !perksScreen.isVisible())
        animationsUpdate(delta);

        switch (world.getPlayer().getCurrentWeapon()) {
            case ASSAULTRIFLE:
                if(isReloading == false) {
                    world.playerUpdate(world.getMoveAnimation(), delta);
                }
                else
                {
                    world.playerUpdate(world.getReloadAnimation(), delta);
                }
                break;
            case ICERIFLE:
                if(isReloading == false) {
                    world.playerUpdate(world.getMoveAnimation_icegun(), delta);
                }
                else
                {
                    world.playerUpdate(world.getReloadAnimation_icegun(), delta);
                }
                break;
            case FLAMETHROWER:
                if(isReloading == false) {
                    world.playerUpdate(world.getMoveAnimation_thrower(), delta);
                }
                else
                {
                    world.playerUpdate(world.getReloadAnimation_thrower(), delta);
                }
                break;
        }

        world.getHPcircle().update(world.getPlayer().getCurrentHP(), world.getPlayer().getMaxHP());
        // world.botUpdate(world.getBot(), delta);
        world.botsUpdate(currentBot, delta, world.getBotbyIndex(currentBot).getCurrentState());
        if (!pauseScreen.isVisible() && !inventoryScreen.isVisible()) {
            playerController.update(world);
            cameraController.update(world);
            botController.update(world);
        }
     /*
       FPSbuilder = new StringBuilder();
        FPSbuilder.append(" FPS: ").append(Gdx.graphics.getFramesPerSecond()); //fps label
        FPSlabel.setText(FPSbuilder);*/





        world.getPlayer().rotateBy(touchpadRight.getKnobPercentX() * -5);
        world.getPlayer().setBoxRotation((float) Math.toRadians(touchpadRight.getKnobPercentX() * -5));

        switch (inventoryCurrentChoose) {
            case 1:
                m4a4hud.setVisible(true);
                icegunhud.setVisible(false);
                // firegunhud.setVisible(false);
                throwerhud.setVisible(false);
                rifleInfo.setVisible(true);
                iceInfo.setVisible(false);
                flameInfo.setVisible(false);
                break;
            case 2:
                icegunhud.setVisible(true);
                m4a4hud.setVisible(false);
                //   firegunhud.setVisible(false);
                throwerhud.setVisible(false);
                rifleInfo.setVisible(false);
                iceInfo.setVisible(true);
                flameInfo.setVisible(false);
                break;
            case 3:
                icegunhud.setVisible(false);
                m4a4hud.setVisible(false);
                throwerhud.setVisible(true);
                rifleInfo.setVisible(false);
                iceInfo.setVisible(false);
                flameInfo.setVisible(true);
                break;
        }
        if(isReloading == false){
        switch (world.getPlayer().getCurrentWeapon()) {
            case ASSAULTRIFLE:
                if (Gdx.input.isKeyPressed(Input.Keys.Q) || (touchpadRight.getKnobPercentX() != 0) || touchpadRight.getKnobPercentY() != 0) { //System.out.println("Shooting..");
                    if (TimeUtils.nanoTime() - shotTime > 100000000 && bulletsLeft != 0) {      // default: 200000000
                        //System.out.println("Shooting..");
                        shot();
                    }
                }
                break;
            case ICERIFLE:
                if (Gdx.input.isKeyPressed(Input.Keys.Q) || (touchpadRight.getKnobPercentX() != 0) || touchpadRight.getKnobPercentY() != 0) { //System.out.println("Shooting..");
                    if (TimeUtils.nanoTime() - shotTime > 1000000000 && bulletsLeft != 0) {      // default: 200000000
                        //System.out.println("Shooting..");
                        shot();
                    }
                }break;
            case FLAMETHROWER:
                if (Gdx.input.isKeyPressed(Input.Keys.Q) || (touchpadRight.getKnobPercentX() != 0) || touchpadRight.getKnobPercentY() != 0) { //System.out.println("Shooting..");
                    if (TimeUtils.nanoTime() - shotTime > 1000000000 && bulletsLeft != 0) {      // default: 200000000
                        //System.out.println("Shooting..");
                        shot();
                    }
                }break;
        }}

        for (int i = 0; i < bullets.size; i++) {
            // System.out.println("Shooting..");
            //bullets.get(i).setY(bullets.get(i).getY() + 10);
            if(bullets.get(i).getBulletType() == BulletType.FLAME || world.getPlayer().getCurrentWeapon() == Weapon.FLAMETHROWER) {
                bullets.get(i).setTexture(world.getFlameAnimation().getFrame());
                if((Gdx.input.isKeyPressed(Input.Keys.Q) || (touchpadRight.getKnobPercentX() != 0) || touchpadRight.getKnobPercentY() != 0) && bulletsLeft > 0) {
                    bullets.get(i).setVisible(true);
                bullets.get(i).getBox().setActive(true);
                }
                else
                {
                   bullets.get(i).getBox().setType(BodyDef.BodyType.StaticBody);
                    bullets.get(i).getBox().setActive(false);
                    bullets.get(i).remove();

                }
            }
            //System.out.println(bullets.get(i).getBox());
            if(bullets.get(i).getBulletType() != BulletType.FLAME) {
                bullets.get(i).moveBy(bulletSpeed * delta * (float) Math.cos(bullets.get(i).getRotation() / 180 * Math.PI), bulletSpeed * delta * (float) Math.sin(bullets.get(i).getRotation() / 180 * Math.PI));
            }
            else
            {
                bullets.get(i).setPosition(
                        world.getPlayer().getX() + world.getPlayer().getOriginX()
                                + 80 * (float) Math.cos(Math.toRadians(world.getPlayer().getRotation()))
                                + 10 * (float) Math.sin(Math.toRadians(world.getPlayer().getRotation())),
                        world.getPlayer().getY() + world.getPlayer().getOriginY()
                                + 80 * (float) Math.sin(Math.toRadians(world.getPlayer().getRotation()))
                                - 10 * (float) Math.cos(Math.toRadians(world.getPlayer().getRotation()))
                );
                bullets.get(i).setRotation(world.getPlayer().getRotation());
            }
            //bullets.get(i).getBox().setLinearVelocity(bulletSpeed * delta *(float) Math.cos(Math.toRadians(bullets.get(i).getRotation())), bulletSpeed *delta * (float) Math.sin(Math.toRadians(bullets.get(i).getRotation())));
            if (bullets.get(i).getY() >= 1000) {
                bullets.get(i).remove();
                bullets.get(i).getBox().setActive(false);
                bullets.removeIndex(i);

            }
        }
        world.draw();
        UIstage.draw();
        //world.getHPcircle().setVisible(false);
        if (!inventoryScreen.isVisible() && !pauseScreen.isVisible() && !perksScreen.isVisible()) {
            world.act(Gdx.graphics.getDeltaTime());
            UIstage.act(Gdx.graphics.getDeltaTime());
            world.getPhysicsWorld().step(0, 0 ,0);
        }

        batch.begin();
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
        dispose();
    }

    public boolean isObjectTouched(Actor actor) {
        if (Gdx.input.isTouched() && (Gdx.input.getX() > actor.getX() && Gdx.input.getX() < actor.getWidth() + actor.getX()) && (Gdx.input.getY() < actor.getY() && Gdx.input.getY() > actor.getY() + actor.getHeight()))
            return true;
        return false;
    }

    public float getLeftTouchpadKnobX() {
        return touchpadLeft.getKnobPercentX();
    }

    public float getLeftTouchpadKnobY() {
        return touchpadLeft.getKnobPercentY();
    }

    public float getRightTouchpadKnobX() {
        return touchpadRight.getKnobPercentX();
    }

    public float getRightTouchpadKnobY() {
        return touchpadRight.getKnobPercentY();
    }

    public void isBotNearPlayer() {


        for (int i = 1; i < world.getBotscount(); i++) {
            //System.out.println((Math.abs(world.getBotbyIndex(i).getX() - world.getPlayer().getX()))+ " " + (Math.abs(world.getBotbyIndex(i).getY() - world.getPlayer().getY())));
            if ((Math.abs(world.getBotbyIndex(i).getX() - world.getPlayer().getX()) < 70) && (Math.abs(world.getBotbyIndex(i).getY() - world.getPlayer().getY()) < 70) && world.getBotbyIndex(i).getCurrentState() != ObjectState.DISABLED) {
                world.getBotbyIndex(i).setNearplayer(true);
            } else {
                world.getBotbyIndex(i).setNearplayer(false);
            }

            //System.out.println("BOT # " + i + " NEAR PLAYER?: "+world.getBotbyIndex(i).isNearPlayer());
        }

    }

    public LevelWorld getWorld() {
        return world;
    }

    public void botsAttacking() throws IOException {
        for (int i = 1; i < world.getBotscount(); i++) {
            if (world.getBotbyIndex(i).getCurrentState() == ObjectState.ATTACKING) {
                world.getPlayer().setCurrentHP(world.getPlayer().getCurrentHP() - 10);
                if (world.getPlayer().getCurrentHP() <= 0)
                    crimsonTD.getInstance().showDeathScreen();
            }
        }
        //System.out.println(world.getPlayer().getCurrentHP());
    }

    public GameDifficulty getGameDifficulty() {
        return gameDifficulty;
    }

    public void setGameDifficulty(GameDifficulty gameDifficulty) {
        this.gameDifficulty = gameDifficulty;
    }

    public void setInventoryCurrentChoose(int choose) {
       // System.out.println("CHOOSED "+choose);
        inventoryCurrentChoose = choose;
    }

    public int getInventoryCurrentChoose() {
        return inventoryCurrentChoose;
    }

    private void spawningBots(float delta)
    {

        nextspawn += delta;
        if (nextspawn >= spawntime) {
            nextspawn = 0;
            getWorld().spawnbot(delta);
            if (spawntime > 1) {
                spawntime -= 0.1f;
            }

        }
    }
    private void animationsUpdate(float delta)
    {
        world.getReloadAnimation().update(delta);
        world.getReloadAnimation_icegun().update(delta);
        world.getMoveAnimation().update(delta);
        world.getMoveAnimation_icegun().update(delta);

        world.getZombieMoveAnimation().update(delta);
        world.getZombieAttackAnimation().update(delta);
        world.getZombieSpawnAnimation().update(delta);

        world.getZombieRangeMoveAnimation().update(delta);
        world.getZombieRangeAttackAnimation().update(delta);
        world.getZombieRangeSpawnAnimation().update(delta);

        world.getZombieDoctorMoveAnimation().update(delta);
        world.getZombieDoctorAttackAnimation().update(delta);
        world.getZombieDoctorSpawnAnimation().update(delta);

        world.getZombiePyroMoveAnimation().update(delta);
        world.getZombiePyroAttackAnimation().update(delta);
        world.getZombiePyroSpawnAnimation().update(delta);

        world.getZombiePudgeMoveAnimation().update(delta);
        world.getZombiePudgeAttackAnimation().update(delta);
        world.getZombiePudgeSpawnAnimation().update(delta);

        world.getZombieHulkMoveAnimation().update(delta);
        world.getZombieHulkAttackAnimation().update(delta);
        world.getZombieHulkSpawnAnimation().update(delta);

        world.getZombieWitchMoveAnimation().update(delta);
        world.getZombieWitchAttackAnimation().update(delta);
        world.getZombieWitchSpawnAnimation().update(delta);

        world.getMoveAnimation_thrower().update(delta);
        world.getReloadAnimation_thrower().update(delta);
        world.getFlameAnimation().update(delta);
    }

    public void appleWeapon()
    {
        switch(world.getPlayer().getCurrentWeapon())
        {
            case ASSAULTRIFLE:
            {
                bulletsCountDefault = 30;
                bulletsLeft = 30;
                inventoryCurrentChoose = 1;
                for (int i = 0; i < bulletsCounterCountersRifle.length; i++) {
                    bulletsCounterCountersRifle[i].setVisible(true);
                }
                for (int i = 0; i < bulletsCounterCountersIcegun.length; i++) {
                    bulletsCounterCountersIcegun[i].setVisible(false);
                }
                bulletsCounterCountersThrower.setVisible(false);
                rifleEnabled.setVisible(true);
                icegunEnabled.setVisible(false);
                throwerEnabled.setVisible(false);
            } break;
            case ICERIFLE:
            {
                bulletsCountDefault = 5;
                bulletsLeft = 5;
                inventoryCurrentChoose = 2;
                for (int i = 0; i < bulletsCounterCountersRifle.length; i++) {
                    bulletsCounterCountersRifle[i].setVisible(false);
                }
                for (int i = 0; i < bulletsCounterCountersIcegun.length; i++) {
                    bulletsCounterCountersIcegun[i].setVisible(true);
                }
                bulletsCounterCountersThrower.setVisible(false);
                rifleEnabled.setVisible(false);
                icegunEnabled.setVisible(true);
                throwerEnabled.setVisible(false);
            } break;
            case FLAMETHROWER:
                bulletsCountDefault = 8;
                bulletsLeft = 8;
                inventoryCurrentChoose = 3;
                for (int i = 0; i < bulletsCounterCountersRifle.length; i++) {
                    bulletsCounterCountersRifle[i].setVisible(false);
                }
                for (int i = 0; i < bulletsCounterCountersIcegun.length; i++) {
                    bulletsCounterCountersIcegun[i].setVisible(false);
                }
                bulletsCounterCountersThrower.setVisible(true);
                bulletsCounterCountersThrower.update(bulletsLeft, bulletsCountDefault);
                rifleEnabled.setVisible(false);
                icegunEnabled.setVisible(false);
                throwerEnabled.setVisible(true);
                break;
        }
    }

    public void deathScreen() throws IOException {
        FontActor[] deathLB  = {deathLB1, deathLB2, deathLB3, deathLB4};
        //String s = (String)crimsonTD.getInstance().getScore();
        /*
        for(int i = 0; i <leaderboards.length; i++)
        {
            fw.append(Integer.toString(100 * i) + "\r\n");
        }
       */
        //fw = new FileWriter(new File(crimsonTD.getInstance().resolvePath("stats//stats.txt"), true);

        //THERE WAS FILEWRITER

        //fw.append(Integer.toString(crimsonTD.getInstance().getScore()) + "\r\n");
        deathScreen.setVisible(true);
        //fw.close();
      //  sc.reset();
       // System.out.println(sc.next());


        for(int i = 0; i < leaderboards.length; i++)
        {
          /*  if(br.readLine() != null)
            {
                leaderboards[i] = Integer.valueOf(br.readLine());
            }*/
        sc = new Scanner(crimsonTD.getInstance().resolvePath("stats//stats.txt"));
           // sc.reset();
       // System.out.println(sc.hasNextInt());
            if(sc.hasNextInt()) {
                leaderboards[i] = Integer.valueOf(sc.nextInt());
            }
            else
            {
                leaderboards[i] = 0;
            }
        }
        sc.close();
        //br.close();
        Arrays.sort(leaderboards);
        for(int i = 0; i < leaderboards.length; i++)
        {
            if(crimsonTD.getInstance().getScore() > leaderboards[i])
            {
                leaderboards[i] = crimsonTD.getInstance().getScore();
            i = leaderboards.length;}
            //System.out.println(leaderboards[i]);
        }
        Arrays.sort(leaderboards);

        fw = new FileWriter(new File(crimsonTD.getInstance().resolvePath("stats//stats.txt")));

        for(int i = 0; i <leaderboards.length; i++)
        {
            fw.append(Integer.toString(leaderboards[i])  + "\r\n");
        }

        fw.close();

        //sc.close();
        /*
        Arrays.sort(leaderboards);
        for(int i = 0; i < leaderboards.length; i++)
        {
            if(crimsonTD.getInstance().getScore() > leaderboards[i])
                leaderboards[i] = crimsonTD.getInstance().getScore();
        i = leaderboards.length;
            //System.out.println(leaderboards[i]);
        }*/
        Arrays.sort(leaderboards);
        for(int i = 0; i < leaderboards.length; i++)
        {
            if (leaderboards[i] == crimsonTD.getInstance().getScore())
            {
                currentscoreindex = i;
            }
        }
        for(int i = 0; i < leaderboards.length; i++)
        {
          //  System.out.println(leaderboards[i]);
        }

        sc = new Scanner(new File(crimsonTD.getInstance().resolvePath("stats//stats_strings.txt")));
        for(int i = 0; i < deathLB.length; i++)
        {
            if(sc.hasNextLine())
            {
                deathLB[i].update(sc.nextLine());
               //System.out.println(sc.next());
            }
        }
        sc.close();
/*
        deathLB1.update("1. JOHN - " + String.format("%07d", leaderboards[9]));
        deathLB2.update("2. ADAM - " + String.format("%07d", leaderboards[8]));
        deathLB3.update("3. JAMES - " + String.format("%07d", leaderboards[7]));
        deathLB4.update("4. ANDREW - " + String.format("%07d", leaderboards[6]));*/
        //Arrays.sort(leaderboards);
        switch(currentscoreindex)
        {
            case 9:
                //leaderboards[6] = leaderboards[7];
               // leaderboards[7] = leaderboards[8];
             //   leaderboards[8] = leaderboards[9];
                deathLB1.update("1. YOU - " + String.format("%07d", leaderboards[9]), Color.RED);
                break;
            case 8:
               // leaderboards[6] = leaderboards[7];
             //   leaderboards[7] = leaderboards[8];
                deathLB2.update("2. YOU - " + String.format("%07d", leaderboards[8]), Color.RED);
                break;
            case 7:
            //    leaderboards[6] = leaderboards[7];
                deathLB3.update("3. YOU - " + String.format("%07d", leaderboards[7]), Color.RED);
                break;
            case 6: deathLB4.update("4. YOU - " + String.format("%07d", leaderboards[6]), Color.RED);
                break;
        }

        fw = new FileWriter(new File(crimsonTD.getInstance().resolvePath("stats//stats_strings.txt")));
        fw.append(deathLB1.getText() + "\n"
                +deathLB2.getText() + "\n"
                +deathLB3.getText() + "\n"
                +deathLB4.getText() + "\n");
        fw.close();
        /*
        if(sc1.hasNextInt())
            temp = sc1.nextInt();
        //temp++;

        deathScreen.setVisible(true);
        fw.append((temp + 1) + ". " + crimsonTD.getInstance().getScore() + " - " + dateFormat.format(date) + "\r\n");
        fw.close();
        //fw.close();
        if(sc.hasNext())
        {

        }*/
    }
    public void switchingweapons()
    {
        if(Gdx.input.isKeyPressed(Input.Keys.NUM_1))
        {
            crimsonTD.getInstance().setWeapon(Weapon.ASSAULTRIFLE);
            crimsonTD.getInstance().applyWeapon();
        }
        if(Gdx.input.isKeyPressed(Input.Keys.NUM_2))
        {
            crimsonTD.getInstance().setWeapon(Weapon.ICERIFLE);
            crimsonTD.getInstance().applyWeapon();
        }
        if(Gdx.input.isKeyPressed(Input.Keys.NUM_3))
        {
            crimsonTD.getInstance().setWeapon(Weapon.FLAMETHROWER);
            crimsonTD.getInstance().applyWeapon();
        }
    }
    public void transparencyController()
    {
       // if(world.getPlayer().getPosition().x > bulletsCounter.getX() && world.getPlayer().getPosition().x > bulletsCounter.getX());
    }
    public boolean twoActorsCross(ImageActor actor1, Group actor2)
    {
        if(actor1.getX() > actor2.getX() && actor1.getY() > actor2.getY()
                && actor1.getX() + actor1.getWidth() < actor2.getX() + actor2.getWidth()
                && actor1.getY() + actor1.getHeight() < actor2.getY() + actor2.getHeight())
        {
            return true;
        }
        return false;
    }
    public void setReloading(boolean toSet)
    {
        isReloading = toSet;
    }

    public void LvlUp()
    {
        perksScreen.setVisible(true);
        randomPerk = (int)(Math.random() * 11);
        randomTemp = randomPerk;
        perkEnabled1 = perkArray.get(randomPerk);
        perkEnabled1.setVisible(true);
        perkEnabled1.setPosition(perkPos1.x, perkPos1.y);
       // perkArray.get(randomPerk).setVisible(true);
        //perkArray.get(randomPerk).setPosition(perkPos1.x, perkPos1.y);
        randomPerk = (int)(Math.random() * 11);
        while (randomTemp == randomPerk)
            randomPerk = (int)(Math.random() * 11);
        randomTemp = randomPerk;
        perkEnabled2 = perkArray.get(randomPerk);
        perkEnabled2.setVisible(true);
        perkEnabled2.setPosition(perkPos2.x, perkPos2.y);
        //perkArray.get(randomPerk).setVisible(true);
        //perkArray.get(randomPerk).setPosition(perkPos2.x, perkPos2.y);
        randomPerk = (int)(Math.random() * 11);
        while (randomTemp == randomPerk)
            randomPerk = (int)(Math.random() * 11);
        randomTemp = randomPerk;
        perkEnabled3 = perkArray.get(randomPerk);
        perkEnabled3.setVisible(true);
        perkEnabled3.setPosition(perkPos3.x, perkPos3.y);
// perkArray.get(randomPerk).setVisible(true);
        // perkArray.get(randomPerk).setPosition(perkPos3.x, perkPos3.y);
        perkEnabled1.addListener(new PerksClick(perkEnabled1, perkEnabled2, perkEnabled3));
        perkEnabled2.addListener(new PerksClick(perkEnabled2, perkEnabled1, perkEnabled3));
        perkEnabled3.addListener(new PerksClick(perkEnabled3, perkEnabled2, perkEnabled1));

    }
    public Array<Perk> getPerkArray()
    {
        return perkArray;
    }
    public void ClosingPerksScreen()
    {
        for(int i = 0; i < perkArray.size - 1; i++)
            perkArray.get(i).setVisible(false);
        perkEnabled1.setVisible(false);
        perkEnabled2.setVisible(false);
        perkEnabled3.setVisible(false);
    }
}

