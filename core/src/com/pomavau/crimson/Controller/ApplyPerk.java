package com.pomavau.crimson.Controller;

import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.pomavau.crimson.Model.Perk;
import com.pomavau.crimson.crimsonTD;

/**
 * Created by Pomavau on 31.05.16.
 */
public class ApplyPerk extends ClickListener {
    private Perk perk;
    private Group group;
    public ApplyPerk(Group group)
    {
        this.group = group;
    }
    public void clicked(InputEvent event, float x, float y) {
        crimsonTD.getInstance().showMenu(group);
        perk = crimsonTD.getInstance().getPerkEnabled();
        if(perk != null) {
            switch (perk.getPerkType()) {
                case MSUP:
                    crimsonTD.getInstance().Perk_MS();
                    break;
                case RSUP:
                    crimsonTD.getInstance().Perk_RS();
                    break;
                case LVLUP:
                    crimsonTD.getInstance().Perk_LVLUP();
                    break;
                case FREEZE:
                    crimsonTD.getInstance().Perk_Freeze();
                    break;
            }

            //crimsonTD.getInstance().getPerkArray().get(i).setVisible(false);
            perk.changeState(ObjectState.OFF);
            crimsonTD.getInstance().ClosingPerksScreen();
        }
    }
}
