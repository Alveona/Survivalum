package com.pomavau.crimson.Controller;

import com.badlogic.gdx.Gdx;

/**
 * Created by Гриша on 23.02.2016.
 */
public class Pointer {
    int screenX;
    int screenY;
    int button;

    public Pointer(int screenX, int screenY, int button) {
        setPosition(screenX, screenY);
        this.button = button;
    }

    public void setPosition(int screenX, int screenY) {
        this.screenX = screenX;
        this.screenY = Gdx.graphics.getHeight() - screenY;
    }

    public int getX() {
        return screenX;
    }

    public int getY() {
        return screenY;
    }


}
