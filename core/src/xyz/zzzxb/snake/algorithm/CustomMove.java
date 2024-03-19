package xyz.zzzxb.snake.algorithm;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import xyz.zzzxb.snake.enums.Direction;
import xyz.zzzxb.snake.game.*;

import java.util.Arrays;

/**
 * zzzxb
 * 2024/3/19
 */
public class CustomMove extends AbstractMoveAlgo {
    private final Square square;
    public final static CustomMove INSTANCE = new CustomMove();
    private final Array<Position> algoPath = new Array<>();
    private final Array<Direction> algoDir = new Array<>();

    public CustomMove() {
        square = new Square(new Color(0, 0, 0, 0.1f), 16, 16, true);
    }

    @Override
    public Direction move(Wall wall, Snake snake, Food food) {
        if (algoPath.isEmpty() || algoPath.size == 0) {
            Position sp = snake.getPositions().first();
            Position fp = food.getPosition();
            Direction d = snake.getDirection();
            boolean succ = false;
            int option = 0;
            do {
                float x = sp.getX(), y = sp.getY();

                Gdx.app.log("new position ", x + ":" + y + " - " + fp.getX() + ":" + fp.getY());

                if (sp.getX() < fp.getX() && (option == 0 || option == 1) && d != Direction.LEFT) {
                    x += 16;
                    if (snake.getPositions().contains(new Position(x, y), false) || x < 16) {
                        option += 1;
                        continue;
                    }
                    algoDir.add(Direction.RIGHT);
                } else if (sp.getX() > fp.getX() && (option == 0 || option == 2) && d != Direction.RIGHT) {
                    x -= 16;
                    if (snake.getPositions().contains(new Position(x, y), false) || x < 16) {
                        option += 1;
                        continue;
                    }
                    algoDir.add(Direction.LEFT);
                } else if (sp.getY() < fp.getY() && (option == 0 || option == 3) && d != Direction.DOWN) {
                    y += 16;
                    if (snake.getPositions().contains(new Position(x, y), false) || y > 512) {
                        option += 1;
                        continue;
                    }
                    algoDir.add(Direction.UP);
                } else if (sp.getY() > fp.getY() && (option == 0 || option == 4) && d != Direction.UP) {
                    y -= 16;
                    if (snake.getPositions().contains(new Position(x, y), false) || y < 16) {
                        option += 1;
                        continue;
                    }
                    algoDir.add(Direction.DOWN);
                } else if (Math.abs(sp.getX() - wall.getWidth()) > Math.abs(sp.getX() - wall.getX()) && (option == 0 || option == 5) && d != Direction.RIGHT) {
                    x -= 16;
                    if (snake.getPositions().contains(new Position(x, y), false) || x < 16) {
                        option += 1;
                        continue;
                    }
                    algoDir.add(Direction.RIGHT);
                } else if (Math.abs(sp.getX() - wall.getWidth()) < Math.abs(sp.getX() - wall.getX()) && (option == 0 || option == 6) && d != Direction.LEFT) {
                    x += 16;
                    if (snake.getPositions().contains(new Position(x, y), false) || x > 512) {
                        option += 1;
                        continue;
                    }
                    algoDir.add(Direction.LEFT);
                } else if (Math.abs(sp.getY() - wall.getHeight()) > Math.abs(sp.getY() - wall.getY()) && (option == 0 || option == 7) && d != Direction.UP) {
                    y -= 16;
                    if (snake.getPositions().contains(new Position(x, y), false) || y < 16) {
                        option += 1;
                        continue;
                    }
                    algoDir.add(Direction.DOWN);
                } else if (Math.abs(sp.getY() - wall.getHeight()) < Math.abs(sp.getY() - wall.getY()) && (option == 0 || option == 8) && d != Direction.DOWN) {
                    y += 16;
                    if (snake.getPositions().contains(new Position(x, y), false) || y > 512) {
                        option += 1;
                        continue;
                    }
                    algoDir.add(Direction.UP);
                } else {
                    if (!snake.getPositions().contains(new Position(x, y + 16), false)) {
                        algoDir.add(Direction.UP);
                    } else if (!snake.getPositions().contains(new Position(x, y - 16), false)) {
                        algoDir.add(Direction.DOWN);
                    } else if (!snake.getPositions().contains(new Position(x + 16, y), false)) {
                        algoDir.add(Direction.RIGHT);
                    } else if (!snake.getPositions().contains(new Position(x - 16, y), false)) {
                        algoDir.add(Direction.LEFT);
                    }
                }

                option = 0;
                d = algoDir.peek();
                Position position = new Position(x, y);
                succ = !position.equals(fp);
                algoPath.add(position);
                sp = position;
            } while (succ);
        }

        Gdx.app.log("move path: ", String.valueOf(algoPath));
        Gdx.app.log("move dir: ", String.valueOf(algoDir));

        Direction first = Direction.RIGHT;
        if (!algoDir.isEmpty() && algoDir.size != 0) {
            first = algoDir.first();
            algoDir.removeIndex(0);
            algoPath.removeIndex(0);
        }
        return first;
    }

    @Override
    public void reset() {
        algoDir.clear();
        algoPath.clear();
    }

    @Override
    public void drawAuxiliaryLine(SpriteBatch batch) {
        if (!algoPath.isEmpty() && algoPath.size != 0) {
            for (Position p : algoPath) {
                square.draw(batch, p);
            }
        }
    }

    @Override
    public void dispose() {
        square.dispose();
    }
}
