
public class ThrowScreen extends TargetScreen {

    @Override
    protected String getVerb() {
        return "throw";
    }

    @Override
    protected boolean isAcceptable(Creature creature) {

            if (player.canSee(creature.x, creature.y) && !isPlayer(creature)) {
                return true;
            }
        return false;
    }

    @Override
    protected Screen use(Creature creature) {
        player.throwObject(creature);
        return null;
    }

    public ThrowScreen(Creature player, World world) {
        super(player, world);

    }

    @Override
    public void update() {

    }
}
