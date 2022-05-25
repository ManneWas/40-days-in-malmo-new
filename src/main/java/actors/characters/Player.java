package actors.characters;

import actors.Actor;
import dev.morphia.annotations.Entity;
import dev.morphia.annotations.Id;
import dev.morphia.annotations.Reference;
import dev.morphia.annotations.Transient;
import interactions.combat.Combat;
import inventories.Backpack;
import world.Location;

@Entity
public class Player extends Character {
	@Id
	String id;
	int time = 0;
	@Transient
	private final Backpack backpack;

	@Reference
	Location location;

	public void passTime()
	{
		time++;
	}

	public Player setInFocus(final Actor inFocus)
	{
		this.inFocus = inFocus;
		return this;
	}

	@Transient
	private Actor inFocus;

	public Player()
	{

		backpack = new Backpack();
	}

	public Location location()
	{
		return location;
	}

	public Player setLocation(Location location)
	{
		this.location = location;
		return this;
	}

	public Actor inFocus()
	{
		return inFocus;
	}

	public void fight()
	{
		Combat combat = new Combat();
		combat.setResponder((Character) inFocus).setInstigator(this);
	}

	public Backpack backpack()
	{
		return backpack;
	}

}
