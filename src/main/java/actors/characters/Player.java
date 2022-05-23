package actors.characters;

import dev.morphia.annotations.*;
import world.Location;

@Entity
public class Player extends Character {
    @Id
    String id;

    String name;

    @Reference
    Location location;

    public Player() {

    }


    public Location location() {
        return location;
    }

    public Player setLocation(Location location) {
        this.location = location;
        return this;
    }


    public String name() {
        return name;
    }

    public Player setName(String name) {
        this.name = name;
        return this;
    }
}
