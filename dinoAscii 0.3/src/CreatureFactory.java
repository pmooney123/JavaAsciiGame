import java.lang.reflect.Array;
import java.util.ArrayList;

public class CreatureFactory {
    private World world;
    private FieldOfView fov;
    public CreatureFactory(World world, FieldOfView fov){
        this.world = world;
        this.fov = fov;
    }

    public Creature newBat(){
        Creature bat = new Creature(world, 'b', AsciiPanel.yellow);
        world.addAtEmptyLocation(bat);
        new BatAi(bat);
        return bat;
    }

    public Creature newPlayer(){
        Creature player = new Creature(world, '@', AsciiPanel.brightWhite);
        world.addAtEmptyLocation(player);
        new PlayerAi(player, fov);
        return player;
    }

    public Creature newFungus(){
        Creature fungus = new Creature(world, 'P', AsciiPanel.green);
        world.addAtEmptyLocation(fungus);
        new FungusAi(fungus);

        return fungus;
    }
}




