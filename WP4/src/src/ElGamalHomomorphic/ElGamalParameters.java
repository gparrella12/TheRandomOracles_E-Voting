package src.ElGamalHomomorphic;

import java.io.File;
import java.io.FileNotFoundException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author gparrella
 */
public class ElGamalParameters {

    private final BigInteger g;
    private final BigInteger p;
    private final BigInteger q;
    private BigInteger SECURITY_PARAMETER;

    public ElGamalParameters() {
        File file = new File("parameters.txt");
        List<BigInteger> parameters = new ArrayList<>();
        try {

            Scanner sc = new Scanner(file);

            while (sc.hasNextLine()) {
                BigInteger i = sc.nextBigInteger();
                parameters.add(i);
            }
            sc.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        SECURITY_PARAMETER = parameters.get(0);
        g = parameters.get(1);
        p = parameters.get(2);
        q = parameters.get(3);
    }

    public BigInteger getG() {
        return g;
    }

    public BigInteger getP() {
        return p;
    }

    public BigInteger getQ() {
        return q;
    }

    public BigInteger getSECURITY_PARAMETER() {
        return SECURITY_PARAMETER;
    }
    
    

}
