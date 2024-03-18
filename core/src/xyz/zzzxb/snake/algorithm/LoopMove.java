package xyz.zzzxb.snake.algorithm;

import xyz.zzzxb.snake.enums.Direction;
import xyz.zzzxb.snake.game.Position;

/**
 * zzzxb
 * 2024/3/18
 */
public class LoopMove implements MoveAlgo {
    public final static LoopMove INSTANCE = new LoopMove();
    boolean once;

    @Override
    public Direction move(Position head, Direction direction) {
        if (!once) {
            once = true;
            return Direction.RIGHT;
        }
        // 左下角、右下角 行为
        if ((head.getX() <= 16 && head.getY() <= 16 && direction == Direction.LEFT) ||
                (head.getX() >= 512 && head.getY() <= 16 && direction == Direction.RIGHT)) {
            return Direction.UP;
        }

        if (head.getX() >= 512 && head.getY() >= 512 && direction == Direction.UP) {
            // 右上角行为
            return Direction.LEFT;
        } else if (head.getX() <= 16 && head.getY() >= 512 && direction == Direction.UP) {
            // 左上角行为
            return Direction.RIGHT;
        }

        if (head.getX() <= 16 && direction == Direction.LEFT) {
            // 左墙面行为
            return Direction.DOWN;
        } else if (head.getX() <= 16 && direction == Direction.DOWN) {
            // 右墙面行为
            return Direction.RIGHT;
        }

        if (head.getX() >= 512 && direction == Direction.RIGHT) {
            return Direction.DOWN;
        } else if (head.getX() >= 512 && direction == Direction.DOWN) {
            return Direction.LEFT;
        }


        return direction;
    }

    @Override
    public void reset() {
        once = false;
    }
}
