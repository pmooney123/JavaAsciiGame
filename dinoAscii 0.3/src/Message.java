import java.awt.*;

public class Message {
    private Color color;
    private String string;
    private int lifespan;
    public String string() {
        return string;
    }
    public Color color() {
        return color;
    }

    public Message(Color color, String string, int lifespan) {
        this.color = color;
        this.string = string;
        this.lifespan = lifespan;
    }
    public boolean Active() {
        if (lifespan > 0) {
            return true;
        }
        return false;
    }
    public void decay() {
        lifespan--;
    }
}
