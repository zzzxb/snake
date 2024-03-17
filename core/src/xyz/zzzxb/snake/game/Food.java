package xyz.zzzxb.snake.game;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;

public class Food {
    Square square;
    Square square1;
    Position position;

    public Food(Color color, int width, int height) {
        square = new Square(color, width, height, true);
        square1 = new Square(Color.BLACK, width, height, false);
    }

    public void randomPosition(Array<Position> exclude) {
        int x = MathUtils.random(1, 32) * square.getWidth();
        int y = MathUtils.random(1, 32) * square.getHeight();
        if (this.position != null) {
            this.position.setPosition(x, y);
        } else {
            this.position = new Position(x, y);
        }
        if (exclude.contains(this.position, false)) {
            randomPosition(exclude);
        }
    }

    public void draw(SpriteBatch batch) {
        if (position != null) {
            square.draw(batch, position);
            Position copy = position.copy();
            copy.setPosition(copy.getX() + 8, copy.getY() + 8);
            square1.draw(batch, copy);
        }
    }

    public void clearFood() {
        position = null;
    }

    public boolean crash(Position position) {
        return position.equals(this.position);
    }

    public void dispose() {
        square.dispose();
    }
}
