package com.pomavau.crimson.Controller;

import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.pomavau.crimson.crimsonTD;

/**
 * Created by Pomavau on 09.03.16.
 */
public class ShowMenu extends ClickListener {
    Group group;
    Group group2;
    Group group3;
    public ShowMenu (Group group)
    {
        /*
        if(group.isVisible())
        {group.setVisible(false);}
        else {group.setVisible(true); }

        System.out.println("screen status changed");
        */
        this.group = group;
    }
    public ShowMenu (Group group, Group group2, Group group3)
    {
        this.group = group;
        this.group2 = group2;
        this.group3 = group3;
    }

    public void clicked(InputEvent event, float x, float y) {
        crimsonTD.getInstance().showMenu(group);
        if(group2 != null && group3 != null)
        {

        }
    }
}
