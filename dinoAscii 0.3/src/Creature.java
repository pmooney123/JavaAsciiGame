import java.awt.*;
import java.util.ArrayList;

public class Creature {
    private World world;
    private String name ="Default_name";
    public String name() {return name;}

    public int stamina = 0;
    public int staminaMax;

    //stats
    private int hp;
    private int hpMax;
    public boolean isAlive() {
        return hp > 0;
    }
    public int hp() {return hp;}
    public int hpMax() {return hpMax;}
    private int attack = 5;
    public int attack() {return attack;}
    private int defense = 2;
    private int evasion = 0; private int accuracy = 0;
    public int evasion() {return evasion;}; public int accuracy() {return accuracy;}
    public int defense() {return defense;}
    public int attackCooldown = 0;
    public boolean canAttack = true;
    public void attackCooldownTick() {
        attackCooldown--;
        if (attackCooldown <= 0) {
            attackCooldown = 0;
            canAttack = true;
        } else {
            canAttack = false;
        }

    }
    public void modifyHP(int amount) {
        if (amount <= 0) {
            PlayScreen.messages.add(new Message(Color.cyan, name + " took " + -1 * amount + " damage!", 500));
        } else {
            PlayScreen.messages.add(new Message(Color.green, name + " healed " + amount + " points", 500));
        }
        hp += amount;
        if (hp > hpMax) {
            hp = hpMax;
        }
    }

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
    public boolean hasItemInHand() {
        return equipped != null;
    }
    public boolean isWearingItem() { return worn != null;
    }

    public int x;
    public int y;

    private Inventory inventory;
    public Inventory inventory() { return inventory; }

    public int attackTotal() {
        if (hasItemInHand()) {
            return attack() + equipped().getAttackValue();
        } else {
            return attack();
        }
    }
    public int accuracyTotal() {
        if (hasItemInHand()) {
            return accuracy + equipped().getAccuracyValue();
        } else {
            return accuracy;
        }
    }
    public int defenseTotal() {
        if (isWearingItem()) {
            return defense + worn.getDefenseValue();
        } else {
            return defense;
        }
    }
    public int evasionBonus() {
        if (isWearingItem()) {
            return worn.getEvasionValue();
        } else {
            return 0;
        }
    }
    public int attackBonus() {
        if (hasItemInHand()) {
            return equipped().getAttackValue();
        } else {
            return 0;
        }
    }
    public int accuracyBonus() {
        if (hasItemInHand()) {
            return equipped().getAccuracyValue();
        } else {
            return 0;
        }
    }
    public int defenseBonus() {
        if (isWearingItem()) {
            return worn.getDefenseValue();
        } else {
            return 0;
        }
    }
    public int evasionTotal() {
        if (isWearingItem()) {
            return evasion + worn.getEvasionValue();
        } else {
            return evasion;
        }
    }
    private CreatureAi ai;
    public CreatureAi ai() { return ai; }
    public void setCreatureAi(CreatureAi ai) { this.ai = ai; }

    private int visionRadius = 15;
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

    public Creature(World world, char glyph, Color color, int attack, int defense, int hp, int stamina, String name, int evasion, int accuracy){
        this.world = world;
        this.glyph = glyph;
        this.color = color;
        this.inventory = new Inventory(20);
        this.hpMax = hp;
        this.hp = hp;
        this.attack = attack;
        this.staminaMax = stamina;
        this.name = name;
        this.defense = defense;
        this.evasion = evasion;
        this.accuracy = accuracy;
    }
    public void pickup(){

        Item item;
        if (world.itemsHere(x, y).size() > 0) {
            item = world.itemsHere(x, y).get(world.itemsHere(x, y).size() - 1);
            if (inventory.isFull() || item == null){
                PlayScreen.addRedMessage("Your inventory is full!");
            } else {
                PlayScreen.addRedMessage("You pick up '" + item.name() + "'");
                inventory.add(item);
                if ((item.holdable() && equipped == null) || (item.wearable() && worn == null)) {
                    equip(item);
                }
                world.remove(item);
            }
        }
    }
    public void pickupItem(Item item){

        if (inventory.isFull() || item == null){
            PlayScreen.addRedMessage("Your inventory is full!");
        } else {
            PlayScreen.addRedMessage("You pick up '" + item.name() + "'");
            inventory.add(item);
            if ((item.holdable() && equipped == null) || (item.wearable() && worn == null)) {
                equip(item);
            }
            world.remove(item);
        }
    }

