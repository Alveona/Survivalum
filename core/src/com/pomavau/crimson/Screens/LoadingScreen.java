package com.pomavau.crimson.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.TextureLoader;
import com.badlogic.gdx.assets.loaders.resolvers.ExternalFileHandleResolver;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeType;
import com.pomavau.crimson.crimsonTD;

import java.io.File;

/**
 * Created by Pomavau on 14.05.16.
 */
public class LoadingScreen implements Screen {
    AssetManager assetManager = new AssetManager(new InternalFileHandleResolver());
        public LoadingScreen(SpriteBatch batch)
        {
            System.out.println("STARTED LOADING");
            //Main Menu Assets
            assetManager.load(crimsonTD.getInstance().resolvePath("mainmenu/music.mp3"), Music.class);
            assetManager.load(crimsonTD.getInstance().resolvePath("mainmenu/mainmenu_art.png"), Texture.class);
            assetManager.load(crimsonTD.getInstance().resolvePath("mainmenu/mainmenu_bg.png"), Texture.class);
            assetManager.load(crimsonTD.getInstance().resolvePath("mainmenu/mainmenu_btnPlay.png"), Texture.class);
            assetManager.load(crimsonTD.getInstance().resolvePath("mainmenu/mainmenu_btnSet.png"), Texture.class);
            assetManager.load(crimsonTD.getInstance().resolvePath("mainmenu/mainmenu_btnStat2.png"), Texture.class);
            assetManager.load(crimsonTD.getInstance().resolvePath("mainmenu/title2.png"), Texture.class);
            assetManager.load(crimsonTD.getInstance().resolvePath("Menu.png"), Texture.class);
            assetManager.load(crimsonTD.getInstance().resolvePath("mainmenu/mainmenu_btn.png"), Texture.class);
            assetManager.load(crimsonTD.getInstance().resolvePath("mainmenu/InventoryMenu2.png"), Texture.class);
            assetManager.load(crimsonTD.getInstance().resolvePath("mainmenu/mainmenu_settings_enabled.png"), Texture.class);
            assetManager.load(crimsonTD.getInstance().resolvePath("mainmenu/mainmenu_settings_disabled.png"), Texture.class);
//            assetManager.load(crimsonTD.getInstance().resolvePath("nasalization-rg.ttf"), BitmapFont.class);
            //Game Screen Assets
//            assetManager.finishLoading();
            System.out.println("FINISHED LOADING");
        }
    @Override
    public void show() {
        //assetManager.load(crimsonTD.getInstance().resolvePath("testpath.png"), Texture.class);
        Gdx.gl.glClearColor(0.5f, 1, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        //System.out.println("RENDERED!");
        //assetManager.finishLoading();
        System.out.println(assetManager.getProgress() * 100);

        if(assetManager.update())
        {
            crimsonTD.getInstance().createMenuScreen();
           // crimsonTD.getInstance().createGameScreen();
            crimsonTD.getInstance().showMenu();
        }
    }

    @Override
    public void resize(int width, int height) {

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

    public AssetManager getAssetManager() {
        return assetManager;
    }

}
