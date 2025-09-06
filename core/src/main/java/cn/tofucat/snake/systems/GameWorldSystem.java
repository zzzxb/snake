package cn.tofucat.snake.systems;

import cn.tofucat.snake.world.GameWorld;

public abstract class GameWorldSystem {
    protected GameWorld gameWorld;

    public GameWorldSystem(GameWorld gameWorld) {
        this.gameWorld = gameWorld;
    }

    public abstract void update(float delta);
}
