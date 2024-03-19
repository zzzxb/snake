package xyz.zzzxb.snake.algorithm;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import xyz.zzzxb.snake.enums.Direction;
import xyz.zzzxb.snake.game.*;

import java.util.Arrays;

/**
 * zzzxb
 * 2024/3/19
 */
public class CustomMove extends AbstractMoveAlgo {
    public final static CustomMove INSTANCE = new CustomMove();
    private final Square square;
    private final Array<Position> algoPath = new Array<>();
    private final Array<Direction> algoDir = new Array<>();

    public CustomMove() {
        square = new Square(new Color(0, 0, 0, 0.1f), 16, 16, true);
    }

    @Override
    public Direction move(Wall wall, Snake snake, Food food) {
        generationPath(wall, snake, food);
        return popDirection();
    }

    private void pathAlgo(float sx , float sy, float fx, float fy, float step, Wall wall, Snake snake, Food food) {
        float x = sx , y = sy;
        Direction direction = null;

        do {
            Direction lastDir = algoDir.isEmpty() ? null : algoDir.peek();
            if (sx > fx &&  lastDir != Direction.RIGHT) {
                x -= step;
                direction = Direction.LEFT;
            } else if (sx < fx && lastDir != Direction.LEFT) {
                x += step;
                direction = Direction.RIGHT;
            } else if (sy > fy && lastDir != Direction.UP) {
                y -= step;
                direction = Direction.DOWN;
            } else if (sy < fy && lastDir != Direction.DOWN) {
                y += step;
                direction = Direction.UP;
            }
        }while (snake.getPositions().contains(new Position(x, y), false));

        if (direction != null) {
            algoPath.add(new Position(x, y));
            algoDir.add(direction);
        }
        if((sx != fx || sy != fy)) {
            pathAlgo(x, y, fx, fy, step, wall, snake, food);
        }
    }

    private void generationPath(Wall wall, Snake snake, Food food) {
        if (!algoPath.isEmpty() && !algoDir.isEmpty()) {
            return;
        }
        Position head = snake.getPositions().first();
        Position fp = food.getPosition();
        pathAlgo(head.getX(), head.getY(), fp.getX(), fp.getY(), wall.getX(), wall, snake, food);
    }

    private Direction popDirection() {
        Direction first = algoDir.removeIndex(0);
        algoPath.removeIndex(0);
        return first;
    }

    @Override
    public void reset() {
        algoDir.clear();
        algoPath.clear();
    }

    @Override
    public void drawAuxiliaryLine(SpriteBatch batch) {
        if (!algoPath.isEmpty() && algoPath.size != 0) {
            for (Position p : algoPath) {
                square.draw(batch, p);
            }
        }
    }

    @Override
    public void dispose() {
        square.dispose();
    }
}
