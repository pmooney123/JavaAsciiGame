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
        Creature bat = new Creature(world, 'b', AsciiPanel.yellow, 10, 10, 10, 10, "Cave bat");
        world.addAtEmptyLocation(bat);
        new BatAi(bat);
        return bat;
    }

    public Creature newPlayer(){
        Creature player = new Creature(world, '@', AsciiPanel.brightWhite, 10, 10, 100, 10, "Player");
        world.addAtEmptyLocation(player);
        new PlayerAi(player, fov);
        return player;
    }

    public Creature newFungus(){
        Creature fungus = new Creature(world, 'P', AsciiPanel.green, 10, 10, 10, 0, "Fungus plant");
        world.addAtEmptyLocation(fungus);
        new FungusAi(fungus);

        return fungus;
    }

    public Item newRock(){
        Item rock = new Item(',', AsciiPanel.yellow, "rock", false, true, 10, 0, 10);
        world.addAtEmptyLocation(rock);
        return rock;
    }
    public Item newHat(){
        Item rock = new Item('~', AsciiPanel.green, "hat", true, false, 0, 10, 0);
        world.addAtEmptyLocation(rock);
        return rock;
    }
    public Item newSpear(){
        Item rock = new Item('-', AsciiPanel.cyan, "spear", false, true, 20, 0, 20);
        world.addAtEmptyLocation(rock);
        return rock;
    }
}




