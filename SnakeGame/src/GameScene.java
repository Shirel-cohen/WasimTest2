import javax.swing.*;
import java.awt.*;

public class GameScene extends JPanel {
    private Snake snake;
    private Food food;




    public GameScene(){
        this.setBackground( new Color(32,32,32));
        this.setDoubleBuffered(true);
        this.snake = new Snake();
        this.food = new Food();
        this.mainGameLoop();
    }
    public void paint(Graphics graphics) {
        super.paint(graphics);
            this.snake.paint(graphics);
        this.food.paint(graphics);

    }


    private void mainGameLoop() {

        new Thread(() -> {
            int increaseSpeed = 50;
            int foodEaten = 0;
            while (true) {
                if (snake.isAlive()) {
                    snake.move();
                }
                checkCollisionWithLimits();
                    if (collision(snake, food)){

//                        here we should increase the body of the snake  && add new Food  !
                        snake.increaseSnakeBody();
                        this.food.newFood();
                        foodEaten++;
                }

                repaint();
                if (foodEaten == 2) {
                    increaseSpeed = 30;
                }

                try {

                    Thread.sleep(increaseSpeed);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public Snake getSnake() {
        return snake;
    }

    public Food getFood() {
        return food;
    }

    private boolean collision (Snake snake, Food food){
        int snakeHeadX = snake.getBody()[0].getX();
        int snakeHeadY = snake.getBody()[0].getY();

        Rectangle snakeRectangle = new Rectangle(snakeHeadX, snakeHeadY, Definitions.BODY_SIZE, Definitions.BODY_SIZE);
        Rectangle foodRectangle = new Rectangle(food.getX(), food.getY(), Definitions.FOOD_SIZE, Definitions.FOOD_SIZE);
        return snakeRectangle.intersects(foodRectangle);
    }
    private void checkCollisionWithLimits() {
        //check if head collides with Body
        Body[] snakeBody = this.snake.getBody();
        int snakeHeadX = snakeBody[0].getX();
        int snakeHeadY = snakeBody[0].getY();

        for (int i = 1; i <snakeBody.length-1; i++) {
            if ((snakeHeadX == snakeBody[i].getX()) && (snakeHeadY == snakeBody[i].getY())) {
                this.snake.setAlive(false);
            }
        }


        if (snakeHeadX < 0) {
            this.snake.setAlive(false);
        }

        if (snakeHeadX > Definitions.WINDOW_WIDTH) {
            this.snake.setAlive(false);
        }

        if (snakeHeadY < 0) {
            this.snake.setAlive(false);
        }

        if (snakeHeadY > Definitions.WINDOW_HEIGHT) {
            this.snake.setAlive(false);
        }

    }

    public void move () {

    }





























}
