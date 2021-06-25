import javax.naming.CompositeName;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Random;

public class PlayScreen implements Screen {
    public static Random random = new Random();
    private World world;
    private int centerX;
    private int centerY;
    private int screenWidth;
    private int screenHeight;
    private boolean paused = false;
    private boolean isRunning = true;
    private int framesPerSecond = 32;
    private int timePerLoop = 1000000000 / framesPerSecond;
    public Creature player;
    public FieldOfView fov;
    private Screen subscreen;
    public static ArrayList<Message> messages;
    public static boolean HELP = true;

    //debugging thing
    public boolean reset = false;

    public PlayScreen(){
        //System.out.println("init playscreen");
        screenWidth = AsciiPanel.PORT_WIDTH;
        screenHeight = AsciiPanel.PORT_HEIGHT;
        createWorld();
        messages = new ArrayList<Message>();
        this.fov = new FieldOfView(world);
        Factory factory = new Factory(world, fov);

        createCreatures(factory);

        messages.add(new Message(Color.yellow,"Initialized", 500));
    }

    private void createWorld(){
        world = new WorldBuilder(300, 100)
                .makeCaves()
                .build();
    }

    private void createCreatures(Factory factory){
        player = factory.newPlayer(fov);
        for (int i = 0; i < 40; i++){
            factory.newFungus();
        }
        for (int i = 0; i < 40; i++){
            factory.newGiantBat();
        }
        for (int i = 0; i < 40; i++){
            factory.newBat();
        }
        for (int i = 0; i < 30; i++){
            factory.newRock();
        }
        for (int i = 0; i < 30; i++){
            factory.newSpear();
        }
        for (int i = 0; i < 30; i++){
            factory.newHat();
        }

    }

    public void update() {
        world.update();
        World.incTime();
    }

    public int getScrollX() { return Math.max(0, Math.min(player.x - screenWidth / 2, world.width() - screenWidth));  }

    public int getScrollY() { return Math.max(0, Math.min(player.y - screenHeight / 2, world.height() - screenHeight)); }

    private void displayTiles(AsciiPanel terminal, int left, int top) {
        fov.update(player.x, player.y, player.visionRadius());

        for (int x = 0; x < screenWidth; x++) {
            for (int y = 0; y < screenHeight; y++) {
                int wx = x + left; //left = space to left of screen
                int wy = y + top; //top = space above screen view
                if (player.canSee(wx, wy)) {
                    terminal.write(world.glyph(wx, wy), x, y, world.color(wx, wy));
                } else {
                    terminal.write(fov.tile(wx, wy).glyph(), x, y, Color.darkGray);
                }


                /*
                if (player.canSee(wx, wy)) {
                    Creature creature = world.creatureHere(wx, wy);
                    if (creature != null) {
                        terminal.write(creature.glyph(), creature.x - left, creature.y - top, creature.color());
                    } else {
                        terminal.write(world.glyph(wx, wy), x, y, world.color(wx, wy));
                    }
                } else {
                    terminal.write(fov.tile(wx, wy).glyph(), x, y, Color.darkGray);
                }

                 */

            }
            //System.out.println("creatures size " + world.Creatures().size());
        }
        for (Item item : world.items()) {
            if (item.x >= left && item.x < left + screenWidth) {
                if (item.y >= top && item.y < top + screenHeight) {
                    if (player.canSee(item.x, item.y)) {
                        terminal.write(item.glyph(), item.x - left, item.y - top, item.color());
                    }
                }
            }
            //System.out.println("creatures size " + world.Creatures().size());
        }
        for (Creature creature : world.Creatures()) {
            if (creature.x >= left && creature.x < left + screenWidth) {
                if (creature.y >= top && creature.y < top + screenHeight) {
                    if (player.canSee(creature.x, creature.y)) {
                        terminal.write(creature.glyph(), creature.x - left, creature.y - top, creature.color());
                    }
                }
            }
            //System.out.println("creatures size " + world.Creatures().size());
        }
    }

