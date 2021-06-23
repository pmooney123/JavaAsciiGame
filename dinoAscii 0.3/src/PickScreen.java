import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class PickScreen implements Screen {

    protected Creature player;
    private String letters;
    protected World world;
    protected String getVerb() {
        return "pick up";
    }
    protected boolean isAcceptable(Item item) {
        return true;
    }
    protected Screen use(Item item) {
        player.pickupItem(item);
        return null;
    }

    public PickScreen(Creature player, World world){
        this.player = player;
        this.world = world;
        this.letters = "abcdefghijklmnopqrstuvwxyz";
    }
    private ArrayList<String> getList() {
        ArrayList<String> lines = new ArrayList<String>();
        Item[] items = world.itemsHere(player.x, player.y).toArray(new Item[0]);

        for (int i = 0; i < items.length; i++) {
            Item item = items[i];

            if (item == null || !isAcceptable(item)) {
                continue;
            }

            String line = letters.toUpperCase().charAt(i) + " - (" + item.glyph() + ") " + item.name();

            lines.add(line);
        }
        return lines;
    }

    public void displayOutput(AsciiPanel terminal) {
        ArrayList<String> lines = getList();

        int y = 23 - lines.size();
        int x = 4;

        if (lines.size() > 0)
            terminal.clear(' ', x, y, 20, lines.size());

        for (String line : lines){
            terminal.write(line, x, y++);
        }

        terminal.clear(' ', 0, 23, 80, 1);
        terminal.write("What would you like to " + getVerb() + "?", 2, 23);

        terminal.repaint();
    }
    public Screen respondToUserInput(KeyEvent key) {
        char c = key.getKeyChar();

        Item[] items = world.itemsHere(player.x, player.y).toArray(new Item[0]);

        if (letters.indexOf(c) > -1
                && items.length > letters.indexOf(c)
                && items[letters.indexOf(c)] != null
                && isAcceptable(items[letters.indexOf(c)])) {
            return use(items[letters.indexOf(c)]);
        }
        else if (key.getKeyCode() == KeyEvent.VK_ESCAPE){
            return null;
        }
        else{
            return this; }
    }

    @Override
    public void update() {

    }
}