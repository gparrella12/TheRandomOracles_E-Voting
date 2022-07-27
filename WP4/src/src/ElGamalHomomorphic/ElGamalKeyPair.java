package src.ElGamalHomomorphic;import java.math.*;
import java.security.*;
import src.Authority.Authority;

/**
 *
 * @author Ernesto
 */
public class ElGamalKeyPair {

    private BigInteger secretKey;
    private BigInteger publicKey;

    public ElGamalKeyPair() {

        CyclicGroupParameters param = new CyclicGroupParameters();
        // Genera un elemento random di SECURITY_PARAMETER bit
        secretKey = new BigInteger(param.getSECURITY_PARAMETER().intValue(), new SecureRandom());
        // PK = G^SK mod P
        publicKey = param.getG().modPow(secretKey, param.getP());

    }

    public BigInteger getPublicKey() {
        return publicKey;
    }

    public BigInteger getSecretKey() {
        return secretKey;
    }
}
