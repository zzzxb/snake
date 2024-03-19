package xyz.zzzxb.snake;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Audio;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.ScreenUtils;
import org.omg.PortableInterceptor.ACTIVE;
import xyz.zzzxb.snake.algorithm.MoveAlgo;
import xyz.zzzxb.snake.algorithm.MoveAlgoFactory;
import xyz.zzzxb.snake.algorithm.NoneAlgo;
import xyz.zzzxb.snake.enums.CtrlState;
import xyz.zzzxb.snake.enums.Direction;
import xyz.zzzxb.snake.enums.GameState;
import xyz.zzzxb.snake.game.*;

import java.util.function.Supplier;

public class App extends ApplicationAdapter {
    SpriteBatch batch;

    Wall wall;
    Snake snake;
    Food food;
    GameInfo gameInfo;
    GameSound sound;

    int score;
    GameState gameState;
    CtrlState ctrlState;


    @Override
    public void create() {
        batch = new SpriteBatch();

        wall = new Wall(Color.BLACK, 512, 512, 16, 16);
        snake = new Snake(new Color(0x3aa97cff), 16, 16, 0.05f);
        snake.setSpeed(0.01f, 0.01f, 0.09f);
        food = new Food(new Color(0xfbf236ff), 16, 16);
        food.randomPosition(snake.getPositions());
        gameInfo = new GameInfo();
        sound = new GameSound();

        gameState = GameState.NEWBORN;
        ctrlState = CtrlState.MANUAL;
    }

    @Override
    public void render() {
        ScreenUtils.clear(0.81f, 0.87f, 1, 1);

        batch.begin();
        MoveAlgoFactory.algo(ctrlState).drawAuxiliaryLine(batch);
        gameInfo.drawGameInfo(batch, snake, gameState, ctrlState, score);
        snake.draw(batch);
        food.draw(batch);
        wall.draw(batch);
        batch.end();

        gameCtrl();
    }

    private void algoCtrl() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_1)) {
            ctrlState = CtrlState.MANUAL;
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_2)) {
            ctrlState = CtrlState.AUTO_LOOP;
            MoveAlgoFactory.algo(ctrlState).reset();
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_3)) {
            ctrlState = CtrlState.CUSTOM_MOVE;
        }
    }


    private void gameCtrl() {
        // 退出游戏
        if (Gdx.input.isKeyJustPressed(Input.Keys.Q)) {
            Gdx.app.exit();
        }
        snake.speedCtrl(); // 移动速度控制
        algoCtrl(); // 移动算法控制

        // 游戏结束, 按R重新开始
        if (gameState == GameState.GAME_OVER && Gdx.input.isKeyPressed(Input.Keys.R)) {
            gameState = GameState.ACTIVE;
            score = 0;
            snake.init();
            gameInfo.init();
            sound.init();
            MoveAlgoFactory.algo(ctrlState).reset();
        } else if (gameState != GameState.GAME_OVER) {
            // 游戏开始时，人物移动时播放音乐
            if (!snake.directionEq(Direction.STOP)  && !sound.getBgm().isPlaying()) {
                sound.resetGOM();
                sound.getBgm().play();
                gameState = GameState.ACTIVE;
            }
            ctrlMove();
            eatFood();
            checkGameOver();
        }
    }

    public void checkGameOver() {
        if (wall.crash(snake.getHead()) || snake.suicide()) {
            snake.setDirection(Direction.STOP);
            gameState = GameState.GAME_OVER;
            sound.getBgm().pause();
            sound.getGom().play();
        }
    }

    private void eatFood() {
        if (food.crash(snake.getHead())) {
            sound.getGold().setVolume(sound.getGold().play(), 0.03f);
            score += 1;
            snake.addBody(score);
            food.randomPosition(snake.getPositions());
        }
    }

    private void ctrlMove() {
        // 贪吃蛇每次移动，都是有cd的，不然移动过快, 冷却完毕即可移动
        MoveAlgo algo = MoveAlgoFactory.algo(ctrlState);
        if (algo instanceof NoneAlgo) {
            snake.ctrl();
            snake.checkCD();
        } else {
            if (!snake.checkCD())
                snake.setDirection(algo.move(wall, snake, food));
        }
        snake.move(1, 16);
    }

    @Override
    public void dispose() {
        MoveAlgoFactory.dispose();
        gameInfo.dispose();
        sound.dispose();
        food.dispose();
        snake.dispose();
        wall.dispose();
        batch.dispose();
    }
}
