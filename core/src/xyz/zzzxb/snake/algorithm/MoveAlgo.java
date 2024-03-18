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
public interface MoveAlgo {

    Direction move(Wall wall, Snake snake, Food food);

    void reset();

}
