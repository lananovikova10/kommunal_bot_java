package Data;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.logging.Logger;

public interface DocksEntityDataGenerator {

    // adding logger to DocksEntityDataGenerator to log something instead of using println()
    static Logger log = Logger.getLogger(DocksEntityDataGenerator.class.getName());

    public default String allData() throws IOException {
        // just read the whole file to string to send via telegram bot
        String allDataValues = new String(Files.readAllBytes(Paths.get("resources/data.txt")));
        return allDataValues;
    }


    public default String getLastLine() throws IOException {
        // this is to return the last values from the file
        // using in 2 cases: request from user for last data
        // in calcNew function to calc difference with new values
        String lastLines = "";
        log.info("Reading the file");
        String currentLIne;
        String entitiesIds = "";

        // reads the file
        BufferedReader br = new BufferedReader(
                new FileReader("resources/data.txt"));

        // getting the last line
        while ((currentLIne = br.readLine()) != null) {
            lastLines = currentLIne;
        }
        String lastLine;
        log.info("Last line is: " + lastLine);

        return lastLine;
        return entitiesIds;
    }
}