import java.awt.*;
import java.awt.event.KeyEvent;

public class QuaffScreen extends SubScreen {

    @Override
    protected String getVerb() {
        return "quaff";
    }

    @Override
    protected boolean isAcceptable(Item item) {
       if (player.inUse(item)) {
            return true;
       }
       return false;
    }

    @Override
    protected Screen use(Item item) {
        PlayScreen.messages.add(new Message(Color.red, "Item " + "'"+item.name()+"'" + " stowed!", 500));
        player.quaff(item);
        return null;
    }

    public QuaffScreen(Creature player) {
        super(player);
    }

    @Override
    public void update() {

    }

}