    private void displayTiles2(AsciiPanel terminal, int left, int top) {
        for (int x = 0; x < screenWidth; x++){
            for (int y = 0; y < screenHeight; y++){
                int wx = x + left;
                int wy = y + top;


                terminal.write(world.glyph(wx, wy), x, y, world.color(wx, wy));


                //System.out.println("creatures size " + world.Creatures().size());

            }
        }
        for (Creature creature : world.Creatures()) {
            if (creature.x >= left && (creature.x < left + screenWidth) && (creature.y >= top) && (creature.y < top + screenHeight)) {
                terminal.write(creature.glyph(), creature.x - left, creature.y - top, creature.color());
            }
        }
    }
    private void displayMessages(AsciiPanel terminal, ArrayList<Message> messages) {
        int size = messages.size();
        if (size > 10) {
            size = 10;
        }
        int top = screenHeight - size + 10;
        int yVert = Math.min(messages.size() - 1, 10);
        //System.out.println("yvert" + yVert);
        for (int i = messages.size() - 1; i >= 0 && i > (messages.size() - 11); i--){

            terminal.write(messages.get(i).string(), screenWidth, screenHeight + yVert - 10, messages.get(i).color(), Color.black);
            yVert--;
        }
        if (messages.size() > 10) {
            terminal.write(". . . ", screenWidth, top - size, Color.red, Color.black);
        }
        for (int j = 0; j < messages.size() - 1; j++) {
            Message message = messages.get(j);
            if (message.Active()) {
                //message.decay();
            } else {
                messages.remove(message);
                j--;
            }

        }

    }
    private void displayStats(AsciiPanel terminal) {
        double hpfraction = (player.hp() + 1.0) / player.hpMax();
        hpfraction *= 10;

        int hpfrac = (int) Math.round(hpfraction);

        for (int x = 0; x < hpfrac; x++) {
            terminal.write((char)250, AsciiPanel.PORT_WIDTH + x + 2, 10, Color.cyan, Color.cyan);

        }
        double cdfraction = (player.attackCooldown);
        cdfraction /= 3;

        int cdfrac = (int) Math.round(cdfraction);

        for (int x = 0; x < cdfrac; x++) {
            terminal.write((char)250, AsciiPanel.PORT_WIDTH/2 + x, screenHeight - 2, Color.white, Color.white);

        }
        String attackLine = "Attack: " + player.attackTotal();
        if (player.attackBonus() != 0) {
            attackLine = attackLine.concat(" (+" + player.attackBonus() + ")");
        }
        String defenseLine = "Defense: " + player.defenseTotal();
        if (player.defenseBonus() != 0) {
            defenseLine = defenseLine.concat(" (+" + player.defenseBonus() + ")");
        }
        String evasLine = "Evasion: " + player.evasionTotal();
        if (player.evasionBonus() != 0) {
            evasLine = evasLine.concat(" (+" + player.evasionBonus() + ")");
        }
        String accLine = "Accuracy: " + player.accuracyTotal();
        if (player.accuracyBonus() != 0) {
            accLine = accLine.concat(" (+" + player.accuracyBonus() + ")");
        }

        terminal.write("HP: " + player.hp() + "/" + player.hpMax(), AsciiPanel.PORT_WIDTH + 2, 11, Color.cyan);
        terminal.write(attackLine, AsciiPanel.PORT_WIDTH + 2, 12, Color.red);
        terminal.write(defenseLine, AsciiPanel.PORT_WIDTH + 2, 13, AsciiPanel.green);
        terminal.write(evasLine, AsciiPanel.PORT_WIDTH + 2, 14, Color.pink);
        terminal.write(accLine, AsciiPanel.PORT_WIDTH + 2, 15, Color.yellow);



    }

