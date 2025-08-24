package cn.tofucat.snake;

import cn.tofucat.snake.conf.Config;
import cn.tofucat.snake.world.LogoReveal;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.FitViewport;

public class SnakeGame extends Game {
    public SpriteBatch batch;
    public FitViewport viewport;

    @Override
    public void create() {
        batch = new SpriteBatch();
        viewport = new FitViewport(Config.instance.WINDOW_WIDTH,Config.instance.WINDOW_HEIGHT);
        setScreen(new LogoReveal(this));
    }

    @Override
    public void dispose() {
        batch.dispose();
    }
}
