package xyz.zzzxb.snake.enums;

/**
 * zzzxb
 * 2024/3/30
 */
public enum Direction {
    UP(1), DOWN(-1), LEFT(2), RIGHT(-2), STOP(0);
    private final int num;

    Direction(int num) {
        this.num = num;
    }

    public boolean opposite(Direction d) {
        return (this.num + d.num) == 0;
    }
}
