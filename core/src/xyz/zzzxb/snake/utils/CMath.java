package xyz.zzzxb.snake.utils;

import java.math.BigDecimal;

/**
 * zzzxb
 * 2024/3/31
 */
public class CMath {
    public static BigDecimal add(float x, float y) {
        return new BigDecimal(String.valueOf(x))
                .add(new BigDecimal(String.valueOf(y)));

    }

    public static BigDecimal sub(float x, float y) {
        return new BigDecimal(String.valueOf(x))
                .subtract(new BigDecimal(String.valueOf(y)));
    }
}
