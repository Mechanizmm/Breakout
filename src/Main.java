import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Main {

    private JFrame f;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    Main window = new Main();
                    window.f.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the application.
     */
    public Main() {
        initialize();
    }

    /**
     * Initialize the contents of the frame.
     */
    private void initialize() {
        f = new JFrame();
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JPanel p = new JPanel();
        p.setPreferredSize(new Dimension(600, 800)); /** Przy zmianie - zmienić wartości w Sprite.java */
        f.add(p);
        f.pack();
        f.setLocationRelativeTo(null);

            ModelTable model = new ModelTable(p.getHeight());
            ViewTable view = new ViewTable(model);
            Animator a = new Animator(model, view);

        f.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                model.keyPressed(e);
            }

            @Override
            public void keyReleased(KeyEvent e) {
                model.keyReleased(e);
            }
        });

            f.add(view);
            f.setContentPane(view);
            Thread t = new Thread(a);
            t.start();
        }

    }


