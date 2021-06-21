import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

public class World {
    private final Tile[][] tiles;
    private final int width;
    public int width() { return width; }
    private static int time = 0;
    public static int time() {return time;}
    public static void incTime() {time++;}
    private ArrayList<Creature> creatures;
    public ArrayList<Creature> Creatures() { return creatures; }
    private final int height;
    public int height() { return height; }

    public World(Tile[][] tiles){
        this.tiles = tiles;
        this.width = tiles.length;
        this.height = tiles[0].length;
        this.creatures = new ArrayList<>();
    }

    public Tile tile(int x, int y){
        if (x < 0 || x >= width || y < 0 || y >= height)
            return Tile.BOUNDS;
        else
            return tiles[x][y];
    } //tile getter

    public char glyph(int x, int y){
        return tile(x, y).glyph();
    } //get tile char

    public void dig(int x, int y, double digChance) {
        //if (tile(x,y).isDiggable() && Math.random() < digChance) {
            tiles[x][y] = Tile.FLOOR;
            PlayScreen.messages.add(new Message(Color.red, "dug a tile", 100));
        //}
    }

    public Color color(int x, int y){
        return tile(x, y).color();
    } //get tile color

    public boolean visible(int x, int y) { return tile(x, y).visible();}

    public void addAtEmptyLocation(Creature creature){
        int x;
        int y;

        do {
            x = (int)(Math.random() * width);
            y = (int)(Math.random() * height);
        }
        while (!tile(x,y).isGround() || creatureHere(x,y) != null);

        creature.x = x;
        creature.y = y;
        creatures.add(creature);
    }
    public Creature creatureHere(int x, int y){
        if (creatures.size() > 0) {
            for (Creature c : creatures) {
                if (c.x == x && c.y == y) {
                    return c;
                }
            }
        }
        return null;
    }

    public void remove(Creature other) {
        creatures.remove(other);
    }

    public void update(){
        ArrayList<Creature> toUpdate = new ArrayList<Creature>(creatures);
        for (Creature creature : toUpdate){
            creature.update(this);
            creature.ai().restoreStamina();
        }
    }

}