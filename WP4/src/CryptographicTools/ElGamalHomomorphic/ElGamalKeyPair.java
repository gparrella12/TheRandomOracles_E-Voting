package CryptographicTools.ElGamalHomomorphic;

import java.math.BigInteger;
import java.security.SecureRandom;

/**
 * This class contains a Key Pair of ElGamal encryption scheme. The ElGamal
 * encryption scheme has a key pair <code>(sk,pk)</code> such that:
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
     * Creates an ElGamal key pair. This method uses the default parameters to
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
        // h <- Z_p*
        BigInteger h = new BigInteger(securityParameter, new SecureRandom()).mod(p);
        while(h.equals(BigInteger.ONE) || h.equals(BigInteger.ZERO)){
            h = new BigInteger(securityParameter, new SecureRandom()).mod(p);
        }
        // SK = h^2 mod p (because p=2q+1), this is a group element of cyclic group of order q [pag. 322]
        this.secretKey = h.modPow(new BigInteger("2"), p);
        if (isInQSubgroup(this.secretKey, p) == 0) {
            throw new RuntimeException("Malformed ElGamal key");
        }
        // PK = G^SK mod P
        this.publicKey = g.modPow(secretKey, p);
    }

    private static int isInQSubgroup(BigInteger x, BigInteger p) {
        // x ^ {(p-1)/2} mod p == 1 <-> x^q = 1 mod p [pag. 323]
        if (x.modPow(p.subtract(BigInteger.ONE).divide(BigInteger.TWO), p).compareTo(BigInteger.ONE) == 0) {
            return 1;
        }
        return 0;
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
