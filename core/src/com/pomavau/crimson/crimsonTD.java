package com.pomavau.crimson;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.pomavau.crimson.Controller.GameDifficulty;
import com.pomavau.crimson.Controller.MovementControlStyle;
import com.pomavau.crimson.Controller.Weapon;
import com.pomavau.crimson.Screens.GameScreen;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Scanner;

public class crimsonTD extends Game {
	private float ppuX, ppuY;
	private MovementControlStyle movementControlStyle;
	boolean soundState;
	private SpriteBatch batch;
	private com.pomavau.crimson.Screens.MainMenuScreen menuScreen;
	private com.pomavau.crimson.Screens.GameScreen gameScreen;
	HashMap<Integer, TextureRegion> textureRegions;
	private ShapeRenderer shape;
	private BitmapFont font;
	private static crimsonTD instance = new crimsonTD();
	private boolean firstlaunch = true;

	private boolean bloodEnabled = true;
	Scanner choosePlatform;

	public static crimsonTD getInstance() {
		return instance;
	}

	private crimsonTD() {}
	public void setMovementControlStyle(MovementControlStyle value) {movementControlStyle = value;}
	public MovementControlStyle getMovementControlStyle() {
		return movementControlStyle;
	}
	public void setSoundState(boolean soundState){this.soundState = soundState;}
	public boolean getSoundState(){return soundState;}

