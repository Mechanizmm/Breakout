import javax.swing.*;
import java.awt.*;

public class Sprite extends JFrame{
    protected int xpos;
    protected int ypos;
    protected int sizeX;
    protected int sizeY;
    protected int screenWidth = 600;
    protected int screenHeight = 800;

    protected void setXpos(int xpos) {
        this.xpos = xpos;
    }

    protected void setYpos(int ypos) {
        this.ypos = ypos;
    }

    protected int getXpos() {
        return xpos;
    }

    protected int getYpos() {
        return ypos;
    }

    protected int getSizeX() {
        return sizeX;
    }

    protected int getSizeY() {
        return sizeY;
    }

    protected Rectangle getRect() {
        return new Rectangle(xpos, ypos, sizeX, sizeY);
    }

}
