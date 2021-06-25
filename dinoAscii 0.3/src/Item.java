import java.awt.Color;

public class Item {
    private boolean holdable;
    private boolean wearable;
    public boolean holdable() {return holdable;}
    public boolean wearable() {return wearable;}

    private int attackValue = 0;
    public int getAttackValue() {return attackValue;}
    private int defenseValue = 0;
    public int getDefenseValue() { return defenseValue;}
    private int accuracyValue = 0;
    public int getAccuracyValue() {return accuracyValue;}
    private int evasionValue = 0;
    public int getEvasionValue() {return evasionValue;}
    private int cooldownValue = 0;
    public int getCooldownValue() {return cooldownValue;}

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

    private int nutrition = 0;
    public int nutrition() {return nutrition;}
    public void nutrition(int x) {nutrition = x;}

    public Item(char glyph, Color color, String name, boolean wearable, boolean holdable, int att, int def, int acc, int eva, int cd) {
        this.glyph = glyph;
        this.color = color;
        this.name = name;
        this.wearable = wearable;
        this.holdable = holdable;
        this.attackValue = att;
        this.defenseValue = def;
        this.accuracyValue = acc;
        this.evasionValue = eva;
        this.cooldownValue = cd;
    }

}
