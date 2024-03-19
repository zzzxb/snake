package xyz.zzzxb.snake.algorithm;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import xyz.zzzxb.snake.enums.Direction;
import xyz.zzzxb.snake.game.Food;
import xyz.zzzxb.snake.game.Position;
import xyz.zzzxb.snake.game.Snake;
import xyz.zzzxb.snake.game.Wall;

/**
 * zzzxb
 * 2024/3/18
 */
public class LoopMove extends AbstractMoveAlgo {
    public final static LoopMove INSTANCE = new LoopMove();
    boolean firstMeet;

    @Override
    public Direction move(Wall wall, Snake snake, Food food) {
        Position head = snake.getHead();
        Direction choosingDir = snake.getDirection();

        if (!firstMeet) {
            firstMeet = true;
            choosingDir = (head.getY() / wall.getY()) % 2 == 0 ? Direction.RIGHT : Direction.LEFT;
        } else if (reachCornerLD(head, wall) && snake.directionEq(Direction.LEFT) ||
                reachCornerRD(head, wall) && snake.directionEq(Direction.RIGHT)) {
            choosingDir = Direction.UP;
        } else if (reachCornerRU(head, wall) && snake.directionEq(Direction.UP)) {
            choosingDir = Direction.LEFT;
        } else if (reachCornerLU(head, wall) && snake.directionEq(Direction.UP)) {
            choosingDir = Direction.RIGHT;
        } else if (reachEdgeL(head, wall, 1)) {
            if (!reachEdgeD(head, wall) && snake.directionEq(Direction.LEFT)) choosingDir = Direction.DOWN;
            else if (snake.directionEq(Direction.DOWN)) choosingDir = Direction.RIGHT;
        } else if (reachEdgeR(head, wall)) {
            if (snake.directionEq(Direction.RIGHT)) choosingDir = Direction.DOWN;
            else if (snake.directionEq(Direction.DOWN)) choosingDir = Direction.LEFT;
        }

        return choosingDir;
    }

    @Override
    public void reset() {
        firstMeet = false;
    }
}
