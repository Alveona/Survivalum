package com.pomavau.crimson.Model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.physics.box2d.World;
import com.pomavau.crimson.Controller.BodyEditorLoader;
import com.pomavau.crimson.Controller.BulletType;
import com.pomavau.crimson.Controller.CustomUserData;
import com.pomavau.crimson.View.ImageActor;
import com.badlogic.gdx.graphics.Texture;
import com.pomavau.crimson.crimsonTD;

import static com.badlogic.gdx.physics.box2d.BodyDef.BodyType.DynamicBody;
import static com.badlogic.gdx.physics.box2d.BodyDef.BodyType.KinematicBody;

/**
 * Created by Gleb on 06.02.2016.
 */
public class Bullet extends ImageActor {

    public Fixture playerPhysicsFixture;
    public Fixture playerSensorFixture;
    private Body box;
    private Vector2 bulletOrigin;
    private Vector2 bulletPos;
    private Vector2 currentPosition;
    private CustomUserData customUserData;
    private Vector2 transformingvector;

    public BulletType getBulletType() {
        return bulletType;
    }

    public void setBulletType(BulletType bulletType) {
        this.bulletType = bulletType;
    }

    private BulletType bulletType;


    private boolean isForDeleting = false;

    public Bullet(Texture image, float x, float y, float width, float height, World world, BulletType bulletType) {

        super(image, x, y, width, height);
        this.bulletType = bulletType;
        //setOrigin(originX / image.getWidth() * width, originY / image.getHeight() * height);
        switch (bulletType)
        {
            case BULLET:createBodyBullet(world);
                break;
            case ICEBALL:createBodyIceball(world);
                break;
            case FLAME:createBodyFire(world);
                break;
        }


        currentPosition = new Vector2(x, y);
        transformingvector = new Vector2(0,0);
        //currentPosition = getX(), get

    }

