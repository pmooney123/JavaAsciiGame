import java.awt.*;

public class Creature {
    private World world;
    private String name ="Bob";
    public String name() {return name;}

    public int x;
    public int y;

    private CreatureAi ai;
    public CreatureAi ai() { return ai; }
    public void setCreatureAi(CreatureAi ai) { this.ai = ai; }
    
    private int visionRadius = 25;
    public int visionRadius() { return visionRadius; }

    public boolean canSee(int wx, int wy){
        return ai.canSee(wx, wy);
    }
    public Tile tile(int wx, int wy) {
        return world.tile(wx, wy);
    }


    private char glyph;
    public char glyph() { return glyph; }

    private Color color;
    public Color color() { return color; }

    public Creature(World world, char glyph, Color color){
        this.world = world;
        this.glyph = glyph;
        this.color = color;
    }


    public void dig(int wx, int wy) {

        world.dig(wx, wy, 0.1);
    }

    public void moveBy(int mx, int my){
        Creature other = world.creatureHere(x+mx, y+my);

        if (other == null) {
            ai.onEnter(x + mx, y + my, world.tile(x + mx, y + my));
        }
        else {

        }
    }

    public void attack(Creature other){
        PlayScreen.messages.add(new Message(Color.cyan, "Enemy slain", 500));
        world.remove(other);
    }

    public void update(World world){
        ai.onUpdate(world);
    }

    public boolean canEnter(int wx, int wy) {
        return world.tile(wx, wy).isGround() && world.creatureHere(wx, wy) == null;
    }

}