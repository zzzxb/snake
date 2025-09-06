package cn.tofucat.snake.world;

import cn.tofucat.snake.SnakeGame;
import cn.tofucat.snake.conf.Config;
import cn.tofucat.snake.entity.Floor;
import cn.tofucat.snake.entity.Food;
import cn.tofucat.snake.entity.Snake;
import cn.tofucat.snake.entity.Wall;
import cn.tofucat.snake.systems.CtrlSystem;
import cn.tofucat.snake.systems.DieSystem;
import cn.tofucat.snake.systems.EatingSystem;
import cn.tofucat.snake.systems.MovementSystem;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.ScreenUtils;

public class GameWorld extends ScreenAdapter {
    SnakeGame game;
    AssetManager assetManager;
    CtrlSystem ctrlSystem;
    MovementSystem movementSystem;
    EatingSystem eatingSystem;
    DieSystem dieSystem;

    public Floor floor;
    public Wall wall;
    public Food food;
    public Snake snake;
    public GameState gameState;
    public int level;

    public enum GameState {
        START, STOP, GAME_OVER
    }

    public GameWorld(SnakeGame game) {
        this.game = game;
    }

    @Override
    public void show() {
        assetManager = new AssetManager();
        assetManager.load("brick.png", Texture.class);
        assetManager.finishLoading();
        level = Config.instance.LEVEL;
        gameState = GameState.STOP;

        TextureRegion[][] split = new TextureRegion(assetManager.get("brick.png", Texture.class))
            .split(8, 8);
        floor = new Floor(split[0][0]);
        wall = new Wall(split[0][1]);
        snake = new Snake(split[0][2]);
        food = new Food(split[0][3]);
        ctrlSystem = new CtrlSystem(this);
        movementSystem = new MovementSystem(this);
        eatingSystem = new EatingSystem(this);
        dieSystem = new DieSystem(this);
    }

    @Override
    public void render(float delta) {
        ctrlSystem.update(delta);
        if (gameState == GameState.START) {
            movementSystem.update(delta);
            eatingSystem.update(delta);
            dieSystem.update(delta);
        }

        ScreenUtils.clear(Color.BLACK);
        game.viewport.apply();
        game.batch.setProjectionMatrix(game.viewport.getCamera().combined);

        game.batch.begin();
        floor.draw(game.batch);
        wall.draw(game.batch);
        food.draw(game.batch);
        snake.draw(game.batch);
        game.batch.end();
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
