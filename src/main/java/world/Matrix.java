package world;

import actors.Actor;
import actors.characters.Player;
import dev.morphia.annotations.Transient;
import interactions.system.Menu;

import java.util.*;

public class Matrix {

    @Transient
    final List<Point> points = new ArrayList<>();
    PlayerPoint playerPoint;

    public Matrix() {
    }

    public Tile getTile(int x, int y) {
        Point point = points.stream().filter(p -> p.x == x && p.y == y).findFirst().orElse(null);
        return point != null ? point.tile : null;
    }

    public List<Actor> getActors() {
        List<Actor> result = new ArrayList<>();
        for (Point point : points) {
            result.addAll(point.tile.actors());
        }
        return result.isEmpty() ? null : result;
    }

    public boolean addTile(Tile tile, int x, int y) {
        boolean success;
        if (getTile(x, y) == null) {
            points.add(new Point(x, y).setTile(tile));
            success = true;
        }
        else {
            success = false;
        }
        return success;
    }

    public void enter(final int x, final int y) {
        Point point;
        if (getTile(x, y) == null) {
            point = new Point(x, y).setTile(new Tile());
            points.add(point);
        }
        else {
            point = getPoint(x, y);
        }
        playerPoint = new PlayerPoint().setPoint(point);
    }

    private Point getPoint(final int x, final int y) {
        return points.stream().filter(p -> p.x == x && p.y == y).findFirst().orElse(null);
    }

    private static class PlayerPoint {
        Player player = Menu.instance().player();

        public Point point() {
            return point;
        }

        public PlayerPoint setPoint(final Point point) {
            PlayerPoint.point = point;
            return this;
        }

        static Point point;

        enum Direction {
            NORTH, EAST, SOUTH, WEST
        }

        public void move(Direction direction) {
            switch (direction) {
                case NORTH -> System.out.println("walk north");
                case EAST -> System.out.println("walk east");
                case SOUTH -> System.out.println("walk south");
                case WEST -> System.out.println("walk west");
            }
        }
    }

    private static class Point {
        public Point setTile(final Tile tile) {
            this.tile = tile;
            return this;
        }

        Tile tile;
        int x;
        int y;

        public Point(final int x, final int y) {

        }

        public Tile tile() {
            return tile;
        }
    }

    public List<Actor> vicinity() {
        return playerPoint.point().tile().actors();
    }

    public Actor findInVicinity(String name){
        return vicinity().stream().filter(actor -> actor.name().equals(name)).findFirst().orElse(null);
    }


}
