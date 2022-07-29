package src.Voter;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 *
 * @author fsonnessa
 */
public class TestVoter {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        BufferedReader reader;
        String line = "";
        try {
            reader = new BufferedReader(new FileReader("Certificati/Voters/.voters_list.txt"));
            line = reader.readLine();
            while (line != null) {
                String filename = "Certificati/Voters/" + line;
                Voter v = new Voter(filename + ".crt", filename + ".p8");
                System.out.println(v);
                //System.out.println(line);
                // read next line
                line = reader.readLine();
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Voter v = new Voter(Loader.loadCrtFromFile("Certificati/Voters/" + line + ".crt"));
        // System.out.println(v.getName());
    }

}
