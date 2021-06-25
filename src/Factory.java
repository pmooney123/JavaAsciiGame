import java.lang.reflect.Array;
import java.util.ArrayList;

public class Factory {
    private World world;
    private FieldOfView fov;
    public Factory(World world, FieldOfView fov){
        this.world = world;
        this.fov = fov;
    }

    public Creature newBat(){
        Creature bat = new Creature(world, 'b', AsciiPanel.yellow, 10, 2, 10, 10, "Cave bat", 50, 0);
        world.addAtEmptyLocation(bat);
        new BatAi(bat);
        return bat;
    }
    public Creature newGiantBat(){
        Creature bat = new Creature(world, 'B', AsciiPanel.red, 10, 2, 30, 10, "Giant cave bat", 20, -30);
        world.addAtEmptyLocation(bat);
        new GiantBatAi(bat);
        return bat;
    }
    public Creature newPlayer(FieldOfView fov){
        Creature player = new Creature(world, '@', AsciiPanel.brightWhite, 5, 0, 100, 10, "Player", 30, -20);
        world.addAtEmptyLocation(player);
        new PlayerAi(player, fov);
        return player;
    }

    public Creature newFungus(){
        Creature fungus = new Creature(world, 'P', AsciiPanel.green, 10, 0, 10, 0, "Fungus plant", -100, -100);
        world.addAtEmptyLocation(fungus);
        new FungusAi(fungus);

        return fungus;
    }

    public Item newRock(){
        Item rock = new Item(',', AsciiPanel.yellow, "rock", false, true, 10, 0, 20, 0, 100);
        world.addAtEmptyLocation(rock);
        return rock;
    }
    public Item newHat(){
        Item rock = new Item('~', AsciiPanel.green, "hat", true, false, 0, 5, 0, 30, 100);
        world.addAtEmptyLocation(rock);
        return rock;
    }
    public Item newSpear(){
        Item rock = new Item('-', AsciiPanel.cyan, "spear", false, true, 20, 0, 40, 0, 100);
        world.addAtEmptyLocation(rock);
        return rock;
    }
    public Item newCorpse(Creature creature){
        Item corpse = new Item('%', creature.color(), creature.name() + " corpse", false, false, 0, 0, 0, 0, 100);
        corpse.setXY(creature.x, creature.y);
        world.items().add(corpse);
        return corpse;
    }

}




