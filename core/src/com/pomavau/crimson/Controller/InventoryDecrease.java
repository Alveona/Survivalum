package com.pomavau.crimson.Controller;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.pomavau.crimson.crimsonTD;

/**
 * Created by 01k1203 on 16.05.2016.
 */
public class InventoryDecrease extends ClickListener{
    public void clicked(InputEvent event, float x, float y) {
        if(crimsonTD.getInstance().getInventoryCurrentChoose() > 0)
        crimsonTD.getInstance().setInventoryCurrentChoose(crimsonTD.getInstance().getInventoryCurrentChoose() - 1);
    }
}
