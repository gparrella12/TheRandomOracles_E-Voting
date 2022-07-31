package src.CryptographicTools.SchnorrNIZPK;

import java.io.Serializable;
import java.math.BigInteger;

/**
 * This class contains the elements to perform a 
 * Schnorr Non-interactive Zero-Knowledge Proof
 * used to prove that the owner of a public key knows its secret key
 *
 * @author gparrella
 */
public class SchnorrNIProof implements Serializable {

    private final BigInteger a; // a = g^r mod p, x is the secrete to demostrate and r some randommess
    private final BigInteger c; // c = H(y || a), con y=g^x mod p
    private final BigInteger z; // z = (r + c*x) mod q

    /**
     * Creates a Schnorr Non-interactive Zero-Knowledge Proof by passing:
     * <ul>
     * <li><code>a = g<sup>r</sup> mod p</code></li>
     * <li><code>c = H(y || a)</code>, whit <code>y=g<sup>x</sup> mod p</code></li>
     * <li><code>z = (r + c*x) mod q</code></li>
     * </ul>
     *
     * where:
     * <ul>
     * <li><code>x</code> is the secrete to prove</li>
     * <li><code>r</code> is randomness </li>
     * <li><code>p, q, g</code> are parameters of a cyclic group of order q,
     * for schemes for schemes that use the DLog </li>
     * </ul>
     *
     * @param a g<sup>r</sup> mod p
     * @param c H(g<sup>x</sup> mod p || g<sup>r</sup> mod p)
     * @param z (r + c*x) mod q
     */
    public SchnorrNIProof(BigInteger a, BigInteger c, BigInteger z) {
        this.a = a;
        this.c = c;
        this.z = z;
    }

    /**
     * This method returns the a value, that is g^r mod p
     * @return a BigInteger with <code>a</code> value.
     */
    public BigInteger getA() {
        return a;
    }

    /**
     * This method returns the c value, that is H(y || a)
     * @return a BigInteger with <code>c</code> value.
     */
    public BigInteger getC() {
        return c;
    }

    /**
     * This method returns the z value, that is (r + c*x) mod q
     * @return a BigInteger with <code>z</code> value.
     */
    public BigInteger getZ() {
        return z;
    }

    /**
     * This method returns the string representation of the triple (a,c,z)
     * 
     * @return a string containing the representation of (a,c,z)
     */
    @Override
    public String toString() {
        return "(" + "a=" + a + ", c=" + c + ", z=" + z + ')';
    }

}
