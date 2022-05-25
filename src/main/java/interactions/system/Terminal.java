package interactions.system;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Terminal {
	String line;
	final BufferedReader reader;
	private boolean playing = true;

	public Terminal()
	{
		reader = new BufferedReader(new InputStreamReader(System.in));
	}

	public void run()
	{
		printf("Welcome to the Terminal %n %s", "-".repeat(15));
        displayCommands();

        try {
			while ((line = reader.readLine()) != null) {
				Menu.instance().callCommand(line);
                displayCommands();

            }
		}
		catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void quit()
	{
		System.exit(0);
	}

	public Terminal setPlaying(boolean playing)
	{
		this.playing = playing;
		return this;
	}

	public void displayCommands()
	{
		printf("Command list: %n");
		Menu.instance().commands().stream().filter(command -> command.state() != Command.State.HIDDEN)
				.forEach(command -> printf("%s%s %n", command.signature(), command.description() != null ?
                                                                           " : " + command.description() + "." : "."));
        printf("%n");
	}

	private void printf(String format, Object... args)
	{
		System.out.printf(format, args);
	}
}
