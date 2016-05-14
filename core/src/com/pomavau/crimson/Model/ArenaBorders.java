package com.pomavau.crimson.Model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.pomavau.crimson.Controller.BodyEditorLoader;
import com.pomavau.crimson.View.ImageActor;

import static com.badlogic.gdx.physics.box2d.BodyDef.BodyType.DynamicBody;
import static com.badlogic.gdx.physics.box2d.BodyDef.BodyType.KinematicBody;
import static com.badlogic.gdx.physics.box2d.BodyDef.BodyType.StaticBody;

/**
 * Created by Pomavau on 10.05.16.
 */
public class ArenaBorders extends ImageActor {
    private Body box;
   // private Vector2 bulletOrigin;
    //private Vector2 bulletPos;
    //private Vector2 currentPosition;



    private boolean isForDeleting = false;

    public ArenaBorders(Texture image, float x, float y, float width, float height, com.badlogic.gdx.physics.box2d.World world) {

        super(image, x, y, width, height);
        //setOrigin(originX / image.getWidth() * width, originY / image.getHeight() * height);
        createBody(world);
        //currentPosition = new Vector2(x, y);
        //currentPosition = getX(), get

    }

    public void createBody(com.badlogic.gdx.physics.box2d.World world){

        BodyDef bodyDef = new BodyDef();
        bodyDef.position.x = getX()+getWidth()/2;
        bodyDef.position.y = getY()+getHeight()/2;
        bodyDef.type = StaticBody;
        //bodyDef.type = DynamicBody;
        box = world.createBody(bodyDef);
        //  box.setType(DynamicBody);
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.density = 5f;
        fixtureDef.restitution = 1f;
        fixtureDef.friction = 1f;

        PolygonShape poly = new PolygonShape();
        poly.setAsBox(1145, 616);
        //poly.setAsBox(getWidth(), getHeight(), new Vector2(getOriginX(), getOriginY()), 0);
        // poly.setAsBox(getWidth() * 0.7f, getHeight() * 0.7f);
        // poly.setAsBox(100, 60);
        fixtureDef.shape = poly;

        box.createFixture(fixtureDef);
        poly.dispose();
        //box.getLocalCenter().set(getOriginX(), getOriginY());
        //loader.attachFixture(box, "bullet", fixtureDef, 20, "bullet");


        box.setUserData("arenaborders");
    }
    public void act(float delta)
    {
        //box.getPosition().x = getX();
        //box.getPosition().y = getY();
        System.out.println(box.getPosition().y);
        //currentPosition.set(getX(), getY());
        //box.setLinearVelocity(currentPosition);
        //box.setTransform(currentPosition, (float)Math.toRadians(getRotation()));

    }

    public Vector2 getPosition(){
        return box.getPosition();
    }

    public Body getBox() {
        return box;
    }
    public boolean isForDeleting() {
        return isForDeleting;
    }

    public void setForDeleting(boolean isForDeleting) {
        this.isForDeleting = isForDeleting;
    }
}

