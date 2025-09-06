package cn.tofucat.snake.entity;

import cn.tofucat.snake.conf.Config;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

public class Snake extends Brick {
    public final Array<Vector2> points;
    public Direction direction;
    public Array<Direction> directions;
    public int len;
    public float speed;

    public Snake(TextureRegion textureRegion) {
        super(textureRegion);
        points = new Array<>();
        directions = new Array<>();
        init();
    }

    public void init() {
        if (!points.isEmpty()) points.clear();
        if (!directions.isEmpty()) directions.clear();
        len = Config.instance.DEFAULT_SNAKE_LEN;
        speed = (float) Config.instance.DEFAULT_SNAKE_SPEED / 100;
        initDirection(Direction.UP);
    }

    public void initDirection(Direction direction) {
        int centerPoint = (int) (Config.instance.BRICK_NUM / 2) + 1;
        directions.add(this.direction = direction);
        for (int i = 0; i < Config.instance.DEFAULT_SNAKE_LEN; i++) {
            points.add(new Vector2((centerPoint - 1) * Config.instance.BRICK_SIZE, (centerPoint - i) * Config.instance.BRICK_SIZE));
        }
    }

    public void draw(Batch batch) {
        for (int i = 0; i < len; i++) {
            Vector2 v2 = points.get(i);
            batch.draw(textureRegion, v2.x, v2.y, Config.instance.BRICK_SIZE, Config.instance.BRICK_SIZE);
        }
    }

    public enum Direction {
        UP, DOWN, LEFT, RIGHT
    }
}
