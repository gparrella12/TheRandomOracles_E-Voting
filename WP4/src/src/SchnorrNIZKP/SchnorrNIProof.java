package src.SchnorrNIZKP;

import java.io.Serializable;
import java.math.BigInteger;

/**
 *
 * @author gparrella
 */
public class SchnorrNIProof implements Serializable{

    private BigInteger a; // a = g^r, x is the secrete to demostrate and r some randommess
    private BigInteger c; // c = H(y || a), con y=g^x mod p
    private BigInteger z; // z = (r + c*x) mod q

    /**
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
