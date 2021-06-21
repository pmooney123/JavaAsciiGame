import java.awt.*;
import java.awt.event.KeyEvent;

public class EquipScreen extends SubScreen {

    @Override
    protected String getVerb() {
        return "equip";
    }

    @Override
    protected boolean isAcceptable(Item item) {
        if ((item.wearable() || item.holdable()) && !player.inUse(item)) {
            return true;
        }
        return false;
    }

    @Override
    protected Screen use(Item item) {
        PlayScreen.messages.add(new Message(Color.red, "Item equipped!", 500));
        player.equip(item);
        return null;
    }

    public EquipScreen(Creature player) {
        super(player);
    }

    @Override
    public void update() {

    }

}