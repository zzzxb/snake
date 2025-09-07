package cn.tofucat.snake.systems;

import cn.tofucat.snake.conf.Config;
import cn.tofucat.snake.entity.Snake;
import cn.tofucat.snake.world.GameWorld;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;

public class MovementSystem extends GameWorldSystem {
    private float time = 0;

    public MovementSystem(GameWorld gameWorld) {
        super(gameWorld);
    }

    @Override
    public void update(float delta) {
        level();
        time += delta;
        if (time >= gameWorld.snake.speed) {
            time = 0;
            moveSnake();
            removeSpoor();
            switchDirection();
        }
    }

    public void level() {
        if (gameWorld.level <= 1) {
            gameWorld.snake.speed = Config.instance.DEFAULT_SNAKE_SPEED;
        } else if (gameWorld.level == 2) {
            int dss = Config.instance.DEFAULT_SNAKE_SPEED - 3;
            gameWorld.snake.speed = dss - (Math.min(0, gameWorld.snake.len - Config.instance.DEFAULT_SNAKE_LEN - Config.instance.BRICK_NUM)) *
                dss / (float) (Config.instance.BRICK_NUM * Config.instance.BRICK_NUM);
        } else {
            gameWorld.snake.speed = 3;
        }
        gameWorld.snake.speed /= 100;
    }

    public void switchDirection() {
        if (gameWorld.snake.directions.size > 1) {
            gameWorld.snake.directions.removeValue(gameWorld.snake.direction, true);
            Gdx.app.log("snake", "switchDirection - remove old direction: " + gameWorld.snake.direction);
            gameWorld.snake.direction = gameWorld.snake.directions.first();
            Gdx.app.log("snake", "switchDirection - use new direction: " + gameWorld.snake.direction);
        }
    }

    private void moveSnake() {
        Snake snake = gameWorld.snake;
        Vector2 first = snake.points.first();
        Vector2 nextStep = first.cpy();
        switch (snake.direction) {
            case UP:
                nextStep.y += Config.instance.BRICK_SIZE;
                break;
            case DOWN:
                nextStep.y -= Config.instance.BRICK_SIZE;
                break;
            case LEFT:
                nextStep.x -= Config.instance.BRICK_SIZE;
                break;
            case RIGHT:
                nextStep.x += Config.instance.BRICK_SIZE;
                break;
        }
        snake.points.insert(0, nextStep);
    }

    private void removeSpoor() {
        Snake snake = gameWorld.snake;
        if (snake.points.size == 0) return;
        if (snake.points.size > snake.len + 2) {
            snake.points.removeIndex(snake.points.size - 1);
        }
    }
}
