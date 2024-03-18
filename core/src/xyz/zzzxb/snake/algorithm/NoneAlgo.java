package xyz.zzzxb.snake.algorithm;

import xyz.zzzxb.snake.enums.Direction;
import xyz.zzzxb.snake.game.Position;

/**
 * zzzxb
 * 2024/3/18
 */
public class NoneAlgo implements MoveAlgo {
    public static final NoneAlgo INSTANCE = new NoneAlgo();
    @Override
    public Direction move(Position head, Direction direction) {
        return null;
    }

    @Override
    public void reset() {
    }
}
