package com.pomavau.crimson.Screens;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.TextureLoader;
import com.badlogic.gdx.graphics.Texture;
import com.pomavau.crimson.crimsonTD;

/**
 * Created by Pomavau on 14.05.16.
 */
public class LoadingScreen implements Screen {
    AssetManager assetManager = new AssetManager();

    @Override
    public void show() {
        //assetManager.load(crimsonTD.getInstance().resolvePath("testpath.png"), Texture.class);
    }

    @Override
    public void render(float delta) {

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


}
