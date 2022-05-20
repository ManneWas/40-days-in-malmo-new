package world;

import dev.morphia.annotations.Entity;
import dev.morphia.annotations.Id;

@Entity
public class Location {
	@Id
	String id;

	public String name()
	{
		return name;
	}

	public Location setName(String name)
	{
		this.name = name;
		return this;
	}

	String name;
}
