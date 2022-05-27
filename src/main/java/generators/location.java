package generators;


import com.fasterxml.jackson.databind.ObjectMapper;
import world.*;

import java.io.*;
import java.nio.file.*;
import java.util.*;

public class location {
    public static void generate() {
        for (File file : files()) {
            if (filePath(file) != null) {
                Configuration configuration;
                try (Reader reader = Files.newBufferedReader(filePath(file))) {
                    ObjectMapper objectMapper = new ObjectMapper();
                    var location = objectMapper.readTree(file);
                    var neighbors = location.get("neighbours");
                    var items = location.get("items");
                    World.createLocation(location.get("name").asText());
                    for (String name : neighbors.findValuesAsText("name")) {
                        World.createLocation(name);
                    }
                    World.addNeighbours(location.get("name").asText(), neighbors.findValuesAsText("name"));
                }
                catch (IOException ignored) {

                }

            }
        }

    }

    private static List<File> files() {
        List<File> result;
        try (var files = Files.list(Paths.get("src/main/resources/locations")).filter(Files::isRegularFile)) {
            result = files.map(Path::toFile).toList();
        }
        catch (IOException e) {
            result = null;
        }
        return result;
    }


    private static Path filePath(final File file) {
        Path result;
        if (file.getName().contains("json")) {
            result = file.toPath();
        }
        else {result = null;}
        return result;
    }

    private record Configuration(String name, String[] neighbors, String[] items, String[] npcs) {

    }

}
