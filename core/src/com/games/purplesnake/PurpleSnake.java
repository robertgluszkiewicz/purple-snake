package com.games.purplesnake;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
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
	private String restartInstruction;
	private BitmapFont font;
	private int score;
	private String scoreName;

	@Override
	public void create() {
		batch = new SpriteBatch();
		snakeElementImage = new Texture("snake.png");
		foodImage = new Texture("food.png");
		snakeLogic = new SnakeLogic(snakeElementImage);
		food = new Food(foodImage);
		eat = Gdx.audio.newSound(Gdx.files.internal("eat.wav"));
		fail = Gdx.audio.newSound(Gdx.files.internal("fail.wav"));
		font = new BitmapFont();
		restartInstruction = "Game over! Press 'N' button to start new game.";

		startNewGame();
	}

	@Override
	public void render() {
		controlGameplay();

		ScreenUtils.clear(0, 0, 0, 1);
		batch.begin();
		food.draw(batch);
		snakeLogic.draw(batch);
		font.setColor(Color.YELLOW);
		if (gameOver) {
			font.draw(
					batch, restartInstruction,
					BoardDimension.BOARD_HEIGHT.getDimension() / 2 - 150,
					BoardDimension.BOARD_WIDTH.getDimension() / 2
			);
		}
		font.draw(batch, scoreName, 5, 15);
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
		score = 0;
		scoreName = "Score: 0";
	}

	private void controlGameplay() {
		if (!gameOver) {
			snakeLogic.controlGameSpeed(Gdx.graphics.getDeltaTime());

			if (snakeLogic.isAteFood(food.getPoint())) {
				snakeLogic.lengthenSnake();
				food.generateRandomPoint();
				eat.play();
				score++;
				scoreName = "Score: " + score;
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

