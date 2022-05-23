package interactions;

import actors.characters.Player;

import java.util.*;

public class Menu {

    public Player player() {
        return player;
    }

    public void setPlayer(final Player player) {
        this.player = player;
    }

    Player player;


    private void printf(String format, Object... args) {
        System.out.printf(format, args);
    }

    private static final Menu instance = new Menu();

    private Menu() {}

    public static Menu instance() {
        return instance;
    }

    private final List<Command> commands = new ArrayList<>();


    public void callCommand(String call) {
        List<Command> matches = commands.stream().filter(command -> call.contains(command.signature())).toList();

        if (matches.isEmpty()) {
            //@fixme Try and replace 10 with an argument, or remove completely. It's just for style anyways
            printf("Command: %10s not found", call);
        }
        else if (matches.size() == 1) {

            String args = call.replace(matches.get(0).signature(), "");
            matches.get(0).call(args);
        }
        else {
            printf("Commands matching the call: %n");
            matches.forEach(command -> printf("%s %n", command.signature()));
        }
    }

    public List<Command> commands() {
        return commands;
    }
}
