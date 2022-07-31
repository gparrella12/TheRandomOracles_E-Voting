package src.CryptographicTools.ElGamalHomomorphic;

import java.io.File;
import java.io.FileNotFoundException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * This class contains the parameters that defines a cyclic group of order q. 
 * In particular, an object of this class contains 
 * two primes <code>p</code> and <code>q</code> such that:
 * <ul>
 * <li><code>p=2*q+1</code> </li>
 * <li><code>|q| = securityParameter bit</code>
 * (i.e. defines the length in bit of q)</li>
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
    
    // The prime q, with |q|= length in bit of securityParameter
    private BigInteger q;
    
    // The security Parameter
    private BigInteger securityParameter;

    /**
     * Creates an object of this class by reading the values of the parameters,
     * previously generated, from a file with a fixed name.
     */
    public CyclicGroupParameters() {
        this.importFromFile("parameters.txt"); // Default name
    }

    /**
     * Creates an object of this class by reading the values of the parameters,
     * previously generated, from file formatted as:
     * <code>
     * <br></br>
     * securityParameter <br></br>
     * g <br></br>
     * p <br></br>
     * q <br></br>
     * </code>
     *
     * @param fileName is the name of the file.
     */
    public CyclicGroupParameters(String fileName) {
        this.importFromFile(fileName);
    }

    /**
     * Creates an object of this class by passing:
     * <ul><li><code>g, p, q and the SECURITY_PARAMETER</code></li></ul>      
     * 
     * 
     * @param g generator of cyclic group of order q
     * @param p p=2*q+1
     * @param q a prime number, such |q|= the length in bit of securityParameter
     * @param SECURITY_PARAMETER the security parameter of the cyclic group
     */
    public CyclicGroupParameters(BigInteger g, BigInteger p, BigInteger q, BigInteger SECURITY_PARAMETER) {
        this.g = g;
        this.p = p;
        this.q = q;
        this.securityParameter = SECURITY_PARAMETER;
    }

    
     /**
     * This method reads the parameters from a file
     * 
     * @param fileName the name of the file
     */
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
     * This method returns the <code>g</code> value.
     * @return a BigInteger with <code>g</code> value.
     */
    public BigInteger getG() {
        return g;
    }

    /**
     * This method returns the <code>p</code> value.
     * @return a BigInteger with <code>p</code> value.
     */
    public BigInteger getP() {
        return p;
    }

    /**
     *  This method returns the <code>q</code> value..
     * @return a BigInteger with <code>q</code> value.
     */
    public BigInteger getQ() {
        return q;
    }

    /**
     * This method returns the <code>securityParameter</code> value.     * 
     * @return a BigInteger with <code>securityParameter</code> value.
     */
    public BigInteger getSecurityParameter() {
        return securityParameter;
    }

}
