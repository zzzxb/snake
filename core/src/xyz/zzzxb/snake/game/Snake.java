package xyz.zzzxb.snake.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import xyz.zzzxb.snake.enums.Direction;

public class Snake {
    private final Square square;
    private final Array<Position> positions;
    private Direction direction;
    private final float originalSpeed;
    private float moveSpeed;
    private float speedDeltaTime;
    private int len;

    public Snake(Color color, int width, int height, float speed) {
        this.originalSpeed = speed;
        square = new Square(color, width, height, true);
        positions = new Array<>();
        init(speed);
    }

    public void init() {
        init(this.originalSpeed);
    }

    public void init(float speed) {
        this.positions.clear();
        this.positions.add(new Position(512f, 512f));
        this.direction = Direction.STOP;
        this.moveSpeed = speed;
        this.len = this.positions.size;
    }

    public void draw(SpriteBatch sprite) {
        if (speedDeltaTime <= moveSpeed) {
            for (Position p : positions) {
                square.draw(sprite, p);
            }
        }
    }

    public void addBody(int score) {
        this.len = score + 1;
    }

    public void move(int step, int distance) {
        float deltaTime = Gdx.graphics.getDeltaTime();
        speedDeltaTime += deltaTime;
        Position position = positions.get(0).copy();
        if (speedDeltaTime >= moveSpeed) {
            switch (direction) {
                case UP:
                    position.moveY(step, distance);
                    break;
                case DOWN:
                    position.moveY(-step, distance);
                    break;
                case LEFT:
                    position.moveX(-step, distance);
                    break;
                case RIGHT:
                    position.moveX(step, distance);
                    break;
            }
            speedDeltaTime = 0;
            positions.insert(0, position);
            if(positions.size > len) {
                positions.removeIndex(positions.size - 1);
            }
        }
    }

    public boolean suicide() {
        return false;
    }

    public Position getHead() {
        return positions.get(0);
    }

    public void ctrl() {
        if (direction != Direction.DOWN && (Gdx.input.isKeyPressed(Input.Keys.W) || Gdx.input.isKeyPressed(Input.Keys.UP))) {
            direction = Direction.UP;
        } else if (direction != Direction.UP && (Gdx.input.isKeyPressed(Input.Keys.S) || Gdx.input.isKeyPressed(Input.Keys.DOWN))) {
            direction = Direction.DOWN;
        } else if (direction != Direction.RIGHT && (Gdx.input.isKeyPressed(Input.Keys.A) || Gdx.input.isKeyPressed(Input.Keys.LEFT))) {
            direction = Direction.LEFT;
        } else if (direction != Direction.LEFT && (Gdx.input.isKeyPressed(Input.Keys.D) || Gdx.input.isKeyPressed(Input.Keys.RIGHT))) {
            direction = Direction.RIGHT;
        }
        if (this.moveSpeed < 0.09f && Gdx.input.isKeyJustPressed(Input.Keys.MINUS)) {
            this.moveSpeed += 0.01f;
        }
        if (this.moveSpeed > 0.02 && Gdx.input.isKeyJustPressed(Input.Keys.EQUALS)) {
            this.moveSpeed -= 0.01f;
        }
    }

    public int getSpeedLevel() {
        return 10 -(int)(this.moveSpeed / 0.01);
    }

    public float getSpeedDeltaTime() {
        return speedDeltaTime;
    }

    public Array<Position> getPositions() {
        return positions;
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public void dispose() {
        square.dispose();
    }
}
