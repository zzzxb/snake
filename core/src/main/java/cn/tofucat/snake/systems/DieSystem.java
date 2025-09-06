package cn.tofucat.snake.systems;

import cn.tofucat.snake.world.GameWorld;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

public class DieSystem extends GameWorldSystem {
    public DieSystem(GameWorld gameWorld) {
        super(gameWorld);
    }

    @Override
    public void update(float delta) {
        crash();
        suicide();
    }

    private void crash() {
        if (gameWorld.gameState == GameWorld.GameState.GAME_OVER) return;

        Array<Vector2> wp = gameWorld.wall.points;
        if (wp.contains(gameWorld.snake.points.first(), false)) {
            Gdx.app.log("die", "Collide with the wall!");
            gameWorld.gameState = GameWorld.GameState.GAME_OVER;
        }
    }

    private void suicide() {
        if (gameWorld.gameState == GameWorld.GameState.GAME_OVER) return;

        for (int i = 1; i < gameWorld.snake.points.size; i++) {
            if (gameWorld.snake.points.get(i).equals(gameWorld.snake.points.first())) {
                Gdx.app.log("die", "commit suicide!");
                gameWorld.gameState = GameWorld.GameState.GAME_OVER;
                return;
            }
        }
    }
}
