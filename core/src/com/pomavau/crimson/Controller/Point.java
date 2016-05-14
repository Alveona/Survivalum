package com.pomavau.crimson.Controller;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;

import static com.badlogic.gdx.physics.box2d.BodyDef.BodyType.DynamicBody;
import static com.badlogic.gdx.physics.box2d.BodyDef.BodyType.StaticBody;

/**
 * Created by Pomavau on 12.05.16.
 */
public class Point extends Actor {
    float x;
    float y;
    private Body box;

    public Point(float x, float y, World world) {

        this.x = x;
        this.y = y;
        //setPosition(x, y);
        createBody(world);
    }

    public void createBody(com.badlogic.gdx.physics.box2d.World world){

        BodyDef bodyDef = new BodyDef();
        bodyDef.position.x = getX()+getWidth()/2;
        bodyDef.position.y = getY()+getHeight()/2;
        //bodyDef.type = StaticBody;
        bodyDef.type = DynamicBody;
        box = world.createBody(bodyDef);
        //  box.setType(DynamicBody);
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.density = 0f;
        fixtureDef.restitution = 0f;
        fixtureDef.friction = 0f;

        PolygonShape poly = new PolygonShape();
        poly.setAsBox(5f, 5f);
        //poly.setAsBox(getWidth(), getHeight(), new Vector2(getOriginX(), getOriginY()), 0);
        // poly.setAsBox(getWidth() * 0.7f, getHeight() * 0.7f);
        // poly.setAsBox(100, 60);
        fixtureDef.shape = poly;

        box.createFixture(fixtureDef);
        poly.dispose();
        //box.getLocalCenter().set(getOriginX(), getOriginY());
        //loader.attachFixture(box, "bullet", fixtureDef, 20, "bullet");


        box.setUserData("shootingpoint");
    }
    public void act(float delta)
    {
        box.getPosition().x = getX();
        box.getPosition().y = getY();
    }
}
