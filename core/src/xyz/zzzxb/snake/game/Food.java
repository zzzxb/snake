package xyz.zzzxb.snake.game;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import xyz.zzzxb.snake.config.AppConfig;

/**
 * zzzxb
 * 2024/3/30
 */
public class Food {
    public final Block block;
    private Position position;
    private final Array<Position> blockPositions;

    public Food(int width, int height, Color color) {
        this.block = new Block(width, height, color);
        this.blockPositions = new Array<>();
        init();
    }

    public void init() {
        for (int i = 0; i < AppConfig.BLOCK_NUM; i++) {
            for (int j = 0; j < AppConfig.BLOCK_NUM; j++) {
                blockPositions.add(new Position(i, j));
            }
        }
    }

    public boolean crash(Position p) {
        return this.position.equals(p);
    }

    public void randomGenFood(Array<Position> exclude) {
        Array<Position> bp = new Array<>(blockPositions);
        if (exclude != null && !exclude.isEmpty()) {
            bp.removeAll(exclude, false);
        }
        position = bp.get(MathUtils.random(0, bp.size - 1));
    }

    public void draw(SpriteBatch batch) {
        if (this.position != null)
            block.draw(batch, (this.position.x * AppConfig.BLOCK_SIZE) + AppConfig.MARGIN_X, (this.position.y * AppConfig.BLOCK_SIZE) + AppConfig.MARGIN_Y);
    }

    public Position getPosition() {
        return position;
    }

    public void dispose() {
        block.dispose();
    }
}
