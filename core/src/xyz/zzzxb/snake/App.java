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
    GameSound sound;
    GameInfo gameInfo;

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
    }

    @Override
    public void render() {
        ScreenUtils.clear(0.81f, 0.87f, 1, 1);

        batch.begin();
        MoveAlgoFactory.algo(gameInfo.getCtrlState()).drawAuxiliaryLine(batch);
        gameInfo.drawGameInfo(batch, snake);
        snake.draw(batch);
        food.draw(batch);
        wall.draw(batch);
        batch.end();

        gameCtrl();
    }

    private void algoCtrl() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_1)) {
            gameInfo.setCtrlState(CtrlState.MANUAL);
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_2)) {
            gameInfo.setCtrlState(CtrlState.AUTO_LOOP);
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_3)) {
            gameInfo.setCtrlState(CtrlState.CUSTOM_MOVE);
        }
    }


    private void gameCtrl() {
        algoCtrl();
        snake.speedCtrl();
        resetGame();
        beginGame();

        // 退出游戏
        if (Gdx.input.isKeyJustPressed(Input.Keys.Q)) {
            Gdx.app.exit();
        }
    }

    public void resetGame() {
        if (gameInfo.gameStateEq(GameState.GAME_OVER)  && Gdx.input.isKeyPressed(Input.Keys.R)) {
            gameInfo.setGameState(GameState.ACTIVE);
            snake.init();
            gameInfo.init();
            sound.init();
        }
    }

    public void beginGame() {
        if (!gameInfo.gameStateEq(GameState.GAME_OVER)) {
            // 游戏开始时，人物移动时播放音乐
            if (!snake.directionEq(Direction.STOP)  && !sound.getBgm().isPlaying()) {
                sound.resetGOM();
                sound.getBgm().play();
                gameInfo.setGameState(GameState.ACTIVE);
            }
            ctrlMove();
            eatFood();
            checkGameOver();
        }
    }

    public void checkGameOver() {
        if (wall.crash(snake.getHead()) || snake.suicide()) {
            snake.setDirection(Direction.STOP);
            gameInfo.setGameState(GameState.GAME_OVER);
            snake.getPositions().removeIndex(0);
            snake.getPositions().add(snake.getLastPosition());
            sound.getBgm().pause();
            sound.getGom().play();
        }
    }

    private void eatFood() {
        if (food.crash(snake.getHead())) {
            sound.getGold().setVolume(sound.getGold().play(), 0.03f);
            snake.addBody(gameInfo.incrScore());
            food.randomPosition(snake.getPositions());
        }
    }

    private void ctrlMove() {
        MoveAlgo algo = MoveAlgoFactory.algo(gameInfo.getCtrlState());
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
