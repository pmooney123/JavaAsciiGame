import javax.swing.JFrame;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class ApplicationMain extends JFrame implements KeyListener {
    private static final long serialVersionUID = 1060623638149583738L;
    private boolean isRunning;
    private int framesPerSecond = 60;
    private int timePerLoop = 1000000000 / framesPerSecond;
    public static AsciiPanel terminal;
    private Screen screen;

    public ApplicationMain(){
        super();
        terminal = new AsciiPanel(AsciiPanel.SCREEN_WIDTH, AsciiPanel.SCREEN_HEIGHT);
        add(terminal);
        pack();
        screen = new PlayScreen();
        addKeyListener(this);
        repaint();
    }       //button pressed -> adjust screen -> adjust camera -> paint world
            //time passed -> adjust screen, camera, update world -> time passed -> repeat

    public void repaint(){
        terminal.clear();
        screen.displayOutput(terminal);
        super.repaint();
    }
    public void run() {
        while (true) {
            long startTime = System.nanoTime();

            repaint();

            long endTime = System.nanoTime();

            long sleepTime = timePerLoop - (endTime-startTime);

            if (sleepTime > 0) {
                try {
                    Thread.sleep(sleepTime/1000000);
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
    }
    public void keyPressed(KeyEvent e) {
        //System.out.println("e " + e);
        screen = screen.respondToUserInput(e);
    }

    public void keyReleased(KeyEvent e) {

    }

    public void keyTyped(KeyEvent e) { }

    public static void main(String[] args) {
        ApplicationMain app = new ApplicationMain();
        app.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        app.setVisible(true);
        app.run();
    }
}