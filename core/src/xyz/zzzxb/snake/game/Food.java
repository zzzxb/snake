package xyz.zzzxb.snake.game;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;

public class Food {
    Square square;
    Square square1;
    Position position;
    Array<Position> positions;

    public Food(Color color, int width, int height) {
        square = new Square(color, width, height, true);
        square1 = new Square(Color.BLACK, width, height, false);
        positions = new Array<>();
    }

    public void randomPosition(Array<Position> exclude) {
        if ((positions.size / 2) < exclude.size) {
            Array<Position> gameBlock = buildBlock();
            gameBlock.removeAll(exclude, false);
            int i = MathUtils.random(0, gameBlock.size);
            this.position = gameBlock.get(i).copy();
        } else {
            do {
                int x = MathUtils.random(1, 32) * square.getWidth();
                int y = MathUtils.random(1, 32) * square.getHeight();
                if (this.position != null) {
                    this.position.setPosition(x, y);
                } else {
                    this.position = new Position(x, y);
                }
            } while (exclude.contains(this.position, false));
        }
    }

    public void draw(SpriteBatch batch) {
        if (position != null) {
            square.draw(batch, position);
            Position copy = position.copy();
            copy.setPosition(copy.getX() + 4, copy.getY() + 4);
            square1.draw(batch, copy);
        }
    }

    public Array<Position> buildBlock() {
        if (positions.isEmpty()) {
            for (int i = 1; i <= 32; i++) {
                for (int j = 1; j <= 32; j++) {
                    positions.add(new Position(i * 16, j * 16));
                }
            }
        }
        return new Array<>(positions);
    }

    public Position getPosition() {
        return position;
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
