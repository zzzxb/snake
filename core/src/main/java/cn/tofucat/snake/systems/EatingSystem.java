package cn.tofucat.snake.systems;

import cn.tofucat.snake.conf.Config;
import cn.tofucat.snake.world.GameWorld;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.math.Vector2;

public class EatingSystem extends GameWorldSystem {
    public EatingSystem(GameWorld gameWorld) {
        super(gameWorld);
    }

    @Override
    public void update(float delta) {
        level();
        Vector2 sf = gameWorld.snake.points.first();
        for (Vector2 point : gameWorld.food.points) {
            if (sf.equals(point)) {
                Gdx.app.debug("eat", "Eating food!");
                gameWorld.assetManager.get("gold.mp3", Sound.class).play();
                gameWorld.snake.len += 1;
                gameWorld.food.points.removeValue(point, true);
                gameWorld.food.addFood(gameWorld.food.foodNum - gameWorld.food.points.size);
                return;
            }
        }
    }

    private void level() {
        if (gameWorld.level <= 1) {
            gameWorld.food.foodNum = Config.instance.DEFAULT_FOOD_NUM;
        } else if (gameWorld.level == 2) {
            gameWorld.food.foodNum = Math.min(gameWorld.snake.len - Config.instance.DEFAULT_SNAKE_LEN + Config.instance.DEFAULT_FOOD_NUM,
                Config.instance.DEFAULT_FOOD_MAX_NUM / 2);
        } else {
            gameWorld.food.foodNum = Math.min(gameWorld.snake.len - Config.instance.DEFAULT_SNAKE_LEN + Config.instance.DEFAULT_FOOD_NUM,
                Config.instance.DEFAULT_FOOD_MAX_NUM);
        }
    }
}
