package cn.tofucat.snake.world;

import cn.tofucat.snake.SnakeGame;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

public class GameWorld  extends ScreenAdapter {
    private SnakeGame game;
    private SpriteBatch batch;

    public GameWorld(SnakeGame game) {
        this.game = game;
    }

    @Override
    public void show() {
        batch = new SpriteBatch();
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0.18f, 0.20f, 0.25f, 1f);

        batch.begin();
        batch.end();
    }

    @Override
    public void hide() {
    }
}
