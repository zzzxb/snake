package xyz.zzzxb.snake.algorithm;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import xyz.zzzxb.snake.enums.Direction;
import xyz.zzzxb.snake.game.Food;
import xyz.zzzxb.snake.game.Position;
import xyz.zzzxb.snake.game.Snake;
import xyz.zzzxb.snake.game.Wall;

/**
 * zzzxb
 * 2024/3/19
 */
public abstract class AbstractMoveAlgo implements MoveAlgo {

    @Override
    public abstract Direction move(Wall wall, Snake snake, Food food);

    @Override
    public void reset() {
    }

    @Override
    public void drawAuxiliaryLine(SpriteBatch batch) {
    }

    @Override
    public void dispose() {
    }

    // 是否小于墙体边缘
    public boolean isHitWallL(Position head, Wall wall) { return isHitWallL(head, wall, 0); }
    public boolean isHitWallL(Position head, Wall wall, int spacing) { return head.getX() < wall.getX() * (spacing + 1); }

    public boolean isHitWallR(Position head, Wall wall) { return isHitWallR(head, wall, 0); }
    public boolean isHitWallR(Position head, Wall wall, int spacing) { return head.getX() > wall.getWidth() - (wall.getX() * spacing); }

    public boolean isHitWallU(Position head, Wall wall) { return isHitWallU(head, wall, 0); }
    public boolean isHitWallU(Position head, Wall wall, int spacing) { return head.getY() > wall.getHeight() - (wall.getY() * spacing); }

    public boolean isHitWallD(Position head, Wall wall) { return isHitWallD(head, wall, 0); }
    public boolean isHitWallD(Position head, Wall wall, int spacing) { return head.getY() < wall.getY() * (spacing + 1); }

    // 是否达到墙体边缘
    public boolean reachEdgeL(Position head, Wall wall) { return reachEdgeL(head, wall, 0); }
    public boolean reachEdgeL(Position head, Wall wall, int spacing) { return head.getX() == wall.getX() * (spacing + 1); }

    public boolean reachEdgeR(Position head, Wall wall) { return reachEdgeR(head, wall, 0);}
    public boolean reachEdgeR(Position head, Wall wall, int spacing) { return head.getX() == wall.getWidth() - (wall.getX() * spacing); }

    public boolean reachEdgeU(Position head, Wall wall) { return reachEdgeU(head, wall, 0); }
    public boolean reachEdgeU(Position head, Wall wall, int spacing) { return head.getY() == wall.getHeight() - (wall.getY() * spacing); }

    public boolean reachEdgeD(Position head, Wall wall) { return reachEdgeD(head, wall, 0); }
    public boolean reachEdgeD(Position head, Wall wall, int spacing) { return head.getY() == wall.getY() * (spacing + 1); }

    // 是否达到角落
    public boolean reachCornerLU(Position head, Wall wall){ return reachCornerLU(head, wall, 0);}
    public boolean reachCornerLU(Position head, Wall wall, int spacing){ return head.getX() == wall.getX() + (wall.getX() * spacing)  && head.getY() == wall.getHeight() - (wall.getY() * spacing);}
    public boolean reachCornerLD(Position head, Wall wall){ return reachCornerLD(head, wall, 0);}
    public boolean reachCornerLD(Position head, Wall wall, int spacing){ return head.getX() == wall.getX() + (wall.getX() * spacing) && head.getY() == wall.getY() + (wall.getY() * spacing);}
    public boolean reachCornerRU(Position head, Wall wall){ return reachCornerRU(head, wall, 0);}
    public boolean reachCornerRU(Position head, Wall wall, int spacing){ return head.getX() == wall.getWidth() - (wall.getX() * spacing) && head.getY() == wall.getHeight() - (wall.getY() * spacing);}
    public boolean reachCornerRD(Position head, Wall wall){ return reachCornerRD(head, wall, 0);}
    public boolean reachCornerRD(Position head, Wall wall, int spacing){ return head.getX() == wall.getWidth() - (wall.getX() * spacing)&& head.getY() == wall.getY() + (wall.getY() * spacing);}
}
