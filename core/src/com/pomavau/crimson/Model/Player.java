package com.pomavau.crimson.Model;
import com.badlogic.gdx.graphics.Texture;
import com.pomavau.crimson.Controller.Direction;
import com.pomavau.crimson.Controller.PlayerController;
import com.pomavau.crimson.View.ImageActor;

public class Player extends ImageActor {
    private PlayerController controller;

    public void setController(PlayerController controller) {
        this.controller = controller;
    }

    float movementStep;
    float rotationStep;
    private Direction rotationDirection;
    private Direction movementDirection;
    private boolean speededUp;
    private float destinationAngle;


    public Player(Texture image, float x, float y, float width, float height, float originX, float originY) {
        super(image, x, y, width, height);
        setOrigin(originX / image.getWidth() * width, originY / image.getHeight() * height);
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
    public float getDestinationAngle() {
        return destinationAngle;
    }

    public float getRotationStep() {
        return rotationStep;
    }
    public float getMovementStep() { return movementStep; }
}
