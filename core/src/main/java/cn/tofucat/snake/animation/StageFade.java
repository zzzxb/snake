package cn.tofucat.snake.animation;

import cn.tofucat.gdx.effect.FadeEffect;
import cn.tofucat.gdx.effect.StageEffect;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.utils.Array;

public class StageFade {
    private final StageEffect effect = new FadeEffect();
    private final Array<Times> timesList = new Array<>();
    private Sprite nowSprite = null;

    public void addSprite(Sprite sprite) {
        addSprite(sprite, 1, 1);
    }

    public void addSprite(Sprite sprite, float inTime, float outTime) {
        timesList.add(new Times(inTime, outTime, sprite));
        Gdx.app.log("StageFade", "add sprite " + sprite.getTexture());
    }

    public Sprite process(float delta) {
        return process(delta, false);
    }

    public Sprite process(float delta, boolean dispose) {
        if(effect.complete()) {
            removeFirst(dispose);
            nowSprite = null;
            effect.init();
        }

        if(timesList.size > 0) {
            Times times = timesList.first();
            if(nowSprite == null) {
                nowSprite = times.sprite;
                Gdx.app.log("StageFade", "now sprite is " + nowSprite.getTexture());
            }
            nowSprite.setAlpha(effect.show(delta, times.inTime, times.outTime));
        }
        return nowSprite;
    }

    private void removeFirst() {
        removeFirst(false);
    }

    private void removeFirst(boolean dispose) {
        if(timesList.size == 0) return;

        Times first = timesList.first();
        timesList.removeIndex(0);
        Gdx.app.log("StageFade", "remove sprite " + first.sprite.getTexture());

        if(dispose) {
            first.sprite.getTexture().dispose();
            Gdx.app.log("StageFade", "dispose sprite " + first.sprite.getTexture());
        }
    }

    static class Times {
        private final float inTime;
        private final float outTime;
        private final Sprite sprite;

        public Times(float inTime, float outTime, Sprite sprite) {
            this.inTime = inTime;
            this.outTime = outTime;
            this.sprite = sprite;
        }
    }
}
