public class BatAi extends CreatureAi {
    private int stamina = 10;
    private int mstamina = 10;

    public BatAi(Creature creature) {
        super(creature);
    }

    public void onUpdate(World world){
        wander();
    }
}