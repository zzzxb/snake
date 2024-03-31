package xyz.zzzxb.snake.algo;

import com.badlogic.gdx.utils.Array;
import xyz.zzzxb.snake.config.AppConfig;
import xyz.zzzxb.snake.enums.Direction;
import xyz.zzzxb.snake.game.Food;
import xyz.zzzxb.snake.game.Position;
import xyz.zzzxb.snake.game.Snake;

/**
 * zzzxb
 * 2024/3/31
 */
public class AutoLoopAlgo implements PathAlgo {
    public static final AutoLoopAlgo INSTANCES = new AutoLoopAlgo();


    /**
     *  -----------
     * |>>>>>>>>>>V|
     * |^V<<<<<<<<<|
     * |^>>>>>>>>>V|
     * |^<<<<<<<<<<|
     *  -----------|
     */
    @Override
    public Direction takeDirection(Snake snake, Food food, boolean pop) {
        Position head = snake.getHead();
        Direction d = head.y % 2 == 0 ?  Direction.LEFT : Direction.RIGHT;
        if (head.x == 0 && head.y == AppConfig.BLOCK_NUM - 1) d = Direction.RIGHT; // 触左上角往右走
        else if (head.x == 0) d = Direction.UP; // 触左墙往上走
        else if (head.y == 0) d = Direction.LEFT; // 触底往左走
        else if (head.x == AppConfig.BLOCK_NUM - 1 && snake.getDirection() == Direction.RIGHT) d = Direction.DOWN; // 触右墙往下
        else if (head.x == AppConfig.BLOCK_NUM - 1 && snake.getDirection() == Direction.DOWN) d = Direction.LEFT; // 触右墙下走后向右
        else if (head.x == 1 && snake.getDirection() == Direction.LEFT) d = Direction.DOWN; // 触左墙向下
        else if (head.x == 1 && snake.getDirection() == Direction.DOWN) d = Direction.RIGHT; // 触左墙向下后再向右
        return pop ? d : null;
    }

    @Override
    public void reset() {
    }

}
