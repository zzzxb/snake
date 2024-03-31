package xyz.zzzxb.snake.game;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * zzzxb
 * 2024/3/30
 */
public class Block {
    public final int width;
    public final int height;
    public final Texture texture;

    public Block(int width, int height, Color color) {
        this(width, height, color, true);
    }

    public Block(int width, int height, Color color, boolean fill) {
        this.width = width;
        this.height = height;
        Pixmap pixmap = new Pixmap(width, height, Pixmap.Format.RGBA8888);
        pixmap.setColor(color);
        if (fill) {
            pixmap.fillRectangle(0, 0, width, height);
        } else {
            pixmap.drawRectangle(0, 0, width, height);
        }
        texture = new Texture(pixmap);
    }

    public void draw(SpriteBatch batch, float x, float y) {
        batch.draw(texture, x, y);
    }

    public void draw(SpriteBatch batch, Position p) {
        batch.draw(texture, p.x, p.y);
    }

    public void dispose() {
        texture.dispose();
    }
}
