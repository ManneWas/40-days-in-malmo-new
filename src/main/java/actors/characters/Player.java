package actors.characters;

import dev.morphia.annotations.Entity;
import dev.morphia.annotations.Id;
import dev.morphia.annotations.Reference;
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

    private void travelTo(String place) {
        Location destination;
        if ((destination = this.location.neighbours().stream().filter(location -> location.name().equals(place)).findFirst().orElse(null)) != null) {
            location = destination;
        }
        else {
            System.out.printf("No exit found to: %s", place);
        }
    }

    public String name() {
        return name;
    }

    public Player setName(String name) {
        this.name = name;
        return this;
    }
}
