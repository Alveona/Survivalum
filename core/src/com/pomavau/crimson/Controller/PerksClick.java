package com.pomavau.crimson.Controller;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.pomavau.crimson.Model.Perk;
import com.pomavau.crimson.crimsonTD;

/**
 * Created by Pomavau on 31.05.16.
 */
public class PerksClick extends ClickListener {
    private Perk perkE;
    private Perk perkD1;
    private Perk perkD2;
    public PerksClick(Perk perkE, Perk perkD1, Perk perkD2)
    {
        this.perkE = perkE;
        this.perkD1 = perkD1;
        this.perkD2 = perkD2;
    }
    public void clicked(InputEvent event, float x, float y) {
      perkE.changeState(ObjectState.ON);
        perkD1.changeState(ObjectState.OFF);
        perkD2.changeState(ObjectState.OFF);

        crimsonTD.getInstance().setPerkEnabled(perkE);
    }
}
