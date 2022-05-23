package interactions;

import java.io.*;

public class Terminal {
    String line;
    final BufferedReader reader;

    public Terminal() {
        reader = new BufferedReader(new InputStreamReader(System.in));
    }

    public void run() {
        printf("Welcome to the Terminal %n %s", "-".repeat(15));
        try {
            while ((line = reader.readLine()) != null) {
                displayCommands();
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }


    }

    public void displayCommands() {
        printf("Command list: %n");
        Menu.instance().commands().stream().filter(command -> command.state() != Command.State.HIDDEN).forEach(command -> printf("%s : %s %n", command.name(), command.description()));
    }

    private void printf(String format, Object... args) {
        System.out.printf(format, args);
    }
}
