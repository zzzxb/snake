package xyz.zzzxb.snake.config;

import com.badlogic.gdx.Application;

/**
 * zzzxb
 * 2024/7/31
 */
public class Config {
    public final static String TITLE = "snake";
    public final static int SNAKE_SIZE = 10;
    public final static int BLOCK_LINE = 32;
    public final static int WALL_SIZE = SNAKE_SIZE * BLOCK_LINE;
    public final static int CENTER = WALL_SIZE / 2;
    public final static int[] WINDOW_SIZE = new int[]{WALL_SIZE + SNAKE_SIZE * 2, WALL_SIZE + SNAKE_SIZE * 2};
    public final static int LOGGER_LEVEL = Application.LOG_DEBUG;

}
