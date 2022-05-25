package game;

import actors.characters.Player;
import actors.items.Item;
import dev.morphia.Datastore;
import interactions.system.Menu;
import world.Location;
import world.World;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static utilities.DbConnection.instance;

public class Game {

	Player player;
	final Menu menu;

	public Game() {menu = Menu.instance();}

	public Game new_game()
	{
		List<String> place_names = Arrays.asList("Malmö C", "Möllan", "Södervärn", "Kirseberg", "Mobilia", "Emporia");
		World world = new World();
		world.populate(place_names);
		Location playerLocation;
		Datastore datastore = instance().datastore();
		player = new Player();
		Menu.instance().setPlayer(player);
		Objects.requireNonNull(world.fetchLocation("Start")).enter();
		datastore.save(player);
		return this;
	}

	public void load()
	{
		player = instance().datastore().find(Player.class).first();
	}

	public void save()
	{
		if (player != null) {
			instance().datastore().save(player);
		}
	}

	public void start()
	{
		if (player == null) {
			load();
		}
	}

	public Player player()
	{
		return player;
	}

	public Game create_demo()
	{
		List<String> place_names = Arrays.asList("Malmö C", "Möllan", "Södervärn", "Kirseberg", "Mobilia", "Emporia");
		World world = new World();
		world.populate(place_names);
		Location playerLocation;
		Datastore datastore = instance().datastore();
		player = new Player();
		Menu.instance().setPlayer(player);
		Objects.requireNonNull(world.fetchLocation("Start")).enter();
		world.asMock();

		datastore.save(player);
		return this;
	}
}
