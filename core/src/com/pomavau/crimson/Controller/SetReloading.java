package com.pomavau.crimson.Controller;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.pomavau.crimson.crimsonTD;

/**
 * Created by Pomavau on 27.05.16.
 */
public class SetReloading extends ClickListener {
    public void clicked(InputEvent event, float x, float y) {
        crimsonTD.getInstance().setReloading(true);
    }
}
