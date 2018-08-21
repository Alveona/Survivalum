package com.pomavau.crimson.Model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.utils.Array;
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

    private boolean invul = false;
    private boolean infammo = false;
    private float invultimer;
    private float infammotimer;
    private float invulMax = 15;
    private float infammoMax = 20;

    private int currentXP = 0;

    private int reqXP;
    private int currentLVL = 1;
    private Array<Integer> XPScale;

    private int scorescount = 0;

    private int maxHP = 25000;  //DEFAULT: 50k
    private int currentHP = maxHP;



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
        //currentWeapon = Weapon.FLAMETHROWER;
        //setOrigin(getWidth()/2, getHeight()/2);
        XPScale = new Array<Integer>();
        XPScale.add(1000);
        XPScale.add(1000);
        reqXP = XPScale.get(XPScale.size - 1);
    }
    public void createBody(World world){
        BodyEditorLoader loader = new BodyEditorLoader(Gdx.files.internal(crimsonTD.getInstance().resolvePath("bodyproject.json")));
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
        Filter f = new Filter();
        f.groupIndex = -1;

        loader.attachFixture(box, "player", fixtureDef, 95, customUserData, -1);
        //Fixture abc = loader.attachFixture(box, "player", fixtureDef, 95);
        playerOrigin = loader.getOrigin("player", 95).cpy();
        setOrigin(playerOrigin.x, playerOrigin.y);
        playerPos = getPosition().sub(playerOrigin);
        setPosition(playerPos.x, playerPos.y);
        //box.getPosition().x = getX() + getWidth() / 2;
        //box.getPosition().y = getY() + getWidth() / 2;

    }


    public void act(float delta) {

        setRotation((float) Math.toDegrees(box.getAngle()));
        //setPosition(box.getPosition().x-getOriginX(), box.getPosition().y-getOriginY());
        setPosition(box.getPosition().x - getWidth() / 2+15, box.getPosition().y - getHeight() / 2+8); //+15 +8
        if(crimsonTD.getInstance().getRightTouchpadKnobX() == 0
                && crimsonTD.getInstance().getRightTouchpadKnobY() == 0)
            box.setAngularVelocity(0);
        shootingPoint.setPosition(getX() + getWidth(), getY() + getHeight());

        if (currentXP > reqXP)
            crimsonTD.getInstance().LvlUp();
        //System.out.println(currentWeapon);

        if(invul == true)
            invultimer += delta;
        if(invultimer >= invulMax) {
            invul = false;
            invultimer = 0;
        }
        if(infammo == true)
            infammotimer += delta;
        if(infammotimer >= infammoMax) {
            infammo = false;
            infammotimer = 0;
        }
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


    public int getScorescount() {
        return scorescount;
    }

    public void setScorescount(int scorescount) {
        this.scorescount = scorescount;
    }

    public int getMaxHP() {
        return maxHP;
    }

    public void setMaxHP(int maxHP) {
        this.maxHP = maxHP;
    }

    public void XPScaleAdd(Array<Integer> array)
    {
        array.add(array.get(array.size - 1) + array.get(array.size - 2));
    }

    public void LvLup()
    {
        currentXP = 0;
        XPScaleAdd(XPScale);
        reqXP = XPScale.get(currentLVL + 1);
        currentLVL++;
        System.out.format("Level Up! Current LVL: %d", currentLVL);
    }


    public int getCurrentXP() {
        return currentXP;
    }

    public void setCurrentXP(int currentXP) {
        this.currentXP = currentXP;
    }



    public int getReqXP() {
        return reqXP;
    }

    public void setReqXP(int reqXP) {
        this.reqXP = reqXP;
    }


    public void Perk_MS()
    {
        movementStep *= 1.1;
    }
    public void Perk_RS()
    {
        rotationStep *= 1.15;
    }
    public void Perk_LvlUP()
    {
        LvLup();
    }



    public boolean isInvul() {
        return invul;
    }

    public void setInvul(boolean invul) {
        this.invul = invul;
    }



    public boolean isInfammo() {
        return infammo;
    }

    public void setInfammo(boolean infammo) {
        this.infammo = infammo;
    }
}
