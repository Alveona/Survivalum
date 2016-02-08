package com.pomavau.crimson.Controller;

//import com.ads.gdxtest.TestGame;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.pomavau.crimson.crimsonTD;

/**
 * Created by ga_nesterchuk on 25.01.2016.
 */
public class MoveToGame extends ClickListener {
    public void clicked(InputEvent event, float x, float y) {
        crimsonTD.getInstance().showGame();
    }
}
