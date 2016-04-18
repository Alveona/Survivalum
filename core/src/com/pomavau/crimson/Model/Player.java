package com.pomavau.crimson.Model;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.pomavau.crimson.Controller.Animation;
import com.pomavau.crimson.Controller.Direction;
import com.pomavau.crimson.Controller.PlayerController;
import com.pomavau.crimson.View.ImageActor;

import static com.badlogic.gdx.physics.box2d.BodyDef.BodyType.DynamicBody;
import static com.badlogic.gdx.physics.box2d.BodyDef.BodyType.KinematicBody;

public class Player extends ImageActor {
    private PlayerController controller;

    public void setController(PlayerController controller) {
        this.controller = controller;
    }

    float movementStep;
    float rotationStep;
    Body box;
    //World phWorld = new World(new LevelWorld().getPhysicsWorld());
    public Fixture playerPhysicsFixture;
    public Fixture playerSensorFixture;
    private Direction rotationDirection;
    private Direction movementDirection;
    private Direction viewDirection;
    private boolean speededUp;
    private float destinationAngle;
    private boolean isBlockedX = false;
    private boolean isBlockedY = false;
    private boolean isPaused = false;
    private boolean isBlockedTop = false;
    private boolean isBlockedDown = false;
    private boolean isBlockedRight = false;
    private boolean isBlockedLeft = false;
    private Box2DDebugRenderer debugRenderer;


    public Player(Texture image, float x, float y, float width, float height, float originX, float originY, World world) {
        super(image, x, y, width, height);
        setOrigin(originX / image.getWidth() * width, originY / image.getHeight() * height);
        setRotationDirection(Direction.NONE);
        setMovementDirection(Direction.NONE);
        rotationStep = 5;
        movementStep = 150;
        createBody(world);

    }
    public void createBody(World world){
        BodyDef bodyDef = new BodyDef();
        bodyDef.position.x = getX();
        bodyDef.position.y = getY();
        //bodyDef.type = KinematicBody;
        bodyDef.type = KinematicBody;
        box = world.createBody(bodyDef);
       // box.setType(DynamicBody);
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.density = 1f;
        fixtureDef.restitution = 1f;
        fixtureDef.friction = 1f;
        PolygonShape poly = new PolygonShape();
        poly.setAsBox(0.4f, 0.4f);
        fixtureDef.shape = poly;
        box.createFixture(fixtureDef);
        poly.dispose();



    }

    public Player(TextureRegion image, float x, float y, float width, float height, float originX, float originY)
    {
        super(image, x, y, width, height);
        //setOrigin(originX / image.getWidth() * width, originY / image.getHeight() * height);
        setRotationDirection(Direction.NONE);
        setMovementDirection(Direction.NONE);
        rotationStep = 5;
        movementStep = 150;
      //  image = animation.getFrame();
    }
    public Player(Body b, Texture image, float x, float y, float width, float height, float originX, float originY) {
        super(image, x, y, width, height);

        box = b;
        PolygonShape poly = new PolygonShape();
        poly.setAsBox(0.4f, 0.4f);
        playerPhysicsFixture = box.createFixture(poly,0);
        poly.dispose();
        setOrigin(originX / image.getWidth() * width, originY / image.getHeight() * height);
        setRotationDirection(Direction.NONE);
        setMovementDirection(Direction.NONE);
        rotationStep = 5;
        movementStep = 150;
        box.setBullet(true);
    }
    public Player(Body b, TextureRegion image, float x, float y, float width, float height, float originX, float originY)
    {
        super(image, x, y, width, height);
        box = b;
        PolygonShape poly = new PolygonShape();
        poly.setAsBox(0.4f, 0.4f);
        playerPhysicsFixture = box.createFixture(poly,0);
        poly.dispose();
        //setOrigin(originX / image.getWidth() * width, originY / image.getHeight() * height);
        setRotationDirection(Direction.NONE);
        setMovementDirection(Direction.NONE);
        rotationStep = 5;
        movementStep = 150;
        box.setBullet(true);

        //  image = animation.getFrame();
    }

