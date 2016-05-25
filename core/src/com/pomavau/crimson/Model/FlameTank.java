package com.pomavau.crimson.Model;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.pomavau.crimson.Controller.Animation;
import com.pomavau.crimson.View.ImageActor;
import com.pomavau.crimson.crimsonTD;

/**
 * Created by Pomavau on 19.05.16.
 */
public class FlameTank extends ImageActor{
    private Animation tankAnimation;
    LevelWorld world;
    float temp;
    public FlameTank(Texture image, float x, float y, float width, float height) {
        super(image, x, y, width, height);
        tankAnimation = new Animation(new TextureRegion(new Texture(crimsonTD.getInstance().resolvePath("flametank.png")), 348, 49), 12, 0);
        this.world = world;
        img = tankAnimation.getFrame();
    }
    public void update(float bulletsleft, float bulletsmax)
    {
        temp = bulletsleft/bulletsmax * 100;
        if(temp <= 100 && temp > 92)
            tankAnimation.setFrame(0);
        if(temp <= 92 && temp > 84)
            tankAnimation.setFrame(1);
        if(temp <= 84 && temp > 76)
            tankAnimation.setFrame(2);
        if(temp <= 76 && temp > 68)
            tankAnimation.setFrame(3);
        if(temp <= 68 && temp > 60)
            tankAnimation.setFrame(4);
        if(temp <= 60 && temp > 52)
            tankAnimation.setFrame(5);
        if(temp <= 52 && temp > 44)
            tankAnimation.setFrame(6);
        if(temp <= 44 && temp > 36)
            tankAnimation.setFrame(7);
        if(temp <= 36 && temp > 28)
            tankAnimation.setFrame(8);
        if(temp <= 28 && temp > 20)
            tankAnimation.setFrame(9);
        if(temp <= 20 && temp > 12)
            tankAnimation.setFrame(10);
        if(temp <= 12 && temp > 4)
            tankAnimation.setFrame(11);
        if(temp <= 4 && temp > 0)
            tankAnimation.setFrame(12);
        img = tankAnimation.getFrame();
    }

}
