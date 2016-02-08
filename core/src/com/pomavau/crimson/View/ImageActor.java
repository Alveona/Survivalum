package com.pomavau.crimson.View;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.pomavau.crimson.crimsonTD;

/**
 * Created by Pomavau on 29.01.16.
 */
public class ImageActor extends Actor {
    TextureRegion img;

    public ImageActor(TextureRegion img, float x, float y, float width, float height) {
        this.img = img;
        setPosition(x * crimsonTD.getInstance().getPpuX(), y * crimsonTD.getInstance().getPpuY());
        setSize(width * crimsonTD.getInstance().getPpuX(), height * crimsonTD.getInstance().getPpuY());
    }

    public ImageActor(Texture img, float x, float y) {
        this(new TextureRegion(img), x, y, img.getWidth(), img.getHeight());
    }

    public ImageActor(Texture img, float x, float y, float width, float height) {
        this(new TextureRegion(img), x, y, width, height);
    }

    public ImageActor(TextureRegion img, float x, float y) {
        this(img, x, y, img.getRegionWidth(), img.getRegionHeight());
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(img, getX(), getY(), getWidth(), getHeight());
    }

   /* public void rotate(float degrees)
    {
        this.rotate(degrees);
    }*/
}
