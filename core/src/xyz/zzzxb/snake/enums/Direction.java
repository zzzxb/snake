package xyz.zzzxb.snake.enums;

public enum Direction {
    UP("UP/W"),
    DOWN("DOWN/S"),
    LEFT("LEFT/A"),
    RIGHT("RIGHT/D"),
    STOP("STOP");

    private final String DESC;

    Direction(String desc) {
        this.DESC = desc;
    }

    public String getDESC() {
        return DESC;
    }
}
