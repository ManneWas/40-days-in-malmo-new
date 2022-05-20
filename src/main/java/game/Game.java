package game;

import actors.characters.Player;
import utilities.DbConnection;
import world.Location;

public class Game {



	Player player;

	public void new_game(){
		DbConnection.getInstance().getDatastore().save(new Location().setName("Start"));

		player = new Player().setLocation(DbConnection.getInstance().getDatastore().find(Location.class).first());
		DbConnection.getInstance().getDatastore().save(player);
	}

	public void load(){
		player = DbConnection.getInstance().getDatastore().find(Player.class).first();
	}
	public void save(){
		if (player!= null){
			DbConnection.getInstance().getDatastore().save(player);
		}
	}

	public void start()
	{
		if (player==null){
			load();
		}
	}

	public Player player()
	{
		return player;
	}
}