    public void displayOutput(AsciiPanel terminal) {
        if (!reset) {
            terminal.write("You are on the PLAY SCREEN", 1, 1);
            //System.out.println("display playscreen");
            terminal.writeCenter("press 'ESC' to die", 22);
            //  System.out.println(world.Creatures().size());
            int left = getScrollX();
            int top = getScrollY();

            displayTiles(terminal, left, top);
            displayMessages(terminal, messages);
            //terminal.write(player.glyph(), player.x - left, player.y - top, player.color());
            if (!paused && subscreen == null) {
                update();
            } else {
                terminal.write("*PAUSED*", 0, 0, Color.cyan, Color.white);
            }

            if (subscreen != null) {
                subscreen.displayOutput(terminal);
            }

            if (player.worn() != null) {
                terminal.write("Worn: " + player.worn().name(), screenWidth + 2, 5, Color.cyan);
            }
            if (player.equipped() != null) {
                terminal.write("Equipped: " + player.equipped().name(), screenWidth + 2, 6, Color.cyan);
            }
            int y = 1;
            if (HELP) {
                terminal.write("'H' - help", 0, y++, Color.white);
                terminal.write("'G' - pick up", 0, y++, Color.white);
                terminal.write("'P' - pause", 0, y++, Color.white);
                terminal.write("'T' - throw", 0, y++, Color.white);
                terminal.write("'E' - equip", 0, y++, Color.white);
                terminal.write("'Q' - stow", 0, y++, Color.white);

                terminal.write("'I' - drop", 0, y++, Color.white);
                terminal.write("'C' - consume", 0, y++, Color.white);
            }

            displayStats(terminal);
        } else {
            reset = false;
            for (int x = 0; x < screenWidth; x++) {
                for (int y = 0; y < screenHeight; y++) {
                    terminal.write((char)250, x, y, Color.black, Color.black);
                }
            }
        }
    }
    public Screen respondToUserInput(KeyEvent key) {
        if (!player.isAlive()) {
            return new EndScreen();
        }
        if (subscreen != null) {
            subscreen = subscreen.respondToUserInput(key);
        } else {
            //System.out.println("playscreen wait for input");
            switch (key.getKeyCode()) {

                case KeyEvent.VK_LEFT:
                case KeyEvent.VK_A:
                    if (!paused) {
                        player.moveBy(-1, 0);
                    }
                    break;
                case KeyEvent.VK_RIGHT:
                case KeyEvent.VK_D:
                    if (!paused) {
                        player.moveBy(1, 0);
                    }
                    break;
                case KeyEvent.VK_UP:
                case KeyEvent.VK_W:
                    if (!paused) {
                        player.moveBy(0, -1);
                    }
                    break;
                case KeyEvent.VK_DOWN:
                case KeyEvent.VK_S:
                    if (!paused) {
                        player.moveBy(0, 1);
                    }
                    break;
                case KeyEvent.VK_ESCAPE:
                case KeyEvent.VK_P:
                    paused = !paused;
                    break;
                case KeyEvent.VK_G:
                    //addRedMessage("You reach down to pick up an item");
                    //player.pickup();
                    int itemshere = world.itemsHereNeighbors(player.x, player.y).size();
                    if (itemshere == 1) {
                        player.pickupItem(world.itemsHereNeighbors(player.x, player.y).get(0));
                    } else if (itemshere > 0) {
                        subscreen = new PickScreen(player, world);
                    }
                    break;
                case KeyEvent.VK_I: subscreen = new DropScreen(player); break;
                case KeyEvent.VK_E: subscreen = new EquipScreen(player); break;
                case KeyEvent.VK_Q: subscreen = new StowScreen(player); break;
                case KeyEvent.VK_C: subscreen = new ConsumeScreen(player); break;

                case KeyEvent.VK_T:
                    if (player.hasItemInHand()) {
                        subscreen = new ThrowScreen(player, world);
                    } else {
                        messages.add(new Message(Color.red, "Equip an item first!", 500));
                    }
                    break;
                case KeyEvent.VK_H:
                    HELP = !HELP;
                    break;
                case KeyEvent.VK_SPACE:
                    for (Creature creature : world.creaturesNear(player, 1)) {
                        player.attack(creature);
                    }
                    break;
                case KeyEvent.VK_F1:
                    reset = true;
                    break;
            }
        }
        return this;
    }

    private void scrollBy(int mx, int my){
        centerX = Math.max(0, Math.min(centerX + mx, world.width() - 1));
        centerY = Math.max(0, Math.min(centerY + my, world.height() - 1));
    }

    public static void addRedMessage(String string) {
        messages.add(new Message(Color.red, string, 500));
    }

}