package src.ElGamalHomomorphic;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Ernesto
 */
public class ElGamalParametersGeneration {

    //Per velocità 5, poi andrà settato a 2048.
    private static BigInteger SECURITY_PARAMETER = BigInteger.valueOf(2048);
    private static List<BigInteger> schemeParameters;
    private static BigInteger g, p, q;

    public static List<BigInteger> getParameters() {

        if (schemeParameters != null && !schemeParameters.isEmpty()) {
            return schemeParameters;
        }

        schemeParameters = new ArrayList<>();
        SecureRandom sr = new SecureRandom();

        while (true) {
            q = BigInteger.probablePrime(SECURITY_PARAMETER.intValue(), sr);
            p = q.multiply(BigInteger.TWO);
            p = p.add(BigInteger.ONE);

            if (p.isProbablePrime(50) == true) {
                g = new BigInteger("4"); // da modificare
                schemeParameters.add(SECURITY_PARAMETER);
                schemeParameters.add(g);
                schemeParameters.add(p);
                schemeParameters.add(q);
                return schemeParameters;
            }

        }

    }

    public static BigInteger getSECURITY_PARAMETER() {
        return SECURITY_PARAMETER;
    }

    public static BigInteger getG() {
        return g;
    }

    public static BigInteger getP() {
        return p;
    }

    public static BigInteger getQ() {
        return q;
    }

}
