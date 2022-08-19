package CryptographicTools.Schnorr.NIZKP;

import java.io.Serializable;
import java.math.BigInteger;

/**
 * This class contains the elements to perform a Schnorr Non-interactive
 * Zero-Knowledge Proof used to prove that the owner of a public key knows its
 * secret key.
 *
 * @author gparrella
 */
public class SchnorrNIProof implements Serializable {

    private final BigInteger a; // a = g^r mod p, where r is some randommess
    private final BigInteger z; // z = (r + c*x) mod q

    /**
     * Creates a Schnorr Non-interactive Zero-Knowledge Proof by passing:
     * <ul>
     * <li><code>a = g<sup>r</sup> mod p</code></li>
     * <li><code>z = (r + c*x) mod q</code></li>
     * </ul>
     *
     * where:
     * <ul>
     * <li><code>x</code> is the secrete to prove</li>
     * <li><code>r</code> is randomness </li>
     * <li><code>p, q, g</code> are parameters of a cyclic group of order q, for
     * schemes for schemes that use the DLog </li>
     * </ul>
     *
     * @param a g<sup>r</sup> mod p
     * @param z (r + c*x) mod q
     */
    public SchnorrNIProof(BigInteger a, BigInteger z) {
        this.a = a;
        this.z = z;
    }

    /**
     * This method returns the <code>a</code> value, that is equal to
     * g<sup>r</sup> mod p.
     *
     * @return a <code>BigInteger</code> representing the <code>a</code> value.
     */
    public BigInteger getA() {
        return a;
    }

    /**
     * This method returns the <code>z</code> value, that is equal to
     * (r+c*x)mod(q)
     *
     * @return a <code>BigInteger</code> representing the <code>z</code> value.
     */
    public BigInteger getZ() {
        return z;
    }

    /**
     * This method prints the couple (a, z).
     *
     * @return a <code>String</code> containing the representation of (a , z).
     */
    @Override
    public String toString() {
        return "(\n \ta=" + a + "\n\tz=" + z + "\n)";
    }

}
