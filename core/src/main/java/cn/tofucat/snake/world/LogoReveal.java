package cn.tofucat.snake.world;

import cn.tofucat.snake.SnakeGame;
import cn.tofucat.snake.animation.StageFade;
import cn.tofucat.snake.conf.Config;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.utils.ScreenUtils;

public class LogoReveal extends ScreenAdapter {
    SnakeGame game;
    StageFade fade;

    public LogoReveal(SnakeGame game) {
        this.game = game;
    }

    @Override
    public void show() {
        fade = new StageFade();

        fade.addSprite(createSprite("logo/snake.png"), 1.5f, 1.5f);
        fade.addSprite(createSprite("logo/libgdx.png"), 1f, 1f);
    }

    @Override
    public void render(float delta) {
        Sprite sprite = fade.process(delta, true);
        if (sprite == null) game.setScreen(new GameWorld(game));

        ScreenUtils.clear(Color.BLACK);
        game.viewport.apply();
        game.batch.setProjectionMatrix(game.viewport.getCamera().combined);

        game.batch.begin();
        if (sprite != null) sprite.draw(game.batch);
        game.batch.end();
    }

    @Override
    public void resize(int width, int height) {
        game.viewport.update(width, height, true);
    }

    private Sprite createSprite(String fileName) {
        Sprite sprite = new Sprite(new Texture(fileName));
        sprite.setPosition(((float) Config.instance.WINDOW_WIDTH - sprite.getWidth()) / 2,
            (float) Config.instance.WINDOW_HEIGHT / 2 - sprite.getHeight() / 4);
        return sprite;
    }
}
