import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class PlayScreen implements Screen {
    private World world;
    private int centerX;
    private int centerY;
    private int screenWidth;
    private int screenHeight;
    private boolean isRunning = true;
    private int framesPerSecond = 1;
    private int timePerLoop = 1000000000 / framesPerSecond;
    Creature player;

    public PlayScreen(){
        screenWidth = AsciiPanel.PORT_WIDTH;
        screenHeight = AsciiPanel.PORT_HEIGHT;
        createWorld();

        CreatureFactory creatureFactory = new CreatureFactory(world);
        player = creatureFactory.newPlayer();
        createCreatures(creatureFactory);

    }

    private void createWorld(){
        world = new WorldBuilder(90, 31)
                .makeCaves()
                .build();
    }

    private void createCreatures(CreatureFactory creatureFactory){
        for (int i = 0; i < 8; i++){
            creatureFactory.newFungus();
        }
    }

    public void update() {

    }

    public int getScrollX() {
        return Math.max(0, Math.min(player.x - screenWidth / 2, world.width() - screenWidth));
    }

    public int getScrollY() { return Math.max(0, Math.min(player.y - screenHeight / 2, world.height() - screenHeight)); }

    private void displayTiles(AsciiPanel terminal, int left, int top) {
        for (int x = 0; x < screenWidth; x++){
            for (int y = 0; y < screenHeight; y++){
                int wx = x + left;
                int wy = y + top;

                if (world.visible(wx, wy)) {
                    terminal.write(world.glyph(wx, wy), x, y, world.color(wx, wy));
                }
                //System.out.println("creatures size " + world.Creatures().size());

            }
        }
        for (Creature creature : world.Creatures()) {
            if (creature.x >= left && (creature.x < left + screenWidth) && (creature.y >= top) && (creature.y < top + screenHeight)) {
                terminal.write(creature.glyph(), creature.x - left, creature.y - top, creature.color());
            }
        }
    }

    public void displayOutput(AsciiPanel terminal) {
        terminal.write("You are on the PLAY SCREEN", 1, 1);
        terminal.writeCenter("press 'ESC' to die", 22);
        int left = getScrollX();
        int top = getScrollY();

        displayTiles(terminal, left, top);
        //terminal.write(player.glyph(), player.x - left, player.y - top, player.color());
    }
    public Screen respondToUserInput(KeyEvent key) {
        switch (key.getKeyCode()){
            case KeyEvent.VK_ESCAPE: return new EndScreen();
            case KeyEvent.VK_LEFT:
            case KeyEvent.VK_H: player.moveBy(-1, 0); break;
            case KeyEvent.VK_RIGHT:
            case KeyEvent.VK_L: player.moveBy( 1, 0); break;
            case KeyEvent.VK_UP:
            case KeyEvent.VK_K: player.moveBy( 0,-1); break;
            case KeyEvent.VK_DOWN:
            case KeyEvent.VK_J: player.moveBy( 0, 1); break;
            case KeyEvent.VK_Y: player.moveBy(-1,-1); break;
            case KeyEvent.VK_U: player.moveBy( 1,-1); break;
            case KeyEvent.VK_B: player.moveBy(-1, 1); break;
            case KeyEvent.VK_N: player.moveBy( 1, 1); break;
        }
        world.update();
        return this;
    }
    private void scrollBy(int mx, int my){
        centerX = Math.max(0, Math.min(centerX + mx, world.width() - 1));
        centerY = Math.max(0, Math.min(centerY + my, world.height() - 1));
    }


}