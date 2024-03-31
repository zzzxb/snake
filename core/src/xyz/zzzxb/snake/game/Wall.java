package xyz.zzzxb.snake.game;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import xyz.zzzxb.snake.config.AppConfig;

/**
 * zzzxb
 * 2024/3/30
 */
public class Wall {
    public final Block block;
    public final Position position;


    public Wall(int width, int height, Color color, boolean fill) {
        this(0, 0, width, height, color, fill);
    }

    public Wall(int x, int y, int width, int height, Color color, boolean fill) {
        this.position = new Position(x, y);
        this.block = new Block(width, height, color, fill);
    }

    public Wall(Block block, Position position) {
        this.block = block;
        this.position = position;
    }

    public void draw(SpriteBatch batch) {
        block.draw(batch, (this.position.x * AppConfig.BLOCK_SIZE) + AppConfig.MARGIN_X, (this.position.y * AppConfig.BLOCK_SIZE) + AppConfig.MARGIN_Y);
    }

    public boolean contain(Position p) {
        return p.x >= this.position.x && p.y >= this.position.y && p.x < AppConfig.BLOCK_NUM && p.y < AppConfig.BLOCK_NUM;
    }

    public void dispose() {
        block.dispose();
    }
}
