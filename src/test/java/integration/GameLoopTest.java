package integration;

import actors.items.Item;
import game.Game;
import interactions.system.Menu;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;

import java.io.*;

@TestMethodOrder(OrderAnnotation.class)
public class GameLoopTest {
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    private final PrintStream originalErr = System.err;
    Game game;

    @BeforeEach
    public void setUpStreams() {
        System.setOut(new PrintStream(outContent));
        System.setErr(new PrintStream(errContent));
    }

    @AfterEach
    public void restoreStreams() {
        System.setOut(originalOut);
        System.setErr(originalErr);
    }

    @Test
    public void everything() {
        //Startup with Empty backpack
        theGameIsOn();
        iAmAt("Start");
        iStartWithAnEmptyBackpack();
        iGoTo("Malmö C");
        iAmAt("Malmö C");

        //@fixme dont hardcode this shit
        game.player().location().matrix().getTile(0, 0).place(new Item().setSize(3).setName("Torch"));


        //Find a torch
        iAmAt("Malmö C");
        iLookAroundMyVicinity();
        iSeeAnItem("Torch");

        // Pick up torch
        iSeeAnItem("Torch");
        iHaveSpaceInMyBackpack("Torch");
        iSay("pick up Torch");
        theItemIsInMyBackpack("Torch");
    }


    private void iStartWithAnEmptyBackpack() {
        Assertions.assertEquals(game.player().backpack().spaceLimit(), game.player().backpack().currentSpace());
    }


    public void theGameIsOn() {
        game = new Game();
        game.new_game().start();
        Assertions.assertNotNull(game);
        Assertions.assertNotNull(game.player());
    }


    public void iAmAt(String place) {
        Assertions.assertNotNull(game);
        Assertions.assertEquals(place, game.player().location().name());
    }



    public void iGoTo(String place) {
        Assertions.assertTrue(game.player().location().neighbours().stream().anyMatch(location -> location.name().equals(place)));

        Menu.instance().callCommand("travel to " + place, game.player());

    }



    public void iSay(String command) {
            Menu.instance().callCommand(command);
    }


    public void iLookAroundMyVicinity() {
        Menu.instance().callCommand("look around");
    }


    public void iSeeAnItem(String itemName) {
        Assertions.assertTrue(outContent.toString().contains(itemName));
    }

    public void iHaveSpaceInMyBackpack(String itemName) {
        Assertions.assertTrue(game.player().backpack().spaceCheck((Item) game.player().location().matrix().vicinity().stream().filter(actor -> actor.name().equals(itemName)).findFirst().orElse(null)));
    }


    private void theItemIsInMyBackpack(String itemName) {
        Assertions.assertTrue(game.player().backpack().contains(itemName));
    }

}


