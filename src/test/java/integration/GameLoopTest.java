package integration;

import actors.items.Item;
import game.Game;
import interactions.system.Menu;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class GameLoopTest {
	private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
	private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();
	private final PrintStream originalOut = System.out;
	private final PrintStream originalErr = System.err;
	Game game;

	@BeforeEach
	public void setUpStreams()
	{
		System.setOut(new PrintStream(outContent));
		System.setErr(new PrintStream(errContent));

	}

	@AfterEach
	public void restoreStreams()
	{
		System.setOut(originalOut);
		System.setErr(originalErr);

	}

	@Test
	@Order(1)
	public void theGameIsOn()
	{
		game = new Game();
		game.new_game().start();
		assertNotNull(game);
		assertNotNull(game.player());
	}

	@Test
	@Order(2)
	public void iAmAtStart()
	{
		iAmAt("Start");
	}

	@Test
	@Order(3)
	private void iStartWithAnEmptyBackpack()
	{
		assertEquals(game.player().backpack().size(), game.player().backpack().currentSpace());
	}

	@Test
	@Order(4)
	public void iGoToMalmoC()
	{
		iGoTo("Malmö C");
	}

	@Test
	@Order(5)
	public void iAmAtMalmoC()
	{
		iAmAt("Malmö C");
		//@fixme dont hardcode this shit
		game.player().location().matrix().getTile(0, 0).place(new Item().setSize(3).setName("Torch"));
	}

	@Test
	@Order(6)
	public void lookAround()
	{
		iLookAroundMyVicinity();
	}

	@Test
	@Order(7)
	public void iSeeATorch()
	{
		iSeeAnItem("Torch");
	}

	@Test
	@Order(8)
	public void iPickUpTorch()
	{
		iSay("pick up Torch");
	}

	@Test
	@Order(9)
	public void torchIsInBackpack()
	{
		theItemIsInMyBackpack("Torch");
	}

	@Test
	@Order(10)
	public void showBackpack()
	{
		iSay("open backpack");
	}

	@Test
	@Order(11)
	public void checkInventory()
	{
		iSeeMyInventory();
	}

	private void iSeeMyInventory()
	{
		assertEquals(Menu.instance().player().backpack().toString(), outContent.toString());
	}

	public void iAmAt(String place)
	{
		assertNotNull(game);
		assertEquals(place, game.player().location().name());
	}

	public void iGoTo(String place)
	{
		assertTrue(game.player().location().neighbours().stream().anyMatch(location -> location.name().equals(place)));

		Menu.instance().callCommand("travel to " + place, game.player());
		outContent.reset();

	}

	public void iSay(String command)
	{
		outContent.reset();
		Menu.instance().callCommand(command);

	}

	public void iLookAroundMyVicinity()
	{
		Menu.instance().callCommand("look around");
	}

	public void iSeeAnItem(String itemName)
	{
		assertTrue(outContent.toString()
				           .contains(itemName), String.format("Expected: %s%nActual: %s", itemName, outContent));
	}

	public void iHaveSpaceInMyBackpack(String itemName)
	{
		assertTrue(game.player().backpack().spaceCheck((Item) game.player().location().matrix().vicinity().stream()
				.filter(actor -> actor.name().equals(itemName)).findFirst().orElse(null)));
	}

	@SuppressWarnings("SameParameterValue")
	private void theItemIsInMyBackpack(String itemName)
	{
		assertTrue(game.player().backpack().contains(itemName));
	}

}


