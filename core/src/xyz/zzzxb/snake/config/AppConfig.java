package xyz.zzzxb.snake.config;

public class AppConfig {
    public final static String TITLE = "snake";
    public final static int FPS = 60;
    public final static int BLOCK_NUM = 32;
    public final static int BLOCK_SIZE = 16;
    public final static int WINDOW_WIDTH = 512 + (BLOCK_SIZE * 2);
    public final static int WINDOW_HEIGHT = 512 + (BLOCK_SIZE * 2);
    public final static int MARGIN_X = (int) ((WINDOW_WIDTH - BLOCK_NUM * BLOCK_SIZE) / 2);
    public final static int MARGIN_Y = (int) ((WINDOW_HEIGHT - BLOCK_NUM * BLOCK_SIZE) / 2);
    public final static float DEFAULT_SPEED = 0.05f;
}
