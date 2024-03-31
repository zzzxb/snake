package xyz.zzzxb.snake;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import xyz.zzzxb.snake.game.GameWorld;

public class App extends ApplicationAdapter {

    SpriteBatch batch;
    GameWorld gameWorld;

    @Override
    public void create() {
        batch = new SpriteBatch();
        gameWorld = new GameWorld(batch);
    }

    @Override
    public void render() {
        ScreenUtils.clear(0.81f, 0.87f, 1, 1);
        gameWorld.ctrl();

        batch.begin();
        gameWorld.render();
        batch.end();
    }

    @Override
    public void dispose() {
        gameWorld.dispose();
    }
}
