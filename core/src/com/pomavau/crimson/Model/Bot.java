package com.pomavau.crimson.Model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.pomavau.crimson.Controller.BodyEditorLoader;
import com.pomavau.crimson.Controller.BotController;
import com.pomavau.crimson.Controller.BotType;
import com.pomavau.crimson.Controller.CustomUserData;
import com.pomavau.crimson.Controller.Direction;
import com.pomavau.crimson.Controller.ObjectState;
import com.pomavau.crimson.Controller.PlayerController;
import com.pomavau.crimson.View.ImageActor;
import com.pomavau.crimson.crimsonTD;

import java.awt.Image;

import static com.badlogic.gdx.physics.box2d.BodyDef.BodyType.DynamicBody;
import static com.badlogic.gdx.physics.box2d.BodyDef.BodyType.KinematicBody;
import static com.badlogic.gdx.physics.box2d.BodyDef.BodyType.StaticBody;

/**
 * Created by Pomavau on 12.03.16.
 */
public class Bot extends ImageActor {
    private BotController controller;
    public void setController(BotController controller) {
        this.controller = controller;
    }

    float size;

    private float movementStep;
    private float rotationStep;
    private Direction rotationDirection;
    private Direction movementDirection;
    private ObjectState currentState = ObjectState.MOVING;
    private boolean speededUp;
    private boolean nearplayer;
    ShapeRenderer shape;
    Body box;
    private float destinationAngle;
    private Vector2 botOrigin;
    private Vector2 botPos;
    private CustomUserData customUserData;
    private int maxHP = 100;
    private int currentHP = 100;

    private Blood blood;
    private float timefromspawn;
    private float spawntimer = 1;

    public float getTimefreezed() {
        return timefreezed;
    }

    public void setTimefreezed(float timefreezed) {
        this.timefreezed = timefreezed;
    }

    private float timefreezed;
    private float freezetimer = 5f;

    private boolean isspawned = false;

    private BotType botType;

    private ImageActor iceblock;
    private com.badlogic.gdx.physics.box2d.World phworld;
    LevelWorld levelworld;
/*
    public Bot(float x, float y, float size, float step) {
        setPosition(x, y);
        setSize(size, size);
        setRotation(0);
        //shape = crimsonTD.getInstance().getShape();
        this.size = size;
        this.movementStep = step;
        this.rotationStep = 10;
    }
*/
public void createBodyZombie(com.badlogic.gdx.physics.box2d.World world){
    phworld = world;
    BodyEditorLoader loader = new BodyEditorLoader(Gdx.files.internal(crimsonTD.getInstance().resolvePath("bodyproject.json")));
    BodyDef bodyDef = new BodyDef();
    bodyDef.position.x = getX();
    bodyDef.position.y = getY();
    //bodyDef.type = KinematicBody;
    bodyDef.type = DynamicBody;
    //bodyDef.type = StaticBody;
    box = world.createBody(bodyDef);
    //  box.setType(DynamicBody);
    FixtureDef fixtureDef = new FixtureDef();
    fixtureDef.density = 0; //default 1f
    fixtureDef.restitution = 1f;
    fixtureDef.friction = 1f;
    PolygonShape poly = new PolygonShape();
    poly.setAsBox(0.0f, 0.0f);
    //poly.setAsBox(getWidth(), getHeight());
    fixtureDef.shape = poly;
    //box.createFixture(fixtureDef).setUserData("bot");
    poly.dispose();
    customUserData = new CustomUserData("bot", this);
    loader.attachFixture(box, "zombie", fixtureDef, 65, customUserData);
    botOrigin = loader.getOrigin("zombie", 65).cpy();
    setOrigin(botOrigin.x, botOrigin.y);
    botPos = getPosition().sub(botOrigin);
    setPosition(botPos.x, botPos.y);
    box.setUserData(customUserData);

}
    public void createBodyPudge(com.badlogic.gdx.physics.box2d.World world){
        phworld = world;
        BodyEditorLoader loader = new BodyEditorLoader(Gdx.files.internal(crimsonTD.getInstance().resolvePath("bodyproject.json")));
        BodyDef bodyDef = new BodyDef();
        bodyDef.position.x = getX();
        bodyDef.position.y = getY();
        //bodyDef.type = KinematicBody;
        bodyDef.type = DynamicBody;
        //bodyDef.type = StaticBody;
        box = world.createBody(bodyDef);
        //  box.setType(DynamicBody);
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.density = 0; //default 1f
        fixtureDef.restitution = 1f;
        fixtureDef.friction = 1f;
        PolygonShape poly = new PolygonShape();
        poly.setAsBox(0.0f, 0.0f);
        //poly.setAsBox(getWidth(), getHeight());
        fixtureDef.shape = poly;
        //box.createFixture(fixtureDef).setUserData("bot");
        poly.dispose();
        customUserData = new CustomUserData("bot", this);
        loader.attachFixture(box, "pudge", fixtureDef, 65, customUserData);
        botOrigin = loader.getOrigin("pudge", 65).cpy();
        setOrigin(botOrigin.x, botOrigin.y);
        botPos = getPosition().sub(botOrigin);
        setPosition(botPos.x, botPos.y);
        box.setUserData(customUserData);

    }

