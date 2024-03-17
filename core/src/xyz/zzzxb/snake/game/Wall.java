package xyz.zzzxb.snake.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Wall {
    Square square;
    int width;
    int height;
    float x;
    float y;

    public Wall(Color color, int width, int height, float x, float y) {
        this(color, width, height, x, y, false);
    }

    public Wall(Color color, int width, int height, float x, float y, boolean fill) {
        this.width = width;
        this.height = height;
        this.x = x;
        this.y = y;
        square = new Square(color, width, height, fill);
    }

    public void draw(SpriteBatch batch) {
        this.draw(batch, this.x, this.y);
    }

    public void draw(SpriteBatch batch, float x, float y) {
        square.draw(batch, x, y);
    }

    public boolean crash(Position position) {
        return position.getX() <= 0 || position.getX() >= this.x + this.width
                || position.getY() <= 0 || position.getY() >= this.y + this.height;
    }

    public void dispose() {
        square.dispose();
    }
}