    public void drop(Item item){
        PlayScreen.addRedMessage(name +" dropped a " + item.name());
        inventory.remove(item);
        world.addAtEmptySpace(item, x, y);
    }

    public void equip(Item item){
        PlayScreen.addRedMessage(name + " equipped a " + item.name());
        if (item.wearable()) {
            worn = item;
        } else if (item.holdable()) {
            equipped = item;
        }
    }
    public void stow(Item item){
        PlayScreen.addRedMessage(name + " you stowed a " + item.name());
        if (item.wearable()) {
            worn = null;
        } else if (item.holdable()) {
            equipped = null;
        }
    }
    public void quaff(Item item) {
        inventory.remove(item);
        modifyHP(item.nutrition());
        PlayScreen.messages.add(new Message(Color.green, name + " consumed '" + item.name() + "'", 500));
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
    public void moveBy(Point point){
        Creature other = world.creatureHere(x+point.x, y+point.y);

        if (other == null) {
            ai.onEnter(x + point.x, y + point.y, world.tile(x + point.x, y + point.y));
        }
        else {

        }
    }
    public void attack(Creature other) {
        if (canAttack) {
            int damage = attackTotal();
            damage -= other.defenseTotal();
            if (damage < 0) {
                damage = 0;
            }
            if (contestAccuracy(other)) {
                other.modifyHP(-damage);
            } else {
                PlayScreen.addRedMessage(name + " missed " + other.name + "!");
            }
            if (hasItemInHand()) {
                attackCooldown = equipped().getCooldownValue();
            } else {
                attackCooldown = 25;
            }
        }
    }
    public boolean contestAccuracy(Creature other) {
        int accuracy = accuracy();
        int evasion = other.evasion;
        if (hasItemInHand()) {
            accuracy += equipped().getAccuracyValue();
        }
        if (other.isWearingItem()) {
            evasion += other.worn().getEvasionValue();
        }
        double evasionmod = (evasion + 100);
        double accuracymod = (accuracy + 100);

        double chance = accuracymod / (evasionmod + 0.0);
        //System.out.println("Chance: " + chance);
        if ((Math.random()) > chance) {
            return false;
        }
        return true;
    }
    public boolean contestAccuracyRange(Creature other, double range) {
        int accuracy = accuracyTotal();
        int evasion = other.evasionTotal();

        double evasionmod = (evasion + range * 3 + 100);
        double accuracymod = (accuracy + 100);

        double chance = accuracymod / (evasionmod + 0.0);
        //System.out.println("Chance: " + chance);
        if ((Math.random()) > chance) {
            return false;
        }
        return true;
    }

    public void update(World world){
        ai.onUpdate(world);
    }

    public boolean canEnter(int wx, int wy) {
        return world.tile(wx, wy).isGround() && world.creatureHere(wx, wy) == null;
    }

    public void throwObject(Creature creature) {
        Item thrown = equipped();
        String thrownName = thrown.name();
        equipped = null;
        inventory.remove(thrown);
        double distance = Math.sqrt((creature.x - x)*(creature.x - x) + (creature.y - y)*(creature.y - y));
        if (contestAccuracyRange(creature, distance)) {
            creature.modifyHP(-thrown.getAttackValue());
            PlayScreen.addRedMessage(name + " threw " + thrown.name() + " at " + creature.name() + "");
        } else {
            PlayScreen.addRedMessage("Missed!");
        }

        if (Math.random() < 1.0) {
            world.items().add(thrown);
            thrown.setXY(creature.x, creature.y);
        }

        for (int j = 0; j < inventory.getItems().length; j++) {
            Item item = inventory.getItems()[j];
            if (item != null) {
                if (item.name().equals(thrownName)) {
                    equip(item);
                    PlayScreen.addRedMessage("Re-equipped " + item.name() +"!");
                    break;
                }
            }
        }
    }

}