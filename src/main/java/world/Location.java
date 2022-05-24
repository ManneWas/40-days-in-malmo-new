package world;

import actors.characters.Character;
import actors.characters.*;
import dev.morphia.annotations.*;
import interactions.system.*;

import java.util.*;

@Entity
public class Location {
    @Id
    private String id;

    private String name;

    @Transient
    Matrix matrix = new Matrix();


    @Transient
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

            Command enter = new Command("travel to " + location.name(), "enter", location) {{
                setState(Command.State.VISIBLE);
                setIsStateChanger(true);
            }};

            Menu.instance().commands().add(enter);
        }
        Command lookAround = new Command("look around", "lookAround", this) {{
            setState(Command.State.VISIBLE);
            setIsStateChanger(false);
        }};
        printf("Welcome To %s %n", name);
        Menu.instance().addCommand(lookAround);
    }

    public void lookAround() {
            printf("As i look around %s, I see: %n",name);
            matrix.vicinity().forEach(actor -> printf("%s %n", actor.name()));
    }

    private void updatePlayer() {
        this.player = Menu.instance().player();
        player.setLocation(this);
        matrix.enter(0, 0);
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


    public Matrix matrix() {
        return matrix;
    }
}
