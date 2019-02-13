public class Brick extends Sprite{


    private int value;
    private int lives;

    private boolean destroyed;


    Brick(int xpos, int ypos, int lives, int value)
    {
        this.xpos = xpos;
        this.ypos = ypos;

        this.lives = lives;
        this.value = value;

        sizeX = 60;
        sizeY = 30;

        destroyed = false;
    }

    public boolean isDestroyed()
    {
        return destroyed;
    }

    public void setDestroyed(boolean var)
    {
        if(lives < 1) {
            destroyed = var;

            xpos = -sizeX;
            ypos = -sizeY;
        }
    }

    public int getLives() {
        return lives;
    }

    public void decreaseLives() {
        lives--;
    }

    public int getValue() {
        return value;
    }
}
