package com.pomavau.crimson.Model;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.pomavau.crimson.Controller.BotController;
import com.pomavau.crimson.Controller.Direction;
import com.pomavau.crimson.Controller.PlayerController;
import com.pomavau.crimson.View.ImageActor;
import com.pomavau.crimson.crimsonTD;

/**
 * Created by Pomavau on 12.03.16.
 */
public class Bot extends ImageActor {
    private BotController controller;
    public void setController(BotController controller) {
        this.controller = controller;
    }

    float size;
    float movementStep;
    float rotationStep;
    private Direction rotationDirection;
    private Direction movementDirection;
    private boolean speededUp;
    ShapeRenderer shape;
    private float destinationAngle;
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
public Bot(Texture image, float x, float y, float width, float height, float originX, float originY) {
    super(image, x, y, width, height);
    setOrigin(originX / image.getWidth() * width, originY / image.getHeight() * height);
    setRotationDirection(Direction.NONE);
    setMovementDirection(Direction.NONE);
    rotationStep = 5;
    movementStep = 150;
}
    public Bot(TextureRegion image, float x, float y, float width, float height, float originX, float originY)
    {
        super(image, x, y, width, height);
      //  setOrigin(originX / image.getWidth() * width, originY / image.getHeight() * height);
        setRotationDirection(Direction.NONE);
        setMovementDirection(Direction.NONE);
        rotationStep = 5;
        movementStep = 150;
    }


    public void act(float delta) {
        if (speededUp) delta*=3;
        switch (rotationDirection){
            case LEFT:
                if (Math.abs(getRotation()-destinationAngle) < rotationStep) {
                    setRotation(destinationAngle);
                    break;
                }
                rotateBy(rotationStep);
                setRotation(getRotation()%360);
                break;
            case RIGHT:
                if (Math.abs(getRotation()-destinationAngle) < rotationStep) {
                    setRotation(destinationAngle);
                    break;
                }
                rotateBy(-rotationStep);
                setRotation((360 + getRotation()) % 360);
                break;

        }
        switch (movementDirection){
            case FORWARD:
                moveBy(movementStep * delta * (float) Math.cos(getRotation() / 180 * Math.PI), movementStep * delta * (float) Math.sin(getRotation() / 180 * Math.PI));
                break;
            case BACKWARD:
                moveBy(-movementStep * delta * (float) Math.cos(getRotation() / 180 * Math.PI), -movementStep * delta * (float) Math.sin(getRotation() / 180 * Math.PI));

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
}
