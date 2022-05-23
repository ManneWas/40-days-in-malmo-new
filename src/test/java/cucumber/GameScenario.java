package cucumber;

import game.Game;
import interactions.*;
import io.cucumber.java.en.*;
import org.junit.jupiter.api.Assertions;

public class GameScenario {

    Game game;

    @Given("the game is on")
    public void theGameIsOn() {
        game = new Game();
        game.new_game().start();
        Assertions.assertNotNull(game);
        Assertions.assertNotNull(game.player());
    }

    @Given("I am at {string}")
    public void iAmAt(String place) {
        Assertions.assertEquals(place, game.player().location().name());

    }

    @Given("the game prologue story is told")
    public void theGamePrologueStoryIsTold() {

    }

    @Given("I start with an empty backpack")
    public void iStartWithAnEmptyBackpack() {
        //Assertions.assertEquals(game.player().getBackpack().getSpaceLimit(), game.player.getBackpack().getCurrentSpace());
    }

    @When("I go to {string}")
    public void iGoTo(String place) {
        Assertions.assertTrue(game.player().location().neighbours().stream().anyMatch(location -> location.name().equals(place)));
        new Terminal().displayCommands();
        Menu.instance().callCommand("alt travel to " + place, game.player());
    }


    @Given("I have a menu")
    public void iHaveAMenu() {
    }

    @When("I quit")
    public void iQuit() {
        //  game.menu.callCommand("quit");
    }

}
