public class GiantBatAi extends CreatureAi {


    public GiantBatAi(Creature creature) {
        super(creature);
    }

    public void onUpdate(World world){

        if (creature.stamina >= creature.staminaMax){
            if (world.creaturesNear(creature, 1).size() > 0) {
                for (Creature other : world.creaturesNear(creature, 1)) {
                    if (other.glyph() == '@') {
                        creature.attack(other);
                    }
                }
            }
            wander();
            creature.stamina = 0;
        }

    }
}