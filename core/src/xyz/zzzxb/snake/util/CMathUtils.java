package xyz.zzzxb.snake.util;

import java.math.BigDecimal;

public class CMathUtils {

    public static float sum(float a, float b) {
        return sum(String.valueOf(a), String.valueOf(b)).floatValue();
    }

    public static double sum(double a, double b) {
        return sum(String.valueOf(a), String.valueOf(b)).doubleValue();
    }

    public static BigDecimal sum(String a, String b) {
        return new BigDecimal(a).add(new BigDecimal(b));
    }
}
