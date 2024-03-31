package xyz.zzzxb.snake;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import xyz.zzzxb.snake.config.AppConfig;

// Please note that on macOS your application needs to be started with the -XstartOnFirstThread JVM argument
public class DesktopLauncher {
	public static void main (String[] arg) {
		Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
		config.setForegroundFPS(AppConfig.FPS);
		config.setTitle(AppConfig.TITLE);
		config.setWindowedMode(AppConfig.WINDOW_WIDTH, AppConfig.WINDOW_HEIGHT);
		config.useVsync(true);
		config.setResizable(false);
		config.setWindowIcon("icon16.png", "icon32.png", "icon48.png", "icon64.png");
		new Lwjgl3Application(new App(), config);
	}
}
