package com.pomavau.crimson.Model;

import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;

/**
 * Created by Pomavau on 09.05.16.
 */
public class CharacterBody extends Body {
    private Actor BodyActor;

    public Actor getBodyActor() {
        return BodyActor;
    }

    public void setBodyActor(Actor bodyActor) {
        BodyActor = bodyActor;
    }



    /**
     * Constructs a new body with the given address
     *  @param world the world
     * @param addr  the address
     * @param bodyActor
     */
    protected CharacterBody(World world, long addr, Actor bodyActor) {
        super(world, addr);
        BodyActor = bodyActor;
    }
}
