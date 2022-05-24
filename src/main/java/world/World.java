package world;

import dev.morphia.Datastore;
import org.jetbrains.annotations.Nullable;
import utilities.DbConnection;

import java.util.List;

public class World {




    public void populate(List<String> locations) {
        Datastore datastore = DbConnection.instance().datastore();
        if (fetchLocation("Start") == null) {
            datastore.save(new Location().setName("Start"));
        }
        Location start = fetchLocation("Start");
        assert start != null;
        for (String name : locations) {
            if (fetchLocation(name) == null) {
                datastore.save(new Location().setName(name));
            }
            Location location = fetchLocation(name);
            assert location != null;
            if (start.neighbours().stream().noneMatch(neighbour -> neighbour.name().equals(location.name()))) {
                start.neighbours().add(location);
                printf("Adding %s to start alternatives", location.name());
            }
        }
        datastore.save(start);
    }

    private void createLocation(String name){

    }

    @Nullable
    public Location fetchLocation(String name) {
        return DbConnection.instance().datastore().find(Location.class).stream().filter(location -> location.name().equalsIgnoreCase(name)).findFirst().orElse(null);
    }

    public void printf(String string, Object... args) {
        System.out.printf(string, args);
    }


}
