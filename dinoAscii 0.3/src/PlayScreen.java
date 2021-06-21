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
    private boolean paused = true;
    private boolean isRunning = true;
    private int framesPerSecond = 32;
    private int timePerLoop = 1000000000 / framesPerSecond;
    public Creature player;
    public FieldOfView fov;
    public static ArrayList<Message> messages;

    public PlayScreen(){
        //System.out.println("init playscreen");
        screenWidth = AsciiPanel.PORT_WIDTH;
        screenHeight = AsciiPanel.PORT_HEIGHT;
        createWorld();
        messages = new ArrayList<Message>();
        this.fov = new FieldOfView(world);
        CreatureFactory creatureFactory = new CreatureFactory(world, fov);

        createCreatures(creatureFactory);


    }

    private void createWorld(){
        world = new WorldBuilder(120, 50)
                .makeCaves()
                .build();
    }

    private void createCreatures(CreatureFactory creatureFactory){
        player = creatureFactory.newPlayer();
        for (int i = 0; i < 8; i++){
            creatureFactory.newFungus();
        }
        for (int i = 0; i < 20; i++){
            creatureFactory.newBat();
        }

    }

    public void update() {
        world.update();
        world.incTime();
    }

    public int getScrollX() {
        return Math.max(0, Math.min(player.x - screenWidth / 2, world.width() - screenWidth));
    }

    public int getScrollY() { return Math.max(0, Math.min(player.y - screenHeight / 2, world.height() - screenHeight)); }

    private void displayTiles(AsciiPanel terminal, int left, int top) {
        fov.update(player.x, player.y, player.visionRadius());

        for (int x = 0; x < screenWidth; x++) {
            for (int y = 0; y < screenHeight; y++) {
                int wx = x + left;
                int wy = y + top;

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

        for (int i = size - 1; i > 0; i--){
            terminal.write(messages.get(i).string(), screenWidth, top + i - 10, messages.get(i).color(), Color.black);
        }
        if (messages.size() > 10) {
            terminal.write(". . . ", screenWidth, top - size, Color.red, Color.black);
        }
        for (int j = 0; j < messages.size() - 1; j++) {
            Message message = messages.get(j);
            if (message.Active()) {
                message.decay();
            } else {
                messages.remove(message);
                j--;
            }

        }

    }

    public void displayOutput(AsciiPanel terminal) {
        terminal.write("You are on the PLAY SCREEN", 1, 1);
        //System.out.println("display playscreen");
        terminal.writeCenter("press 'ESC' to die", 22);
      //  System.out.println(world.Creatures().size());
        int left = getScrollX();
        int top = getScrollY();

        displayTiles(terminal, left, top);
        displayMessages(terminal, messages);
        //terminal.write(player.glyph(), player.x - left, player.y - top, player.color());
        if (!paused) {
            update();
        } else {
            terminal.write("*PAUSED*", 0, 0, Color.cyan, Color.white);
        }
    }
    public Screen respondToUserInput(KeyEvent key) {

        //System.out.println("playscreen wait for input");
        switch (key.getKeyCode()){
            case KeyEvent.VK_ESCAPE: return new EndScreen();
            case KeyEvent.VK_LEFT:
            case KeyEvent.VK_A: if (!paused) {player.moveBy(-1, 0); }break;
            case KeyEvent.VK_RIGHT:
            case KeyEvent.VK_D: if (!paused) {player.moveBy(1, 0); }break;
            case KeyEvent.VK_UP:
            case KeyEvent.VK_W: if (!paused) {player.moveBy(0, -1); }break;
            case KeyEvent.VK_DOWN:
            case KeyEvent.VK_S: if (!paused) {player.moveBy(0, 1); }break;
            case KeyEvent.VK_P: paused = !paused; break;
        }

        return this;
    }
    private void scrollBy(int mx, int my){
        centerX = Math.max(0, Math.min(centerX + mx, world.width() - 1));
        centerY = Math.max(0, Math.min(centerY + my, world.height() - 1));
    }


}