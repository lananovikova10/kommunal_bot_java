package Calcs;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class Calcs {

    private static Logger log = Logger.getLogger(Calcs.class.getName());

    public String getLastLine() throws IOException {
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


    public String allData() throws IOException {
        // just read the whole file to string
        String allDataValues = new String(Files.readAllBytes(Paths.get("resources/data.txt")));
        return allDataValues;
    }

    public String allHistory() {
        // TODO
        // read the file with data in reverse order and calc the history of changes
        String allHistoryChanges = null;
        return allHistoryChanges;
    }

    public String calcNew(String msg) throws IOException {

        log.info("Calculating differences and amount to pay for");

        // making the list of new values from string given
        List<Integer> newValuesI = Arrays.stream(msg.split(" "))
                .skip(msg.startsWith("/new ") ? 1 : 0)
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .map(s -> "_".equals(s) ? null : Integer.valueOf(s)).collect(Collectors.toList());

        int new_t1 = newValuesI.get(0);
        int new_t2 = newValuesI.get(1);
        int new_gor = newValuesI.get(2);
        int new_hol = newValuesI.get(3);
        log.info(String.valueOf("New values are: " + new_t1 + ", " + new_t2 + ", " + new_gor + ", " + new_hol));

        // getting previous values (last line)
        String lastLine = getLastLine();

        int[] oldValuesI = new int[lastLine.length()];
        String[] oldValues = lastLine.split(",");

        // making ints
        for (int index = 0; index < oldValues.length; index++) {
            String sValue = oldValues[index];
            oldValuesI[index] = Integer.parseInt(oldValues[index].trim());
        }

        log.info("getting last: " + oldValuesI);

        int t1 = oldValuesI[0];
        int t2 = oldValuesI[1];
        int gor = oldValuesI[2];
        int hol = oldValuesI[3];
        log.info(String.valueOf("Old values are: " + t1 + ", " + t2 + ", " + gor + ", " + hol));

        // calculating the whole sum
        double sum = sum = ((new_t1 - t1) * 5.92) + ((new_t2 - t2) * 1.74) +
                ((new_gor - gor) * 205.15) + ((new_hol - hol) * 42.30);
        // light and water
        double s_svet = ((new_t1 - t1) * 5.92) + ((new_t2 - t2) * 1.74);
        double s_voda = ((new_gor - gor) * 205.15) + ((new_hol - hol) * 42.30);

        log.info("Calcs.Calcs: " + sum + ", " + s_svet + ", " + s_voda);

        List<Double> list = Arrays.asList(sum, s_svet, s_voda);
        log.info(String.valueOf(list));

        return String.valueOf(list);
    }

    public String saveNewToFile(String msg) throws IOException {
        // saving data given after "/save" command
        // make ints
        List<Integer> newValuesI = Arrays.stream(msg.split(" "))
                .skip(msg.startsWith("/save ") ? 1 : 0)
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .map(s -> "_".equals(s) ? null : Integer.valueOf(s)).collect(Collectors.toList());

        // making string to save
        String stringToSave = "\n" + newValuesI.get(0) + "," + newValuesI.get(1) + "," + newValuesI.get(2) + "," + newValuesI.get(3);
        log.info("String to save: " + stringToSave);

        // writing to the file
        Writer output;
        output = new BufferedWriter(new FileWriter("resources/data.txt", true));
        output.append(stringToSave);
        output.close();

        return "Saved";
    }
}