	@Override
	public void create() {
	choosePlatform = new Scanner(System.in);
		/*
		System.out.println("CHOOSE YOUR PLATFORM:\n1 - PC\n2 - ANDROID/IOS");
		switch (choosePlatform.nextInt())
		{
			case 1: setMovementControlStyle(MovementControlStyle.BUTTONS); break;
			case 2: setMovementControlStyle(MovementControlStyle.JOYPAD); break;
		}*/
		setMovementControlStyle(MovementControlStyle.BUTTONS);
		//setMovementControlStyle(MovementControlStyle.JOYPAD);
		//setMovementControlStyle(MovementControlStyle.JOYPAD);
		ppuX = Gdx.graphics.getWidth()/1145;
		ppuY = Gdx.graphics.getHeight()/616;
		loadGraphics();
		batch = new SpriteBatch();
		shape = new ShapeRenderer();
		font = new BitmapFont();
		try {
			menuScreen = new com.pomavau.crimson.Screens.MainMenuScreen(batch);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		try {
			gameScreen = new com.pomavau.crimson.Screens.GameScreen(batch, shape, font, textureRegions);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}


		setScreen(menuScreen);
		//setScreen(gameScreen);
	}

	private void loadGraphics() {
		/*
		textureRegions = new HashMap<Integer, TextureRegion>();
		Texture atlas = new Texture(resolvePath("heromove_atlas.png"));
		//southnortheastwest
		textureRegions.put(0, new TextureRegion(atlas, 0, 0, 313, 206));
		textureRegions.put(1, new TextureRegion(atlas, 313, 0, 313, 206));
		textureRegions.put(2, new TextureRegion(atlas, 626, 0, 313, 206));
		textureRegions.put(3, new TextureRegion(atlas, 939, 0, 313, 206));
		textureRegions.put(4, new TextureRegion(atlas, 1252, 0, 313, 206));
		textureRegions.put(5, new TextureRegion(atlas, 0, 206, 313, 206));
		textureRegions.put(6, new TextureRegion(atlas, 313, 206, 313, 206));
		textureRegions.put(7, new TextureRegion(atlas, 626, 206, 313, 206));
		textureRegions.put(8, new TextureRegion(atlas, 939, 206, 313, 206));
		textureRegions.put(9, new TextureRegion(atlas, 1252, 206, 313, 206));
		textureRegions.put(10, new TextureRegion(atlas, 0, 412, 313, 206));
		textureRegions.put(11, new TextureRegion(atlas, 313, 412, 313, 206));
		textureRegions.put(12, new TextureRegion(atlas, 626, 412, 313, 206));
		textureRegions.put(13, new TextureRegion(atlas, 939, 412, 313, 206));
		textureRegions.put(14, new TextureRegion(atlas, 1252, 412, 313, 206));
		textureRegions.put(15, new TextureRegion(atlas, 0, 618, 313, 206));
		textureRegions.put(16, new TextureRegion(atlas, 313, 618, 313, 206));
		textureRegions.put(17, new TextureRegion(atlas, 626, 618, 313, 206));
		textureRegions.put(18, new TextureRegion(atlas, 939, 618, 313, 206));
		textureRegions.put(19, new TextureRegion(atlas, 1252, 618, 313, 206));*/

	}
	public void showGame() {
		if(firstlaunch)
		{
			setScreen(gameScreen);
			firstlaunch = false;
		}
		else {
			try {
				gameScreen = new com.pomavau.crimson.Screens.GameScreen(batch, shape, font, textureRegions);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			setScreen(gameScreen);
		}
	}

	public void showMenu() {
		setScreen(menuScreen);
	}

	public void showMenu(Group group) {
		if(group.isVisible())
		{group.setVisible(false);}
		else {group.setVisible(true); }

		//System.out.println("screen status changed");
	}

	public String resolvePath (String fileName){
		if(Gdx.app.getType() == Application.ApplicationType.iOS || Gdx.app.getType() == Application.ApplicationType.Android)
		{
			return fileName;
		}
		else
		{
			return String.format("android/assets/%s", fileName);
		}
	}

	public boolean getMenuVisibility(Group group)
	{
		return group.isVisible();
	}

	@Override
	public void render(){

	super.render();
	}

	public float getPpuY() {
		return ppuY;
	}

	public void setPpuY(float ppuY) {
		this.ppuY = ppuY;
	}

	public float getPpuX() {
		return ppuX;
	}

	public void setPpuX(float ppuX) {
		this.ppuX = ppuX;
	}

	public float getLeftTouchpadKnobX()
	{
		return gameScreen.getLeftTouchpadKnobX();
	}
	public float getLeftTouchpadKnobY()
	{
		return gameScreen.getLeftTouchpadKnobY();
	}

	public float getRightTouchpadKnobX()
	{
		return gameScreen.getRightTouchpadKnobX();
	}
	public float getRightTouchpadKnobY()
	{
		return gameScreen.getRightTouchpadKnobY();
	}

	public void spawnbots()
	{
		gameScreen.getWorld().spawnbot(Gdx.graphics.getDeltaTime());
	}

	public void setGameDifficulty(GameDifficulty gameDifficulty)
	{
		gameScreen.setGameDifficulty(gameDifficulty);
	}

	public void setScore(int scorescount)
	{
		gameScreen.getWorld().getPlayer().setScorescount(scorescount);
	}
	public int getScore()
	{
		return gameScreen.getWorld().getPlayer().getScorescount();
	}

	public void setWeapon(Weapon currentweapon)
	{
		gameScreen.getWorld().getPlayer().setCurrentWeapon(currentweapon);
	}
	public Weapon getWeapon()
	{
		return gameScreen.getWorld().getPlayer().getCurrentWeapon();
	}
	public void setInventoryCurrentChoose(int choose)
	{
		gameScreen.setInventoryCurrentChoose(choose);
	}
	public int getInventoryCurrentChoose()
	{
		return gameScreen.getInventoryCurrentChoose();
	}
	public void applyWeapon()
	{
		gameScreen.appleWeapon();
	}
	public void disposingScreens()
	{
		gameScreen.dispose();
	}

	public void showDeathScreen() throws IOException {
		gameScreen.deathScreen();
	}

	public boolean isBloodEnabled() {
		return bloodEnabled;
	}

	public void setBloodEnabled(boolean bloodEnabled) {
		this.bloodEnabled = bloodEnabled;
	}
	public Group getSettingScreen()
	{
		return menuScreen.getSettingScreen();
	}

	public void setReloading(boolean toSet)
	{
		gameScreen.setReloading(toSet);
	}

	public int getCurrentXP() {
		return gameScreen.getWorld().getPlayer().getCurrentXP();
	}

	public void setCurrentXP(int currentXP) {
		gameScreen.getWorld().getPlayer().setCurrentXP(currentXP);
	}

	public void LvlUp()
	{
		gameScreen.getWorld().getPlayer().LvLup();
		gameScreen.LvlUp();
	}

	public int getReqXP() {
		return gameScreen.getWorld().getPlayer().getReqXP();

	}

	public void setReqXP(int reqXP) {
		gameScreen.getWorld().getPlayer().setReqXP(reqXP);
	}
}