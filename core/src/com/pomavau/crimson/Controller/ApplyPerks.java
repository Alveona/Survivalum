package com.pomavau.crimson.Controller;

import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

/**
 * Created by Pomavau on 31.05.16.
 */
public class ApplyPerks extends ClickListener {
private Group group;
    public ApplyPerks(int index, Group group)
    {
        //System.out.println(index);

        this.group = group;
    }
    public void clicked(InputEvent event, float x, float y) {
    }
}

