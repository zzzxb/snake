package xyz.zzzxb.snake.algorithm;

import xyz.zzzxb.snake.enums.Direction;
import xyz.zzzxb.snake.game.Position;

/**
 * zzzxb
 * 2024/3/18
 */
public interface MoveAlgo {

    Direction move(Position head, Direction direction);

    void reset();

}
