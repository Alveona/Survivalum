package com.pomavau.crimson.Controller;


import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.pomavau.crimson.crimsonTD;

/**
 * Created by Gleb on 23.01.2016.
 */
public class SoundToggle extends ClickListener {
    public void clicked(InputEvent event, float x, float y) {
        crimsonTD.getInstance().setSoundState(!crimsonTD.getInstance().getSoundState());

        System.out.println("Sound State Changed");
    }
}