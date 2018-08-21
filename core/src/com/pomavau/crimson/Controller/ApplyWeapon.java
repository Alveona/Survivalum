package com.pomavau.crimson.Controller;

import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.pomavau.crimson.crimsonTD;

/**
 * Created by 01k1203 on 16.05.2016.
 */
public class ApplyWeapon extends ClickListener {
   private int index;
    private Group group;
    public ApplyWeapon(int index, Group group)
   {
       //System.out.println(index);
       this.index = index;
       this.group = group;
   }
    public void clicked(InputEvent event, float x, float y) {
       //System.out.println(index);
       //System.out.println(index);
        index = crimsonTD.getInstance().getInventoryCurrentChoose();
        switch (index)
        {
            case 1: crimsonTD.getInstance().setWeapon(Weapon.ASSAULTRIFLE);
                break;
            case 2: crimsonTD.getInstance().setWeapon(Weapon.ICERIFLE);
                break;
            case 3: crimsonTD.getInstance().setWeapon(Weapon.FLAMETHROWER);
                break;
            default: crimsonTD.getInstance().setWeapon(Weapon.ASSAULTRIFLE);
                break;
        }

        //crimsonTD.getInstance().setWeapon(Weapon.ASSAULTRIFLE);
       // group.setVisible(false);
        crimsonTD.getInstance().applyWeapon();
        crimsonTD.getInstance().showMenu(group);
    }
}
