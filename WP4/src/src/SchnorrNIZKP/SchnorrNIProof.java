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

    public SchnorrNIProof(BigInteger a, BigInteger c, BigInteger z) {
        this.a = a;
        this.c = c;
        this.z = z;
    }

    public BigInteger getA() {
        return a;
    }

    public BigInteger getC() {
        return c;
    }

    public BigInteger getZ() {
        return z;
    }

    @Override
    public String toString() {
        return "(" + "a=" + a + ", c=" + c + ", z=" + z + ')';
    }

}
