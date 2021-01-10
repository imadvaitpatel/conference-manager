package Gateway;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;

public class ScheduleStorage {

    /**
     * Saves the schedule
     * @param codeStr string of code
     * @param outputPath location to save it
     */
    public boolean saveSchedule(String codeStr, String outputPath) {
        try {
            Path file = Paths.get(getProperPath(outputPath));
            Files.write(file, Collections.singleton(codeStr), StandardCharsets.UTF_8);
            return true;
        } catch (IOException ioe) {
            return false;
        }
    }

    private String getProperPath(String path) {
        if (path.endsWith(".html")) {
            return path;
        } else if (path.endsWith("/")) {
            return path + "Schedule.html";
        } else {
            return path + "/Schedule.html";
        }
    }
}
