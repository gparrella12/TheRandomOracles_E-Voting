package src.CryptographicTools.SchnorrNIZPK;

import java.io.Serializable;
import java.math.BigInteger;

/**
 * This class contains the triad (a,b,c) to perform a ShnorrNIZKP, where:
 * <ul>
 * <li><code>a = g^r mod p</code></li>
 * <li><code>c = H(y || a)</code>, whit <code>y=g^x mod p</code></li>
 * <li><code>z = (r + c*x) mod q</code></li>
 * </ul>
 *
 * <code>x</code> is the secrete to demostrate and <code>r</code> some
 * randommess <code>p, q, g</code> are parameters of cyclic group for schemes
 * who uses DLog problem
 *
 * @author gparrella
 */
public class SchnorrNIProof implements Serializable {

    private final BigInteger a; // a = g^r mod p, x is the secrete to demostrate and r some randommess
    private final BigInteger c; // c = H(y || a), con y=g^x mod p
    private final BigInteger z; // z = (r + c*x) mod q

    /**
     * Creates a SchnorrNIProof by passing:
     * <ul>
     * <li><code>a = g^r mod p</code></li>
     * <li><code>c = H(y || a)</code>, whit <code>y=g^x mod p</code></li>
     * <li><code>z = (r + c*x) mod q</code></li>
     * </ul>
     *
     * Where
     * <ul>
     * <li><code>x</code> is the secrete to demostrate and <code>r</code> some
     * randommess</li>
     * <li><code>p, q, g</code> are parameters of cyclic group for schemes who
     * uses DLog problem</li>
     * </ul>
     *
     * @param a
     * @param c
     * @param z
     */
    public SchnorrNIProof(BigInteger a, BigInteger c, BigInteger z) {
        this.a = a;
        this.c = c;
        this.z = z;
    }

    /**
     *
     * @return
     */
    public BigInteger getA() {
        return a;
    }

    /**
     *
     * @return
     */
    public BigInteger getC() {
        return c;
    }

    /**
     *
     * @return
     */
    public BigInteger getZ() {
        return z;
    }

    /**
     *
     * @return
     */
    @Override
    public String toString() {
        return "(" + "a=" + a + ", c=" + c + ", z=" + z + ')';
    }

}
