package src.CryptographicTools.ElGamalHomomorphic;

import java.io.File;
import java.io.FileNotFoundException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * This class contains the parameters that defines a cyclic group of order q. In
 * particular, an object of this class contains two primes <code>p</code> and
 * <code>q</code> such that:
 * <ul>
 * <li><code>p=2*q+1</code> </li>
 * <li><code>|q| = securityParameter bit</code> (so the securityParameter
 * defines the q length in bit)</li>
 * <li><code>g</code> is a generator of the cyclic group of order
 * <code>q</code></li>
 * </ul>
 *
 * @author gparrella
 */
public class CyclicGroupParameters {

    // Generator of cyclic group of order q
    private BigInteger g;
    // The prime p = 2*q+1
    private BigInteger p;
    // The prime q, with |q|= securityParameter bit
    private BigInteger q;
    // The security Parameter
    private BigInteger securityParameter;

    /**
     * Creates an object of this class by reading the values of the parameters
     * from a default file previously generated.
     */
    public CyclicGroupParameters() {
        this.importFromFile("parameters.txt"); // Default name
    }

    /**
     * Creates an object of this class by reading the values of the parameters
     * from file formatted as:      <code>
     * securityParameter \n
     * g \n
     * p \n
     * q \n
     * </code>
     *
     * @param fileName is the name of the file.
     */
    public CyclicGroupParameters(String fileName) {
        this.importFromFile(fileName);
    }

    /**
     *
     * @param g
     * @param p
     * @param q
     * @param SECURITY_PARAMETER
     */
    public CyclicGroupParameters(BigInteger g, BigInteger p, BigInteger q, BigInteger SECURITY_PARAMETER) {
        this.g = g;
        this.p = p;
        this.q = q;
        this.securityParameter = SECURITY_PARAMETER;
    }

    private void importFromFile(String fileName) {
        File file = new File(fileName);
        List<BigInteger> parameters = new ArrayList<>();
        try(Scanner sc = new Scanner(file)) {

            while (sc.hasNextLine()) {
                BigInteger i = sc.nextBigInteger();
                parameters.add(i);
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return;
        }

        securityParameter = parameters.get(0);
        g = parameters.get(1);
        p = parameters.get(2);
        q = parameters.get(3);
    }

    /**
     * Get the <code>g</code> value.
     * @return a BigInteger with <code>g</code> value.
     */
    public BigInteger getG() {
        return g;
    }

    /**
     * Get the <code>p</code> value.
     * @return a BigInteger with <code>p</code> value.
     */
    public BigInteger getP() {
        return p;
    }

    /**
     * Get the <code>q</code> value.
     * @return a BigInteger with <code>g</code> value.
     */
    public BigInteger getQ() {
        return q;
    }

    /**
     * Get the <code>securityParameter</code> value.
     * @return a BigInteger with <code>g</code> value.
     */
    public BigInteger getSecurityParameter() {
        return securityParameter;
    }

}
