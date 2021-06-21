import java.awt.event.KeyEvent;

public class DropScreen extends SubScreen {

    @Override
    protected String getVerb() {
        return "drop";
    }

    @Override
    protected boolean isAcceptable(Item item) {
        if (!player.inUse(item)) {
            return true;
        }
        return false;
    }

    @Override
    protected Screen use(Item item) {
        player.drop(item);
        return null;
    }

    public DropScreen(Creature player) {
        super(player);
    }

    @Override
    public void update() {

    }

}