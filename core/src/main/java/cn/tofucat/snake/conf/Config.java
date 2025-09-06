package cn.tofucat.snake.conf;

public class Config {
    public static final Config instance = new Config();

    public final String TITLE = "snake";
    public final int BRICK_SIZE = 16;
    public final int BRICK_NUM = 32;
    public final int WINDOW_WIDTH = BRICK_NUM * BRICK_SIZE;
    public final int WINDOW_HEIGHT = BRICK_NUM * BRICK_SIZE;
    public final String[] WINDOW_ICON = {"logo16", "logo32", "logo64", "logo128"};

}
