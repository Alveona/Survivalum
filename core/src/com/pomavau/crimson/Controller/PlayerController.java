package com.pomavau.crimson.Controller;

import com.badlogic.gdx.Gdx;
import com.pomavau.crimson.Controller.MovementControlStyle;
import com.pomavau.crimson.crimsonTD;
import com.pomavau.crimson.Model.LevelWorld;
import com.pomavau.crimson.Model.Player;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.Vector2;
//import com.pomavau.crimson.crimsonTD;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import static com.badlogic.gdx.Input.Keys.A;
import static com.badlogic.gdx.Input.Keys.CONTROL_LEFT;
import static com.badlogic.gdx.Input.Keys.D;
import static com.badlogic.gdx.Input.Keys.S;
import static com.badlogic.gdx.Input.Keys.SHIFT_LEFT;
import static com.badlogic.gdx.Input.Keys.W;

/**
 * Created by Гриша on 15.02.2016.
 */
public class PlayerController implements InputProcessor {
    private MovementControlStyle movementControlStyle;
    HashSet<Integer> pressedKeys;
    ArrayList<Character> printedCharacters;
    HashMap<Integer, Pointer> pressedPointers;
    HashSet<Integer> usedButtons;
    Player player;

    public PlayerController(LevelWorld stage) {
        player = stage.getPlayer();
        pressedKeys = new HashSet<Integer>();
        printedCharacters = new ArrayList<Character>();
        pressedPointers = new HashMap<Integer, Pointer>();
        movementControlStyle = crimsonTD.getInstance().getMovementControlStyle();
        usedButtons = new HashSet<Integer>();
        usedButtons.add(A);
        usedButtons.add(S);
        usedButtons.add(D);
        usedButtons.add(W);
        usedButtons.add(SHIFT_LEFT);
        usedButtons.add(CONTROL_LEFT);
    }

    public void update(LevelWorld world) {
        if(movementControlStyle == MovementControlStyle.JOYPAD) {
            player.getBody()
                    .setLinearVelocity(
                            crimsonTD.getInstance().getLeftTouchpadKnobX() * player.getMovementStep(),
                            crimsonTD.getInstance().getLeftTouchpadKnobY() * player.getMovementStep()
                    );
        }
        //System.out.format("FORWARD: %b, BACKWARD: %b\r\n", movesForward(), movesBackward());
        if(movementControlStyle == MovementControlStyle.BUTTONS) {
            //player.setMovementDirection(getMovementDirection());
            // player.setRotationDirection(getRotationDirection());
            //&& player.getRotation() > 270 && player.getRotation() < 360
            if (movesForward()) {
                player.getBody().setLinearVelocity((float)((player.getX() + player.getMovementStep()) * Math.cos(Math.toRadians(player.getRotation()))), (float) ((player.getY() + player.getMovementStep()) * Math.sin(Math.toRadians(player.getRotation()))));
                //player.getBody().setLinearVelocity((float) (player.getX() + player.getMovementStep()), (float) (player.getY() + player.getMovementStep()));

            }
            else {
                if (movesBackward()) {
                    player.getBody().setLinearVelocity(-(float) ((player.getX() + player.getMovementStep()) * Math.cos(Math.toRadians(player.getRotation()))), -(float) ((player.getY() + player.getMovementStep()) * Math.sin(Math.toRadians(player.getRotation()))));
                    // player.getBody().setLinearVelocity((float) (player.getX() - player.getMovementStep()), (float) (player.getY() - player.getMovementStep()));
                } else {
                    player.getBody().setLinearVelocity(0, 0);
                }
            }
            if(rotatesRight())
            {
                //player.setRotation(player.getRotation() - 5);
                player.getBody().setTransform(player.getPosition(), (float) Math.toRadians((player.getRotation() - 5)));

            }
            if(rotatesLeft())
            {
                //player.setRotation(player.getRotation() + 5);
                player.getBody().setTransform(player.getPosition(), (float) Math.toRadians((player.getRotation() + 5)));
            }
        }
        /*

        player.setSpeededUp(isSpeededUp());
        if (player.getX() > world.getWorldBorders().getX() && player.getX() < world.getWorldBorders().getX() + world.getWorldBorders().getWidth())
        {
            player.setBlockedX(false);
            player.setIsBlockedRight(false);
            player.setIsBlockedLeft(false);
            /*
            if(player.getX() > world.getWorldBorders().getX())
                player.setIsBlockedTop(true);
            if(player.getX() < world.getWorldBorders().getX() + world.getWorldBorders().getWidth())
                player.setIsBlockedDown(true);

        }
        else
        {
            player.setBlockedX(true);
            if(player.getX() < world.getWorldBorders().getX())
                player.setIsBlockedLeft(true);
            if(player.getX() > world.getWorldBorders().getX() + world.getWorldBorders().getWidth())
                player.setIsBlockedRight(true);
        }

        if (player.getY() > world.getWorldBorders().getY() && player.getY() < world.getWorldBorders().getY() + world.getWorldBorders().getHeight())
        {
            player.setBlockedY(false);
            player.setIsBlockedTop(false);
            player.setIsBlockedDown(false);
        }
        else
        {
            player.setBlockedY(true);
            if(player.getY() < world.getWorldBorders().getY() )
                player.setIsBlockedDown(true);
            if(player.getY() > world.getWorldBorders().getY() + world.getWorldBorders().getHeight())
                player.setIsBlockedTop(true);
        }*/
    }

