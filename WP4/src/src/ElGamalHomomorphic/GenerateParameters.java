package src.ElGamalHomomorphic;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigInteger;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author gparrella
 */
public class GenerateParameters {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        List<BigInteger> parameters = ElGamalParametersGeneration.getParameters();
        BufferedWriter writer;
        try {
            writer = new BufferedWriter(new FileWriter("parameters.txt"));
            writer.write(parameters.get(0).toString() + "\n"); // SECURITY PARAMETER
            writer.write(parameters.get(1).toString() + "\n"); // G
            writer.write(parameters.get(2).toString() + "\n"); // P
            writer.write(parameters.get(3).toString()); // Q
            writer.close();
        } catch (IOException ex) {
            Logger.getLogger(GenerateParameters.class.getName()).log(Level.SEVERE, null, ex);
        }
        

    }

}
