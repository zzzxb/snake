package xyz.zzzxb.snake.algo;

import xyz.zzzxb.snake.enums.Direction;
import xyz.zzzxb.snake.game.Food;
import xyz.zzzxb.snake.game.Snake;

/**
 * zzzxb
 * 2024/3/31
 */
public interface PathAlgo {

    Direction takeDirection(Snake snake, Food food, boolean pop);

    void reset();
}
