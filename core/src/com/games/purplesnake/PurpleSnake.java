package com.games.purplesnake;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

public class PurpleSnake extends ApplicationAdapter {
	private SpriteBatch batch;
	private Texture snakeElementImage;
	private Texture foodImage;
	private SnakeLogic snakeLogic;
	private Food food;
	private boolean gameOver;
	private Sound eat;
	private Sound fail;

	@Override
	public void create() {
		batch = new SpriteBatch();
		snakeElementImage = new Texture("snake.png");
		foodImage = new Texture("food.png");
		snakeLogic = new SnakeLogic(snakeElementImage);
		food = new Food(foodImage);
		eat = Gdx.audio.newSound(Gdx.files.internal("eat.wav"));
		fail = Gdx.audio.newSound(Gdx.files.internal("fail.wav"));

		startNewGame();
	}

	@Override
	public void render() {
		controlGameplay();

		ScreenUtils.clear(0, 0, 0, 1);
		batch.begin();
		food.draw(batch);
		snakeLogic.draw(batch);
		batch.end();
	}

	@Override
	public void dispose() {
		batch.dispose();
		snakeElementImage.dispose();
		foodImage.dispose();
		eat.dispose();
		fail.dispose();
	}

	private void startNewGame() {
		snakeLogic.startGame();
		food.generateRandomPoint();
		gameOver = false;
	}

	private void controlGameplay() {
		if (!gameOver) {
			snakeLogic.controlGameSpeed(Gdx.graphics.getDeltaTime());

			if (snakeLogic.isAteFood(food.getPoint())) {
				snakeLogic.lengthenSnake();
				food.generateRandomPoint();
				eat.play();
			}

			if (snakeLogic.isAteItself()) {
				gameOver = true;
				fail.play();
			}

		} else {
			if (Gdx.input.isKeyJustPressed(Input.Keys.N)) {
				startNewGame();
			}
		}
	}
}

