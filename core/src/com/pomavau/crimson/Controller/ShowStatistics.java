package com.pomavau.crimson.Controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
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
    FileHandle statsfile;

    public ShowStatistics (Group group, FontActor LB1 , FontActor LB2, FontActor LB3, FontActor LB4) throws FileNotFoundException {
        this.group = group;
        this.LB1 = LB1;
        this.LB2 = LB2;
        this.LB3 = LB3;
        this.LB4 = LB4;
        //sc = new Scanner(new File(crimsonTD.getInstance().resolvePath("stats//stats_strings.txt")));
        //sc = new Scanner(new File(crimsonTD.getInstance().resolvePath("stats//stats_strings.txt")));
        statsfile = new FileHandle(Gdx.files.internal(crimsonTD.getInstance().resolvePath("stats//stats_strings.txt")).path());

    }

    public void clicked(InputEvent event, float x, float y) {
        crimsonTD.getInstance().showMenu(group);

        FontActor[] LB = {LB1, LB2, LB3, LB4};
        if(group.isVisible() == true)
        {

                //sc = new Scanner(new File(crimsonTD.getInstance().resolvePath("stats//stats_strings.txt")));
                //sc = new Scanner(new File(Gdx.files.internal("stats//stats_strings.txt").path()));
                statsfile = new FileHandle(crimsonTD.getInstance().resolvePath("stats//stats_strings.txt"));

            //for(int i = 0; i < LB.length; i++)
        //{


               // LB[i].update(sc.nextLine());
                LB[1].update(statsfile.readString());
                //System.out.println(sc.next());

       // }
        //sc.close();
    }}
}

