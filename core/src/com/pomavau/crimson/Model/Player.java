package com.pomavau.crimson.Model;

import com.badlogic.gdx.Gdx;
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
import com.pomavau.crimson.Controller.BodyEditorLoader;
import com.pomavau.crimson.Controller.CustomUserData;
import com.pomavau.crimson.Controller.Direction;
import com.pomavau.crimson.Controller.ObjectState;
import com.pomavau.crimson.Controller.PlayerController;
import com.pomavau.crimson.Controller.Point;
import com.pomavau.crimson.Controller.Weapon;
import com.pomavau.crimson.View.ImageActor;
import com.pomavau.crimson.crimsonTD;

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

    public Fixture playerPhysicsFixture;
    public Fixture playerSensorFixture;
    private Direction rotationDirection;
    private Direction movementDirection;
    private Direction viewDirection;
    private ObjectState currentState = ObjectState.MOVING;
    private boolean speededUp;
    private float destinationAngle;
    private boolean isBlockedX = false;
    private boolean isBlockedY = false;
    private boolean isPaused = false;
    private boolean isBlockedTop = false;
    private boolean isBlockedDown = false;
    private boolean isBlockedRight = false;
    private boolean isBlockedLeft = false;
    private CustomUserData customUserData;
    private Box2DDebugRenderer debugRenderer;
    private Vector2 playerOrigin;
    private Vector2 playerPos;
    private Point shootingPoint;
    private Weapon currentWeapon;



    private int currentHP = 100;
    private int maxHP = 100;

    public Player(Texture image, float x, float y, float width, float height, float originX, float originY, World world) {
        super(image, x, y, width, height);
        //setOrigin(originX / image.getWidth() * width, originY / image.getHeight() * height);
        setRotationDirection(Direction.NONE);
        setMovementDirection(Direction.NONE);
        rotationStep = 5;
        movementStep = 150;
        createBody(world);
        shootingPoint = new Point(x, y, world);
        currentWeapon = Weapon.ASSAULTRIFLE;
        //currentWeapon = Weapon.ICERIFLE;
        //setOrigin(getWidth()/2, getHeight()/2);

    }
    public void createBody(World world){
        BodyEditorLoader loader = new BodyEditorLoader(Gdx.files.internal("android/assets/hero/bodyproject.json"));
        BodyDef bodyDef = new BodyDef();
      //  bodyDef.position.x = getX()+getWidth()/2;
       // bodyDef.position.y = getY()+getHeight()/2;
        bodyDef.position.x = getX();
        bodyDef.position.y = getY();
        bodyDef.type = DynamicBody;
        //bodyDef.type = KinematicBody;
        box = world.createBody(bodyDef);
      //  box.setType(DynamicBody);
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.density = 500f;
        fixtureDef.restitution = 1f;
        fixtureDef.friction = 1f;

        PolygonShape poly = new PolygonShape();
        poly.setAsBox(0.4f, 0.4f);
        //poly.setAsBox(getWidth(), getHeight(), new Vector2(getOriginX(), getOriginY()), 0);
       // poly.setAsBox(getWidth() * 0.7f, getHeight() * 0.7f);
       // poly.setAsBox(100, 60);
        fixtureDef.shape = poly;
        //box.createFixture(fixtureDef);
        poly.dispose();
        //box.getLocalCenter().set(getOriginX(), getOriginY());
        customUserData = new CustomUserData("player", this);
        loader.attachFixture(box, "player", fixtureDef, 95, customUserData);
        //Fixture abc = loader.attachFixture(box, "player", fixtureDef, 95);
        playerOrigin = loader.getOrigin("player", 95).cpy();
        setOrigin(playerOrigin.x, playerOrigin.y);
        playerPos = getPosition().sub(playerOrigin);
        setPosition(playerPos.x, playerPos.y);
        //box.getPosition().x = getX() + getWidth() / 2;
        //box.getPosition().y = getY() + getWidth() / 2;
    }

    public void act1(float delta) {
        if (speededUp) delta*=3;

        switch (rotationDirection){
            case LEFT:
                box.setAngularVelocity((float) Math.toRadians(rotationStep));
                break;
            case RIGHT:
                box.setAngularVelocity((float) -Math.toRadians(rotationStep));
                break;
        }
        switch (movementDirection){
            case FORWARD:
                //box.applyAngularImpulse(movementStep*delta, true);
                box.setLinearVelocity(movementStep * delta * (float) Math.cos(getRotation() / 180 * Math.PI) * 100* crimsonTD.getInstance().getLeftTouchpadKnobX(), movementStep * delta * (float) Math.sin(getRotation() / 180 * Math.PI) * 100* crimsonTD.getInstance().getLeftTouchpadKnobY());
                break;
            case BACKWARD:

                /*if (isBlockedY == false)
                moveBy(-movementStep * delta * (float) Math.cos(getRotation() / 180 * Math.PI), -movementStep * delta * (float) Math.sin(getRotation() / 180 * Math.PI));
                */
               // box.setLinearVelocity(-movementStep * delta * (float) Math.cos(getRotation() / 180 * Math.PI), -movementStep * delta * (float) Math.sin(getRotation() / 180 * Math.PI));
            /*    if(isBlockedY == false && isBlockedX == false) {
                    moveBy(-movementStep * delta * (float) Math.cos(getRotation() / 180 * Math.PI) * 100, -movementStep * delta * (float) Math.sin(getRotation() / 180 * Math.PI)* 100);
                }
                else
                {
                    if(isBlockedX == true)
                        moveBy(0, -movementStep * delta * (float) Math.sin(getRotation() / 180 * Math.PI));
                    if (isBlockedY == true)
                        moveBy(-movementStep * delta * (float) Math.cos(getRotation() / 180 * Math.PI), 0);

                }*/
               //box.setLinearVelocity(getPosition());
                currentState = ObjectState.MOVING;
               // if(currentState != ObjectState.)
                box.setLinearVelocity(-movementStep * delta * (float) Math.cos(getRotation() / 180 * Math.PI) * 100 * crimsonTD.getInstance().getLeftTouchpadKnobX(), -movementStep * delta * (float) Math.sin(getRotation() / 180 * Math.PI)* 100 * crimsonTD.getInstance().getLeftTouchpadKnobY());
                //box.getPosition().x = getX();
                //box.getPosition().y = getY();

                //box.applyLinearImpulse(-movementStep * delta * (float) Math.cos(getRotation() / 180 * Math.PI), -movementStep * delta * (float) Math.sin(getRotation() / 180 * Math.PI), getX(), getY(), true);
                //box.applyLinearImpulse(box.getLinearVelocity(), box.getLinearVelocity(), true);

                //System.out.println(box.getLinearVelocity());
                //box.applyAngularImpulse(movementStep, true);
                break;
            default:
                box.setLinearVelocity(0, 0);
        }

        /*
        if(currentState == ObjectState.STAYING)
        {
            box.setLinearVelocity(0, 0);
        }
        if ((getRotation() <= 45 && getRotation() >= 0) || (getRotation() >= 315 && getRotation() <= 360))
            setViewDirection(Direction.RIGHT);
        if ((getRotation() <= 135 && getRotation() >= 90) || (getRotation() >= 90 && getRotation() <= 45))
            setViewDirection(Direction.FORWARD);
        if ((getRotation() <= 180 && getRotation() >= 135) || (getRotation() >= 180 && getRotation() <= 225))
            setViewDirection(Direction.LEFT);
        if ((getRotation() <= 315 && getRotation() >= 270) || (getRotation() >= 225 && getRotation() <= 270))
            setViewDirection(Direction.BACKWARD);*/
       // setPosition(box.getPosition().x, box.getPosition().y);
        setRotation((float) Math.toDegrees(box.getAngle()));
        setX(box.getPosition().x);
        setY(box.getPosition().y);
        //box.getPosition().x = getX();
        //box.getPosition().y = getY();
       // box.setUserData("pl");
        //System.out.println("PlayerX: " +getX()+"/BoxX: "  +box.getPosition().x + "PlayerY: " +getY()+"/BoxY: "  +box.getPosition().y + " BoxAngle: " + box.getAngle());
        System.out.println(crimsonTD.getInstance().getLeftTouchpadKnobX());
    }

    public void act(float delta) {

        setRotation((float) Math.toDegrees(box.getAngle()));
        //setPosition(box.getPosition().x-getOriginX(), box.getPosition().y-getOriginY());
        setPosition(box.getPosition().x - getWidth() / 2 + 15, box.getPosition().y - getHeight() / 2 + 8);
        if(crimsonTD.getInstance().getRightTouchpadKnobX() == 0
                && crimsonTD.getInstance().getRightTouchpadKnobY() == 0)
            box.setAngularVelocity(0);
        shootingPoint.setPosition(getX() + getWidth(), getY() + getHeight());

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
    public void setState(ObjectState state)
    {
        currentState = state;
    }
    public ObjectState getCurrentState()
    {
        return currentState;
    }
    public void setBoxRotation(float angle)
    {
        box.setTransform(getPosition(), box.getAngle() + angle);
      //  box.
    }

    public Point getShootingPoint()
    {
        return shootingPoint;
    }

    public Weapon getCurrentWeapon() {
        return currentWeapon;
    }

    public void setCurrentWeapon(Weapon currentWeapon) {
        this.currentWeapon = currentWeapon;
    }
    public int getCurrentHP() {
        return currentHP;
    }

    public void setCurrentHP(int currentHP) {
        this.currentHP = currentHP;
    }
}