    public void createBodyBullet(com.badlogic.gdx.physics.box2d.World world){
        BodyEditorLoader loader = new BodyEditorLoader(Gdx.files.internal(crimsonTD.getInstance().resolvePath("bodyproject.json")));
        BodyDef bodyDef = new BodyDef();
        //bodyDef.position.x = getX()+getWidth()/2;
        //bodyDef.position.y = getY()+getHeight()/2;
        bodyDef.position.x = getX()+getWidth()/2;
        bodyDef.position.y = getY()+getHeight()/2;
        bodyDef.type = KinematicBody;
        //bodyDef.type = DynamicBody;
        box = world.createBody(bodyDef);
        //  box.setType(DynamicBody);
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.density = 0;
        fixtureDef.restitution = 1f;
        fixtureDef.friction = 1f;

        PolygonShape poly = new PolygonShape();
        poly.setAsBox(0.4f, 0.4f);
        //poly.setAsBox(getWidth(), getHeight(), new Vector2(getOriginX(), getOriginY()), 0);
        // poly.setAsBox(getWidth() * 0.7f, getHeight() * 0.7f);
        // poly.setAsBox(100, 60);
        fixtureDef.shape = poly;
        //box.createFixture(fixtureDef).setUserData("bullet");
        poly.dispose();
        //box.getLocalCenter().set(getOriginX(), getOriginY());
        customUserData = new CustomUserData("bullet", this);
        loader.attachFixture(box, "bullet", fixtureDef, 10, customUserData);    //DEFAULTSCALE: 20

        bulletOrigin = loader.getOrigin("bullet", 10).cpy();
        setOrigin(bulletOrigin.x, bulletOrigin.y);
        bulletPos = getPosition().sub(bulletOrigin);
        setPosition(bulletPos.x, bulletPos.y);
        box.setUserData(customUserData);

    }
    public void createBodyIceball(com.badlogic.gdx.physics.box2d.World world){
        BodyEditorLoader loader = new BodyEditorLoader(Gdx.files.internal(crimsonTD.getInstance().resolvePath("bodyproject.json")));
        BodyDef bodyDef = new BodyDef();
        //bodyDef.position.x = getX()+getWidth()/2;
        //bodyDef.position.y = getY()+getHeight()/2;
        bodyDef.position.x = getX()+getWidth()/2;
        bodyDef.position.y = getY()+getHeight()/2;
        bodyDef.type = KinematicBody;
        //bodyDef.type = DynamicBody;
        box = world.createBody(bodyDef);
        //  box.setType(DynamicBody);
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.density = 5f;
        fixtureDef.restitution = 1f;
        fixtureDef.friction = 1f;

        PolygonShape poly = new PolygonShape();
        poly.setAsBox(10f, 10f);
        //poly.setAsBox(getWidth(), getHeight(), new Vector2(getOriginX(), getOriginY()), 0);
        // poly.setAsBox(getWidth() * 0.7f, getHeight() * 0.7f);
        // poly.setAsBox(100, 60);
        fixtureDef.shape = poly;
        //box.createFixture(fixtureDef);
        poly.dispose();
        //box.getLocalCenter().set(getOriginX(), getOriginY());
        customUserData = new CustomUserData("bullet", this);
        loader.attachFixture(box, "Name", fixtureDef, 12, customUserData);    //DEFAULTSCALE: 20
//        setOrigin(bulletOrigin.x, bulletOrigin.y);
        bulletPos = getPosition();
        setPosition(bulletPos.x, bulletPos.y);
        box.setUserData(customUserData);


    }
    public void createBodyFire(com.badlogic.gdx.physics.box2d.World world){
        BodyEditorLoader loader = new BodyEditorLoader(Gdx.files.internal(crimsonTD.getInstance().resolvePath("bodyproject.json")));
        BodyDef bodyDef = new BodyDef();
        //bodyDef.position.x = getX()+getWidth()/2;
        //bodyDef.position.y = getY()+getHeight()/2;
        bodyDef.position.x = getX()+getWidth()/2;
        bodyDef.position.y = getY()+getHeight()/2;
        bodyDef.type = KinematicBody;
       // bodyDef.type = DynamicBody;
        box = world.createBody(bodyDef);
        //  box.setType(DynamicBody);
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.density = 0;
        fixtureDef.restitution = 1f;
        fixtureDef.friction = 0;

        PolygonShape poly = new PolygonShape();
        //poly.setAsBox(30f, 20f);
        //poly.setAsBox(getWidth(), getHeight(), new Vector2(getOriginX(), getOriginY()), 0);
        // poly.setAsBox(getWidth() * 0.7f, getHeight() * 0.7f);
        // poly.setAsBox(100, 60);
        fixtureDef.shape = poly;
        //fixtureDef.filter.groupIndex = -1;
        //box.createFixture(fixtureDef);
        poly.dispose();
        //box.getLocalCenter().set(getOriginX(), getOriginY());
        customUserData = new CustomUserData("bullet", this);
       // Filter f = new Filter();
        //f.groupIndex = -1;

        loader.attachFixture(box, "fire", fixtureDef, 90, customUserData, -1);    //DEFAULTSCALE: 20
       // bulletOrigin = loader.getOrigin("fire", 90);
        //setOrigin(bulletOrigin.x, bulletOrigin.y);
        bulletPos = getPosition();
        setPosition(bulletPos.x, bulletPos.y);
        box.setUserData(customUserData);
        box.getFixtureList().get(0).setDensity(0);

    }
    public void act(float delta)
    {
        //box.getPosition().x = getPosition().x + getWidth() /2;
       // currentPosition.set(0,0);

        switch (bulletType)
                {
                    case BULLET:
                        currentPosition.set(getX() + 7, getY() + 2);
                        break;
                    case ICEBALL:
                        currentPosition.set((float)(getX() + getWidth() / 2 * Math.cos(Math.toRadians(getRotation()))), (float)(getY() + (getHeight() / 2)* Math.toRadians(Math.sin(getRotation()))));
                        //currentPosition.set((float)(getX() + getWidth() / 2 *  ), (float)(getY() + getHeight() /2 ));
                        break;
                    case FLAME:
                        currentPosition.set(getX() + 10 * (float)Math.cos(getRotation()), getY());

                        break;
                }
        transformingvector.set(currentPosition.x + getOriginX(), currentPosition.y - 70 * getOriginY());
        box.setLinearVelocity(currentPosition);
        if(bulletType == BulletType.FLAME)
        box.setTransform(transformingvector, (float) Math.toRadians(getRotation()));
        else
            box.setTransform(currentPosition, (float) Math.toRadians(getRotation()));
       // setPosition(box.getPosition().x, box.getPosition().y);
/*
        box.setTransform(getPosition(), (float) Math.toRadians((getRotation())));
        setRotation((float) Math.toDegrees(box.getAngle()));
        //setPosition(box.getPosition().x-getOriginX(), box.getPosition().y-getOriginY());
        setPosition(box.getPosition().x - getWidth() / 2 + 7, box.getPosition().y - getHeight() / 2 + 7);*/

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
    public void setTexture(TextureRegion texture)
    {
        this.img = texture;
    }



}
