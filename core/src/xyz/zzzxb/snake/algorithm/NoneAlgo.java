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
public class NoneAlgo implements MoveAlgo {
    public static final NoneAlgo INSTANCE = new NoneAlgo();
    @Override
    public Direction move(Wall wall, Snake snake, Food food){return  null;}

    @Override
    public void reset() {
    }
}
