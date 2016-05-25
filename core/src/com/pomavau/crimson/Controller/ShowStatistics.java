package com.pomavau.crimson.Controller;

import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.pomavau.crimson.View.FontActor;
import com.pomavau.crimson.crimsonTD;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;


/**
 * Created by Pomavau on 21.05.16.
 */
public class ShowStatistics  extends ClickListener{
    Group group;
    FontActor LB1;
    FontActor LB2;
    FontActor LB3;
    FontActor LB4;
    Scanner sc;

    public ShowStatistics (Group group, FontActor LB1 , FontActor LB2, FontActor LB3, FontActor LB4) throws FileNotFoundException {
        this.group = group;
        this.LB1 = LB1;
        this.LB2 = LB2;
        this.LB3 = LB3;
        this.LB4 = LB4;
        sc = new Scanner(new File(crimsonTD.getInstance().resolvePath("stats//stats_strings.txt")));

    }

    public void clicked(InputEvent event, float x, float y) {
        crimsonTD.getInstance().showMenu(group);

        FontActor[] LB = {LB1, LB2, LB3, LB4};
        if(group.isVisible() == true)
        {
            try {
                sc = new Scanner(new File(crimsonTD.getInstance().resolvePath("stats//stats_strings.txt")));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            for(int i = 0; i < LB.length; i++)
        {
            if(sc.hasNextLine())
            {
                LB[i].update(sc.nextLine());
                //System.out.println(sc.next());
            }
        }
        sc.close();
    }}
}

