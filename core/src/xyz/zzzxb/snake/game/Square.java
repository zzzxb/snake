package xyz.zzzxb.snake.game;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Square {
    private final Texture texture;
    private final int width;
    private final int height;


    public Square(Color color, int width, int height) {
        this(color, width, height, false);
    }

    public Square(Color color, int width, int height, boolean fill) {
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

    public void draw(SpriteBatch sprite, float x, float y) {
        draw(sprite, new Position(x, y));
    }

    public void draw(SpriteBatch sprite, Position position) {
        sprite.draw(texture, position.getX(), position.getY());
    }

    public void draw0(SpriteBatch sprite, Position position) {
        sprite.draw(texture, position.getX() + 8, position.getY()+8);
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public void dispose() {
        texture.dispose();
    }
}