    public void act(float delta) {
        if (speededUp) delta*=3;
        switch (rotationDirection){
            case LEFT:
               // box.getLinearVelocity();//math.cos(rotation)*speed, math.sin(rotation)*speed


                if (Math.abs(getRotation()-destinationAngle) < rotationStep) {
                    setRotation(destinationAngle);
                    break;
                }
                rotateBy(rotationStep);
                box.setTransform(getPosition(), getRotation());
                setRotation(getRotation() % 360);


                break;

            case RIGHT:

                if (Math.abs(getRotation()-destinationAngle) < rotationStep) {
                    setRotation(destinationAngle);
                    break;
                }
                rotateBy(-rotationStep);
                box.setTransform(getPosition(), getRotation());
                setRotation((360 + getRotation()) % 360);


                break;

        }
        switch (movementDirection){
            case FORWARD:
               /*
                if (isBlockedY == false)
                moveBy(movementStep * delta * (float) Math.cos(getRotation() / 180 * Math.PI), movementStep * delta * (float) Math.sin(getRotation() / 180 * Math.PI));
               */
                //box.setLinearVelocity(movementStep * delta * (float) Math.cos(getRotation() / 180 * Math.PI), movementStep * delta * (float) Math.sin(getRotation() / 180 * Math.PI));
                if(isBlockedY == false && isBlockedX == false) {
                    moveBy(movementStep * delta * (float) Math.cos(getRotation() / 180 * Math.PI), movementStep * delta * (float) Math.sin(getRotation() / 180 * Math.PI));
                }
                else
                {
                    if(isBlockedX == true) {
                        if ((isBlockedRight() == true && viewDirection != Direction.LEFT) || (isBlockedLeft() == true && viewDirection != Direction.RIGHT)) {
                            moveBy(0, movementStep * delta * (float) Math.sin(getRotation() / 180 * Math.PI));
                        }
                        else
                        {
                            moveBy(movementStep * delta * (float) Math.cos(getRotation() / 180 * Math.PI), movementStep * delta * (float) Math.sin(getRotation() / 180 * Math.PI));

                        }

                    }
                  // System.out.format("blockedTOP: %b ditection: %s\r\n", isBlockedTop(), viewDirection);
                    if (isBlockedY == true) {
                        if ((isBlockedTop() == true && viewDirection != Direction.BACKWARD) || (isBlockedDown() == true && viewDirection != Direction.FORWARD)) {
                            moveBy(movementStep * delta * (float) Math.cos(getRotation() / 180 * Math.PI), 0);
                        }
                        else
                        {
                            moveBy(movementStep * delta * (float) Math.cos(getRotation() / 180 * Math.PI), movementStep * delta * (float) Math.sin(getRotation() / 180 * Math.PI));
                        }
                    }
                }
                //box.setLinearVelocity(getPosition());
                box.setLinearVelocity(movementStep * delta * (float) Math.cos(getRotation() / 180 * Math.PI), movementStep * delta * (float) Math.sin(getRotation() / 180 * Math.PI));
                box.applyLinearImpulse(movementStep * delta * (float) Math.cos(getRotation() / 180 * Math.PI), movementStep * delta * (float) Math.sin(getRotation() / 180 * Math.PI), getX(), getY(), true);
               // box.applyAngularImpulse(movementStep, true);
               // System.out.println((movementStep * delta * (float) Math.cos(getRotation() / 180 * Math.PI))+ " " + movementStep * delta * (float) Math.sin(getRotation() / 180 * Math.PI));
               // box.getPosition().x = getX();
               // box.getPosition().y = getY();
                break;
            case BACKWARD:

                /*if (isBlockedY == false)
                moveBy(-movementStep * delta * (float) Math.cos(getRotation() / 180 * Math.PI), -movementStep * delta * (float) Math.sin(getRotation() / 180 * Math.PI));
                */
               // box.setLinearVelocity(-movementStep * delta * (float) Math.cos(getRotation() / 180 * Math.PI), -movementStep * delta * (float) Math.sin(getRotation() / 180 * Math.PI));
                if(isBlockedY == false && isBlockedX == false) {
                    moveBy(-movementStep * delta * (float) Math.cos(getRotation() / 180 * Math.PI), -movementStep * delta * (float) Math.sin(getRotation() / 180 * Math.PI));
                }
                else
                {
                    if(isBlockedX == true)
                        moveBy(0, -movementStep * delta * (float) Math.sin(getRotation() / 180 * Math.PI));
                    if (isBlockedY == true)
                        moveBy(-movementStep * delta * (float) Math.cos(getRotation() / 180 * Math.PI), 0);

                }
               //box.setLinearVelocity(getPosition());
                 box.setLinearVelocity(-movementStep * delta * (float) Math.cos(getRotation() / 180 * Math.PI), -movementStep * delta * (float) Math.sin(getRotation() / 180 * Math.PI));
                //box.getPosition().x = getX();
                //box.getPosition().y = getY();

                box.applyLinearImpulse(-movementStep * delta * (float) Math.cos(getRotation() / 180 * Math.PI), -movementStep * delta * (float) Math.sin(getRotation() / 180 * Math.PI), getX(), getY(), true);
                //box.applyLinearImpulse(box.getLinearVelocity(), box.getLinearVelocity(), true);

                //System.out.println(box.getLinearVelocity());
                //box.applyAngularImpulse(movementStep, true);
                break;
        }

        if ((getRotation() <= 45 && getRotation() >= 0) || (getRotation() >= 315 && getRotation() <= 360))
            setViewDirection(Direction.RIGHT);
        if ((getRotation() <= 135 && getRotation() >= 90) || (getRotation() >= 90 && getRotation() <= 45))
            setViewDirection(Direction.FORWARD);
        if ((getRotation() <= 180 && getRotation() >= 135) || (getRotation() >= 180 && getRotation() <= 225))
            setViewDirection(Direction.LEFT);
        if ((getRotation() <= 315 && getRotation() >= 270) || (getRotation() >= 225 && getRotation() <= 270))
            setViewDirection(Direction.BACKWARD);
       // setPosition(box.getPosition().x, box.getPosition().y);
        setRotation(box.getAngle());
        setX(box.getPosition().x);
        setY(box.getPosition().y);
        //box.getPosition().x = getX();
        //box.getPosition().y = getY();
       // box.setUserData("pl");
        System.out.println("PlayerX: " +getX()+"/BoxX: "  +box.getPosition().x + "PlayerY: " +getY()+"/BoxY: "  +box.getPosition().y + " BoxAngle: " + box.getAngle());
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
    public float getDestinationAngle() {
        return destinationAngle;
    }

    public float getRotationStep() {
        return rotationStep;
    }
    public float getMovementStep() { return movementStep; }

    public void setMovementStep(float movementStep) { this.movementStep = movementStep; }

    public void setTexture(TextureRegion texture)
    {
        this.img = texture;
    }

    public boolean getBlockedX() {
        return isBlockedX;
    }

    public void setBlockedX(boolean isBlockedX) {
        this.isBlockedX = isBlockedX;
    }

    public boolean getBlockedY() {
        return isBlockedY;
    }

    public void setBlockedY(boolean isBlockedY) {
        this.isBlockedY = isBlockedY;
    }

    public void setPaused(boolean isPaused)
    {
        this.isPaused = isPaused;
    }
    public boolean getPaused() {
        return isPaused;
    }

    public Direction getViewDirection() {
        return viewDirection;
    }

    public void setViewDirection(Direction viewDirection) {
        this.viewDirection = viewDirection;
    }

    public boolean isBlockedTop() {
        return isBlockedTop;
    }

    public void setIsBlockedTop(boolean isBlockedTop) {
        this.isBlockedTop = isBlockedTop;
    }

    public boolean isBlockedDown() {
        return isBlockedDown;
    }

    public void setIsBlockedDown(boolean isBlockedDown) {
        this.isBlockedDown = isBlockedDown;
    }

    public boolean isBlockedRight() {
        return isBlockedRight;
    }

    public void setIsBlockedRight(boolean isBlockedRight) {
        this.isBlockedRight = isBlockedRight;
    }

    public boolean isBlockedLeft() {
        return isBlockedLeft;
    }

    public void setIsBlockedLeft(boolean isBlockedLeft) {
        this.isBlockedLeft = isBlockedLeft;
    }

    public float getFriction(){
        return playerSensorFixture.getFriction();
    }
    public Body getBody(){
        return box;
    }
    public void setFriction(float f){
        playerSensorFixture.setFriction(f);
        playerPhysicsFixture.setFriction(f);
    }
    public Vector2 getPosition(){
        return box.getPosition();
    }
}
