package actors.characters;

import dev.morphia.annotations.Entity;
import dev.morphia.annotations.Id;
import dev.morphia.annotations.Reference;
import world.Location;

@Entity
public class Player extends Character {
	@Id
	String id;

	public String name()
	{
		return name;
	}

	public Player setName(String name)
	{
		this.name = name;
		return this;
	}

	String name;

	@Reference
	Location location;

	public Location location()
	{
		return location;
	}

	public Player setLocation(Location location)
	{
		this.location = location;
		return this;
	}

}
