package xyz.zzzxb.snake.game;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import xyz.zzzxb.snake.config.AppConfig;
import xyz.zzzxb.snake.enums.Direction;

/**
 * zzzxb
 * 2024/3/30
 */
public class Snake {
    public final Block block;
    public final Array<Position> body;
    private Position head;
    private Direction direction;
    private boolean directionLock;
    private float speed;

    public Snake(int width, int height, Color color, boolean fill) {
        this(new Block(width, height, color, fill));
    }

    public Snake(Block block) {
        this.block = block;
        this.body = new Array<>();
        init();
    }

    public void init() {
        this.head = new Position(15, 15);
        this.body.clear();
        this.directionLock = false;
        this.direction = Direction.STOP;
        this.speed = AppConfig.DEFAULT_SPEED;
    }

    public Position moveSimulate(Direction d) {
        Position p = null;
        switch (d) {
            case UP:
                p = new Position(head.x, head.y + 1);
                break;
            case DOWN:
                p = new Position(head.x, head.y - 1);
                break;
            case LEFT:
                p = new Position(head.x - 1, head.y);
                break;
            case RIGHT:
                p = new Position(head.x + 1, head.y);
                break;
            default:
                p = head.cp();
        }
        return p;
    }

    public void move(Position p, boolean removeTail) {
        if (!body.isEmpty() && removeTail) {
            body.insert(0, head.cp());
            body.removeIndex(body.size - 1);
        } else if (!removeTail) {
            body.insert(0, head.cp());
        }
        head = p;
        this.directionLock = false;
    }

    public void draw(SpriteBatch batch) {
        block.draw(batch, (head.x * AppConfig.BLOCK_SIZE) + AppConfig.MARGIN_X, (head.y * AppConfig.BLOCK_SIZE) + AppConfig.MARGIN_Y);
        for (Position p : body) {
            block.draw(batch, (p.x * AppConfig.BLOCK_SIZE) + AppConfig.MARGIN_X, (p.y * AppConfig.BLOCK_SIZE) + AppConfig.MARGIN_Y);
        }
    }

    public Array<Position> getPositionAll() {
        Array<Position> positionAll = new Array<>();
        positionAll.add(head);
        if (!body.isEmpty()) positionAll.addAll(body);
        return positionAll;
    }

    public Position getHead() {
        return head;
    }

    public Direction getDirection() {
        return direction;
    }

    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public void setDirection(Direction direction) {
        if (!this.directionLock && !direction.opposite(this.direction)) {
            this.directionLock = true;
            this.direction = direction;
        }
    }

    public boolean isDirectionLock() {
        return directionLock;
    }

    public void dispose() {
        block.dispose();
    }
}
