package xyz.zzzxb.snake.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import xyz.zzzxb.snake.App;
import xyz.zzzxb.snake.algorithm.MoveAlgoFactory;
import xyz.zzzxb.snake.enums.CtrlState;
import xyz.zzzxb.snake.enums.Direction;
import xyz.zzzxb.snake.enums.GameState;

/**
 * zzzxb
 * 2024/3/19
 */
public class GameInfo {
    private final BitmapFont font;
    private long beginTime = 0, endTime = 0;
    private int score;
    private GameState gameState;
    private CtrlState ctrlState;

    public GameInfo() {
        TextureRegion tr = new TextureRegion(new Texture("pt_mono.png"));
        font = new BitmapFont(Gdx.files.internal("pt_mono.fnt"), tr);
        init();
    }

    public void init() {
        beginTime = 0;
        endTime = 0;
        score = 0;
        gameState = GameState.NEWBORN;
        ctrlState = CtrlState.MANUAL;
        MoveAlgoFactory.algo(ctrlState).reset();
    }

    public  void drawGameInfo(SpriteBatch batch, Snake snake) {
        font.draw(batch, time(snake, gameState), 560, 530);
        font.draw(batch, "Score: " + score, 560, 500);
        font.draw(batch, "FPS: " + Gdx.graphics.getFramesPerSecond(), 680, 500);

        font.draw(batch, "Speed: " + snake.getSpeedLevel(), 780, 500);
        font.draw(batch, "Suicide: " + snake.isSuicideState(), 780, 480);
        font.draw(batch, (ctrlState == CtrlState.MANUAL ? "*1 " : "  1 ") + CtrlState.MANUAL, 760, 460);
        font.draw(batch, (ctrlState == CtrlState.AUTO_LOOP ? "*2 " : "  2 ") + CtrlState.AUTO_LOOP, 760, 440);
        font.draw(batch, (ctrlState == CtrlState.CUSTOM_ALGO ? "*3 " : "  3 ") + CtrlState.CUSTOM_ALGO, 760, 420);

        if (gameState == GameState.NEWBORN) {
            font.draw(batch, "Control the direction to start the game.", 545, 300);
        } else if (gameState == GameState.GAME_OVER) {
            font.draw(batch, "Game Over!", 682, 300);
            font.draw(batch, "Enter \"R\" Reset Game!", 625, 280);
        } else {
            font.draw(batch, snake.getDirection().getDESC(), 700, 300);
        }
        font.draw(batch, "1(Slow/-) ~ 9(Fast/+)", 620, 60);
        font.draw(batch, "UP/W DOWN/S LEFT/A RIGHT/D", 620, 40);
    }

    private String time(Snake snake, GameState gameState) {
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

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int incrScore() {
        return (score += 1);
    }

    public GameState getGameState() {
        return gameState;
    }

    public boolean gameStateEq(GameState gameState) {
        return this.gameState == gameState;
    }

    public void setGameState(GameState gameState) {
        this.gameState = gameState;
    }

    public CtrlState getCtrlState() {
        return ctrlState;
    }

    public void setCtrlState(CtrlState ctrlState) {
        this.ctrlState = ctrlState;
        MoveAlgoFactory.algo(this.ctrlState).reset();
    }



    public void dispose() {
        font.dispose();
    }
}