    @Override
    public boolean keyDown(int keycode) {
        return usedButtons.contains(keycode) && pressedKeys.add(keycode);
    }

    @Override
    public boolean keyUp(int keycode) {
        return usedButtons.contains(keycode) && pressedKeys.remove(keycode);
    }

    @Override
    public boolean keyTyped(char character) {
        return printedCharacters.add(character);
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        pressedPointers.put(pointer, new Pointer(screenX, screenY, button));
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        if (!pressedPointers.containsKey(pointer))
            return false;
        pressedPointers.remove(pointer);
        return true;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        if (!pressedPointers.containsKey(pointer))
            return false;
        pressedPointers.get(pointer).setPosition(screenX, screenY);
        return true;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }

    public boolean isWalking() {
        return (movesBackward() || movesForward());
    }

    public boolean isRotating() {
        return (rotatesLeft() || rotatesRight());
    }

    public boolean isMoving() {
        return (isWalking() || isRotating()) && !isStanding();
    }

    public boolean isSpeededUp() {
        switch (movementControlStyle) {
            case BUTTONS:
                return (pressedKeys.contains(SHIFT_LEFT)) && !isStanding();
            case TOUCH:
                return pressedPointers.size() > 2;
            case JOYPAD:
                return false;
        }
        return false;

    }

    public boolean isStanding() {
        switch (movementControlStyle) {
            case BUTTONS:
                return (pressedKeys.contains(CONTROL_LEFT));
            case TOUCH:
                return pressedPointers.size() == 0;
            case JOYPAD:
                return crimsonTD.getInstance().getLeftTouchpadKnobX() == 0 && crimsonTD.getInstance().getLeftTouchpadKnobY() == 0;
        }
        return false;
    }

    public boolean movesForward() {
        switch (movementControlStyle) {
            case BUTTONS:
                return pressedKeys.contains(W);
            case TOUCH:
                return pressedPointers.size() > 0;
            case JOYPAD:
                return crimsonTD.getInstance().getLeftTouchpadKnobY() > 0;
        }
        return false;
    }

    public boolean movesBackward() {
        switch (movementControlStyle) {
            case BUTTONS:
                return pressedKeys.contains(S);
            case TOUCH:
                return false;
            case JOYPAD:
                return crimsonTD.getInstance().getLeftTouchpadKnobY() < 0;
        }
        return false;
    }

    public boolean rotatesLeft() {
        switch (movementControlStyle) {
            case BUTTONS:
                if (!(pressedKeys.contains(A)))
                    return false;
                player.setDestinationAngle(player.getRotation()+player.getRotationStep());
                return true;
            case TOUCH:
                if (pressedPointers.size() == 0) return false;
                Pointer tmp = pressedPointers.values().iterator().next();
                float destAngle = new Vector2(tmp.getX(), tmp.getY()).sub(player.getX(), player.getY()).angle();
                player.setDestinationAngle(destAngle);
                float playerAngle = player.getRotation();
                float delta = destAngle - playerAngle;
                if (delta > 0 && delta < 180 || delta < -180)
                    return true;
        }
        return false;
    }

    public boolean rotatesRight() {
        switch (movementControlStyle) {
            case BUTTONS:
                if (!(pressedKeys.contains(D)))
                    return false;
                player.setDestinationAngle(player.getRotation()-player.getRotationStep());
                return true;
            case TOUCH:
                if (pressedPointers.size() == 0) return false;
                Pointer tmp = pressedPointers.values().iterator().next();
                float destAngle = new Vector2(tmp.getX(), tmp.getY()).sub(player.getX(), player.getY()).angle();
                player.setDestinationAngle(destAngle);
                float playerAngle = player.getRotation();
                float delta = destAngle - playerAngle;
                if (delta < 0 && delta > -180 || delta > 180)
                    return true;
        }
        return false;
    }

    public Direction getMovementDirection() {
        switch (movementControlStyle) {
            case BUTTONS:
            case TOUCH:
                if (movesForward() && !movesBackward() && !isStanding()) return Direction.FORWARD;
                if (!movesForward() && movesBackward() && !isStanding()) return Direction.BACKWARD;
                break;
            case JOYPAD:
                if (movesForward() && !movesBackward() && !isStanding()) return Direction.FORWARD;
                if (!movesForward() && movesBackward() && !isStanding()) return Direction.BACKWARD;
                break;
        }
        return Direction.NONE;
    }

    public Direction getRotationDirection() {
        switch (movementControlStyle) {
            case BUTTONS:
            case TOUCH:
                if (rotatesRight() && !rotatesLeft() && !isStanding()) return Direction.RIGHT;
                if (!rotatesRight() && rotatesLeft() && !isStanding()) return Direction.LEFT;
                break;
            case JOYPAD:
                if (rotatesRight() && !rotatesLeft() && !isStanding()) return Direction.RIGHT;
                if (!rotatesRight() && rotatesLeft() && !isStanding()) return Direction.LEFT;
                break;
        }
        return Direction.NONE;
    }
}