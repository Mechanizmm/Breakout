import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.Random;

class ModelTable {

    private Random rand = new Random();

    private static int NUM_OF_BRICKS = 35;

    Bonus extendWidth;
    Bonus shrinkWidth;
    Bonus increaseSpeed;
    Bonus decreaseSpeed;
    Ball ball;
    Paddle paddle;

    private int height;
    private boolean isGameOver = false;
    private boolean isVictory = false;
    private boolean isRunning = false;
    private int points = 0;
    private int lives = 3;


    Brick[] brick = new Brick[NUM_OF_BRICKS];
    int brickSpaceX = 65;
    int brickSpaceY = 35;

    ModelTable( int height ) {
        this.height = height;

        initGameObjects();
    }

    public void initGameObjects(){
        initBricks();
        ball = new Ball();
        paddle = new Paddle();
        extendWidth = new Bonus();
        shrinkWidth = new Bonus();
        increaseSpeed = new Bonus();
        decreaseSpeed = new Bonus();
    }

    public void initBricks() {

       /** LEVEL 1 **/

        int k = 0;

        for (int i = 0; i < 1; i++) {
            for (int j = 0; j < 7; j++) {
                brick[k] = new Brick(j * brickSpaceX + 80, i * brickSpaceY + 50, 2, 20);
                k++;
            }
        }

        for (int i = 2; i < 4; i++) {
            for (int j = 0; j < 7; j++) {
                brick[k] = new Brick(j * brickSpaceX + 80, i * brickSpaceY + 50, 2, 20);
                k++;
            }
        }

        for (int i = 5; i < 7; i++) {
            for (int j = 0; j < 7; j++) {
                brick[k] = new Brick(j * brickSpaceX + 80, i * brickSpaceY + 50, 1, 10);
                k++;
            }
        }
    }


    void update() {
        ball.move(isRunning);
        paddle.move(isRunning);
        extendWidth.move();
        shrinkWidth.move();
        increaseSpeed.move();
        decreaseSpeed.move();

        checkCollision();
        checkVictory();
    }

    private void checkCollision() {

        /** Zmienne definiujące wierzchołki kwadratu opisanego na BALL **/
        Point leftTop = new Point(ball.getXpos(), ball.getYpos());
        Point leftBottom = new Point(ball.getXpos(), ball.getYpos() + ball.getSizeX());
        Point rightTop = new Point(ball.getXpos() + ball.getSizeX(), ball.getYpos());
        Point rightBottom = new Point(ball.getXpos() + ball.getSizeX(), ball.getYpos() + ball.getSizeX());


        /** Sprawdź kolizję z PADDLE - jeśli jest odbij **/
        if (paddle.getRect().intersects(ball.getRect())) {
            ball.setVy(-ball.getVy());
            if( ( paddle.getRect().contains(leftTop) && paddle.getRect().contains(rightTop) ) || ( paddle.getRect().contains(leftBottom) && paddle.getRect().contains(rightBottom) ) )
            {
                ball.setVy(-ball.getVy());
            }

            if( ( paddle.getRect().contains(leftTop) && paddle.getRect().contains(leftBottom) ) || ( paddle.getRect().contains(rightTop) && paddle.getRect().contains(rightBottom) ) ) {
                ball.setVx(-ball.getVx());
            }
        }


        /** Sprawdź kolizję z BRICKS - jeśli jest odbij i wykonaj destroyBlock**/
        for (int k = 0; k < brick.length; k++) {

            if ((ball.getRect()).intersects(brick[k].getRect())) {

                brick[k].decreaseLives();

                if( ( brick[k].getRect().contains(leftTop) && brick[k].getRect().contains(rightTop) ) || ( brick[k].getRect().contains(leftBottom) && brick[k].getRect().contains(rightBottom) ) ) {
                    ball.setVy(-ball.getVy());
                    destroyBlock(k);
                }

                if( ( brick[k].getRect().contains(leftTop) && brick[k].getRect().contains(leftBottom) ) || ( brick[k].getRect().contains(rightTop) && brick[k].getRect().contains(rightBottom) ) ) {
                    ball.setVx(-ball.getVx());
                    destroyBlock(k);
                }
            }
        }

        /**Sprawdź czy BALL spadł - GAME OVER?**/
        if( ball.getYpos() >  height )
        {
            lives--;

            ball.reset();
            isRunning = false;
            ball.setVy(-ball.getVy());

            if( lives <= 0 )
            {
                setGameOver(true);
            }
        }

        /**Sprawdź czy złapano BONUS**/
        if(paddle.getRect().intersects(extendWidth.getRect()))
        {
            if(paddle.getSizeX() < 400)
                paddle.setSizeX(paddle.getSizeX() * 2);

            extendWidth.setDestroyed(true);
        }

        if(paddle.getRect().intersects(shrinkWidth.getRect()))
        {
            if(paddle.getSizeX() > 50)
                paddle.setSizeX(paddle.getSizeX() / 2);

            shrinkWidth.setDestroyed(true);
        }

        if(paddle.getRect().intersects(increaseSpeed.getRect()))
        {
            if((ball.getVy() < 6 && ball.getVy() > -6)) {
                ball.setVy(ball.getVy() * 2);
                ball.setVx(ball.getVx() * 2);
            }

            increaseSpeed.setDestroyed(true);
        }

        if(paddle.getRect().intersects(decreaseSpeed.getRect()))
        {
            if((ball.getVy() > 1 || ball.getVy() < -1)) {
                ball.setVy(ball.getVy() / 2);
                ball.setVx(ball.getVx() / 2);
            }

            decreaseSpeed.setDestroyed(true);
        }
    }

