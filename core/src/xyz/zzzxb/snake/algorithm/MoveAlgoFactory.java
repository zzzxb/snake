package xyz.zzzxb.snake.algorithm;

import xyz.zzzxb.snake.enums.CtrlState;

import java.util.Objects;

/**
 * zzzxb
 * 2024/3/18
 */
public class MoveAlgoFactory {
    public static MoveAlgo algo(CtrlState ctrlState) {
        switch (ctrlState) {
            case AUTO_LOOP: return LoopMove.INSTANCE;
            case CUSTOM_ALGO: return CustomMove.INSTANCE;
        }
        return NoneAlgo.INSTANCE;
    }

    public static void dispose() {
        for (CtrlState value : CtrlState.values()) {
            algo(value).dispose();
        }
    }
}
