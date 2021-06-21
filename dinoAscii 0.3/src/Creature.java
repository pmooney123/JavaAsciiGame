import java.awt.*;

public class Creature {
    private World world;
    private String name ="Bob";
    public String name() {return name;}

    private Item equipped;
    public Item equipped() {return equipped;}
    private Item worn;
    public Item worn() {return worn;}
    public boolean inUse(Item item) {
        if (item == worn() || item == equipped()) {
            return true;
        }
        return false;
    }

    public int x;
    public int y;

    private Inventory inventory;
    public Inventory inventory() { return inventory; }

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
        this.inventory = new Inventory(20);
    }
    public void pickup(){

        Item item;
        if (world.itemsHere(x, y).size() > 0) {
            item = world.itemsHere(x, y).get(0);
            if (inventory.isFull() || item == null){
                PlayScreen.addRedMessage("Your inventory is full!");
            } else {
                PlayScreen.addRedMessage("You pick up '" + item.name() + "'");
                inventory.add(item);
                world.remove(item);
            }
        }


    }

    public void drop(Item item){
        PlayScreen.addRedMessage("You dropped a " + item.name());
        inventory.remove(item);
        world.addAtEmptySpace(item, x, y);
    }

    public void equip(Item item){
        PlayScreen.addRedMessage("You equipped a " + item.name());
        if (item.wearable()) {
            worn = item;
        } else if (item.holdable()) {
            equipped = item;
        }
    }
    public void quaff(Item item){
        PlayScreen.addRedMessage("You quaffed a " + item.name());
        if (item.wearable()) {
            worn = null;
        } else if (item.holdable()) {
            equipped = null;
        }
    }
    public void equipold(Item item){
        PlayScreen.addRedMessage("You equipped a " + item.name());
        if (item.wearable()) {
            if (worn != null) {
                inventory.add(worn);
            }
            inventory.remove(item);
            worn = item;
        } else if (item.holdable()) {
            if (equipped != null) {
                inventory.add(equipped);
            }
            inventory.remove(item);
            equipped = item;
        }
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