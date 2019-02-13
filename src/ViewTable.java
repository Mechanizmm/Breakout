import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ViewTable extends JLabel {

    private ModelTable model;
    private BufferedImage liveImg;
    private BufferedImage arrowImg;
    private BufferedImage shrinkWidthImg;

    private void setImg()
    {
        try
        {
            liveImg = ImageIO.read(new File("images/live.png"));
            arrowImg = ImageIO.read(new File("images/arrow.gif"));
            shrinkWidthImg = ImageIO.read(new File("images/shrinkwidth.png"));
        }
        catch ( IOException exc )
        {
            //TODO: Handle exception.
            System.out.println("Problem with loading image");
        }
    }

    ViewTable(ModelTable model) {
        this.model = model;

        setImg();
    }


    /** Źródło funkcji http://www.java2s.com/Tutorial/Java/0261__2D-Graphics/Centertext.htm **/
    private void drawCenteredString(String s, int w, int h, Graphics g) {
        FontMetrics fm = g.getFontMetrics();
        int x = (w - fm.stringWidth(s)) / 2;
        int y = (fm.getAscent() + (h - (fm.getAscent() + fm.getDescent())) / 2);
        g.drawString(s, x, y);
    }

    @Override
    public void paint(Graphics g) {
        drawBall(g);
        drawPaddle(g);
        drawBonuses(g);
        drawBricks(g);
        drawInterface(g);
    }

    private void drawBall(Graphics g)
    {
        g.fillArc(model.ball.getXpos(), model.ball.getYpos(), model.ball.getSizeX(), model.ball.getSizeX(), 0, 360);
    }

    private void drawPaddle(Graphics g)
    {
        g.fillRect(model.paddle.getXpos(), model.paddle.getYpos(), model.paddle.getSizeX(), model.paddle.getSizeY());
        g.setColor(Color.lightGray);
        g.fillRect(model.paddle.getXpos() + 2, model.paddle.getYpos() + 2, model.paddle.getSizeX() - 4, model.paddle.getSizeY() - 4);
    }

    private void drawBonuses(Graphics g)
    {
        g.drawImage(arrowImg, model.extendWidth.getXpos(), model.extendWidth.getYpos(), this);

        g.drawImage(shrinkWidthImg, model.shrinkWidth.getXpos(), model.shrinkWidth.getYpos(), this);

        g.setColor(Color.darkGray);
        g.fillArc(model.increaseSpeed.getXpos(), model.increaseSpeed.getYpos(), model.increaseSpeed.getSizeX(), model.increaseSpeed.getSizeY(), 0, 360);
        g.setColor(Color.RED);
        g.fillArc(model.increaseSpeed.getXpos() + 1, model.increaseSpeed.getYpos() + 1, model.increaseSpeed.getSizeX() - 2, model.increaseSpeed.getSizeY() - 2, 0, 360);

        g.setColor(Color.GREEN);
        g.fillArc(model.decreaseSpeed.getXpos(), model.decreaseSpeed.getYpos(), model.decreaseSpeed.getSizeX(), model.decreaseSpeed.getSizeY(), 0, 360);
    }

    private void drawBricks(Graphics g)
    {
        for(int i = 0; i < model.brick.length; i++) {
            if (!model.brick[i].isDestroyed()) {

                if(model.brick[i].getLives() > 1) {
                    g.setColor(Color.darkGray);
                    g.fillRect(model.brick[i].getXpos(), model.brick[i].getYpos(), model.brick[i].getSizeX(), model.brick[i].getSizeY());

                    g.setColor(Color.lightGray);
                    g.fillRect(model.brick[i].getXpos() + 5, model.brick[i].getYpos() + 5, model.brick[i].getSizeX() - 10, model.brick[i].getSizeY() - 10);
                }
                else
                {
                    g.setColor(Color.darkGray);
                    g.fillRect(model.brick[i].getXpos(), model.brick[i].getYpos(), model.brick[i].getSizeX(), model.brick[i].getSizeY());

                    g.setColor(Color.lightGray);
                    g.fillRect(model.brick[i].getXpos() + 1, model.brick[i].getYpos() + 1, model.brick[i].getSizeX() - 2, model.brick[i].getSizeY() - 2);
                }
            }
        }
    }

    private void drawInterface(Graphics g)
    {
        Font font = new Font("Verdana", Font.PLAIN, 22);
        g.setFont(font);
        g.setColor(Color.BLACK);

        if( model.isGameOver() )
        {
            drawCenteredString("GAME OVER", getWidth(), getHeight(), g);
            drawCenteredString("Naciśnij spację aby zagrać jeszcze raz", getWidth(), getHeight() + 100, g);
        }

        if( model.isVictory() )
        {
            drawCenteredString("VICTORY", getWidth(), getHeight(), g);
            drawCenteredString("Naciśnij spację aby zagrać jeszcze raz", getWidth(), getHeight() + 100, g);
        }

        if( !model.isRunning() && !model.isGameOver() && !model.isVictory())
        {
            drawCenteredString("Naciśnij spację aby rozpocząć", getWidth(), getHeight(), g);
        }

        for(int i = 0; i <= model.getLives(); i++) {
            g.drawImage(liveImg, getWidth() - (20*i), 10, this);
        }

        g.drawString("Punkty: " + model.getPoints(), 20, 20);
    }
}
