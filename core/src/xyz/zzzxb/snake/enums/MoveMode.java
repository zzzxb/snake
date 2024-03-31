package xyz.zzzxb.snake.enums;

import xyz.zzzxb.snake.algo.AutoLoopAlgo;
import xyz.zzzxb.snake.algo.PathAlgo;

/**
 * zzzxb
 * 2024/3/31
 */
public enum MoveMode {
    KEYBOARD(null),
    AUTO_LOOP(AutoLoopAlgo.INSTANCES);

    public final PathAlgo algo;

    MoveMode(PathAlgo algo) {
        this.algo = algo;
    }
}
