package com.pomavau.crimson.Controller;

import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.Vector2;
import com.pomavau.crimson.Model.Bot;
import com.pomavau.crimson.Model.LevelWorld;
import com.pomavau.crimson.Model.Player;
import com.pomavau.crimson.crimsonTD;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.logging.Level;

import static com.badlogic.gdx.Input.Keys.A;
import static com.badlogic.gdx.Input.Keys.CONTROL_LEFT;
import static com.badlogic.gdx.Input.Keys.CONTROL_RIGHT;
import static com.badlogic.gdx.Input.Keys.D;
import static com.badlogic.gdx.Input.Keys.DOWN;
import static com.badlogic.gdx.Input.Keys.LEFT;
import static com.badlogic.gdx.Input.Keys.RIGHT;
import static com.badlogic.gdx.Input.Keys.S;
import static com.badlogic.gdx.Input.Keys.SHIFT_LEFT;
import static com.badlogic.gdx.Input.Keys.SHIFT_RIGHT;
import static com.badlogic.gdx.Input.Keys.UP;
import static com.badlogic.gdx.Input.Keys.W;

/**
 * Created by Pomavau on 12.03.16.
 */
public class BotController { //implements InputProcessor
    private MovementControlStyle movementControlStyle;
    HashSet<Integer> pressedKeys;
    ArrayList<Character> printedCharacters;
    HashMap<Integer, Pointer> pressedPointers;
    Bot bot;
    Pointer pointer = new Pointer(0, 0, 0);
    public BotController(LevelWorld stage) {
        bot = stage.getBot();
        pressedKeys = new HashSet<Integer>();
        printedCharacters = new ArrayList<Character>();
        pressedPointers = new HashMap<Integer, Pointer>();
        movementControlStyle = crimsonTD.getInstance().getMovementControlStyle();
    }

    public void update(LevelWorld world) {
        bot.setMovementDirection(getMovementDirection());
        bot.setRotationDirection(getRotationDirection());
        bot.setSpeededUp(isSpeededUp());
        pointer.setPosition((int)world.getPlayer().getX(), (int)world.getPlayer().getY());
    }

    /*
    @Override
    public boolean keyDown(int keycode) {
        return pressedKeys.add(keycode);
    }

    @Override
    public boolean keyUp(int keycode) {
        return pressedKeys.remove(keycode);
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
*/
    public void following(){

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
                return pressedPointers.size() > 2;
    }

    public boolean isStanding() {

                return pressedPointers.size() == 0;
    }

    public boolean movesForward() {
                return pressedPointers.size() > 0;
    }

    public boolean movesBackward() {
        return false;
    }

    public boolean rotatesLeft() {

                if (pressedPointers.size() == 0) return false;
                Pointer tmp = pressedPointers.values().iterator().next();
                float destAngle = new Vector2(tmp.getX(), tmp.getY()).sub(bot.getX(), bot.getY()).angle();
                bot.setDestinationAngle(destAngle);
                float playerAngle = bot.getRotation();
                float delta = destAngle - playerAngle;
                if (delta > 0 && delta < 180 || delta < -180)
                    return true;

        return false;
    }

    public boolean rotatesRight() {

                if (pressedPointers.size() == 0) return false;
                Pointer tmp = pressedPointers.values().iterator().next();
                float destAngle = new Vector2(tmp.getX(), tmp.getY()).sub(bot.getX(), bot.getY()).angle();
                bot.setDestinationAngle(destAngle);
                float playerAngle = bot.getRotation();
                float delta = destAngle - playerAngle;
                if (delta < 0 && delta > -180 || delta > 180)
                    return true;

        return false;
    }

    public Direction getMovementDirection() {

                if (movesForward() && !movesBackward() && !isStanding()) return Direction.FORWARD;
                if (!movesForward() && movesBackward() && !isStanding()) return Direction.BACKWARD;

        return Direction.NONE;
    }

    public Direction getRotationDirection() {
                if (rotatesRight() && !rotatesLeft() && !isStanding()) return Direction.RIGHT;
                if (!rotatesRight() && rotatesLeft() && !isStanding()) return Direction.LEFT;

        return Direction.NONE;
    }
}
