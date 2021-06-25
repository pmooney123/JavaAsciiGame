import java.awt.event.KeyEvent;
import java.util.ArrayList;

public abstract class TargetScreen implements Screen {

    protected Creature player;
    private String letters;
    protected World world;

    protected abstract String getVerb();
    protected abstract boolean isAcceptable(Creature creature);
    protected abstract Screen use(Creature creature);
    protected boolean isPlayer(Creature creature) {
        if (creature.glyph() == '@') {
            return  true;
        }
        return  false;
    }

    public TargetScreen(Creature player, World world){
        this.player = player;
        this.world = world;
        this.letters = "abcdefghijklmnopqrstuvwxyz";
    }

    private ArrayList<String> getList() {
        ArrayList<String> lines = new ArrayList<String>();

        //Item[] inventory = player.inventory().getItems();
        ArrayList<Creature> creatures = world.Creatures();
        ArrayList<Creature> creatures2 = new ArrayList<>();

        for (Creature creature : creatures) {
            if (player.canSee(creature.x, creature.y) && !isPlayer(creature)) {
                creatures2.add(creature);
            }
        }

        for (int i = 0; i < creatures2.size(); i++){
            Creature creature = creatures2.get(i);

            if (creature == null || !isAcceptable(creature)) {
                continue;
            }

            String line = letters.toUpperCase().charAt(i) + " - (" + creature.glyph() + ") " + creature.name() + " distance: " + getDistance(player, creature);

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
        terminal.write("Choose a target to " +
                getVerb() + " " +
                player.equipped().name() +
                " at?", 2, 23);

        terminal.repaint();
    }
    public Screen respondToUserInput(KeyEvent key) {
        char c = key.getKeyChar();

        ArrayList<Creature> creatures = world.Creatures();
        ArrayList<Creature> creatures2 = new ArrayList<>();

        for (Creature creature : creatures) {
            if (player.canSee(creature.x, creature.y) && !isPlayer(creature)) {
                creatures2.add(creature);
            }
        }

        if (letters.indexOf(c) > -1
                && creatures2.size() > letters.indexOf(c)
                && creatures2.get(letters.indexOf(c)) != null
                && isAcceptable(creatures2.get(letters.indexOf(c)))) {
            return use(creatures2.get(letters.indexOf(c)));
        }
        else if (key.getKeyCode() == KeyEvent.VK_ESCAPE){
            return null;
        }
        else{
            return this; }
    }

    public int getDistance(Creature creature, Creature creature2) {
        return (int) Math.round(Math.sqrt((creature2.x - creature.x )* (creature2.x - creature.x) + (creature2.y - creature.y) * (creature2.y - creature.y)));
    }
}