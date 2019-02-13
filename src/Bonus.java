public class Bonus extends Sprite {

    private int vy = 0;
    boolean destroyed;

    Bonus(){
        sizeX = 20;
        sizeY = 20;

        xpos = - sizeX;
        ypos = - sizeY;

        destroyed = true;
    }

    public void setVy(int vy) {
        this.vy = vy;
    }

    public void move()
    {
        ypos = ypos + vy;
    }

    public boolean isDestroyed()
    {
        return destroyed;
    }

    public void setDestroyed(boolean var)
    {
        destroyed = var;

        if(var) {
            xpos = -sizeX;
            ypos = -sizeY;
        }
    }
}
