package com.pomavau.crimson.Model;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.pomavau.crimson.Controller.Animation;
import com.pomavau.crimson.Controller.BorderType;
import com.pomavau.crimson.View.ImageActor;

/**
 * Created by Pomavau on 17.05.16.
 */
public class HPcircle extends ImageActor {
    private Animation circleAnimation;
    LevelWorld world;
    float temp;
    public HPcircle(Texture image, float x, float y, float width, float height, LevelWorld world) {
        super(image, x, y, width, height);
        circleAnimation = new Animation(new TextureRegion(new Texture("android/assets/hpCircle.png"), 5050, 202), 25, 0);
        this.world = world;
        img = circleAnimation.getFrame();
    }
    public void update(float playercurrenthp, float playermaxhp)
    {
        //System.out.println(playercurrenthp / playermaxhp * 100);
        temp = playercurrenthp / playermaxhp * 100;
        if(temp == 100)
            circleAnimation.setFrame(0);
        if(temp < 100 && temp >=96)
            circleAnimation.setFrame(1);
        if(temp < 96 && temp >=92)
            circleAnimation.setFrame(2);
        if(temp < 92 && temp >=88)
            circleAnimation.setFrame(3);
        if(temp < 88 && temp >=84)
            circleAnimation.setFrame(4);
        if(temp < 84 && temp >=80)
            circleAnimation.setFrame(5);
        if(temp < 80 && temp >=76)
            circleAnimation.setFrame(6);
        if(temp < 76 && temp >=72)
            circleAnimation.setFrame(7);
        if(temp < 72 && temp >=68)
            circleAnimation.setFrame(8);
        if(temp < 68 && temp >=64)
            circleAnimation.setFrame(9);
        if(temp < 64 && temp >=60)
            circleAnimation.setFrame(10);
        if(temp < 60 && temp >=56)
            circleAnimation.setFrame(11);
        if(temp < 56 && temp >=52)
            circleAnimation.setFrame(12);
        if(temp < 52 && temp >=48)
            circleAnimation.setFrame(13);
        if(temp < 48 && temp >=44)
            circleAnimation.setFrame(14);
        if(temp < 44 && temp >=40)
            circleAnimation.setFrame(15);
        if(temp < 40 && temp >=36)
            circleAnimation.setFrame(16);
        if(temp < 36 && temp >=32)
            circleAnimation.setFrame(17);
        if(temp < 32 && temp >=28)
            circleAnimation.setFrame(18);
        if(temp < 28 && temp >=24)
            circleAnimation.setFrame(19);
        if(temp < 24 && temp >=20)
            circleAnimation.setFrame(20);
        if(temp < 20 && temp >=16)
            circleAnimation.setFrame(21);
        if(temp < 16 && temp >=12)
            circleAnimation.setFrame(22);
        if(temp < 12 && temp >=8)
            circleAnimation.setFrame(23);
        if(temp < 8 && temp >=4)
            circleAnimation.setFrame(24);
       // setPosition(world.getPlayer().getX(), world.getPlayer().getY());
        setPosition(world.getPlayer().getPosition().x,world.getPlayer().getPosition().y );
        setPosition(world.getPlayer().getPosition().x - world.getPlayer().getWidth() / 2 + 15, world.getPlayer().getPosition().y - world.getPlayer().getHeight() / 2 + 3);
        img = circleAnimation.getFrame();
    }
}
