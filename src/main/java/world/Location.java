package world;

import actors.characters.Character;
import actors.characters.*;
import dev.morphia.annotations.*;
import interactions.system.*;
import utilities.DbConnection;

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


    private List<Location> neighbours;


    public void enter() {
        neighbours = World.fetchLocation(name()).neighbours();
        DbConnection.instance().datastore().save(this);
        printf("Welcome To %s %n", name);
        updatePlayer();
        if (World.fetchLocation(name()) != null) {
            for (Location location : neighbours) {
                Command enter = new Command("travel to " + location.name(), "enter", location) {{
                    setState(State.HIDDEN);
                    setIsStateChanger(true);
                }};
                Menu.instance().addCommand(enter);
            }
        }
        else {
            System.out.println("WTF");
            System.exit(-1);
        }
        Command lookAround = new Command("look around", "lookAround", this) {{
            setState(Command.State.VISIBLE);
            setIsStateChanger(false);
        }};

        Menu.instance().addCommand(lookAround);
        Menu.instance().addCommand(new Command("Show Exits", "displayExits", this));

    }

    public void lookAround() {
        printf("As i look around %s, I see: %n", name);
        matrix.vicinity().forEach(actor -> {
            printf("%s %n", actor.name());
            actor.interact();
        });
    }

    public void displayExits(){
        System.out.printf("%nExits from %s are the following:%n",name);
        neighbours().forEach(l -> System.out.println(l.name()));
    }

    private void updatePlayer() {
        this.player = Menu.instance().player();
        player.setLocation(this);
        matrix.enter(0, 0);
        DbConnection.instance().datastore().save(player);
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
