package cn.tofucat.snake.entity;

import cn.tofucat.snake.conf.Config;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

public class Wall extends Brick {
    public final Array<Vector2> points;

    public Wall(TextureRegion textureRegionRegion) {
        super(textureRegionRegion);
        points = new Array<>();
        init();
    }

    public void init() {
        if(!points.isEmpty()) points.clear();
        for (int i = 0; i < Config.instance.BRICK_NUM; i++) {
            // Top
            points.add(new Vector2(i * Config.instance.BRICK_SIZE,
                (Config.instance.BRICK_NUM - 1) * Config.instance.BRICK_SIZE));
            // Bottom
            points.add(new Vector2(i * Config.instance.BRICK_SIZE, 0));
            // Left
            points.add(new Vector2(0, i * Config.instance.BRICK_SIZE));
            // Right
            points.add(new Vector2((Config.instance.BRICK_NUM - 1) * Config.instance.BRICK_SIZE,
                i * Config.instance.BRICK_SIZE));
        }
    }

    public void draw(Batch batch) {
        points.forEach(point -> batch.draw(textureRegion, point.x, point.y, Config.instance.BRICK_SIZE, Config.instance.BRICK_SIZE));
    }
}
