package xyz.zzzxb.snake.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;

/**
 * zzzxb
 * 2024/3/19
 */
public class GameSound {
    private final Music bgm;
    private final Music gom;
    private final Sound gold;

    public GameSound() {
        bgm = Gdx.audio.newMusic(Gdx.files.internal("WhereIsTheLove.mp3"));
        gom = Gdx.audio.newMusic(Gdx.files.internal("game-over.mp3"));
        gold = Gdx.audio.newSound(Gdx.files.internal("gold.mp3"));

    }

    public void init() {
        resetBGM();
        resetGOM();
        gold.pause();
    }

    public void resetBGM() {
        bgm.pause();
        bgm.setVolume(0.1f);
        bgm.setLooping(true);
        bgm.setPosition(0);
    }

    public void resetGOM() {
        gom.pause();
        gom.setVolume(0.02f);
        gom.setPosition(0);
    }

    public Music getBgm() {
        return bgm;
    }

    public Music getGom() {
        return gom;
    }

    public Sound getGold() {
        return gold;
    }

    public void dispose() {
        bgm.dispose();
        gom.dispose();
        gold.dispose();
    }
}
