package com.pomavau.crimson.Model;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.pomavau.crimson.Controller.ObjectState;
import com.pomavau.crimson.Controller.PerkType;
import com.pomavau.crimson.View.ImageActor;

/**
 * Created by Pomavau on 29.05.16.
 */
public class Perk extends ImageActor {
    private TextureRegion textureE;
    private TextureRegion textureD;
    private PerkType perkType;
    private ObjectState state = ObjectState.OFF;

    public Perk (TextureRegion imageD, TextureRegion imageE, float x, float y, PerkType perkType)
    {
        super(imageD, x, y);
        this.perkType = perkType;
        this.textureD = imageD;
        this.textureE = imageE;
    }

    public void changeState(ObjectState state)
    {
        this.state = state;
        if(state == ObjectState.OFF)
            this.img = textureD;
        if(state == ObjectState.ON)
            this.img = textureE;
    }

}
