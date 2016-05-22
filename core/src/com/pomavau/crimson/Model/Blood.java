package com.pomavau.crimson.Model;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.pomavau.crimson.Controller.Animation;
import com.pomavau.crimson.View.ImageActor;
import com.pomavau.crimson.crimsonTD;

/**
 * Created by Pomavau on 21.05.16.
 */
public class Blood extends ImageActor {
    private Animation bloodAnimation;
    LevelWorld world;
    public Blood(Texture image, float x, float y, float width, float height, LevelWorld world) {
        super(image, x, y, width, height);
        bloodAnimation = new Animation(new TextureRegion(new Texture("android/assets/blood.png"), 330, 45), 6, 0.5f);
        this.world = world;
        img = bloodAnimation.getFrame();
        setVisible(false);
        world.addActor(this);
    }
    public void update(Bot bot, float delta)
    {
        if(bloodAnimation.isFinishedOnce() == false) {

            //bloodAnimation.setFrame(1);
            bloodAnimation.update(delta);
        }
        else
        {
            setVisible(false);
        }
       // setRotation(bot.getRotation());
        setRotation(world.getPlayer().getRotation());
        setPosition(bot.getPosition().x, bot.getPosition().y);
        this.img = bloodAnimation.getFrame();
    }
    public void makeBlood()
    {
        if(crimsonTD.getInstance().isBloodEnabled()) {
            bloodAnimation.setFinishedOnce(false);
            //bloodAnimation.setFrame(0);
            setVisible(true);
        }
    }
}
