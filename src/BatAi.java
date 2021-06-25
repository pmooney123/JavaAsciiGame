public class BatAi extends CreatureAi {


    public BatAi(Creature creature) {
        super(creature);
    }

    public void onUpdate(World world){
        if (creature.stamina >= creature.staminaMax){
            wander();
            creature.stamina = 0;
        }
    }
}