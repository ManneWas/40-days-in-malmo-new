package game;

import actors.characters.Player;
import dev.morphia.Datastore;
import interactions.system.Menu;
import world.*;

import java.util.*;

import static utilities.DbConnection.instance;

public class Game {

	Player player;
	final Menu menu;

	public Game() {menu = Menu.instance();}

	public Game new_game()
	{

		Location playerLocation;
		Datastore datastore = instance().datastore();
		player = new Player();
		Menu.instance().setPlayer(player);
		Objects.requireNonNull(World.fetchLocation("Start")).enter();
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


}
