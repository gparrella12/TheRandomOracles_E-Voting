package src.CryptographicTools.ElGamalHomomorphic;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigInteger;
import java.security.SecureRandom;

/**
 * This class contains a main method that generate the parameters for the cyclic
 * group of order q.
 *
 * @author gparrella
 */
public class GenerateParameters {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        BigInteger securityParameter = BigInteger.valueOf(2048);
        String filename = "parameters.txt";
        BigInteger g, p, q;
        SecureRandom sr = new SecureRandom();

        while (true) {
            q = BigInteger.probablePrime(securityParameter.intValue(), sr);
            p = q.multiply(BigInteger.TWO);
            p = p.add(BigInteger.ONE);

            if (p.isProbablePrime(50) == true) {
                g = new BigInteger("4");
                try ( BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
                    writer.write(securityParameter.toString() + "\n"); // SECURITY PARAMETER
                    writer.write(g.toString() + "\n"); // G
                    writer.write(p.toString() + "\n"); // P
                    writer.write(q.toString()); // Q
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                break;
            }

        }

    }

}
