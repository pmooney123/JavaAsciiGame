import java.util.ArrayList;

public class PlayerAi extends CreatureAi {

    private final FieldOfView fov;
    public PlayerAi(Creature creature, FieldOfView fov) {
        super(creature);
        this.fov = fov;
    }
    @Override
    public void onEnter(int x, int y, Tile tile){
        if (creature.stamina >= 0) {
            creature.stamina = 0;
            if (tile.isGround()) {
                creature.x = x;
                creature.y = y;
            } else if (tile.isDiggable()) {
                creature.dig(x, y);

            }
        }
    }

    public boolean canSee(int wx, int wy) {
        return fov.isVisible(wx, wy);
    }
}