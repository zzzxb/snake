package cn.tofucat.snake.entity;

import cn.tofucat.snake.conf.Config;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

public class Floor extends Brick {
    public final Array<Vector2> points;

    public Floor(TextureRegion textureRegion) {
        super(textureRegion);
        points = new Array<>();
        init();
    }

    public void init() {
        if(!points.isEmpty()) points.clear();
        for (int i = 1; i < Config.instance.BRICK_NUM - 1; i++) {
            for (int j = 1; j < Config.instance.BRICK_NUM - 1; j++) {
                points.add(new Vector2(i * Config.instance.BRICK_SIZE, j * Config.instance.BRICK_SIZE));
            }
        }
    }

    public void draw(Batch batch) {
        points.forEach(point -> batch.draw(textureRegion, point.x, point.y, Config.instance.BRICK_SIZE, Config.instance.BRICK_SIZE));
    }
}
