import java.awt.Color;

public enum Tile {
    FLOOR((char)250, AsciiPanel.red),
    WALL((char)177, AsciiPanel.red),
    BOUNDS('x', AsciiPanel.brightBlack);

    private boolean visible;
    public boolean visible() {return visible;}

    private char glyph;
    public char glyph() { return glyph; }

    private Color color;
    public Color color() { return color; }

    Tile(char glyph, Color color){
        this.glyph = glyph;
        this.color = color;
        this.visible = true;
    }

    public boolean isDiggable() {
        return this == Tile.WALL;
    }
    public boolean isGround() {
        return this != WALL && this != BOUNDS;
    }
}