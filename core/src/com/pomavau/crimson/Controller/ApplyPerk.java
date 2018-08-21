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
                    crimsonTD.getInstance().ClosingPerksScreen();
                    crimsonTD.getInstance().Perk_LVLUP();
                    break;
                case FREEZE:
                    crimsonTD.getInstance().Perk_Freeze();
                    break;
                case INVUL:
                    crimsonTD.getInstance().Perk_Invul();
                    break;
                case INFAMMO:
                    crimsonTD.getInstance().Perk_Ammo();
                    break;
            }
            crimsonTD.getInstance().showMenu(group);
            //crimsonTD.getInstance().getPerkArray().get(i).setVisible(false);
            perk.changeState(ObjectState.OFF);
            crimsonTD.getInstance().ClosingPerksScreen();
        }
    }
}
