package com.pomavau.crimson.Controller;

import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.pomavau.crimson.crimsonTD;

/**
 * Created by Pomavau on 22.05.16.
 */
public class SettingsController extends ClickListener {
    Group group1;
    Group group2;
    SettingsType settingsType;
    String type;
    public SettingsController (Group group1, Group group2)
    {
       this.group1 = group1;
        this.group2 = group2;

    }
    public SettingsController (Group group1, Group group2, SettingsType settingsType, String type)
    {
        this.group1 = group1;
        this.group2 = group2;
        this.settingsType = settingsType;
        this.type = type;
    }

    public void clicked(InputEvent event, float x, float y) {
        //crimsonTD.getInstance().showMenu(group1, group2);
        //if(group1.isVisible() == false)
       // {
            //group1.setVisible(true);
            //group2.setVisible(false);
        group1.getChildren().get(0).setVisible(true);
        group1.getChildren().get(1).setVisible(false);
        group2.getChildren().get(0).setVisible(false);
        group2.getChildren().get(1).setVisible(true);
     //   }
       switch (settingsType)
       {
           case BLOOD:
               if(type.equals("on"))
               {
                   crimsonTD.getInstance().setBloodEnabled(true);
               }
               if(type.equals("off"))
               {
                   crimsonTD.getInstance().setBloodEnabled(false);
               }
               break;
           default: break;
       }
    }
}
