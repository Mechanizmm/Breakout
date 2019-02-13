public class Paddle extends Sprite {

    private int vx = 0;

    Paddle(){
        sizeX = 100;
        sizeY = 10;
        ypos = 300;

        xpos = screenWidth/2 - sizeX/2;     /** Wysrodkuj paddle przy starcie. **/
        ypos = screenHeight - sizeY*2;
    }

    public void setVx(int vx) {
        this.vx = vx;
    }

    public void setSizeX(int sizeX) {
        this.sizeX = sizeX;
    }

    public void move(boolean isRunning) {
        if (isRunning) {
            xpos = xpos + vx;

            if (xpos <= 0) {
                xpos = 0;
            }

            if (xpos >= screenWidth - sizeX) {
                xpos = screenWidth - sizeX;
            }
        }
    }
}
