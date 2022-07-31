package src.CryptographicTools.ElGamalHomomorphic;

import java.math.BigInteger;
import java.security.SecureRandom;

/**
 * This class contains a Key Pair of ElGamal encryption scheme. The ElGamal
 * encryption scheme has a key pair <code>(sk,pk)</code> such that:
 * <ul>
 * <li><code>sk=x</code> , with <code>x</code> randomly chosen in
 * <code>Z_q</code>.</li>
 * <li><code>pk = g^x mod p</code>, where <code>x</code> is the secret key
 * value.
 * </ul>
 *
 * @author Ernesto
 */
public class ElGamalKeyPair {

    private final BigInteger secretKey;
    private final BigInteger publicKey;

    /**
     * Create an ElGamal key pair. This methods use the default parameters to
     * generate a cyclic group of order q by using an object of
     * <code>CyclicGroupParameters</code> class.
     */
    public ElGamalKeyPair() {
        // Take the default parameters of a cyclic group of order q.
        CyclicGroupParameters param = new CyclicGroupParameters();
        int securityParameter = param.getSecurityParameter().intValue();
        BigInteger q = param.getQ();
        BigInteger p = param.getP();
        BigInteger g = param.getG();

        // Take a random value of securityParameter bit.
        // SK <- Z_q
        this.secretKey = new BigInteger(securityParameter, new SecureRandom()).mod(q);

        // PK = G^SK mod P
        this.publicKey = g.modPow(secretKey, p);
    }

    /**
     * Get the public key value of the pair.
     *
     * @return a BigInteger with the public key value.
     */
    public BigInteger getPublicKey() {
        return publicKey;
    }

    /**
     * Get the secret key value of the pair.
     *
     * @return a BigInteger with the secret key value.
     */
    public BigInteger getSecretKey() {
        return secretKey;
    }
}
