package xyz.zzzxb.snake;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

public class App extends ApplicationAdapter {
    SpriteBatch batch;

    @Override
    public void create() {
        batch = new SpriteBatch();
    }

    @Override
    public void render() {
        ScreenUtils.clear(0.81f, 0.87f, 1, 1);

        batch.begin();
        batch.end();
    }

    @Override
    public void dispose() {
        batch.dispose();
    }
}
