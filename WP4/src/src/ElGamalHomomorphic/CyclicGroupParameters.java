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
public class CyclicGroupParameters {

    private BigInteger g;
    private BigInteger p;
    private BigInteger q;
    private BigInteger SECURITY_PARAMETER;

    public CyclicGroupParameters() {
        this.importFromFile("parameters.txt"); // Default name
    }

    public CyclicGroupParameters(String fileName) {
        this.importFromFile(fileName);
    }

    public CyclicGroupParameters(BigInteger g, BigInteger p, BigInteger q, BigInteger SECURITY_PARAMETER) {
        this.g = g;
        this.p = p;
        this.q = q;
        this.SECURITY_PARAMETER = SECURITY_PARAMETER;
    }

    private void importFromFile(String fileName) {
        File file = new File(fileName);
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
