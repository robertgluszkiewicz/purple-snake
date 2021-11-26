package com.games.purplesnake;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

public class PurpleSnake extends ApplicationAdapter {
	private SpriteBatch batch;
	private Texture snakeElementImage;
	private Texture foodImage;
	private Snake snake;
	private Food food;
	private boolean gameOver;

	@Override
	public void create() {
		batch = new SpriteBatch();
		snakeElementImage = new Texture("snake.png");
		foodImage = new Texture("food.png");
		snake = new Snake(snakeElementImage);
		food = new Food(foodImage);
		startNewGame();
	}

	private void startNewGame() {
		snake.startGame();
		food.generateRandomPoint();
		gameOver = false;
	}

	@Override
	public void render() {
		controlGameplay();

		ScreenUtils.clear(0, 0, 0, 1);
		batch.begin();
		food.draw(batch);
		snake.draw(batch);
		batch.end();
	}

	private void controlGameplay() {
		if (!gameOver) {
			snake.controlGameSpeed(Gdx.graphics.getDeltaTime());

			if (snake.isAteFood(food.getPoint())) {
				snake.lengthenSnake();
				food.generateRandomPoint();
			}

			if (snake.isAteItself()) {
				gameOver = true;
			}
		}
	}

	@Override
	public void dispose() {
		batch.dispose();
		snakeElementImage.dispose();
		foodImage.dispose();
	}
}

