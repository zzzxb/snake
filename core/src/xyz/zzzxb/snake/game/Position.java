package xyz.zzzxb.snake.game;

public class Position {
    private float x;
    private float y;

    public Position(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public void moveX(int step, int distance) {
        this.x += (float)(step * distance);
    }

    public void moveY(int step, int distance) {
        this.y += (float)(step * distance);
    }

    public void setPosition(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }


    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Position) {
            Position p = (Position) obj;
            return this.getX() == p.getX() && this.getY() == p.getY();
        }
        return false;
    }

    public Position copy() {
        return new Position(this.x, this.y);
    }
}