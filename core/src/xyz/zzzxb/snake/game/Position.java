package xyz.zzzxb.snake.game;

/**
 * zzzxb
 * 2024/3/30
 */
public class Position {
    public final int x;
    public final int y;

    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public boolean ltX(Position p) { return this.x < p.x;}
    public boolean gtX(Position p) { return this.x > p.x;}
    public boolean ltY(Position p) { return this.y < p.y;}
    public boolean gtY(Position p) { return this.y > p.y;}

    public Position cp() {
        return new Position(this.x, this.y);
    }

    @Override
    public boolean equals(Object obj) {
        boolean eq = false;
        if(obj instanceof Position) {
            Position p = (Position) obj;
            eq = this.x == p.x && this.y == p.y;
        }
        return eq;
    }

    @Override
    public String toString() {
        return "{" +
                "x:" + x +
                ", y:" + y +
                '}';
    }
}
