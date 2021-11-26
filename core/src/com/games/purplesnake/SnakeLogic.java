package com.games.purplesnake;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.GridPoint2;

import java.util.LinkedList;
import java.util.List;

import static com.games.purplesnake.BoardDimension.BOARD_HEIGHT;
import static com.games.purplesnake.BoardDimension.BOARD_WIDTH;

public class SnakeLogic {
    private final Texture texture;
    private final List<GridPoint2> snakeElements;
    private ControlDirection direction;
    private float timeSinceLastMove;

    public SnakeLogic(Texture texture) {
        this.texture = texture;
        snakeElements = new LinkedList<>();
    }

    public void startGame() {
        timeSinceLastMove = 0;
        direction = ControlDirection.UP;

        snakeElements.clear();
        snakeElements.add(new GridPoint2(BOARD_HEIGHT.getDimension() / 2 - 20, BOARD_WIDTH.getDimension() / 2));
        snakeElements.add(new GridPoint2(BOARD_HEIGHT.getDimension() / 2, BOARD_WIDTH.getDimension() / 2));
        snakeElements.add(new GridPoint2(BOARD_HEIGHT.getDimension() / 2 + 20, BOARD_WIDTH.getDimension() / 2));
    }

    public void draw(Batch batch) {
        for (GridPoint2 point : snakeElements) {
            batch.draw(texture, point.x, point.y);
        }
    }

    public void controlGameSpeed(float deltaTime) {
        controlSnakeDirection();
        timeSinceLastMove += deltaTime;

        if (timeSinceLastMove >= 0.1f) {
            timeSinceLastMove = 0;
            moveSnake();
        }
    }

    public boolean isAteFood(GridPoint2 foodPoint) {
        return getPointOfFirstElementOfSnake().equals(foodPoint);
    }

    public void lengthenSnake() {
        snakeElements.add(new GridPoint2(snakeElements.get(snakeElements.size() - 1)));
    }

    public boolean isAteItself() {
        for (int i = 1; i < snakeElements.size(); i++) {
            if (snakeElements.get(i).equals(getPointOfFirstElementOfSnake())) {
                return true;
            }
        }
        return false;
    }

    private void controlSnakeDirection() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.LEFT) &&
                direction != ControlDirection.RIGHT) {
            direction = ControlDirection.LEFT;
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.RIGHT) &&
                direction != ControlDirection.LEFT) {
            direction = ControlDirection.RIGHT;
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.UP) &&
                direction != ControlDirection.DOWN) {
            direction = ControlDirection.UP;
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.DOWN) &&
                direction != ControlDirection.UP) {
            direction = ControlDirection.DOWN;
        }
    }

    private void moveSnake() {
        for (int i = snakeElements.size() - 1; i > 0; i--) {
            snakeElements.get(i).set(snakeElements.get(i - 1));
        }

        int elementWidth = texture.getWidth();
        int elementHeight = texture.getHeight();
        int lastBoardElementX = Gdx.graphics.getWidth() - elementWidth;
        int lastBoardElementY = Gdx.graphics.getHeight() - elementHeight;

        GridPoint2 firstElementOfSnake = getPointOfFirstElementOfSnake();

        switch (direction) {
            case UP:
                firstElementOfSnake.y =
                        (firstElementOfSnake.y == lastBoardElementY) ? 0 : firstElementOfSnake.y + elementHeight;
                break;
            case DOWN:
                firstElementOfSnake.y =
                        (firstElementOfSnake.y == 0) ? lastBoardElementY : firstElementOfSnake.y - elementHeight;
                break;
            case LEFT:
                firstElementOfSnake.x =
                        (firstElementOfSnake.x == 0) ? lastBoardElementX : firstElementOfSnake.x - elementWidth;
                break;
            case RIGHT:
                firstElementOfSnake.x =
                        (firstElementOfSnake.x == lastBoardElementX) ? 0 : firstElementOfSnake.x + elementWidth;
                break;
        }
    }

    private GridPoint2 getPointOfFirstElementOfSnake() {
        return snakeElements.get(0);
    }
}
