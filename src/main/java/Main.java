import game.Game;
import interactions.system.Menu;

public class Main {
	public static void main(String[] args)
	{
		Game game = new Game().new_game();
		game.start();

		Menu.instance().openTerminal();
	}
}
