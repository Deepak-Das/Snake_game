import javafx.scene.shape.Rectangle;

import java.util.List;

public interface MoveHandler {
    public void moveLeft (Rectangle rect);
    public void moveRight (Rectangle rect);
    public void moveUp (Rectangle rect);
    public void moveDown (Rectangle rect);
    public void makeSnakeFood();
    public Rectangle makeSnakeTail();
    public void runningSnake(List<Rectangle> snakeSizeLs);
}
