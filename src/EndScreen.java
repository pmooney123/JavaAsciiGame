import java.awt.event.KeyEvent;

public class EndScreen implements Screen {

    public void displayOutput(AsciiPanel terminal) {
        terminal.write("THE GAME IS OVER", 1, 1);
        terminal.writeCenter("press 'enter' to restart", 22);
    }

    public Screen respondToUserInput(KeyEvent key) {
        return key.getKeyCode() == KeyEvent.VK_ENTER ? new PlayScreen() : this;
    }

    public void update() {

    }
}