    public Bot(TextureRegion image, float x, float y, float width, float height, float originX, float originY, World world, BotType bottype, LevelWorld levelworld)
    {
        super(image, x, y, width, height);
        this.botType = bottype;
      //  setOrigin(originX / image.getWidth() * width, originY / image.getHeight() * height);
        setRotationDirection(Direction.NONE);
        setMovementDirection(Direction.NONE);
        rotationStep = 10;
        movementStep = 50;
        switch(bottype) {
            case ZOMBIE:  createBodyZombie(world); break;
            case ZOMBIE_RANGE: createBodyZombie(world); break;
            case DOCTOR: createBodyZombie(world); break;
            case PYRO: createBodyZombie(world); break;
            case PUDGE: createBodyPudge(world); break;
            case HULK: createBodyZombie(world); break;
            case WITCH: createBodyZombie(world); break;
        }
        box.setUserData(new String("bot"));
        this.levelworld = levelworld;
        iceblock = new ImageActor(new Texture(crimsonTD.getInstance().resolvePath("iceblock.png")), 0, 0, 85, 85);
        iceblock.setVisible(false);
        blood = new Blood(new Texture(crimsonTD.getInstance().resolvePath("arenaborders.png")), 0, 0, 55, 45, levelworld);
    }


    public void act(float delta) {
        blood.update(this, delta);
        //System.out.println(box.getUserData());
      // System.out.println(isNearPlayer());
        if (speededUp) delta*=3;
        if(currentState == ObjectState.MOVING) {
        switch (rotationDirection){
            case LEFT:
                if (Math.abs(getRotation()-destinationAngle) < rotationStep) {
                    //setRotation(destinationAngle);
                    break;
                }
                if(!isNearPlayer() && currentState != ObjectState.FREEZED) {
                    rotateBy(rotationStep);
                    box.setTransform(getPosition(), (float) Math.toRadians((getRotation())));

                    //box.setTransform(getPosition(), (float)Math.toRadians((rotationStep)));
                    setRotation(getRotation() % 360);
                }
                else
                {
                    rotateBy(0);
                    box.setTransform(getPosition(), (float) Math.toRadians((getRotation())));
                    setRotation(getRotation() % 360);
                }
                break;
            case RIGHT:
                if (Math.abs(getRotation()-destinationAngle) < rotationStep) {
                    //setRotation(destinationAngle);
                    break;
                }
                if(!isNearPlayer() && currentState != ObjectState.FREEZED) {
                    rotateBy(-rotationStep);
                    box.setTransform(getPosition(), (float) Math.toRadians((getRotation())));

                    //box.setTransform(getPosition(), (float)Math.toRadians((-rotationStep)));
                    setRotation((360 + getRotation()) % 360);
                }
                else
                {
                    rotateBy(0);
                    box.setTransform(getPosition(), (float) Math.toRadians((getRotation())));
                    setRotation(getRotation() % 360);
                }
                break;

        }

            if(currentState == ObjectState.SPAWNING || currentState == ObjectState.FREEZED)
            {
                box.setType(StaticBody);
            }
            else
            {
                box.setType(DynamicBody);
            }
            switch (movementDirection){
            case FORWARD:

                //moveBy(movementStep * delta * (float) Math.cos(getRotation() / 180 * Math.PI), movementStep * delta * (float) Math.sin(getRotation() / 180 * Math.PI));
                if(nearplayer == false && currentState != ObjectState.FREEZED) {
                    box.setLinearVelocity(movementStep * delta * (float) Math.cos(getRotation() / 180 * Math.PI) * 100, movementStep * delta * (float) Math.sin(getRotation() / 180 * Math.PI) * 100);
                }
                else
                {
                    box.setLinearVelocity(0,0);
                }
                break;
            case BACKWARD:
               // moveBy(-movementStep * delta * (float) Math.cos(getRotation() / 180 * Math.PI), -movementStep * delta * (float) Math.sin(getRotation() / 180 * Math.PI));
                box.setLinearVelocity(-movementStep * delta * (float) Math.cos(getRotation() / 180 * Math.PI) * 100, -movementStep * delta * (float) Math.sin(getRotation() / 180 * Math.PI) * 100);
                    break;
        }}

        if(currentState == ObjectState.FREEZED) {
            box.setLinearVelocity(0, 0);
            timefreezed += delta;
            //iceblock = new ImageActor(new Texture("android//assets//iceblock.png"), box.getPosition().x - getWidth() / 2 + 5, box.getPosition().y - getHeight() / 2 + 3, 70, 70);
            iceblock.setPosition(box.getPosition().x - getWidth(), box.getPosition().y - getHeight() / 2);
            box.setType(StaticBody);
            iceblock.setVisible(true);
            levelworld.addActor(iceblock);
            if (timefreezed >= freezetimer) {
                iceblock.setVisible(false);
                box.setType(DynamicBody);
                if (!isspawned)
                    timefromspawn += delta;
                if (timefromspawn <= spawntimer) {
                    // timefromspawn = 0;
                    currentState = ObjectState.SPAWNING;
                } else {
                    isspawned = true;
                    if (isNearPlayer() && currentState != ObjectState.DISABLED) {
                        currentState = ObjectState.ATTACKING;
                    } else {
                        if (currentState != ObjectState.SPAWNING && currentState != ObjectState.DISABLED) {
                            currentState = ObjectState.MOVING;
                        } else {
                            if (currentState != ObjectState.DISABLED)
                                currentState = ObjectState.SPAWNING;
                        }
                    }
                }

            }
        }
        else {
            if (!isspawned)
                timefromspawn += delta;
            if (timefromspawn <= spawntimer) {
                // timefromspawn = 0;
                currentState = ObjectState.SPAWNING;
            } else {
                isspawned = true;
                if (isNearPlayer() && currentState != ObjectState.DISABLED) {
                    currentState = ObjectState.ATTACKING;
                } else {
                    if (currentState != ObjectState.SPAWNING && currentState != ObjectState.DISABLED) {
                        currentState = ObjectState.MOVING;
                    } else {
                        if (currentState != ObjectState.DISABLED)
                            currentState = ObjectState.SPAWNING;
                    }
                }
            }
        }
        //System.out.println(nearplayer);
        //setRotation((float)Math.toDegrees(box.getAngle()));

        //setX(box.getPosition().x);
        //setY(box.getPosition().y);
        setPosition(box.getPosition().x - getWidth() / 2, box.getPosition().y - getHeight() / 2);
    }
    public void act2(float delta)
    {
        setRotation((float) Math.toDegrees(box.getAngle()));
        //setPosition(box.getPosition().x-getOriginX(), box.getPosition().y-getOriginY());

        setPosition(box.getPosition().x - getWidth() / 2 + 5, box.getPosition().y - getHeight() / 2 + 3);
    }


