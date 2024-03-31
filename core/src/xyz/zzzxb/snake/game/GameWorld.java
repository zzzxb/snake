package xyz.zzzxb.snake.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import xyz.zzzxb.snake.config.AppConfig;
import xyz.zzzxb.snake.enums.Direction;
import xyz.zzzxb.snake.enums.GameState;
import xyz.zzzxb.snake.enums.MoveMode;
import xyz.zzzxb.snake.utils.CMath;

/**
 * zzzxb
 * 2024/3/30
 */
public class GameWorld extends ApplicationAdapter {
    SpriteBatch batch;
    Wall wall;
    Snake snake;
    Food food;
    float deltaTimeCount;
    GameState gameState;
    GameState gameStateRecord;
    MoveMode moveMode;
    Texture texture;
    Music music;

    public GameWorld(SpriteBatch batch) {
        this.batch = batch;
        int wallSize = AppConfig.BLOCK_SIZE * AppConfig.BLOCK_NUM;
        wall = new Wall(wallSize, wallSize, Color.BLACK, false);
        snake = new Snake(AppConfig.BLOCK_SIZE, AppConfig.BLOCK_SIZE, new Color(0.36f, 0.65f, 0.50f, 1), true);
        food = new Food(AppConfig.BLOCK_SIZE, AppConfig.BLOCK_SIZE, new Color(0.98f, 0.95f, 0.37f, 1));
        food.randomGenFood(snake.getPositionAll());
        texture = new Texture("help.png");
        music = Gdx.audio.newMusic(Gdx.files.internal("WhereIsTheLove.mp3"));
        music.setLooping(true);
        music.setVolume(0.8f);
        gameState = GameState.START;
        moveMode = MoveMode.KEYBOARD;

    }

    public void render() {
        if (gameState == GameState.HELP || gameState == GameState.STOP) {
            batch.draw(texture, AppConfig.BLOCK_SIZE, AppConfig.BLOCK_SIZE);
        } else {
            food.draw(batch);
            snake.draw(batch);
        }
        wall.draw(batch);
    }

    public void ctrl() {
        startGame();
        selectCtrlMode();
        otherKeyCtrl();
    }

    public void startGame() {
        if (gameState == GameState.START) {
            if(!music.isPlaying()) {
                music.setPosition(0);
                music.play();
            }

            Direction d = null;
            if (moveMode == MoveMode.KEYBOARD) {
                d = keyMoveSnake();
            } else {
                d = moveMode.algo.takeDirection(snake, food, !snake.isDirectionLock());
            }

            if (!snake.isDirectionLock() && d != null) {
                snake.setDirection(d);
            }

            gameComputing();
        }
    }

    public void selectCtrlMode() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_1)) {
            moveMode = MoveMode.KEYBOARD;
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_2)) {
            moveMode = MoveMode.AUTO_LOOP;
            snake.setSpeed(0.01f);
        }
        Gdx.graphics.setTitle(AppConfig.TITLE + " - " + moveMode.name() + " - " + snake.getSpeed());
    }

    public void gameComputing() {
        if (deltaTimeCount < snake.getSpeed()) {
            deltaTimeCount = CMath.add(deltaTimeCount, Gdx.graphics.getDeltaTime()).floatValue();
        }

        if (deltaTimeCount >= snake.getSpeed()) {
            boolean removeTail = true;
            Position snakeHeadPosition = snake.moveSimulate(snake.getDirection());
            crash(snakeHeadPosition);
            removeTail = !food.crash(snakeHeadPosition);
            if (gameState == GameState.START) {
                snake.move(snakeHeadPosition, removeTail);
                deltaTimeCount = 0;
            }
            if (!removeTail) {
                food.randomGenFood(snake.getPositionAll());
                if (moveMode.algo != null) {
                    moveMode.algo.reset();
                }
            }
        }
    }

    public void otherKeyCtrl() {
        if (gameState == GameState.GAME_OVER && Gdx.input.isKeyJustPressed(Input.Keys.R)) {
            gameState = GameState.START;
            snake.init();
            food.init();
        }

        if (gameState != GameState.GAME_OVER && Gdx.input.isKeyJustPressed(Input.Keys.P)) {
            gameState = gameState == GameState.STOP ? GameState.START : GameState.STOP;
            if (gameState == GameState.STOP) {
                music.pause();
            }else {
                music.play();
            }
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.Q)) {
            Gdx.app.exit();
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.EQUALS)) {
            if (snake.getSpeed() < 0.1f) snake.setSpeed(CMath.add(snake.getSpeed(), 0.01f).floatValue());
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.MINUS)) {
            if (snake.getSpeed() > 0.01f) snake.setSpeed(CMath.sub(snake.getSpeed(), 0.01f).floatValue());
        }

        if (Gdx.input.isKeyPressed(Input.Keys.H)) {
            if (gameStateRecord == null) {
                gameStateRecord = gameState;
            }
            gameState = GameState.HELP;
            music.pause();
        } else if (gameStateRecord != null){
            gameState = gameStateRecord;
            gameStateRecord = null;
            music.play();
        }
    }

    public Direction keyMoveSnake() {
        Direction d = null;
        if (Gdx.input.isKeyJustPressed(Input.Keys.W)) {
            d = Direction.UP;
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.S)) {
            d = Direction.DOWN;
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.A)) {
            d = Direction.LEFT;
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.D)) {
            d = Direction.RIGHT;
        }
        return d;
    }

    public void crash(Position p) {
        boolean crashWall = wall.contain(p);
        boolean crashBody = snake.body.contains(p, false);
        if (!crashWall || crashBody) {
            gameState = GameState.GAME_OVER;
        }
    }

    public void dispose() {
        music.dispose();
        texture.dispose();
        snake.dispose();
        wall.dispose();
        batch.dispose();
    }
}
