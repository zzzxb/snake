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
import xyz.zzzxb.snake.game.Food;
import xyz.zzzxb.snake.game.Snake;
import xyz.zzzxb.snake.game.Square;
import xyz.zzzxb.snake.game.Wall;

import java.util.function.Supplier;

public class App extends ApplicationAdapter {
    SpriteBatch batch;
    OrthographicCamera camera;

    Wall wall;
    Snake snake;
    Food food;

    int score;
    GameState gameState;
    CtrlState ctrlState;

    BitmapFont font;
    TextureRegion tr;

    Music bgm;
    Music gom;
    Sound gold;

    long beginTime, endTime;

    @Override
    public void create() {
        batch = new SpriteBatch();
        wall = new Wall(Color.BLACK, 512, 512, 16, 16);
        snake = new Snake(new Color(0x3aa97cff), 16, 16, 0.05f);
        snake.setSpeed(0.01f, 0.01f, 0.09f);
        food = new Food(new Color(0xfbf236ff), 16, 16);
        food.randomPosition(snake.getPositions());
        camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        camera.position.set(Gdx.graphics.getWidth() / 2f, Gdx.graphics.getHeight() / 2f, 0);
        tr = new TextureRegion(new Texture("pt_mono.png"));
        font = new BitmapFont(Gdx.files.internal("pt_mono.fnt"), tr);

        bgm = Gdx.audio.newMusic(Gdx.files.internal("WhereIsTheLove.mp3"));
        bgm.setVolume(0.1f);
        bgm.setLooping(true);
        gom = Gdx.audio.newMusic(Gdx.files.internal("game-over.mp3"));
        gom.setVolume(0.02f);
        gold = Gdx.audio.newSound(Gdx.files.internal("gold.mp3"));

        gameState = GameState.NEWBORN;
        ctrlState = CtrlState.MANUAL;
    }

    @Override
    public void render() {
        ScreenUtils.clear(0.81f, 0.87f, 1, 1);
        camera.update();
        batch.setProjectionMatrix(camera.combined);

        batch.begin();
        sign();
        snake.draw(batch);
        food.draw(batch);
        wall.draw(batch);
        batch.end();

        gameCtrl();
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
            beginTime = 0;
            endTime = 0;

            gold.pause();
            gom.pause();
            bgm.pause();
            gom.setPosition(0);
            bgm.setPosition(0);
            MoveAlgoFactory.algo(ctrlState).reset();
        } else if (gameState != GameState.GAME_OVER) {
            // 游戏开始时，人物移动时播放音乐
            if (snake.getDirection() != Direction.STOP && !bgm.isPlaying()) {
                gom.pause();
                gom.setPosition(1);
                bgm.play();
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
            snake.getPositions().pop();
            snake.getPositions().add(snake.getLastPosition());
            bgm.pause();
            gom.play();
        }
    }

    private void eatFood() {
        if (food.crash(snake.getHead())) {
            gold.setVolume(gold.play(), 0.03f);
            score += 1;
            snake.addBody(score);
            food.randomPosition(snake.getPositions());
        }
    }

    private void algoCtrl() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_1)) {
            ctrlState = CtrlState.MANUAL;
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_2)) {
            ctrlState = CtrlState.AUTO_LOOP;
            MoveAlgoFactory.algo(ctrlState).reset();
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

    private void sign() {
        font.draw(batch, "FPS: " + Gdx.graphics.getFramesPerSecond(), 840, 500);
        font.draw(batch, "speed: " + snake.getSpeedLevel(), 710, 500);
        font.draw(batch, "score: " + score, 580, 500);
        font.draw(batch, time(), 580, 530);
        if (gameState == GameState.NEWBORN) {
            font.draw(batch, "Control the direction to start the game.", 545, 300);
        } else if (gameState == GameState.GAME_OVER) {
            font.draw(batch, "Game Over!", 682, 300);
            font.draw(batch, "Enter \"R\" Reset Game!", 625, 280);
        } else {
            font.draw(batch, snake.getDirection().getDESC(), 700, 300);
        }
        font.draw(batch, (ctrlState == CtrlState.MANUAL ? "*1 " : "  1 ") + CtrlState.MANUAL, 620, 100);
        font.draw(batch, (ctrlState == CtrlState.AUTO_LOOP ? "*2 " : "  2 ") + CtrlState.AUTO_LOOP, 620, 80);
        font.draw(batch, "1(slow/-) ~ 9(fast/+)", 620, 60);
        font.draw(batch, "UP/W DOWN/S LEFT/A RIGHT/D", 620, 40);
        time();
    }

    private String time() {
        if (snake.getDirection() != Direction.STOP && gameState != GameState.GAME_OVER && beginTime == 0) {
            beginTime = System.currentTimeMillis();
        }
        if (gameState == GameState.GAME_OVER && endTime == 0) {
            endTime = System.currentTimeMillis();
        }
        long totalMS = (endTime == 0 ? System.currentTimeMillis() : endTime) - beginTime;
        int ms = beginTime == 0 ? 0 : (int) (totalMS % 1000);
        long totalS = totalMS / 1000;
        int s = beginTime == 0 ? 0 : (int) (totalS % 60);
        long totalM = totalS / 60;
        int m = beginTime == 0 ? 0 : (int) totalM;
        int h = beginTime == 0 ? 0 : (int) (totalM / 60);
        return String.format("%02d:%02d:%02d.%03d", h, m, s, ms);
    }

    @Override
    public void dispose() {
        font.dispose();
        bgm.dispose();
        gom.dispose();
        gold.dispose();
        food.dispose();
        snake.dispose();
        wall.dispose();
        batch.dispose();
    }
}
