package com.pomavau.crimson.Controller;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.pomavau.crimson.crimsonTD;

/**
 * Created by 01k1203 on 16.05.2016.
 */
public class InventoryIncrease extends ClickListener {
    public void clicked(InputEvent event, float x, float y) {
        crimsonTD.getInstance().setInventoryCurrentChoose(crimsonTD.getInstance().getInventoryCurrentChoose() + 1);
        //System.out.println(crimsonTD.getInstance().getInventoryCurrentChoose());
    }
}
