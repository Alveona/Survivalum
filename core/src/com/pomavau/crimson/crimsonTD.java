package com.pomavau.crimson;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.pomavau.crimson.Model.World;

import java.io.FileNotFoundException;
import java.util.HashMap;

public class crimsonTD extends Game {
	private float ppuX, ppuY;

	private SpriteBatch batch;
	private com.pomavau.crimson.Screens.MainMenuScreen menuScreen;
	private com.pomavau.crimson.Screens.GameScreen gameScreen;
	HashMap<Integer, TextureRegion> textureRegions;
	private ShapeRenderer shape;
	private BitmapFont font;
	private static crimsonTD instance = new crimsonTD();

	public static crimsonTD getInstance() {
		return instance;
	}

	private crimsonTD() {}


	@Override
	public void create() {
		ppuX = Gdx.graphics.getWidth()/800;
		ppuY = Gdx.graphics.getHeight()/600;
		loadGraphics();
		batch = new SpriteBatch();
		shape = new ShapeRenderer();
		font = new BitmapFont();
		menuScreen = new com.pomavau.crimson.Screens.MainMenuScreen(batch);
		try {
			gameScreen = new com.pomavau.crimson.Screens.GameScreen(batch, shape, font, textureRegions);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}



		setScreen(menuScreen);
		//setScreen(gameScreen);
	}

	private void loadGraphics() {
		textureRegions = new HashMap<Integer, TextureRegion>();
		Texture atlas = new Texture("android/assets/hero/heromove_atlas.png");
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
		textureRegions.put(19, new TextureRegion(atlas, 1252, 618, 313, 206));

	}
	public void showGame() {
		setScreen(gameScreen);
	}

	public void showMenu() {
		setScreen(menuScreen);
	}

	public void showSettings() {

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
}