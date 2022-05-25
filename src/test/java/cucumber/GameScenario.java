package cucumber;

import actors.items.Item;
import game.Game;
import interactions.system.Menu;
import io.cucumber.java.en.*;
import org.junit.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.*;

public class GameScenario {
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    private final PrintStream originalErr = System.err;
    Game game;

    @Before
    public void setUpStreams() {
        System.setOut(new PrintStream(outContent));
        System.setErr(new PrintStream(errContent));
    }

    @After
    public void restoreStreams() {
        System.setOut(originalOut);
        System.setErr(originalErr);
    }

    @Test
    @Given("the game is on")
    public void theGameIsOn() {
        game = new Game();
        game.new_game().start();
        Assertions.assertNotNull(game);
        Assertions.assertNotNull(game.player());
    }
    @Test
    @Given("I am at {string}")
    public void iAmAt(String place) {
        Assertions.assertNotNull(game);
        Assertions.assertEquals(place, game.player().location().name());
        if (place.equals("Malmö C")) {
            game.player().location().matrix().getTile(0, 0).place(new Item().setName("Torch"));
        }
    }
    @Test
    @Given("the game prologue story is told")
    public void theGamePrologueStoryIsTold() {

    }


    @Test
    @Given("I start with an empty backpack")
    public void iStartWithAnEmptyBackpack() {
        //Assertions.assertEquals(game.player().getBackpack().getSpaceLimit(), game.player.getBackpack().getCurrentSpace());
    }

    @Test
    @When("I go to {string}")
    public void iGoTo(String place) {
        Assertions.assertTrue(game.player().location().neighbours().stream().anyMatch(location -> location.name().equals(place)));

        Menu.instance().callCommand("travel to " + place, game.player());

    }


    @Test
    @Given("I have a menu")
    public void iHaveAMenu() {
    }

    @Test
    @When("I quit")
    public void iQuit() {
        //  game.menu.callCommand("quit");
    }

    @Test
    @When("I say {string}")
    public void iSay(String command) {
        if (command.equalsIgnoreCase("talk")) {

        }
        else {
            Menu.instance().callCommand(command);
        }

    }

    @Test
    @When("I look around my vicinity")
    public void iLookAroundMyVicinity() {
        Menu.instance().callCommand("look around");
    }

    @Test
    @Given("I see a {string} item")
    public void iSeeAItem(String itemName) {

    }
}
