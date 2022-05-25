package generators;

import world.Location;
import world.World;

import java.util.ArrayList;
import java.util.List;

public class location {
	public void generate(){
		List<String> locations = new ArrayList<>();
		for (String name:locations){
			new World().createLocation(name);

		}
		for (String place:locations){

			new World().addNeighbours(place,new ArrayList<String>());

		}
	}
}
