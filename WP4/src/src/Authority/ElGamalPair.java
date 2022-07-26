package src.Authority;
import java.math.*;
import java.security.*;
import java.util.List;

/**
 *
 * @author Ernesto
 */
public class ElGamalPair {

    private BigInteger g, p, q;
    private BigInteger SECURITY_PARAMETER;
    private BigInteger skValue;
    private BigInteger pkValue;

    public ElGamalPair() {
        List<BigInteger> schemeParameters = ElGamalParameters.getParameters();
        SECURITY_PARAMETER = schemeParameters.get(0);
        g = schemeParameters.get(1);
        p = schemeParameters.get(2);
        q = schemeParameters.get(3);
        SecureRandom sr = new SecureRandom();

        skValue = new BigInteger(SECURITY_PARAMETER.intValue(), sr);
        pkValue = g.modPow(skValue, p);

    }

    public BigInteger getPkValue() {
        return pkValue;
    }

    public BigInteger getSkValue() {
        return skValue;
    }

}
