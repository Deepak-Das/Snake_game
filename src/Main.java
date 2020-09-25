import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;


public class Main extends Application implements MoveHandler {

    static int SIZE = 25, MOVE = 25, XMAX = 25 * SIZE, YMAX = 25 * SIZE, SCORE = 0;
    static int[][] MESH = new int[XMAX / 25][YMAX / 25];
    static boolean LEFT = false, RIGHT = true, UP = false, DOWN = false, GAMEOVER = false;
    static List<Rectangle> snakeSize = new ArrayList<>();
    static Rectangle food = new Rectangle(10 * MOVE, 15 * MOVE, SIZE, SIZE), headRect;
    static int preX, preY;
    static Pane pane;
    static Label soreCount;


    int color = 1;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        pane = new Pane();
        Stage stage = new Stage();
        Scene scene = new Scene(pane, XMAX + 150, YMAX);
        headRect = new Rectangle(MOVE * 5, MOVE * 5, 25, 25);
        headRect.setFill(Color.GREEN);
        snakeSize.add(headRect);
        MESH[(int) (food.getY() / 25)][(int) (food.getX() / 25)] = 2;
        food.setFill(Color.RED);


        Line line = new Line(XMAX, 0, XMAX, YMAX);


        Label score = new Label("SCORE");
        score.setFont(Font.font("", FontWeight.BOLD, 30));
        score.setStyle("-fx-font: bold");
        score.setStyle("-fx-text-fill: forestgreen");

        soreCount = new Label(String.valueOf(SCORE));
        soreCount.setFont(Font.font("", FontWeight.BOLD, 24));
        soreCount.setStyle("-fx-font: bold");
        soreCount.setStyle("-fx-text-fill: darkred");

        Label gameOver = new Label("GAME\nOVER");
        gameOver.setTextFill(Color.RED);
        gameOver.setFont(Font.font("", FontWeight.BOLD, 40));
        gameOver.setVisible(false);
        gameOver.setLayoutX(XMAX / 2 - 25);
        gameOver.setLayoutY(YMAX / 3);
        gameOver.toFront();


        VBox vBox = new VBox(10);

        vBox.getChildren().addAll(score, soreCount);
        vBox.setLayoutX(XMAX + 10);
        vBox.setLayoutY(50);
        vBox.setAlignment(Pos.CENTER);


        stage.setScene(scene);

        scene.setOnKeyPressed(event -> {
            KeyCode key = event.getCode();
            if (!GAMEOVER) {

                switch (key) {
                    case RIGHT:
                        if (LEFT)
                            break;
                        RIGHT = true;
                        LEFT = false;
                        UP = false;
                        DOWN = false;
                        break;
                    case LEFT:
                        if (RIGHT)
                            break;
                        RIGHT = false;
                        LEFT = true;
                        UP = false;
                        DOWN = false;
                        break;
                    case UP:
                        if (DOWN)
                            break;
                        RIGHT = false;
                        LEFT = false;
                        UP = true;
                        DOWN = false;
                        break;
                    case DOWN:
                        if (UP)
                            break;
                        RIGHT = false;
                        LEFT = false;
                        UP = false;
                        DOWN = true;
                }
            }
        });

