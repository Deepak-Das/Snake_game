import javafx.scene.shape.Rectangle;

import java.util.List;
//this interface class is just to track my methods name this for my own understanding
public interface MoveHandler {
    public void moveLeft (Rectangle rect);
    public void moveRight (Rectangle rect);
    public void moveUp (Rectangle rect);
    public void moveDown (Rectangle rect);
    public void makeSnakeFood();
    public Rectangle makeSnakeTail();
    public void runningSnake(List<Rectangle> snakeSizeLs);
}
