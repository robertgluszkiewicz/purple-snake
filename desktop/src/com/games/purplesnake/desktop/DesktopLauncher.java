package com.games.purplesnake.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.games.purplesnake.PurpleSnake;
import static com.games.purplesnake.BoardDimension.BOARD_HEIGHT;
import static com.games.purplesnake.BoardDimension.BOARD_WIDTH;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();

		config.resizable = false;
		config.width = BOARD_WIDTH.getDimension();
		config.height = BOARD_HEIGHT.getDimension();
		config.title = "Aspire to be the longest Purple Snake ever!";

		new LwjglApplication(new PurpleSnake(), config);
	}
}
