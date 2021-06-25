import java.util.Iterator;

public class FieldOfView {
    private World world;
    private int depth;

    private boolean[][] visible;

    public boolean isVisible(int x, int y) {
        boolean check1 = x >= 0;
        boolean check2 = y >= 0;
        boolean check3 = x < visible.length;
        boolean check4 = y < visible[0].length;
        boolean check5 = visible[x][y];
        //System.out.println(check1 + " " + check2 + " " + check3 + " " + check4 + " " + check5);
        return check1 && check2 && check3 && check4 && check5;
    }

    private Tile[][] tiles;

    public Tile tile(int x, int y) {
        return tiles[x][y];
    }

    public FieldOfView(World world) {
        this.world = world;
        this.visible = new boolean[world.width()][world.height()];
        this.tiles = new Tile[world.width()][world.height()];

        for (int x = 0; x < world.width(); x++) {
            for (int y = 0; y < world.height(); y++) {
                tiles[x][y] = Tile.UNKNOWN;
            }
        }
    }

    public void update(int wx, int wy, int r) {
        visible = new boolean[world.width()][world.height()];

        for (int x = -r; x < r; x++) {
            for (int y = -r; y < r; y++) {
                if (x * x + y * y > r * r) {

                    continue;
                }


                if (wx + x < 0 || wx + x >= world.width() || wy + y < 0 || wy + y >= world.height()){
                    continue;
                }

                for (Iterator<Point> it = new Line(wx, wy, wx + x, wy + y).iterator(); it.hasNext(); ) {
                    Point p = it.next();
                    Tile tile = world.tile(p.x, p.y);
                    visible[p.x][p.y] = true;
                    tiles[p.x][p.y] = tile;

                    if (!tile.isGround())
                        break;
                }
            }
        }
    }
}