import javax.swing.*;

public class Animator implements Runnable
{
    private ModelTable model;
    private ViewTable view;

    Animator(ModelTable model, ViewTable view){
        this.model = model;
        this.view = view;
    }

    public void run() {
        while (true)
        {
            model.update();

            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    view.repaint();
                }
            });

            try {
                Thread.sleep(10);
            } catch (InterruptedException ignored) {}
        }
    }
}
