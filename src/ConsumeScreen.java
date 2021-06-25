public class ConsumeScreen extends SubScreen {

    public ConsumeScreen(Creature player) {
        super(player);
    }

    protected String getVerb() {
        return "consume";
    }

    protected boolean isAcceptable(Item item) {
        return item.nutrition() > 0;
    }

    protected Screen use(Item item) {
        player.quaff(item);
        return null;
    }

    @Override
    public void update() {

    }
}