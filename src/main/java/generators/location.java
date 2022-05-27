package generators;

import org.json.JSONObject;

import java.io.*;
import java.nio.file.*;
import java.util.List;

public class location {
    public void generate() {
        try (var files = Files.list(Paths.get("folder"))
                .filter(Files::isRegularFile)) {
            for (File file : files.map(Path::toFile).toList()) {
                if (file.getName().contains(".json")) {
                    JSONObject locationInfo = new JSONObject(Files.readAllBytes(file.toPath()));
                    System.out.println(locationInfo);
                }
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    private record configuration(String name, List<String> neighbors, List<String> items, List<String> npcs) {

    }

}