    private void destroyBlock(int k){

        points += brick[k].getValue(); //Dodaj punkty

        int n = rand.nextInt(20) + 1;

        switch (n) {

            case 1: createBonus(k, extendWidth);
                    break;
            case 2: createBonus(k, shrinkWidth);
                    break;
            case 3: createBonus(k, increaseSpeed);
                    break;
            case 4: createBonus(k, decreaseSpeed);
                    break;
        }

        brick[k].setDestroyed(true);
    }

    private void createBonus(int k, Bonus bonus) {

        if(bonus.isDestroyed()){
            bonus.setXpos(brick[k].getXpos() + brick[k].getSizeX()/2);
            bonus.setYpos(brick[k].getYpos() + brick[k].getSizeY()/2);
            bonus.setVy(2);

            bonus.setDestroyed(false);
        }

        if(bonus.getYpos() > height)
        {
            bonus.setDestroyed(true);
        }
    }

    private void checkVictory()
    {
        int j = 0;

        for(int i = 0; i < brick.length; i++)
        {
            if(brick[i].isDestroyed())
            {
                j++;
            }

            if(j == brick.length)
            {
                setVictory(true);
            }
        }
    }

    private void reset()
    {
        initGameObjects();
        setGameOver(false);
        setVictory(false);

        points = 0;
        lives = 3;
    }

    public int getPoints() {
        return points;
    }

    public int getLives() {
        return lives;
    }

    public boolean isRunning() {
        return isRunning;
    }

    public boolean isGameOver() {
        return isGameOver;
    }

    public boolean isVictory() {
        return isVictory;
    }

    private void setGameOver(boolean gameOver)
    {
        isGameOver = gameOver;
    }

    public void setVictory(boolean victory) {
        isVictory = victory;
    }

    public void keyPressed(KeyEvent e) {

        int key = e.getKeyCode();

        if (key == KeyEvent.VK_LEFT) {
            paddle.setVx(-3);
        }

        if (key == KeyEvent.VK_RIGHT) {
            paddle.setVx(3);
        }

        if (key == KeyEvent.VK_SPACE && ( !isGameOver() || !isVictory()) ) {
            isRunning = true;
        }

        if (key == KeyEvent.VK_SPACE && ( isGameOver() || isVictory()) ) {
            reset();
            isRunning = true;
        }

        /** Do TESTÓW **/
        if (key == KeyEvent.VK_X) {
            paddle.setSizeX(paddle.getSizeX()*2);
            ball.setVy(ball.getVy()*2);
            ball.setVx(ball.getVx()*2);
        }

        if (key == KeyEvent.VK_C) {
            paddle.setSizeX(paddle.getSizeX()/2);
        }
    }

    void keyReleased(KeyEvent e) {

        int key = e.getKeyCode();

        if (key == KeyEvent.VK_LEFT) {
            paddle.setVx(0);
        }

        if (key == KeyEvent.VK_RIGHT) {
            paddle.setVx(0);
        }
    }
}


