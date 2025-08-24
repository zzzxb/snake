package cn.tofucat.snake.conf;

public class Config {
    public static final Config instance = new Config();

    public final String TITLE = "snake";
    public final int WINDOW_WIDTH = 640;
    public final int WINDOW_HEIGHT = 480;
    public final String[] WINDOW_ICON = {"logo16", "logo32", "logo64", "logo128"};
}
