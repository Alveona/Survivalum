package com.pomavau.crimson.Model;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.pomavau.crimson.Controller.MyContactListener;

/**
 * Created by Pomavau on 11.04.16.
 */
public class PhWorld {
    com.badlogic.gdx.physics.box2d.World world;
    Player player;
  //  public static float CAMERA_WIDTH = 12f;
  //  public static  float CAMERA_HEIGHT = 8f;
    Body boxP;
    public int width;
    public int height;

    public void update(float delta){

    }

    public Player getPlayer(){
        return player;
    }
    public com.badlogic.gdx.physics.box2d.World getWorld(){
        return world;
    }

    public PhWorld(){
        width = 30;
        height = 8;
        world = new com.badlogic.gdx.physics.box2d.World(new Vector2(0, -20), true);
        world.setContactListener(new MyContactListener(world));
        //world.setAutoClearForces(true);
        createWorld();
    }

    public void setPP(float x, float y){
        //ppuX = x;
        //ppuY = y;
    }

    public void createWorld()
    {
        BodyDef def = new BodyDef();
        def.type = BodyDef.BodyType.DynamicBody;
        boxP = world.createBody(def);
    }
    private Body createBox(BodyDef.BodyType type, float width, float height, float density) {
        BodyDef def = new BodyDef();
        def.type = type;
        Body box = world.createBody(def);

        PolygonShape poly = new PolygonShape();
        poly.setAsBox(width, height);

        box.createFixture(poly, density);
        poly.dispose();

        return box;
    }

    public Body getBox()
    {
        return boxP;
    }
}
