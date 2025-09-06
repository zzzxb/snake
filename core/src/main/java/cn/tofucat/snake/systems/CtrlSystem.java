package cn.tofucat.snake.systems;

import cn.tofucat.snake.entity.Snake;
import cn.tofucat.snake.world.GameWorld;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;

public class CtrlSystem extends GameWorldSystem {

    public CtrlSystem(GameWorld gameWorld) {
        super(gameWorld);
    }

    @Override
    public void update(float delta) {
        if (Gdx.input.isKeyJustPressed(Input.Keys.R)) {
            Gdx.app.log("ctrl", "reset game");
            gameWorld.gameState = GameWorld.GameState.STOP;
            gameWorld.food.init();
            gameWorld.snake.init();
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.P)) {
            gameWorld.gameState = gameWorld.gameState == GameWorld.GameState.STOP ? GameWorld.GameState.START : GameWorld.GameState.STOP;
            Gdx.app.log("ctrl", gameWorld.gameState + " game");
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_1)) {
            gameWorld.level = 1;
            Gdx.app.log("ctrl", "reset game level: " + gameWorld.level);
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_2)) {
            gameWorld.level = 2;
            Gdx.app.log("ctrl", "reset game level: " + gameWorld.level);
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_3)) {
            gameWorld.level = 3;
            Gdx.app.log("ctrl", "reset game level: " + gameWorld.level);
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.Q)) {
            Gdx.app.log("ctrl", "exit game");
            Gdx.app.exit();
        }

        if (gameWorld.gameState == GameWorld.GameState.START) {
            moveSnake(gameWorld.snake);
        }
    }

    private void moveSnake(Snake snake) {
        if (snake.direction != Snake.Direction.DOWN && Gdx.input.isKeyJustPressed(Input.Keys.K)) {
            addDirection(Snake.Direction.UP);
        }
        if (snake.direction != Snake.Direction.UP && Gdx.input.isKeyJustPressed(Input.Keys.J)) {
            addDirection(Snake.Direction.DOWN);
        }
        if (snake.direction != Snake.Direction.RIGHT && Gdx.input.isKeyJustPressed(Input.Keys.H)) {
            addDirection(Snake.Direction.LEFT);
        }
        if (snake.direction != Snake.Direction.LEFT && Gdx.input.isKeyJustPressed(Input.Keys.L)) {
            addDirection(Snake.Direction.RIGHT);
        }
    }

    public void addDirection(Snake.Direction direction) {
        gameWorld.gameState = GameWorld.GameState.START;
        if (gameWorld.snake.directions.size < 2) {
            Gdx.app.log("ctrl", "move snake -> " + direction);
            gameWorld.snake.directions.add(direction);
        } else {
            Gdx.app.log("ctrl", "direction queue is full.");
        }
    }
}
