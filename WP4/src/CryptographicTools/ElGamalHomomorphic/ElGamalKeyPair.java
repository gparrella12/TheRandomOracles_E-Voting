package CryptographicTools.ElGamalHomomorphic;

import java.math.BigInteger;
import java.security.SecureRandom;

/**
 * This class contains a Key Pair of ElGamal encryption scheme. 
 * The ElGamal encryption scheme has a key pair <code>(sk,pk)</code> such that:
 * <ul>
 * <li><code>(secrete key) sk=x</code> , with <code>x</code> randomly chosen in
 * <code>Z_q</code>.</li>
 * 
 * <li><code>(public key) pk = g<sup>x</sup> mod p</code></li>
 * 
 * </ul>
 *
 * @author Ernesto
 */
public class ElGamalKeyPair {

    private final BigInteger secretKey;
    private final BigInteger publicKey;

    /**
     * Creates an ElGamal key pair. 
     * This method uses the default parameters to
     * generate a cyclic group of order q by using an object of
     * <code>CyclicGroupParameters</code> class.
     */
    public ElGamalKeyPair() {
        // Take the default parameters of a cyclic group of order q.
        CyclicGroupParameters param = CyclicGroupParameters.getInstance();
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
     * This method returns the public key value of the ElGamal pair.
     *
     * @return a <code>BigInteger</code> with the public key value.
     */
    public BigInteger getPublicKey() {
        return publicKey;
    }

    /**
     * This method returns the secrete key value of the ElGamal pair.
     *
     * @return a <code>BigInteger</code> with the secret key value.
     */
    public BigInteger getSecretKey() {
        return secretKey;
    }
}
