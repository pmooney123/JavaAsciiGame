import java.awt.Color;

public class Item {
    private boolean holdable;
    private boolean wearable;
    public boolean holdable() {return holdable;}
    public boolean wearable() {return wearable;}

    public int x;
    public int y;
    public void setXY(int x, int y) {
        this.x = x;
        this.y = y;
    }
    private char glyph;
    public char glyph() { return glyph; }

    private Color color;
    public Color color() { return color; }

    private String name;
    public String name() { return name; }

    public Item(char glyph, Color color, String name){
        this.glyph = glyph;
        this.color = color;
        this.name = name;
        wearable = false;
        holdable = false;
    }
    public Item(char glyph, Color color, String name, boolean wearable, boolean holdable){
        this.glyph = glyph;
        this.color = color;
        this.name = name;
        this.wearable = wearable;
        this.holdable = holdable;
    }
    public Item(char glyph, Color color, String name, int x, int y){
        this.glyph = glyph;
        this.color = color;
        this.name = name;
        this.x = x;
        this.y = y;
        wearable = false;
        holdable = false;
    }
}
