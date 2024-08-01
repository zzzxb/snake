package xyz.zzzxb.snake;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import xyz.zzzxb.snake.config.Config;
import xyz.zzzxb.snake.game.AbstractScreen;

public class FirstScreen extends AbstractScreen {

    SpriteBatch batch;
    Texture snake;
    Texture wall;
    Texture food;

    GameStatus gameStatus;
    Array<Vector2> map;
    Array<Vector2> snakeXY;
    int snakeX, snakeY;
    int foodX, foodY;
    int offsetX, offsetY;
    float moveSpeed;
    float moveSpeedDelta;
    boolean delSnakeTail = true;

    enum GameStatus {
        WIN, OVER, NONE
    }

    @Override
    public void show() {
        Gdx.app.setLogLevel(Config.LOGGER_LEVEL);
        batch = new SpriteBatch();
        Pixmap pixmap = new Pixmap(Config.SNAKE_SIZE, Config.SNAKE_SIZE, Pixmap.Format.RGBA8888);
        pixmap.setColor(Color.WHITE);
        pixmap.fillRectangle(0, 0, Config.SNAKE_SIZE, Config.SNAKE_SIZE);
        snake = new Texture(pixmap);


        Pixmap pixmap1 = new Pixmap(Config.SNAKE_SIZE, Config.SNAKE_SIZE, Pixmap.Format.RGBA8888);
        pixmap1.setColor(Color.YELLOW);
        pixmap1.fillRectangle(0, 0, Config.SNAKE_SIZE, Config.SNAKE_SIZE);
        food = new Texture(pixmap1);

        Pixmap pixmap2 = new Pixmap(Config.WALL_SIZE, Config.WALL_SIZE, Pixmap.Format.RGBA8888);
        pixmap2.setColor(Color.WHITE);
        pixmap2.drawRectangle(0, 0, Config.WALL_SIZE, Config.WALL_SIZE);
        wall = new Texture(pixmap2);

        snakeXY = new Array<>();
        map = new Array<>();
        initMap();
        init();
        randomFood();
    }

    public void initMap() {
        for (int i = 1; i <= Config.BLOCK_LINE; i++) {
            for (int j = 1; j <= Config.BLOCK_LINE; j++) {
                map.add(new Vector2(i * Config.SNAKE_SIZE, j * Config.SNAKE_SIZE));
            }
        }
    }

    public void init() {
        gameStatus = GameStatus.NONE;
        offsetX = 0;
        offsetY = 0;
        snakeX = Config.CENTER;
        snakeY = Config.CENTER;
        delSnakeTail = true;
        moveSpeed = 0.1f;
        moveSpeedDelta = 0;
        snakeXY.clear();
        snakeXY.add(new Vector2(snakeX, snakeY));
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(Color.BLACK);
        control(delta);

        batch.begin();
        batch.draw(food, foodX, foodY);
        for (Vector2 vector2 : snakeXY) {
            batch.draw(snake, vector2.x, vector2.y);
        }
        batch.draw(wall, Config.SNAKE_SIZE, Config.SNAKE_SIZE);
        batch.end();

        eatFood();
        win();
    }

    public void randomFood() {
        Array<Vector2> cpMap = new Array<>(map);
        cpMap.removeAll(snakeXY, false);
        Gdx.app.debug("地图总位置", String.valueOf(map.size));
        Gdx.app.debug("蛇占用位置", String.valueOf(snakeXY.size));
        Gdx.app.debug("空余位置", String.valueOf(cpMap.size));
        int random = MathUtils.random(0, cpMap.size - 1);
        Vector2 vector2 = cpMap.get(random);
        foodX = (int) vector2.x;
        foodY = (int) vector2.y;
    }

    public void win() {
        Array<Vector2> cpSnakeXY = new Array<>(snakeXY);
        Vector2 vector2 = cpSnakeXY.removeIndex(0);
        boolean commitSuicide = cpSnakeXY.contains(vector2,false);

        if (commitSuicide || snakeX < Config.SNAKE_SIZE || snakeX > Config.WALL_SIZE ||
            snakeY < Config.SNAKE_SIZE || snakeY > Config.WALL_SIZE) {
            gameStatus = GameStatus.OVER;
            offsetX = 0;
            offsetY = 0;
        }
    }

    public void control(float delta) {
        if (Gdx.input.isKeyJustPressed(Input.Keys.Q)) {
            Gdx.app.debug("游戏控制", "退出游戏");
            Gdx.app.exit();
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.R)) {
            Gdx.app.debug("游戏控制", "游戏重置");
            init();
        }

        if (gameStatus != GameStatus.OVER) {
            if (Gdx.input.isKeyJustPressed(Input.Keys.W) && offsetY != -Config.SNAKE_SIZE) {
                Gdx.app.debug("游戏控制", "上");
                offsetX = 0;
                offsetY = Config.SNAKE_SIZE;
            }
            if (Gdx.input.isKeyJustPressed(Input.Keys.S) && offsetY != Config.SNAKE_SIZE) {
                Gdx.app.debug("游戏控制", "下");
                offsetX = 0;
                offsetY = -Config.SNAKE_SIZE;
            }
            if (Gdx.input.isKeyJustPressed(Input.Keys.A) && offsetX != Config.SNAKE_SIZE) {
                Gdx.app.debug("游戏控制", "左");
                offsetY = 0;
                offsetX = -Config.SNAKE_SIZE;
            }
            if (Gdx.input.isKeyJustPressed(Input.Keys.D) && offsetX != -Config.SNAKE_SIZE) {
                Gdx.app.debug("游戏控制", "右");
                offsetY = 0;
                offsetX = Config.SNAKE_SIZE;
            }

            moveSpeedDelta += delta;
            if (moveSpeedDelta >= moveSpeed) {
                moveSpeedDelta = 0;
                snakeX += offsetX;
                snakeY += offsetY;
                snakeXY.insert(0, new Vector2(snakeX, snakeY));
                if (delSnakeTail) {
                    snakeXY.removeIndex(snakeXY.size - 1);
                }
                delSnakeTail = true;
            }
        }
    }

    public void eatFood() {
        if (foodX == snakeX && foodY == snakeY) {
            randomFood();
            delSnakeTail = false;
        }
    }

    @Override
    public void dispose() {
        snake.dispose();
        food.dispose();
        wall.dispose();
        batch.dispose();
    }
}
