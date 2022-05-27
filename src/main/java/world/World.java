package world;

import actors.characters.Enemy;
import actors.items.Item;
import dev.morphia.Datastore;
import utilities.DbConnection;

import java.util.List;

import static interactions.system.Menu.instance;

public class World {

    public void populate(List<String> locations) {
        Datastore datastore = DbConnection.instance().datastore();
        if (fetchLocation("Start") == null) {
            datastore.save(new Location().setName("Start"));
        }
        Location start = fetchLocation("Start");
        assert start != null;
        for (String name : locations) {
            createLocation(name);
            Location location = fetchLocation(name);
            assert location != null;
        }
        addNeighbours("Start", locations);
        datastore.save(start);
    }

    public void asMock() {
        Location m = instance().player().location().neighbours().stream()
                .filter(location -> location.name().equals("Malmö C")).findFirst().orElse(null);
        if (m != null) {
            m.matrix().addTile(new Tile(), 0, 0);
            m.matrix().getTile(0, 0).place(new Item().setName("Torch"));
            m.matrix().getTile(0, 0).place(new Enemy().setName("Marauder"));
        }
    }

    public static void createLocation(String name) {
        if (fetchLocation(name) == null) {
            DbConnection.instance().datastore().save(new Location().setName(name));
            System.out.printf("Creating location: %s%n", name);
        }
    }

    public static void addNeighbours(String place, List<String> neighbours) {
        Location location = fetchLocation(place);
        assert location != null;
        System.out.printf("Adding neighbours to %s:%n",place);
        for (String neighbourName : neighbours) {
            if (location.neighbours().stream()
                    .noneMatch(neighbourLocation -> neighbourLocation.name().equals(neighbourName))) {
                createLocation(neighbourName);
                location.neighbours().add(fetchLocation(neighbourName));
                System.out.printf("Neighbour added: %s%n",neighbourName);
            }
            System.out.printf("%n");
        }
        DbConnection.instance().datastore().save(location);
    }

    public static Location fetchLocation(String name) {
        return DbConnection.instance().datastore().find(Location.class).stream()
                .filter(location -> location.name().equalsIgnoreCase(name)).findFirst().orElse(null);
    }

    public void printf(String string, Object... args) {
        System.out.printf(string, args);
    }

}
