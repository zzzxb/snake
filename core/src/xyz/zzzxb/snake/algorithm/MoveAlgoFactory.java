package xyz.zzzxb.snake.algorithm;

import xyz.zzzxb.snake.enums.CtrlState;

import java.util.Objects;

/**
 * zzzxb
 * 2024/3/18
 */
public class MoveAlgoFactory {
    public static MoveAlgo algo(CtrlState ctrlState) {
        if (Objects.requireNonNull(ctrlState) == CtrlState.AUTO_LOOP) {
            return LoopMove.INSTANCE;
        }
        return NoneAlgo.INSTANCE;
    }
}
