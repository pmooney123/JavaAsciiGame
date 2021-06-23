import java.awt.Color;

public class Item {
    private boolean holdable;
    private boolean wearable;
    public boolean holdable() {return holdable;}
    public boolean wearable() {return wearable;}

    private int attackValue;
    public int getAttackValue() {return attackValue;}
    private int defenseValue;
    public int getDefenseValue() { return defenseValue;}
    private int throwValue;
    public int getThrowValue() {return throwValue;}

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


    public Item(char glyph, Color color, String name, boolean wearable, boolean holdable, int attackValue, int defenseValue, int throwValue){
        this.glyph = glyph;
        this.color = color;
        this.name = name;
        this.wearable = wearable;
        this.holdable = holdable;
        this.attackValue = attackValue;
        this.defenseValue = defenseValue;
        this.throwValue = throwValue;
    }

}
