import java.awt.*;
import java.util.ArrayList;

class CreatureAi {
    protected Creature creature;

    public CreatureAi(Creature creature){
        this.creature = creature;
        this.creature.setCreatureAi(this);
    }

    public void wander(){
        int mx = (int)(Math.random() * 3) - 1;
        int my = (int)(Math.random() * 3) - 1;
        creature.moveBy(mx, my);
    }

    public void onEnter(int x, int y, Tile tile){
        if (tile.isGround()){
            creature.x = x;
            creature.y = y;
        } else {
            //PlayScreen.messages.add(new Message(Color.red,creature.name() + "bumped into wall", 500));
        }
    }

    public void onUpdate(World world) {

    }
    public boolean canSee(int wx, int wy) {
        if ((creature.x-wx)*(creature.x-wx) + (creature.y-wy)*(creature.y-wy) >= creature.visionRadius()*creature.visionRadius()) {
            return false;
        }

        for (Line it = new Line(creature.x, creature.y, wx, wy); it.hasNext(); ) {
            Point p = it.next();
            if (creature.tile(p.x, p.y).isGround() || p.x == wx && p.y == wy) {
                continue;
            }

            return false;
        }

        return true;
    }

    public void restoreStamina() {
        creature.stamina++;
        if (creature.stamina > creature.staminaMax) {
            creature.stamina = creature.staminaMax;
        }
    }



}