        Timer timer = new Timer();
        TimerTask snakeWalk = new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(() -> {
                    if (!GAMEOVER) {
                        if (LEFT)
                            moveLeft(headRect);
                        else if (RIGHT)
                            moveRight(headRect);
                        else if (UP)
                            moveUp(headRect);
                        else if (DOWN)
                            moveDown(headRect);
                    } else {
                        gameOver.setVisible(true);
                    }

                });

            }
        };


        timer.schedule(snakeWalk, 300, 300);

        pane.getChildren().addAll(headRect, food, line, gameOver, vBox);
        stage.show();



    }

    @Override
    public void moveLeft(Rectangle rect) {
        if (rect.getX() / 25 == -1) {
            GAMEOVER = true;
        } else if (rect.getX() / 25 == 0) {
            runningSnake(snakeSize);
            rect.setX(rect.getX() - 25);
            GAMEOVER = true;
        } else if (MESH[(int) (rect.getY() / 25)][(int) (rect.getX() / 25) - 1] == 1) {
            //TODO: code if head bit  body
            runningSnake(snakeSize);
            rect.setX(rect.getX() - 25);
            GAMEOVER = true;
        } else if (rect.getX() - 25 > -1 && MESH[(int) (rect.getY() / 25)][(int) (rect.getX() / 25) - 1] != 2) {
            try {

                MESH[(int) (rect.getY() / 25)][(int) (rect.getX() / 25)] = 0;
                runningSnake(snakeSize);
                rect.setX(rect.getX() - 25);
            } catch (Exception ignored) {
            }
            MESH[(int) (rect.getY() / 25)][(int) (rect.getX() / 25)] = 1;
        } else {
            MESH[(int) (food.getY() / 25)][(int) (food.getX() / 25)] = 0;
            makeSnakeFood();
            MESH[(int) (food.getY() / 25)][(int) (food.getX() / 25)] = 2;
        }

    }

    @Override
    public void moveRight(Rectangle rect) {

        if (rect.getX() / 25 == 25) {
            GAMEOVER = true;
        } else if (rect.getX() / 25 == 24) {
            runningSnake(snakeSize);
            runningSnake(snakeSize);
            rect.setX(rect.getX() + 25);
            GAMEOVER = true;
        } else if (MESH[(int) (rect.getY() / 25)][(int) (rect.getX() / 25) + 1] == 1) {
            //TODO: code if head bit  body
            runningSnake(snakeSize);
            rect.setX(rect.getX() + 25);
            GAMEOVER = true;
        } else if (MESH[(int) (rect.getY() / 25)][(int) (rect.getX() / 25) + 1] != 2) {
            try {

                MESH[(int) (rect.getY() / 25)][(int) (rect.getX() / 25)] = 0;
                runningSnake(snakeSize);
                rect.setX(rect.getX() + 25);
            } catch (Exception ignored) {
            }
            MESH[(int) (rect.getY() / 25)][(int) (rect.getX() / 25)] = 1;
        } else {
            MESH[(int) (food.getY() / 25)][(int) (food.getX() / 25)] = 0;
            makeSnakeFood();
            MESH[(int) (food.getY() / 25)][(int) (food.getX() / 25)] = 2;

        }
    }

    @Override
    public void moveUp(Rectangle rect) {
        if (rect.getY() / 25 == -1) {
            System.out.println("game over");

            GAMEOVER = true;

        } else if (rect.getY() / 25 == 0) {
            runningSnake(snakeSize);
            rect.setY(rect.getY() - 25);
        } else if (MESH[(int) (rect.getY() / 25) - 1][(int) (rect.getX() / 25)] == 1) {
            runningSnake(snakeSize);
            rect.setY(rect.getY() - 25);
            GAMEOVER = true;

        } else if (rect.getY() - 25 > -1 && MESH[(int) (rect.getY() / 25) - 1][(int) (rect.getX() / 25)] != 2) {
            try {

                MESH[(int) (rect.getY() / 25)][(int) (rect.getX() / 25)] = 0;
                runningSnake(snakeSize);
                rect.setY(rect.getY() - 25);
            } catch (Exception ignored) {
            }
            MESH[(int) (rect.getY() / 25)][(int) (rect.getX() / 25)] = 1;
        } else {
            MESH[(int) (food.getY() / 25)][(int) (food.getX() / 25)] = 0;
            makeSnakeFood();
            MESH[(int) (food.getY() / 25)][(int) (food.getX() / 25)] = 2;

        }

    }

    @Override
    public void moveDown(Rectangle rect) {

        if (rect.getY() / 25 == 25) {
            System.out.println("game over");
            GAMEOVER = true;

        } else if (rect.getY() / 25 == 24) {
            runningSnake(snakeSize);
            rect.setY(rect.getY() + 25);
        } else if (MESH[(int) (rect.getY() / 25) + 1][(int) (rect.getX() / 25)] == 1) {
            runningSnake(snakeSize);
            rect.setY(rect.getY() + 25);
            GAMEOVER = true;
        } else if (rect.getY() + 25 < YMAX && MESH[(int) (rect.getY() / 25) + 1][(int) (rect.getX() / 25)] != 2) {
            try {

                MESH[(int) (rect.getY() / 25)][(int) (rect.getX() / 25)] = 0;
                runningSnake(snakeSize);
                rect.setY(rect.getY() + 25);
            } catch (Exception ignored) {
            }
            MESH[(int) (rect.getY() / 25)][(int) (rect.getX() / 25)] = 1;

        } else {
            MESH[(int) (food.getY() / 25)][(int) (food.getX() / 25)] = 0;

            makeSnakeFood();
            MESH[(int) (food.getY() / 25)][(int) (food.getX() / 25)] = 2;

        }

    }

    @Override
    public void makeSnakeFood() {
        int radomeX = (int) (Math.random() * 25);
        int radomeY = (int) (Math.random() * 25);
        if (MESH[radomeX][radomeY] != 1) {
            food.setX(radomeX * MOVE);
            food.setY(radomeY * MOVE);
            Rectangle tail = makeSnakeTail();
            if (color % 2 == 0)
                tail.setFill(Color.SADDLEBROWN);
            else
                tail.setFill(Color.YELLOW);
            color++;
            snakeSize.add(tail);
            pane.getChildren().add(tail);

        } else makeSnakeFood();
    }

    @Override
    public Rectangle makeSnakeTail() {
        SCORE = SCORE + 10;
        soreCount.setText(String.valueOf(SCORE));

        int x = (int) snakeSize.get(snakeSize.size() - 1).getX();
        int y = (int) snakeSize.get(snakeSize.size() - 1).getY();
        return new Rectangle(x, y, 25, 25);
    }


    @Override
    public void runningSnake(List<Rectangle> snakeSizeLs) {
        preX = (int) headRect.getX();
        preY = (int) headRect.getY();
        int curX, curY;

        for (int i = 1; i <= snakeSizeLs.size() - 1; i++) {
            curX = (int) snakeSizeLs.get(i).getX();
            curY = (int) snakeSizeLs.get(i).getY();
            try {
                MESH[curY / 25][curX / 25] = 0;
                MESH[preY / 25][preX / 25] = 1;
            } catch (Exception ignored) {
            }
            snakeSizeLs.get(i).setX(preX);
            snakeSizeLs.get(i).setY(preY);
            preX = curX;
            preY = curY;
        }
    }

}
