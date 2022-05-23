package world;

import actors.characters.Player;
import dev.morphia.annotations.*;
import interactions.*;

import java.util.*;

@Entity
public class Location {
    @Id
    private String id;

    private String name;

    Player player;

    @Transient
    Character character;

    public List<Location> neighbours() {
        return neighbours;
    }


    private final List<Location> neighbours;

    public Location() {
        neighbours = new ArrayList<>();
    }

    public void enter() {
        updatePlayer();
        for (Location location : neighbours) {
            Menu.instance().commands().add(new Command("travel to " + location.name(), "enter", location).setState(Command.State.VISIBLE));
            Menu.instance().commands().add(new Command("alt travel to " + location.name(), "alternate_enter", location).setState(Command.State.VISIBLE));
        }
        printf("Welcome To %s %n", name);
    }

    public void alternate_enter(Character character){
        this.character = character;
        for (Location location : neighbours) {
            Menu.instance().commands().add(new Command("travel to " + location.name(), "enter", location).setState(Command.State.VISIBLE));
            Menu.instance().commands().add(new Command("alt travel to " + location.name(), "alternate_enter", location).setState(Command.State.VISIBLE));
        }
    }

    private void updatePlayer() {
        this.player = Menu.instance().player();
        player.setLocation(this);
    }

    public void showNeighbours() {
        for (Location location : neighbours) {
            printf("Can enter: %s with method", location.name());
        }
    }

    private void printf(String string, Object... args) {
        System.out.printf(string, args);
    }

    public String name() {
        return name;
    }

    public Location setName(String name) {
        this.name = name;
        return this;
    }


}
