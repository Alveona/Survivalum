package com.pomavau.crimson.Model;

import com.pomavau.crimson.View.ImageActor;
import com.badlogic.gdx.graphics.Texture;

/**
 * Created by Gleb on 06.02.2016.
 */
public class Bullet extends ImageActor {

    public Bullet(Texture image, float x, float y) {
        super(image, x, y);
    }
    public void Move()
    {
        setX(getX()+5);
        setY(getY()+5);
    }
    public void shot ()
    {

    }
}
