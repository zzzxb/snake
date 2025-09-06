package cn.tofucat.snake.entity;

import cn.tofucat.snake.conf.Config;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

public class Food extends Brick {
    public final Array<Vector2> points;
    public int foodNum;

    public Food(TextureRegion textureRegion) {
        super(textureRegion);
        this.points = new Array<>();
        init();
    }

    public void init() {
        if (!points.isEmpty()) points.clear();
        foodNum = Config.instance.DEFAULT_FOOD_NUM;
        addFood(foodNum);
    }

    public void addFood(int num) {
        if(num == 0) return;

        // todo 2025/9/7 需要根据空位生成食物，而非随机生成
        for (int i = 0; i < num; i++) {
            while (true) {
                int x = MathUtils.random(1, Config.instance.BRICK_NUM - 2);
                int y = MathUtils.random(1, Config.instance.BRICK_NUM - 2);
                Vector2 vector2 = new Vector2(x * Config.instance.BRICK_SIZE, y * Config.instance.BRICK_SIZE);
                if (!points.contains(vector2, false)) {
                    Gdx.app.log("eat", "add food point: " + vector2);
                    points.add(vector2);
                    break;
                }
            }
        }
    }

    public void draw(Batch batch) {
        points.forEach(point -> batch.draw(textureRegion, point.x, point.y, Config.instance.BRICK_SIZE, Config.instance.BRICK_SIZE));
    }
}
