package xyz.zzzxb.snake.algorithm;

import xyz.zzzxb.snake.enums.Direction;
import xyz.zzzxb.snake.game.Food;
import xyz.zzzxb.snake.game.Position;
import xyz.zzzxb.snake.game.Snake;
import xyz.zzzxb.snake.game.Wall;

/**
 * zzzxb
 * 2024/3/18
 */
public class LoopMove implements MoveAlgo {
    public final static LoopMove INSTANCE = new LoopMove();
    boolean once;

    @Override
    public Direction move(Wall wall, Snake snake, Food food) {
        Position head = snake.getHead();
        Direction direction = snake.getDirection();
        if (!once) {
            once = true;
            return (snake.getHead().getY() / wall.getY()) % 2 == 0 ? Direction.RIGHT : Direction.LEFT;
        }

        // 左下角、右下角 行为
        if ((head.getX() <= wall.getX() && direction == Direction.LEFT) ||
                (head.getX() >= wall.getWidth() && head.getY() <= wall.getY() && direction == Direction.RIGHT)) {
            return Direction.UP;
        }

        if (head.getX() >= wall.getWidth() && head.getY() >= wall.getHeight() && direction == Direction.UP) {
            // 右上角行为
            return Direction.LEFT;
        } else if (head.getX() <= wall.getX() && head.getY() >= wall.getHeight() && direction == Direction.UP) {
            // 左上角行为
            return Direction.RIGHT;
        }

        // 左墙面行为
        if (head.getX() <= wall.getX() + wall.getX() && direction == Direction.LEFT) {
            if (head.getY() <= wall.getY()) {
                return Direction.LEFT;
            }
            return Direction.DOWN;
        } else if (head.getX() <= wall.getX() + wall.getX() && direction == Direction.DOWN) {
            return Direction.RIGHT;
        }

        // 右墙面行为
        if (head.getX() >= wall.getWidth() && direction == Direction.RIGHT) {
            return Direction.DOWN;
        } else if (head.getX() >= wall.getWidth() && direction == Direction.DOWN) {
            return Direction.LEFT;
        }

        return direction;
    }

    @Override
    public void reset() {
        once = false;
    }
}
