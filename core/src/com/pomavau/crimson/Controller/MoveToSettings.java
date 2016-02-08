package com.pomavau.crimson.Controller;

//import com.ads.gdxtest.TestGame;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.pomavau.crimson.crimsonTD;

/**
 * Created by ga_nesterchuk on 25.01.2016.
 */
public class MoveToSettings extends ClickListener {
    public void clicked(InputEvent event, float x, float y) {
       // FrameBuffer frameBuffer = new FrameBuffer()
        crimsonTD.getInstance().showSettings();
    }
}