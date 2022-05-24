package world;

import actors.Actor;

import java.util.*;

public class Tile {
    private final List<Actor> actors = new ArrayList<>();

    public Tile place(final Actor actor) {
        actors.add(actor);
        return this;
    }

    public List<Actor> actors() {
        return actors;
    }
}
