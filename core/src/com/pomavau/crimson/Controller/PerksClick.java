package com.pomavau.crimson.Controller;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.pomavau.crimson.Model.Perk;
import com.pomavau.crimson.crimsonTD;

/**
 * Created by Pomavau on 31.05.16.
 */
public class PerksClick extends ClickListener {
    private Perk perk;
    public PerksClick(Perk perk)
    {
        this.perk = perk;
    }
    public void clicked(InputEvent event, float x, float y) {
      perk.changeState();
    }
}
