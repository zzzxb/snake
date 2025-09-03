package cn.tofucat.snake.world;

import cn.tofucat.snake.SnakeGame;
import cn.tofucat.snake.conf.Config;
import cn.tofucat.snake.entities.Wall;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.ScreenUtils;

public class GameWorld  extends ScreenAdapter {
    SnakeGame game;
    AssetManager assetManager;
    Wall wall;


    public GameWorld(SnakeGame game) {
        this.game = game;
    }

    @Override
    public void show() {
        assetManager = new AssetManager();
        assetManager.load("brick.png", Texture.class);
        assetManager.finishLoading();

        TextureRegion[][] split = new TextureRegion(assetManager.get("brick.png", Texture.class))
            .split(8, 8);
        wall = new Wall(split[0][0]);
    }

    @Override
    public void render(float delta) {
        ctrl();

        ScreenUtils.clear(Color.BLACK);
        game.viewport.apply();
        game.batch.setProjectionMatrix(game.viewport.getCamera().combined);

        game.batch.begin();
        wall.draw(game.batch);
        game.batch.end();
    }

    private void ctrl() {
        if(Gdx.input.isKeyJustPressed(Input.Keys.R)) {
            Gdx.app.log("ctrl", "reset game");
        }
        if(Gdx.input.isKeyJustPressed(Input.Keys.Q)) {
            Gdx.app.log("ctrl", "exit game");
            Gdx.app.exit();
        }
    }

    @Override
    public void resize(int width, int height) {
        game.viewport.update(width, height, true);
    }

    @Override
    public void dispose() {
        assetManager.dispose();
    }
}
