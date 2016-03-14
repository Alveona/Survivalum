package com.pomavau.crimson.Controller;

import com.pomavau.crimson.crimsonTD;
import com.pomavau.crimson.Model.LevelWorld;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Rectangle;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import static com.badlogic.gdx.Input.Keys.CONTROL_RIGHT;
import static com.badlogic.gdx.Input.Keys.DOWN;
import static com.badlogic.gdx.Input.Keys.LEFT;
import static com.badlogic.gdx.Input.Keys.RIGHT;
import static com.badlogic.gdx.Input.Keys.SHIFT_RIGHT;
import static com.badlogic.gdx.Input.Keys.UP;

/**
 * Created by Гриша on 23.02.2016.
 */
public class CameraController implements InputProcessor {
    private MovementControlStyle movementControlStyle;
    private float step;
    private float zoomAmount;
    private Rectangle cameraBorders;
    OrthographicCamera camera;
    HashSet<Integer> pressedKeys;
    ArrayList<Character> printedCharacters;
    HashSet<Integer> usedButtons;
    HashMap<Integer, Pointer> pressedPointers;


    public CameraController(LevelWorld world) {
        this.step = 3;
        this.zoomAmount = 0.2f; //0.2 default
        this.camera = (OrthographicCamera)world.getCamera();
        cameraBorders = new Rectangle(camera.viewportWidth / 2, camera.viewportHeight / 2, world.getWidth() - camera.viewportWidth, world.getHeight() - camera.viewportHeight);
        pressedKeys = new HashSet<Integer>();
        printedCharacters = new ArrayList<Character>();
        pressedPointers = new HashMap<Integer, Pointer>();
        movementControlStyle = crimsonTD.getInstance().getMovementControlStyle();
        usedButtons = new HashSet<Integer>();
        usedButtons.add(UP);
        usedButtons.add(DOWN);
        usedButtons.add(LEFT);
        usedButtons.add(RIGHT);
        usedButtons.add(SHIFT_RIGHT);
        usedButtons.add(CONTROL_RIGHT);
    }

    public void update(LevelWorld world) {
        //System.out.format("%b, %b, %b, %b\n", movesDown(), movesLeft(), movesRight(), movesUp());
        camera.translate(
                movesRight() && camera.position.x + step < cameraBorders.getX() + cameraBorders.getWidth() ? step : 0,
                movesUp() && camera.position.y + step < cameraBorders.getY() + cameraBorders.getHeight() ? step : 0,
                0);
        camera.translate(
                movesLeft() && camera.position.x - step > cameraBorders.getX() ? -step : 0,
                movesDown() && camera.position.y - step > cameraBorders.getY() ? -step : 0,
                0);
        camera.zoom += zoomIn() ? zoomAmount : 0;
        camera.zoom -= zoomOut() && camera.zoom > zoomAmount ? zoomAmount : 0;

        //if ((camera.position.x + step > cameraBorders.getX() + cameraBorders.getWidth()) && (camera.position.y + step > cameraBorders.getY() + cameraBorders.getHeight()))
        camera.position.set(world.getPlayer().getX(), world.getPlayer().getY(), 0);

        camera.update();
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

    public boolean movesRight() {
        switch (movementControlStyle) {
            case BUTTONS:
                return pressedKeys.contains(RIGHT);
        }
        return false;
    }

    public boolean movesLeft() {
        switch (movementControlStyle) {
            case BUTTONS:
                return pressedKeys.contains(LEFT);
        }
        return false;
    }

    public boolean movesUp() {
        switch (movementControlStyle) {
            case BUTTONS:
                return pressedKeys.contains(UP);
        }
        return false;
    }

    public boolean movesDown() {
        switch (movementControlStyle) {
            case BUTTONS:
                return pressedKeys.contains(DOWN);
        }
        return false;
    }

    public boolean zoomIn() {
        switch (movementControlStyle) {
            case BUTTONS:
                return pressedKeys.contains(SHIFT_RIGHT);
        }
        return false;
    }

    public boolean zoomOut() {
        switch (movementControlStyle) {
            case BUTTONS:
                return pressedKeys.contains(CONTROL_RIGHT);
        }
        return false;
    }
}
