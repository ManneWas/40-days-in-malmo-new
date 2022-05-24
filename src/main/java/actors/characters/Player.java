package actors.characters;

import actors.Actor;
import dev.morphia.annotations.*;
import interactions.combat.Combat;
import world.Location;

@Entity
public class Player extends Character {
    @Id
    String id;



    @Reference
    Location location;

    public Player setInFocus(final Actor inFocus) {
        this.inFocus = inFocus;
        return this;
    }

    @Transient
    private Actor inFocus;

    public Player() {

    }


    public Location location() {
        return location;
    }

    public Player setLocation(Location location) {
        this.location = location;
        return this;
    }


    public Actor inFocus() {
        return inFocus;
    }

    public void fight(){
        Combat combat = new Combat();
        combat.setResponder((Character) inFocus).setInstigator(this);
    }
}