    public Direction getRotationDirection() {
        return rotationDirection;
    }
    public void setRotationDirection(Direction rotationDirection) {
        this.rotationDirection = rotationDirection;
    }
    public Direction getMovementDirection() {
        return movementDirection;
    }
    public void setMovementDirection(Direction movementDirection) {
        this.movementDirection = movementDirection;
    }
    public boolean isSpeededUp() {
        return speededUp;
    }
    public void setSpeededUp(boolean speededUp) {
        this.speededUp = speededUp;
    }

    public void setDestinationAngle(float destinationAngle) {
        this.destinationAngle = destinationAngle;
    }

    public float getMovementStep() {
        return movementStep;
    }
    public void setMovementStep(float movementStep) {
        this.movementStep = movementStep;
    }
    public float getRotationStep() {
        return rotationStep;
    }
    public void setRotationStep(float rotationStep) {
        this.rotationStep = rotationStep;
    }

    public void setTexture(TextureRegion texture)
    {
        this.img = texture;
    }
    public void setState(ObjectState state)
    {
        currentState = state;
    }
    public ObjectState getCurrentState()
    {
        return currentState;
    }
    public void setCurrentState(ObjectState currentState) {
        this.currentState = currentState;
    }
    public Vector2 getPosition(){
        return box.getPosition();
    }



    public boolean isNearPlayer() {
        return nearplayer;
    }

    public void setNearplayer(boolean nearplayer) {
        this.nearplayer = nearplayer;
    }

    public void setCurrentHP(int currentHP) {
        this.currentHP = currentHP;
    }
    public int getCurrentHP() {
        return currentHP;
    }


    public BotType getBotType() {
        return botType;
    }


    public Blood getBlood() {
        return blood;
    }


}
