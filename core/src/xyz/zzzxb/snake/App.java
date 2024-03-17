package xyz.zzzxb.snake;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Audio;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import org.omg.PortableInterceptor.ACTIVE;
import xyz.zzzxb.snake.enums.Direction;
import xyz.zzzxb.snake.enums.GameState;
import xyz.zzzxb.snake.game.Food;
import xyz.zzzxb.snake.game.Snake;
import xyz.zzzxb.snake.game.Square;
import xyz.zzzxb.snake.game.Wall;

public class App extends ApplicationAdapter {
    SpriteBatch batch;
    OrthographicCamera camera;
    Wall wall;
    Snake snake;
    Food food;
    int score;
    BitmapFont font;
    GameState gameState;
    Music bgm;
    Music gom;
    Sound gold;

    @Override
    public void create() {
        batch = new SpriteBatch();
        wall = new Wall(Color.BLACK, 1024, 1024, 32, 32);
        snake = new Snake(new Color(0x3aa97cff), 32, 32, 0.05f);
        food = new Food(new Color(0xfbf236ff), 32, 32);
        food.randomPosition(snake.getPositions());
        camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        camera.position.set(Gdx.graphics.getWidth() / 2f, Gdx.graphics.getHeight() / 2f, 0);
        font = new BitmapFont();
        font.getData().setScale(3);
        gameState = GameState.NEWBORN;
        bgm = Gdx.audio.newMusic(Gdx.files.internal("WhereIsTheLove.mp3"));
        bgm.setLooping(true);
        gom = Gdx.audio.newMusic(Gdx.files.internal("game-over.mp3"));
        gold = Gdx.audio.newSound(Gdx.files.internal("gold.mp3"));
    }

    @Override
    public void render() {
        ScreenUtils.clear(0.81f, 0.87f, 1, 1);
        camera.update();
        batch.setProjectionMatrix(camera.combined);

        batch.begin();
        sign();
        food.draw(batch);
        snake.draw(batch);
        wall.draw(batch);
        batch.end();

        ctrl();
    }

    public void ctrl() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.P)) {
            snake.setDirection(Direction.STOP);
            bgm.pause();
        }

        if (gameState == GameState.GAME_OVER) {
            if (Gdx.input.isTouched() || Gdx.input.isKeyPressed(Input.Keys.R)) {
                gameState = GameState.ACTIVE;
                score = 0;
                snake.init();

                gold.pause();
                gom.pause();
                gom.setPosition(0);
                bgm.pause();
                bgm.setPosition(0);

            }
            return;
        }
        if(food.crash(snake.getHead())) {
            gold.play();
            score += 1;
            snake.addBody(score);
            food.randomPosition(snake.getPositions());
        }
        if (gameState != GameState.GAME_OVER) {
            if (snake.getDirection() != Direction.STOP) {
                gom.pause();
                gom.setPosition(1);
                bgm.play();
                gameState = GameState.ACTIVE;
            }
            snake.ctrl();
            snake.move(1, 32);
        }

        if(wall.crash(snake.getHead()) || snake.suicide()) {
            bgm.pause();
            gom.play();
            gameState = GameState.GAME_OVER;
        };
    }

    public void sign() {
        font.draw(batch, "UP/W DOWN/S LEFT/A RIGHT/D", 1160, 100);
        font.draw(batch, "score: " + score, 1400, 1000);
        font.draw(batch, "speed: " + snake.getSpeedLevel(), 1400, 900);
        if (gameState == GameState.NEWBORN) {
            font.draw(batch, "Control the direction to start the game.", 1120, 600);
        }else if (gameState == GameState.GAME_OVER) {
            font.draw(batch, "Game Over!", 1365, 600);
        }else {
            font.draw(batch, snake.getDirection().getDESC(), 1400, 600);
        }
    }

    @Override
    public void dispose() {
        bgm.dispose();
        gom.dispose();
        gold.dispose();
        food.dispose();
        snake.dispose();
        wall.dispose();
        batch.dispose();
    }
}
