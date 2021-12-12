package Data;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.logging.Logger;

public interface Data {

    static Logger log = Logger.getLogger(Data.class.getName());

    public default String allData() throws IOException {
        // just read the whole file to string
        String allDataValues = new String(Files.readAllBytes(Paths.get("resources/data.txt")));
        return allDataValues;
    }


    public default String getLastLine() throws IOException {
        // this is to return the last values from the file
        String lastLine = "";
        log.info("Reading the file");
        String currentLIne;

        // read the file
        BufferedReader br = new BufferedReader(
                new FileReader("resources/data.txt"));

        // getting the last line
        while ((currentLIne = br.readLine()) != null) {
            lastLine = currentLIne;
        }
        log.info("Last line is: " + lastLine);

        return lastLine;
    }
}