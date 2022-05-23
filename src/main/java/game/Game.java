package game;

import actors.characters.Player;
import dev.morphia.Datastore;
import interactions.Menu;
import org.jetbrains.annotations.Nullable;
import utilities.DbConnection;
import world.Location;


public class Game {


    Player player;
    final Menu menu;

    public Game() {menu = Menu.instance();}

    public Game new_game() {
        Location playerLocation;
        Datastore datastore = DbConnection.instance().datastore();
        if (fetchStartLocation() == null) {
            datastore.save(new Location().setName("Start"));
        }
        if (fetchMalmoC() == null) {
            datastore.save(new Location().setName("Malmö C"));
        }
        playerLocation = fetchStartLocation();
        Location malmoC = fetchMalmoC();
        assert playerLocation != null;
        assert malmoC != null;
        if (playerLocation.neighbours().stream().noneMatch(location -> location.name().equals(malmoC.name()))) {
            playerLocation.neighbours().add(malmoC);
            datastore.save(playerLocation);
            System.out.println("Adding Malmö C to Start location neighbours");
        }
        player = new Player();
        Menu.instance().setPlayer(player);
        playerLocation.alternate_enter(player);
        datastore.save(player);
        return this;
    }


    public void load() {
        player = DbConnection.instance().datastore().find(Player.class).first();
    }

    public void save() {
        if (player != null) {
            DbConnection.instance().datastore().save(player);
        }
    }

    public void start() {
        if (player == null) {
            load();
        }
    }

    public Player player() {
        return player;
    }

    @Nullable
    private Location fetchStartLocation() {
        return DbConnection.instance().datastore().find(Location.class).stream().filter(location -> location.name().equalsIgnoreCase("Start")).findFirst().orElse(null);
    }

    @Nullable
    private Location fetchMalmoC() {
        return DbConnection.instance().datastore().find(Location.class).stream().filter(location -> location.name().equalsIgnoreCase("Malmö C")).findFirst().orElse(null);

    }

}
