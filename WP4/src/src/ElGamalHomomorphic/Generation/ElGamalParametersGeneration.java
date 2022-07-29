package src.ElGamalHomomorphic.Generation;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Ernesto
 */
public class ElGamalParametersGeneration {

    public static List<BigInteger> generateParameters() {
        BigInteger SECURITY_PARAMETER = BigInteger.valueOf(2048);
        List<BigInteger> schemeParameters;
        BigInteger g, p, q;

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

}
