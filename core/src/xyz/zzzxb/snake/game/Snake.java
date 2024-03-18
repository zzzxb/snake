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
    private float moveSpeed;
    private float speedStep;
    private float minSpeed;
    private float maxSpeed;
    private float speedDeltaTime;
    private int len;
    private boolean cd;

    public Snake(Color color, int width, int height, float speed) {
        square = new Square(color, width, height, true);
        positions = new Array<>();
        init(speed);
    }

    public void init() {
        init(this.moveSpeed);
    }

    public void init(float speed) {
        this.positions.clear();
        this.positions.add(new Position(256f, 256f));
        this.direction = Direction.STOP;
        this.moveSpeed = speed;
        this.len = this.positions.size;
    }

    public void draw(SpriteBatch sprite) {
        for (Position p : positions) {
            square.draw(sprite, p);
        }
    }

    public void addBody(int score) {
        this.len = score + 1;
    }

    public void move(int step, int distance) {
        Position position = positions.get(0).copy();
        if (!cd) {
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
            positions.insert(0, position);
            if (positions.size > len) {
                positions.removeIndex(positions.size - 1);
            }
            cd = true;
        }
    }

    // true 冷却中， false 冷却完毕
    public boolean checkCD() {
        if (!cd) {
            return false;
        }

        float deltaTime = Gdx.graphics.getDeltaTime();
        speedDeltaTime += deltaTime;
        if (speedDeltaTime >= moveSpeed) {
            speedDeltaTime = 0;
            cd = false;
        } else {
            cd = true;
        }
        return cd;
    }

    public boolean suicide() {
        return false;
    }

    public Position getHead() {
        return positions.peek();
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
    }

    public void setSpeed(float speedStep, float minSpeed, float maxSpeed) {
        setSpeed(this.moveSpeed, speedStep, minSpeed, maxSpeed);
    }

    public void setSpeed(float initSpeed, float speedStep, float minSpeed, float maxSpeed) {
        this.speedStep = speedStep;
        this.minSpeed = minSpeed;
        this.maxSpeed = maxSpeed;
        this.moveSpeed = initSpeed;
    }

    public void speedCtrl() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.EQUALS)) {
            if (moveSpeed > minSpeed) {
                this.moveSpeed -= speedStep;
            }
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.MINUS)) {
            if (moveSpeed < maxSpeed) {
                this.moveSpeed += speedStep;
            }
        }
    }

    public int getSpeedLevel() {
        return (int) ((this.maxSpeed - this.minSpeed) / speedStep) - (int) ((moveSpeed - this.minSpeed) / speedStep) + 1;
    }

    public Array<Position> getPositions() {
        return positions;
    }

    public Direction getDirection() {
        return direction;
    }

    public float getMoveSpeed() {
        return moveSpeed;
    }

    public void setMoveSpeed(float moveSpeed) {
        this.moveSpeed = moveSpeed;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public void dispose() {
        square.dispose();
    }
}
