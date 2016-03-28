package com.pomavau.crimson.Model;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.pomavau.crimson.Controller.Animation;
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
    private boolean isBlockedX = false;
    private boolean isBlockedY = false;
    private boolean isPaused = false;

    public Player(Texture image, float x, float y, float width, float height, float originX, float originY) {
        super(image, x, y, width, height);
        setOrigin(originX / image.getWidth() * width, originY / image.getHeight() * height);
        setRotationDirection(Direction.NONE);
        setMovementDirection(Direction.NONE);
        rotationStep = 5;
        movementStep = 150;
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
                if (isBlockedY == false)
                moveBy(movementStep * delta * (float) Math.cos(getRotation() / 180 * Math.PI), movementStep * delta * (float) Math.sin(getRotation() / 180 * Math.PI));
                break;
            case BACKWARD:
                if (isBlockedY == false)
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

    public void setMovementStep(float movementStep) { this.movementStep = movementStep; }

    public void setTexture(TextureRegion texture)
    {

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
}
