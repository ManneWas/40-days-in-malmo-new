import game.Game;
import interactions.system.Menu;
import interactions.system.Terminal;

public class Main {
	public static void main(String[] args)
	{
		Game game = new Game().create_demo();
		game.start();

		Menu.instance().openTerminal();
	}
}
