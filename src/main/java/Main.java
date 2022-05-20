import game.Game;

public class Main {
	public static void main(String[] args)
	{
		Game game = new Game();
		game.load();
		System.out.println(game.player().name());
		game.player().setName("torsten");
		game.save();
		System.out.println(game.player().name());
		System.out.println(game.player().location().name());
	}
}
