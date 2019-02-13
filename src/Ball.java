import java.awt.*;

public class Ball extends Sprite{

    private int vx = 3;
    private int vy = -3;

    Ball(){
        sizeX = 17;
        reset();
    }

    public void move(boolean isRunning)
    {
        if(isRunning) {
            xpos = xpos + vx;
            if (xpos < 0 || xpos > screenWidth - sizeX)
                vx = -vx;

            ypos = ypos + vy;
            if (ypos < 0)
                vy = -vy;
        }
    }

    /** Wysrodkuj ball przy starcie. **/
    public void reset()
    {
        xpos = screenWidth/2 - sizeX/2;
        ypos = screenHeight - sizeX*2;
    }

    public int getVy() {
        return vy;
    }

    public void setVy(int vy) {
        this.vy = vy;
    }

    public int getVx() {
        return vx;
    }

    public void setVx(int vx) {
        this.vx = vx;
    }

    protected Rectangle getRect() {
        return new Rectangle(xpos, ypos, sizeX, sizeX);
    }
}
